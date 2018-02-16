package com.jgg.sdp.blocks.reflect;

public class Reflect {

    protected int begLine   = -1;
    protected int endLine   = -1;
    protected int begColumn = -1;
    protected int endColumn = -1;
	
	public  String getValue()     { return "";        }
	public int    getBegLine()    { return begLine;   }
	public int    getBegColumn()  { return begColumn; }
	public int    getEndLine()    { return endLine;   }
	public int    getEndColumn()  { return endColumn; }
	public int    getLines()      { return endLine - begLine + 1; }
	
    protected void setLines(boolean begin, Reflect obj) {
    	if (begin) {
    	    begLine   = obj.getBegLine();
    	    begColumn = obj.getBegColumn();
    	}
    	endLine   = obj.getEndLine();
    	endColumn = obj.getEndColumn();
    }
    
    protected void setLines(Reflect beg, Reflect end) {
   	    begLine   = beg.getBegLine();
   	    begColumn = beg.getBegColumn();
    	endLine   = end.getEndLine();
    	endColumn = end.getEndColumn();
    }

}
