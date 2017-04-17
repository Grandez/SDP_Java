/**
 * Mantiene la tabla de la lista de parrafos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.Paragraph;

public class TBParagraphs {

	private int parrafos = 0;
	
	ArrayList<Paragraph>       lstParrs  = new ArrayList<Paragraph>();
	HashMap<String, Integer>   parrs     = new HashMap<String, Integer>();
	ArrayList<String>          lstFrom   = new ArrayList<String>();
	ArrayList<String>          lstTo     = new ArrayList<String>();	
	
	public Paragraph addParagraph(String name, int line, int stmts) {
		String upper = name.toUpperCase();
		Paragraph p = new Paragraph(upper, line, stmts);
		p.setIndex(++parrafos);

		p.setOrden(lstParrs.size());
		p.setLine(line);
		p.setSentences(stmts);
		
		// Parrafo inicial, se referencia por caida
		if (stmts == 0) p.incReferences();

		lstParrs.add(p);
        parrs.put(upper, parrafos - 1);
        
		if (lstParrs.size() > 1) {
			Paragraph p2 = lstParrs.get(lstParrs.size() - 2); 
			p2.setSentences(p.getSentences() - p2.getSentences());
		}
		return p;
	}

	public void addReference(String from, String to) {
		lstFrom.add(from.toUpperCase());
		lstTo.add(to);
	}

	public void setReferences() {
	   Integer iParr = 0;
	   for (int idx = 0; idx < lstFrom.size(); idx++) {
		   iParr = parrs.get(lstFrom.get(idx));
		   // Si el parrafo esta en una COPY y no se ha procesado
		   // iParr es NULL y el codigo esta marcado como no procesado
		   if (iParr != null) {
		      lstParrs.get(iParr).incReferences();
		      setReferencesThru(iParr, lstTo.get(idx));
		   }
	   }
	}

	private void setReferencesThru(int from, String to) {
		Paragraph p     = null;
		String    upper = null;
		
		if (to == null) return;
		upper = to.toUpperCase();
		
		while (++from < lstParrs.size() ) {
			p = lstParrs.get(from);
			p.incReferences();
			if (p.getName().compareTo(upper) == 0) return;
		}
	}
	
	public int getCount() {
		return lstParrs.size();
	}
	
	public Paragraph getCurrentParagraph() {
		if (lstParrs.size() == 0) return null;
		return lstParrs.get(lstParrs.size() - 1);
	}

	public Paragraph getParagraph(String name) {
		for (Paragraph p : getParagraphs()) {
			if (p.getName().compareTo(name.toUpperCase()) == 0) return p;
		}
		return null;
	}

	public Paragraph getParagraph(Integer order) {
		if (order < 1 || order >= lstParrs.size()) return null;
		return lstParrs.get(order);
	}
	
	public ArrayList<Paragraph> getParagraphs() {
		return lstParrs;
	}
	
	public int getParagraphOrder(String name) {
		Integer pos = parrs.get(name);
		return (pos == null) ? -1 : pos;
	}
}
