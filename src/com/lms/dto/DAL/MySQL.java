package com.lms.dto.DAL;

import java.sql.*;

public class MySQL {
	
	public static void makeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection connect() {
		makeConnection();
		String host = "jdbc:mysql://localhost/lms_fastenal";
		String username = "webuser";
		String password = "fastenal123";
		
		try {			
			Connection connection = DriverManager.getConnection(host, username, password);
			return connection;		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	public static boolean close(Connection c) {
		try {
			c.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
