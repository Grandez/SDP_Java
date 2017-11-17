package com.jgg.sdp.ivp.evaluators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.jgg.sdp.ivp.items.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.ivp.IVPCase;
import com.jgg.sdp.printer.Printer;

public class AnalyzerEval {

	private final int SDPANALYZER = 1;
	private final int ISSUES      = 2;
	
	private Module module = null;
	
	private Printer     printer  = Printer.getInstance();
	
	private HashMap<Integer, BlockCases> bloques = new HashMap<Integer, BlockCases>();
	
	public AnalyzerEval(Module module) {
	   this.module = module;	
	}
	
	public int evaluate(int block) {
		int ko = 0;
		int rc = 0;
		
		Groups cases = new Groups();
		cases.loadCases(module.getIVPCases());
		for (IVPCase c : cases.getCases(block)) {
			rc = evaluateCase(module, c); 
			if (rc == 1) {
				if (ko == 0) printer.lineFixEnd("KO");
//				printer.line("        ERROR: " + msgErr);
			}
			ko += rc;
		}
		
		if (block == 0) loadOtherBlocks(cases);
		return ko;
	}
	
	private void loadOtherBlocks(Groups cases) {
/*		
		List<Integer> grupos = cases.getGroups();
		for (Integer grupo : grupos) {
			if (grupo != 0) {
			   if (bloques.containsKey(grupo) == false) {
				   bloques.put(grupo, new BlockCases());
			   }
			   BlockCases p = bloques.get(grupo);
//			   int pos = p.addCase(currCase);
//			   p.addModule(pos, currArchivo);
			}   
		}
		
//		bloques.
*/		
	}
	
	private int evaluateCase(Module module, IVPCase c) {
		if (c.getDescription().length() > 0) printer.lineFixCnt(" - " + c.getDescription());
	
		int obj = selectObject(c.getObject()); 
		switch(obj) {
		   case SDPANALYZER: return evaluateAnalyzer(module, c);
		   default: return evaluateComponent(obj, module, c);
		}
    }

	private int evaluateAnalyzer(Module module, IVPCase c) {
		String objeto = "get" + c.getObject().toUpperCase();
		
		Object res = getResult(module, c.getMethod());
//		System.out.println(res.toString());
		if (res instanceof Integer) return Matchers.matchInteger((Integer) res, c);
		return 0;
		
	}

	private int evaluateComponent(int id, Module module, IVPCase c) {
		
		Object o = null;
		switch (id) {
		   case ISSUES: o = module.getTbIssues(); break; 
		}
		
		Object res = getResult(o, c.getMethod());
		//System.out.println(res.toString());
		if (res instanceof Integer) return Matchers.matchInteger((Integer) res, c);
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
	

	private int selectObject(String txt) {
		if (txt.compareToIgnoreCase("SDPAnalyzer") == 0) return SDPANALYZER;
		if (txt.compareToIgnoreCase("Issues")      == 0) return ISSUES;		
		return 0;
	}
	
}
