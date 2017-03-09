package com.jgg.SQLSplit;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;

public class SQLSplit {
	                               
    private final String DIRTGT = "P:/SDP/Java/SDPParserSql/src/main/java/com/jgg/sdp/parser/sql/";
    private final String DIRSRC = "P:/SDP/Java/SDPParserSqlRaw/src/main/java/com/jgg/sdp/parser/sql/";  

    private final String PROD_FUNCTION = 
    "protected static final short _production_table[][] =  unpackFromStrings(SQLParserProduction.getProductionTable());";
    private final String ACT_FUNCTION = 
    "protected static final short _action_table[][] =  unpackFromStrings(SQLParserAction.getActionTable());";

    private final String PACKAGE = "package com.jgg.sdp.parser.sql;";
    
   private final String FILENAME = "SQLParserRaw.java";

    private int count = 0;
    private final int MAX = 5000;
    
    BufferedReader in     = null;
    BufferedWriter parser = null;
    BufferedWriter out    = null;
    BufferedWriter prod   = null;
    BufferedWriter act    = null;

    String line;
    
    public static void main( String[] args ) {
        SQLSplit split = new SQLSplit();
        split.process();
    }


    private void process() {
    	parseSQLParserRaw();
    	createSQLParserAction();
    	copyLexer();
    	System.out.println("Analizador preparado");
    }
    
    private void parseSQLParserRaw() {
    	
    	boolean skip = false;
    	int iTag = 0;

        
        in = openInputReader();
        parser = openOutput(DIRTGT + "SQLParser.java");
        out = parser;
        
        
        while ((line = readLine()) != null) {
          switch (iTag) {
             case 0: if (!line.contains("short _production_table")) break;
    		         write(PROD_FUNCTION);
       		         beginProduction();
       		         line = readLine();
       		         skip = true;
       		         iTag++;
       		         break;
             case 1: if (processProduction()) {
            	         iTag++;
                     } 
                     break;
             case 2: 
            	     endProduction();
                     iTag++;
                     skip = true;
                     break;

             case 3: if (!line.contains("short[][] _action_table")) break;             
                     write(ACT_FUNCTION);
                     line = readLine();
		             skip = true;
		             iTag++;
		             break;
             case 4: if (processAction()) {
                         iTag++;
                     }
                     break;
             case 5: endAction(true);
                     out = parser;
                     skip = true;
                     iTag++;
                     break;   
                    
             case 6: if (!line.contains("public final java_cup.runtime.Symbol ")) break;
                     iTag++;
		             break;
             case 7: if (line.contains("{")) {
            	         addSnippetFirst(line);
            	         iTag++;
            	         skip = true;
                     }
                     break;
             case 8: if (!line.contains("case 999: ")) break;
                     addSnippetSecond(line);
                     skip = true;
                     iTag++;
                     break;
                                        
          }

          if (line.contains("SQLParserRaw")) line = line.replaceAll("SQLParserRaw", "SQLParser");
          
          if (!skip) {
              write(line);
       	  }
       	  else {
       		  skip = false;
       	  }		  

       }
       closeInputReader(in);
       closeOutput(parser);
       
       
   }

   private String readLine() {
	   try {
		   return in.readLine();
	   }
	   catch (IOException e) {
		   e.printStackTrace();
		   System.exit(16);
	   }
	   return null;
   }
   
   private void beginProduction() {
	   prod = openOutput(DIRTGT + "SQLParserProduction.java");
	   out = prod;
	   write(PACKAGE);
	   write("");
	   write("public class SQLParserProduction {");
	   write("");
	   write("   static final String production_table[] = {"); 
   }

   private boolean processProduction() {
	     if (line.contains("});")) {
	    	 line = line.replace("});", "};");
	    	 return true;
	     }
         return false; 
   }
   
   private void endProduction() {
        write("   public static String[] getProductionTable() {");
   	    write("       return production_table;");
        write("   }");
        write("}");
        closeOutput(prod);
        out = parser;
   }

   private boolean processAction() {

      if (line.contains("});")) {
          line = line.replace("});", "};");
     	  return true;
       }
	   
	   if ((count % MAX) == 0) {
		   if (count / MAX > 0) endAction(false);
		   beginAction();
	   }
	   count++;
	   return false;
   }

   private void beginAction() {
	   String name = "SQLParserAction" + String.format("%03d",  count / 1000);
   
	   act = openOutput(DIRTGT + name + ".java");
	   out = act;
	   write(PACKAGE);
	   write("");
	   write("public class " + name + " {");
	   write("");
	   write("   static final String action_table[] = {"); 
   }
   
   private void endAction(boolean last) {
	   if (!last) {
	      write("   };");
	   }   
	   write(" ");
       write("   public static String[] getActionTable() {");
  	   write("       return action_table;");
       write("   }");
       write("");
       write("   public static int getLengthTable() {");
       write("          return action_table.length;");
       write("   }");
       write("}");

       closeOutput(act);
       out = parser;
  }
   
   private void addSnippetFirst(String line) {
	   write(line);
	   write( "      if (CUP$SQLParser$act_num > 999) {");
	   write( "          return CUP$SQLParser$do_action1(CUP$SQLParser$act_num, " +
	                           "CUP$SQLParser$parser, CUP$SQLParser$stack, CUP$SQLParser$top);");
	   write( "      }");
	   write( "      return CUP$SQLParser$do_action2(CUP$SQLParser$act_num, " +
	                       "CUP$SQLParser$parser, CUP$SQLParser$stack, CUP$SQLParser$top);");
	   write( "  }");
	   write(" ");
		  
	   write("  public final java_cup.runtime.Symbol CUP$SQLParser$do_action1(");
	   write("     int                        CUP$SQLParser$act_num,");
	   write("     java_cup.runtime.lr_parser CUP$SQLParser$parser,");
	   write("     java.util.Stack            CUP$SQLParser$stack,");
	   write("     int                        CUP$SQLParser$top)");
	   write("     throws java.lang.Exception");
	   write("  {");
   }

   private void addSnippetSecond(String line) {
	   write("   default:");
	   write("      throw new Exception(");
	   write("           \"Invalid action number found in internal parse table\");");
       write(" ");
	   write("   }");
	   write("}");
       write(" ");
       write(" public final java_cup.runtime.Symbol CUP$SQLParser$do_action2(");
       write("        int                        CUP$SQLParser$act_num,");
       write("        java_cup.runtime.lr_parser CUP$SQLParser$parser,");
       write("        java.util.Stack            CUP$SQLParser$stack,");
       write("        int                        CUP$SQLParser$top)");
       write("        throws java.lang.Exception");
       write(" {");
 	  
       write(" /* Symbol object for return from actions */");
       write(" java_cup.runtime.Symbol CUP$SQLParser$result;");
       write(" ");
       write(" /* select the action based on the action number */");
       write(" switch (CUP$SQLParser$act_num)");
       write(" {");

	   write(line);
   }
   
   private void write(String txt) {
	   if (txt == null) return;
	   try {
	      out.write(txt);
	      out.newLine();
	   } catch (IOException e) {
		   e.printStackTrace();
		   System.exit(16);
	   }
   }
   
   private BufferedReader openInputReader() {
	   BufferedReader br = null;
   
	   try {
         br = new BufferedReader(new FileReader(DIRSRC + FILENAME));
       } catch (IOException e) {
         e.printStackTrace();
         System.exit(16);
       }
	   return br;
   }
   
   private BufferedWriter openOutput(String fileName) {
	   BufferedWriter bw = null;
	   try {
	       File fout = new File(fileName);
		   FileOutputStream fos = new FileOutputStream(fout);
		   bw = new BufferedWriter(new OutputStreamWriter(fos));
	   
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
		return bw;
   }
   
   private void closeInputReader(BufferedReader br) {
       try {
         if (br != null)  br.close();
       } catch (IOException ex) {
       }
    }
	   

   private void closeOutput(BufferedWriter bw) {
	   try {
		   bw.close();
	   }
	   catch (IOException e) {
		   
	   }
   }
   
   private void createSQLParserAction() {
	   out = openOutput(DIRTGT + "SQLParserAction.java");
       int num = 0;
	   int max = 0;
	   int inc = MAX / 1000;
	   
	   String prfx = "         System.arraycopy(SQLParserAction";
	   String mTable = ".getActionTable(), 0, action, current, SQLParserAction";
	   String pCurrent = "         current += SQLParserAction";
	   String suffix;

	   write(PACKAGE);
	   write(" ");
	   write("public class SQLParserAction {");
	   write("   private static int size = 0;");
	   write(" ");
	   write("   public static int getSize() {");
	   
	   while (max < count) {
		   suffix = String.format("%03d", num);
		   num += inc;
		   max += MAX;
		   write("      size += SQLParserAction" + suffix + ".getLengthTable();");
	   } 
	   write("      return size; ");
	   write("   }");
	   write(" ");
	   write("   public static String[] getActionTable() {");
	   write("		 int current = 0;");
	   write("		 String[] action = new String[getSize()];");
	   
	   max = 0;
       num = 0;
	   
       while (max < count) {
		   suffix = String.format("%03d", num);
		   num += inc;
		   max += MAX;
		   write(prfx + suffix + mTable + suffix + ".getLengthTable());");
		   write(pCurrent + suffix +  ".getLengthTable();");
	   } 

	   write("      return action; ");
	   write("   }");
	   write(" ");
	   write("}");
	   
	   closeOutput(out);
   }

   private void copyLexer() {
	   Path from = Paths.get(DIRSRC + "SQLSym.java");
	   Path to   = Paths.get(DIRTGT + "SQLSym.java");
	   try {
	      Files.copy(from, to, REPLACE_EXISTING);
	      from = Paths.get(DIRSRC + "SQLLexer.java");
	      to   = Paths.get(DIRTGT + "SQLLexer.java");
	      Files.copy(from, to, REPLACE_EXISTING);
	   } catch (IOException e) {
		   e.printStackTrace();
		   System.exit(16);
	   }
   }	

}   
