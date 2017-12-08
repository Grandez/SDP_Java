/**
 * Implementa la funcionalidad de busqueda de ficheros 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.common.files;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.config.ConfigurationBase;

public class FileFinder {

	private static Configuration cfg = ConfigurationBase.getInstance();
	
	/**
     * Busca los nombres de los ficheros de acuerdo a sus patrones.
     *
     * @param patterns   Lista de ficheros encontrados
     * @return the array list
     */
    public static ArrayList<Archive> find(String from, String pattern) {
    	return find(from, new String[] {pattern});
    }
    
	public static ArrayList<Archive> find(String from, String[] patterns) {
		ArrayList<Archive> lista = new ArrayList<Archive>();
		for (int idx = 0; idx < patterns.length; idx++) {
			String full = mountFullPath(from, patterns[idx]);
			if (hasWildCard(full)) {
			    for (Path p : findByDir(new Archive(full))) {
			    	if (p.toFile().isFile()) {
			    	    lista.add(new Archive(p.toString()));
			    	}    
			    }
			}
			else {
				lista.add(new Archive(full));
			}
		}
		return lista;
	}
	
	private static String mountFullPath(String dir, String name) {
		// Full path
		if (name.charAt(0) == '/' || name.charAt(0) == '\\') return name;
		if (name.charAt(1) == ':')                           return name;
		if (dir == null || dir.length() == 0)                return name;
		
		if (name.indexOf('/')  != -1) return name;
		if (name.indexOf('\\') != -1) return name;
		
		if (dir.endsWith("/") || dir.endsWith("\\")) return dir + name;
		return dir + File.separatorChar + name;
	}
	
	/**
	 * Busca los ficheros que cumplen el patron.
	 * Si el fichero se ha pasado con directorio, solo busca ahi
	 * Si no tiene directorio se busca primero en CWD y si no hay ninguno
	 * se le inserta el directorio de entrada
	 * 
	 * @param archivo Archivo a analizar
	 * @return Lista de archivos que cumplen el patron
	 */
	private static ArrayList<Path> findByDir(Archive archivo) {
		ArrayList<Path> lista = null;
        boolean done = false;
        
        while (!done) {
	        Path startingDir = Paths.get(archivo.getFullDirName());
        	String pattern = archivo.getFileName();

           Finder finder = new Finder(pattern);
        
           try {
			  Files.walkFileTree(startingDir, finder);
		   } catch (IOException e) {
			  e.printStackTrace();
		   }
           lista = finder.getFiles();
           if (lista.size() > 0 || archivo.hasDir()) {
        	   done = true;
           }
           else {
        	   Archive tmp = new Archive(cfg.getInputDir() + archivo.getFileName());
        	   archivo = tmp;
           }
        }
        return lista;
	}
	
	/**
	 * Indica si el patron tiene caracteres comodin
	 * @param pat El patron a analizar
	 * @return Cierto si tiene comodines
	 */
	private static boolean hasWildCard(String pat) {
		for (int idx = 0; idx < pat.length(); idx++) {
			if (pat.charAt(idx) == '*') return true;
			if (pat.charAt(idx) == '?') return true;
		}
		return false;
	}
}