package com.jgg.sdp.ivp;

import java.util.*;

import com.jgg.sdp.Analyzer;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.FileException;
import com.jgg.sdp.core.exceptions.NotSupportedException;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.core.tools.FileFinder;
import com.jgg.sdp.ivp.cases.Case;
import com.jgg.sdp.ivp.cases.Groups;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.ivp.IVPCase;
import com.jgg.sdp.module.unit.SDPUnit;
import com.jgg.sdp.parser.base.ParseException;
import com.jgg.sdp.print.Printer;


public class IVP {

    private Messages      msg = Messages.getInstance("IVP");    
    private Configuration cfg = Configuration.getInstance();

    private XMLIVP xml = new XMLIVP();
    
	private Printer printer = new Printer();
	

	private int count = 0;
	
    private int countErrors = 0;
    private int countModulos = 0;

    private Module module = null;
        
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   IVP launcher = new IVP();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		SDPUnit unit = null;
		int     maxRC   = RC.OK;
		int     procesar = MSG.OK;
			
		String[] def = {"*"};
			
		cfg.setTitles(MSG.TITLE_SDP_IVP);
			
		args = cfg.processCommandLine(IVPParms.parms, args);
		
		banner();
		
		xml.loadFile("P:/SDP/Config/ivp.xml");
		process();
		return maxRC;
	}
	
	
	private void process() {
		for (Case c : xml.getCases()) {
			System.out.println(c.getName());
			processModules(c.getModules());
		}
	}
	
	private void processModules(List<String> patterns) {
		Module module = null;
		
		int rc = 0;
		for (String pattern : patterns) {
	        for (Archivo archivo : FileFinder.find("P:/SDP/Cobol", pattern)) { // (cfg.getInputDir(), pattern)) {
	        	printer.lineBeg(String.format("%5d - %8s - ", ++count, archivo.getBaseName()));
	        	module = analyze(archivo);
	        	evaluate(module);
	        	printer.lineEnd("OK");
	        } 	
		}
	}
	
	private Module analyze(Archivo archivo) {
		Module module = null;
		Exception ex = null;
		
		Analyzer analyzer = new Analyzer();
		
		try {
			analyzer.analyze(archivo);
        } catch (FileException f) {
        	ex = f;
        } catch (NotSupportedException s) {
        	ex = s;
        } catch (ParseException s) {
        	ex = s;
        } catch (SDPException s) {
        	ex = s;
        } catch (Exception e) {
        	ex = e;
        } finally {
          module = analyzer.getModule();
          module.setException(ex);
        }
        return module;
	}
	
	private int evaluate(Module module) {
		Groups cases = new Groups();
		cases.loadCases(module.getIVPCases());
		for (IVPCase c : cases.getCases("default")) {
			evaluateCase(module, c);
		}
		return 0;
	}
	
	private void evaluateCase(Module module, IVPCase c) {
		System.out.println(c.getObject());
		System.out.println(c.getMethod());
		System.out.println(c.getValue());
	}
/*
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
	*/
	private void banner() {
		
		printer.boxBeg();
		printer.boxLine("SERENDIPITY", true);
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxEnd();
		printer.nl();
	}
	
}
