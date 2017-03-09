/**
 * 
 * Indica si un modulo o conjunto de modulos es es TS o no
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.sql.base;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.parser.sql.*;

public class SQLParse {

    
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
	       ,{"1", "" , "force"   , ""                 , CFG.PARSER_FORCE , "0" , "214;215" , Args.BOOLEAN}	       
	};

	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   SQLParse launcher = new SQLParse();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		int     maxRC   = RC.OK;
		int     procesar = MSG.OK;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_LITE, MSG.USE_SDP_LITE);
		
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) args = def;

        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
        	if (cfg.getVerbose() > 1) msg.progressCont(MSG.PARSING, archivo.getBaseName());
            
			try {	
 				if (procesar == MSG.OK)      analyze(archivo);
				if (cfg.getVerbose() > 1)    msg.progress(procesar);

			} catch (Exception e) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
                e.printStackTrace();
                maxRC = RC.ERROR;
                break;
			}
        }
        
        return maxRC;
	}

	
	private void analyze(Archivo archivo) throws SDPException, Exception {
		String d = archivo.getFullPath();
		SQLParser p = new SQLParser(new SQLLexer(new java.io.FileReader(d)));
		p.parse();
	}
	

}
