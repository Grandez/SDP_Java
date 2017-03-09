package com.jgg.sdp.cics.code;

import java.util.*;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.cics.parser.*;
import com.jgg.sdp.cics.lang.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.Copy;

import java_cup.runtime.*;

public class CICSCode {

	private Module module = null;
	private ArrayList<CICSParm> parms = null;

	private CICSTS cicsTs = CICSTS.getInstance();
	
    public CICSCode(Module module) {
	   this.module = module;
	}

    public CICSStmt mountStatement(Symbol verb) {
    	CICSStmt stmt = new CICSStmt((String) verb.value);
    	stmt.setVerb((String) verb.value);
    	stmt.setParms(parms);
    	return stmt;
    }
    
    /**
     * Realizamos aqui el proceso asociado a las sentencias CICS
     * En principio:
     *    - Chequear LINK y XCTL
     *    - Verificar si la sentencia es TS o no
     *    
     * Cualquier otro proceso de control deberia ir aqui   
     * @param stmt
     * @return
     */
    public CICSStmt processCICSStatement(CICSStmt stmt) {
    	String verb = stmt.getVerb().toUpperCase();

    	if (verb.compareTo("LINK") == 0) processCall(stmt, CDG.CALL_LINK);
    	if (verb.compareTo("XCTL") == 0) processCall(stmt, CDG.CALL_XCTL);
    	
    	cicsTs.checkTS(stmt);
    	module.addCICSVerb(stmt.getFullVerb(), stmt.getQRType() == CICSTS.TS, stmt.getQRType());
    	
    	return stmt;
    }
    
    private void processCall(CICSStmt stmt, int type) {
    	CICSParm parm = stmt.getParameter("PROGRAM");
    	if (parm == null) return;
    	processDependence(parm.getValue(), parm.getType(), type);
    }
    
   	private void processDependence(String nombre, Integer tipo, int type) {
		int modo = (tipo == CICSSym.ID) ? CDG.DEP_ST_VARIABLE : CDG.CALL_STATIC;
/*JGG    	
		Copy dep = new Copy();
    	dep.setNombre(nombre);
    	dep.setTipo(CDG.DEP_MODULE);
    	dep.setSubtipo(type);
    	dep.setModo(modo);

    	if (tipo == CICSSym.ID) {
    		module.addCall(dep);
    	}
    	else {
    		module.addCopy(dep);
    	}
    	module.getSummary().setCall(type);
    	module.getSummary().setDynam(modo);
*/    	
	}

}
