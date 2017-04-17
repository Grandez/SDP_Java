/**
 * Implementa un objeto Variable
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.*;

public class Var {
	
	public final static int NUMERIC  = 0;
	public final static int ALPHA    = 1;
	public final static int ALPHANUM = 2;
	
	public final int READ_DIRECT   = 1;  // Leida directamente
	public final int READ_INDIRECT = 2;  // Leida a traves de un padre
	
	public final int WRITE_DIRECT   = 1; // Modificada directamente
	public final int WRITE_INDIRECT = 2; // Modificada a traves de un padre
	
	private String name;
	private int    level;
	private int    type;
	private int    size = 1;    // Longitud de la picture
	private Object value = null;
	private Var   padre;
	private Var   hermano;
	private int    references;
	
	private int leido   = 0; // Es rvalue?
	private int escrito = 0; // Es lvalue?
	
    private int    elements = 0;   // Es una tabla
	
	private ArrayList<Var> hijos = new ArrayList<Var>();
	
	public Var(String name, int level) {
		this.name = name;
		this.level = level;
	}

	/**
	 * Constructor usado en el parser.
	 * Evita hacer el analisis del numero ahi
	 * @param name Nombre de la variable
	 * @param level nivel como cadena
	 */
	public Var (String name, String level) {
		this.level = Integer.parseInt(level.trim());
		this.name = name;
	}
	
	public void addHijo(Var hijo) {
		hijos.add(hijo);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Var getPadre() {
		return padre;
	}
	public void setPadre(Var padre) {
		this.padre = padre;
	}
	public Var getHermano() {
		return hermano;
	}
	public void setHermano(Var hermano) {
		this.hermano = hermano;
	}
	public int getReferences() {
		return references;
	}
	public void setReferences(int references) {
		this.references = references;
	}

    /**
     * Indica si la variable es utilizada en el programa bien 
     * de manera directa o indirecta 
     * @return true si es utilizada
     *         false si no lo es
     */
    public boolean isRead() {
        return leido != 0;
    }
    
    /**
     * Marca la variable como usada en tiempo de ejecucion
     * @param tipo READ_DIRECT    Se usa explicitamente
     *             READ_INDIRECT  Se usa a traves de un nivel superior
     */
    public void setRead(int tipo) {
        this.escrito |= tipo;
    }
	
	/**
	 * Indica si la variable es modificada a lo largo de 
	 * la ejecucion del programa o no
	 * @return true si es modificada
	 *         false si no lo es
	 */
	public boolean isWritten() {
		return escrito != 0;
	}
	
	/**
	 * Marca la variable como modificada en tiempo de ejecucion
	 * @param tipo WRITE_DIRECT   Se modifica explicitamente
	 *             WRITE_INDIRECT Se modifica a traves de un nivel superior
	 */
	public void setWritten(int tipo) {
		this.escrito |= tipo;
	}
	
    public void setElements(int elements) {
    	this.elements = elements;
    }
	public void setSize(int size) {
		this.size = size;
	}
	public int getElements() {
		return elements;
	}
	
	public int getSize() {
		return size;
	}
}
