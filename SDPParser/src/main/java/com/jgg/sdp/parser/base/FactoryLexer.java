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
import com.jgg.sdp.parser.cics.lang.CICSLexer;
import com.jgg.sdp.parser.cobol.lang.ZCLexer;


public class FactoryLexer {

	public static ILexer getLexer(Source source, int type) {
		
		switch (type) {
//		   case PARSER.PROXY:      return new ProxyLexer(source);
		   //case PARSER.ZCOBOL:     return new ZCLexer(source);
//		   case PARSER.DB2:        return new SQLLexer(source);
//		   case PARSER.CICS:       return new CICSLexer(source);	
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
