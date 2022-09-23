package com.training.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class ConnectionFactory {
	public static Connection getMySqlConnection() {
		Connection con =null;
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			String url = bundle.getString("datasource.url");
			String userName = bundle.getString("datasource.username");
			String password = bundle.getString("datasource.password");
			
			con = DriverManager.getConnection(url, userName, password);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
