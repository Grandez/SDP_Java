package com.jgg.sdp.parser.db2;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.db2.base.DB2Parsers;
import com.jgg.sdp.parser.db2.lang.*;

import java_cup.runtime.Scanner;

public class DB2Lexer extends DB2Base {

	public static Scanner getLexer(Source src) {
		switch (getLexerType(src.getWord(1))) {
		   case DB2Parsers.DML: return new DMLLexer(src);
		   case DB2Parsers.DCL: return new DCLLexer(src);
		   case DB2Parsers.DDL: return new DDLLexer(src);
		   case DB2Parsers.TCL: return new TCLLexer(src);
		   case DB2Parsers.PCL: return new PCLLexer(src);
		   default: return null;
		}
	}
}
