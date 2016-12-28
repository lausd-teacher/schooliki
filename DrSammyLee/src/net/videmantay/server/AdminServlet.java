package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.user.DB;

public class AdminServlet extends HttpServlet{
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		
		res.setContentType("text/html");
		DB.start();
		res.getWriter().write(ViewsUtils.adminView());
		
		
	}

}
