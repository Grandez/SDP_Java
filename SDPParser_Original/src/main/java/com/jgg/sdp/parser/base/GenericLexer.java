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
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.parser.lang.*;
import com.jgg.sdp.parser.work.CopyLoader;
import com.jgg.sdp.rules.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.Copy;
import com.jgg.sdp.module.unit.*;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.*;

public abstract class GenericLexer {

   public abstract void   yybegin(int newState);
   public abstract String yytext();
   public abstract int    yylength();
   public abstract Symbol next_token() throws java.io.IOException;
   public abstract void   yypushStream(java.io.Reader reader);

   public abstract Symbol literal(String txt);
   
   protected Stack<Integer>       stack = new Stack<Integer>();
   protected ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   protected SDPUnit         unit   = null;
   protected Module          module = null;
   protected boolean         data   = false;

   protected StringBuilder cadena = new StringBuilder("");
   protected StringBuilder buffer = null;
   
   protected int           litLine;
   protected int           litColumn;
   protected boolean       litQuote;
   protected boolean       litTrim = false;
   
   protected boolean inCode      = false;  // Estamos en PROCEDURE DIVISION?
   protected boolean inDesc      = false;  // Procesando Descripcion?
   protected boolean inCopy      = false;  // Estamos en estado COPY?       
   
   protected Configuration cfg = Configuration.getInstance();
   
   private CopyLoader  loader    = new CopyLoader();
   private boolean     sendEOF   = false;
   private boolean     begParser = false;
   private Symbol      sym       = null;
   private Symbol      lastSym   = null;

   private Copy        currCopy  = null;
   
   // Para el control de COPYs e INCLUDEs
   
   protected boolean                  isCopy    = false;
   protected int                      begCopy   = -1;   // Linea de la sentencia de COPY/INCLUDE
   protected ArrayList<StringBuilder> chgTokens = null;
   protected StringBuilder            chgToken  = null;
   protected RulesChecker             rules     = null;   
   
   private   int         parserType  = Parsers.COBOL;
   
   protected Symbol      exec      = null;
   protected Symbol      endExec   = null;

   protected ParserInfo  info = ParserInfo.getInstance();
   
   private boolean ignoreReserved = false;
   
   protected final int OFFSET = cfg.getMarginLeft();
   
   // Control de bloques de comentarios
   private int lastLineComment    = -1;
   private boolean inCommentBlock = false;

   public void setModule(Module module) {
	   this.module = module;
	   rules = new RulesChecker(module);
   }
   
   public void setParseUnit (SDPUnit unit) {
	   this.unit = unit;
	   setModule(unit.getCurrentModule());
   }
   
   protected void initLexer() {
	   data = false;
       stack.push(0);
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
    
    protected void popState() {
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
   /*** GESTION DE COMENTARIOS                          ***/
   /*******************************************************/
   
   protected void checkSymbol(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
	   rules.checkSymbol((String) s.value,  s.left, s.right);
   }
   
   protected void checkLiteral(Symbol sym) {
	   Symbol s = (Symbol) sym.value;
	   rules.checkSymbol(" ",  s.left, s.right);
   }
   
   protected void setComment(String txt , int yyline, int yycolumn) {
	   
	   if (txt.charAt(0) != '*') {
	       rules.checkComment(txt.substring(0, 1), yyline);
	   }
	   
	   int diff = yyline - lastLineComment;
	   if (diff == 1) {
		   if (inCommentBlock == false) {
			   inCommentBlock = true;
			   module.incCommentBlocks();
		   }
	   }
	   else {
		   inCommentBlock = false;
	   }
	   lastLineComment = yyline;
   }
   
   public Symbol symbolic(String value) {
	   cadena.setLength(0);
	   return literal(value);
   }
   
   public void setIgnoreReserved()   { ignoreReserved = true;  }
   public void unsetIgnoreReserved() { ignoreReserved = false; }
   public boolean isIgnoreReserved() { return ignoreReserved;  }
   
   public void print(String txt) {
//        System.out.println(txt);
   }   

   protected String removeQuotes(String txt) {
	   return txt.substring(1, txt.length() - 1);
   }
   
   protected void setBegCopy(int yyline, boolean isCopy) {
	   chgTokens = null;
	   this.isCopy = isCopy;
	   if (info.getNumSources() == 1) begCopy = yyline;
   }
 
   protected void loadCopy(boolean popState, int tipo) {
	   
	   String name = info.getCopyName();
	   
	   currCopy = info.getCurrentCopy();
	   
	   currCopy.setNombre(name);
	   currCopy.setTipo(tipo);
	   
	   if (cfg.isIgnored(name)) {
		   info.markCopyIgnored();
		   module.setCopyStatus(CDG.CPY_ST_IGNORED);
		   currCopy.setEstado(CDG.CPY_ST_IGNORED);
		   module.addCopy(currCopy);
		   module.setStatus(CDG.STATUS_PARTIAL);
		   if (popState) popState();
		   return;
	   }

	   Source source = loader.load(name, info.getReplacingTokens());
	   if (source != null) {
//		   module.setCopyStatus(CDG.CPY_MISSING);
		   unit.addSource(source);
		   info.addMember(name);
		   currCopy.setEstado(CDG.CPY_ST_FULL);
		   yypushStream(source);
	   }
	   else {
	      module.setStatus(CDG.STATUS_PARTIAL);
	      currCopy.setEstado(CDG.CPY_ST_MISSING);
	   }
	   
	   if (popState) popState();
	   
	   info.setCurrentCopy(module.addCopy(currCopy));
   }

   protected void initEmbedded(int type) {
       if (buffer == null) {
           buffer = new StringBuilder(4096);
       }
       else {
           buffer.setLength(0);
       }       
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
		   module.addIssue(issue);
	   }
   }
*/
   protected void checkDivision() {
//   	   if (module.getNumSources() != 1) {
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

   public Symbol tokenSQL() throws Exception {
	   Symbol s = next_token(); 
	   return s;
   }

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

}


