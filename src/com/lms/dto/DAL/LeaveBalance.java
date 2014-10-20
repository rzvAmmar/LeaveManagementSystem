package com.lms.dto.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveBalance {

	public static class Record {

		public Record() {
		}

		public Record(String empId, Integer leavesAvailable,
				Integer leavesUnplanned) {
			this.employee_id = empId;
			this.leaves_available = leavesAvailable;
			this.leaves_unplanned = leavesUnplanned;
		}

		public String employee_id;
		public Integer leaves_available;
		public Integer leaves_unplanned;

	}

	public static Record by_employee_id(Connection con, String empId) {

		Record rec = new Record();
		try {
			PreparedStatement statement = con
					.prepareStatement("select * from leave_balance where employee_id = ?");
			statement.setString(1, empId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				System.out.println("Record: " + result.getString(1) + "  "
						+ result.getString(2));
				rec.employee_id = result.getString(1);
				rec.leaves_available = result.getInt(2);
				rec.leaves_unplanned = result.getInt(3);
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
		statement = con
				.prepareStatement("insert into leave_balance values(?, ?, ?)");
		statement.setString(1, rec.employee_id);
		statement.setLong(2, rec.leaves_available);
		statement.setLong(3, rec.leaves_unplanned);
		status = statement.executeUpdate();
		statement.close();

		return status;
	}

	public static int updateAvailableLeaves(Connection con, Record rec,
			Integer paidLeaves) {

		int status = 0;
		try {
			PreparedStatement statement;
			statement = con
					.prepareStatement("update leave_balance set leaves_unplanned = ? where employee_id = ?");

			int tempNumber = rec.leaves_unplanned - paidLeaves;
			statement.setInt(1, tempNumber);
			statement.setString(2, rec.employee_id);
			status = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;

	}

	public static void increaseUnplanned(Connection con, String employeeId,
			Integer paidLeavesInDuration) throws SQLException {
		String sqlQuery = "update leave_balance set leaves_unplanned = leaves_unplanned + ?	"
				+ "where employee_id = ?";
		PreparedStatement statement = con.prepareStatement(sqlQuery);

		statement.setInt(1, paidLeavesInDuration);
		statement.setString(2, employeeId);

		System.out.println("SQL: " + statement.toString());
		statement.executeUpdate();
		statement.close();
	}

	public static void decreaseUnplanned(Connection con, String employeeId,
			Integer paidLeavesInDuration) throws SQLException {
		String sqlQuery = "update leave_balance set leaves_unplanned = leaves_unplanned - ?	"
				+ "where employee_id = ?";
		PreparedStatement statement = con.prepareStatement(sqlQuery);

		statement.setInt(1, paidLeavesInDuration);
		statement.setString(2, employeeId);

		System.out.println("SQL: " + statement.toString());
		statement.executeUpdate();
		statement.close();
	}

	public static void decreasePlanned(Connection con, String employeeId,
			Integer paidLeavesInDuration) throws SQLException {
		String sqlQuery = "update leave_balance set leaves_available = leaves_available - ? "
				+ "where employee_id = ?";
		PreparedStatement statement = con.prepareStatement(sqlQuery);
		
		statement.setInt(1, paidLeavesInDuration);
		statement.setString(2, employeeId);

		System.out.println("SQL: " + statement.toString());
		statement.executeUpdate();
		statement.close();
	}

	public static void increasePlanned(Connection con, String employeeId,
			Integer paidLeavesInDuration) throws SQLException {
		String sqlQuery = "update leave_balance set leaves_available = leaves_available + ? "
				+ "where employee_id = ?";
		PreparedStatement statement = con.prepareStatement(sqlQuery);

		statement.setInt(1, paidLeavesInDuration);
		statement.setString(2, employeeId);

		System.out.println("SQL: " + statement.toString());
		statement.executeUpdate();
		statement.close();
	}

}
