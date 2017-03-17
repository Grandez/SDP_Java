/**
 * Chapu para implementar una pseudo factoria
 * Hay que meter cada parser en su objeto
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;


import com.jgg.sdp.parser.cics.lang.*;
import com.jgg.sdp.parser.cobol.lang.*;
import com.jgg.sdp.parser.db2.DB2Parser;
import com.jgg.sdp.parser.copy.lang.*;

import java_cup.runtime.Symbol;

public class GenericParser {

	private int dialecto = 0;
	private ZCData   zcd   = null;
	private ZCCode   zcc   = null;
	private DB2Parser  sql  = null;
	private CICSParser cics = null;
	private COPYParser copy = null;
	
	public GenericParser(Object parser, int type) {
		dialecto = type;
		if (parser instanceof ZCData)       this.zcd  = (ZCData) parser;
		if (parser instanceof ZCCode)       this.zcc  = (ZCCode) parser;
		if (parser instanceof DB2Parser)    this.sql  = (DB2Parser)    parser;
		if (parser instanceof CICSParser)   this.cics = (CICSParser)   parser;		
		if (parser instanceof COPYParser)   this.copy = (COPYParser)   parser;			
	}

	public Symbol parse() throws Exception {
		switch(dialecto) {
		   case Parsers.ZCOBOL:     return zcd.parse();
		   case Parsers.ZCOBOLDATA: return zcd.parse();
		   case Parsers.ZCOBOLCODE: return zcc.parse();
		   case Parsers.DB2:        return sql.parse();
		   case Parsers.CICS:       return cics.parse();
		   case Parsers.COPY:       return copy.parse();	
		}
		
		return null;
	}
}
