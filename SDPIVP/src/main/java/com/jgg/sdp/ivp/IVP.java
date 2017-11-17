package com.jgg.sdp.ivp;


import java.io.File;
/**
 * TODO Tiene que arrancar con cero
 * Carga el entorno para el caso cero
 * Luego cada modulo que tenga mas de un bloque meterlo en un conjunto de listas bloque - lista
 * Cuando acabe el cero, 
 *   Cargar el entorno para el caso x
 *   Procesar por el conjunto de listas, buscando solo esos programas
 * 
 */
import java.net.URL;

import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.config.*;

import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.ivp.base.Banners;
import com.jgg.sdp.ivp.base.IVPConfig;
import com.jgg.sdp.ivp.components.IVPAnalyzer;
import com.jgg.sdp.ivp.jaxb.Component;
import com.jgg.sdp.ivp.jaxb.IVPConfigType;
import com.jgg.sdp.ivp.jaxb.SDPIVP;
import com.jgg.sdp.printer.Printer;

public class IVP {

    private Configuration cfg = DBConfiguration.getInstance();

	private int count     = 0;
	private int countErrs = 0;
	private int modules   = 0;
	
    private IVPConfig cfgBase;
    
    private IVP() {
    	initObject();
    }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   IVP launcher = new IVP();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		
		int     maxRC   = RC.OK;
			
		args = cfg.processCommandLine(IVPParms.parms, args);
		
		Banners.banner();
		
//		if (args.length == 0) {
			loadFromResources();
//		}
//		else {
//			return loadFromFileSystem(args);
//		}
		
//		xml.loadFile(cfg.getString(CFG.IVP_CONFIG));
//		process();
		
		printResults();
		
		return maxRC;
	}
	private int loadFromResources() {
		URL root = Thread.currentThread().getContextClassLoader().getResource("SDPIVP.xml");
		File base = new File(root.getPath());

		try {
			IVPParser parser = new IVPParser();
			//RULES rule = parser.parse("P:/SDP/config/rule00.xml");
			SDPIVP ivp = parser.parse(base.getAbsolutePath());
			for (Component component : ivp.getComponent()) {
				switch (component.getName()) {
				   case SDP_ANALYZER:  processAnalyzer(component); break;
				}
				
			}
		} catch (Exception ex) {
			if (ex instanceof javax.xml.bind.UnmarshalException) {
	            javax.xml.bind.UnmarshalException unmarshalEx = (javax.xml.bind.UnmarshalException) ex;
	            if (unmarshalEx.getLinkedException() != null) {
	                System.out.println(unmarshalEx.getLinkedException().getMessage());
	            } else {
	                System.out.println(unmarshalEx.getMessage());
	            }
	        }
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return 0;				
	}

	private  void processAnalyzer(Component component) throws Exception {
		Banners.bannerComponent("SDPAnalyzer");
		IVPAnalyzer analyzer = new IVPAnalyzer(component, cfgBase);
		analyzer.process();
		modules   += analyzer.getCountModules();
		count     += analyzer.getCount();
		countErrs += analyzer.getCountErrs();
	}
/*	
	private void process() {

		BlockCases block = null;
		
		// Bloque inicial
		processBloque(xml.getCases());

		// Cuando acaba la primera ronda busca otros bloques
		// Los ordenamos 
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		
		for (Map.Entry<Integer, BlockCases> entry : bloques.entrySet()) {
		    blocks.add(entry.getKey());
		}
		
		Collections.sort(blocks);
		
		for (int bloque : blocks) {
			currBloque = bloque;
            block = bloques.get(bloque);
			processBloque(block.getCases());
		}
		
	}

*/	
	
	
/*	

*/	
	
	
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
	

	private void setDefaultEnvironment(IVPConfigType xmlCfg) {
		cfgBase = new IVPConfig();
		if (xmlCfg == null) return;
		cfgBase.setBaseDir(xmlCfg.getBaseDir());
		cfgBase.setBinDir(xmlCfg.getBinDir());
		cfgBase.setWorkingDir(xmlCfg.getWorkingDir());
		cfgBase.setCobolDir(xmlCfg.getCobolDir());
	}
	
	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_IVP);
		cfg.addTitle(CDG.TXT_SKIP,  0);
		cfg.addTitle(CDG.TXT_USE,   MSG.USE_SDP_IVP);
	}
	
	private void printResults() {
		Printer printer = new Printer();
		printer.boxBeg();
		printer.boxLine("SERENDIPITY - IVP", true);
		printer.boxLine(String.format("%s %5d",  "Modulos analizados: ", modules)  , false);
		printer.boxLine(String.format("%s %5d",  "Casos realizados:   ", count)    , false);
		printer.boxLine(String.format("%s %5d",  "Casos erroneos:     ", countErrs), false);		
		printer.boxEnd();
		printer.nl();
		
	}

}
