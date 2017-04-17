/**
 * 
 * Programa de test para analisis SQL/DB2
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser;

import java.io.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.base.GenericLexer;

import java_cup.runtime.Scanner;

public class DB2ParserMain {

    
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();
	
  	/* Tabla de parametros aceptados
	 * 0 - valor simbolico
	 * 1 - parametro corto
	 * 2 - parametro largo
	 * 3 - variable de entorno
	 * 4 - clave de configuracion
	 * 5 - Requiere valor (0 - no)
	 * 6 - tipo de dato
	 */

	String prm[][] = { 
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT    , "1" , "204" , Args.DIR}			          
		   ,{" ", "l" , "left"   , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT  , "1" , "206" , Args.NUMBER}	   
		   ,{" ", "r" , "right"  , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT , "1" , "207" , Args.NUMBER}
		   ,{" ", "f" , "file"   , "SDP_INPUT_FILE"   , CFG.FILE_INPUT   , "1" , "207" , Args.STRING}
	       ,{"1", "" , "force"   , ""                 , CFG.PARSER_FORCE , "0" , "214;215" , Args.BOOLEAN}	       
	};

	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   DB2ParserMain launcher = new DB2ParserMain();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		int     maxRC   = RC.OK;
		int     rc = RC.OK;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_LITE, MSG.USE_SDP_LITE);
		
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) args = def;

		cfg.setParameter(CFG.MARGIN_LEFT,  "0");
		
		maxRC = processFile();
		rc = processCommandLine(args);
		if (rc > maxRC) maxRC = rc;
        return maxRC;
	}

	private int processFile() {
		int rc = MSG.OK;
		int maxRC = MSG.OK;
		
		String file = cfg.getString(CFG.FILE_INPUT);
		if (file == null) return 0;
		String dir = cfg.getInputDir();
		String sqlName;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((sqlName = br.readLine()) != null) {
				Archivo archivo = new Archivo(dir + sqlName.trim());
				rc = procesa(archivo);
				if (rc > maxRC) maxRC = rc;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return maxRC;
	}
	
	private int processCommandLine(String[] args) {
		int rc;
		int maxRC = 0;
        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
        	rc = procesa(archivo);
        	if (rc > maxRC) maxRC = rc;
        }
        return maxRC;
	}
	
	private int procesa(Archivo archivo) {
		int procesar = MSG.OK;
    	if (cfg.getVerbose() > 1) msg.progressCont(MSG.PARSING, archivo.getBaseName());
        
		try {	
			if (procesar == MSG.OK)      analyze(archivo);
			if (cfg.getVerbose() > 1)    msg.progress(procesar);

		} catch (Exception e) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
            e.printStackTrace();
            procesar = RC.ERROR;
            System.exit(16);
		}
		return procesar;
	}
	
	private void analyze(Archivo archivo) throws SDPException, Exception {
		String d = archivo.getFullPath();
 		GenericLexer lexer = DB2Lexer.getLexer(new Source(new Archivo(d), null));
		DB2Parser parser = new DB2Parser((Scanner) lexer);
		parser.parse();
	}
	

}
