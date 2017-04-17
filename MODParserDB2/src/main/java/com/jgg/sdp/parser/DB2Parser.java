package com.jgg.sdp.parser;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.lang.*;

import java_cup.runtime.*;

public class DB2Parser extends DB2Base {

	private DMLParser dml   = null;
	private DDLParser ddl   = null;
	private DCLParser dcl   = null;
	private TCLParser tcl = null;
	private PCLParser pcl = null;
	
	public DB2Parser(Scanner sc) {
//		dialecto = getLexerType(file.getWord(1));
//		Scanner sc = getLexer(dialecto, file);
		setParser(sc);		
	}
	 
	public void setParser(Scanner sc) {
		switch (dialecto) {
		   case DB2ParserTypes.DML:  dml = new DMLParser(sc); break;		
		   case DB2ParserTypes.DDL:  ddl = new DDLParser(sc); break;
		   case DB2ParserTypes.DCL:  dcl = new DCLParser(sc); break;		   
		   case DB2ParserTypes.TCL:  tcl = new TCLParser(sc); break;
		   case DB2ParserTypes.PCL:  pcl = new PCLParser(sc); break;
		}
	}

	public Symbol parse() throws Exception {
		switch(dialecto) {
		   case DB2ParserTypes.DML:       return dml.parse();		
		   case DB2ParserTypes.DDL:       return ddl.parse();
		   case DB2ParserTypes.DCL:       return dcl.parse();		   
		   case DB2ParserTypes.TCL:       return tcl.parse();	
		   case DB2ParserTypes.PCL:       return pcl.parse();		   
		}
		
		return null;
	}

	public Scanner getLexer(int type, Source src) {
		switch (type) {
		   case DB2ParserTypes.DML: return new DMLLexer(src);
		   case DB2ParserTypes.DCL: return new DCLLexer(src);
		   case DB2ParserTypes.DDL: return new DDLLexer(src);
		   case DB2ParserTypes.TCL: return new TCLLexer(src);
		   case DB2ParserTypes.PCL: return new PCLLexer(src);
		   default: return null;
		}
	}

}
