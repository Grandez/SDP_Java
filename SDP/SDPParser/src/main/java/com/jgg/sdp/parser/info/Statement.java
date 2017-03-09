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
package com.jgg.sdp.parser.info;

import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class Statement {

	private ArrayList<Symbol>    tokens = new ArrayList<Symbol>();

	private Symbol verbo = null;
    private int id = 0;
    private int orden = -1; 
	private boolean endPoint = false;

	public Statement() {
	}
	
	public Statement (Symbol s) {
		verbo = s;
		id = s.sym;
	}

	public Statement (Symbol s, int count) {
		verbo = s;
		id = s.sym;
        orden = count;		
	}
	
	public Statement(Tokens tokens) {
		for (Symbol token : tokens.getTokens()) {
		    this.tokens.add(token);	
		}
	}

	/**
	 * Constructor de copia.<br>
	 * @param s El objeto Statement a duplicar
	 */
	public Statement (Statement s) {
		tokens = s.getSymbols();
		verbo  = s.getVerb();
		id     = s.getId();
		orden  = s.getOrden();
	}

	public Statement addTokens(Tokens tokens) {
		return addSymbols(tokens.getTokens());
	}
	
	public Statement addSymbols(ArrayList<Symbol> lista) {
		for (Symbol s: lista) {
			tokens.add(s);
		}
		return this;
	}
	
	public Statement addVariables(VarList vars) {
	    for (Variable v : vars.getVarList()) {
	        tokens.add(v);
	    }
	    return this;
	}
	
	public Symbol getVerb() {
		return verbo;
	}
	
	public String getVerbName() {
		return (String) verbo.value;
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
	
	public int getFirstLine() {
		return verbo.left;
	}

	public int getFirstColumn() {
		return verbo.right;
	}
	
	public int getLastLine() {
		if (tokens.size() == 0) return verbo.left;
		return tokens.get(tokens.size() - 1).left;		
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
}
