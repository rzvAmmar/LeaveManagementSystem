package com.lms.dto.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Date;
import java.util.Date;

public class EmployeeDetails {

	public static class Record {

		public Record() {
		}

		public Record(String empId, String name, String email,
				Integer privilege, String department, String manager,
				Date date_of_joining, Date date_of_birth, String contact_number) {
			this.employee_id = empId;
			this.name = name;
			this.email_id = email;
			this.privilege = privilege;
			this.department = department;
			this.manager_employee_id = manager;
			this.date_of_joining = date_of_joining;
			this.date_of_birth = date_of_birth;
			this.contact_number = contact_number;
		}

		public String employee_id;
		public String name;
		public String email_id;
		public Integer privilege;
		public String department;
		public String manager_employee_id;
		public Date date_of_joining;
		public Date date_of_birth;
		public String contact_number;
	}

	public static Record by_employee_id(Connection con, String employee_id) {

		Record rec = new Record();
		try {
			PreparedStatement statement = con
					.prepareStatement("select * from employee_details where employee_id = ?");
			statement.setString(1, employee_id);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				System.out.println("Record: " + result.getString(1) + "  "
						+ result.getString(2));
				rec.employee_id = result.getString(1);
				rec.name = result.getString(2);
				rec.email_id = result.getString(3);
				rec.privilege = result.getInt(4);
				rec.department = result.getString(5);
				rec.manager_employee_id = result.getString(6);
				rec.date_of_joining = result.getDate(7);
				rec.date_of_birth = result.getDate(8);
				rec.contact_number = result.getString(9);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rec;
	}

	public static int insert(Connection con, Record rec) throws SQLException {

		int status = 0;

		PreparedStatement statement;
		statement = con.prepareStatement("insert into employee_details "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		statement.setString(1, rec.employee_id);
		statement.setString(2, rec.name);
		statement.setString(3, rec.email_id);
		statement.setLong(4, rec.privilege);
		statement.setString(5, rec.department);
		statement.setString(6, rec.manager_employee_id);
		statement.setDate(7, new java.sql.Date(rec.date_of_joining.getTime()));
		statement.setDate(8, new java.sql.Date(rec.date_of_birth.getTime()));
		statement.setString(9, rec.contact_number);

		status = statement.executeUpdate();
		statement.close();
		return status;
	}
}
