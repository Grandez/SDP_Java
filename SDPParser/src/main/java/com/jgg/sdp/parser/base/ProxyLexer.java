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

import static com.jgg.sdp.parser.cobol.lang.ZCDSym.*;
import static com.jgg.sdp.parser.cobol.lang.ZCCSym.*;
import static com.jgg.sdp.parser.cobol.lang.ZCZSym.*;

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

	   // DETECTOR DE PROCEDURE
	   switch (continueParsing) {
	       case 0: break;
	       case 1: continueParsing++;
	               return new Symbol(ZCZSym.EOF);
	       case 2: continueParsing++;
	               return continueSymbol;
	   }
	   
	   Symbol s = scanner.next_token();

       switch (s.sym) {    	  
         case CICSCODE:
        	  Symbol cic = (Symbol) s.value;
       		  info.addOffset(cic.left);
   	          parserType = Parsers.CICS;
        	  break;
         case EXECSQL:	    	  
        	  Symbol sql = (Symbol) s.value;
       		  info.addOffset(sql.left);        		  
   	          parserType = Parsers.DB2; 
   	          s = changeSymbol(s);
        	  break;
         case COPY:	    	        
       	      Symbol cpy = (Symbol) s.value;
       	      info.addOffset(cpy.left);
    	      parserType = Parsers.COPY;
    	 	  s =  next_token();
    	      break;
         case ENDCOPY:	
              alterParser();
              s =  next_token();
	          break;
         case ENDEXEC:	    	  		    
              alterParser();
              s =  next_token();
 	          break;	          
        case DIVPROC:
             continueSymbol = changeSymbol(s);
             continueParsing++;
             return new Symbol(ZCZSym.EOF);
       }
	   return s;
   }

   private Symbol changeSymbol(Symbol s) {
	   switch (s.sym) {
           case EXECSQL:   return alterSymbol(s, info.inCode() ? SQLCODE : SQLDATA); 
           case DIVPROC:   return alterSymbol(s, DIV_PROC);
//           case EXECCICS:  return alterSymbol(s, info.inCode() ? SQLCODE : SQLDATA);
	   }
	   return s;
   }
   
   private Symbol alterSymbol(Symbol s, int id) {
	   s.sym = id;
	   Symbol aux = s;
	   while (aux.value instanceof Symbol) {
		   aux = (Symbol) aux.value;
		   aux.sym = id;
	   }
	   return s;
   }

   private void alterParser() throws Exception { 
	   switch (parserType) {
            case Parsers.CICS: parseCICS(); break;
            case Parsers.DB2:  parseSQL();  break;
            case Parsers.COPY: parseCOPY(); break;
	   }
   }
	   
   private void parseSQL() throws Exception {
	   info.setIncludeParsed(false);
	   Symbol s = parseAlter(Parsers.DB2);
   	   
	   if (s == null) {
   		   System.out.println("SQL PARATE");
   		   return;
   	   }

	   // En funcion de los EOF puede incluir stmtSQL en niveles
	   // mas internos
	   while (!(s.value instanceof StmtSQL)) s = (Symbol) s.value;
	   
   	   StmtSQL sql = (StmtSQL) s.value;

   	   if (sql.isInclude()) {
   		   createCopy(s, CDG.DEP_INCLUDE);
   		   updateCopy(s);
   	       loadCopy(sql.getRValue(0).getName(), null);
   	       return;
   	   } 

   	   // No se pueden guardar las sentencias que estan en un include
   	   if (info.getOffsetDepth() < 2) {
   	       if (postSQL == null) postSQL = new PostSQL(info.module);
   	       postSQL.setStatement(sql);
   	       postSQL.setSource(tmpSource);
   	       postSQL.parse();
   	   }    
   }

   private void parseCICS() throws Exception {
	   parseAlter(Parsers.CICS);   
   }

   private void parseCOPY() throws Exception {

	   Symbol s = parseAlter(Parsers.COPY);
   	   s = (Symbol) s.value;
   	   StmtCopy cpy = (StmtCopy) s.value;

	   createCopy(s, CDG.DEP_COPY);
       updateCopy(s);
       loadCopy(cpy.getCopyName(), cpy.getReplacingTokens());
   }
   
   private Symbol parseAlter(int type) throws Exception {
	   tmpSource = new Source(new Archivo("embedded.tmp"), true);
	   tmpSource.setData(info.buffer.toString());
	   
//	   System.out.println("Datos: " + info.buffer.toString());

	   Scanner scanner = getLexer(type, tmpSource);

   	   GenericParser parser = FactoryParser.getParser(scanner, tmpSource, type);
   	   Symbol s = parser.parse();
   	   info.removeOffset();
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
		   return;
	   }
	   
	   info.unit.addMember(name, source);

	   copy.setEstado(CDG.CPY_ST_FULL);
	   
//	   info.removeOffset();
	   info.addOffset(0);	   
	   lexer.yypushStream(source);
	   
   }
   
}
