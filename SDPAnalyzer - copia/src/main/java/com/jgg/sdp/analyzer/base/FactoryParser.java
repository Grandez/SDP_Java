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

import com.jgg.sdp.parser.*;
import com.jgg.sdp.parser.lang.*;

import java_cup.runtime.Scanner;

public class FactoryParser {

	public static GenericParser getParser(Scanner lexer, int type) {
        Scanner scanner = (Scanner) lexer;
		switch (type) {
		   case Parsers.ZCOBOLDATA: return new GenericParser(new ZCDParser (scanner), type);
		   case Parsers.ZCOBOLCODE: return new GenericParser(new ZCCParser (scanner), type);
		   case Parsers.DB2:        return new GenericParser(new DB2Parser (scanner), type);
		   case Parsers.CICS:       return new GenericParser(new CICSParser(scanner), type);
		   case Parsers.COPY:       return new GenericParser(new COPYParser(scanner), type);
		}
		
		return null;
	}

}
