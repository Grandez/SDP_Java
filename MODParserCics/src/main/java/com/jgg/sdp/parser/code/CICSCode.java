package com.jgg.sdp.parser.code;

import java.util.*;

import com.jgg.sdp.blocks.stmt.Option;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.Routine;
import com.jgg.sdp.parser.rules.RulesCICS;
import com.jgg.sdp.parser.stmt.*;
import com.jgg.sdp.parser.symbols.SDPSymbol;
import com.jgg.sdp.tools.Cadena;

import static com.jgg.sdp.common.ctes.CDG.*;
import static com.jgg.sdp.parser.lang.CICSSym.*;


public class CICSCode {

	private RulesCICS rules = new RulesCICS();
	
	private Module            module = null;
	private ArrayList<Option> parms  = null;

//	private CICSTS cicsTs = CICSTS.getInstance();
	
	public CICSCode() {
		
	}
	
    public CICSCode(Module module) {
	   this.module = module;
	}

    public StmtCICS mountStatement(SDPSymbol verb) {
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
    	switch (stmt.getGroup()) {
             case STMT_CICS_CTL: processCICSControl(stmt); break;
    	}
//        public static final int STMT_CICS      = 20;
//        public static final int STMT_CICS_SYS  = 21;
//        public static final int STMT_CICS_PGM  = 22;
//        public static final int STMT_CICS_CTL  = 23;
//        public static final int STMT_CICS_WEB  = 24;
    	
    	rules.checkRESP(stmt);
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
    
    private void processCICSControl(StmtCICS stmt) {
    	int callMode = CALL_UNDEF;
    	int callType = CALL_DYNAMIC;

    	switch (stmt.getId()) {
            case LINK:   callMode = CALL_LINK;   break;
	        case XCTL:   callMode = CALL_XCTL;   break;
	        case START:  callMode = CALL_START;	 break;        
	        case RETURN: callMode = CALL_RETURN; break;
	        default:     callMode = CALL_UNDEF;
        }
    	
    	Option opt = stmt.getOption(PROGRAM);
    	if (opt == null) opt = stmt.getOption(TRANSID);
    	if (opt.getValueType() == LITERAL) callType =  CALL_STATIC;

    	processDependence(callMode, callType, opt.getValue());
    }
    
   	
   	private void processDependence(int callMode, int callType, String nombre) {
		nombre = nombre.trim();
		
	    Routine rut = new Routine();
    	rut.setNombre(Cadena.removeQuotes(nombre));
    	rut.setModo(callMode);
    	rut.setMetodo(callType);

    	module.addRoutine(rut);
	}

}
