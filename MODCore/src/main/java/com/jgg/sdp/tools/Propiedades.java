/**
 * Implementa la gestion de los ficheros de configuracion 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.tools;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class Propiedades {

    private BufferedReader in = null;
    private StringBuilder sb = new StringBuilder();
    private HashMap<String, String> parms = new HashMap<String, String>();

    private char sep = '=';
    
    public HashMap<String, String> loadResource(String resourceName) {
    	return loadResource(resourceName, '=');
    }
    
    public HashMap<String, String> loadResource(String resourceName, char sep) {
    	this.sep = sep;
        in = getResource(resourceName);
        if (in != null) loadKeys();
        return parms;
    }

    public HashMap<String, String> loadConfigFile(String resourceName) {
        in = getFile(resourceName);
        if (in != null) loadKeys();
        return parms;
    }
    
    private BufferedReader getResource(String resourceName) {
       String resName = null;
       
       ClassLoader cl = ClassLoader.getSystemClassLoader();
       if (cl == null) return null;
       
       if (resourceName.indexOf('.') == -1) {
           resName = resourceName + ".properties";
       }
       else {
           resName = resourceName;
       }
       
       URL url = cl.getResource(resName);
       if (url == null) url = cl.getResource("/" + resName);
       if (url == null) return null;
       
       try {
           in = new BufferedReader(new InputStreamReader(url.openStream()));
       } catch (IOException e) {
           return null;
       }
       return in;
    }
    
    
    private BufferedReader getFile(String resourceName) {
        // Ya se ha verificado que el fichero existe
        try {
            in = new BufferedReader(new FileReader(new File(resourceName)));
        } catch (FileNotFoundException e1) {
            in = null;
        }
        return in;
    } 

    private void loadKeys() {
        String linea;
        while ((linea = getLine()) != null) {
            loadClave(linea);
        }
    }
    
    private String getLine() {
        int    pos;
        String linea = null;
        boolean done = false;
        
        while (!done) {
           try {
             linea = in.readLine();
           } catch (IOException e) {
             return null;
           }
           if (linea == null) return null;
           sb.setLength(0);
           sb.append(linea);
           pos = sb.indexOf("#");
           if (pos != -1) sb.setLength(pos);
           linea = sb.toString().trim();
           if (linea.length() > 0) done = true;
        }
        return linea;
    }
    
    private void loadClave(String linea) {
        String[] par = linea.split(Character.toString(sep));
        StringBuilder sb = new StringBuilder();
        if (par.length == 1) {
        	sb.append(par[0]);
        	sb.append(sep);
        }
        else {
        	for (int idx = 1; idx < par.length; idx++) {
            	sb.append(par[idx]);
            	sb.append(sep);
        	}
        }
        sb.deleteCharAt(sb.length() - 1);
        String valor = sb.toString().trim();
        if (valor.charAt(0) == '\"' || valor.charAt(0) == '\'') {
            valor = valor.substring(1, valor.length() - 1);
        }
        parms.put(par[0].trim(), valor);
    }
}
