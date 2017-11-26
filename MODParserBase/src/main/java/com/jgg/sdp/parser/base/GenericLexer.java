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

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.core.exceptions.NotSupportedException;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.unit.Unit;
import com.jgg.sdp.module.work.CommentBlock;
import com.jgg.sdp.module.work.CommentLine;
import com.jgg.sdp.rules.components.RulesBase;

import java_cup.runtime.*;

public abstract class GenericLexer {

   protected abstract void   yybegin(int newState);
   protected abstract String yytext();
   protected abstract int    yylength();
   
   public abstract Symbol next_token() throws IOException, ParseException ;
   public abstract void   yypushStream(java.io.Reader reader);

   protected Stack<Integer>       stack = new Stack<Integer>();
   protected ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   protected Unit      unit   = null;
   protected boolean   data   = false;
   protected int       lastID = -1; 

   // Usado para generar tokens de tipo cadena (con espacios)
   protected StringBuilder cadena  = new StringBuilder("");
   protected int           cadLine = 0;
   protected int           cadCol  = 0;
   
   protected int           litLine;
   protected int           litColumn;
   protected boolean       litQuote;
   protected boolean       litTrim = false;

   protected int    lastSymbol = -1;
   protected int    prevSymbol = -1;
   
   protected boolean inDesc      = false;  // Procesando Descripcion?      
   
   protected Configuration cfg = ConfigurationBase.getInstance();

   // Para el control de COPYs e INCLUDEs
   
   protected boolean                  isCopy    = false;
   protected int                      begCopy   = -1;   // Linea de la sentencia de COPY/INCLUDE
   protected ArrayList<StringBuilder> chgTokens = null;
   protected StringBuilder            chgToken  = null;
   
   
   protected Symbol      exec      = null;
   protected Symbol      endExec   = null;

   protected ParserInfo  info = ParserInfo.getInstance();
   
   protected       int linOffset;   
   protected final int COLOFFSET = cfg.getMarginLeft();

   private RulesBase rules = new RulesBase();
   
   private boolean ignoreReserved = false;

   private CommentBlock cmt     = null;

   public void setModule(Module module) {
//	   rules = new RulesChecker(info.module);
//	   cmt.setModule(module);
   }
   
   public void setParseUnit (Unit unit) {
	   this.unit = unit;
//	   setModule(unit.getCurrentModule());
   }
   
   protected void initLexer() {
       initLexer(-1);
//       cmt.setModule(info.getModule());
   }
   
   protected void initLexer(int commentState) {
	   data = false;
       stack.push(0);
       linOffset = info.getOffset();
   }
   
   protected void resetCadena(int yyline, int yycol) {
	   cadena = new StringBuilder();
	   cadLine = yyline;
	   cadCol =  yycol;
   }

   /*******************************************************/
   /*** GESTION DE ESTADOS                              ***/
   /*******************************************************/

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
    
    public void popState() {
       if (!stack.isEmpty()) stack.pop();
       yybegin(stack.peek().intValue());
   }    

   public void resetState(int newState) {
      while (!stack.empty()) stack.pop();
      stack.push(0);  // Estado inicial
      pushState(newState);
   }

   public int getState() {
	   return stack.peek();
   }

   /*******************************************************/
   /*** GESTION DE SIMBOLOS                             ***/
   /*******************************************************/

   protected Symbol symbol(int code, String txt, int yyline, int yycolumn) {
	      setLastSymbol(code);
	      data = true;
	      int col = yycolumn + COLOFFSET;
	      int line = yyline + linOffset;
	      print("Devuelve SYMBOL " + code + " (" + line + "," + col + ") " + txt);   
	      return symbolFactory.newSymbol(txt, code, new Symbol(code, line, col, txt));
   }

   protected Symbol literal(int code, boolean clean) { 
       String txt = cadena.toString();
       if (clean) cadena.setLength(0);
       return literal(code, txt); 
   }
   
   protected Symbol literal(int code, String txt) {
	      setLastSymbol(code);
	      print("Devuelve LITERAL - " + txt);
	      Symbol s = new Symbol(code, litLine, litColumn, txt);
	      Symbol x = symbolFactory.newSymbol(txt, code, s);
	      
	      // Espacio es el primer caracter no imprimible en ASCII
	      // Character.codePointAt(new char[] {'a'},0)
	      
//	      for (int idx = 0; idx < txt.length(); idx++) {
//	          if (txt.charAt(idx) < ' ') {
//	              ruleNoPrintable(litLine, litColumn);
//	              break;
//	          } 
//	      }
	      return x;      
	   }
   
   protected Symbol symbolDummy(int code) {
	      data = true;
	      lastSymbol = 0;
	      return symbol(code, "EXEC", -1, -1);
   }

   private void setLastSymbol(int id) {
	      prevSymbol = lastSymbol;
	      lastSymbol = id;
   }

  protected void excepcion(int code, String txt, int line, int col) {
      throw new NotSupportedException(code, info.module.getName(), line + info.getOffset() + 1, col + 1, txt);
  }
   
   
   protected Symbol makeSymbol(int code, int yyline, int yycolumn, String token) {
	      data = true;
	      lastID = code;
	      
	      int line = linOffset + 1 + yyline;
	      int col = yycolumn + COLOFFSET;
	      
	      print ("Devuelve SYMBOL(" + code + ") - (" + line + "," + col + ") " + token);
	      Symbol s = new Symbol(code, line, col, token);
	      return info.setLastSymbol(symbolFactory.newSymbol(token, code, s));
   }
   
   // code = LITERAL, pero depende del parser
   // Se procesa en su estado, por lo que hay que quitarlo
   protected Symbol literal(int code) {
	      lastID = code;
	      String texto = cadena.toString();
	      cadena.setLength(0);
	      
	      popState();
	      
	      print ("Devuelve LITERAL (" + code + ") - " + texto);
	      Symbol s = new Symbol(code, litLine, litColumn, texto);
	      return info.setLastSymbol(symbolFactory.newSymbol(texto, code, s));
	   }

   protected void checkSymbol(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
//	   rules.checkSymbol((String) s.value,  s.left, s.right);
   }
   
   protected void checkLiteral(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
//	   rules.checkSymbol(" ",  s.left, s.right);
   }

   /********************************************************/
   /* Comentarios                                          */
   /********************************************************/
   
   protected void commentInit(String txt , int line) {
	   if (cmt != null) {
		   if (line - cmt.getLastComment().getLine() > 1) {
			   info.getModule().getComment().incBlocks();
			   cmt = new CommentBlock();
		   }
	   }
	   else {	   
		  cmt = new CommentBlock();
	   }
	   
	   cmt.addLine(txt, line);
   }
   
   protected void commentAppend(String txt) {
       cmt.appendLine(txt);
   }
   
   protected void commentEnd(int line) {
       cmt.endLine(line);
       processComment(cmt.getLastComment());
       popState();
   }
   
   private void processComment(CommentLine comment) {
       int pos = comment.getRawComment().indexOf('\t');
	   if ( pos != -1) rules.checkTab(comment.getLine(), COLOFFSET + pos);  
	   
	   info.getModule().getComment().incLines();
	   if (comment.isDecorator()) info.getModule().getComment().incDecorators();
       if (comment.isComment())   info.getModule().getComment().incDocs();
       rules.checkComment(comment);
   }
	   
   public void setIgnoreReserved()   { ignoreReserved = true;  }
   public void unsetIgnoreReserved() { ignoreReserved = false; }
   public boolean isIgnoreReserved() { return ignoreReserved;  }
   
   public void print(String txt) {
//        System.out.println(txt);
   }   
   public void debug(String txt) {
     System.out.println(txt);
   }   

   protected String removeQuotes(String txt) {
	   return txt.substring(1, txt.length() - 1);
   }
   
   protected void setBegCopy(int yyline, boolean isCopy) {
	   chgTokens = null;
	   this.isCopy = isCopy;
//	   if (info.getNumSources() == 1) begCopy = yyline;
   }
 
   protected void initEmbedded() {
       info.buffer = new StringBuilder(4096);
   }

   protected void appendEmbedded(String txt) {
	   info.buffer.append(txt);
   }
   
   /********************************************************/
   /* Issues en el analizador lexico                       */
   /********************************************************/
   protected void checkDivision() {
//   	   if (info.module.getNumSources() != 1) {
//	       issues.addIssue(ISSUE.DIV_IN_COPY, begCopy + 1, 0);
//	   } 
   }
   
}


