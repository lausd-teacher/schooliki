package net.videmantay.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ViewsUtils {
	
	
	private static final String TEACHER_VIEW = "teacher.html";
	private static final String ADMIN_VIEW = "admin.html";
	
	private static final File TEACHER_HTML_FILE = new File(TEACHER_VIEW);
	private static final File ADMIN_HTML_FILE = new File(ADMIN_VIEW);
	
	public static String adminView(){
		String adminView = "<h1> error rendering the admin view on the server </h1>";
			try {
				adminView = FileUtils.readFileToString(ADMIN_HTML_FILE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return adminView;
	}
	
	public static String teacherView(){
		String teacherView = "<h1> error rendering the teacher view on the server </h1>";
		try {
			teacherView = FileUtils.readFileToString(TEACHER_HTML_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	return teacherView;
	}
	
	
	public static String loginView(boolean isloggedIn, String currentUserName){
		String loginView = "<h1> error rendering the login view on the server </h1>";
		try {
			if(isloggedIn){
				loginView = TemplateGen.getInstance().getLoggedInLoginView(currentUserName);
			}else{
				
				loginView = TemplateGen.getInstance().getLoggedOutLoginView();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return loginView;
	}
	
	
	public static String loginViewWithErrors(String errorMessage){
		String loginView = "<h1> error rendering the login view on the server </h1>";
		try {
				loginView = TemplateGen.getInstance().getLoginViewWithError(errorMessage);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	return loginView;
	}
	

}
