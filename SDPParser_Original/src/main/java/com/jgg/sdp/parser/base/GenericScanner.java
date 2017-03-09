package com.jgg.sdp.parser.base;

import java_cup.runtime.*;

public interface GenericScanner extends Scanner {
	
//	public Symbol tokenCobol() throws java.lang.Exception;
	public Symbol tokenCobolData() throws java.lang.Exception;
	public Symbol tokenCobolCode() throws java.lang.Exception;	
	public Symbol tokenSQL  () throws java.lang.Exception;
	public Symbol tokenCICS () throws java.lang.Exception;
	public Symbol tokenCOPY () throws java.lang.Exception;
	
}
