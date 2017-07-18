package com.main.java.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.main.java.model.Application;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;

public abstract class BaseImportService<T,V>{
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
		
	 public  String batchCommit(List<V> list, Class<T> type) {
		 
		 
         SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
         //通过新的session获取mapper
         T repository = session.getMapper(type);

         try {
    		 //如果不是增量添加，需在子类实现deleteAllData
			 deleteAllData();   

        	 IntStream.range(0, list.size()).forEach(i -> {
        		 batchInsertInfo(repository, list.get(i));
        		 if ((i % Constants.COMMIT_COUNT_EVERY_TIME == 0  && i > 0)|| i == list.size() - 1) {
                     //手动每1000个一提交，提交后无法回滚
                     session.commit();
                    //清理缓存，防止溢出
                     session.clearCache();
                }        		 
        	 });
        	 
         }
        catch (Exception e) {
             e.printStackTrace();
             session.rollback();
             return Constants.RESULT_FAIL;
         } finally {
             if (session != null) {
                 session.close();
             }
         }
         
         return Constants.RESULT_SUCCESS;
     }
	 
	 
	 protected abstract void batchInsertInfo(T mapper, V bean) ;
	 
	 protected void deleteAllData() {}
	 
	 
	 
	 
	 public Workbook writeNewExcel(File file, V searchParam) throws Exception {
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		       // 打开HSSFWorkbook
	        POIFSFileSystem fs = new POIFSFileSystem(in);
	        HSSFWorkbook wb = new HSSFWorkbook(fs);
			Sheet sheet = wb.getSheetAt(0);

			// 循环插入数据
			CellStyle cs = ExcelUtil.setSimpleCellStyle(wb); // Excel单元格样式
			
			writeCells(wb, sheet, cs, searchParam);
			
			return wb;
		}
	 
	 protected abstract void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs, V searchParam);


}


