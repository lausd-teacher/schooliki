package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.videmantay.server.entity.IncidentType;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.server.util.UserPasswordGenerator;

public class DemoDataServlet extends HttpServlet {
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
	
	DB<IncidentType> incidentTypeDB = new DB<IncidentType>(IncidentType.class);
		
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
		demoUser2.setFirstName("dummy");
		demoUser2.setLastName("student");
		demoUser2.setRoles(new String[]{"STUDENT"});
		demoUser2.setFirstLogin(true);
		
		AppUser demoUser3 = new AppUser();
		demoUser3.seteMail("test2@test2.com");
		demoUser3.setActive(true);
		demoUser3.setImageUrl("https://lh5.googleusercontent.com/-J2k22BcSKq4/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK8wzWVw8lWF-_0MRZmAL0LpSNpiHA/s96-c/photo.jpg");
		demoUser3.setFirstName("john");
		demoUser3.setLastName("doe");
		demoUser3.setRoles(new String[]{"STUDENT"});
		demoUser3.setFirstLogin(true);
		
		AppUser demoUser4 = new AppUser();
		demoUser4.seteMail("test3@test3.com");
		demoUser4.setActive(true);
		demoUser4.setImageUrl("https://lh5.googleusercontent.com/-J2k22BcSKq4/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK8wzWVw8lWF-_0MRZmAL0LpSNpiHA/s96-c/photo.jpg");
		demoUser4.setFirstName("Foo");
		demoUser4.setLastName("bar");
		demoUser4.setRoles(new String[]{"STUDENT"});
		demoUser4.setFirstLogin(true);
		
		IncidentType type = new IncidentType();
		type.setImageUrl("img/1.png");
		type.setName("Helping others");
		type.setPoints(2);
		
		IncidentType type2 = new IncidentType();
		type2.setImageUrl("img/32.png");
		type2.setName("On task");
		type2.setPoints(1);
		
		IncidentType type3 = new IncidentType();
		type3.setImageUrl("img/5.png");
		type3.setName("Participating");
		type3.setPoints(3);
		
		IncidentType type4 = new IncidentType();
		type4.setImageUrl("img/6.png");
		type4.setName("Persistence");
		type4.setPoints(1);
		
		IncidentType type5 = new IncidentType();
		type5.setImageUrl("img/7.png");
		type5.setName("Teamwork");
		type5.setPoints(2);
		
		IncidentType type6 = new IncidentType();
		type6.setImageUrl("img/9.png");
		type6.setName("Test");
		type6.setPoints(3);
		
		IncidentType type7 = new IncidentType();
		type7.setImageUrl("img/10.png");
		type7.setName("Working hard");
		type7.setPoints(2);
		
		appUserDB.save(demoUser);
		appUserDB.save(demoUser2);
		appUserDB.save(demoUser3);
		appUserDB.save(demoUser4);
		
		incidentTypeDB.save(type);
		incidentTypeDB.save(type2);
		incidentTypeDB.save(type3);
		incidentTypeDB.save(type4);
		incidentTypeDB.save(type5);
		incidentTypeDB.save(type6);
		incidentTypeDB.save(type7);
		
		resp.getWriter().write("Demo Data added successfuly");
		
	}

}
