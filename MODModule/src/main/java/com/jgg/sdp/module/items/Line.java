/**
 * Implementa una linea de codigo con su informacion asociada
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

public class Line {

	public final static int LINE_BLANK    = 0;
	public final static int LINE_CODE     = 1;
	public final static int LINE_COMMENT  = 2;
	public final static int LINE_RESERVED = 3;
	public final static int LINE_LABEL    = 4;
	
	private int line;
    private int type;
    
	private StringBuilder datos;

	public Line(int line) {
		datos = new StringBuilder();
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public String getDatos() {
		return datos.toString();
	}

	public void add(StringBuilder datos) {
		this.datos.append(datos);
	}
	
	public void add(String datos) {
		this.datos.append(datos);
	}
	
	public void add(Character dato) {
		this.datos.append(dato);
	}

	
}
