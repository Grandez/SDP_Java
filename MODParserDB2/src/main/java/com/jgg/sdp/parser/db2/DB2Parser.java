package com.jgg.sdp.parser.db2;


import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.lang.*;

import java_cup.runtime.*;

import static com.jgg.sdp.common.ctes.CDG.*;

public class DB2Parser extends DB2Base {

	private DMLParser dml   = null;
	private DDLParser ddl   = null;
	private DCLParser dcl   = null;
	private TCLParser tcl = null;
	private PCLParser pcl = null;
	
	public DB2Parser(Scanner sc) {
		setParser(sc, null);		
	}
	
	public DB2Parser(Scanner sc, SymbolFactory sf) {
//		dialecto = getLexerType(file.getWord(1));
//		Scanner sc = getLexer(dialecto, file);
		setParser(sc, sf);		
	}
	 
	public void setParser(Scanner sc, SymbolFactory sf) {
		switch (dialecto) {
		   case STMT_SQL_DML:  dml = new DMLParser(sc); break;		
		   case STMT_SQL_DDL:  ddl = new DDLParser(sc); break;
		   case STMT_SQL_DCL:  dcl = new DCLParser(sc); break;		   
		   case STMT_SQL_TCL:  tcl = new TCLParser(sc); break;
		   case STMT_SQL_PCL:  pcl = new PCLParser(sc); break;
		}
	}

	public Symbol parse() throws Exception {
		switch(dialecto) {
		   case STMT_SQL_DML:       return dml.parse();		
		   case STMT_SQL_DDL:       return ddl.parse();
		   case STMT_SQL_DCL:       return dcl.parse();		   
		   case STMT_SQL_TCL:       return tcl.parse();	
		   case STMT_SQL_PCL:       return pcl.parse();		   
		}
		
		return null;
	}

	public Scanner getLexer(int type, Source src) {
		switch (type) {
		   case STMT_SQL_DML: return new DMLLexer(src);
		   case STMT_SQL_DCL: return new DCLLexer(src);
		   case STMT_SQL_DDL: return new DDLLexer(src);
		   case STMT_SQL_TCL: return new TCLLexer(src);
		   case STMT_SQL_PCL: return new PCLLexer(src);
		   default: return null;
		}
	}

}
