package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.security.AppCurrentUsers;

public class LogoutServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		 String token = req.getParameter("token");
		 //Check token later
		 AppCurrentUsers.unregisterUser(req.getSession(false).getId());
		 req.getSession(false).invalidate();
		 res.sendRedirect("/login");
		 
		
	}

}
