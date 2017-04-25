/**
 * Chapu para implementar una pseudo factoria
 * Hay que meter cada parser en su objeto
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.analyzer.base;


import com.jgg.sdp.parser.db2.DB2Parser;
import com.jgg.sdp.parser.lang.*;

import java_cup.runtime.Symbol;

import static com.jgg.sdp.analyzer.base.Parsers.*;

public class GenericParser {

	private int dialecto = 0;
	private ZCDParser  zcd  = null;
	private ZCCParser  zcc  = null;
	private DB2Parser  sql  = null;
	private CICSParser cics = null;
	private COPYParser copy = null;
	
	public GenericParser(Object parser, int type) {
		dialecto = type;
		if (parser instanceof ZCDParser)    this.zcd  = (ZCDParser)    parser;
		if (parser instanceof ZCCParser)    this.zcc  = (ZCCParser)    parser;
		if (parser instanceof DB2Parser)    this.sql  = (DB2Parser)    parser;
		if (parser instanceof CICSParser)   this.cics = (CICSParser)   parser;		
		if (parser instanceof COPYParser)   this.copy = (COPYParser)   parser;			
	}

	public Symbol parse() throws Exception {

		switch(dialecto) {
		   case ZCOBOL:     return zcd.parse();
		   case ZCOBOLDATA: return zcd.parse();
		   case ZCOBOLCODE: return zcc.parse();
		   case COPYBOOK:   return copy.parse();
		   case DB2:        return sql.parse();
		   case CICS:       return cics.parse();
		}
		
		return null;
	}
	
}
