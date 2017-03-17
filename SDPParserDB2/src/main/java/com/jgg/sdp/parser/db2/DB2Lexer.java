package com.jgg.sdp.parser.db2;

import java.util.Properties;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.db2.base.DB2Parsers;
import com.jgg.sdp.parser.db2.lang.*;

import java_cup.runtime.Scanner;

public class DB2Lexer {

	private static Properties verbs = null;
	
	public static Scanner getLexer(Source src) {
		switch (getLexerType(src)) {
		   case DB2Parsers.DML: return new DMLLexer(src);
		   case DB2Parsers.DCL: return new DCLLexer(src);
		   case DB2Parsers.DDL: return new DDLLexer(src);
		   case DB2Parsers.TCL: return new TCLLexer(src);
		   case DB2Parsers.PCL: return new PCLLexer(src);
		   default: return null;
		}
	}

	private static int getLexerType(Source src) {		
		if (verbs == null) loadVerbs();
		String wrd = src.getWord(1);
		System.out.print("\t" + wrd + "\t");
		Object sqlType = verbs.get(wrd);
		if (sqlType == null) return 0;
        return Integer.parseInt((String) sqlType);
	}

	private static void loadVerbs() {
		verbs = new Properties();
		try {
		   verbs.load(Thread.currentThread().getClass().getResourceAsStream("sql.properties"));
		} catch (Exception e) {
			System.exit(16);
		}
	}

}
