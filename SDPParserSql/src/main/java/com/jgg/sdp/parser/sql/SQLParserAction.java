package com.jgg.sdp.parser.sql;
 
public class SQLParserAction {
   private static int size = 0;
 
   public static int getSize() {
      size += SQLParserAction000.getLengthTable();
      size += SQLParserAction005.getLengthTable();
      size += SQLParserAction010.getLengthTable();
      size += SQLParserAction015.getLengthTable();
      size += SQLParserAction020.getLengthTable();
      size += SQLParserAction025.getLengthTable();
      size += SQLParserAction030.getLengthTable();
      size += SQLParserAction035.getLengthTable();
      size += SQLParserAction040.getLengthTable();
      size += SQLParserAction045.getLengthTable();
      size += SQLParserAction050.getLengthTable();
      size += SQLParserAction055.getLengthTable();
      size += SQLParserAction060.getLengthTable();
      return size; 
   }
 
   public static String[] getActionTable() {
		 int current = 0;
		 String[] action = new String[getSize()];
         System.arraycopy(SQLParserAction000.getActionTable(), 0, action, current, SQLParserAction000.getLengthTable());
         current += SQLParserAction000.getLengthTable();
         System.arraycopy(SQLParserAction005.getActionTable(), 0, action, current, SQLParserAction005.getLengthTable());
         current += SQLParserAction005.getLengthTable();
         System.arraycopy(SQLParserAction010.getActionTable(), 0, action, current, SQLParserAction010.getLengthTable());
         current += SQLParserAction010.getLengthTable();
         System.arraycopy(SQLParserAction015.getActionTable(), 0, action, current, SQLParserAction015.getLengthTable());
         current += SQLParserAction015.getLengthTable();
         System.arraycopy(SQLParserAction020.getActionTable(), 0, action, current, SQLParserAction020.getLengthTable());
         current += SQLParserAction020.getLengthTable();
         System.arraycopy(SQLParserAction025.getActionTable(), 0, action, current, SQLParserAction025.getLengthTable());
         current += SQLParserAction025.getLengthTable();
         System.arraycopy(SQLParserAction030.getActionTable(), 0, action, current, SQLParserAction030.getLengthTable());
         current += SQLParserAction030.getLengthTable();
         System.arraycopy(SQLParserAction035.getActionTable(), 0, action, current, SQLParserAction035.getLengthTable());
         current += SQLParserAction035.getLengthTable();
         System.arraycopy(SQLParserAction040.getActionTable(), 0, action, current, SQLParserAction040.getLengthTable());
         current += SQLParserAction040.getLengthTable();
         System.arraycopy(SQLParserAction045.getActionTable(), 0, action, current, SQLParserAction045.getLengthTable());
         current += SQLParserAction045.getLengthTable();
         System.arraycopy(SQLParserAction050.getActionTable(), 0, action, current, SQLParserAction050.getLengthTable());
         current += SQLParserAction050.getLengthTable();
         System.arraycopy(SQLParserAction055.getActionTable(), 0, action, current, SQLParserAction055.getLengthTable());
         current += SQLParserAction055.getLengthTable();
         System.arraycopy(SQLParserAction060.getActionTable(), 0, action, current, SQLParserAction060.getLengthTable());
         current += SQLParserAction060.getLengthTable();
      return action; 
   }
 
}
