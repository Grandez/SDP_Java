package com.jgg.sdp.parser.db2;

import java.util.Properties;

import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.common.exceptions.SDPException;
import com.jgg.sdp.parser.base.NotSupportedException;

public abstract class DB2Base {

	protected static Properties verbs = null;
	protected static int        dialecto = 0;
	

	
	protected static void loadVerbs() {
		String props = "sql.properties";
		verbs = new Properties();
		try {
		   verbs.load(DB2Lexer.class.getClassLoader().getResourceAsStream(props));
		} catch (Exception e) {
			throw new SDPException(MSG.FATAL_EXCEPTION, props);
		}
	}
	
	protected static int getLexerType(String wrd) {		
		if (verbs == null) loadVerbs();
		Object sqlType = verbs.get(wrd);
		if (sqlType == null) { 
			throw new NotSupportedException(MSG.EXCEPTION_DB2_VERB, wrd);
	    }
		dialecto = Integer.parseInt((String) sqlType); 
        return dialecto;
	}


}
