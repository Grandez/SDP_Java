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

import java.io.FileReader;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.Messages;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.lang.OCParser;

public class Parser {

    private Source source = null;
    private Module module = null;

    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();

    private int countErrors = 0;
    private int countModulos = 0;
    
	String prm[][] = { 
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT    , "1" , "204" , Args.DIR}			          
		   ,{" ", "o" , "output" , "SDP_OUTPUT"       , CFG.DIR_OUTPUT   , "1" , "205" , Args.DIR}
	       ,{" ", "n" , "name"   , ""                 , CFG.TEMP_NAME    , "1" , "211" , Args.STRING}
		   ,{" ", "l" , "left"   , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT  , "1" , "206" , Args.NUMBER}	   
		   ,{" ", "r" , "right"  , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT , "1" , "207" , Args.NUMBER}
		   ,{" ", "q" , "qname"  , "SDP_QUEUE"        , CFG.JMS_QUEUE    , "1" , "208" , Args.STRING}
	       ,{"1", "e" , "error"  , ""                 , CFG.PARSER_ERR   , "0" , "212" , Args.BOOLEAN}
	};
    
	/**
     * Entrada al programa
     *
     * @param args Argumentos de llamada al programa
     */
	public static void main(String[] args) {
	   int rc = RC.OK;
	   Parser launcher = new Parser();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
	    int maxRC = RC.OK;
        try {
            process(args);
         }
         catch (SDPException e) {
             msg.exception(e);
             return RC.FATAL;
         }
        if (countErrors > 0) {
            msg.warning(MSG.PARSE_ERRORS, countErrors);
            maxRC = RC.WARNING;
        }
        msg.progress(1, MSG.PARSED, countModulos);
        return maxRC;
	}
	
	private int process(String[] args) {
		cfg.setTitles(MSG.TITLE_SDP_PARSER);
		
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) {
		    msg.warning(MSG.NO_DATA);
		    return RC.NO_DATA;
		}

		if (cfg.getBoolean(CFG.PARSER_ERR)) {
		    return sendBadFile(args, null);
		}
        for (Archivo pgm : FileFinder.find(args)) {
            countModulos++;
        	msg.progress(2, MSG.PARSING, pgm.getBaseName());
        	Source source = loadFileIntoMemory(pgm);

        	module = ModulesFactory.getModule(source.getFullName());
        	module.setSource(source);

        	CobolLexer lexer = CobolLexerFactory.getLexer(source);
        	lexer.setFullName(source.getFullName());
        	OCParser parser = CobolParserFactory.getParser(lexer);
        	
			try {
				parser.parse();
	            generateOutputData(source);
	            storeData(module);
			} catch (SDPException s) {
			    sendBadFile(args, module);
			    msg.exception(s);
			    return s.getExitCode();
			} catch (Exception e) {
	             msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
                 countErrors++;
			}
        }
        return RC.OK;
	}

	private void storeData (Module module) {
		Serializer ser = new Serializer();
		ser.storeModuleInfo(module);
	}

	private void generateOutputData(Source source) {
		module = ModulesFactory.getModule(source.getFullName());
		generateProgram();
	}
	
	private void generateProgram() {
		Generator generator = new Generator(source, module);
		generator.generate();
	}
	
	private Source loadFileIntoMemory(Archivo input) {
		input = checkFile(input);

		int size = ((Long) input.length()).intValue();
        source = SourcesFactory.getSource(input.getAbsolutePath());
        
        source.setModuleName(input.getBaseName());
        source.setSize(size);
        
        try {
        	FileReader reader = new FileReader(input);
        	reader.read(source.getRawData(), 0, size);
        	reader.close();
        }
        catch (Exception e) {
	    	throw new FileException(MSG.FILE_NOT_READ, input.getAbsoluteFile());
        }
        
        source.prepareData();
	    return source;
	}
	
	private Archivo checkFile(Archivo pgmName) {
		if (!pgmName.hasDir() && pgmName.exists() == false) {
			pgmName = new Archivo(cfg.getInputDir() + pgmName.getFileName());
		}

	    if (pgmName.exists() == false) {
	    	throw new FileException(MSG.FILE_NOT_EXIST, pgmName.getAbsoluteFile());
	    }
        if (!pgmName.canRead()) {
	    	throw new FileException(MSG.FILE_NOT_READ, pgmName.getAbsoluteFile());
	    }
        return pgmName;
	}

	private int sendBadFile(String[] args, Module module) {
	    Module modErr = module;
	    if (modErr == null) {
            for (Archivo pgm : FileFinder.find(args)) {
                 Source source = loadFileIntoMemory(pgm);
                 modErr = ModulesFactory.getModule(source.getFullName());
                 modErr.setSource(source);
            }
	    }
        Serializer ser = new Serializer();
        ser.storeModuleErr(modErr);
        return 0;
	}
}
