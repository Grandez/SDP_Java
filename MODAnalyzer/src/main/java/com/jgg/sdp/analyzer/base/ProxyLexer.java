/**
 * Actua como proxy para el resto de Lexers y Parsers
 * Cada Parser pide su token con token_TYPE()
 * 
 */
package com.jgg.sdp.analyzer.base;

import static com.jgg.sdp.parser.lang.ZCCSym.*;
import static com.jgg.sdp.parser.lang.ZCDSym.*;
import static com.jgg.sdp.parser.lang.ZCZSym.*;

import java.util.ArrayList;

import java_cup.runtime.*;

import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.analyzer.post.PostSQL;
import com.jgg.sdp.analyzer.work.CopyLoader;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;

import com.jgg.sdp.module.items.Copy;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.parser.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.lang.*;
import com.jgg.sdp.parser.stmt.StmtSQL;


public class ProxyLexer implements java_cup.runtime.Scanner {
	
	private Configuration cfg = Configuration.getInstance();
	
	private CopyLoader  loader    = new CopyLoader();	

	private PostSQL postSQL = null;
	
	private GenericLexer lexer;
		
	private int parserType = Parsers.COBOL;
	
	private Copy     copy     = null;
	
	private ParserInfo  info = ParserInfo.getInstance();
	
	private Source main      = null;
    private Source tmpSource = null;
	
	public ProxyLexer(Source source, int type) {
		this.main = source;		
		lexer = getLexer(type, main);
	}

	private GenericLexer getLexer(int type, Source src) {

		switch (type) {
           case Parsers.ZCOBOLDATA: return new ZCDLexer(src);    
           case Parsers.ZCOBOLCODE: return new ZCCLexer(src); 
		   case Parsers.COPY:	    return new COPYLexer(src);
		   case Parsers.CICS:	    return new CICSLexer(src);
           case Parsers.DB2:        return     DB2Lexer.getLexer(src);
		   default:                 return null;                   
		}
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

	   Symbol s = lexer.next_token();

       switch (s.sym) {    	  
         case CICSCODE:
        	  Symbol cic = (Symbol) s.value;
       		  info.addOffset(cic.left);
   	          parserType = Parsers.CICS;
        	  break;
         case EXECSQL:
         case SQLDATA:
        	  Symbol sql = (Symbol) s.value;
       		  info.addOffset(sql.left);        		  
   	          parserType = Parsers.DB2; 
        	  break;
         case COPY:	    	        
       	      Symbol cpy = (Symbol) s.value;
       	      info.addOffset(cpy.left);
    	      parserType = Parsers.COPY;
    	 	  s =  lexer.next_token();
    	      break;
         case ENDCOPY:	
              alterParser();
              s =  lexer.next_token();
	          break;
         case ENDEXEC:	    	  		    
         case ENDSQL:    
              alterParser();
              s =  lexer.next_token();
 	          break;	          
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
   	   } else if (info.getOffsetDepth() < 2) { // No se pueden guardar las sentencias que estan en un include
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
	   
	   GenericLexer  lexer  = getLexer(type, tmpSource);
   	   GenericParser parser = FactoryParser.getParser((Scanner) lexer, type);
   	   
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
