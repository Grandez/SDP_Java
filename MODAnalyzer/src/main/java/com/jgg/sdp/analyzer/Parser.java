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
package com.jgg.sdp.analyzer;

import com.jgg.sdp.analyzer.base.*;
import com.jgg.sdp.analyzer.post.*;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.parser.base.ParserInfo;

import java_cup.runtime.Scanner;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.SDPUnit;
import com.jgg.sdp.module.unit.Source;

public class Parser {

	private SDPUnit unit   = null;
    private Module  module = null;

   
    private Configuration cfg = Configuration.getInstance();

    public Parser() {
    	
    }
    public Parser(SDPUnit unit) {
       this.unit = unit;	
    }
    
	public void process() throws SDPException, Exception {
		module = unit.getCurrentModule();
		
        // Procesa COBOL		
		parseCobol(module, getCobolDialect());

		// Procesa las variables
	   PostVariables vars = new PostVariables();
	   vars.parse(module);
	   
	   // Procesa los CALL ...
	   PostCall calls = new PostCall(module);
	   calls.parse();
	   
	   // Chequea si hay MQSeries
	   checkMQAndCalls(module);
	   
	   //Procesa el grafo
	   module.makeGraph();
	   
	}

	private void parseCobol(Module module, int type) throws SDPException, Exception {
        ProxyLexer    lexer  = null;
        GenericParser parser = null;

	    ParserInfo info = ParserInfo.getInstance();
	    
        Source source =  unit.getMainSource();

       	// Parse el modulo fuente hasta encontrar PROCEDURE
        lexer = new ProxyLexer(source, Parsers.ZCOBOLDATA);
        parser = FactoryParser.getParser((Scanner) lexer, Parsers.ZCOBOLDATA);
       	parser.parse();
       	
       	// A EOF se le llama dos veces
       	source.forward(info.removeOffset());
       	info.removeOffset();
       	info.removeOffset();
       	
       	// Parse el modulo fuente hasta encontrar IDENTIFICATION
       	lexer = new ProxyLexer(source, Parsers.ZCOBOLCODE);
        parser = FactoryParser.getParser((Scanner) lexer, Parsers.ZCOBOLCODE);
        parser.parse();
	}
	
	/*
	private void storeInfo (Module module) {
	    Serializer ser = new Serializer();
	    ser.storeModuleInfo(module, cfg.getBoolean(CFG.PARSER_LOCAL));
	}

	private void generate(Source source) {
		module = ModulesFactory.getModule(source.getFullName());
		generateProgram(source);
	}
	
	private void generateProgram(Source source) {
		Generator generator = new Generator(source, module);
		generator.generate();
	}
	*/
	private Source loadFileIntoMemory(Archivo input) {
		return SourcesFactory.getSource(input);
	}	
	
/*
	private int sendBadFile(String[] args, Module module) {
		
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
        return 0;
	}
*/
	private int getCobolDialect() {
		String dialect = cfg.getCobolDialect();
		if (dialect.compareToIgnoreCase("ZCobol") == 0) {
			return Parsers.ZCOBOLDATA;
		}
		if (dialect.compareToIgnoreCase("OCobol") == 0) {
			return Parsers.OCOBOL;
		}
		if (dialect.compareToIgnoreCase("Test") == 0) {
			return Parsers.TEST;
		}
		throw new SDPException(MSG.EXCEP_NO_LANG_DEF);
	}
	
	private void checkMQAndCalls(Module module) {
		int count = 0;
		Summary summ = module.getSummary();
		
        for (Routine d : module.getCalls()) {
        	 count++;
             String prfx = d.getNombre().toUpperCase();
             if (prfx.startsWith("MQ")) summ.setMQ();
        }
        summ.setCallCount(count);
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
