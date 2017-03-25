/**
 * Implementa un objeto Sentencia <br> 
 * Contiene una lista tokens o una lista statements
 * Pero siempre tiene al menos un token, el verbo y siempre es el primero
 * <br>
 *
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base.stmt;

import java.util.*;

import com.jgg.sdp.core.tools.Firma;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.parser.base.symbol.SymbolExt;
import com.jgg.sdp.parser.base.symbol.SymbolExtList;
import com.jgg.sdp.parser.base.symbol.SymbolList;

import java_cup.runtime.Symbol;

public class Statement {

    private SymbolExt             verbo       = null;
    private ArrayList<SymbolExt>  lvalues     = new ArrayList<SymbolExt>();
    private ArrayList<SymbolExt>  rvalues     = new ArrayList<SymbolExt>();
    
	// La lista mantiene el orden de las opciones
	// El map pregunta por su clae
	private ArrayList<Symbol> tokens     = new ArrayList<Symbol>();
    private ArrayList<Option> lstOptions = new ArrayList<Option>();
    private ArrayList<Issue>  issues     = new ArrayList<Issue>();
    
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
		id = s.sym;
	}

	public Statement (Symbol s, int count) {
		verbo = new SymbolExt(s);
		id = s.sym;
        orden = count;		
	}

	public Statement (Symbol s, SymbolList list) {
		verbo = new SymbolExt(s);
		id = s.sym;
        addRValues(list);		
	}
	
	public Statement(Tokens tokens) {
		for (Symbol token : tokens.getTokens()) {
		    this.tokens.add(token);	
		}
	}

	public Statement (Statement s) {
		tokens = s.getSymbols();
		verbo  = s.getVerb();
		id     = s.getId();
		orden  = s.getOrden();
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
	
	public Statement addRValue(Symbol s) {
		rvalues.add(new SymbolExt(s));
		return this;
	}
	
	public Statement addRValue(SymbolExt s) {
		rvalues.add(s);
		return this;
	}

	public Statement addRValues(SymbolList list) {
		for (Symbol s : list.getVarList()) {
			addRValue(s);
		}
		return this;
	}
	public SymbolExt getRValue(int index) {
		return rvalues.get(index);
	}
	
	public ArrayList<SymbolExt> getRValues() {
		return rvalues;
	}
	
	public Statement addTokens(Tokens tokens) {
		if (tokens == null) return this;
		return addSymbols(tokens.getTokens());
	}
	
	public Statement addSymbols(ArrayList<Symbol> lista) {
		for (Symbol s: lista) {
			tokens.add(s);
		}
		return this;
	}
	
	public Statement addVariables(SymbolExtList vars) {
		/* JGG
	    for (Variable v : vars.getVarList()) {
	        tokens.add(v);
	    }
	    */
	    return this;
	}

	/**
	 * Establece el verbo 
	 * @param s Verbo
	 * @param replace si es true replaza el verbo existente (en caso de que exista)
	 */
    public void setVerb(Symbol s, boolean replace) {
    	if (replace || verbo == null) {
    		verbo = new SymbolExt(s);
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
	
	/**
     * Obtiene la posicion de un determinado simbolo identificado por su Id.
     *
     * @param id
     *            ID del simbolo
     * @return La posicion del simbolo si existe<br>
     *         -1 si no existe
     */
	public int getSymbolById(int id) {
		for (int idx = 0; idx < tokens.size(); idx ++ ) {
			if (tokens.get(idx).sym == id) return idx;
		}
		return -1;
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
	
	public Statement addSymbol(Symbol s) {
		tokens.add(s);
		return this;
	}

	public Symbol getSymbol(int pos) {
		if (pos < tokens.size()) return tokens.get(pos);
		return null;
	}
	
	public ArrayList<Symbol> getSymbols() {
		return tokens;
	}
		
	public boolean hasSymbol(int id) {
		for (Symbol s : tokens) {
			if (s.sym == id) return true;
		}
		return false;
	}
	
	public void replaceSymbol(int pos, Symbol s) {
		Symbol tok = tokens.get(pos);
		tok.left = s.left;
		tok.right = s.right;
		tok.sym = s.sym;
		tok.value = s.value;
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

	// Caso WHEN / WHEN OTHER
	// Podria quedarse un WHEN huerfano
	public Option replaceOption(SymbolExt s) {
 		Option opt = new Option(s.sym, (String) s.value, s);
 		lstOptions.remove(lstOptions.size() - 1);
		lstOptions.add(opt);
		options.put(opt.getId(),  opt);
		return opt;
	}

	// Caso n times, hay que quitar la opcion n y poner times n
	public Option mergeOption(Option opt) {
		Option last = lstOptions.get(lstOptions.size() - 1);
	    opt.addSymbol(last.getAsSymbolExt());
	    lstOptions.remove(lstOptions.size() - 1);
	    options.remove(last.getId());
	    
	    return addOption(opt);
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
