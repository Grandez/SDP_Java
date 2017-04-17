/**
 * Contiene un verbo que es TS
 * Cuando el verbo es compuesto, como por ejemplo:
 *    WEB SEND
 *    WEB STARTBROWSE FORMFIELD
 *    WEB STARTBROWSE HTTPHEADER
 *   
 * Se almacena una lista de Verbos TS indicando si es o no
 * Ejemplo:
 *   WEB - 0 - 2 (No vale, tiene dos posibles continuaciones)
 *            SEND        - 1 - 0 (Si vale, es suficiente)
 *            STARTBROWSE - 0 - 2 (No vale, tiene dos posibles continuaciones)
 *                          FORMFIELD  - 1 - 0
 *                          HTTPHEADER - 1 - 0
 *                           
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */

package com.jgg.sdp.parser.code;

import java.util.*;

public class TSVerb {

	private String name;
	private int    type = 0; // No vale, es decir, no es verbo completo
	private TSVerb last = null;
	
	private HashMap<String, TSVerb> verbs = new HashMap<String, TSVerb>();
	private HashSet<String>         opt   = new HashSet<String>();
	
	public TSVerb (String name) {
		this.name = name;
		last = this;
	}
	
	public String getName() {
		return name;
	}
	
	public int getType() {
		return type;
	}
	
	public TSVerb get (String word) {
		return verbs.get(word);
	}

	public void setType(int type) {
		last.type = type;
	}
	
	public void add(String name) {
		last = last.addVerb(new TSVerb(name));
	}
	
	private TSVerb addVerb(TSVerb verb) {
		verbs.put(verb.getName(), verb);
		return verb;
	}
	
	public void addOption(String option) {
		opt.add(option);
	}
	
	public ArrayList<String> getOptionsQR() {
		return new ArrayList<String>(opt);
	}
	
	public void reset() { last = this; }
	
}
