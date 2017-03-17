package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.Variable;

public class TBVarsFile {

	private String fileName;
	private Variable root;
	
	// Lista de variables raiz (01 y 77) pero en copys puede que no
	protected ArrayList<Variable> raiz = new ArrayList<Variable>();

		// JGG Se montara cuando soportemos la opcion VAR OF VAR
//JGG    private HashMap<String, HashMap<String, Variable>> vars = new HashMap<String, HashMap<String, Variable>>();
    protected HashMap<String, Variable> vars = new HashMap<String, Variable>();

    // La pila mantiene los diferentes niveles de las variables
    private Stack<Variable> pila = new Stack<Variable>();

    public TBVarsFile() {}
    
    public TBVarsFile(String module) {
    	fileName = module;
    }
    public String getFileName()                { return fileName;          }
    public void   setFileName(String fileName) { this.fileName = fileName; }


    public ArrayList<Variable> getRoots()      { return raiz; }
    
    
    public Variable addVariable(Variable v) {
        setPadre(v);
		vars.put(v.getName(), v);
    	return v;
    }
	    
    	/**
	 * Le asigna el padre y el nivel 01
	 * Si el tope de la pila es mayor que el actual, es el padre
	 * Si es menor hay que quitar los elementos
	 * 01 xxx
	 *    03 xxxx
	 *       05  xxxx
	 *    03 xxxx
	 *    
	 * 01 yyyy       
	 * @param v
	 */
	protected void setPadre(Variable v) {
    	if (v.getLevel() == 77) {
    		raiz.add(v);
    		return;
    	}
    	
		while (!pila.isEmpty() && pila.peek().getLevel() >= v.getLevel()) {
			pila.pop();
		} 
		
		if (pila.isEmpty() == false) {
		    Variable padre = pila.peek();
			padre.addHijo(v);
			v.setPadre(padre);
			v.setRoot(padre.getRoot());
		}
		else {
			raiz.add(v);
		}
		pila.push(v);
	}

	public int calculateMemorySize() {
		return calculateMemorySize(root);
	}
	
	private int calculateMemorySize(Variable v) {
		int size = v.calculateMemory();
		
	    for (Variable hijo : v.getHijos()) {
	       size += calculateMemorySize(hijo);	
	    }
	    v.setMemory(size);
	    return size;
	}
}
