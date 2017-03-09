/**
 * Clase base para los analizadores lexicos 
 * Implementa funciones de utilidad que no estan implementadas 
 * en el analizador lexico generado por JFLEX
 *  
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser.base;

import java.util.*;

import java_cup.runtime.ComplexSymbolFactory;

public abstract class GenericLexer {
	
   protected Stack<Integer> stack = new Stack<Integer>();
   protected ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   /**
     * Inicia un nuevo estado de analisis
     *
     * @param newState El estado a iniciar
     */
   public abstract void   yybegin(int newState);
   
   /**
     * Devuelve el token analizado
     *
     * @return token analizado
     */
   public abstract String yytext();
   
   /**
     * Devuelve la longitud del token analizado
     *
     * @return longitud del token
     */
   public abstract int    yylength();
   
	   
   /**
     * Establece el analizador a un nuevo estado<br>
     * Mantiene la pila de estados.
     *
     * @param newState
     *            Nuevo estado
     */
   	public void pushState(int newState) {
	     stack.push(newState);
	     yybegin(newState);
   	}
	
	/**
     * Retorna el analizador al estado anterior al actual.
     */
	public void popState() {
		   if (!stack.isEmpty()) {
			   stack.pop();
		   }
		   
	      yybegin(stack.peek().intValue());
   }    

	/**
     * Establece el analizador al estado indicado Si el nuevo estado no es el
     * inicial (YYBEGIN = 0) el estado inicial se conserva.
     *
     * @param newState
     *            Nuevo estado
     */
   	public void resetState(int newState) {
	      while (!stack.empty()) stack.pop();
	      if (newState != 0) stack.push(0);
	      pushState(newState);
	   }
	 
}
