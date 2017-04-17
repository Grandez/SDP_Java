package com.jgg.sdp.parser.base;

import java_cup.runtime.*;

public interface GenericScanner extends Scanner {
	
	  public Symbol next_token() throws java.io.IOException, ParseException;

}
