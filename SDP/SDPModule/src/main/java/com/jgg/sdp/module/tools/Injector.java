/**
 * Se encarga de inyectar los traps en el nuevo codigo fuente
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tools;

import java.util.ArrayList;
import java.util.HashMap;

import com.jgg.sdp.core.ctes.TRAP;

public class Injector {

	private int    nextTrap = -1;
    
    // Variables que se resuelven al final del proceso
    private HashMap<String, String> vars = new HashMap<String, String>();
    
	private ArrayList<Trap> trapCode = new ArrayList<Trap>();
	
    // Trap a insertar antes de un verbo (Se conoce la posicion)
	private ArrayList<Trap> before = null;
	
	//Trap a insertar en el siguiente verbo (No se conoce la posicion)
	private ArrayList<Trap> after = null;
	
	private HashMap<String, String> variables = new HashMap<String, String>();

	public Injector() {
	}
	
	/**
     * Inserta el Trap en la lista de traps a insertar ANTES de la sentencia
     *
     * @param trap Trap a almacenar
     * @return     El propio Trap 
     */
	public Trap setPrevTrap(Trap trap) {
		if (before == null) before = new ArrayList<Trap>();
		before.add(trap);
		return trap;
	}
	
    /**
     * Inserta el Trap en la lista de traps a insertar DESPUES de la sentencia
     *
     * @param trap Trap a almacenar
     * @return     El propio Trap 
     */
	public Trap setNextTrap(Trap trap) {
		if (after == null) after = new ArrayList<Trap>();
		after.add(trap);
		return trap;
	}
	
    /**
     * Inserta el Trap en la lista de traps a insertar antes de la sentencia
     * En la primera posicion de la lista de Traps
     *
     * @param trap Trap a almacenar
     * @return     El propio Trap 
     */
	public Trap setFirstTrap(Trap trap) {
		if (trapCode.size() == 0) {
			trapCode.add(trap);
			return trap;
		}
		Trap t = trapCode.get(trapCode.size() - 1);
		t.addBefore(trap);
		return trap;
	}
	
    /**
     * Carga el trap en la posicion indicada Se utiliza para insertar el codigo
     * de Working .
     *
     * @param pos  Columna donde debe insertarse 
     * @param trap Trap a insertar
     */
	public void setTrap(int pos, Trap trap) {
		trapCode.add(pos, trap);
	}
	
	/**
     * Transfiere los Traps de antes de la sentencia a despues de la sentencia
     */
	public void setTrapsAfter() {
		for (Trap trap : before) {
			setNextTrap(trap);
		}
		before = null;
	}
	
	/**
     * Carga los Traps
     *
     * @param line Linea del codigo fuente
     * @param col  Columna del codigo fuente
     * @return El propio Trap
     */
	public Trap loadTraps(int line, int col) {
		Trap last = getLastTrap();

		if (before != null) {
			for (Trap trap : before) {
                 trap.setLine(line);
                 trap.setColumn(col);
                 trapCode.add(trap);
			}
		}

		before = after;
		after = null;
		return last;
	}
	
	/**
     * Quita los Traps pendientes Es necesario en el caso de la sentencia EXIT.
     */
	public void removeTraps() {
		before = null;
		after = null;
	}
	
	/**
     * Elimina los Traps pendientes
     */
	public void removeLastTrap() {
		if (after == null) return;
		after.remove(after.size() - 1);
	}

	/**
     * Inserta los Traps de fin de fichero
     */
	public void lastTraps() {
		Trap trap = loadTraps(Integer.MAX_VALUE, 0);
		if (trap != null) trap.setEndStatement(true);
	}
	
	/**
     * Guarda el Trap como siguiente
     *
     * @param trap El Trap a guardar
     */
	public void storeNextTrap(Trap trap) {
		if (after == null) after = new ArrayList<Trap>();
		after.add(trap);
	}

	/**
	 * Devuelve los traps pendientes y los quita
	 * @return La lista de traps pendientes
	 */
	public ArrayList<Trap> getPendingTraps() {
		ArrayList<Trap> aux = new ArrayList<Trap>();
		if (after != null) {
		   for (Trap t : after) {
			  aux.add(t);
		   }
	   }
	   after = null;
	   return aux;
	}
	
	/**
	 * Devuelve la lista de bloques identificados como Traps
	 * @return lista de Traps de tipo bloque
	 */
	public ArrayList<Trap> getTrapBlocks() {
		ArrayList<Trap> bloques = new ArrayList<Trap>();
		if (trapCode != null) {
			for (Trap trap : trapCode) {
				if (trap.getType() == TRAP.BLOCK) {
					bloques.add(trap);
				}
			}
		}
		return bloques;
	}


	/**
	 * Devuelve la siguiente linea del codigo original
	 * donde se debe inyectar codigo
	 * @return la linea donde se debe inyectar el codigo
	 *          -1 si no hay mas lineas a inyectar
	 */
	public int getNextInjection() {
		nextTrap++;
		if (nextTrap >= trapCode.size()) return -1;

		return trapCode.get(nextTrap).getLine();
	}
	
	/**
	 * Devuelve las lineas a inyectar
	 * En este punto, el puntero apunta a esas lineas
	 * @return
	 */
	public ArrayList<String> getCodeToInject() {
		Trap trap = trapCode.get(nextTrap);
		return translate(trap.getTrapCode());
	}

	public int getColumn() {
		return trapCode.get(nextTrap).getColumn();
	}

	/**
     * Reconstruye la lista de codigo a inyectar unifica los que van en la misma
     * linea.
     *
     * @param vars Lista de variables
     */
	public void prepare(String vars) {
		loadVariables(vars);	
	}
	
	private void loadVariables(String v) {
		String[] vv = v.split(";");
		for (int idx = 0; idx < vv.length; idx += 2) {
			variables.put(vv[idx], vv[idx + 1]);
		}
	}
	
	/**
     * Crea una variable interna
     * 
     * @param key Clave de la variable
     * @param val Valor de la variable
     */
   public void setVar(String key, Object val) {
		String value = "";
		if (val instanceof String)  value = (String) val; 
		if (val instanceof Integer) value = ((Integer) val).toString();
		vars.put(key, value);
	}

	/**
     * Obtiene el valor de una variable interna
     *
     * @param key Clave de la variable
     * @return Valor de la variable o cadena vacia
     */
	public String getVar(String key) {
		String aux = vars.get(key);
		return (aux == null) ? "" : aux;
	}
	
	private ArrayList<String> translate(ArrayList<String> lineas) {
		ArrayList<String> lines = new ArrayList<String>();
		for (String line : lineas) {
			lines.add(translateLine(line));
		}
		return lines;
	}
	
	private String translateLine(String code) {
			int beg = code.indexOf("${");
		    String tmp = code;
			while (beg != -1) {
				int end = tmp.indexOf("}");
				String tok = tmp.substring(beg + 2, end);
				String rep = getVar(tok);
				tmp = tmp.replaceAll("\\$\\{" + tok + "\\}", rep);
				beg = tmp.indexOf("${");
			}
			return tmp;
	}
	
	private Trap getLastTrap() {
		if (trapCode.size() == 0) return null;
		return trapCode.get(trapCode.size() - 1);
	}
	
}
