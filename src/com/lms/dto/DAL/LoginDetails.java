package com.lms.dto.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDetails {

	public static class Record {
		public Record() {
		}

		public Record(String e, String p) {
			this.employee_id = e;
			this.password = p;
		}

		public String employee_id;
		public String password;
		// public String security_question;
		// public String security_answer;
	}

	public static Record by_username(Connection con, String username)
			throws SQLException {
		PreparedStatement statement = con
				.prepareStatement("select * from login_details where employee_id = ?");
		statement.setString(1, username);
		ResultSet result = statement.executeQuery();
		Record rec = new Record();
		while (result.next()) {
			System.out.println("Record: " + result.getString(1) + "  "
					+ result.getString(2));
			rec.employee_id = result.getString(1);
			rec.password = result.getString(2);
			// rec.security_question = result.getString(3);
			// rec.security_answer = result.getString(4);
		}
		statement.close();
		return rec;
	}

	public static int insert(Connection con, Record rec) throws SQLException {

		int status = 0;
		PreparedStatement statement;
		statement = con
				.prepareStatement("insert into login_details values(?, ?)");
		statement.setString(1, rec.employee_id);
		statement.setString(2, rec.password);
		status = statement.executeUpdate();
		statement.close();

		return status;
	}
}
