package com.jgg.sdp.module.graph;

public class NodeTypes {

    public final static int PGMBEG    = 1;   // Inicio Grafo
    public final static int PGMEND    = 2;   // Fin Grafo
    public final static int BLOCK     = 10;  // Bloque de codigo
    public final static int PARAGRAPH = 20;
    
    public final static int BEGIN     = 11;
    public final static int END       = 12;
    public final static int NODE      = 21;

    
    public final static int IF         = 40;
    public final static int BRANCH_BEG = 41;
    public final static int BRANCH_END = 42;
    public final static int IF_LEFT    = 44;
    public final static int IF_RIGHT   = 45;
    public final static int ELSE       = 46;

    public final static int EVALUATE   = 50;
    public final static int EVAL_BEG   = 51;
    public final static int EVAL_END   = 52;
    public final static int EVAL_WHEN  = 53;
    public final static int EVAL_OTHER = 54;

    public final static int SEARCH    = 53;    
    public final static int WHEN      = 55;
    public final static int OTHER     = 56;
    public final static int CHOICE    = 57;
    public final static int BRANCH    = 58;   
    public final static int PERFORM   = 30;

    
    public final static int ROOT       = 10;
    public final static int COBOL      = 11;
    public final static int CICS       = 12;
    public final static int TRAN       = 13;
    public final static int GRAL       = 15;    
    
    public final static int EDGE_NORMAL = 0;
    public final static int EDGE_IF     = 1;
    public final static int EDGE_ELSE   = 2;
        
    public final static int B_START  = 1;
    public final static int B_END    = 2;
    public final static int B_LEFT   = 0;
    public final static int B_RIGHT  = 1;
    
    public static String getNodeName(int type) {
    	String txt;
    	switch (type) {
 	       case IF          : txt = "IF";            break;
    	   case EVALUATE    : txt = "EVAL";          break;
    	   case SEARCH      : txt = "SEARCH";        break;
    	   case ELSE        : txt = "ELSE";          break;
    	   case WHEN        : txt = "WHEN";          break;
    	   case OTHER       : txt = "OTHER";         break;
    	   case PGMBEG      : txt = "PGMBEG";        break;
    	   case PGMEND      : txt = "PGMEND";        break;
    	   case BEGIN       : txt = "BEGIN";         break;
    	   case END         : txt = "END";           break;
    	   case NODE        : txt = "NODE";          break;
    	   case BLOCK       : txt = "BLOCK";         break;
    	   case PARAGRAPH   : txt = "PARAGRAPH";     break;
    	   case CHOICE      : txt = "CHOICE";        break;
    	   case BRANCH      : txt = "BRANCH";        break;
    	   case PERFORM     : txt = "PERFORM";       break;
    	   default: txt = "NODE";
    	}
    	return txt;
    }
}
