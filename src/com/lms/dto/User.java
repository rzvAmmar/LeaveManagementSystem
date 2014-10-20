package com.lms.dto;

import java.sql.Connection;
import java.util.Deque;
import java.util.LinkedList;

import com.lms.dto.DAL.EmployeeDetails;
import com.lms.dto.DAL.LeaveBalance;
import com.lms.dto.DAL.LeavesRequest;
import com.lms.dto.DAL.MySQL;

public class User {

	private String empId;
	private String name;
	private String emailAddress;
	private String department;
	private String directManager;
	private Integer totalLeavesLeft;
	private Integer totalLeavesUnplanned;
	private Integer privilege;

	public User(final String empId) {
		Connection con = MySQL.connect();
		this.empId = empId;

		EmployeeDetails.Record emp_details = EmployeeDetails.by_employee_id(
				con, empId);
		this.name = emp_details.name;
		this.emailAddress = emp_details.email_id;
		this.department = emp_details.department;
		this.directManager = emp_details.manager_employee_id;
		this.privilege = emp_details.privilege;

		LeaveBalance.Record leave_balance = LeaveBalance.by_employee_id(con,
				empId);
		this.totalLeavesLeft = leave_balance.leaves_available;
		this.totalLeavesUnplanned = leave_balance.leaves_unplanned;

		MySQL.close(con);
	}

	public String getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getDepartment() {
		return department;
	}

	public String getDirectManager() {
		return directManager;
	}

	public Integer getTotalLeavesLeft() {
		return totalLeavesLeft;
	}

	public Integer getTotalLeavesUnplanned() {
		return totalLeavesUnplanned;
	}
	
	public Integer getPrivilege() {
		return this.privilege;
	}

	public Deque<LeaveRecord> getLeavesRequests() {
		Connection con = MySQL.connect();
		Deque<LeavesRequest.Record> recs = LeavesRequest.by_employee_id(con,
				empId);
		Deque<LeaveRecord> LeaveRecords = new LinkedList<LeaveRecord>();
		for(LeavesRequest.Record rec: recs) {
			LeaveRecords.add(new LeaveRecord(this, rec));
		}
		MySQL.close(con);
		return LeaveRecords;
	}
	
	public Deque<LeaveRecord> getPendingApprovals() {
		Connection con = MySQL.connect();
		Deque<LeavesRequest.Record> recs = LeavesRequest.pending_by_approver_id(con,
				empId);
		Deque<LeaveRecord> LeaveRecords = new LinkedList<LeaveRecord>();
		for(LeavesRequest.Record rec: recs) {
			LeaveRecords.add(new LeaveRecord(new User(rec.employee_id), rec));
		}
		MySQL.close(con);
		return LeaveRecords;
	}

	public Deque<LeaveRecord> getAllApprovals() {
		Connection con = MySQL.connect();
		Deque<LeavesRequest.Record> recs = LeavesRequest.by_approver_id(con,
				empId);
		Deque<LeaveRecord> LeaveRecords = new LinkedList<LeaveRecord>();
		for(LeavesRequest.Record rec: recs) {
			LeaveRecords.add(new LeaveRecord(new User(rec.employee_id), rec));
		}
		MySQL.close(con);
		return LeaveRecords;
	}	
	
	public boolean isUserPrivileged() {
		// This function will return true for user with privilege greater than
		// equal to HR Manager.
		if (privilege <= 1)
			return true;

		return false;
	}
}
