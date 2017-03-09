/**
 * Contiene todas las acciones semanticas independientemente del 
 * dialecto SQL
 * De esta forma se separa el proceso de analisis de sus acciones
 * reutilizando el codigo
 *  
 * Casos especiales en algun dialecto deberian extender esta clase
 * y sobreescribir las acciones correspondientes
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.code;

import java.util.*;

import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.info.*;
import com.jgg.sdp.parser.lang.DB2Sym;
import com.jgg.sdp.parser.work.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;

public class DB2Code {

	private HashSet<String> tmpTables = new HashSet<String>();
	
	private Module   module;
 
	private ParserInfo      info   = ParserInfo.getInstance();
		
    public DB2Code(Module module) {
	   this.module = module;
	   
	}
    
    public Statement processInclude(StmtCopy copy) {
//        	addDependence(copy);
    	//processIssue();
    	info.addCopy(copy);
    	info.setIncludeParsed(true);
    	module.getSummary().setSQL();
    	Copy cpy = info.createtCurrentCopy();
    	Symbol include = copy.getVerb();
    	cpy.setBegLine(include.left);
    	cpy.setBegColumn(include.right);
    	return copy;
	
    }

    public StmtSQL processDeclare(StmtSQL stmt) {
    	Persistence pers = new Persistence();
    	switch (stmt.getOptionByPos(0).getId()) {
    	    case DB2Sym.TABLE:     pers.setType(Persistence.TABLE);  break;
    	    case DB2Sym.CURSOR:    pers.setType(Persistence.CURSOR); break;
    	    case DB2Sym.STATEMENT: return stmt;
    	}
    	pers.setPhysicalName(stmt.getRValue(0).getName());
    	pers.setLogicalName (stmt.getRValue(0).getName());
    	module.addFile(pers);
    	return stmt;
    }
    
    private void addDependence(StmtCopy copy) {
    	String name = null;
    	int    type    = CDG.DEP_INCLUDE;
    	int    subtype = CDG.DEP_INCLUDE;
    	    	
        SymbolExt sym = copy.getRValue(0);
        name = sym.getName();

        switch(info.getSection()) {
            case CDG.SECT_ID: break;
            case CDG.SECT_ENV: break; 
        }
        module.addCopy(new Copy(name, type, subtype));
    }

    public Symbol addTempTable(Symbol sym) {
          tmpTables.add(getTableName(sym));   
          return sym;
    }
    
    public SymbolList addTablesRead(SymbolList lista) {
    	
    	for (Symbol sym : lista.getVarList()) {
    	    String tableName = getTableName(sym.value);
    	    if (tmpTables.contains(tableName) == false) {
                Persistence pers = module.getFile(tableName);
                if (pers == null) {
                	addTable(tableName, Persistence.READ);
                }
                else {
                	pers.setType(Persistence.READ);
                }
    	    }
    	}
    	
    	return lista;
    }
    
    private String getTableName(Object value) {
    	String full = (String) value;
    	int last = full.lastIndexOf('.');
    	if (last == -1) return full;
    	return full.substring(last);
    }
    
    private void addTable(String name, int access) {
    	Persistence pers = new Persistence();
    	pers.setPhysicalName(name);
    	pers.setAccess(access);
    	pers.setType(Persistence.TABLE);
    	module.addFile(pers);
    }

}
