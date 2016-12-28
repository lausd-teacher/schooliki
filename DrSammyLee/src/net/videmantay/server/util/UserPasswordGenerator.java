package net.videmantay.server.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class UserPasswordGenerator {
	private static SecureRandom random = new SecureRandom();

	  public static String nextPassword() {
	    return new BigInteger(130, random).toString(32);
	  }
}
