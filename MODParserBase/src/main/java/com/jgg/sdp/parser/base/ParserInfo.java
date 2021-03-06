/**
 * Se utiliza como singleton para comunicar los diferentes parsers
 * Por extension, se mantienen aqui los objetos externos y comunes
 */
package com.jgg.sdp.parser.base;

import java.util.*;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.Copy;
import com.jgg.sdp.module.ivp.IVPCase;
import com.jgg.sdp.module.unit.Unit;
import com.jgg.sdp.parser.symbols.*;


public class ParserInfo {

	private static ParserInfo info = null;

	private Configuration cfg = ConfigurationBase.getInstance();
	
	private SDPSymbolFactory symbolFactory = new SDPSymbolFactory();
	   
    public Unit   unit   = null;
    public Module module = null;

    private IVPCase  c = null;
    
	
	private Stack<Integer> sections     = new Stack<Integer>();
	private Stack<Integer> lineSections = new Stack<Integer>();
	
	public StringBuilder buffer = null;
	   
	// Mantiene la linea de inicio de los diferentes parsers
	private Stack<Integer> stkOffset   = new Stack<Integer>();	
	protected int offset = 0;
	
	private boolean includeParsed = false;
	
	private Copy currCopy = null;

	private String moduleName = null;
	
	// Simbolo ultimo.
	// Usado para COPY e INCLUDE
	private SDPSymbol prevSymbol = null;
	private SDPSymbol lastSymbol = null;

	private ParserInfo() {
		stkOffset.add(0);
	}
	
	public static ParserInfo getInstance() {
		if (info == null) 
			info = new ParserInfo();
		return info;
	}
	
	public static ParserInfo getInstance(boolean reset) {
		if (reset) info = null;
		return getInstance();
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Module getModule() {
		return this.module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
//	public SDPSymbol cosa(String txt, int code, int line, int col) {
//		int l = line + getOffset() + 1;
//		//SDPSymbol s = new SDPSymbol(code, l, col, txt); 
//		return symbolFactory.newSymbol(txt, code, new SDPSymbol(code, l, col, txt));
//	}
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
	
 		
	// Se usa para el caso de EXEC SQL INCLUDE
	
	public void    setIncludeParsed(boolean inc) { includeParsed = inc; }
	public boolean isIncludeParsed()             { return includeParsed; }

	/***********************************************************/
	/***  Gestion del stkOffset de las lineas                ***/
	/***********************************************************/

	public void addOffset(int line) {
	    stkOffset.push(line);
	}

	public int getOffset() {
	    return stkOffset.empty() ? 0 : stkOffset.peek();
	}

    public int removeOffset() {
        if (stkOffset.size() > 1) return stkOffset.pop();
        return 0;
    }
    
	
	public int getOffsetDepth() {
		return stkOffset.size();
	}
	
	public void removeMember() {
		unit.removeSource();
		unit.removeMember();
	}

    /***********************************************************/
    /***  Gestion de miembros                                ***/
    /***********************************************************/
	
	public String getMemberName() {
		if (unit == null) { // fuera del proceso
			return "NONAME";
		}
		
		String[] members = unit.getStackOfMembers();
		
		// Obviamente, siempre hay un mimebro
		StringBuilder name = new StringBuilder(members[0]);
		
		if (members.length > 1) {
			name.append(" [");
			for (int idx = 1; idx < members.length; idx++) {
				if (idx > 1) name.append(", ");
	            name.append(members[idx]);
            }
			name.append("] ");
		}
		
		return name.toString();
	}
	
	public SDPSymbol setPrevSymbol(SDPSymbol s) {
		prevSymbol = s;
		return s;
	}
	
	public SDPSymbol getPrevSymbol() {
		return prevSymbol;
	}
	
	public SDPSymbol setLastSymbol(SDPSymbol s) {
		lastSymbol = s;
		return s;
	}
	
	public SDPSymbol getLastSymbol() {
		return lastSymbol;
	}
	
	/***********************************************************/
	/***  Gestion de errores                                 ***/
	/***********************************************************/

	public void syntax_error (SDPSymbol s) {
        
//       int col = cfg.getInteger(CFG.MARGIN_LEFT,  0);
       int col = 0;
       col = col + s.column + 1;

       throw new ParseException(MSG.EXCEPTION_SYNTAX, 
                                info.getMemberName(), 
                                s.line,  
                                col, 
                                s.value);
   }

   public void unrecovered_syntax_error(SDPSymbol s) {
       throw new ParseException(MSG.EXCEPTION_CUP, 
                               getMemberName(), 
                               s.line, 
                               s.column + 1, 
                               s.value); 
   }

	/***********************************************************/
	/***  Gestion de IVP                                     ***/
	/***********************************************************/
   
   public void prepareCase(String data) {
	   String[] toks = data.split(" - ");
	   c = new IVPCase();
	   if (toks.length > 1) c.setDescription(toks[1].trim());
	   toks = toks[0].trim().split("[ ]+");
	   c.setGroup(Integer.parseInt(toks[0]));
	   c.setComponent(toks[1]);
	   c.setOperator(toks[2]);
	   c.setValue(toks[3]);
	   module.addIVPCase(c);
   }
   
}
