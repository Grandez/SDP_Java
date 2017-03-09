/**
 * Utilidad para extraer las sentencias SQL
 *    
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.sql;

import java.io.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.sql.lang.*;

public class SDPSQL {

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
		    {" ", "i" , "input"   , "SDP_INPUT"        , CFG.DIR_INPUT      , "1" , "204" , Args.DIR}			          
	       ,{"" , "d" , "doc"     , "SDP_DIR_DOC"      , CFG.DIR_DOCS       , "1" , "217" , Args.DIR}	   
		   ,{"1", "t" , "isolated", ""                 , CFG.OUTPUT_ISOLATED, "0" , "218" , Args.BOOLEAN}		   
		   ,{"1", ""  , "include" , ""                 , CFG.SQL_INCLUDE    , "0" , "219" , Args.BOOLEAN}		          
	};

	Module  module   = null;
	
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   SDPSQL launcher = new SDPSQL();
	   rc = launcher.start(args);
       System.exit(rc);
	}

    private int start(String[] args) {
    	SDPUnit unit     = null;
		int     maxRC    = RC.NO_DATA;
		int     procesar = MSG.OK;
		String  moduleName;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_EXTRACTOR);
		
        args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) args = def;

        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
        	moduleName = archivo.getBaseName();
        	if (cfg.getVerbose() > 1) msg.progressCont(MSG.PARSING, moduleName);
        	unit = new SDPUnit(archivo);
			try {
                analyze(unit, moduleName);
                maxRC = MSG.OK;
                if (cfg.getVerbose() > 1)    msg.progress(procesar);
			} catch (SDPException s) {
				if (cfg.getVerbose() > 0) System.out.println(msg.getMsg(MSG.KO));
			    if (maxRC < RC.WARNING) maxRC = RC.WARNING;
			} catch (Exception e) {
				if (cfg.getVerbose() > 0) System.out.println(msg.getMsg(MSG.KO));
                msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
                e.printStackTrace();
                if (maxRC < RC.ERROR) maxRC = RC.ERROR;
			}
        }
        return maxRC;
    }

	private void analyze(SDPUnit unit, String moduleName) throws SDPException, Exception {
		SQLExtract lexer = new SQLExtract(unit.getCurrentSource());
		lexer.setModuleName(moduleName);
		lexer.cleanFileSystem();
		lexer.yylex();
	}

}
