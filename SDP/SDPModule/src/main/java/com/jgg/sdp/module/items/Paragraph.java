/**
 * Implementa un objeto Parrafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

public class Paragraph {

	private String  name = null;     // Nombre del parrafo
	private int     line = -1;       // Linea
	private int     references = 0;  // Numero de referencias
	private int     sentences = 0;   // Numero de sentencias
    private int     ciclomatic = 1;  // Conplejidad ciclomatica
	private int     index = -1;      // Indice en la tabla Working
    private int     orden = 0;       // Orden en el fichero fuente 
    private boolean isExit = false;  // Indica si es un parrafo de EXIT
    
	public Paragraph(String name) {
		this.name = name.trim();
	}
	
	public Paragraph(String name, int line, int sentences) {
		// La mascara es {espacio}nombre parrafo
		this.name = name.trim();
		this.line = line;
		this.sentences = sentences;
	}
	
	public String getName() { 
		return name;
	}
	public void setName(String name) {
		this.name = name.trim();
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getReferences() {
		return references;
	}
	
	public void incReferences() {
		references++;
	}
	public void setSentences(int sentences) {
		this.sentences = sentences;
	}
	public int getSentences() {
		return sentences;
	}

	public int getCiclomatic() {
		return ciclomatic;
	}

	public void incCiclomatic() {
		ciclomatic++;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public void setExit(boolean exit) {
		isExit = exit;
	}
	public boolean isExit() {
		return isExit;
	}
	public boolean isVirtual() {
	    return this.name.length() == 0;
	}
}
