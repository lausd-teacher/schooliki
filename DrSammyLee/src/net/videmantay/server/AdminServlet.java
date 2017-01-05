package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminServlet extends HttpServlet{

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		TemplateGen template = (TemplateGen) this.getServletContext().getAttribute("template");
		res.setContentType("text/html");
		res.getWriter().write(template.getAdminPage());
		
		
	}

}
