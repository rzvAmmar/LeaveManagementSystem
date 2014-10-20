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

import com.lms.dto.User;
import com.lms.dto.DAL.LeaveBalance;
import com.lms.dto.DAL.LeavesRequest;
import com.lms.dto.DAL.LeavesRequest.Record;
import com.lms.dto.DAL.MySQL;

/**
 * Servlet implementation class LeaveRequestHandler
 */
@WebServlet("/LeaveRequestHandler")
public class LeaveRequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");

		System.out.println("Frome date " + request.getParameter("fromDate"));
		System.out.println("To Date " + request.getParameter("toDate"));
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

		Date fromDate = null;
		try {
			fromDate = date_format
					.parse(request.getParameter("fromDate"));
			System.out.println("TESTING From : " + fromDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Date toDate = null;
		try {
			toDate = date_format.parse(request.getParameter("toDate"));
			System.out.println("TESTING TO : " + toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String totalLeavesStr = request.getParameter("totalLeaves");
		Integer totalLeaves = 0;
		if (totalLeavesStr != null && !totalLeavesStr.isEmpty()) {
			totalLeaves = Integer.parseInt(totalLeavesStr);
		}
		String paidLeavesStr = request.getParameter("paidLeaves");
		Integer paidLeaves = 0;
		if (paidLeavesStr != null && !paidLeavesStr.isEmpty()) {
			paidLeaves = Integer.parseInt(paidLeavesStr);
		}
		String unpaidLeavesStr = request.getParameter("unpaidLeaves");
		Integer unpaidLeaves = 0;
		if (unpaidLeavesStr != null && !unpaidLeavesStr.isEmpty()) {
			unpaidLeaves = Integer.parseInt(unpaidLeavesStr);
		}

		String leaveType = request.getParameter("leaveType");

		String remark = request.getParameter("remark");

		if (paidLeaves <= user.getTotalLeavesUnplanned() && !fromDate.after(toDate)) {

			Record rec = new Record(user, fromDate, toDate, totalLeaves, paidLeaves,
					unpaidLeaves, leaveType, remark);

			Connection con = MySQL.connect();
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				MySQL.close(con);
				String serverMsg = "Error! Unable to connect DB.";
				request.setAttribute("serverMsg", serverMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
				dispatcher.forward(request, response);
			}
			
			try {
				LeavesRequest.insert(con, rec);
				LeaveBalance.decreaseUnplanned(con, user.getEmpId(), paidLeaves);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String serverMsg = "Error! Leaves NOT updated.";
				request.setAttribute("serverMsg", serverMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
				dispatcher.forward(request, response);
			}
			
			try {
				con.commit();
				LoginHandler.refreshUser(request, response);
				MySQL.close(con);	
				String serverMsg = "Request submitted successfully.";
				request.setAttribute("serverMsg", serverMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
				dispatcher.forward(request, response);

			} catch (SQLException e) {
				MySQL.close(con);
				String serverMsg = "Error! Something went wrong, contact admin.";
				request.setAttribute("serverMsg", serverMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
				dispatcher.forward(request, response);
			}
			
		} else if (fromDate.after(toDate)) {
			String serverMsg = "From date cannot be after To Date.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher req = request.getRequestDispatcher("redirect.jsp");
			req.forward(request, response);			
		} else {
			String serverMsg = "You Dont have enough paid Leaves. Either cancel any future leave or go for Unpaid Leave.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher req = request.getRequestDispatcher("redirect.jsp");
			req.forward(request, response);			
		}				
	}
}
