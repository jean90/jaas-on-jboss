package com.jaasonjboss.login;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	private static final String USERNAME = "testinguser";
	private static final String PASSWORD = "123456789";
	
	@EJB
	private StatelessEjbExample ejbExample;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			
			String user = req.getParameter("user") != null?req.getParameter("user"):USERNAME;
			String password = req.getParameter("password") != null?req.getParameter("password"):PASSWORD;
			
			req.login(user, password);
			
			if(req.isUserInRole("CLIENT")) {
				System.out.println("Client user loged in...");
			}
			
			if(req.isUserInRole("ADMIN")) {
				System.out.println("Admin user loged in...");
			}
			
			ejbExample.testLogedInUser();
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			req.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
