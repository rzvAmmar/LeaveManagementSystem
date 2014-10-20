package com.lms.dto.DAL;

import java.sql.Connection;

public class LoginService {

	public static boolean authenticate(String username, String password) {
		
		if(username.isEmpty() || password.isEmpty()) return false;
	
		try {
			Connection con = MySQL.connect();
			
			LoginDetails.Record rec = LoginDetails.by_username(con, username);
			
			if(rec != null && !rec.password.isEmpty() && rec.password.equals(password)) {
				System.out.println("Password is correct!\n");
				return true;
			} else {
				System.out.println("Password is not correct!\n");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
