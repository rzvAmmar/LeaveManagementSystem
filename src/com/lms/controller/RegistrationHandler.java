package com.lms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dto.DAL.EmployeeDetails;
import com.lms.dto.DAL.LeaveBalance;
import com.lms.dto.DAL.LoginDetails;
import com.lms.dto.DAL.MySQL;

/**
 * Servlet implementation class RegistrationHandler
 */
@WebServlet("/RegistrationHandler")
public class RegistrationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String empId = request.getParameter("empId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String department = request.getParameter("department");
		String manager = request.getParameter("manager");
		String password = request.getParameter("password");

		String leavesAvail = request.getParameter("leavesAvail");
		Integer leavesAvailable = 0;
		if(leavesAvail != null && !leavesAvail.isEmpty()) {
			leavesAvailable = Integer.parseInt(leavesAvail);
		}
		String leavesUnPlan = request.getParameter("leavesUnPlan");
		Integer leavesUnplanned = 0;
		if(leavesUnPlan != null && !leavesUnPlan.isEmpty()) {
			leavesUnplanned = Integer.parseInt(leavesUnPlan);
		}

		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_of_birth = null;
		try {
			date_of_birth = date_format
					.parse(request.getParameter("birthDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date date_of_joining = null;
		try {
			date_of_joining = date_format.parse(request
					.getParameter("joinDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String contact_number = request.getParameter("contactNumber");

		Integer privilege = 2;
		String temp_privilege = request.getParameter("privilege"); // adm or hrm
																	// or reg
		if (temp_privilege.equalsIgnoreCase("adm"))
			privilege = 0;
		else if (temp_privilege.equalsIgnoreCase("hrm"))
			privilege = 1;

		LoginDetails.Record loginDetailsRec = new LoginDetails.Record(empId,
				password);
		LeaveBalance.Record leaveBalanceRec = new LeaveBalance.Record(empId,
				leavesAvailable, leavesUnplanned);
		EmployeeDetails.Record employeeDetailsRec = new EmployeeDetails.Record(
				empId, name, email, privilege, department, manager,
				date_of_joining, date_of_birth, contact_number);

		Connection con = MySQL.connect();
		
		try {
			con.setAutoCommit(false);
			
			LoginDetails.insert(con, loginDetailsRec);
			LeaveBalance.insert(con, leaveBalanceRec);
			EmployeeDetails.insert(con, employeeDetailsRec);
			con.commit();
			String serverMsg = "User was registered successfully.";
			request.setAttribute("serverMsg", serverMsg);

		} catch (SQLException e) {
			try {
				con.rollback();
				e.printStackTrace();
				String serverMsg = "Warning! User was NOT registered.";
				request.setAttribute("serverMsg", serverMsg);
			} catch (SQLException e1) {
				e1.printStackTrace();
				String serverMsg = "Error! Please contact system admin.";
				request.setAttribute("serverMsg", serverMsg);
			}
		}

		MySQL.close(con);
	
		RequestDispatcher req = request.getRequestDispatcher("redirect.jsp");
		req.forward(request, response);			
	}
}
