/**
 * Modulo principal de ejecucion del analizador
 * Se ejecuta con la instrucción main
 * 
 * Analiza el codigo
 * Genera un nuevo modulo
 * Envia la informacion del analisis al servidor
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser;

import java.io.*;
import java.util.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.Messages;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.post.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.module.unit.Source;

public class Controller {

	private Properties IVPData = null;
	
    private Module module = null;

    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();

    private int countErrors = 0;
    private int countModulos = 0;
    
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
		   ,{"1", " " , "local"  , ""                 , CFG.PARSER_LOCAL , "0" , "213" , Args.BOOLEAN}		   
	       ,{"1", "e" , "error"  , ""                 , CFG.PARSER_ERR   , "0" , "212" , Args.BOOLEAN}
	       ,{"1", "t" , "test"   , ""                 , CFG.IVP_TEST     , "0" , "205" , Args.BOOLEAN}	       
	       ,{" ", "g" , "group"  , ""                 , CFG.IVP_GROUP    , "1" , "205" , Args.NUMBER}
	       ,{"1", "p" , "parse"  , ""                 , CFG.PARSER_PARSE , "0" , "205" , Args.BOOLEAN}
	};
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Controller launcher = new Controller();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	public int    IVP(String[] args) throws Exception { return start(args);	}
	public Module IVPGetModule()                      {	return module;      }
	
	private int start(String[] args) {
	    int maxRC = RC.OK;
        try {
            mainProcess(args);
        }
        catch (SDPException e) {
             msg.exception(e);
             return RC.FATAL;
        }
        if (countErrors > 0) {
            msg.warning(MSG.PARSE_ERRORS, countErrors);
            maxRC = RC.WARNING;
        }
        msg.trace(1, MSG.PARSED, countModulos);
        return maxRC;
	}
	
	private int mainProcess(String[] args) {
		cfg.setTitles(MSG.TITLE_SDP_PARSER);
		/*
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) {
		    msg.warning(MSG.NO_DATA);
		    return RC.NO_DATA;
		}

		if (cfg.getBoolean(CFG.PARSER_ERR)) {
		    return sendBadFile(args, null);
		}
		
        for (Archivo pgm : FileFinder.find(cfg.getInputDir(), args)) {
            countModulos++;
        	msg.progress(2, MSG.PARSING, pgm.getBaseName());
        	Source source = loadFileIntoMemory(pgm);

        	module = ModulesFactory.getModule(source.getFullName());
        	module.addSource(source);

			try {
				process(module);
				postProcess(module);
//				test(module);
				if (!cfg.getBoolean(CFG.PARSER_PARSE)) generate(source);
	            storeInfo(module);
	            checkIVP(module);
			} catch (JMSException j) {
				msg.exception(j);
				return j.getExitCode();
			} catch (SDPException s) {
			    sendBadFile(args, module);
			    msg.exception(s);
			    return s.getExitCode();
			} catch (Exception e) {
				e.printStackTrace();
                msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
                countErrors++;
			}
        }
        writeIVP();
*/        
        return RC.OK;
	}

	/*JGG
	private void preProcess(Module module) throws SDPException, Exception {
		parse(module, Parsers.CALL);
		module.loadCallDynamic();
	}
	*/
	private void process(Module module) throws SDPException, Exception {
//		module.getSource().rewind();
		parse(module, Parsers.COBOL);
	}

	private void postProcess(Module module) {
	   // Procesa las variables
	   PostVariables vars = new PostVariables();
	   vars.parse(module);
	   
	   // Procesa los CALL ...
	   PostCall calls = new PostCall(module);
	   calls.parse();
	   
	}
	
	private void parse(Module module, int type) throws SDPException, Exception {
/*		
		Source source = module.getSource();
       	ILexer lexer = FactoryLexer.getLexer(source, type);
       	GenericParser parser = FactoryParser.getParser((GenericScanner) lexer, type);
       	parser.parse();
*/       	
	}
	
	private void storeInfo (Module module) {
	    Serializer ser = new Serializer();
//	    ser.storeModuleInfo(module, cfg.getBoolean(CFG.PARSER_LOCAL));
	}

	private void generate(Source source) {
		module = ModulesFactory.getModule(source.getFullName());
		generateProgram(source);
	}
	
	private void generateProgram(Source source) {
		Generator generator = new Generator(source, module);
		generator.generate();
	}
	
	private Source loadFileIntoMemory(Archivo input) {
		return SourcesFactory.getSource(input);
	}	
	

	private int sendBadFile(String[] args, Module module) {
/*
		if (cfg.getBoolean(CFG.PARSER_LOCAL)) return 0;
		
	    Module modErr = module;
	    if (modErr == null) {
            for (Archivo pgm : FileFinder.find(cfg.getInputDir(), args)) {
                 Source source = loadFileIntoMemory(pgm);
                 modErr = ModulesFactory.getModule(source.getFullName());
                 modErr.addSource(source);
            }
	    }
        Serializer ser = new Serializer();
        ser.storeModuleErr(modErr);
  */
        return 0;
	}

	private void writeIVP() {
	   if (IVPData == null) return;
        OutputStream out;
		try {
			out = new FileOutputStream("P:\\SDP\\IVP\\sdpivp.dat");
            IVPData.store(out, "");
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void checkIVP(Module module) {
        if (cfg.getBoolean(CFG.IVP_TEST) == false) return;	
        loadIVPData();
        String data = IVPData.getProperty(module.getName());
        data = updateData(data); 
        IVPData.setProperty(module.getName(), data);
	}
	
	private void loadIVPData() {
		if (IVPData != null) return;
	
		try {
          InputStream input = new FileInputStream("P:\\SDP\\IVP\\sdpivp.dat");
          IVPData = new Properties();
          IVPData.load(input);
          input.close();
        } catch (IOException ex) {
          ex.printStackTrace();
	    }
	}
	
	private String updateData(String oldData) {
		String[] datos = new String[3];
		if (oldData != null) {
			datos = oldData.split(";");
		}
		else {
			datos[0] = Integer.toString(cfg.getInteger(CFG.IVP_GROUP, 0));
		}
		return datos[0] + ";" + module.getNewHash() + ";" + module.getXMLHash();
	}
	
	//***************************************************************
	// Testing
	//***************************************************************

	/*
	private void test(Module module) {
		ArrayList<Variable> lista = module.getVariables();
		for (Variable raiz : lista) {
			System.out.printf("%s \t- %02d - %s\n", raiz.getModule(), raiz.getLevel(), raiz.getName());
			printHijos(1, raiz);
		}
	}
	
	private void printHijos(int nivel, Variable var) {
		if (var.getHijos() == null) return;
		for (Variable hijo : var.getHijos()) {
			System.out.printf("%s \t-", hijo.getModule());
			for (int i = 0; i < nivel; i++) System.out.print("     ");
			System.out.printf("%02d - %s\n", hijo.getLevel(), hijo.getName());
			printHijos(nivel + 1, hijo);
		}
	}
	
	*/
}
