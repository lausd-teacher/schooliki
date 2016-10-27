package net.videmantay.security;

import java.util.HashMap;
import java.util.Map;


public class AppCurrentUsersTokens {
	
	
	private final static Map<String, String> currentUsers = new HashMap<String, String>();
	
	
	
	public static void registerUserToken(String sessionId, String token){
		 currentUsers.put(sessionId, token);
	}
	
	public static void unregisterUserToken(String sessionId){
		currentUsers.remove(sessionId);
	}
	
	
	public static String getUserToken(String sessionId){
		return currentUsers.get(sessionId);
	}
	
	

}
