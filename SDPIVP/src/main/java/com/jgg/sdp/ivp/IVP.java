package com.jgg.sdp.ivp;

import java.util.ArrayList;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.ivp.data.Case;
import com.jgg.sdp.ivp.data.Data;
import com.jgg.sdp.parser.Parser;
import com.jgg.sdp.print.Printer;


public class IVP {

	private Printer printer = new Printer();
	private Data    data    = new Data();
	
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();

    private int countErrors = 0;
    private int countModulos = 0;

    private Module module = null;
    
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
		   ,{" ", "o" , "output" , "SDP_OUTPUT"       , CFG.DIR_OUTPUT   , "1" , "205" , Args.DIR}
	       ,{" ", "n" , "name"   , ""                 , CFG.TEMP_NAME    , "1" , "211" , Args.STRING}
		   ,{" ", "l" , "left"   , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT  , "1" , "206" , Args.NUMBER}	   
		   ,{" ", "r" , "right"  , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT , "1" , "207" , Args.NUMBER}
		   ,{" ", "q" , "qname"  , "SDP_QUEUE"        , CFG.JMS_QUEUE    , "1" , "208" , Args.STRING}
		   ,{"1", " " , "local"  , ""                 , CFG.PARSER_LOCAL , "0" , "213" , Args.STRING}		   
	       ,{"1", "e" , "error"  , ""                 , CFG.PARSER_ERR   , "0" , "212" , Args.BOOLEAN}
	};
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   IVP launcher = new IVP();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
	    int maxRC = RC.OK;
		banner();
		data.load("P:\\SDP\\IVP\\sdpivp.dat");
		process();
        return maxRC;
	}
	
	private void process() {
		processNoGroup();
//		processVariables();
	}
	
	private void processNoGroup() {
		printer.box("Verificando casos no identificados");
		for (Case c : data.getGrupo(0)) process(c);
	}
	
	private void processVariables() {
		printer.box("Verificando variables");
		ArrayList<Case> cases = data.getGrupo(1);
		for (Case c : cases) {
			printer.lineBeg(c.getName());
			printer.lineEnd("OK");
		}
		
	}

	private void process(Case c) {
		printer.lineBeg(c.getName());
		Parser p = new Parser();
		try {
//JGG		   int rc = p.IVP(new String[] {"--local", c.getName() + ".cbl"});
//JGG		   checkCase(c, rc, p.IVPGetModule());

		}
		catch (Exception e) {
           printer.lineEnd("KO - Exception: " + e.getMessage());
		}
		
	}
	
	private void checkCase(Case c, int rc, Module module) {
		if (rc != 0) {
			printer.lineEnd("KO - Invalid Return Code: " + rc);
			return;
		}
		if (c.getNewHash().compareTo(module.getNewHash()) != 0) {
			printer.lineEnd("KO - Invalid Source code");
			return;			
		}
		if (c.getXMLHash().compareTo(module.getXMLHash()) != 0) {
			printer.lineEnd("KO - Invalid analysis");
			return;			
		}
		printer.lineEnd("OK");
	}
	
	private void banner() {
		
		printer.boxBeg();
		printer.boxLine("SERENDIPITY", true);
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxEnd();
		printer.nl();
	}
	
}
