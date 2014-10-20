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
@WebServlet("/LeaveStatusHandler")
public class LeaveStatusHandler extends HttpServlet {
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

		Deque<Integer> approvedLeaveIds = new LinkedList<Integer>();
		Deque<Integer> rejectedLeaveIds = new LinkedList<Integer>();

		Connection con = MySQL.connect();
		try {
			con.setAutoCommit(false);
		} catch(SQLException e) {
			MySQL.close(con);
			String serverMsg = "Error! Unable to connect DB.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
			dispatcher.forward(request, response);
		}
		
		for (LeaveRecord rec : leaveRecords) {
			String action = request.getParameter("action-" + rec.getLeaveId());

			if (action.equals("approve")) {
				approvedLeaveIds.add(rec.getLeaveId());
				try {
					LeaveBalance.decreasePlanned(con, rec.getEmployeeId(),
							rec.getPaidLeavesInDuration());
				} catch (SQLException e) {
					e.printStackTrace();
					try {
						con.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					MySQL.close(con);
					String serverMsg = "Error! Leaves NOT updated.";
					request.setAttribute("serverMsg", serverMsg);
					RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
					dispatcher.forward(request, response);
				}

			} else if (action.equals("reject")) {
				rejectedLeaveIds.add(rec.getLeaveId());
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
					MySQL.close(con);
					String serverMsg = "Error! Leaves NOT updated.";
					request.setAttribute("serverMsg", serverMsg);
					RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
					dispatcher.forward(request, response);
				}
			}
		}

		try {
			LeavesRequest.updateLeavesStatus(con, approvedLeaveIds,
					rejectedLeaveIds);
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			MySQL.close(con);
			String serverMsg = "Error! Leaves NOT updated.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
			dispatcher.forward(request, response);
		}
		
		try {
			con.commit();
			MySQL.close(con);
			LoginHandler.refreshUser(request, response);
			String serverMsg = "Leaves updated succesfully.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			MySQL.close(con);
			String serverMsg = "Error! Leaves NOT updated.";
			request.setAttribute("serverMsg", serverMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("redirect.jsp");
			dispatcher.forward(request, response);			
		}
	}
}

