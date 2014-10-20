package com.lms.dto;

import java.util.Date;

import com.lms.dto.DAL.LeavesRequest;

public class LeaveRecord {

	LeaveRecord(User user, LeavesRequest.Record rec) {
		this.leaveId = rec.leave_id;
		this.employeeId = user.getEmpId();
		this.name = user.getName();
		this.fromDate = rec.from_date;
		this.toDate = rec.to_date;
		this.totalLeavesInDuration = rec.total_leaves_in_duration;
		this.paidLeavesInDuration = rec.paid_leaves_in_duration;
		this.unpaidLeavesInDuration = rec.unpaid_leaves_in_duration;
		this.leaveType = rec.leave_type;
		this.status = rec.status;
		this.aproverId = user.getDirectManager();
		this.remark = rec.remark;
		this.department = user.getDepartment();

	}
	
	public Integer getLeaveId() {
		return leaveId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public String getName() {
		return name;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public Integer getTotalLeavesInDuration() {
		return totalLeavesInDuration;
	}
	public Integer getPaidLeavesInDuration() {
		return paidLeavesInDuration;
	}
	public Integer getUnpaidLeavesInDuration() {
		return unpaidLeavesInDuration;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public String getStatus() {
		return status;
	}
	public String getAproverId() {
		return aproverId;
	}
	public String getRemark() {
		return remark;
	}
	public String getDepartment() {
		return department;
	}

	private Integer leaveId;
	private String employeeId;
	private String name;
	private Date fromDate;
	private Date toDate;
	private Integer totalLeavesInDuration;
	private Integer paidLeavesInDuration;
	private Integer unpaidLeavesInDuration;
	private String leaveType;
	private String status;
	private String aproverId;
	private String remark;
	private String department;
	
}
