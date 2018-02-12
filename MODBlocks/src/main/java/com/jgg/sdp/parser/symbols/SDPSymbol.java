package com.jgg.sdp.parser.symbols;

import java.util.ArrayList;

import com.jgg.sdp.blocks.reflect.IReflect;

public class SDPSymbol  implements IReflect {
	public int id     = -1;
	public int sym    = -1;
	public int line   = -1;
	public int column = -1;
	public String value;

	public boolean function = false;  // El objeto va entre parentesis
	public boolean comma    = false;  // Los hijos van separados por comas

	public SDPSymbol parent = null;
	public SDPSymbol prefix = null;
	public SDPSymbol suffix = null;
	
	public ArrayList<SDPSymbol> syms = new ArrayList<SDPSymbol>();

	private StringBuilder sb = null;
	
	public SDPSymbol(int id, String value) {
		this(id,-1,-1,value);
	}
	
	public SDPSymbol(int id, int line, int column, String value) {
		this.id     = id;
		this.sym    = id;
		this.value  = value;
		this.line   = line;
		this.column = column;
	}

	public SDPSymbol(SDPSymbol other) {
		this.id     = other.id;
		this.sym    = other.id;
		this.value  = other.value;
		this.line   = other.line;
		this.column = other.column;
	}
	

	/************************************************/
	/*   SETTERS AND GETTERS                        */
	/************************************************/
	
    public int getId() {
		return id;
	}

	public SDPSymbol setId(int id) {
		this.id = id;
		return this;
	}

	public int getLine() {
		return line;
	}

	public SDPSymbol setLine(int line) {
		this.line = line;
		return this;
	}

	public int getColumn() {
		return column;
	}

	public SDPSymbol setColumn(int column) {
		this.column = column;
		return this;
	}

	public String getValue() {
		return value;
	}

	public SDPSymbol setValue(String value) {
		this.value = value;
		return this;
	}

	public SDPSymbol getParent() {
		return parent;
	}
	public String getParentName() {
		return (parent == null) ? null : parent.value;
	}

	public SDPSymbol setParent(SDPSymbol parent) {
		this.parent = parent;
		return this;
	}

	public SDPSymbol getPrefix() {
		return prefix;
	}

	public SDPSymbol setPrefix(SDPSymbol prefix) {
		this.prefix = prefix;
		return this;
	}

	public SDPSymbol getSuffix() {
		return suffix;
	}

	public SDPSymbol setSuffix(SDPSymbol suffix) {
		this.suffix = suffix;
		return this;
	}

	public SDPSymbol setFunction() {
		function = true;
		return this;
	}

	public SDPSymbol setComma() {
		comma = true;
		return this;
	}
	public SDPSymbol setComma(SDPSymbol c) {
		if (c != null) comma = true;
		return this;
	}

	public SDPSymbol add(SDPSymbol... s) {
		int idx;
		for (idx = 0; idx < s.length; idx++) {
			if (s[idx] != null) break;
		}
		if (idx < s.length && s[idx] != null) {
			syms.add(s[idx]);
			s[idx].add(idx, s);
		}
		
		return this;
	}

	private SDPSymbol add(int me, SDPSymbol... s) {
		int idx;
		for (idx = me + 1; idx < s.length; idx++) {
			if (s[idx] != null) break;
		}
		if (idx < s.length && s[idx] != null) {
			s[me].add(s[idx]);
			s[idx].add(idx, s);
		}
		
		return this;
	}
	
	public ArrayList<SDPSymbol> getSymbols() {
		return syms;
	}
	
	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add(value);
		for (SDPSymbol s : syms) {
			values.addAll(s.getValues());
		}
		return values;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public String toText() {
		String sep = (comma) ? ", " : " ";
		if (sb != null) return sb.toString();
		sb = new StringBuilder();

		if (function)            sb.append('(');
		
		if (prefix != null)      sb.append(prefix.toText() + ' ');
		sb.append(value);
		if (suffix != null)      sb.append(' ' + suffix.toText());
		if (parent != null)      sb.append(" OF " + parent.toText());
		for (SDPSymbol s : syms) sb.append(sep + s.toText());
		if (function)            sb.append(')');
		
		return sb.toString();
	}

	public String toString(){
        return value;
    }
	
	public int    getLines()     { return 1;      }
	public int    getBegLine()   { return line;   }
	public int    getBegColumn() { return column; }
	public int    getEndLine()   { return line;   }
	public int    getEndColumn() { return column + value.length(); }
    
    
}
