package com.jgg.sdp.module.graph;

public enum Nodes {
    BEGIN, VIRTUAL, BLOCK, IF, EVALUATE, PERFORM, CALL_STATIC, CALL_DYNAMIC, END;

	public boolean isItem() {
		switch(this) {
		   case PERFORM: return true;
		   case CALL_STATIC: return true;
		   case CALL_DYNAMIC: return true;
		   default: return false;
		}
	}
}
