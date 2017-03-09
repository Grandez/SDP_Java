package com.jgg.sdp.parser.post;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.tablas.*;

public class PostCall {

	private Routine    tmp     = null;	
	private TBVars     tbVars  = null;
	private Module     module  = null;
	private boolean    incomplete = false;
	
	// Variables que ya han sido analizadas
	private HashSet<String> lstVars = new HashSet<String>();
	
	// Mantiene la lista de variables analizadas para evitar
	// MOVE A TO B
	// MOVE B TO A
	private HashSet<String> pathVars = new HashSet<String>();
	
	public PostCall(Module module) {
		this.module = module;
		tbVars = module.getTBVars();
	}
	
	public void parse() {
        
		String nombre = null;
		
		module.setNumTreeVariables(0);
		
		for (Routine rut : module.getDynamicCalls()) {
		    nombre = rut.getNombre();
			// Si existe ya se ha procesado esa variable
		    if (lstVars.contains(nombre)) continue;

		    pathVars = new HashSet<String>();		    
		    lstVars.add(nombre);		    	
		    
			if (processVariable(rut, nombre)) {
				rut.setEstado(CDG.DEP_ST_VARIABLE);
				module.addRoutineChecked(rut);
				module.addTreeVariable();
			}
		}
		if (module.getStatus() != CDG.STATUS_UNDEF) { // Parcial
			if (!incomplete) module.setStatus(CDG.STATUS_PARTIAL_NO_DOUBT);
		}
		
	}
	
	private boolean processVariable(Routine rut, String nombre) {
		int count = 0;

		Variable v = tbVars.getVariable(nombre);
		if (v == null) {
			incomplete = true;
			return true;
		}

		if (pathVars.contains(nombre)) return true;
		pathVars.add(nombre);
		
		// Procesar las asignaciones literales de la variable
		// Ej.: MOVE 'PEPE' TO ROUTINE
		for (String mod : v.getValues()) {
			if (isAVariable(mod)) {
			   tmp = new Routine(rut);
			   tmp.setNombre(mod.trim());
		       tmp.setModo(CDG.CALL_DYNAMIC);
			   module.addRoutineChecked(tmp);
			}   
			count++;
		}
		
		// Analizar las asignaciones de variables
		// Ej.: MOVE PEPE TO ROUTINE
		for (String var : v.getVariables()) {
		    processVariable(rut, var);
		    count++;
		}
		
		// Si se ha escrito mas veces que las procesadas
		// No tenemos garantia de un analisis completo
		if (v.getNumwrites() > count) return true;
        return false;
	}

	private boolean isAVariable(String mod) {
		if (mod.trim().length() == 0) return false;
		if (mod.compareToIgnoreCase("SPACE")  == 0) return false;
		if (mod.compareToIgnoreCase("SPACES") == 0) return false;
		return true;
	}
}
