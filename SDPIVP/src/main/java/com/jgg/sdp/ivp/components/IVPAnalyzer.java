package com.jgg.sdp.ivp.components;

import java.io.File;
import java.util.*;

import com.jgg.sdp.Analyzer;
import com.jgg.sdp.common.exceptions.*;
import com.jgg.sdp.common.files.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.ivp.IVPParser;
import com.jgg.sdp.ivp.base.*;
import com.jgg.sdp.ivp.evaluators.AnalyzerEval;
import com.jgg.sdp.ivp.items.*;
import com.jgg.sdp.ivp.jaxb.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.printer.Printer;

public class IVPAnalyzer {

    private HashSet<String>              modules = new HashSet<String>();
	private HashMap<Integer, BlockCases> bloques = new HashMap<Integer, BlockCases>();

	
    private String currArchivo = null;
    
	private Component component;
	
	private Printer     printer  = Printer.getInstance();
	
	private IVPPatterns patterns = new IVPPatterns();
	
	private HashMap<Integer, Block> blocks;
	
	private int    currBlock = 0;
	private int    count     = 0;
	private int    countErrs = 0;
    
	private Stack<IVPConfig> cfgs = new Stack<IVPConfig>();
	
	private IVPEnvironment env = new IVPEnvironment();
	
	public IVPAnalyzer(Component component, IVPConfig cfgBase) {
		this.component = component;
		IVPConfig cfg = new IVPConfig(cfgBase);
		cfg.update(component.getConfig());
		cfgs.push(cfg);
	}
	
	public int getCount()        { return count;          }
	public int getCountErrs()    { return countErrs;      }
	public int getCountModules() { return modules.size(); }

	public void process() throws Exception {
		
		for (IVPCaseType c: component.getCases().getCase() ) {
			switch (c.getType()) {
			    case DIRECTORY: processCasesDirectory(c.getValue());
			    default: 
			}
        }
	}
	
	private void processCasesDirectory(String dir) throws Exception {
		Banners.bannerGroup(dir);
		List<File> caseFiles = patterns.getFiles(dir, ".xml");
		processCasesFiles(caseFiles);
	}
	
	private void processCasesFiles(List<File> caseFiles) throws Exception {
		
		for (File f : caseFiles) {
			IVPParser parser = new IVPParser();
			SDPIVP ivp = parser.parse(f.getAbsolutePath());
			IVPConfig cfg = new IVPConfig(cfgs.peek());
			cfg.update(ivp.getConfig());
			cfgs.push(cfg);
			env.setEnvironment(currBlock, cfgs.peek(), ivp.getEnvironment());
			Banners.bannerXML(ivp.getDescription());

			for (IVPGroupType group: ivp.getGroup()) {
				processCasesGroup(group);
			}
			cfgs.pop();
		}
	}
	
	private void processCasesGroup(IVPGroupType group) {

		Banners.bannerGroup(group.getName());
		currBlock = 0;
		Banners.bannerBloque(currBlock);

		// Bloque inicial
		processCblModules(group.getPattern());
/*		
		// Cuando acaba la primera ronda busca otros bloques
		// Los ordenamos 
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		
		for (Map.Entry<Integer, BlockCases> entry : bloques.entrySet()) {
		    blocks.add(entry.getKey());
		}
		
		Collections.sort(blocks);
		
		for (int bloque : blocks) {
			currBlock = bloque;
            block = bloques.get(bloque);
			processBloque(block.getCases());
		}
		
		for (String pat : group.getPattern()) {
			processCasesGroup();
		}
*/		
	}
	
	private void processCblModules(List<String> patterns) {
		
		Module module = null;
		
	   for (String pattern : patterns) {
           for (Archive archivo : FileFinder.find(cfgs.peek().getCobolDir(), pattern)) { 
        	   currArchivo = archivo.getFileName();
        	   modules.add(currArchivo);
        	   printer.lineFixBeg(String.format("%5d - %8s", ++count, archivo.getBaseName()));
        	   module = analyze(archivo);
        	   int rc = evaluate(module); 
               if (rc == 0) printer.lineFixEnd("OK");
               countErrs += rc;
           } 	
	   }
	   
	}

	private Module analyze(Archive archivo) {
		int   rc = 0;
		Module module = null;
		Exception ex = null;
  
		Analyzer analyzer = new Analyzer();
		
		try {
            String[] parms = new String[] {"--local", "--force", "--ivp", archivo.getAbsolutePath()};
			rc = analyzer.process(parms);
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
          module.setRC(rc);
        }
        return module;
	}
	

	private int evaluate(Module module) {
		AnalyzerEval eval = new AnalyzerEval(module);
		return eval.evaluate(currBlock);
	}
	
/*	
	private void processBloque(List<String> patterns) {
		BlockCases block = null;
		
		// Bloque inicial
		processBloque(ivpxml.getCases());

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

	private void processBloque(List<Case> cases) {
		Banners.bannerBloque(currBloque);
		
		if (launcher.setEnvironment(currBloque) != 0) {
			printer.lineBeg("ERROR Cargando entorno para el bloque " + currBloque);
			printer.nl();
			System.exit(RC.FATAL);
		}

		for (Case c : cases) {
			currCase = c;
			printer.line("IVP GRUPO: " + c.getName());
//			processModules(c.getModules());
		}		
	}
	
	private void processCases(SDPIVP ivp) {
		for (IVPGroupType group : ivp.getGroup()) {
			Banners.bannerGroup(group.getDescription());
			processModules(group.getPattern());
		}
	}
	

	
	private int evaluate(Module module) {
		int ko = 0;
		int rc = 0;
		
		Groups cases = new Groups();
		cases.loadCases(module.getIVPCases());
		for (IVPCase c : cases.getCases(currBlock)) {
			rc = evaluateCase(module, c); 
			if (rc == 1) {
				if (ko == 0) printer.lineEnd("KO");
				printer.line("        ERROR: " + msgErr);
			}
			ko += rc;
		}
		
		if (currBlock == 0) loadOtherBlocks(cases);
		return ko;
	}
	
*/	
	/*
	*/
/*	

*/	
	private void loadEnvironments(IVPEnvType env) {
		for (Block block : env.getBlock()) {
			blocks.put(block.getId(),  block);
		}
	}
/*	
	private void createConfig(IVPConfigType xmlCfg) {
		cfg = new IVPConfig(cfgBase);
		if (xmlCfg == null) return;
		cfg.setBaseDir(xmlCfg.getBaseDir());
		cfg.setBinDir(xmlCfg.getBinDir());
		cfg.setWorkingDir(xmlCfg.getWorkingDir());
		cfg.setCobolDir(xmlCfg.getCobolDir());     
	}
*/
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
}
