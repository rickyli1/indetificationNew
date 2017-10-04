package com.main.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MongoDBConfig extends AbstractMongoConfiguration{
    @Override
    protected String getDatabaseName() {
        return "test"; //数据库名
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);//数据库IP和端口
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
    	GridFsTemplate gridFsTemplate = new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    	
    	return gridFsTemplate;
    }

}
