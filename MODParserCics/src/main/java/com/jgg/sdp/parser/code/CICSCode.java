package com.jgg.sdp.parser.code;

import java.util.*;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.Routine;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.base.stmt.*;
import com.jgg.sdp.parser.lang.CICSSym;

import java_cup.runtime.Symbol;

public class CICSCode {

	private Module module = null;
	private ArrayList<Option> parms = null;

	private CICSTS cicsTs = CICSTS.getInstance();
	
	public CICSCode() {
		
	}
	
    public CICSCode(Module module) {
	   this.module = module;
	}

    public StmtCICS mountStatement(Symbol verb) {
    	StmtCICS stmt = new StmtCICS(verb);
    	stmt.setParms(parms);
    	return stmt;
    }
    
    /**
     * Realizamos aqui el proceso asociado a las sentencias CICS
     * En principio:
     *    - Chequear LINK, XCTL y START TRANSID
     *    - Verificar si la sentencia es TS o no
     *    
     * Cualquier otro proceso de control deberia ir aqui   
     * @param stmt
     * @return
     */
    public StmtCICS processCICSStatement(StmtCICS stmt) {
    	/*
    	module.getCodigo().incStmtCics();
    	
    	String verb = stmt.getVerbName().toUpperCase();

    	if (verb.compareTo("LINK")  == 0) {
    		processCall(stmt, CDG.CALL_LINK);
    	} else if (verb.compareTo("XCTL")  == 0) {
    		processCall(stmt, CDG.CALL_XCTL);
    	} else if (verb.compareTo("START") == 0) {
    		if (stmt.getParameter("TRANSID") != null) {
    		    processTransaction(stmt, CDG.CALL_START);
    		}    
    	} else if (verb.compareTo("RETURN") == 0) {
          if (stmt.getParameter("TRANSID") != null) {
		      processTransaction(stmt, CDG.CALL_RETURN);
		  }
    	} else if (verb.startsWith("GETMAIN")) {
    		module.getSummary().setAllocate();
    	}
    	
    	cicsTs.checkTS(stmt);
    	module.addCICSVerb(stmt.getFullVerb(), stmt.getQRType() == CICSTS.TS, stmt.getQRType());
    	*/
    	return stmt;
    }
    
    private void processCall(StmtCICS stmt, int type) {
    	Option parm = stmt.getParameter("PROGRAM");
    	if (parm == null) return;
    	module.getSummary().setCallMode(type);
    	module.getSummary().setCallType(CDG.CALL_DYNAMIC);
    	processDependence(parm.getValue(), parm.getId(), type);
    }
    
    private void processTransaction(StmtCICS stmt, int method) {
    	Option parm = stmt.getParameter("TRANSID");
    	if (parm == null) return;
    	
    	module.getSummary().setCallMode(method);
    	module.getSummary().setCallType(CDG.CALL_DYNAMIC);

    	processDependence(CDG.DEP_TRAN, parm.getValue(), parm.getId(), method);
    }

    private void processDependence(String nombre, Integer tipo, int subtipo) {
   		processDependence(CDG.DEP_MODULE, nombre, tipo, subtipo);
   	}
   	
   	private void processDependence(int mod, String nombre, Integer tipo, int subtipo) {
		int modo = (tipo == CICSSym.ID) ? CDG.DEP_ST_VARIABLE : CDG.CALL_STATIC;
    	
		nombre = nombre.trim();
		
	    Routine rut = new Routine();
    	rut.setNombre(nombre);
    	rut.setMetodo(mod);
    	rut.setModo(modo);

    	module.addRoutine(rut);
	}

}
