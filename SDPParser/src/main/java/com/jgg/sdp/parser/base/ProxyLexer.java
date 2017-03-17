/**
 * Actua como proxy para el resto de Lexers y Parsers
 * Cada Parser pide su token con token_TYPE()
 * 
 */
package com.jgg.sdp.parser.base;

import com.jgg.sdp.core.tools.*;

import java.util.ArrayList;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.*;

import com.jgg.sdp.module.items.Copy;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.parser.cics.lang.CICSLexer;
import com.jgg.sdp.parser.cobol.lang.*;
import com.jgg.sdp.parser.copy.base.StmtCopy;
import com.jgg.sdp.parser.copy.lang.COPYLexer;
import com.jgg.sdp.parser.db2.*;
import com.jgg.sdp.parser.db2.stmt.StmtSQL;
import com.jgg.sdp.parser.post.PostSQL;
import com.jgg.sdp.parser.work.CopyLoader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public class ProxyLexer implements java_cup.runtime.Scanner {
	
	private Configuration cfg = Configuration.getInstance();
	
	private CopyLoader  loader    = new CopyLoader();	

	private PostSQL postSQL = null;
	
	private Scanner scanner;  // Scanner para los Parser
	private Scanner cblLexer;
	private ILexer  lexer;    // Interfaz de acceso al Lexer
	
	private int parserType = Parsers.COBOL;
	
	private Copy     copy     = null;
	
	private ParserInfo  info = ParserInfo.getInstance();
	
	private Source main      = null;
    private Source tmpSource = null;
	
	// Gestiona: el cambio de de parser entre DATA y CODE
	//           Multimodulos
	
	private Symbol  continueSymbol  = null;
	private int     continueParsing = 0;

	public ProxyLexer(Source source) {
		this.main = source;		
		getLexer(Parsers.COBOL, main);
	}

	private Scanner getLexer(int type, Source src) {
		switch (type) {
		   case Parsers.COBOL: 
			    if (cblLexer == null) cblLexer = new ZCLexer(src);
		        scanner = cblLexer;
		        lexer = (ILexer) cblLexer;
		        return cblLexer;
		   case Parsers.DB2:  
			    return DB2Lexer.getLexer(src);
		   case Parsers.COPY:  
			    return new COPYLexer(src);
		   case Parsers.CICS:  
			    return new CICSLexer(src);

		   default:                
			   System.out.println("Parate");         
		}
		return null;
	}
	
	/**
	 * Actua de proxy comun
	 * @return
	 * @throws Exception
	 */
	public Symbol next_token() throws Exception {
           return tokenCobol();
	}
	
   public Symbol tokenCobol() throws Exception {

	   switch (continueParsing) {
	       case 0: break;
	       case 1: continueParsing++;
	               return new Symbol(ZCZSym.EOF);
	       case 2: continueParsing++;
	               return continueSymbol;
	   }
	   
	   Symbol s = scanner.next_token();

       switch (s.sym) {
         case ZCDSym.EXEC_CICS:	    	  
         case ZCCSym.EXEC_CICS:	        	 
       	      if (((String) ((Symbol) ((Symbol) s.value).value).value).startsWith("CICS") ) {
    	          parserType = Parsers.CICS; 
    	          break;
       	      }
         case ZCDSym.EXEC_SQL:	    	  
         case ZCCSym.EXEC_SQL:	        
        	  if (((String) ((Symbol) ((Symbol) s.value).value).value).startsWith("SQL") ) {
    	          parserType = Parsers.DB2; 
    	          break;
        	  }
         case ZCZSym.COPY:	    	          	 
    	      parserType = Parsers.COPY;
    	 	  createCopy(s, CDG.DEP_COPY);  // Aqui pone el inicio en COPY
    	 	  s =  next_token();
    	      break;
         case ZCZSym.END_COPY:	
        	  parseCopy(s);
    	 	  s =  next_token();
    	      break;
         case ZCDSym.END_EXEC:	    	  
         case ZCCSym.END_EXEC:		    
	          if (((String) ((Symbol) s.value).value).toUpperCase().startsWith("END-EXEC") ) {
                 alterParser();
    	         s =  next_token();
    	         break;
        	  }
        case ZCCSym.DIV_PROC:
	         if (((String) ((Symbol) s.value).value).toUpperCase().startsWith("PROCEDURE") ) {
	             continueSymbol = s;
	             continueParsing++;
                 return new Symbol(ZCZSym.EOF);
	         }
	         return s;

       }
/*
       // Casos: SQL | COPY
	   while (specialDataSymbol(s.sym)) {
		 switch (s.sym) {
	         case ZCDSym.END_EXEC:	    	  
	         case ZCCSym.END_EXEC:		        	 
	         case ZCZSym.END_COPY:
                  alterParser();
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
*/	   
	   return s;
   }

	   public Symbol tokenCobolCode() throws Exception {
/*
		   if (begParser ) {
			   begParser = false;
			   return lastSym;
		   }
	*/	   
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
//		        	  sym = createCopy(s);
		    	      //parseCopy();
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

   private void alterParser() throws Exception { 
	   switch (parserType) {
            case Parsers.CICS: parseCICS(); break;
            case Parsers.DB2:  parseSQL();  break;
	   }
	   info.removeOffset();
   }
	   
   private void parseSQL() throws Exception {
	   info.setIncludeParsed(false);
	   Symbol s = parseAlter(Parsers.DB2);
	   System.out.println("Acaba PARSER SQL");
   	   if (s == null) return;
   	   
   	   if (postSQL == null) postSQL = new PostSQL(info.module, s.value);
   	   postSQL.setSource(tmpSource);
   	   postSQL.parse();
   	   
   	   s = (Symbol) s.value;
   	   StmtSQL sql = (StmtSQL) s.value;
   	   if (sql.isInclude()) {
   		   createCopy(s, CDG.DEP_INCLUDE);
   		   updateCopy(s);
   	       loadCopy(sql.getRValue(0).getName(), null);
   	   } 
   	   
   	   // if (info.isIncludeParsed()) loadCopy(false, CDG.DEP_INCLUDE);
   }

   private void parseCICS() throws Exception {
	   parseAlter(Parsers.CICS);   
	   System.out.println("Acaba PARSER CICS");
   }

   private StmtCopy parseCOPY(String name) throws Exception {
	   Symbol s = parseAlter(Parsers.COPY);
	   info.removeOffset();
   	   return (StmtCopy) ((Symbol) s.value).value;
   }
   
   private Symbol parseAlter(int type) throws Exception {
	   tmpSource = new Source(new Archivo("embedded.tmp"), true);
	   tmpSource.setData(info.buffer.toString());
	   
	   System.out.println(info.buffer.toString());
	   Scanner scanner = getLexer(type, tmpSource);

   	   GenericParser parser = FactoryParser.getParser(scanner, tmpSource, type);
   	   Symbol s = parser.parse();
   	   return s;
   }
   
   /****************************************************************************/
   /*                   TRATAMIENTO DE COPYS                                   */
   /****************************************************************************/
   
   private Symbol createCopy(Symbol s, int type) {
	   copy = new Copy();
	   copy.setBegLine(s.left);
	   copy.setBegColumn(s.right);
	   copy.setTipo(type);
	   return s;
   }

   private Symbol updateCopy(Symbol s) {
	   copy.setEndLine(s.left);
	   copy.setEndColumn(s.right);
	   return s;
   }
   
   private void parseCopy(Symbol cpy) throws Exception {
	   updateCopy(cpy);
       StmtCopy s = parseCOPY(copy.getNombre());
       loadCopy(s.getVerbName(), s.getReplacingTokens());  
   }

   private void loadCopy(String name, ArrayList<String> tokens) {
   
	   // La copy esta marcada como ignorada
	   if (cfg.isIgnored(name)) {
//		   info.markCopyIgnored();
		   info.module.setCopyStatus(CDG.CPY_ST_IGNORED);
		   copy.setEstado(CDG.CPY_ST_IGNORED);
		   info.module.addCopy(copy);
		   info.module.setStatus(CDG.STATUS_PARTIAL);
		   lexer.popState();
		   return;
	   }

	   Source source = loader.load(name, tokens);
	   
	   if (source == null) {
	       info.module.setStatus(CDG.STATUS_PARTIAL);
	       copy.setEstado(CDG.CPY_ST_MISSING);
		   lexer.popState();
		   return;
	   }
	   
	   info.unit.addMember(name, source);

	   copy.setEstado(CDG.CPY_ST_FULL);
	   
	   lexer.yypushStream(source);
//	   lexer.popState();
	   
   }
   
}
