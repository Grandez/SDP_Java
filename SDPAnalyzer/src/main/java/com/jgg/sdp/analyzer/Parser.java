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

import com.jgg.sdp.common.config.*;

import com.jgg.sdp.analyzer.base.*;
import com.jgg.sdp.analyzer.post.PostCall;
import com.jgg.sdp.analyzer.post.PostVariables;
import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.common.exceptions.SDPException;
import com.jgg.sdp.common.files.Archive;
import com.jgg.sdp.domain.core.SDPStatus;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.SDPStatusService;

import java_cup.runtime.Scanner;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.status.*;
import com.jgg.sdp.module.tables.TBSumIssues;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.parser.base.ParserInfo;

public class Parser {

	private Unit unit   = null;
    private Module  module = null;

   
    private Configuration cfg = DBConfiguration.getInstance();
    ParserInfo info = ParserInfo.getInstance();
    
    public Parser() {
    	
    }
    public Parser(Unit unit) {
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
	   
	   module.addIssues(info.rules.getIssues());
	   
	   //Procesa el grafo
	   //module.makeGraph();
	   
	   info.rules.checkModule(module);
	   
	   mountStatus(module);

	}

	private void parseCobol(Module module, int type) throws SDPException, Exception {
        ProxyLexer    lexer  = null;
        GenericParser parser = null;
	    
        Source source =  unit.getMainSource();

       	// Parse el modulo fuente hasta encontrar PROCEDURE
        lexer = new ProxyLexer(source, Parsers.ZCOBOLDATA);
        parser = FactoryParser.getParser((Scanner) lexer, Parsers.ZCOBOLDATA);
//        parser.setScanner(lexer);
        
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

	private void mountStatus(Module module) {
		Status current = getCurrentStatus(module);
		Status actual  = module.getStatus();
		
		calculateIssues(actual, module);
		calculateProgress(actual, current);
	}

	private Status getCurrentStatus(Module module) {
		Status status = new Status();
		SDPStatusService service = new SDPStatusService();
		for (SDPStatus st : service.listAll(module.getName())) {
			StatusItem item = new StatusItem(st.getIdGrupo(), st.getIdItem());
			item.setActual(st.getActual());
			item.setExcepcion(st.getExcepcion());
			item.setMaximo(st.getMaximo());
			item.setProgreso(st.getProgreso());
			item.setDelta(st.getDelta());
			item.setStatus(st.getStatus());
			status.add(st.getIdGrupo(), st.getIdItem(), item);
		}
		return status;
	}
	
	private void calculateIssues(Status actual, Module module) {
		int level = 0;
		TBSumIssues issues = module.getSumIssues();
		StatusGroup group = actual.getGroup(Status.ISSUES);
		
		for (int idx = 0; idx < 7; idx++) {
			level = (idx == 6) ? 99 : idx;
			StatusItem item = new StatusItem(group.getId(), level);
			item.setActual(issues.getCount(level));
			item.setMaximo(issues.getMaximum(level));
			item.setStatus(item.getActual() > item.getMaximo() ? 1 : 0);
            group.add(level, item);
		}
	}

	/**
	 * El maximo puede ser un maximo o un minimo.
	 * Si es minimo ira en negativo
	 * Si es maximo puede tener un cero
	 * 
	 * @param actual
	 * @param last
	 */
	private void calculateProgress(Status actual, Status last) {
		for (StatusGroup grp : actual.getGroups()) {
			for (StatusItem itm : grp.getItems()) {
				StatusItem l = last.getItem(grp.getId(), itm.getIdItem());
				if (itm.getMaximo() < 0) {
					calculateProgressMinimum(itm,l);
				}
				else {
					calculateProgressMaximum(itm, l);
				}
			}
		}
	}
	
	private void calculateProgressMinimum(StatusItem actual, StatusItem last) {
		actual.setDelta(100);
		actual.setProgreso(100);
	}

	private void calculateProgressMaximum(StatusItem actual, StatusItem last) {
		// Primera ejecucion
		if (last == null)  {
			calculateProgressMaximumFirst(actual);
			return;
		}

		// Se ha alcanzado el objetivo
		if (actual.getActual() <= actual.getMaximo()) {
			actual.setProgreso(100);
			actual.setDelta(100);
			return;
		}
		
		// No ha habido cambios
		if (actual.getActual() == last.getActual()) {
			actual.setProgreso(last.getProgreso());
			actual.setDelta(0);
			return;
		}
		
		if (last.getActual() <= last.getMaximo()) {
			actual.setDelta(100 - last.getDelta());
			int prg = (actual.getMaximo() == 0) ? actual.getDelta() 
					                            : actual.getActual() * 100 / actual.getMaximo(); 
			actual.setProgreso(prg);
			return;
		}
		
		int diff = last.getActual() - actual.getActual();
		actual.setDelta(diff / (last.getActual() - last.getMaximo()));
		actual.setProgreso(last.getProgreso() + ((1 - last.getProgreso()) * actual.getDelta()));
	}
	
	private void calculateProgressMaximumFirst(StatusItem actual) {
		if (actual.getActual() <= actual.getMaximo()) {
			actual.setProgreso(100);
			actual.setDelta(100);
			return;
		}
		
		if (actual.getMaximo() == 0) {
		    actual.setProgreso(0);
		    actual.setDelta(0);
		}
		else {
			actual.setProgreso(actual.getActual() * 100 / actual.getMaximo());
			actual.setDelta(actual.getProgreso());
		}
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
	private Source loadFileIntoMemory(Archive input) {
		return new Source(input, true);
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
