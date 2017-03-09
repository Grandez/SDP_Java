/**
 * Esta clase es un singleton que se encarga de mantener
 * la lista de verbos CICS que son threadsafe
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */

package com.jgg.sdp.cics.parser;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;

public class CICSTS {
	
	public final static int UNDEF  = 0;
	public final static int TS     = 1;
	public final static int QR     = 2;
	public final static int MAYBE  = 3;
    public final static int QRONLY = 6;
    
	private BufferedReader in = null;
    private StringBuilder sb = new StringBuilder();
    private HashMap<String, TSVerb> verbs = new HashMap<String, TSVerb>();
    
    private static CICSTS cicsTs = null;

    private CICSTS() {
    	loadData();
    }
        
    public static CICSTS getInstance() {
    	if (cicsTs == null) cicsTs = new CICSTS();
    	return cicsTs;
    }
    
    /**
     * Chequea si el verbo es QR o NO
     * @param fullVerb
     * @return
     */
    public int checkTS(CICSStmt stmt) {
    	String parametro = null;
    	int option  = CICSTS.UNDEF;
    	
    	String[] words = stmt.getFullVerbs().split("_");
    	TSVerb verb = verbs.get(words[0].toUpperCase());
    	
    	// No existe, es QR
    	if (verb == null) return setQR(stmt, words, 0);
    	
    	int nWord = 1;
    	
    	while (verb.getType() == CICSTS.UNDEF) {
    	    if (nWord == words.length) return setQR(stmt, words, nWord - 1); 	
    		verb = verb.get(words[nWord++]);
    		if (verb == null) return setQR(stmt, words, nWord - 1);
    	}
    	
    	option = verb.getType(); 
    	
    	for (String opt : verb.getOptionsQR()) {
    		if (stmt.hasOption(opt)) {
    			option = CICSTS.QR;
    			parametro = opt;
    		}
    	}

    	for (String opt : verb.getOptionsQRBloqueante()) {
    		if (stmt.hasOption(opt)) {
    			option = CICSTS.QRONLY;
    			parametro = opt;
    		}
    	}
    	
    	StringBuilder full = new StringBuilder(words[0].toUpperCase());
    	for (int idx = 1; idx < nWord; idx++) {
    		full.append(" " + words[idx].toUpperCase());
    	}
    	
    	if (parametro != null) {
    		full.append(" ");
    		full.append(parametro);
    	}
    	
    	stmt.setFullVerb(full.toString());
    	stmt.setQRType(option);
    	return option;
    }
    
    private void loadData() {
    	in = getResource(CFG.CFG_CICS_TS);
    	if (in == null) {
    		throw new SDPException(MSG.EXCEPTION_CONFIG, CFG.CFG_CICS_TS);
    	}
        loadKeys();
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

    private void loadKeys() {
        String linea;
        while ((linea = getLine()) != null) {
            loadClave(linea.toUpperCase());
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
    
    /**
     * El formato es: 
     *    Verb;type[:[options_making_qr]*:[optios_making_6]*
     *    
     * @param linea
     */
    private void loadClave(String linea) {
    	String[] data = linea.split(":");
    	String[] opts = null;
    	
    	TSVerb verb = loadVerb(data[0]);
    	verbs.put(verb.getName(), verb);
    	if (data.length == 1) return;
    	
    	// Carga las opciones que lo hacen QR
    	opts = data[1].split(";");
    	for (int idx = 0; idx < opts.length; idx++) {
    		verb.addOption(opts[idx]);
    	}
    	if (data.length == 2) return;
    	
    	// Carga las opciones que lo hacen QR Bloqueante
    	opts = data[2].split(";");
    	for (int idx = 0; idx < opts.length; idx++) {
    		verb.addQR(opts[idx]);
    	}
    	
    }
    
    private TSVerb loadVerb(String verbo) {
    	int tipo = 1;
    	String[] par = verbo.split(";");
    	String[] words = null;
    	 
        if (par.length == 2) tipo = Integer.parseInt(par[1]);
        
        words = par[0].trim().split("_");
        
        TSVerb verb = verbs.get(words[0]);
        if (verb == null) verb = new TSVerb(words[0]);
        
        for (int i = 1; i < words.length; i++) {
        	verb.add(words[i]);
        }
        verb.setType(tipo);
        verb.reset();  // Necesario para corregir el puntero
        return verb;
    }


    private int setQR(CICSStmt stmt, String[] words, int count) {
    	StringBuilder full = new StringBuilder(words[0].toUpperCase());
    	for (int idx = 1; idx < count; idx++) {
    		full.append(" " + words[idx].toUpperCase());
    	}
    	stmt.setFullVerb(full.toString());
    	stmt.setQRType(CICSTS.QR);
    	return CICSTS.QR;
    }

}
