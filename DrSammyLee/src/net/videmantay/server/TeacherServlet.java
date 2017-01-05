package net.videmantay.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.videmantay.server.entity.AppUser;

import static net.videmantay.server.util.DB.db;

public class TeacherServlet extends HttpServlet { 
	private final Logger log = Logger.getLogger("logger");
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		User user = UserServiceFactory.getUserService().getCurrentUser();
		AppUser appUser = db().load().type(AppUser.class).filter("email", user.getEmail()).first().now();
		LoginInfo info = new LoginInfo();
		info.firstName = appUser.getFirstName();
		info.lastName  = appUser.getLastName();
		info.img = appUser.getImageUrl();
		info.email = appUser.getEmail();
		Credential cred = GoogleUtils.cred(user.getUserId());
		info.token = cred.getAccessToken();
		
		//make info a json sting
		ObjectMapper mapper = new ObjectMapper();
		String infoJson = mapper.writeValueAsString(info);
		log.info("loginfo is " + infoJson);
		
		TemplateGen template = (TemplateGen) this.getServletContext().getAttribute("template");
		//Could use freemarker as well, but it's not needed for now
		//Not protected yet
		res.setContentType("text/html");
		log.log(Level.INFO, "redenring teacher");
		//populate the root with user profile
		Map<String, Object> root = new HashMap<>();
		root.put("info", infoJson);
		Template teacherPage = template.getTeacherPage();
		
			try {
				teacherPage.process(root, res.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
