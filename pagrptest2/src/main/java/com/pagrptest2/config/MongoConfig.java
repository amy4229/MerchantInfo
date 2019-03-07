package com.pagrptest2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig {

	/*
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	protected String getDatabaseName() {
		return database;
	}

	public MongoClient mongo() throws Exception {
		return new MongoClient(new ServerAddress("localhost", 9090));
	}
	
	public @Bean MongoTemplate mongoTemplate() throws Exception{ 
		return new MongoTemplate(mongo(),database);
	}*/
	
}
