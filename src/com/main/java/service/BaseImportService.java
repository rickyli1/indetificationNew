package com.main.java.service;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.main.java.utils.Constants;

public abstract class BaseImportService<T,V> {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
		
	 public  String batchCommit(List<V> list, Class<T> type) {
		 
		 
         SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
         //通过新的session获取mapper
         T repository = session.getMapper(type);

         try {
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

}


