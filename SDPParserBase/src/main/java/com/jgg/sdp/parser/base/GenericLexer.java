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

import com.jgg.sdp.core.config.Configuration;
//import com.jgg.sdp.parser.work.CopyLoader;
import com.jgg.sdp.rules.*;
import com.jgg.sdp.module.base.*;
//import com.jgg.sdp.info.module.items.Copy;
import com.jgg.sdp.module.unit.*;

import java_cup.runtime.*;

public abstract class GenericLexer implements ILexer {

   protected abstract void   yybegin(int newState);
   protected abstract String yytext();
   protected abstract int    yylength();
   
   public abstract Symbol next_token() throws java.io.IOException;
   public abstract void   yypushStream(java.io.Reader reader);

   protected Stack<Integer>       stack = new Stack<Integer>();
   protected ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   protected SDPUnit         unit   = null;
   protected boolean         data   = false;
   protected int             lastID = -1; 

   // Usado para generar tokens de tipo cadena (con espacios)
   protected StringBuilder cadena  = new StringBuilder("");
   protected int           cadLine = 0;
   protected int           cadCol  = 0;
   
   protected int           litLine;
   protected int           litColumn;
   protected boolean       litQuote;
   protected boolean       litTrim = false;
   
   protected boolean inCode      = false;  // Estamos en PROCEDURE DIVISION?
   protected boolean inDesc      = false;  // Procesando Descripcion?
   protected boolean inCopy      = false;  // Estamos en estado COPY?       
   
   protected Configuration cfg = Configuration.getInstance();

   // Para el control de COPYs e INCLUDEs
   
   protected boolean                  isCopy    = false;
   protected int                      begCopy   = -1;   // Linea de la sentencia de COPY/INCLUDE
   protected ArrayList<StringBuilder> chgTokens = null;
   protected StringBuilder            chgToken  = null;
   protected RulesChecker             rules     = null;   
   
   protected Symbol      exec      = null;
   protected Symbol      endExec   = null;

   protected ParserInfo  info = ParserInfo.getInstance();
   
   protected       int offset;   
   protected final int COLOFFSET = cfg.getMarginLeft();
   
   private boolean ignoreReserved = false;
   
   
   // Control de bloques de comentarios
   private int lastLineComment    = -1;
   private boolean inCommentBlock = false;

   public void setModule(Module module) {
//	   this.module = module;
	   rules = new RulesChecker(info.module);
   }
   
   public void setParseUnit (SDPUnit unit) {
	   this.unit = unit;
	   setModule(unit.getCurrentModule());
   }
   
   protected void initLexer() {
	   data = false;
       stack.push(0);
       offset = info.getOffset();
   }

   
   protected void resetCadena(int yyline, int yycol) {
	   cadena = new StringBuilder();
	   cadLine = yyline;
	   cadCol =  yycol;
   }
   
   /*******************************************************/
   /*** GESTION DE ESTADOS                              ***/
   /*******************************************************/

   // JGG Debug Issues
   private void yybegin2(int state) {
/*
	      switch(state) {
		       case   0:                   System.out.println("Estado: YYINITIAL"); break;
		       case   2:                 System.out.println("Estado: ID_DIVISION"); break;
		       case   4:                System.out.println("Estado: ENV_DIVISION"); break;
		       case   6:               System.out.println("Estado: DATA_DIVISION"); break;
		       case   8:               
		    	   System.out.println("Estado: PROC_DIVISION"); break;
		       case  10:                  System.out.println("Estado: CONF_SECT"); break;
		       case  12:                    System.out.println("Estado: IO_SECT"); break;
		       case  14:                        System.out.println("Estado: PIC"); break;
		       case  16:                   System.out.println("Estado: BLOBSIZE"); break;
		       case  18:                    System.out.println("Estado: ENDLINE"); break;
		       case  20:                    System.out.println("Estado: EATLINE"); break;
		       case  22:                     System.out.println("Estado: STEXEC"); break;
		       case  24:                   System.out.println("Estado: EMBEDDED"); break;
		       case  26:                       System.out.println("Estado: SQL2"); break;
		       case  28:             System.out.println("Estado: EMBEDDED_QUOTE"); break; 
		       case  30:            System.out.println("Estado: EMBEDDED_DQUOTE"); break;
		       case  32:                    System.out.println("Estado: CICSSYM"); break;
		       case  34:                    System.out.println("Estado: COMMENT"); break;
		       case  36:               System.out.println("Estado: QUOTE_STRING"); break;
		       case  38:              System.out.println("Estado: DQUOTE_STRING"); break;
		       case  40:                        System.out.println("Estado: SDP"); break;
		       case  42:                      System.out.println("Estado: COPYS"); break;
		       default: System.out.println("Estado: " + state);
	     }
*/
	      yybegin(state);
	   }
   protected void pushState(int newState) {
       stack.push(newState);
       yybegin2(newState);
   	}
	
    protected void popState(int times) {
    	for (int i = 0; i < times - 1; i++) {
    		if (!stack.isEmpty()) stack.pop();
    	}
    	popState();

    }
    
    public void popState() {
       if (!stack.isEmpty()) stack.pop();
       yybegin2(stack.peek().intValue());
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
   
   protected Symbol makeSymbol(int code, int yyline, int yycolumn, String token) {
	      data = true;
	      lastID = code;
	      
	      int line = offset + 1 + yyline;
	      int col = yycolumn + COLOFFSET;
	      
	      print("Devuelve SYMBOL(" + code + ") - (" + line + "," + col + ") " + token);
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
	      
	      print("Devuelve LITERAL (" + code + ") - " + texto);
	      Symbol s = new Symbol(code, litLine, litColumn, texto);
	      return info.setLastSymbol(symbolFactory.newSymbol(texto, code, s));
	   }

   /*******************************************************/
   /*** GESTION DE COMENTARIOS                          ***/
   /*******************************************************/
   
   protected void checkSymbol(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
//	   rules.checkSymbol((String) s.value,  s.left, s.right);
   }
   
   protected void checkLiteral(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
//	   rules.checkSymbol(" ",  s.left, s.right);
   }
   
   protected void setComment(String txt , int yyline, int yycolumn) {
	   
	   if (txt.charAt(0) != '*') {
	       rules.checkComment(txt.substring(0, 1), yyline);
	   }
	   
	   int diff = yyline - lastLineComment;
	   if (diff == 1) {
		   if (inCommentBlock == false) {
			   inCommentBlock = true;
			   info.module.incCommentBlocks();
		   }
	   }
	   else {
		   inCommentBlock = false;
	   }
	   lastLineComment = yyline;
   }
/*   
   public Symbol symbolic(String value) {
	   cadena.setLength(0);
	   return literal(value);
   }
   */
   public void setIgnoreReserved()   { ignoreReserved = true;  }
   public void unsetIgnoreReserved() { ignoreReserved = false; }
   public boolean isIgnoreReserved() { return ignoreReserved;  }
   
   public void print(String txt) {
//         System.out.println(txt);
   }   

   protected String removeQuotes(String txt) {
	   return txt.substring(1, txt.length() - 1);
   }
   
   protected void setBegCopy(int yyline, boolean isCopy) {
	   chgTokens = null;
	   this.isCopy = isCopy;
//	   if (info.getNumSources() == 1) begCopy = yyline;
   }
 
   /*
   protected void loadCopy(boolean popState, int tipo) {
	   
	   String name = info.getCopyName();
	   
	   currCopy = info.getCurrentCopy();
	   
	   currCopy.setNombre(name);
	   currCopy.setTipo(tipo);
	   
	   if (cfg.isIgnored(name)) {
		   info.markCopyIgnored();
		   info.module.setCopyStatus(CDG.CPY_ST_IGNORED);
		   currCopy.setEstado(CDG.CPY_ST_IGNORED);
		   info.module.addCopy(currCopy);
		   info.module.setStatus(CDG.STATUS_PARTIAL);
		   if (popState) popState();
		   return;
	   }

	   Source source = loader.load(name, info.getReplacingTokens());
	   if (source != null) {
//		   info.module.setCopyStatus(CDG.CPY_MISSING);
		   unit.addSource(source);
		   info.addMember(name);
		   currCopy.setEstado(CDG.CPY_ST_FULL);
		   yypushStream(source);
	   }
	   else {
	      info.module.setStatus(CDG.STATUS_PARTIAL);
	      currCopy.setEstado(CDG.CPY_ST_MISSING);
	   }
	   
	   if (popState) popState();
	   
	   info.setCurrentCopy(info.module.addCopy(currCopy));
   }
*/
   protected void initEmbedded() {
       info.buffer = new StringBuilder(4096);
   }

   /********************************************************/
   /* Issues en el analizador lexico                       */
   /********************************************************/
/*   
   protected void checkRule(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
	   int column = s.right + 1 + cfg.getMarginLeft();
	   for (Integer i : rules.check(s.sym, (String) s.value)) {
		   Issue issue = new Issue(i, s.left, column);
		   issue.setLastLine(s.left);
		   issue.setLastColumn(column);
		   info.module.addIssue(issue);
	   }
   }
*/
   protected void checkDivision() {
//   	   if (info.module.getNumSources() != 1) {
//	       issues.addIssue(ISSUE.DIV_IN_COPY, begCopy + 1, 0);
//	   } 
   }

   
   /*
   protected void issue(int type, int line, int column) { 
	   issues.addIssue(type, line + 1, column); 
   }
   

  */              	   
   /********************************************************/
   /* Actuan como proxies de next_token                    */
   /* Cuando entra en un parser secundario se queda en el  */
   /* Hasta que se indique el final                        */
   /********************************************************/
/*
   public Symbol tokenCobolData() throws Exception {

	   if (sendEOF == true) {
	       sendEOF = false;
		   return new Symbol(ZCZSym.EOF);		   
	   }
	   
	   Symbol s = next_token();

       switch (s.sym) {
         case ZCDSym.EXEC_CICS:	    	  
    	      parserType = Parsers.CICS; 
    	      break;
         case ZCDSym.EXEC_SQL:	    	  
    	      parserType = Parsers.DB2;  
    	      break;
       }

       // Casos: SQL | COPY
       //        SQL | COPY
	   while (specialDataSymbol(s.sym)) {
		 switch (s.sym) {
	         case ZCDSym.END_EXEC:	    	  
	    	      alterParser();
	    	      s =  next_token();
	    	      break;
	         case ZCZSym.COPY:
	        	  sym = createCopy(s);
	    	      parseCopy();
	    	      s =  next_token();
                  break;
           case ZCCSym.DIV_PROC:
        	   Symbol ss = (Symbol) s.value;
    	      if (((String) ss.value).toUpperCase().startsWith("PROCEDURE") ) {
    	          sendEOF = true;
    	          begParser = true;
    	          lastSym = s;
	              return new Symbol(ZCZSym.EOF);
    	      }
    	      return s;
		 }    
	   }
	   return s;
   }

   public Symbol tokenCobolCode() throws Exception {

	   if (begParser ) {
		   begParser = false;
		   return lastSym;
	   }
	   
	   Symbol s = next_token();

       switch (s.sym) {
         case ZCCSym.EXEC_CICS:	    	  
    	      parserType = Parsers.CICS; 
    	      break;
         case ZCCSym.EXEC_SQL:	    	  
    	      parserType = Parsers.DB2;  
    	      break;
       }

       // Casos: SQL | COPY
       //        SQL | COPY
	   while (specialCodeSymbol(s.sym)) {
		 switch (s.sym) {
	         case ZCCSym.END_EXEC:	    	  
	    	      alterParser();
	    	      s =  next_token();
	    	      break;
	         case ZCZSym.COPY:
	        	  sym = createCopy(s);
	    	      parseCopy();
	    	      s =  next_token();
                  break;
	       }
	   }
	   return s;
   }

   private boolean specialDataSymbol (int sym) {
	   switch (sym) {
	      case ZCDSym.END_EXEC:	    	  
	      case ZCZSym.COPY:
	      case ZCCSym.DIV_PROC:	    	  
	    	   return true;
	   }
	   return false;
   }

   private boolean specialCodeSymbol (int sym) {
	   switch (sym) {
	      case ZCCSym.END_EXEC:	    	  
	      case ZCZSym.COPY:
	    	   return true;
	   }
	   return false;
   }

   private Symbol createCopy(Symbol s) {
	   currCopy = info.createtCurrentCopy();
	   currCopy.setBegLine(s.left);
	   currCopy.setBegColumn(s.right);
	   return s;
   }
   
   public Symbol tokenCICS() throws Exception {
	   return next_token();
   }
 
   private void alterParser() throws Exception { 
    
	   switch (parserType) {
            case Parsers.CICS: parseCICS(); break;
            case Parsers.DB2:  parseSQL();  break;
	   }
	   info.removeOffset();
   }
*/
	public Symbol tokenCobolData() throws java.lang.Exception {
		return null;
	}
	public Symbol tokenCobolCode() throws java.lang.Exception {
		return null;
	}

	public Symbol tokenCICS () throws java.lang.Exception {
		return null;
	}
	public Symbol tokenCOPY () throws java.lang.Exception {
		return null;
	}
   
   public Symbol tokenSQL() throws Exception {
	   Symbol s = next_token(); 
	   return s;
   }
/*
   public Symbol tokenCOPY() throws Exception {
	   if (begParser) {
		   sym.sym = COPYSym.CPY_COPY;
		   begParser = false;
		   return sym;
	   }
	   if (sendEOF == true) {
		   return new Symbol(COPYSym.EOF);
	   }
	   Symbol s = next_token(); 
	   if (s.sym == COPYSym.CPY_COPYEND) sendEOF = true;
	   return s;
   }


   private void parseSQL() throws Exception {
	   info.setIncludeParsed(false);
	   Source source = new Source(new Archivo("temp.sql"), true);
	   source.setData(buffer.toString());
   	   ILexer lexer = FactoryLexer.getLexer(source, Parsers.DB2);
   	   GenericParser parser = FactoryParser.getParser((GenericScanner) lexer, Parsers.DB2);
   	   parser.parse();
   	   if (info.isIncludeParsed()) loadCopy(false, CDG.DEP_INCLUDE);
   }

   private void parseCICS() throws Exception {
	   Source source = new Source(new Archivo("temp.sql"), true);
	   source.setData(buffer.toString());
   	   ILexer lexer = FactoryLexer.getLexer(source, Parsers.CICS);
   	   GenericParser parser = FactoryParser.getParser((GenericScanner) lexer, Parsers.CICS);
   	   parser.parse();
   }
   
   private Symbol parseCopy() throws Exception {
	   begParser = true;
   	   GenericParser parser = FactoryParser.getParser((GenericScanner) this, Parsers.COPY);
   	   parser.parse();
	   sendEOF = false;
	   loadCopy(true, CDG.DEP_COPY);
	   return null;
   }
*/
}


