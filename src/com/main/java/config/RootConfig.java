package com.main.java.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration  
@ComponentScan(basePackages={"com.main.java.service"},excludeFilters={@Filter(type=FilterType.ANNOTATION,value=Controller.class)})  
@MapperScan("com.main.java.repository")
@EnableTransactionManagement
@Import({ WebSecurityConfig.class })
public class RootConfig {

	  @Bean
	  public DataSource dataSource() {
		    BasicDataSource ds = new BasicDataSource();

		    
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
	        ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8");
	        ds.setUsername("root");
	        ds.setPassword("123qaz");
	        //ds.setPassword("abc456123");
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
	     ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();        

	    sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
	    sessionFactory.setTypeAliasesPackage("com.main.java.model");
	    return sessionFactory.getObject();
	  }
	  
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
