package com.jgg.sdp.module.graph;

public enum Nodes {
    PGMBEG, PGMEND,   // Usado a nivel programa 
    BEGIN, END,       // subgrafo virtual
    NODE,             // Nodo real
    BLOCK,            // Bucle
    IF, EVALUATE, SEARCH,
    ELSE, WHEN, OTHER, // Ramas
    CHOICE, BRANCH,
    PERFORM, CALL_STATIC, CALL_DYNAMIC;

	public static Nodes getType(Nodes type) {
		switch(type) {
		   case PERFORM: return NODE;
		   case IF:
		   case EVALUATE: 
		   case SEARCH:  return CHOICE;
		   case ELSE:   
		   case WHEN:   return BRANCH;
		   default: return type;
		}
	}
	
	public boolean isItem() {
		return this == NODE;
	}
	
	public boolean isVirtual() {
		switch(this) {
		   case BEGIN: return true;
		   case END: return true;
		   default: return false;
		}
	}
}
