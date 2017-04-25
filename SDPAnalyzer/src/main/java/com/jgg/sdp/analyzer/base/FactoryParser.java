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
package com.jgg.sdp.analyzer.base;

import com.jgg.sdp.parser.db2.DB2Parser;
import com.jgg.sdp.parser.lang.*;
import com.jgg.sdp.parser.symbol.SDPSymbolFactory;

import java_cup.runtime.*;

public class FactoryParser {

	public static GenericParser getParser(Scanner lexer, int type) {
        Scanner scanner = (Scanner) lexer;
        SymbolFactory sf = new SDPSymbolFactory();
		switch (type) {
		   case Parsers.ZCOBOLDATA: return new GenericParser(new ZCDParser (scanner), type);
		   case Parsers.ZCOBOLCODE: return new GenericParser(new ZCCParser (scanner), type);
		   case Parsers.DB2:        return new GenericParser(new DB2Parser (scanner), type);
		   case Parsers.CICS:       return new GenericParser(new CICSParser(scanner), type);
		   case Parsers.COPYBOOK:   return new GenericParser(new COPYParser(scanner), type);
		}
		
		return null;
	}

}
