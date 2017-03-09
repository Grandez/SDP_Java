/**
 * Mantiene la tabla de la lista de parrafos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tablas;

import java.util.*;

import com.jgg.sdp.module.items.Paragraph;

public class TBParagraphs {

	private int parrafos = 0;
	
	ArrayList<Paragraph>       listParrs = new ArrayList<Paragraph>();
	HashMap<String, Integer>   parrs     = new HashMap<String, Integer>();
	HashMap<String, Paragraph> pending   = new HashMap<String, Paragraph>();
	
	/**
     * Crea un parrafo por que ha sido referenciado, no declarado
     *
     * @param name
     *            el nombre del parrafo
     * @return El parrafo creado
     */
	public Paragraph addReference(String name) {
		Paragraph p = null;
		Integer pos = parrs.get(name);
		
		p = (pos == null) ? pending.get(name) : listParrs.get(pos);
		
		if (p == null) {
			p = new Paragraph(name);
			p.setIndex(++parrafos);
			pending.put(name,  p);
		}
		
		p.incReferences();
		return p;
	}
	
	/**
     * Inserta un nuevo parrafo por su declaracion
     *
     * @param name
     *            Nombre del parrafo
     * @param line
     *            Linea donde se ha declarado
     * @param stmts
     *            Numero de sentencias analizadas
     * @return El objeto Paragraph creado
     */
	public Paragraph addParagraph(String name, int line, int stmts) {
		Paragraph p = pending.get(name);
		if (p != null) pending.remove(name);
		if (p == null) {
			p = new Paragraph(name, line, stmts);
			p.setIndex(++parrafos);
		}

		p.setOrden(listParrs.size());
		p.setLine(line);
		p.setSentences(stmts);
		
		// Parrafo inicial, se referencia por caida
		if (stmts == 0) p.incReferences();

		listParrs.add(p);
		
		if (listParrs.size() > 1) {
			Paragraph p2 = listParrs.get(listParrs.size() - 2); 
			p2.setSentences(p.getSentences() - p2.getSentences());
		}
		return p;
	}
		
	public int getCount() {
		return listParrs.size();
	}
	
	public Paragraph getCurrentParagraph() {
		if (listParrs.size() == 0) return null;
		return listParrs.get(listParrs.size() - 1);
	}

	/**
     * Obtiene un parrafo por su nombre
     *
     * @param name
     *            Nombre del parrafo
     * @return El obeto asociado
     */
	public Paragraph getParagraph(String name) {
		for (Paragraph p : getParagraphs()) {
			if (p.getName().compareToIgnoreCase(name) == 0) return p;
		}
		return null;
	}
	
	public ArrayList<Paragraph> getParagraphs() {
		return listParrs;
	}
}
