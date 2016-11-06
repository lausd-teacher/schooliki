package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.server.util.UserPasswordGenerator;

public class DemoDataServlet extends HttpServlet {
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
		
	/* Servlet for adding demo data, as it becomes tedious addding on each server Restart */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AppUser demoUser = new AppUser();
		demoUser.seteMail("zakariaamine26@gmail.com");
		demoUser.setPassword(UserPasswordGenerator.nextPassword());
		demoUser.setActive(true);
		demoUser.setRoles(new String[]{"TEACHER", "ADMIN"});
		demoUser.setFirstLogin(true);
		
		AppUser demoUser2 = new AppUser();
		demoUser2.seteMail("test1@test1.com");
		demoUser2.setActive(true);
		demoUser2.setImageUrl("https://lh5.googleusercontent.com/-J2k22BcSKq4/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK8wzWVw8lWF-_0MRZmAL0LpSNpiHA/s96-c/photo.jpg");
		demoUser2.setFirstName("tester 1");
		demoUser2.setLastName("student 1");
		demoUser2.setRoles(new String[]{"STUDENT"});
		demoUser2.setFirstLogin(true);
		
		AppUser demoUser3 = new AppUser();
		demoUser3.seteMail("test2@test2.com");
		demoUser3.setActive(true);
		demoUser3.setImageUrl("https://lh5.googleusercontent.com/-J2k22BcSKq4/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK8wzWVw8lWF-_0MRZmAL0LpSNpiHA/s96-c/photo.jpg");
		demoUser3.setFirstName("tester 2");
		demoUser3.setLastName("student 2");
		demoUser3.setRoles(new String[]{"STUDENT"});
		demoUser3.setFirstLogin(true);
		
		AppUser demoUser4 = new AppUser();
		demoUser4.seteMail("test3@test3.com");
		demoUser4.setActive(true);
		demoUser4.setImageUrl("https://lh5.googleusercontent.com/-J2k22BcSKq4/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK8wzWVw8lWF-_0MRZmAL0LpSNpiHA/s96-c/photo.jpg");
		demoUser4.setFirstName("tester 3");
		demoUser4.setLastName("student 3");
		demoUser4.setRoles(new String[]{"STUDENT"});
		demoUser4.setFirstLogin(true);
		
		appUserDB.save(demoUser);
		appUserDB.save(demoUser2);
		appUserDB.save(demoUser3);
		appUserDB.save(demoUser4);
		
		resp.getWriter().write("Demo Data added successfuly");
		
	}

}
