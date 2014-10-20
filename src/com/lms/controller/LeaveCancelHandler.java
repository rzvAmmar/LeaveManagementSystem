package com.lms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dto.LeaveRecord;
import com.lms.dto.DAL.LeaveBalance;
import com.lms.dto.DAL.LeavesRequest;
import com.lms.dto.DAL.MySQL;

/**
 * Servlet implementation class LeaveStatusHandler
 */
@WebServlet("/LeaveCancelHandler")
public class LeaveCancelHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		@SuppressWarnings("unchecked")
		Deque<LeaveRecord> leaveRecords = (Deque<LeaveRecord>) request
				.getSession().getAttribute("leaveRecords");

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

		Deque<Integer> cancelLeaveIds = new LinkedList<Integer>();
		for (LeaveRecord rec : leaveRecords) {
			String action = request.getParameter("action-" + rec.getLeaveId());

			if (action != null && action.equals("cancel")) {
				cancelLeaveIds.add(rec.getLeaveId());
				try {
					LeaveBalance.increaseUnplanned(con, rec.getEmployeeId(),
							rec.getPaidLeavesInDuration());
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
			}
		}

		try {
			LeavesRequest.cancelLeaves(con, cancelLeaveIds);
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
			String serverMsg = "Leaves updated succesfully.";
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
	}

}
