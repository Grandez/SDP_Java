package com.jgg.sdp.module.graph;

public class NodeTypes {

    public final static int PGMBEG    = 1;
    public final static int PGMEND    = 2; 
    public final static int BEGIN     = 11;
    public final static int END       = 12;
    public final static int NODE      = 21;
    public final static int BLOCK     = 22;
    public final static int CODE      = 23;
    public final static int PARAGRAPH = 31;
    public final static int IF        = 40;
    public final static int IF_BEG    = 41;
    public final static int IF_END    = 42;
    public final static int IF_LEFT   = 43;
    public final static int IF_RIGHT  = 44;
    public final static int EVALUATE  = 52;
    public final static int SEARCH    = 53;
    public final static int ELSE      = 54;
    public final static int WHEN      = 45;
    public final static int OTHER     = 46;
    public final static int CHOICE    = 47;
    public final static int BRANCH    = 48;   
    public final static int PERFORM   = 51;

    public final static int ROOT       = 10;
    public final static int COBOL      = 11;
    public final static int CICS       = 12;
    public final static int TRAN       = 13;
    public final static int GRAL       = 15;    
    
//    public final static int CALL_STATIC  = 61;
//    public final static int CALL_DYNAMIC = 62;
//    public final static int CALL_LINK    = 63;
//    public final static int CALL_XCTL    = 64;
//    public final static int CALL_TRAN    = 66;
//    public final static int TRX          = 70;
    
    public final static int B_START  = 1;
    public final static int B_END    = 2;
    public final static int B_LEFT   = 0;
    public final static int B_RIGHT  = 1;
    
    public static String getNodeName(int type) {
    	String txt;
    	switch (type) {
    	   case PGMBEG      : txt = "PGMBEG";        break;
    	   case PGMEND      : txt = "PGMEND";        break;
    	   case BEGIN       : txt = "BEGIN";         break;
    	   case END         : txt = "END";           break;
    	   case NODE        : txt = "NODE";          break;
    	   case BLOCK       : txt = "BLOCK";         break;
    	   case CODE        : txt = "CODE";          break;
    	   case PARAGRAPH   : txt = "PARAGRAPH";     break;
    	   case IF          : txt = "IF";            break;
    	   case EVALUATE    : txt = "EVALUATE";      break;
    	   case SEARCH      : txt = "SEARCH";        break;
    	   case ELSE        : txt = "ELSE";          break;
    	   case WHEN        : txt = "WHEN";          break;
    	   case OTHER       : txt = "OTHER";         break;
    	   case CHOICE      : txt = "CHOICE";        break;
    	   case BRANCH      : txt = "BRANCH";        break;
    	   case PERFORM     : txt = "PERFORM";       break;
    	   default: txt = "NODE";
    	}
    	return txt;
    }
}
