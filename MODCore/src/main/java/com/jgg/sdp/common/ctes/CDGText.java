package com.jgg.sdp.common.ctes;

import static com.jgg.sdp.common.ctes.CDG.*;

public class CDGText {

	public static String getStmtGroupName(int group) {
		return getStmtName(group);
	}
	public static String getStmtSubGroupName(int group) {
		return getStmtName(group);
	}
	
	private static String getStmtName(int group) {
		switch (group) {
           case STMT_COBOL:   return "COBOL";    
           case STMT_DATA:    return "DATA";
           case STMT_CONTROL: return "CONTROL";
           case STMT_FLOW:    return "FLOW";
           case STMT_ARIT:    return "ARIT";
           case STMT_IO:      return "IO";
           case STMT_LANG:    return "LANG";    
           case STMT_CICS:    return "CICS";
           case STMT_SQL:     return "SQL";
           case STMT_DCL:     return "DCL";
           case STMT_DDL:     return "DDL";
           case STMT_DML:     return "DML";
           case STMT_PCL:     return "PCL";
           case STMT_TCL:     return "TCL";
           case STMT_COPY:    return "COPY";
           default:           return "N/A";
		}    
	}
}
