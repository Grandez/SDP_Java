package com.jgg.sdp.parser.base;

import java.util.*;

import com.jgg.sdp.parser.info.StmtCopy;
import com.jgg.sdp.module.items.Copy;

public class ParserInfo {

	private static ParserInfo parserInfo = null;
	
	private Stack<Integer> sections     = new Stack<Integer>();
	private Stack<Integer> lineSections = new Stack<Integer>();
	
	private Stack<Object>  modules  = new Stack<Object>();
	private Stack<String>  members  = new Stack<String>();
	
	// Mantiene la linea de inicio de los diferentes parsers
	private Stack<Integer> offset   = new Stack<Integer>();	
	
	private boolean includeParsed = false;
	
	private Copy currCopy = null;
	
	private ParserInfo() {
		offset.add(0);
	}
	
	public static ParserInfo getInstance() {
		if (parserInfo == null) parserInfo = new ParserInfo();
		return parserInfo;
	}
	
	public static ParserInfo getInstance(boolean reset) {
		if (reset) parserInfo = new ParserInfo();
		return parserInfo;
	}

	/*************************************************************/
	/***      TRATAMIENTO DE SECCIONES                         ***/
	/*** Se realiza aqui, por que se usa en diferentes parsers ***/
	/*************************************************************/
	
	public void addDivision(int code, int line) {
		addSectionAndDivision(code, line);
	}
	
	public void addSection(int code, int line) {
		addSectionAndDivision(code, line);
	}
	
	private void addSectionAndDivision(int code, int line) {
		sections.add(code);
		lineSections.add(line);
	}
	
	public int getSection() {
		return sections.peek();
	}
	
	public Integer removeSection() {
		if (!sections.isEmpty()) return sections.pop();
		return null;
	}
	
	public Integer removeSectionLine() {
		return lineSections.pop();
	}
	
	/***********************************************************/
	/***      GESTION DE COPYS                               ***/
	/***********************************************************/

	public Copy createtCurrentCopy() {
		currCopy = new Copy();
		return currCopy;
	}
	
	public Copy setCurrentCopy(Copy copy) {
		currCopy = copy;
		return copy;
	}
	
	public Copy getCurrentCopy() {
		if (currCopy == null) return createtCurrentCopy();
		return currCopy;
	}
	
	public void setTypeCopy(Integer tipo) {
	    if (currCopy != null) currCopy.setSubtipo(tipo);	
	}
	
	public StmtCopy addCopy(StmtCopy copy) {
		modules.push(copy);
		return copy;
	}
	
	public void removeCopy() {
		modules.pop();
	}
	
	public String getCopyName() {
		StmtCopy cpy = (StmtCopy) modules.peek();
		return cpy.getCopyName();
	}

	public ArrayList<String> getReplacingTokens() {
		StmtCopy cpy = (StmtCopy) modules.peek();
		return cpy.getReplacingTokens();
	}
	
	public void markCopyIgnored() {
		StmtCopy cpy = (StmtCopy) modules.peek();
		cpy.setIgnored();
	}
	
	// Se usa para el caso de EXEC SQL INCLUDE
	
	public void    setIncludeParsed(boolean inc) { includeParsed = inc; }
	public boolean isIncludeParsed()             { return includeParsed; }

	/***********************************************************/
	/***  Gestion del offset de las lineas                   ***/
	/***********************************************************/

	public void addOffset(int line) {
		offset.push(line);
	}
	
	public void removeOffset() {
		offset.pop();
	}
	
	public int getOffset() {
		return offset.peek();
	}
	
	public void addMember(String name) {
		members.push(name);
	}
	
	public void removeMember() {
		members.pop();
	}
	
	public String getMemberName() {
		StringBuilder name = new StringBuilder(members.elementAt(0));
		for (int idx = 1; idx < members.size(); idx++) {
			name.append(" [" + members.elementAt(idx) + "]");
		}
		return name.toString();
	}

	public int getNumSources() {
		return members.size();
	}
}
