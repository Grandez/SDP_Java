/**
 * Contiene la información relativa a cada bloque de codigo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.ArrayList;

public class Block {

	private int begin = -1;
	private int end   = -1;
	private int order = -1;
	private int cause = -1;
	
	private int statements = -1;
	
	ArrayList<String> performs = new ArrayList<String>();
	
	/**
     * Genera un nuevo bloque de codigo
     *
     * @param order 
     *            Numero de bloque
     * @param statements
     *            Numero de sentencias analizadas
     * @param cause
     *            Origen del bloque
     */
	public Block(int order, int statements, int cause) {
		this.order = order;
		this.statements = statements;
		this.cause = cause;
	}
	
	public int getBegin() {
		return begin;
	}
	
	public void setBegin(int begin) {
		this.begin = begin;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getStatements() {
		return statements;
	}
	
	public void setStatements(int statements) {
		this.statements = statements;
	}

	public void setCause(int cause) {
	    this.cause = cause;
	}
	
	public int getCause() {
	    return cause;
	}
	
    public ArrayList<String> getPerforms() {
        return performs;
    }

    public void setPerforms(ArrayList<String> performs) {
        this.performs = performs;
    }
    
    public void addPerform(String name) {
        performs.add(name);
    }
	
}
