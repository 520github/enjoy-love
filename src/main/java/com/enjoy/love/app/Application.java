package com.enjoy.love.app;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import com.jolbox.bonecp.BoneCPDataSource;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		String id = applicationContext.getId();
		System.out.println("id--->" + id);
	}
	
	@Bean
	public DataSource getDataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(getEnv("db.driver", "org.h2.Driver"));
		dataSource.setJdbcUrl(getEnv("db.url", "jdbc:h2:mem:datajpa;DB_CLOSE_ON_EXIT=false"));
		dataSource.setUsername(getEnv("db.username", "sa"));
		dataSource.setPassword(getEnv("db.password", ""));
		return dataSource;
	}
	
	private static String getEnv(String name, String defaultValue) {
		String value = System.getenv(name);
		
		if(StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		else{
			return value;
		}
	}

}
