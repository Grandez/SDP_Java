/**
 * Factoria para obtener el analizador lexico adecuado al 
 * dialecto COBOL
 *  
 * Actualmente solo devuelve el analizador lexico de OpenCOBOL
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.parser.lang.*;

public class FactoryLexer {

	public static ILexer getLexer(Source source, int type) {
		
		switch (type) {
		   case Parsers.ZCOBOL:     return new ZCLexer(source);
		   case Parsers.DB2:        return new DB2Lexer(source);
		   case Parsers.CICS:       return new CICSLexer(source);	
//		   case Parsers.TEST:   return new TestLexer(source);		   
//		   case Parsers.CALL:  return new CallLexer(source);
		   default: throw new SDPException(MSG.EXCEP_NO_PARSER);
		}
		
//		return null;
		
		// if (lang.compareToIgnoreCase(CFG.LANG_OPEN_COBOL) == 0) return new OCLexer(source);
		// if (lang.compareToIgnoreCase(CFG.LANG_IBM_COBOL)  == 0) return new ECLexer(source);

		// throw new SDPException(MSG.EXCEP_NO_LANG, lang);
	}
}
