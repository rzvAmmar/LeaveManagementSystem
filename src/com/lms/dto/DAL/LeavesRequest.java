package com.lms.dto.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

import com.lms.dto.User;

public class LeavesRequest {
	public static class Record {

		public Record() {
		}

		public Record(User user, Date fromDate, Date toDate,
				Integer totalLeaves, Integer paidLeaves, Integer unpaidLeaves,
				String leaveType, String remark) {

			this.employee_id = user.getEmpId();
			this.from_date = fromDate;
			this.to_date = toDate;
			this.total_leaves_in_duration = totalLeaves;
			this.paid_leaves_in_duration = paidLeaves;
			this.unpaid_leaves_in_duration = unpaidLeaves;
			this.leave_type = leaveType;
			this.status = "PENDING";
			this.aprover_id = user.getDirectManager();
			this.remark = remark;

		}

		public Integer leave_id;
		public String employee_id;
		public Date from_date;
		public Date to_date;
		public Integer total_leaves_in_duration;
		public Integer paid_leaves_in_duration;
		public Integer unpaid_leaves_in_duration;
		public String leave_type;
		public String status;
		public String aprover_id;
		public String remark;

	}

	public static int insert(Connection con, Record rec) {
		int status = 0;
		try {
			PreparedStatement statement;
			statement = con
					.prepareStatement("insert into leave_request "
							+ "(employee_id, from_date, to_date, total_leaves_in_duration, "
							+ "paid_leaves_in_duration, unpaid_leaves_in_duration,"
							+ "leave_type, status, approver_id, remark)"
							+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			statement.setString(1, rec.employee_id);
			statement.setDate(2, new java.sql.Date(rec.from_date.getTime()));
			statement.setDate(3, new java.sql.Date(rec.to_date.getTime()));
			statement.setInt(4, rec.total_leaves_in_duration);
			statement.setInt(5, rec.paid_leaves_in_duration);
			statement.setInt(6, rec.unpaid_leaves_in_duration);
			statement.setString(7, rec.leave_type);
			statement.setString(8, rec.status);
			statement.setString(9, rec.aprover_id);
			statement.setString(10, rec.remark);

			System.out.println("insert SQL: " + statement.toString());
			status = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public static Deque<Record> by_employee_id(Connection con, String empId) {

		Deque<Record> recs = new LinkedList<Record>();

		try {
			PreparedStatement statement = con
					.prepareStatement("select * from leave_request where employee_id = ?");
			statement.setString(1, empId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Record rec = new Record();
				rec.leave_id = result.getInt(1);
				rec.employee_id = result.getString(2);
				rec.from_date = result.getDate(3);
				rec.to_date = result.getDate(4);
				rec.total_leaves_in_duration = result.getInt(5);
				rec.paid_leaves_in_duration = result.getInt(6);
				rec.unpaid_leaves_in_duration = result.getInt(7);
				rec.leave_type = result.getString(8);
				rec.status = result.getString(9);
				rec.aprover_id = result.getString(10);
				rec.remark = result.getString(11);
				recs.add(rec);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recs;
	}

	public static Deque<LeavesRequest.Record> by_approver_id(Connection con,
			String empId) {

		Deque<Record> recs = new LinkedList<Record>();

		try {
			PreparedStatement statement = con
					.prepareStatement("select * from leave_request where approver_id = ?");
			statement.setString(1, empId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				System.out.println("Record: " + result.getString(1) + "  "
						+ result.getString(2));
				Record rec = new Record();
				rec.leave_id = result.getInt(1);
				rec.employee_id = result.getString(2);
				rec.from_date = result.getDate(3);
				rec.to_date = result.getDate(4);
				rec.total_leaves_in_duration = result.getInt(5);
				rec.paid_leaves_in_duration = result.getInt(6);
				rec.unpaid_leaves_in_duration = result.getInt(7);
				rec.leave_type = result.getString(8);
				rec.status = result.getString(9);
				rec.aprover_id = result.getString(10);
				rec.remark = result.getString(11);
				recs.add(rec);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recs;
	}

	public static Deque<LeavesRequest.Record> pending_by_approver_id(
			Connection con, String empId) {
		Deque<Record> recs = new LinkedList<Record>();

		try {
			PreparedStatement statement = con
					.prepareStatement("select * from leave_request where approver_id = ?"
							+ "and status = ?");
			statement.setString(1, empId);
			statement.setString(2, "PENDING");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				System.out.println("Record: " + result.getString(1) + "  "
						+ result.getString(2));
				Record rec = new Record();
				rec.leave_id = result.getInt(1);
				rec.employee_id = result.getString(2);
				rec.from_date = result.getDate(3);
				rec.to_date = result.getDate(4);
				rec.total_leaves_in_duration = result.getInt(5);
				rec.paid_leaves_in_duration = result.getInt(6);
				rec.unpaid_leaves_in_duration = result.getInt(7);
				rec.leave_type = result.getString(8);
				rec.status = result.getString(9);
				rec.aprover_id = result.getString(10);
				rec.remark = result.getString(11);
				recs.add(rec);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recs;
	}

	public static int updateLeavesStatus(Connection con,
			Deque<Integer> approvedLeaveIds, Deque<Integer> rejectedLeaveIds) throws SQLException {

		if (approvedLeaveIds.size() > 0) {
			String sqlQuery = "update leave_request set status = ? where leave_id in (";
			boolean first = true;
			for (@SuppressWarnings("unused")
			Integer leaveId : approvedLeaveIds) {
				if (first) {
					sqlQuery += " ?";
					first = false;
				} else {
					sqlQuery += ", ?";
				}
			}
			sqlQuery += ")";
			PreparedStatement statement = con.prepareStatement(sqlQuery);

			statement.setString(1, "APPROVED");
			int i = 1;
			for (Integer leaveId : approvedLeaveIds) {
				i++;
				statement.setInt(i, leaveId);
			}
			System.out.println("SQL: " + statement.toString());
			statement.executeUpdate();
			statement.close();
		}

		if (rejectedLeaveIds.size() > 0) {
				String sqlQuery = "update leave_request set status = ? where leave_id in (";
				boolean first = true;
				for (@SuppressWarnings("unused")
				Integer leaveId : rejectedLeaveIds) {
					if (first) {
						sqlQuery += " ?";
						first = false;
					} else {
						sqlQuery += ", ?";
					}
				}
				sqlQuery += ")";
				PreparedStatement statement;
				statement = con.prepareStatement(sqlQuery);

				statement.setString(1, "REJECTED");
				int i = 1;
				for (Integer leaveId : rejectedLeaveIds) {
					i++;
					statement.setInt(i, leaveId);
				}
				System.out.println("SQL: " + statement.toString());
				statement.executeUpdate();
				statement.close();
		}
		return 1;
	}

	public static int cancelLeaves(Connection con, Deque<Integer> cancelLeaveIds) throws SQLException {

		if (cancelLeaveIds.size() > 0) {
				String sqlQuery = "delete from leave_request where leave_id in (";
				boolean first = true;
				for (@SuppressWarnings("unused")
				Integer leaveId : cancelLeaveIds) {
					if (first) {
						sqlQuery += " ?";
						first = false;
					} else {
						sqlQuery += ", ?";
					}
				}
				sqlQuery += ")";
				PreparedStatement statement = con.prepareStatement(sqlQuery);

				int i = 0;
				for (Integer leaveId : cancelLeaveIds) {
					i++;
					statement.setInt(i, leaveId);
				}
				System.out.println("SQL: " + statement.toString());
				statement.executeUpdate();
				statement.close();
		}
		return 1;
	}

}
