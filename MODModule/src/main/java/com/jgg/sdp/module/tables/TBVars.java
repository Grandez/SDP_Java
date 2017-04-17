/**
 * Mantiene la tabla de la lista de variables
 * Hay que mantener una tabla por cada COPY para identificar los issues
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.Variable;

public class TBVars {

	private Variable root = new Variable("0", "root");
	
    private HashMap<String, ArrayList<Variable>> vars = new HashMap<String, ArrayList<Variable>>();
    private Stack<Variable> pila = new Stack<Variable>();

    /***********************************************************/

    public TBVars() {
    }

    public Variable addVariable(Variable v) {
        setPadre(v);
        ArrayList<Variable> lista = vars.get(v.getName());
        if (lista == null) {
        	lista = new ArrayList<Variable>();
        	lista.add(v);
        	vars.put(v.getName(), lista);
        }
        else {
        	lista.add(v);
        }
        return v;
    }
	
    public void addHermano(String curr, String name) {
    	/*JGG
    	Variable hermano1 = vars.get(name);
    	Variable hermano2 = vars.get(curr);
    	hermano1.addHermano(hermano2);
    	hermano2.addHermano(hermano1);
    	*/
    }
    
    public Variable getVariable(String name) {
    	ArrayList<Variable> lista = vars.get(name);
    	if (lista == null) return null;
    	return lista.get(0);
    }

    public ArrayList<Variable> getVariables(String name) {
    	return vars.get(name);
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
    	int nivel = v.getLevel();

    	if (nivel == 88) return;
    	
    	if (nivel == 77 || nivel == 1) {
    		cleanStack();
    		root.addHijo(v);
    		if (nivel == 1) pila.push(v);
    		return;
    	}
    	
    	// La pila no puede estar vacia por que el nivel 01 se hace fuera
		while (pila.peek().getLevel() >= v.getLevel()) {
			pila.pop();
		} 
		
		pila.peek().addHijo(v);
		pila.push(v);
	}
    
    /**
     * Marca la variable como leida
     * @param name
     * @return True si la variable no existe (Falta COPY)
     *         False si existe
     */
    public boolean setVarRead(String name, String padre) {
    	Variable v = getVarQualified(name, padre);
    	if (v == null) return true;
    	v.setRead();
    	return false;
    }
    public boolean setVarWrite(String name, String padre) {
    	Variable v = getVarQualified(name, padre);
    	if (v == null) return true;
    	v.setWrite();
    	return false;
    }
    
    private Variable getVarQualified(String name, String padre) {
    	Variable p = null;
    	String vname = null;

    	ArrayList<Variable> lista = getVariables(name);
    	if (lista == null) return null;
    
    	if (lista.size() == 1 || padre == null) return lista.get(0);

    	for (Variable v : lista) {
    		p = v.getParent();
    		vname = v.getName();
    		while (p.getLevel() != 0) {
    			if (p.getName().compareToIgnoreCase(vname) == 0) return v;
    			p = p.getParent();
    		}
    	}
    	return null;
    }


    /**
     * Actualiza todos los hijos como escritos de manera indirectamente
     * Actualiza los hermanos (REDEFINES) como escritos indirectamente 
     * @param v
     */
    private void updateWrite(Variable v) {
    	for (Variable hijo: v.getHijos()) {
    		if (!hijo.isIndirectWritten()) {
    		    hijo.setWriteIndirect();
    		    updateWrite(hijo);
    	    }
    	}
    	
    	for (Variable hermano: v.getHermanos()) {
    		if (!hermano.isIndirectWritten()) {
    		    hermano.setWriteIndirect();
    		    updateWrite(hermano);
    	    }
    	}
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

	
	public void updateReadWrite() {
		procesaHijos();
		procesaHermanos();
	}
	
	public void procesaHijos() {
		int iRead = 0x0;
		int iWrite = 0x0;
		Integer oRead = new Integer(0);
		Integer oWrite = new Integer(0);
		
	    for (Variable hijo : root.getHijos()) {
	    	if (hijo.getRead()  > 0) iRead = 0x04;
	    	if (hijo.getWrite() > 0) iWrite = 0x04;
	    	updateReadWrite(hijo, iRead, iWrite, oRead, oWrite);
	    	hijo.setRead(oRead);
	    	hijo.setWrite(oWrite);
	    	oRead = 0;
	    	oWrite = 0;
	    }
		
	}

	public void procesaHermanos() {
	    for (Variable hijo : root.getHijos()) {
	    }
		
	}
	
	private void updateReadWrite(Variable v, int iRead, int iWrite, Integer oRead, Integer oWrite) {
		v.setRead(iRead);
		v.setWrite(iWrite);
	    for (Variable hijo : v.getHijos()) {
	    	iRead  |=  ((hijo.getRead()  & 0x01) > 0) ? 0x04 : 0x00;
	    	iWrite |=  ((hijo.getWrite() & 0x01) > 0) ? 0x04 : 0x00;
	    	updateReadWrite(hijo, iRead, iWrite, oRead, oWrite);
	    	v.setRead(oRead);
	    	v.setWrite(oWrite);
	    }
	    
		if ((v.getRead()  & 0x03) != 0) oRead  |= 0x02;
		if ((v.getWrite() & 0x03) != 0) oWrite |= 0x02;		
	}
	
	private void cleanStack() {
		while (!pila.isEmpty()) pila.pop();
	}
}
