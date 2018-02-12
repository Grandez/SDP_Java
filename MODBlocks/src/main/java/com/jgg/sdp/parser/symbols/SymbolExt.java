/**
 * Se utiliza para mantener las variables del tipo A OF B
 */
package com.jgg.sdp.parser.symbols;

import java.util.ArrayList;

import com.jgg.sdp.blocks.reflect.IReflect;

import java_cup.runtime.Symbol;

public class SymbolExt extends Symbol implements IReflect {
	  private Symbol base    = null;
      private Symbol current = null;
      
      private StringBuilder sb = new StringBuilder();
      
      private ArrayList<Object> parents  = new ArrayList<Object>();
      private Integer    id = null;;
      
      private int begLine;
      private int endLine;
      private int begColumn;
      private int endColumn;
      
      public SymbolExt(Symbol s) {
    	  super(s.sym, s.left, s.right, s.value);
    	  base = s;
    	  first(s);
      }
      public SymbolExt(Symbol s, SymbolExt e) {
    	  this(s);
    	  add(e);
      }

      public SymbolExt(Symbol s, String newName) {
    	  super(s.sym, s.left, s.right, newName);
    	  first(s, newName);
    	  base = s;
    	  current.value = newName;
      }
      
      public SymbolExt add(Object p) {
    	  parents.add(p);
    	  last(p);
    	  return this;
      }
      
      public String getValue() {
    	  return sb.toString();
      }
      
      public Symbol getBase() {
    	  return base;
      }
      
      
/*      
      public SymbolExt(Symbol s, SymbolExt p) {
    	  super(s.sym, s.left, s.right, s.value);
    	  first(s);
    	  if (p != null) {
    	      parents.add(p.getSymbol());
    	      parents.addAll(p.getParents());
    	  }
      }

      public SymbolExt(Symbol s, Symbol p) {
    	  super(s.sym, s.left, s.right, s.value);
    	  first(s);
    	  if (p != null) addParent(p);
      }
      

      public SymbolExt(SymbolList l) {
    	  super(l.getSymbol().sym,l.getSymbol().left, l.getSymbol().right,l.getSymbol().value);
    	  first(l.getSymbol());
    	  StringBuilder sb = new StringBuilder();
    	  for (Symbol s : l.getSymbols()) 
    		  sb.append((String) s.value + " ");
    	  current.value = sb.toString();
      }
      
      public SymbolExt addParent(Symbol p) {
    	  parents.add(p);
    	  return this;
      }
      */
      public Symbol getSymbol() {
    	  return current;
      }
      
      public ArrayList<Symbol> getParents() {
    	  ArrayList<Symbol> syms = new ArrayList<Symbol>();
    	  for (Object o : parents) syms.add((Symbol) o);
    	  return syms;
      }
      public Symbol getParent() {
    	  if (parents.size() == 0) return null;
    	  Object o = parents.get(0);
    	  if (o instanceof SymbolExt) {
    		  return ((SymbolExt) o).getBase();
    	  }
    	  return (Symbol) o;
      }
      
      public String getName() {
          if (current.value instanceof Symbol) {
			  return(String) ((Symbol) current.value).value;
          }
    	  return (String) current.value;
      }
      
      public String getParentName() {
    	  if (parents.size() == 0) return null;
    	  Symbol s = getParent();
    	  return (String) s.value;
      }
      
      public void setId(int id) {
    	  this.id = id;
      }
      
      public int getId() {
    	  if (id == null) return current.sym;
    	  return id;
      }

      private void first(Symbol s, String txt) {
    	  current = s;
    	  begLine = s.left;
    	  begColumn = s.right;
    	  endLine = begLine;
    	  endColumn = begColumn;
    	  sb.append(txt);
      }
      
      private void first(Symbol s) {
    	  current = s;
    	  begLine = s.left;
    	  begColumn = s.right;
    	  endLine = begLine;
    	  endColumn = begColumn;
    	  if (s.value instanceof Symbol) {
    		  sb.append((String) ((Symbol) s.value).value);
    	  } else {
    	      sb.append((String) s.value);
    	  }
      }
      
      private void last(Object s) {
    	  Symbol t = null;
    	  if (s instanceof Symbol) t = (SymbolExt) s;
    	  
    	  endLine = t.left;
    	  endColumn = t.right;
    	  sb.append(" ");
    	  sb.append((String) t.value);
      }

    /***************************************************************/
  	/***   INTERFAZ PARA LLAMADAS INTROSPECTION                  ***/
  	/***************************************************************/
 
    public String toString()  { return (String) current.value; }
    public String toValue()   { return getValue();             }
    public int getBegLine()   { return begLine;     }
    public int getEndLine()   { return endLine;     }
    public int getBegColumn() { return begColumn;   }
    public int getEndColumn() { return endColumn;   }    
    public int getLines()     { return endLine - begLine + 1; }

}
