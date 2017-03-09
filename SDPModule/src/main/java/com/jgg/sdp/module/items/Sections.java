/**
 * Mantiene el control de las secciones declaradas en el programa
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import com.jgg.sdp.core.ctes.CDG;

public class Sections {

	int sects[][] = new int[CDG.DIV_COUNT][CDG.SECT_COUNT];

	public int getIdentification() { return getDivision(CDG.SECT_ID);   }
	public int getEnvironment()    { return getDivision(CDG.SECT_ENV);  }
	public int getData()           { return getDivision(CDG.SECT_DATA); }
	public int getProcedure()      { return getDivision(CDG.SECT_PRC);  }

	public int getConfiguration()  { return getSection(CDG.SECT_CONF);  }
	public int getInputOutput()    { return getSection(CDG.SECT_IO);    }
	public int getFile()           { return getSection(CDG.SECT_FILE);  }
	public int getWorkingStorage() { return getSection(CDG.SECT_WORK);  }
	public int getLocalStorage()   { return getSection(CDG.SECT_LOCAL); }
	public int getLinkage()        { return getSection(CDG.SECT_LINK);  }
	
	public boolean hasDataDivision()   { return getDivision(CDG.SECT_DATA) != 0; }	
	public boolean hasWorkingStorage() { return getSection(CDG.SECT_WORK)  != 0; }
	public boolean hasLocalStorage()   { return getSection(CDG.SECT_LOCAL) != 0; }
	public boolean hasLinkage()        { return getSection(CDG.SECT_LINK)  != 0; }
	
	
	private int getDivision(int cdg) { 
		return sects[cdg / 10][0]; 
	}
	private int getSection(int cdg)  { 
		return sects[cdg / 10][cdg % 10]; 
	}

	
	public void setSectionOrDivision(int cdg, int line) {
		sects[cdg / 10][cdg % 10] = line;
	}
}
