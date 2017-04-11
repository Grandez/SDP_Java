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
package com.jgg.sdp.parser.base.stmt;

import java.util.*;

import com.jgg.sdp.core.tools.Firma;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.parser.base.symbol.*;

import java_cup.runtime.Symbol;

public abstract class Statement<T> {

    private SymbolExt             verbo      = null;
    private ArrayList<SymbolExt>  lvalues    = new ArrayList<SymbolExt>();
    private ArrayList<SymbolExt>  rvalues    = new ArrayList<SymbolExt>();    
    protected ArrayList<Option>   lstOptions = new ArrayList<Option>();
    protected ArrayList<Issue>    issues     = new ArrayList<Issue>();
    
    private HashMap<Integer, Option> options = new HashMap<Integer, Option>();    
    
    private String firma = null;
    
    private int id = 0;
    private int orden = -1; 
	private boolean endPoint = false;
 
	private int begLine;
	private int begColumn;
	private int endLine;
	private int endColumn;
	

	/***************************************************************/
	/***     CONSTRUCTORES                                       ***/
	/***************************************************************/
	
	public Statement() {
	}
	
	public Statement (Symbol s) {
		verbo = new SymbolExt(s);
		begLine = s.left;
		endLine = s.left;
		begColumn = s.right;
		endColumn = s.right;
		id = s.sym;
	}

	public Statement (Symbol s, int count) {
		this(s);
        orden = count;		
	}

	public Statement (Symbol s, SymbolList list) {
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
	public void adjust(Symbol last) {

		begLine = verbo.left;
		begColumn = verbo.right;
		endLine = begLine;
		endColumn = begColumn;
		if (last != null) {
		   endLine = ((Symbol) last.value).left;
		   endColumn = ((Symbol) last.value).right;
		}		
	}

	/***************************************************************/
	/***     RVALUES / VARIABLES DE LECTURA                      ***/
	/***************************************************************/
	
	@SuppressWarnings("unchecked")
	public T addRValue(Symbol s) {
		rvalues.add(new SymbolExt(s));
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addRValue(SymbolExt s) {
		rvalues.add(s);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addRValue(SymbolList list) {
		for (Symbol s : list.getVarList()) {
			addRValue(s);
		}
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addLValue(Symbol s) {
		lvalues.add(new SymbolExt(s));
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addLValue(SymbolExt s) {
		lvalues.add(s);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addLValue(SymbolExtList l) {
		for (Symbol s : l.getVarList()) {
			addLValue(s);
		}
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addLValue(SymbolList list) {
		for (Symbol s : list.getVarList()) {
			addLValue(s);
		}
		return (T) this;
	}

	public SymbolExt getRValue(int index) {
		return rvalues.get(index);
	}
	
	public ArrayList<SymbolExt> getRValues() {
		return rvalues;
	}
	
	/**
	 * Establece el verbo 
	 * @param s Verbo
	 * @param replace si es true replaza el verbo existente (en caso de que exista)
	 */
    public void setVerb(Symbol s, boolean replace) {
    	if (replace && verbo != null) {
    		verbo.value = s.value;
    	} else {
    		if (verbo == null) {
    		    verbo = new SymbolExt(s);
    		}    
    	}
    }
	
    public void setVerb(Symbol s) {
    	setVerb(s, false);
    }

    public void setVerb(Symbol s, Symbol t) {
    	setVerb(s, false);
    	appendVerb(t);
    }
    
    public void appendVerb(Symbol s) {
    	verbo.addParent(s);
    }
    
	public SymbolExt getVerb() {
		return verbo;
	}
	
	public String getVerbName() {
		StringBuilder verb = new StringBuilder();
		verb.append(verbo.getName());
		for (Symbol s : verbo.getParents()) {
			verb.append(" ");
            if (s.value instanceof Symbol) {
			   verb.append((String) ((Symbol) s.value).value);
		    }
		    else {
			   verb.append((String) s.value);
		    }
		}
		return verb.toString();
	}
	
	public int getVerbId() {
		return verbo.sym;
	}
	
	public int getBegLine() {
		return begLine;
	}

	public void setBegLine(int begLine) {
		this.begLine = begLine;
	}

	public int getBegColumn() {
		return begColumn;
	}

	public void setBegColumn(int begColumn) {
		this.begColumn = begColumn;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public int getEndColumn() {
		return endColumn;
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
	
	public int getOrden() {
		return orden;
	}
	
	public void setEndPoint(boolean endp) {
		endPoint = endp;
	}
	
	public boolean endWithPoint() {
		return endPoint;
	}
	
	public void addIssue(int id) {
		issues.add(new Issue(id));
	}
	
	/***************************************************************/
	/***     OPCIONES                                            ***/
	/***************************************************************/
	
	public boolean hasOptions() {
		return lstOptions.size() > 0;
	}

	public boolean hasOption(int id) {
		return options.containsKey(id);
	}
	
	public Option addOption(Option opt) {
		if (opt == null) return null;
		lstOptions.add(opt);
		options.put(opt.getId(),  opt);
		return opt;
	}
	
	public Option addOption(SymbolExt s) {
 		Option opt = new Option(s.sym, (String) s.value, s);
		lstOptions.add(opt);
		options.put(opt.getId(),  opt);
		return opt;
	}

    public Option getOption(int idOption) {
    	return options.get(idOption);
    }
    
    public Option getOptionByPos(int pos) {
    	return lstOptions.get(pos);
    }
    
    public Option getOptionByName(String name) {
    	for (Option o: lstOptions) {
    		if (o.getName().compareToIgnoreCase(name) == 0) return o;
    	}
    	return null;
    }
    
    public ArrayList<Option> getOptions() {
    	return lstOptions;
    }
    
    public String getFirma() {
    	if (firma != null) return firma;
    	StringBuilder cadena = new StringBuilder();
    	cadena.append(getVerbName());
    	for (SymbolExt l : lvalues) {
    		cadena.append(l.getName());
    	}
    	for (SymbolExt r : rvalues) {
    		cadena.append(r.getName());
    	}
    	return Firma.calculate(cadena.toString());
    }
}
