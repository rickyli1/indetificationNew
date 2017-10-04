package com.main.java.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
@Deprecated
@MapperScan("com.main.java.repository")
public class DataSourceConfig {

	  @Bean
	  public DataSource dataSource() {
		    BasicDataSource ds = new BasicDataSource();

		    
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
	        ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8");
	        ds.setUsername("root");
	       // ds.setPassword("123qaz");
	        ds.setPassword("abc456123");
	        ds.setInitialSize(5); //初始大小
	        ds.setMaxIdle(5);
	        ds.setMinIdle(2);
	        ds.setDefaultAutoCommit(false);
	        ds.setPoolPreparedStatements(true);
	        return ds;
	  }

	  @Bean
	  public SqlSessionFactory sqlSessionFactory() throws Exception {
	    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	    sessionFactory.setDataSource(dataSource());
	    return sessionFactory.getObject();
	  }
	  
      @Bean
      public DataSourceTransactionManager transactionManager() {
          return new DataSourceTransactionManager(dataSource());
      }
}
