package com.jgg.sdp.parser.lang;

public interface ZCZSym {
  
  public static final int EOF     =     0;	
  
  public static final int COPY     = 30000;
  public static final int END_COPY = 30010;

  public static final int S_TAB        = 40001;
  public static final int S_DIRECTIVE  = 40002;
  
  public static final int FIGURATIVE = 50000;
  public static final int EXEC       = 60000;
  public static final int CICS       = 60001;
  public static final int SQL        = 60002;
  public static final int EXECSQL    = 60005;
  public static final int EXECCICS   = 60006;
  public static final int ENDCOPY    = 60011;

  // Tokens comunes a los dos Lexer
  public static final int DIRECTIVE = 50001;
  public static final int TAB       = 50002;
  public static final int EJECT     = 50003;
  public static final int SKIP      = 50004;
  public static final int NOPRINT   = 50005;
  public static final int CMT_DBG   = 50006;
  public static final int CMT_SLASH = 50007;
  public static final int MULTILINE = 50008;

}

