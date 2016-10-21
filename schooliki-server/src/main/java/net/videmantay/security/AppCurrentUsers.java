package net.videmantay.security;

import java.util.HashMap;
import java.util.Map;

//Temporary while waiting for JAAS to be implemented
public class AppCurrentUsers {
	
	
	private final static Map<String, String> currentUsers = new HashMap<String, String>();
	
	
	
	public static void registerUser(String sessionId, String token){
		 currentUsers.put(sessionId, token);
	}
	
	public static void unregisterUser(String sessionId){
		currentUsers.remove(sessionId);
	}
	
	
	public static String getUser(String sessionId){
		return currentUsers.get(sessionId);
	}
	
	

}
