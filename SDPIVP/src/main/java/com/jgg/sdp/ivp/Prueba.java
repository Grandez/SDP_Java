package com.jgg.sdp.ivp;

/**
 * TODO Tiene que arrancar con cero
 * Carga el entorno para el caso cero
 * Luego cada modulo que tenga mas de un bloque meterlo en un conjunto de listas bloque - lista
 * Cuando acabe el cero, 
 *   Cargar el entorno para el caso x
 *   Procesar por el conjunto de listas, buscando solo esos programas
 * 
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.jgg.sdp.Analyzer;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.core.tools.FileFinder;
import com.jgg.sdp.ivp.cases.Case;
import com.jgg.sdp.ivp.cases.Groups;
import com.jgg.sdp.ivp.cases.BlockCases;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.ivp.IVPCase;
import com.jgg.sdp.module.unit.SDPUnit;
import com.jgg.sdp.parser.base.ParseException;
import com.jgg.sdp.printer.JGGPrinter;
import com.jgg.sdp.domain.DBManagerFactory;
import com.jgg.sdp.domain.cfg.ATest;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.cfg.ATestService;

public class Prueba {

	private ATestService test = new ATestService();
	
	private final int SDPANALYZER   =  1;
	private final int ISSUES        = 50;
	   
    private Configuration cfg = DBConfiguration.getInstance();

    private IVPLaunchers launcher = new IVPLaunchers();
	private JGGPrinter printer = new JGGPrinter();

	private HashMap<Integer, BlockCases> bloques = new HashMap<Integer, BlockCases>();
    private HashSet<String> modules = new HashSet<String>();
	
	private int count = 0;
	private int numErrs = 0;
	
    private String msgErr;

    private Case   currCase = null;
    private int    currBloque = 0;
    private String currArchivo = null;
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Prueba launcher = new Prueba();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		
		int     maxRC   = RC.OK;
			
		cfg.setTitles(MSG.TITLE_SDP_IVP);
		args = cfg.processCommandLine(IVPParms.parms, args);
		
		process();
		
		printResults();
		
		return maxRC;
	}
		
	private void process() {

		BlockCases block = null;

		for (ATest t : test.todo()) {
			System.out.println("Valor: " + t.getClave());
			System.out.println("Tms: " + t.getTms());
		}
		
		for (int i = 0; i < 3; i++) {
			currBloque = i;
			processBloque();
		}
		// Bloque inicial
		

/**
 * JGG - Hay un problema de caches, cuando carga los datos, recupera la version anterior
 * Por ahora solo soportamos un bloque		
 */
/*		
		
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
*/		
	}
	
	private void processBloque() {
		bannerBloque(currBloque);
		
		DBManagerFactory.getInstance().getEntityManager().clear();
		DBManagerFactory.getInstance().getEntityManager().close();
		

		test.createEntityManager();
		
		if (launcher.setEnvironment(currBloque) != 0) {
			printer.lineBeg("ERROR Cargando entorno para el bloque " + currBloque);
			printer.nl();
			System.exit(16);
		}

		test.clearCache();
		
		for (ATest t : test.todo()) {
			System.out.println("Valor: " + t.getClave() + " - " + "Tms: " + t.getTms());
		}
	}
	
	private void processModules(List<String> patterns) {
		
		Module module = null;
		
	   for (String pattern : patterns) {
           for (Archivo archivo : FileFinder.find(cfg.getString(CFG.IVP_INPUT), pattern)) { 
        	   currArchivo = archivo.getFileName();
        	   modules.add(currArchivo);
        	   printer.lineBeg(String.format("%5d - %8s", ++count, archivo.getBaseName()));
        	   module = analyze(archivo);
        	   int rc = evaluate(module); 
               if (rc == 0) printer.lineEnd("OK");
               numErrs += rc;
           } 	
	   }
	   
	}
	
	private Module analyze(Archivo archivo) {
		int   rc = 0;
		Module module = null;
		Exception ex = null;
  
		SDPUnit unit = new SDPUnit(archivo);
		
		Analyzer analyzer = new Analyzer();
		
		try {
			rc = analyzer.analyze(unit);
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
		int ko = 0;
		int rc = 0;
		
		Groups cases = new Groups();
		cases.loadCases(module.getIVPCases());
		for (IVPCase c : cases.getCases(currBloque)) {
			rc = evaluateCase(module, c); 
			if (rc == 1) {
				if (ko == 0) printer.lineEnd("KO");
				printer.line("        ERROR: " + msgErr);
			}
			ko += rc;
		}
		
		if (currBloque == 0) loadOtherBlocks(cases);
		return ko;
	}
	
	private int evaluateCase(Module module, IVPCase c) {
		if (c.getDescription().length() > 0) printer.lineCnt(" - " + c.getDescription());
		int obj = selectObject(c.getObject()); 
		switch(obj) {
		   case SDPANALYZER: return evaluateAnalyzer(module, c);
		   default: return evaluateComponent(obj, module, c);
		}
    }
	
	private int selectObject(String txt) {
		if (txt.compareToIgnoreCase("SDPAnalyzer") == 0) return SDPANALYZER;
		if (txt.compareToIgnoreCase("Issues")      == 0) return ISSUES;
		
		return 0;
	}
	
	private int evaluateAnalyzer(Module module, IVPCase c) {
		String objeto = "get" + c.getObject().toUpperCase();
		
		Object res = getResult(module, c.getMethod());
//		System.out.println(res.toString());
		if (res instanceof Integer) return matchInteger((Integer) res, c);
		return 0;
		
	}

	private int evaluateComponent(int id, Module module, IVPCase c) {
		
		Object o = null;
		switch (id) {
		   case ISSUES: o = module.getTbIssues(); break; 
		}
		
		Object res = getResult(o, c.getMethod());
		//System.out.println(res.toString());
		if (res instanceof Integer) return matchInteger((Integer) res, c);
		return 0;
		
	}
	
	private Object getResult(Object o, String method) {
		
		try {
			Method m = o.getClass().getMethod(method);
			m.setAccessible(true);
			return m.invoke(o);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private int matchInteger(Integer res, IVPCase c) {
		int tgt = c.getValueInteger();
		
		if (tgt != res) msgErr = String.format("Expected: %d. Found: %d",  tgt, res);
		return (tgt == res) ? 0 : 1;
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
	
	private void loadOtherBlocks(Groups cases) {
		List<Integer> grupos = cases.getGroups();
		for (Integer grupo : grupos) {
			if (grupo != 0) {
			   if (bloques.containsKey(grupo) == false) {
				   bloques.put(grupo, new BlockCases());
			   }
			   BlockCases p = bloques.get(grupo);
			   int pos = p.addCase(currCase);
			   p.addModule(pos, currArchivo);
			}   
		}
		
//		bloques.
		
	}
	private void banner() {
		
		printer.boxBeg();
		printer.boxLine("SERENDIPITY", true);
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxEnd();
		printer.nl();
	}

	private void bannerBloque(int bloque) {
		
		printer.boxBeg();
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxLine("BLOQUE DE PRUEBAS " +  bloque, true);		
		printer.boxEnd();
		printer.nl();
	}

	private void printResults() {
		printer.boxBeg();
		printer.boxLine("SERENDIPITY - IVP", true);
		printer.boxLine(String.format("%s %5d",  "Modulos analizados: ", modules.size()), false);
		printer.boxLine(String.format("%s %5d",  "Casos realizados:   ", count), false);
		printer.boxLine(String.format("%s %5d",  "Casos erroneos:     ", numErrs), false);		
		printer.boxEnd();
		printer.nl();
		
	}
}
