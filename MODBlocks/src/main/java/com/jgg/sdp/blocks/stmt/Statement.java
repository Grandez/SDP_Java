/**
 * Implementa un objeto Sentencia <br> 
 * Contiene una lista tokens o una lista statements
 * Pero siempre tiene al menos un token, el verbo y siempre es el primero
 * <br>
 * Una sentencia tiene:
 *     A) un Verbo
 *     B) Un conjunto de lvalues (Values que son modificados)
 *     C) Un conjunto de rvalues (Values que son leidos)
 *     D) Un conjunto de opciones
 *     
 *     Las opciones pueden estar duplicadas, por lo que se mantiene una lista
 *     junto con un map para averiguar si existe
 *     
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.adt.ADTHashDup;
import com.jgg.sdp.blocks.reflect.*;
import com.jgg.sdp.common.ctes.CDGText;
import com.jgg.sdp.parser.symbols.*;

public class Statement<T> implements IStatement {

	private   StringBuilder        text     = new StringBuilder();
    private   SDPSymbol               verbo    = null;
    private   Integer              group    = null;
    private   Integer              subGroup = null;
    private   ArrayList<SDPSymbol> lvalues  = new ArrayList<SDPSymbol>();
    private   ArrayList<SDPSymbol> rvalues  = new ArrayList<SDPSymbol>();

    // Opciones segun aparecen
    protected ArrayList<Option>    lstOptions = new ArrayList<Option>();
    
    // Mapeo para busqueda por nombre
    private ADTHashDup<String, Integer>  mapOptName = new ADTHashDup<String, Integer>();
    // Mapeo para busqueda por SDPSymbol.id
    private ADTHashDup<Integer, Integer> mapOptId = new ADTHashDup<Integer, Integer>();
    
    private String firma = null;
    
    private int id = 0;
    private int orden = -1; 
	private boolean endPoint = false;
 
	public int begLine;
	public int begColumn;
	public int endLine;
	public int endColumn;
	

	/***************************************************************/
	/***     CONSTRUCTORES                                       ***/
	/***************************************************************/
	
	public Statement() {
	}
	
	public Statement(Integer group) {
		this.group = group / 10;
		this.subGroup = group;
	}

	public Statement(Integer group, Integer subGroup) {
		this.group = group;
		this.subGroup = subGroup;
	}
	
	public Statement (SDPSymbol s) {
		verbo     = s;
		begLine   = s.getLine();
		begColumn = s.getColumn();
		endLine   = s.getLine();
		endColumn = s.getColumn() + s.getValue().length();
		id = s.sym;
	}

	public Statement (SDPSymbol s, int count) {
		this(s);
        orden = count;		
	}

	public Statement (SDPSymbol s, SDPSymbol list) {
		this(s);
        addRValue(list);		
	}
	
	public Statement (Statement<T> s) {
		verbo  = s.getVerb();
		id     = s.getId();
		orden  = s.getOrden();
	}

	public Option asOption() {
	    Option opt = new Option(this.verbo);
	    return opt;
	}
	
	/**
	 * Rellena la instruccion con los datos del verbo
	 */
	public void adjust(SDPSymbol last) {

		begLine = verbo.getLine();
		begColumn = verbo.getColumn();
		endLine = begLine;
		endColumn = begColumn;
		if (last != null) {
		   endLine = last.getLine();
		   endColumn = last.getColumn();
		}		
	}

	/***************************************************************/
	/***     RVALUES / VARIABLES DE LECTURA                      ***/
	/***************************************************************/
	
	@SuppressWarnings("unchecked")
	public T addRValue(SDPSymbol s) {
		text.append(s.value + " ");
		rvalues.add(s);
		endLine   = s.getLine();
		endColumn = s.getColumn();
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addLValue(SDPSymbol s) {
		text.append(s.value + " ");
		lvalues.add(s);
		endLine   = s.getLine();
		endColumn = s.getColumn();
		return (T) this;
	}
	
	public SDPSymbol getRValue(int index) {
		return rvalues.get(index);
	}
	
	public ArrayList<SDPSymbol> getRValues() {
		return rvalues;
	}

	/***************************************************************/
	/***                TRATAMIENTO VERBO                        ***/
	/***************************************************************/
	
	public int getVerbId() {
		return verbo.getId();
	}
	/**
	 * Establece el verbo 
	 * @param s Verbo
	 * @param replace si es true replaza el verbo existente (en caso de que exista)
	 */
    public void setVerb(SDPSymbol s, boolean replace) {
    	if (replace && verbo != null) {
    		verbo.value = s.value;
    	} else {
    		if (verbo == null) {
    		    verbo = s;
    		}    
    	}
    }
	
    public void setVerb(SDPSymbol s) {
    	setVerb(s, false);
    }

    public void setVerb(SDPSymbol s, SDPSymbol t) {
    	setVerb(s, false);
    	appendVerb(t);
    }
    
    public void appendVerb(SDPSymbol s) {
    	verbo.add(s);
    }
    
	public SDPSymbol getVerb() {
		return verbo;
	}
	
	public String getVerbName() {
		return verbo.toString();
	}
	
	public void setBegLine(int begLine) {
		this.begLine = begLine;
	}
	public void setBegColumn(int begColumn) {
		this.begColumn = begColumn;
	}
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	// Devuelve el identificador del verbo
	public int getId() {
		return id;
	}
	
	public void setGroup(Integer group) {
		this.group = group;
	}
	
	public Integer getGroup() {
		return group;
	}

	public void setSubGroup(Integer subGroup) {
		this.subGroup = subGroup;
	}
	
	public Integer getSubGroup() {
		return subGroup;
	}
	
	public int getOrden() {
		return orden;
	}
	
	public void setEndPoint(boolean endp) {
		endPoint = endp;
	}
	
	public boolean endWithPoint() {
		return endPoint;
	}
	
//	public void addIssue(int id) {
////		issues.add(new Issue(id));
//	}
	
	/***************************************************************/
	/***     OPCIONES                                            ***/
	/***************************************************************/

	public Option addOption(Option opt) {
		if (opt == null) return null;
		lstOptions.add(opt);
		mapOptName.addItem(opt.getName(),lstOptions.size() - 1);
		mapOptId.addItem  (opt.getId(),  lstOptions.size() - 1);
		return opt;
	}
	
	public boolean hasOptions() {
		return lstOptions.size() > 0;
	}

	public boolean hasOption(int id) {
		return mapOptId.getItem(id) != null;
	}
	
    public Option getOptionByPos(int pos) {
    	return lstOptions.get(pos);
    }
        
    public ArrayList<Option> getOptions() {
    	return lstOptions;
    }
    
    public String getFirma() {
    	if (firma != null) return firma;
    	StringBuilder cadena = new StringBuilder();
    	cadena.append(getVerbName());
    	for (SDPSymbol l : lvalues) {
    		cadena.append(l.getValue());
    	}
    	for (SDPSymbol r : rvalues) {
    		cadena.append(r.getValue());
    	}
    	return Firma.calculate(cadena.toString());
    }

    /***************************************************************/
	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
	/***************************************************************/
    
	public String toValue() {
		return toString();
	}
    
    public boolean hasEndPoint() {
    	return endPoint;
    }
    
    public boolean hasOption(String name) {
    	for (Option opt : lstOptions) {
    		if (opt.getName().compareToIgnoreCase(name) == 0) return true;
    	}
    	return false;
    }
    
	public List<Option> getOptionList() {
		return (List<Option>) lstOptions;
	}

	private List<Option> getOptionListByName(String name) {
    	List<Integer> l = mapOptName.getItemList(name);
    	if (l == null) return null;
    	ArrayList<Option> opts = new ArrayList<Option>();
    	for (Integer i : l) {
    	   opts.add(lstOptions.get(i));	
    	}
    	return opts;
	}
	public Option getOptionByName(String name) {
		List<Option> l = getOptionListByName(name);
    	return (l == null) ? null : l.get(0);
	}

	public Option getOption(int id) {
		List<Option> l = getOptionListById(id);
    	return (l == null) ? null : l.get(0);
	}
	
    public List<Option> getOptionListById(int idOption) {
    	List<Integer> l = mapOptId.getItemList(idOption);
    	if (l == null) return null;
    	ArrayList<Option> opts = new ArrayList<Option>();
    	for (Integer i : l) {
    	   opts.add(lstOptions.get(i));	
    	}
    	return opts;
    }

    public ArrayList<SDPSymbol> getLValueList() {
    	return lvalues; 
    }
    
    public SDPSymbol getLValue() {
    	return (lvalues.size() > 0) ? lvalues.get(0) : null; 
    }

    public ArrayList<SDPSymbol> getRValueList() {
    	return rvalues; 
    }
    
    public SDPSymbol getRValue() {
    	return (rvalues.size() > 0) ? rvalues.get(0) : null; 
    }

    public String getValue()     { return getVerbName(); }
    public int    getBegLine()   { return begLine;       }
    public int    getEndLine()   { return endLine;       }
    public int    getBegColumn() { return begColumn;     }
    public int    getEndColumn() { return endColumn;     }    
    public int    getLines()     { return endLine - begLine + 1; }
    
    public String getGroupName() {
    	return CDGText.getStmtGroupName(group);
    }
    public String getSubgroupName() {
    	return CDGText.getStmtSubGroupName(subGroup);
    }
    public Integer getNumRValues() {
    	return rvalues.size();
    }
    public Integer getNumLValues() {
    	return lvalues.size();
    }

    @Override    
    public String toString() {
    	return verbo.toString();
    }
    
    public String toText() {
    	return text.toString();
    }
}
