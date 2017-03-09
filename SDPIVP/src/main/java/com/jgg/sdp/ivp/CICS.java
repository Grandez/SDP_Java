package com.jgg.sdp.ivp;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.print.*;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.lang.CICSParser;
import com.jgg.sdp.parser.lang.SQLParser;

public class CICS {

	private Printer printer = new Printer();
	private Configuration cfg = Configuration.getInstance();
	private Module module = null;
	    
	private String prm[][] = { 
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT    , "1" , "204" , Args.DIR}			          
	};
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   CICS launcher = new CICS();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
	    args = cfg.processCommandLine(prm, args);
	    
	    for (Archivo cicsFile : FileFinder.find(cfg.getInputDir(), "*.cics")) {
	    	printer.printAction("Analizando " + cicsFile.getFileName());
        	Source source = loadFileIntoMemory(cicsFile);
            module = ModulesFactory.getModule(source.getFullName());
            module.addSource(source);
            
        	try {
        	   parse(module, PARSER.CICS);
        	

	    	   printer.printRes("OK");
        	}
        	catch (Exception e) {
        		printer.printRes("KO");
        	}
	    }
		return 0;
	}

	private void parse(Module module, int type) throws SDPException, Exception {
		Source source = module.getSource();
       	ILexer lexer = FactoryLexer.getLexer(source, type);
       	lexer.setFullName(source.getFullName());
       	CICSParser parser = new CICSParser((GenericScanner) lexer);
       	parser.parse();
	}

	private Source loadFileIntoMemory(Archivo input) {
		return SourcesFactory.getSource(input);
	}	
 
	private void banner() {
		
		printer.boxBeg();
		printer.boxLine("SERENDIPITY", true);
		printer.boxLine("PROCESO DE VERIFICACION ANALIZADOR CICS", true);
		printer.boxEnd();
		printer.nl();
	}

}
