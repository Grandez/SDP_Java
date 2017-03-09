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

import com.jgg.sdp.parser.lang.*;

import java_cup.runtime.Scanner;

public class FactoryParser {
	
	public static GenericParser getParser(GenericScanner lexer, int type) {

		Scanner l = (Scanner) lexer;
		
		switch (type) {
		   case Parsers.ZCOBOL:     return new GenericParser(new ZCData(l), type);
		   case Parsers.ZCOBOLDATA: return new GenericParser(new ZCData(l), type);
		   case Parsers.ZCOBOLCODE: return new GenericParser(new ZCCode(l), type);
//		   case Parsers.OCOBOL:     return new GenericParser(new OCParser(l), type);
		   case Parsers.DB2:        return new GenericParser(new DB2Parser(l), type);
		   case Parsers.CICS:       return new GenericParser(new CICSParser(l), type);
		   case Parsers.COPY:       return new GenericParser(new COPYParser(l), type);
//		   case Parsers.TEST:       return new GenericParser(new TestParser(l), type);
		}
		
		return null;
	}

}
