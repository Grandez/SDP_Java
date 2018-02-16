package com.jgg.sdp.parser.db2;

import static com.jgg.sdp.common.ctes.CDG.*;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.base.GenericLexer;
import com.jgg.sdp.parser.lang.*;

public class DB2Lexer extends DB2Base {

	public static GenericLexer getLexer(Source src) {
		switch (getLexerType(src.getWord(1))) {
		   case STMT_SQL_DML: return new DMLLexer(src);
		   case STMT_SQL_DCL: return new DCLLexer(src);
		   case STMT_SQL_DDL: return new DDLLexer(src);
		   case STMT_SQL_TCL: return new TCLLexer(src);
		   case STMT_SQL_PCL: return new PCLLexer(src);
		   default: return null;
		}
	}
}
