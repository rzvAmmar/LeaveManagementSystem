package com.lms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dto.User;
import com.lms.dto.DAL.LoginService;

/**
 * Servlet implementation class LoginHandler
 */
@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String empId = request.getParameter("empId");
		String password = request.getParameter("password");

		if (LoginService.authenticate(empId, password)) {
			System.out.println("User is authentic.");

			User userObj = new User(empId);
			request.getSession().setAttribute("user", userObj);
		}
		response.sendRedirect("home.jsp");
	}

	public static void refreshUser(HttpServletRequest request,
			HttpServletResponse response) {
		
		User user = (User) request.getSession().getAttribute("user");
		request.getSession().removeAttribute("user");
		user = new User(user.getEmpId());
		request.getSession().setAttribute("user", user);
		
	}
}
