package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-uat.properties")
public class ConnectionJDBCUtil {

	@Value("${spring.datasource.url}")
	static String DB_URL;

	static final String USER = "root";
	static final String PASS = "123456";
	
	public static Connection getConnection() {
		Connection conn = null ;
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
