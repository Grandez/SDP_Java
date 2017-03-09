/**
 * Encapsula la información resumida del modulo
 *   
 * Lineas, sentencias, comentarios, etc.
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.base;

public class Summary {

	private final static int DATOS   =  0;
	private final static int CONTROL =  1;
	private final static int IO      =  2;
	private final static int OLD     =  3;
	private final static int FLUJO   =  4;
	private final static int ARIT    =  5;
	private final static int OTRO    =  6;
	private final static int LANG    =  9;
	private final static int OTH     = 10;
	
	private int lines      = 0;
	private int blanks     = 0;
	private int comments   = 0;
	private int decorators = 0;
	private int verbs      = 0;
	
	private int[] stmts = new int[20];
		
	private boolean hasFiles = false;
	
	public void incLines(boolean data)      { 
		lines++;
		if (data == false) incBlanks();
	}
	
	public void incVerbs()      { verbs++;      }
	public void incBlanks()     { blanks++;     }
	public void incDecorators() { decorators++; }

	public void    setFiles()   { hasFiles = true;   }
	public boolean getFiles()   { return hasFiles;   }
	
	public int getLines()       { return lines;      }
	public int getBlanks()      { return blanks;     }
	public int getComments()    { return comments;   }
	public int getDecorators()  { return decorators; }
	public int getVerbs()       { return verbs;      }

	public int getAllVerbs()    { 
		   int all = 0;
		   for (int i = 0; i < stmts.length; i++ ) { all += stmts[i]; }
		   return all;
	}

	public void incComments(boolean data)   {
		incLines(true);
		comments++;
		if (data == false) incDecorators();
	}

	public void incDecoratorsOrComments(boolean isDecorator) {
		if (isDecorator) {
			decorators++;
		}
		else {
			comments++;
		}
	}

	public void incStmtDatos()   { incStatement(DATOS);   }
	public void incStmtControl() { incStatement(CONTROL); }
    public void incStmtIO()      { incStatement(IO);      }
    public void incStmtOld()     { incStatement(OLD);     }
    public void incStmtFlujo()   { incStatement(FLUJO);   }
    public void incStmtArit()    { incStatement(ARIT);    }
    public void incStmtOtras()   { incStatement(OTRO);    }
    public void incStmtLang()    { incStatement(LANG);    }
    public void incStmtOth()     { incStatement(OTH);     }
    
	public int  getStmtDatos()   { return stmts[DATOS];   }
	public int  getStmtControl() { return stmts[CONTROL]; }
    public int  getStmtIO()      { return stmts[IO];      }
    public int  getStmtOld()     { return stmts[OLD];     }
    public int  getStmtFlujo()   { return stmts[FLUJO];   }
    public int  getStmtArit()    { return stmts[ARIT];    }
    public int  getStmtOtras()   { return stmts[OTRO];    }
    public int  getStmtLang()    { return stmts[LANG];    }
    public int  getStmtOth()     { return stmts[OTH];     }

	private void incStatement(int tipo) {
		stmts[tipo] += 1;
	}
}
