/**
 * Implementa lafuncionalidad de generacion de firmas digitales 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jgg.sdp.core.exceptions.SDPException;

public class Firma {

	public static String calculate(String input) {
		return calculate(input.getBytes(), false);
	}
	
	public static String calculate(byte[] input) {
		return calculate(input, false);
	}
	
	public static String calculate(byte[] input, boolean removeSpaces) {
        MessageDigest md = null;
        
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new SDPException(e);
		}

		byte[] digestBin = md.digest(getBytes(input,removeSpaces));
		StringBuffer hexString = new StringBuffer();
    	for (int i=0; i < digestBin.length;i++) {
    	  String hex = Integer.toHexString(0xFF & digestBin[i]);
    	  if (hex.length() == 1) hex = "0" + hex;
    	  hexString.append(hex);
    	}
    	return hexString.toString().toUpperCase();
	}

	public static String createDefault() {
		String f0 = "00000000000000000000000000000000";
		String f1 = "00000000000000000000000000000000";
		return f0 + f1;
	}
	
	private static byte[] getBytes(byte[] input, boolean removeSpaces) {
		if (!removeSpaces) return input;
		
		int to = 0;
		
		char[] chars = (new String(input)).toCharArray();
		char[] tmp   = new char[chars.length + 1];
		
		for (int from = 0; from < chars.length; from++) {
			if (chars[from] != ' ' && chars[from] != '\t' && chars[from] != '\r' && chars[from] != '\n' ) {
				tmp[to++] = chars[from];
			}
		}
		char[] out = new char[to];
		System.arraycopy( tmp, 0, out, 0, to - 1);
		return (new String(out)).getBytes();
	}
}
