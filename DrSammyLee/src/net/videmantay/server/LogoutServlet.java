package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.security.AppCurrentUsersTokens;

public class LogoutServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		 //Check token later
		 AppCurrentUsersTokens.unregisterUserToken(req.getSession(false).getId());
	}

}
