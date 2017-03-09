/**
 * Clase base para los analizadores lexicos 
 * Implementa funciones de utilidad que no estan implementadas 
 * en el analizador lexico generado por JFLEX
 *  
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.sql.lang;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;

public abstract class SQLExtractBase {

   private String moduleName = null;
   private int    begLine    = 0;
   
   public abstract void   yybegin(int newState);
   public abstract String yytext();
   public abstract int    yylength();
   public abstract void   yypushStream(java.io.Reader reader);

   protected Stack<Integer>       stack = new Stack<Integer>();

   protected StringBuilder buffer = null;
   
   protected Configuration cfg = Configuration.getInstance();
   
   // Para el control de COPYs e INCLUDEs
   
   
   protected final int OFFSET = cfg.getMarginLeft();
   
   protected void initLexer() {
       stack.push(0);
   }

   public void setModuleName(String moduleName) {
	  this.moduleName = moduleName;   
   }

   public String getModuleName() {
	  return this.moduleName;   
   }

   protected void setBegLine(int line) {
	   this.begLine = line;
   }
   
   protected int getBegLine() {
	   return this.begLine;
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

   
   public void print(String txt) {
//        System.out.println(txt);
   }   


   public void cleanFileSystem() {
	   if (moduleName == null) return;
   	   String dirBase = cfg.getDocDir();
   	   if (cfg.getBoolean(CFG.OUTPUT_ISOLATED, false) == true) {
   		   dirBase = dirBase + moduleName + File.pathSeparator;
   	   }
   	   File dir = new File(dirBase);
       for (String file : dir.list()) {
    	   if (file.startsWith(moduleName) && file.endsWith("sql")) {
               new File(dirBase + file).delete();
           }
        }
   }
   
   protected void saveStatement() throws IOException {
	   String fName = mountFileName();
	   try {
          FileOutputStream out = new FileOutputStream(fName);
          out.write(buffer.toString().getBytes());
          out.close();
	   }
	   catch (IOException e) {
           throw new SDPException(MSG.EXCEP_NO_FILE, fName);
	   }
   }
   
   private String mountFileName() {
	   String szLine = String.format("%06d", begLine);
   	   String dirBase = cfg.getDocDir();

   	   if (cfg.getBoolean(CFG.OUTPUT_ISOLATED, false) == false) {
   		   return dirBase + moduleName + "_" + szLine + ".sql";
   	   }
   	
   	   dirBase = dirBase + moduleName;
   	   File f = new File(dirBase);
       if (!f.exists()) f.mkdir();
       
       if (!f.isDirectory()) {
           throw new SDPException(MSG.EXCEP_NO_DIR, dirBase);
       }
       
       return dirBase + File.separatorChar + moduleName + "_" + szLine + ".sql"; 
   }

}


