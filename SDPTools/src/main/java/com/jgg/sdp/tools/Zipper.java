/**
 * Implementa la funcionalidad de compresion de ficheros 
 * con el algoritmo ZIP 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.tools;

import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.util.zip.*;

public class Zipper {

	/**
     * Comprime los datos
     *
     * @param name Nombre del fichero comprimido
     * @param rawChars  Datos a comprimir
     * @return Los datos comprimidos
     */
	public static byte[] zip(String name, char[] rawChars)  {
		CharBuffer charBuffer = CharBuffer.wrap(rawChars);
	    ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    ZipOutputStream zipfile = new ZipOutputStream(bos);  
	    ZipEntry zipentry = null;  

	    zipentry = new ZipEntry(name);  
	    try {
	      zipfile.putNextEntry(zipentry);  
	      zipfile.write(byteBuffer.array());  
	      zipfile.close();  
	    }
	    catch (IOException e) {
	       e.printStackTrace();	
	    }
	    return bos.toByteArray();  
	}  

	/**
     * Descomprime un archivo
     *
     * @param name El nombre del archivo
     * @param rawChars Los datos del archivo
     * @return the char[] Los datos descomprimidos 
     */
	public char[] unzip(String name, byte[] rawChars)  {
	    ByteArrayInputStream bis = new ByteArrayInputStream(rawChars);
	    ZipInputStream zipData = new ZipInputStream(bis);  

	    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    
	    byte[] buffer = new byte[8192];
	    int leido = 0;
	    try {
            // Se debe crear el zipEntry antes de leer
	    	zipData.getNextEntry();

	    	while (0 < (leido=zipData.read(buffer)) ) {
 	         bos.write(buffer,0,leido);
 	        }
	        bos.flush();
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    	System.err.println(e.getMessage());
	    }
	    return bos.toString().toCharArray();
	}  

}
