package com.jgg.sdp.module.base;

public class Codigo {

	private final static int DATOS   =  0;
	private final static int CONTROL =  1;
	private final static int IO      =  2;
	private final static int OLD     =  3;
	private final static int FLUJO   =  4;
	private final static int ARIT    =  5;
	private final static int OTRO    =  6;
	private final static int LANG    =  9;
	private final static int CICS    = 10;
	private final static int SQL     = 11;	
	private final static int OTH     = 12;

	private final static int LINES     =  0;
	private final static int BLANKS    =  1;
	private final static int COMMENTS  =  2;
	private final static int DECORATOR =  3;
	private final static int LOOPS     =  4;
	private final static int BLOCKS    =  5;
	private final static int VERBS     =  6;	
    
	private int[] stmts    = new int[20];
	private int[] counters = new int[20];		
    private int   memory   = 0;
	
	public void incLines(boolean data)      { 
		counters[LINES]++;
		if (data == false) incBlanks();
	}
	
	public void incVerbs()         { counters[VERBS]++;     }
	public void incBlanks()        { counters[BLANKS]++;    }
	public void incDecorators()    { counters[DECORATOR]++; }
	public void incLoops()         { counters[LOOPS]++;     }	
	public void incCommentBlocks() { counters[BLOCKS]++;    }	

	public void incComments(boolean data)   {
		incLines(true);
		counters[COMMENTS]++;
		if (data == false) incDecorators();
	}
	
	public int getLines()         { return counters[LINES];     }
	public int getBlanks()        { return counters[BLANKS];    }
	public int getComments()      { return counters[COMMENTS];  }
	public int getDecorators()    { return counters[DECORATOR]; }
	public int getVerbs()         { return counters[VERBS];     }
	public int getLoops()         { return counters[LOOPS];     }	
	public int getCommentBlocks() { return counters[BLOCKS];    }	

	public int getAllVerbs()    { 
	   int all = 0;
	   for (int i = 0; i < stmts.length; i++ ) { all += stmts[i]; }
	   return all;
	}

	public void incDecoratorsOrComments(boolean isDecorator) {
		int idx = (isDecorator) ? DECORATOR : COMMENTS;
		counters[idx]++;
	}

	public void incStmtDatos()   { incStatement(DATOS);   }
	public void incStmtControl() { incStatement(CONTROL); }
    public void incStmtIO()      { incStatement(IO);      }
    public void incStmtOld()     { incStatement(OLD);     }
    public void incStmtFlujo()   { incStatement(FLUJO);   }
    public void incStmtArit()    { incStatement(ARIT);    }
    public void incStmtOtras()   { incStatement(OTRO);    }
    public void incStmtLang()    { incStatement(LANG);    }
    public void incStmtCics()    { incStatement(CICS);    }
    public void incStmtSql()     { incStatement(SQL);     }    
    public void incStmtOth()     { incStatement(OTH);     }
    
	public int  getStmtDatos()   { return stmts[DATOS];   }
	public int  getStmtControl() { return stmts[CONTROL]; }
    public int  getStmtIO()      { return stmts[IO];      }
    public int  getStmtOld()     { return stmts[OLD];     }
    public int  getStmtFlujo()   { return stmts[FLUJO];   }
    public int  getStmtArit()    { return stmts[ARIT];    }
    public int  getStmtOtras()   { return stmts[OTRO];    }
    public int  getStmtLang()    { return stmts[LANG];    }
    public int  getStmtCics()    { return stmts[CICS];    }
    public int  getStmtSql()     { return stmts[SQL];     }    
    public int  getStmtOth()     { return stmts[OTH];     }

	private void incStatement(int tipo) {
		stmts[tipo] += 1;
	}
	
	public void setMemory(int memory) { this.memory = memory; }
	public int  getMemory()           { return memory;        }
	
}
