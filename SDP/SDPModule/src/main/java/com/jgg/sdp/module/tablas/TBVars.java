/**
 * Mantiene la tabla de la lista de variables
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tablas;

import java.util.ArrayList;
import java.util.HashMap;

import com.jgg.sdp.module.items.Var;

public class TBVars {

	private final String ROOT = "_ROOT_"; // Variable COBOL no valida
	
	private HashMap<String, Var> vars = new HashMap<String, Var>();
	
	/* Para saber el padre, guardamos una pila de los ultimos */
	private ArrayList<Var> pila = new ArrayList<Var>();
//	private int nivelPila = 0;
	
    public TBVars() {
		Var root = new Var(ROOT, 0);
		vars.put(root.getName(), root);
		pila.add(root);
	}
	
	/**
     * OBtiene una variable por su nombre
     *
     * @param name 
     *            Nombre de la variable
     * @return el objeto asociado
     */
	public Var getVariable(String name) {
		return vars.get(name);
	}
	
	/**
     * Inserta una variable en la tabla de variables
     *
     * @param var
     *            El objeto Var a insertar
     */
	public void addVariable(Var var) {
		Var padre;
		int nivel = var.getLevel();
	
		//JGG Al no procesar el FILLER el calculo de memoria es incorrecto
		if (var.getName().compareTo("FILLER") == 0) return;
		
		vars.put(var.getName(),var);
		 
		if (nivel == 77 || nivel == 78 || nivel == 88) {
			padre = vars.get(ROOT);
			var.setPadre(padre);
			padre.addHijo(var);
		}
		else {
           ajustarPila(var);
           padre = pila.get(pila.size() - 2);
           var.setPadre(padre);
           padre.addHijo(var);
		}
	}
	
	/*
	 * La pila tiene un elemento por nivel
	 * cuando se va a insertar una variable esta marca el siguiente nivel
	 * de manera que si la siguiente tiene un nivel mayor es su hija
	 * si lo tiene igual son hermanos
	 * si lo tiene menor marca el nuevo limite
	 */
	private void ajustarPila(Var var) {
		int nivel = var.getLevel();

		while (getNivelPila() > nivel) {
	    	pila.remove(pila.size() - 1);
	    }

		if (nivel == getNivelPila()) {
	    	pila.remove(pila.size() - 1);
	    }
		
//	    if (nivel >= getNivelPila()) {
	    	pila.add(var);
//	    	nivelPila = nivel;
//	    	return;
//	    }
	}
	
	private int getNivelPila() {
		return pila.get(pila.size() - 1).getLevel();
	}
}
