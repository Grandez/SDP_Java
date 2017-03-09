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

import com.jgg.sdp.core.ctes.CDG;

public class Summary {


	private int     memoria = 0;
	private boolean allocate = false;
	
	private boolean cics    = false;  // Tiene CICS?
	private boolean sql     = false;  // Tiene SQL?
	private boolean mq      = false;  // Tiene MQSeries
	private boolean file    = false;  // Tiene ficheros?
	private int     callCount  = 0;      // Modulos llamados
	private int     callMode   = 0;      // Modo de llamada	
	private int     callType   = 0;      // Tipo de llamada	
	
	private int     tsMode = 0;
	private int     missing = 0;
	
	public void setCICS()        { cics = true;    }
	public void setSQL()         { sql  = true;    }
	public void setMQ()          { mq   = true;    }
	public void setFile()        { file = true;    }

	
	public void setMissing(int e) { missing = e; }
	public int  getMissing()      { return missing; }	
	
	public void setTS(int value)  {tsMode |= value;    }
	
	public boolean hasFile()      { return file;  }
	public boolean hasSQL()       { return sql;   }
	public boolean hasCICS()      { return cics;  }
	public boolean hasMQ()        { return mq;    }

	public void setCallCount(int c)  { callCount |= c;   }	
	public void setCallMode(int c)   { callMode  |= c;   }
	public void setCallType(int d)   { callType  |= d;   }
	public int  getCallCount()       { return callCount; }	
	public int  getCallMode()        { return callMode;  }
	public int  getCallType()        { return callType;  }
	
	public boolean isThreadsafe() { return (tsMode == CDG.TS_YES || tsMode == CDG.TS_UNDEF); }
	

	public int getTSMode()      { return tsMode;     }
	
	public void setMemory(int memoria) { this.memoria = memoria; } 
	public int  getMemory()            { return memoria; }
	
	public void    setAllocate()        { allocate = true; }
	public boolean getAllocate()        { return allocate; }

}
