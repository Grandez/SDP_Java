package com.jgg.sdp.parser.base;

import java_cup.runtime.*;

public interface GenericScanner extends Scanner {
	
	  public Symbol tokenCobolData() throws java.io.IOException, ParseException;
	  public Symbol tokenCobolCode() throws java.io.IOException, ParseException;

}
