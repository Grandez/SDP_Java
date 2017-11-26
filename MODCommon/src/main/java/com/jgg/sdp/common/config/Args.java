/**
 * Gestiona la configuracion del parser
 * La configuracion se establece de acuerdo con la siguiente secuencia:
 * 
 *   1.- El fichero de configuracion sdp.properties
 *   2.- Las posibles variables de entorno
 *   3.- Los parametros pasados por linea de comandos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.common.config;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.common.exceptions.ParameterException;

public class Args {

	/* Tabla de parametros:
	 * 0 - valor simbolico
	 * 1 - parametro corto
	 * 2 - parametro largo
	 * 3 - variable de entorno
	 * 4 - clave de configuracion
	 * 5 - Requiere valor (0 - no)
	 * 6 - tipo de dato
	 */
	public final static String STRING   = "0";
	public final static String NUMBER   = "1";
	public final static String DIR      = "2";
	public final static String FILE     = "3";
	public final static String RESOURCE = "4";	
	public final static String BOOLEAN  = "5";
	public final static String FLAG     = "6";
	
	// Lista de argumentos soportados
	ArrayList<String[]> prms = new ArrayList<String[]>();
	
	// Parametros analizados
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public Args() {
		loadDefaults();
	}
	
	public Args(String[][] parms) {
       for (int idx = 0; idx < parms.length; idx++) {
    	   prms.add(parms[idx]);
       }
       loadDefaults();
    }
	
	public HashMap<String, String> getArgs() {
		return params;
	}


	// Devuelve la lista de parametros soportados
	public ArrayList<String[]> getParameters() {
		return prms;
	}

	
	public String[] parseArgs(String[] args) {
        ArrayList<String> resto = new ArrayList<String>();
        
		loadFromEnvironment();
		
		for (int idx = 0; idx < args.length; idx++) {
	        if (args[idx].startsWith("--")) {
	        	idx = parseParm(args, idx, 1);		
	        }
	        else if (args[idx].startsWith("-")) {
	        	idx = parseParm(args, idx, 0);
	        }
	        else {
	        	resto.add(args[idx]);
	        }
		}
		return resto.toArray(new String[resto.size()]);
	}

	/**
	 * Carga las opciones comunes soportadas en la linea de comandos
	 */
	private void loadDefaults() {
		String def[][] = { 
		         {""   , "IVP"    , ""           , CFG.IVP_MODE   , BOOLEAN  , "000" }				
		        ,{"v"  , "verbose", ""           , CFG.VERBOSE    , "X1"     , "209" }	   
		        ,{"V"  , "VERBOSE", ""           , CFG.VERBOSE    , "X2"     , "210" }	
	            ,{"h"  , "help"   , ""           , CFG.HELP       , BOOLEAN  , "222" }		        
				,{ ""  , "config" , "SDP_CONFIG" , CFG.DIR_CONFIG , DIR      , "216" }
		        ,{ ""  , "def"    , ""           , CFG.DEF        , BOOLEAN  , "223" }
		      };
		   
	       for (int idx = 0; idx < def.length; idx++) {
	    	   prms.add(def[idx]);
	       }
		}
	
	private int parseParm(String[] args, int idx, int len)  {
		String p = args[idx].substring(len + 1);
		
		int pos = searchParm(p, len);
		
		String[] prm = prms.get(pos);

		if (prm[4] == Args.BOOLEAN) {
			params.put(prm[3], "1");
		} else if (prm[4].charAt(0) == 'X') {
			params.put(prm[3], prm[4].substring(1));
		} else if (prm[4].charAt(0) < '0' ||  prm[4].charAt(0) > '9') {
			params.put(prm[3], prm[4]);
		} else {
			return processParm(args, idx, pos);
		}
		return idx;
	}
	
	private int searchParm(String p, int col) {
		int idx = 0;
		while (idx < prms.size()) {
			if (p.compareTo(prms.get(idx)[col]) == 0) return idx;
			idx++;
		}
		throw new ParameterException(MSG.PARM_BAD, p);
	}
	
	private int processParm(String[] args, int idx, int pos)   {
		int group    = 0;
		
		int next = idx + 1;
		String[] prm = prms.get(pos);
		
		if (args.length == next) {
			throw new ParameterException(MSG.PARM_VALUE_MISSING, args[idx]);
		}
		
		if (args[next].startsWith("-")) {	
			throw new ParameterException(MSG.PARM_VALUE_MISSING, args[idx]);
		}
		
		// Valor por defecto
		if (prm.length > 5 && prm[6] == "1") {
			if (params.get(prm[3]) == null) {
				params.put(prm[3],  prm[4]);
			}
		}

		// Version vieja
		if (prm.length < 8) {
			if (prm[4] != Args.STRING) {
				validateParm(args[next], prm[4]);
			}
			params.put(prm[4], args[next]);
			return next;
		}
		
		group = getGroup(prm[7]);

		if (group != 0) {
			if (params.get(prm[3]) != null) {
				throw new ParameterException(MSG.PARM_DEFINED, prm[1]);
			}
		}
		
		try {
			Integer.parseInt(prm[4]);
			if (prm[4] != Args.STRING) {
				validateParm(args[next], prm[4]);
			}
			params.put(prm[4], args[next]);
		}
		catch (NumberFormatException e) {
			if (prm[4].charAt(0) == 'X') {
				params.put(prm[3], prm[4].substring(1));
			}
			else {
				params.put(prm[3], prm[4]);
			}
		}
		return next;
	}
	
	private int getGroup(String data) {
		if (data.length() > 1) return Integer.parseInt(data.substring(0, 2));
        return 0;
	}
	
	/*
	 * Recorre la tabla de parametros
	 * Busca si existe la variable en el entorno
	 * Si existe reemplaza el valor existente
	 * Esto se debe ejecutar antes de procesar el parametro de la linea de comandos
	 */
	
	private void loadFromEnvironment() {
		String var;
		for (int idx = 0; idx < prms.size(); idx++) {
			var = System.getenv(prms.get(idx)[2]);
			if (var != null) params.put(prms.get(idx)[3],var);
		}
	}

	private void validateParm(String val, String type) {
		if (type == NUMBER)   validateNumber(val);
		if (type == DIR)      validateDir(val);
	    if (type == FILE)     validateFile(val);
	    if (type == RESOURCE) validateResource(val);	    
	}
	
	private void validateNumber(String val) {
		try {
			Integer.parseInt(val);
		}
		catch (Exception e) {
			throw new ParameterException(MSG.PARM_BAD_NUMBER, val);			
		}
	}

	private void validateDir(String val) {
		try {
			Path path = Paths.get(val);
			if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
				throw new ParameterException(MSG.PARM_BAD_DIR, val);
			}
		}
		catch (Exception e) {
			throw new ParameterException(MSG.PARM_BAD_DIR, val);
		}
	}

    private void validateFile(String val) {
        try {
            Path path = Paths.get(val);
            if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                throw new ParameterException(MSG.PARM_BAD_FILE, val);
            }
        }
        catch (Exception e) {
            throw new ParameterException(MSG.PARM_BAD_FILE, val);
        }
    }

    private void validateResource(String val) {
       ClassLoader cl = ClassLoader.getSystemClassLoader();
       if (cl == null) return;
       URL url = cl.getResource(val);
       if (url == null) {            
    	   throw new ParameterException(MSG.PARM_BAD_RESOURCE, val);
        }
    }
	
}
