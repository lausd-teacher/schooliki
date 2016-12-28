package net.videmantay.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.user.DB;

public class TeacherServlet extends HttpServlet {
	
	private final Logger log = Logger.getLogger("logger");
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		//Could use freemarker as well, but it's not needed for now
		//Not protected yet
		res.setContentType("text/html");
		log.log(Level.INFO, "redenring teacher");
		DB.start();
		res.getWriter().write(ViewsUtils.teacherView());
		
	}

}
