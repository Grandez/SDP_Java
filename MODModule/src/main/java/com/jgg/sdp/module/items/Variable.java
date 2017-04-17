/**
 * Implementa un objeto Variable con informacion acerca de su uso<br> 
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.core.ctes.VAR;

public class Variable {
	
	private String   name;
	private Variable root  = null;   // Nivel 01
	private Variable padre = null;   // Nivel padre
	private Integer  nivel;          // Nivel variable
	private int      section  = 0;   // Seccion donde se ha declarado
	private String   module;         // Modulo donde esta declarado
    private Integer  minItems = 1;   // Caso tablas, longitud minima
    private Integer  maxItems = 1;   // Caso tablas, longitud maxima
    private Integer  size     = 0;   // Longitud logica
    private Integer  memory   = 0;   // Longitud en memoria
    private Integer  type     = VAR.DISPLAY;

	private Integer line;
	private Integer column;
	
	private boolean initialized = false;
    
	private int      numWrites = 0;  // Numero de escrituras
	
	private ArrayList<Variable> hijos    = new ArrayList<Variable>();
	private ArrayList<Variable> hermanos = new ArrayList<Variable>();

	// Lista de valores asignados
	private HashSet<String>  values = new HashSet<String>();

	// Lista de variables asignadas 
	private HashSet<String>  vars   = new HashSet<String>();

	//   W    R
	// +----+----+
	// |XXXX|XXXX|
	// +----+----+
	// 0x01 Se lee/escribe
	// 0x02 Se lee/escribe alguno de sus hijos
	// 0x04 Se lee/escribe en un nivel superior

	
	private int  accRead   = 0x0; // Marcara de acceso  
	private int  accWrite  = 0x0; // Marcara de acceso	
	
	public Variable(String level, String name) {
		nivel = Integer.parseInt(level.trim());
		this.name = name;
		if (nivel == 01) root = this;
		if (nivel == 77) root = this;
	}
	
	public void setLine   (int line)    { this.line    = line;   }
	public void setColumn (int column)  { this.column  = column; }
	public void setPadre  (Variable v)  { this.padre   = v;      }
    public void setRoot   (Variable v)  { this.root    = v;      }	

    public void setType   (int type)    { this.type    = type;   }    
    public void setSize   (int size)    { this.size    = size;   }
    public void setSection(int sect)    { this.section = sect;   }
    public void setModule (String m)    { this.module  = m;      }
    
    public void setRead   (int r)       { this.accRead  |= r;      }    
    public void setRead   ()            { this.accRead  |= 0x01;   }
    public void setWrite   (int r)      { this.accWrite |= r;      }    
    public void setWrite  ()            { this.accWrite |= 0x01;   }
    public void setWriteIndirect()      { this.accWrite |= 0x04;   }
    public void setUsedRead     ()      { this.accRead  |= 0x02;   }
    public void setUsedWrite    ()      { this.accWrite |= 0x02;   }    
    
    public int  getRead()               { return accRead;          }
    public int  getWrite()              { return accWrite;         }
    
    public void setValue    (String value)  { this.values.add(value); }
    public void setVariable (String value)  { this.vars.add(value);   }
    
    public void setInitValue(String val) {
    	this.values.add(val);
    	this.initialized = true;
    }

    public boolean isInitialized()     { return initialized;          }
    public boolean isUsed()            { return (accRead != 0x0 | accWrite != 0x0); }    
    public boolean isRead()            { return (accRead  & 0x01) != 0x0; }
    public boolean isWritten()         { return (accWrite & 0x05) != 0x0; }
    public boolean isDirectWritten()   { return (accWrite & 0x01) != 0x0; }
    public boolean isIndirectWritten() { return (accWrite & 0x04) != 0x0; }

    public void addSize(int size)      { this.size += size;           }
    
    public void addWrite()             { numWrites++;                 }
    public int getNumwrites()          { return numWrites;            }
    
	public void addHijo(Variable v) {
		hijos.add(v);
		v.setPadre(this);
	}

	public void addHermano(Variable v) {
		if (hermanos == null) hermanos = new ArrayList<Variable>();
		hermanos.add(v);
	}
	
	public int      getLine()    { return line;    }
	public int      getColumn()  { return column;  }
	public int      getLevel()   { return nivel;   }
	public int      getSection() { return section; }
	public Variable getRoot()    { return root;    }
	public String   getName()    { return name;    }
	public String   getModule()  { return module;  }
	
	public Variable getParent()  { return padre;   }
	
	public ArrayList<Variable> getHijos()    { return hijos;    }
	public ArrayList<Variable> getHermanos() { return hermanos; }

	public List<String>   getVariables() { return new ArrayList<String>(vars);   }

	public List<String>   getValues()    {
		
		return new ArrayList<String>(values); 
	}	
	/**
     * Indica cual es su variable padre<br>
     * Niveles no 01
     *
     * @param p La variable Padre
     * @return La variable p
     */
	public Variable addPadre(Variable p) {
		padre = p;
		return p;
	}

	public void setBounds(int min, int max) {
		minItems = min;
		maxItems = max;
	}
	
	public void setMemory(int memory) {
		this.memory = memory;
	}
	
	public int calculateMemory() {
		if (section == CDG.SECT_LINK) {
			memory = SYS.POINTER;
			return memory;
		}
		
		memory = size;
		
		switch (type) {
		   case VAR.POINTER: memory = SYS.POINTER;    break; 
		   case VAR.COMP1:   memory = SYS.POINTER;    break;
		   case VAR.COMP2:   memory = 8;              break;
		   case VAR.COMP3:   memory = (size + 1) / 2; break;
		   case VAR.COMP4:
		   case VAR.COMP5:
           case VAR.BINARY:			   
			                 if (size > 9) {
			                     memory = 8;
		                     } 
		                     else if (size > 4) {
		                    	 memory = 4;
		                     }
		                     else {
		                    	 memory = 2; 
		                     }
                             break;		   
		}
		return memory * (maxItems - minItems + 1);
	}
}
