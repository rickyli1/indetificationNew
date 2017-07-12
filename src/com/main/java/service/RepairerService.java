package com.main.java.service;


import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.java.model.Repairer;
import com.main.java.repository.RepairRepository;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;

@Service
public class RepairerService {
	
	@Autowired
	private RepairRepository repairRepository;
	
	//@Autowired
	//private DataSourceTransactionManager transactionManager;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	
	public int getRepairersCount() {
		return repairRepository.findAllRepairersCount();
	}
	
	
	public List<Repairer> findAllRepairersForExport() {
		return repairRepository.findAllRepairersForExport();
	}
	
	public List<Repairer> findAllRepairers(){
		return  repairRepository.findAllRepairers();
	}
	


	public String importRepairers(Principal adminUser, Sheet sheet) throws UnsupportedEncodingException {
		List<Repairer> repairers = new ArrayList<>();
		
		for (Row row : sheet) {
			if (row.getRowNum() < 1) {
				continue;
			}else{
				Repairer repairer = new Repairer();
				for (Cell cell : row) {
		    		String cellValue = ExcelUtil.getCellValue(cell);
		    		
		    		switch (cell.getColumnIndex()) {
		    		    //序号
			    		case 0:
			    			repairer.setRepairerNo(cellValue);
			    		break;	
			    			
		                //辖区
			    		case 1:
			    			repairer.setRepairerArea(cellValue);
			    		break;
			    		
		                //修理单位名称
			    		case 2:
			    			repairer.setRepairerName(cellValue);
			    		break;
			    		
		                //单位性质
			    		case 3:
			    			repairer.setRepairerLevel(cellValue);
			    		break;
			    	}
		    		
				}
				repairer.setCreateor(adminUser.getName());
				repairer.setUpdateor(adminUser.getName());
				repairers.add(repairer);
			}
		}
		
		batchCommit(repairers);
		return Constants.RESULT_SUCCESS;
	}
	
	 private  void batchCommit(List<Repairer> list) {
         SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
         //通过新的session获取mapper
         RepairRepository repairRepository = session.getMapper(RepairRepository.class);

         try {
             for (int i = 0; i < list.size(); i++) {
            	 repairRepository.importRepairers(list.get(i));
                     if ((i % Constants.COMMIT_COUNT_EVERY_TIME == 0  && i > 0)|| i == list.size() - 1) {
                         //手动每1000个一提交，提交后无法回滚
                         session.commit();
                        //清理缓存，防止溢出
                         session.clearCache();
                    }
             }
         }
        catch (Exception e) {
             e.printStackTrace();
             session.rollback();
         } finally {
             if (session != null) {
                 session.close();
             }
         }
     }

	

}
