package com.enjoy.love.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.jolbox.bonecp.BoneCPDataSource;

//@Configuration
//@ComponentScan
public class PersistenceContext {
	
	//@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(getEnv("db.driver", "org.h2.Driver"));
		dataSource.setJdbcUrl(getEnv("db.url",
				"jdbc:h2:mem:datajpa;DB_CLOSE_ON_EXIT=false"));
		dataSource.setUsername(getEnv("db.username", "sa"));
		dataSource.setPassword(getEnv("db.password", ""));

		return dataSource;
	}

	private static String getEnv(String name, String defaultValue) {
		String value = System.getenv(name);

		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}
}
