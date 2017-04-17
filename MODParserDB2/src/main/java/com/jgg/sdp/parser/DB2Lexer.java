package com.jgg.sdp.parser;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.base.GenericLexer;
import com.jgg.sdp.parser.lang.*;

public class DB2Lexer extends DB2Base {

	public static GenericLexer getLexer(Source src) {
		switch (getLexerType(src.getWord(1))) {
		   case DB2ParserTypes.DML: return new DMLLexer(src);
		   case DB2ParserTypes.DCL: return new DCLLexer(src);
		   case DB2ParserTypes.DDL: return new DDLLexer(src);
		   case DB2ParserTypes.TCL: return new TCLLexer(src);
		   case DB2ParserTypes.PCL: return new PCLLexer(src);
		   default: return null;
		}
	}
}
