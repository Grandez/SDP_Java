/**
 * Clase base para los analizadores lexicos 
 * Implementa funciones de utilidad que no estan implementadas 
 * en el analizador lexico generado por JFLEX
 *  
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.cics.base;

import java.util.*;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.base.Summary;
import com.jgg.sdp.module.factorias.ModulesFactory;

import java_cup.runtime.*;

public abstract class GenericLexer {

   public abstract void   yybegin(int newState);
   public abstract String yytext();
   public abstract int    yylength();
   public abstract Symbol next_token() throws java.io.IOException;
   public abstract Symbol literal(String txt);
      
   protected Stack<Integer> stack = new Stack<Integer>();
   protected ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   protected Module  module       = null;
   protected Summary summary      = null;
   protected boolean data         = false;
   
   protected StringBuilder cadena = new StringBuilder("");
   protected StringBuilder buffer = null;
   
   protected int           litLine;
   protected int           litColumn;
   protected boolean       litQuote;
   protected boolean       litTrim = false;
   
   protected boolean inCode      = false;  // Estamos en PROCEDURE DIVISION?
   protected boolean inDesc      = false;  // Procesando Descripcion?
   protected boolean pendingEndP = false;  // Falta un punto?
       

//   private CopyLoader  loader = new CopyLoader();

   protected void initLexer() {
	   data = false;
       stack.push(0);
   }
   
   protected void pushState(int newState) {
	     stack.push(newState);
	     yybegin(newState);
   	}
	
    protected void popState(int times) {
    	for (int i = 0; i < times - 1; i++) {
    		if (!stack.isEmpty()) stack.pop();
    	}
    	popState();

    }
    
    protected void popState() {
       if (!stack.isEmpty()) stack.pop();
       yybegin(stack.peek().intValue());
   }    

   public void resetState(int newState) {
	      while (!stack.empty()) stack.pop();
	      stack.push(0);  // Estado inicial
	      pushState(newState);
   }

   public void setFullName(String fullName) {
      module = ModulesFactory.getModule(fullName);
      summary = module.getSummary();
   }
   
   public String getFullName() {
      return module.getFullName();
   }
   
   public Symbol symbolic(String value) {
	   cadena.setLength(0);
	   return literal(value);
   }
      
   /*
    * Se usa para concatenar las cadenas que estan definidas en dos lineas
    */
   protected void trimLiteral(boolean quote) {
	   litQuote = quote;
	   int len = cadena.length();
	   int idx = len - 1;
	   
	   while (idx > 0 && cadena.charAt(idx) == ' ') idx--;
	   cadena.setLength(idx + 1);
   }

   
   public void print(String txt) {
//      System.out.println(txt);
   }   

   protected String removeQuotes(String txt) {
	   return txt.substring(1, txt.length() - 1);
   }
/*   
   protected Source loadCopy(String name, ArrayList<StringBuilder> toks ) {
	   Source source = loader.load(name, toks);
	   if (source == null) {
		   module.addMissing(name);
	   }
	   else {
		   module.addSource(source);
	   }
	   return source;
   }
*/
   public Symbol tokenCICS() throws Exception {
	   return next_token();
   }
   
}