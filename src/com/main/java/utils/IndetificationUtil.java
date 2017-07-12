package com.main.java.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class IndetificationUtil {
	 /**
	 * 批量提交数据
	 * @param sqlSessionFactory
	 * @param mybatisSQLId SQL语句在Mapper XML文件中的ID
	 * @param list 要提交的数据列表
	 */
	 public static <T> void batchCommit(SqlSessionFactory sqlSessionFactory, String mybatisSQLId,  List<T> list) {
	         SqlSession session = null;
	         try {
	             session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	             int commitCount = (int) Math.ceil(list.size() / (double) Constants.COMMIT_COUNT_EVERY_TIME);
	             List<T> tempList = new ArrayList<T>(Constants.COMMIT_COUNT_EVERY_TIME);
	             int start, stop;
	             for (int i = 0; i < commitCount; i++) {
	                 tempList.clear();
	                 start = i * Constants.COMMIT_COUNT_EVERY_TIME;
	                 stop = Math.min(i * Constants.COMMIT_COUNT_EVERY_TIME + Constants.COMMIT_COUNT_EVERY_TIME - 1, list.size() - 1);
	                 for (int j = start; j <= stop; j++) {
	                     tempList.add(list.get(j));
	                 }
	                 session.insert(mybatisSQLId, tempList);
	                 session.commit();
	                 session.clearCache();
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	             session.rollback();
	         } finally {
	             if (session != null) {
	                 session.close();
	             }
	         }
	     }

}
