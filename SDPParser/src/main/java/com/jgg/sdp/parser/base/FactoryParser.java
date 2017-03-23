/**
 * Factoria para obtener el analizador sintactico adecuado al 
 * dialecto COBOL
 *  
 * Actualmente solo devuelve el analizador sintactico de OpenCOBOL
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.cics.lang.CICSLexer;
import com.jgg.sdp.parser.cics.lang.CICSParser;
import com.jgg.sdp.parser.cobol.lang.*;
import com.jgg.sdp.parser.copy.lang.COPYLexer;
import com.jgg.sdp.parser.copy.lang.COPYParser;
import com.jgg.sdp.parser.db2.DB2Lexer;
import com.jgg.sdp.parser.db2.DB2Parser;

import java_cup.runtime.Scanner;

public class FactoryParser {

	private static Scanner cblLexer;
	
	public static Scanner getLexer(Source src, int type) {
		
		switch (type) {
		   case Parsers.COBOL: 
			    if (cblLexer == null) cblLexer = new ZCLexer(src);
		        return cblLexer;
		   case Parsers.DB2:  
			    return DB2Lexer.getLexer(src);
		   case Parsers.COPY:  
			    return new COPYLexer(src);
		   case Parsers.CICS:  
			    return new CICSLexer(src);

		   default:                
			   System.out.println("Parate");         
		}
		return null;
	}
	
	public static GenericParser getParser(Scanner lexer, Source source, int type) {

		switch (type) {
		   case Parsers.ZCOBOL:     return new GenericParser(new ZCData(lexer), type);
		   case Parsers.ZCOBOLDATA: return new GenericParser(new ZCData(lexer), type);
		   case Parsers.ZCOBOLCODE: return new GenericParser(new ZCCode(lexer), type);
		   case Parsers.DB2:        return new GenericParser(new DB2Parser(lexer), type);
		   case Parsers.CICS:       return new GenericParser(new CICSParser(lexer), type);
		   case Parsers.COPY:       return new GenericParser(new COPYParser(lexer), type);
		}
		
		return null;
	}

}
