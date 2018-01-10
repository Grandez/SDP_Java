/**
 * Se utiliza para mantener las variables del tipo A OF B
 */
package com.jgg.sdp.blocks.symbols;

import java.util.ArrayList;

import com.jgg.sdp.blocks.reflect.IReflect;

import java_cup.runtime.Symbol;

public class SymbolExt extends Symbol implements IReflect {
      private Symbol current = null;
      
      private StringBuilder sb = new StringBuilder();
      
      private ArrayList<Symbol> parents  = new ArrayList<Symbol>();
      private Integer    id = null;;
      
      private int begLine;
      private int endLine;
      private int begColumn;
      private int endColumn;
      
      public SymbolExt(Symbol s) {
    	  super(s.sym, s.left, s.right, s.value);
    	  first(s);
      }

      public SymbolExt(Symbol s, String newName) {
    	  super(s.sym, s.left, s.right, newName);
    	  first(s, newName);
    	  current.value = newName;
      }
      
      public SymbolExt add(Symbol p) {
    	  if (((String) p.value).trim().length() == 0) return this;
    	  parents.add(p);
    	  last(p);
    	  return this;
      }
      
      public String getValue() {
    	  return sb.toString();
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
    	  return parents;
      }
      public Symbol getParent() {
    	  if (parents.size() == 0) return null;
    	  return parents.get(0);
      }
      
      public String getName() {
          if (current.value instanceof Symbol) {
			  return(String) ((Symbol) current.value).value;
          }
    	  return (String) current.value;
      }
      
      public String getParentName() {
    	  if (parents.size() == 0) return null;
    	  return (String) parents.get(0).value;
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
      
      private void last(Symbol s) {
    	  endLine = s.left;
    	  endColumn = s.right;
    	  sb.append(" ");
    	  sb.append((String) s.value);
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
