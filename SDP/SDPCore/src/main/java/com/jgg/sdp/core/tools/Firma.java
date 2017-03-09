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

	/**
     * Calcula una firma digital a partir de losdatos pasados
     *
     * @param input Datos a firmar
     * @return La firma digital en Hexadecimal
     */
	public static String calculate(byte[] input) {
        MessageDigest md = null;
        
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new SDPException(e);
		}

		byte[] digestBin = md.digest(input);
		StringBuffer hexString = new StringBuffer();
    	for (int i=0; i < digestBin.length;i++) {
    	  String hex = Integer.toHexString(0xFF & digestBin[i]);
    	  if (hex.length() == 1) hex = "0" + hex;
    	  hexString.append(hex);
    	}
    	return hexString.toString().toUpperCase();
	}

}
