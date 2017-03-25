/**
 * Se utiliza para mantener las variables del tipo A OF B
 */
package com.jgg.sdp.parser.base.symbol;

import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class SymbolExt extends Symbol {
      private Symbol current = null;
      private ArrayList<Symbol> parents  = new ArrayList<Symbol>();
      private Integer    id = null;;
      
      public SymbolExt(Symbol s) {
    	  super(s.sym, s.left, s.right, s.value);
    	  current = s;
      }

      public SymbolExt(Symbol s, SymbolExt p) {
    	  super(s.sym, s.left, s.right, s.value);
    	  current = s; 
    	  if (p != null) {
    	      parents.add(p.getSymbol());
    	      parents.addAll(p.getParents());
    	  }
      }

      public SymbolExt(Symbol s, Symbol p) {
    	  super(s.sym, s.left, s.right, s.value);
    	  current = s;
    	  if (p != null) addParent(p);
      }
      
      public SymbolExt(Symbol s, String newName) {
    	  super(s.sym, s.left, s.right, newName);
    	  current = s;
    	  current.value = newName;
      }
      
      public SymbolExt addParent(Symbol p) {
    	  parents.add(p);
    	  return this;
      }
      
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
}
