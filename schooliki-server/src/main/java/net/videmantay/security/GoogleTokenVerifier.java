package net.videmantay.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GoogleTokenVerifier {
	
	private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();

    // Verifier that checks that the token has the proper issuer and audience
    //this is being changed to personlized client id per server basis
    private static GoogleIdTokenVerifier verifier =
            new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setIssuer("accounts.google.com")
                    .setAudience(Arrays.asList(ClientToken.getToken()))
            .build();
	
	public static boolean verifyToken(String token) throws GeneralSecurityException, IOException{
		
		GoogleIdToken idToken = verifier.verify(token);
		if (idToken != null) {
		     return true;

		} 
		return false;
	}

}
