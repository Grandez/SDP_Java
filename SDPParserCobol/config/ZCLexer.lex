package com.jgg.sdp.parser.cobol.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.cobol.lang.ZCDSym.*;
import static com.jgg.sdp.parser.cobol.lang.ZCCSym.*;
import static com.jgg.sdp.parser.cobol.lang.ZCZSym.*;

%%

%public
%class      ZCLexer
%extends    GenericLexer
%implements GenericScanner, ILexer
%scanerror  ParseException

%line
%column
%char
%full
%ignorecase
%cup

%xstate  ID_DIVISION , ENV_DIVISION , DATA_DIVISION , PROC_DIVISION
%xstate  CONF_SECT , IO_SECT

%xstate PIC , BLOBSIZE
%xstate ENDLINE , EATLINE 
%xstate STEXEC , EMBEDDED 
%xstate EMBEDDED_QUOTE , EMBEDDED_DQUOTE
%xstate CICSSYM
%xstate FUNCTION

%xstate COMMENT        
// %xstate COMMENT2 
%xstate QUOTE_STRING   
%xstate DQUOTE_STRING 
  
%xstate SDP

// Estados para COPY REPLACING
%xstate COPYS  

%{

   String cpyName = null;
   String cicsVerb = null;
   int    lastSymbol = -1;
   int    prevSymbol = -1;
   int    nPars = 0;
   int    inIndex = 0;
   Symbol begExec = null;
   
   boolean beginPic  = true;   
   boolean prevSpace = false;
         
   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline;
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }

   public Symbol literal(boolean clean) { 
       String txt = cadena.toString();
       if (clean) cadena.setLength(0);
       return literal(txt); 
   }
   
   public Symbol literal(String txt) {
      int litCode = (info.inCode()) ? ZCCSym.LITERAL : ZCDSym.LITERAL;
      
      setLastSymbol(litCode);
      print("Devuelve LITERAL - " + txt);
      Symbol s = new Symbol(litCode, litLine, litColumn, txt);
      Symbol x = symbolFactory.newSymbol(txt, litCode, s);
      for (int idx = 0; idx < txt.length(); idx++) {
          if (txt.charAt(idx) < ' ') {
              checkLiteral(x);
              break;
          } 
      }
      return x;      
   }

   public Symbol symbolDummy(int code) {
      data = true;
      lastSymbol = 0;
      return symbol(code);
   }
                  
   public Symbol checkSymbol(int code) {
      return symbol(checkIndex(code));
   }
                  
   public Symbol symbol(int code){
      return symbol(code, yytext());
   }
   
   public Symbol symbol(String txt){
      return symbol(0, txt);
   }
   
   public Symbol symbol(int code, String txt) {
      setLastSymbol(code);
      data = true;
      int col = yycolumn + COLOFFSET;
      
      if (txt.indexOf('\t') != -1) checkSymbol(symbol("TAB")); 
      
      if (code != 0) {      
          print("Devuelve SYMBOL " + code + " (" + (yyline + 1) + "," + col + ") " + txt);
      }    
      return symbolFactory.newSymbol(txt, code, new Symbol(code, yyline + 1, col, txt));
   }

   public int checkIndex(int code) {
   
      if (code == ZCCSym.LPAR) return checkIndexLPAR(code);
      if (code == ZCCSym.RPAR) return checkIndexRPAR(code);
      if (inIndex == 2) {
          inIndex = 0;
          nPars = 0;
      }
      return code;
   }
   
   public int checkIndexLPAR(int code) {
/*
      if (inIndex == 1) {
          nPars++; 
          // Contruccion FUNCTION NAME(FUNCTION NAME(X))
//          if (lastSymbol == ZCCSym.ID && prevSymbol == FUNCTION) return LPARID;
//            if (lastSymbol == ZCCSym.ID) return LPARID;
          return code;
      }

      // Construcion VAR(i)(j)
      if (inIndex == 2) {
          nPars = 1;
          inIndex = 1;
          return LPARID;
      }

      if (lastSymbol == ZCCSym.ID && inIndex == 0) {
         inIndex = 1;
         nPars = 1;
         return LPARID;
      }
*/
      return code;
   }   

   public int checkIndexRPAR(int code) {
      if (inIndex == 1) {
          nPars--;
          if (nPars == 0) inIndex = 2;
      }
      else {
        if (inIndex == 2) {
            inIndex = 0;
            nPars = 0;
        }
     }   
      return code;
   }   

   private void setLastSymbol(int id) {
      prevSymbol = lastSymbol;
      lastSymbol = id;
   }

  private void excepcion(int code) {
      throw new NotSupportedException(code, info.module.getName(), yyline + 1, yycolumn + 1, yytext());
  }
  
%}

%init{
   initLexer();
%init}

%eofval{

    if (yymoreStreams()) {
        info.unit.removeSource();
        info.removeOffset();
        yypopStream();
    }
    else {    
        return symbolFactory.newSymbol("EOF", ZCZSym.EOF);
    }
        
%eofval}

SPACES=[ ]+
TABS=[\t]+
BLANKS=[ \t]+

// Generico para cargar buffer
WORD=[a-zA-Z0-9\_\-]+
ENDVERB=END-[a-zA-Z]+

ALPHA=[a-zA-Z]+

NUMERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,][0-9]+
DECIMAL2=[\.\,][0-9]+
HEXVALUE=[xX][\'\"][0-9aAbBcCdDeEfF]+[\'\"]

ALPHANUM=[a-zA-Z0-9]
ID = {ALPHANUM}({ALPHANUM}|\-|\_)*
SP=[ ]{1}
SP2=[ ]{1,5}
PARAGRAPH  = {SP}{ID} 

SIZE=\({BLANKS}*{NUMERO}[kKmM]?{BLANKS}*\)
PICLEN=[sS\+\-]?[aAxXzZ9]?{SIZE}

SDPD=DESC  | DESCRIPTION
SDPDESC=[>]?[\ \t]+SDP[\ \t]+{SDPD}
SDPEND=[>]?[\ \t]+SDP[\ \t]+END
SDPMASTER=[>]?[\ \t]+SDP[\ \t]+MASTER

%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO SOLO CONTROLA ID DIVISION                                     ***/
/******************************************************************************/
/******************************************************************************/

 ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
 ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
 ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
 ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
 ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }

IDENTIFICATION{BLANKS}DIVISION  { checkDivision(); 
                                  inDesc = false;
                                  resetState(ID_DIVISION);
                                  return symbol(DIV_ID);
                                }
ID{BLANKS}DIVISION              { checkDivision(); 
                                  inDesc = false;
                                  resetState(ID_DIVISION);
                                  return symbol(DIV_ID);
                                }

COPY         { initEmbedded(); 
               pushState(COPYS); 
               return symbol(ZCZSym.COPY);     
             }
EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC);   pushState(STEXEC);    }
EXEC         { begExec = symbolDummy(ZCZSym.EXEC);   pushState(STEXEC);    }


 ^\*{SDPEND}       { info.module.incComments(true);
                     inDesc = false;
                     data = true;
                   }
                    

 ^\*{SDPDESC}      { resetLiteral("");
                     pushState(SDP);
                     info.module.incComments(true);
                     inDesc = true;
                     data = true;
                     return symbol(SDPDESC);   
                   }

  
// ^\*>               { pushState(COMMENT2);   }

CBL                { checkSymbol(symbol("DIRECTIVE")); pushState(EATLINE);  }
REPLACE            { excepcion(MSG.EXCEPTION_NOT_ALLOW); }
{SPACES}           { /* nada */ }
{TABS}             { checkSymbol(symbol("TAB")); }
{ID}               { checkSymbol(symbol("DIRECTIVE"));  pushState(EATLINE);    }



\n                 { info.module.incLines(data); data = false; }
\r                 { /* do nothing */ }

  [^]                            { System.out.println("JGGERR INIT caracter: " + yytext() + "(" + yyline + "," + yycolumn + ")"); }
//  .                            { System.out.println("JGGERR INIT caracter: " + yytext()); }                             


/******************************************************************************/
/******************************************************************************/
/***               IDENTIFICATION DIVISION                                  ***/
/******************************************************************************/
/******************************************************************************/

<ID_DIVISION> {
  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }

  AUTHOR           { inDesc = false; return symbol(AUTHOR); }
  COMMON           { data = true; }
  
  DATE-WRITTEN     { return symbol(DATEW);    }
  DATE-COMPILED    { return symbol(DATEC);    }  
  
  INITIAL          { data = true; }  
  INSTALLATION     { inDesc = false; return symbol(INSTALLATION); }
  
  PROGRAM-ID       { inDesc = false; return symbol(PGMID);  }
  PROGRAM          { data = true; }  

  RECURSIVE        { data = true; }
  REMARKS          { excepcion(MSG.EXCEPTION_NOT_ALLOW); }
  
  COPY         { initEmbedded(); 
                 pushState(COPYS); 
                 return symbol(ZCZSym.COPY);           
               }
  EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC);  pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(ZCZSym.EXEC);     pushState(STEXEC);    }

  ENVIRONMENT{BLANKS}DIVISION     { inDesc = false;
                                    resetState(ENV_DIVISION); 
                                    return symbol(DIV_ENV); 
                                  }

  DATA{BLANKS}DIVISION            { resetState(DATA_DIVISION);
                                    inDesc = false;   
                                    return symbol(DIV_DATA);
                                  }

  PROCEDURE{BLANKS}DIVISION       { resetState(PROC_DIVISION);
                                    info.setInCode();
                                    inDesc = false;                                  
                                    return symbol(DIVPROC);
                                  }
  
  {ID}             { return symbol(ZCDSym.ID);     }
  {NUMERO}         { return symbol(ZCDSym.NUMERO); }
  {SPACES}         { /* nada */ }
  {TABS}           { checkSymbol(symbol("TAB")); }

  \.               { return symbol(ENDD);   }
  "-"              { data = true;  }
  ","              { data = true;  }
  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }
  .                { data = true;  }
} 

/******************************************************************************/
/******************************************************************************/
/***             ENVIRONMENT DIVISION                                       ***/
/******************************************************************************/
/******************************************************************************/

<ENV_DIVISION> {

  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }
                                   
  CONFIGURATION{BLANKS}SECTION   { pushState(CONF_SECT);   
                                   return symbol(CONF_SECTION); 
                                 }
                                 
  INPUT-OUTPUT{BLANKS}SECTION    { pushState(IO_SECT);
                                   return symbol(IO_SECTION);   
                                 }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { resetState(PROC_DIVISION);
                                   info.setInCode();
                                   inDesc = false;                                  
                                   return symbol(DIVPROC);
                                 }

  COPY         { initEmbedded(); 
                 pushState(COPYS);   
                 return symbol(ZCZSym.COPY);         
               }
  EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(ZCZSym.EXEC);    pushState(STEXEC);    }

  \.               { return symbol(ENDD); }                                   
 
  {SPACES}         { /* nada */ }
  {TABS}           { checkSymbol(symbol("TAB")); }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }

  [^]              { data = true; 
                     System.err.println("JGG: " + yyline + "-" + yycolumn + " -" + yytext() + "-"); }

 }

/******************************************************************************/
/******************************************************************************/
/***    CONFIGURATION SECTION                                               ***/
/******************************************************************************/
/******************************************************************************/

<CONF_SECT> {
  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  CLASS            { return symbol(CLASS); }
  COMMA            { data = true; }
  DEBUGGING        { data = true; }
  DECIMAL-POINT    { return symbol(DEC_POINT); }
  IS               { data = true; }
  MODE             { data = true; }
  OBJECT-COMPUTER  { return symbol(OBJECT_COMPUTER); }
  SOURCE-COMPUTER  { return symbol(SOURCE_COMPUTER); }
  SPECIAL-NAMES    { return symbol(SPECIAL_NAMES);   }
  THRU             { data = true; } 
  WITH             { data = true; }

  COPY         { initEmbedded(); 
                 pushState(COPYS); 
                 return symbol(ZCZSym.COPY);     
               }
  EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(ZCZSym.EXEC);    pushState(STEXEC);    }

  INPUT-OUTPUT{BLANKS}SECTION    { popState();
                                   pushState(IO_SECT);
                                   return symbol(IO_SECTION);   
                                 }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { resetState(PROC_DIVISION);
                                   info.setInCode();
                                   inDesc = false;                                  
                                   return symbol(DIVPROC);
                                 }

  {NUMERO}         { return symbol(ZCDSym.NUMERO); }   
  {ID}             { return symbol(ZCDSym.ID);     }
  {SPACES}         { /* nada */ }
  {TABS}           { checkSymbol(symbol("TAB")); }
  \.               { return symbol(ENDD);   }
  \,               { data = true; }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }

  .       { System.err.println("I-O ERROR Configuration Encuentra en (" + yyline + "," + yycolumn + " -" + yytext() + "-"); }
}

/******************************************************************************/
/***    INPUT-OUTPUT SECTION                                                ***/
/******************************************************************************/

<IO_SECT> {
  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }

  ACCESS         { return symbol(ACCESS);       }
  ALTERNATE      { return symbol(ALTERNATE);    }  
  ASSIGN         { return symbol(ASSIGN);       }
  DYNAMIC        { return symbol(DYNAMIC);      }
  DISPLAY        { return symbol(ZCDSym.DISPLAY);      }
  DUPLICATES     { data = true;                 }  
  INDEXED        { return symbol(INDEXED);      }
  FILE-CONTROL   { return symbol(FILE_CONTROL); }
  FILE           { data = true;                 }
  IS             { data = true;                 }
  KEY            { data = true;                 }
  LINE           { data = true;                 }
  MODE           { data = true;                 }
  OPTIONAL       { data = true;                 }
  ORGANIZATION   { return symbol(ORGANIZATION); }
  RANDOM         { return symbol(RANDOM);       }
  RECORDING      { return symbol(RECORDING);    }
  RECORD         { return symbol(RECORD);       }
  RELATIVE       { return symbol(RELATIVE);     }
  SELECT         { return symbol(SELECT);       }
  SEQUENTIAL     { return symbol(SEQUENTIAL);   }
  SKIP[1-9]?     { data = true;                 }
  STATUS         { return symbol(STATUS);       }
  TO             { data = true;                 }
  WITH           { data = true;                 }

  COPY         { initEmbedded(); 
                 pushState(COPYS);  
                 return symbol(ZCZSym.COPY);          
               }
  EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(ZCZSym.EXEC);    pushState(STEXEC);    }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { resetState(PROC_DIVISION);
                                   info.setInCode();
                                   inDesc = false;                                  
                                   return symbol(DIVPROC);
                                 }
  
 ^\*{SDPMASTER}    { pushState(SDP);
                     info.module.incComments(true);
                     inDesc = false;
                     data = true;
                     return symbol(SDPMASTER);   
                   }

  {SPACES}         { /* nada */ }
  {TABS}           { checkSymbol(symbol("TAB")); }
  {NUMERO}         { return symbol(ZCDSym.NUMERO); }   
  {ID}             { return symbol(ZCDSym.ID);     }
  \.               { return symbol(ENDD);          }
  \,               { data = true; }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }

  .       { System.err.println("I-O ERROR Encuentra en (" + yyline + "," + yycolumn + " -" + yytext() + "-"); }
}


/******************************************************************************/
/******************************************************************************/
/***                    DATA DIVISION                                       ***/
/******************************************************************************/
/******************************************************************************/

<DATA_DIVISION> {

  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }
  ^\-                 { checkSymbol(symbol("-")); }  

  FILE{BLANKS}SECTION            { return symbol(FILE_SECTION);    }
  WORKING-STORAGE{BLANKS}SECTION { return symbol(WORKING_SECTION); }
  LOCAL-STORAGE{BLANKS}SECTION   { return symbol(LOCAL_SECTION);   }
  LINKAGE{BLANKS}SECTION         { return symbol(LINKAGE_SECTION); }
  PROCEDURE{BLANKS}DIVISION      { resetState(PROC_DIVISION);
                                   info.setInCode();
                                   inDesc = false;                                  
                                   return symbol(DIVPROC);
                                 }
  
  ALL                            { data = true;              }
  ANY[ ]+LENGTH                  { data = true;              }
  ARE                            { data = true;              }
  ASCENDING                      { return symbol(ORDER);     }

  BASED                          { data = true;              }
  BINARY                         { return symbol(BINARY);    }
  BLANK[ ]+WHEN[ ]+ZERO          { data = true;              }
  BLANK[ ]+ZERO                  { data = true;              }
  BLOB-LOCATOR                   { return symbol(BLOB_LOCATOR); }
  BLOB                           { pushState(BLOBSIZE) ; return symbol(BLOB);   }
  BLOCK                          { return symbol(BLOCK);     }
  BY                             { data = true;              }
  
  CHARACTER[sS]?                 { data = true;              }
  COMPUTATIONAL-1                { return symbol(COMP1);     }
  COMPUTATIONAL-2                { return symbol(COMP2);     }
  COMPUTATIONAL-3                { return symbol(COMP3);     }
  COMPUTATIONAL-4                { return symbol(COMP4);     }
  COMPUTATIONAL-5                { return symbol(COMP5);     }
  COMPUTATIONAL-6                { return symbol(COMP6);     }      
  COMPUTATIONAL                  { return symbol(BINARY);    }
  COMP-1                         { return symbol(COMP1);     }  
  COMP-2                         { return symbol(COMP2);     }
  COMP-3                         { return symbol(COMP3);     }
  COMP-4                         { return symbol(COMP4);     }
  COMP-5                         { return symbol(COMP5);     }
  COMP-6                         { return symbol(COMP6);     }          
  COMP                           { return symbol(BINARY);    }
  CONTAIN[sS]?                   { data = true;              }
     
  DATA                           { return symbol(DATA);      }
  DATE                           { pushState(ENDLINE);       }
  DEPENDING                      { return symbol(ZCDSym.DEPENDING); }
  DESCENDING                     { return symbol(ORDER);     }
  DFHRESP                        { cicsVerb = "DFHRESP";  pushState(CICSSYM);       }  
  DFHVALUE                       { cicsVerb = "DFHvalue"; pushState(CICSSYM);       }
  DISPLAY                        { return symbol(ZCDSym.DISPLAY);   }
           
  EXTERNAL                       { data = true;              }
  
  FD                             { return symbol(FD);        } 
  FILLER                         { return symbol(FILLER);    }
  FROM                           { data = true;              }  
  F                              { return symbol(FILLER);    }

  GLOBAL                         { data = true;              }
    
  INDEXED                        { return symbol(INDEXED);   }
  INDEX                          { return symbol(INDEX);     }
  IN                             { return symbol(ZCDSym.IN);        }  
  IS                             { data = true;              }
  
  JUSTIFIED                      { data = true;              }
  JUST                           { data = true;              }

  KEY                            { data = true;              }
  
  LABEL                          { return symbol(LABEL);     }
  LEADING                        { data = true;              }
  LINAGE                         { return symbol(LINAGE);    }  
  LINE[sS]?                      { data = true;              }
  
  MODE                           { data = true;              }
  
  NULL[sS]?                      { return symbol(ZCDSym.NULL);      }

  OMITTED                        { data = true;              }
        
  RECORDING                      { return symbol(RECORDING); }                  
  RECORDS                        { return symbol(RECORDS);   }
  RECORD                         { return symbol(RECORD);    }
  REDEFINES                      { return symbol(REDEFINES); }
  RENAMES                        { return symbol(RENAMES);   }
  
  OCCURS                         { return symbol(OCCURS);    }
  OF                             { return symbol(ZCDSym.OF); }
  ON                             { data = true;              }

  PACKED-DECIMAL                 { return symbol(PACKED);    }     
  PICTURE                        { beginPic = true; pushState(PIC) ; return symbol(PICTURE);   }
  PIC                            { beginPic = true; pushState(PIC) ; return symbol(PICTURE);   }
  POINTER                        { return symbol(ZCDSym.POINTER); }

  ROWID                          { return symbol(ROWID);   }
  RIGHT                          { data = true;            }
  
  SD                             { return symbol(SD);      }  
  SEPARATE                       { data = true;            }
  SIGN                           { data = true;            }
  SIZE                           { data = true;            }
  SQL                            { data = true;            }
  STANDARD                       { data = true;            }
  SYNCHRONIZED                   { data = true;            }
  SYNC                           { data = true;            }

  TIMES                          { data = true;            }  
  TO                             { return symbol(ZCDSym.TO);      }
  TRAILING                       { data = true;            }
  THROUGH                        { return symbol(ZCDSym.THRU);    }
  THRU                           { return symbol(ZCDSym.THRU);    }
  TYPE                           { data = true;            }
  
  USAGE                          { return symbol(USAGE);   }
    
  VALUE[sS]?                     { return symbol(ZCDSym.VALUE);   }
  VARYING                        { data = true;            }  

  ZERO[sS]?                      { return symbol(ZCDSym.ZERO);    }
  ZEROES                         { return symbol(ZCDSym.ZERO);    }
  SPACE[sS]?                     { return symbol(ZCDSym.SPACES);  }
  LOW\-VALUE[sS]?                { return symbol(ZCDSym.LOWVAL);  }
  HIGH\-VALUE[sS]?               { return symbol(ZCDSym.HIGHVAL); } 
  QUOTE[sS]?                     { return symbol(ZCDSym.QUOTE);   }

  COPY         { initEmbedded(); 
                 pushState(COPYS);         
                 return symbol(ZCZSym.COPY);  
               }
  EXECUTE      { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }

  {DECIMAL}                      { return symbol(ZCDSym.NUMERO);  }
  {DECIMAL2}                     { return symbol(ZCDSym.NUMERO);  }  
  {NUMERO}                       { return symbol(ZCDSym.NUMERO);  }
  {HEXVALUE}                     { return symbol(ZCDSym.HEX_VAL); }
  {ID}                           { return symbol(ZCDSym.ID);      }
  {SPACES}         { /* nada */ }
  {TABS}           { checkSymbol(symbol("TAB")); }
  {NUMERO}         { return symbol(ZCDSym.NUMERO); }   
  "("              { return symbol(ZCDSym.LPAR);    }
  ")"              { return symbol(ZCDSym.RPAR);    }
  \.               { return symbol(ENDD);   }
  \,               { data = true; }
  \'               { print("ENTRA EN QUOTE"); pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }

}

/* PICTURE es PIC espacios formato [espacios|punto] con comentarios o sin ellos */
<PIC> {
  ^[\*]                   { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]                   { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD][dD][a-zA-Z0-9 ]*  { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }

  {SPACES}      { if (beginPic == false) popState();  }
  {TABS}        { checkSymbol(symbol("TAB")); 
                  if (beginPic == false) popState(); 
                }  
  {PICLEN}      { beginPic = false; return symbol(PIC_LEN);      }

  [\.\,\$][\-\+\*\$zZ09]+        { beginPic = false; return symbol(PIC_FMT);      }
  [sS\+\-]?[09bBaAxXzZ\-\+\$]+  { beginPic = false; return symbol(PIC_FMT);      }              
  [Ee][+-]?[9]+                  { beginPic = false; return symbol(PIC_FMT);      }
  [sSvV]+                        { beginPic = false; return symbol(PIC_DEC_EMPTY);      }
  [vV]{NUMERO}                   { beginPic = false; return symbol(PIC_V);        }
  V                              { beginPic = false; return symbol(PIC_DEC_EMPTY);      }

  \.                             { popState(); return symbol(ENDD); }
  
  \n                             { info.module.incLines(data); }
  \r                             { /* do nothing */ }
  [^]                            { System.out.println("JGGERR DATA caracter: " + yytext()); }
  .                            { System.out.println("JGGERR DATA caracter: " + yytext()); }                             
}

<BLOBSIZE> {
  {SIZE}                       { popState(); return symbol(PIC_LEN);      }
}

/******************************************************************************/
/******************************************************************************/
/***                    PROCEDURE DIVISION                                  ***/
/******************************************************************************/
/******************************************************************************/
/*** Por precedencia de longitudes COPY lo coge como parrafo                ***/
/*** Lo controlamos en el nombre                                            ***/
/******************************************************************************/

<PROC_DIVISION> {

  ^[\*]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[\/]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }  
  ^[dD]               { setComment(yytext(), yyline, yycolumn); pushState(COMMENT);    }
  ^[ ]+SKIP[1-9]?     { checkSymbol(symbol("SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol(symbol("EJECT")); }
  ^\-                 { checkSymbol(symbol("-"));  }  

  ^[ ]+EXECUTE        { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  ^[ ]+EXEC           { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  /* VERBOS */

  ACCEPT            { return symbol(ACCEPT);     }  
  ADD               { return symbol(ADD);        }
  ALLOCATE          { return symbol(ALLOCATE);   }

  ALPHABETIC-LOWER  { return symbol(ALPHABETIC); }
  ALPHABETIC-UPPER  { return symbol(ALPHABETIC); }  
  ALPHABETIC        { return symbol(ALPHABETIC); }       
  CALL              { return symbol(CALL);       }
  CANCEL            { return symbol(CANCEL);     }
  CLOSE             { return symbol(CLOSE );     }
  COMMIT            { return symbol(COMMIT);     }
  COMPUTE           { return symbol(COMPUTE);    }
  CONTINUE          { return symbol(CONTINUE);   }
  CONVERTING        { return symbol(CONVERTING); }  

  // Capturar COPY en una linea
  COPY              { initEmbedded(); 
                      pushState(COPYS);   
                      return symbol(ZCZSym.COPY); 
                    }
  DCBS              { return symbol(DCBS);      }      
  DELETE            { return symbol(DELETE);    }
  DISPLAY           { return symbol(ZCCSym.DISPLAY);   }
  DIVIDE            { return symbol(DIVIDE);    }

  ELSE              { return symbol(ELSE);      }
  EVALUATE          { return symbol(EVALUATE);  }
  EXCEPTION         { return symbol(EXCEPTION); }  

  EXIT[ \t]+PERFORM { return symbol(EXITP);     }
  EXIT[ \t]+PROGRAM { return symbol(EXITPGM);   }
  EXIT              { return symbol(EXIT);      }  

  FREE              { return symbol(FREE);      }
  
  GO                { return symbol(GOTO);       }
  GOBACK            { return symbol(GOBACK);     }                                           
                                           
  IF                { return symbol(IF);         }  
  INITIALIZE        { return symbol(INITIALIZE); }
  INSPECT           { return symbol(INSPECT);    }

  KANJI             { return symbol(KANJI);      }
  
  LENGTH            { return symbol(LENGTH);      }
  
  MERGE             { return symbol(MERGE);      }  
  MOVE              { return symbol(MOVE);       }
  MULTIPLY          { return symbol(MULTIPLY);   }
                                           
  NEXT[ ]+SENTENCE  { return symbol(NEXT);       }
  NULL[sS]?         { return symbol(ZCCSym.NULL);}
  NUMERIC           { return symbol(ZCCSym.NUMERIC);}
  
  OPEN              { return symbol(OPEN);       }   

  PERFORM           { return symbol(PERFORM);    }

  READ              { return symbol(READ);       }
  RELEASE           { return symbol(RELEASE);    }      
  REPLACING         { return symbol(REPLACING);  }                                         
  RETURN            { return symbol(RETURN);     }
  REWRITE           { return symbol(REWRITE);    }

  SEARCH            { return symbol(SEARCH);     }                                           
  SET               { return symbol(SET);        }
  SORT              { return symbol(SORT);       }  
  START             { return symbol(START);      }
  STOP[ ]+RUN       { return symbol(STOPRUN);    }
  STRING            { return symbol(STRING);     }     
  SUBTRACT          { return symbol(SUBTRACT);   }
    
  TALLYING          { return symbol(TALLYING);   }
  TRANSFORM         { return symbol(TRANSFORM);  }                                           

  UNLOCK            { return symbol(UNLOCK);     }
  UNSTRING          { return symbol(UNSTRING);   }                                           

  WRITE             { return symbol(WRITE);      }                                            


  /* CALIFICADORES */

  ADDRESS[ ]+OF     { data = true; } 
  ADVANCING         { data = true; }
  AFTER             { return symbol(AFTER);        }
  ALPHANUMERIC      { return symbol(ALPHANUMERIC); }
  ALL               { return symbol(ALL);          }
  ALSO              { return symbol(ALSO);       }
  AND               { return symbol(AND);        }
  ASCENDING         { return symbol(ASCENDING);  }
  AT                { data = true; }

  BY                { return symbol(ZCCSym.BY);         }
  BEFORE            { return symbol(BEFORE);     }
  
  CHARACTER[sS]?    { return symbol(CHARACTER);  }
  CORRESPONDING     { return symbol(CORR);       }
  CORR              { return symbol(CORR);       }
  COUNT             { return symbol(COUNT);      }
  CYCLE             { data = true; }

  DATA              { data = true; }
  DELIMITED[ ]+BY   { return symbol(DELIMITED);  }
  DELIMITED         { return symbol(DELIMITED);  }
  DELIMITER         { return symbol(DELIMITER);  }
  DESCENDING        { return symbol(DESCENDING); }  
  
  DFHRESP           { cicsVerb = "DFHRESP";  pushState(CICSSYM);   }  
  DFHVALUE          { cicsVerb = "DFHVALUE"; pushState(CICSSYM);   }

  END-EVALUATE     { return symbol(ENDEVAL);    }
  END-IF           { return symbol(ENDIF);      }  
  END-PERFORM      { return symbol(ENDPERFORM); }  
  {ENDVERB}        { return symbol(ENDVERB);        }
  
  END              { return symbol(ATEND);     } 
  EOP              { return symbol(EOP);       }

  EQUAL            { return symbol(EQUAL);     }
  EXTEND           { return symbol(EXTEND);    }

  FIRST            { return symbol(FIRST);    }
  FOREVER          { return symbol(FOREVER);  }    
  FOR              { data = true;             }  
  FROM             { return symbol(FROM);     }
  FUNCTION         { pushState(FUNCTION); return symbol(FUNCTION); }
 
  GIVING           { return symbol(GIVING);   }
  GREATER          { return symbol(GREATER);  }  

  I-O              { return symbol(IO);      }
  IN               { return symbol(ZCCSym.IN);      }
  INITIAL          { data = true; }  
  INPUT            { return symbol(INPUT);   }
  INTO             { return symbol(INTO);    }
  INVALID          { return symbol(INVALID);   }
  IS               { data = true; }
  
  KEY              { return symbol(KEY);   }

  LEADING          { return symbol(LEADING);   }
  LESS             { return symbol(LESS);   }
  LINE[sS]?        { data = true; }
  LOCK             { data = true; }

  NEXT             { data = true; }
  NOT              { data = true; }

  OF               { data = true;             }
  ON               { data = true;  }     
  OR               { return symbol(OR);        }
  OTHER            { return symbol(OTHER);     }
  OUTPUT           { return symbol(OUTPUT);    }
  OVERFLOW         { return symbol(OVERFLOW);  }

  PAGE             { data = true;  }
  POINTER          { return symbol(ZCCSym.POINTER);  }
  PROCEDURE        { data = true;  }
     
  RECORD           { data = true; }
  REEL             { data = true; }
  REFERENCE        { return symbol(REFERENCE); }
  REMOVAL          { data = true; }
  RETURNING        { return symbol(RETURNING); }
  REWIND           { data = true; }
  ROUNDED          { return symbol(ROUNDED);    }  

  SECTION          { return symbol(SECTION);    }
  SIZE[ ]+ERROR    { return symbol(SIZE_ERROR); }
  SIZE             { return symbol(SIZE);       }  

  TEST             { return symbol(TEST);    }
  THAN             { data = true; }  
  THEN             { data = true; }
  THROUGH          { return symbol(ZCCSym.THRU);    }
  THRU             { return symbol(ZCCSym.THRU);    }
  TIMES            { return symbol(ZCCSym.TIMES);   }
  TO               { return symbol(ZCCSym.TO);      }

  UP               { data = true; }
  UNIT             { data = true; }
  UNTIL            { return symbol(UNTIL);   }  
  USING            { return symbol(USING);   }
  
  VALUE            { return symbol(ZCCSym.VALUE);   }
  VARYING          { return symbol(VARYING); }
  
  WHEN             { return symbol(WHEN);    }
  WITH             { data = true; }
         
  SKIP[0-9]?       { data = true;            }

/*******************************************************/  
/* Simbolos y operadores                               */
/*******************************************************/

 "("               { return checkSymbol(ZCCSym.LPAR);  }
 ")"               { return checkSymbol(ZCCSym.RPAR); }

 ">="              { return symbol(REL_GE); }
 "<="              { return symbol(REL_LE); }
 "**"              { if (yycolumn < 4) pushState(COMMENT); else return symbol(OP_POW); }
 "*"               { if (yycolumn < 4) pushState(COMMENT); else return symbol(OP_MUL); }
 "/"               { if (yycolumn < 4) pushState(COMMENT); else return symbol(OP_DIV); }
 "+"               { return symbol(OP_ADD); }
 "-"               { return symbol(OP_SUB); }
 ">"               { return symbol(REL_GT); }
 "<"               { return symbol(REL_LT); }
 "="               { return symbol(REL_EQ); }
 ","               { return symbol(ZCCSym.COMMA); }
 "."               { return symbol(ZCCSym.ENDP); }

 ";"               { data = true; }
 ":"               { return symbol(OP_COL); }  

/*******************************************************/  
/* Patrones                                            */
/*******************************************************/

  {SPACES}      { /* nada */ }
  {TABS}        { checkSymbol(symbol("TAB")); }

  // Capturar  al inicio de linea
  ^[ ]+COPY{BLANKS}              { initEmbedded(); 
                                   pushState(COPYS);   
                                   return symbol(ZCZSym.COPY); 
                                 }

  ^{PARAGRAPH}                   { return symbol(PARRAFO); }
  ^[ ]{1}END{BLANKS}PROGRAM      { return symbol(END_PGM); }


 {NUMERO}           { return symbol(ZCCSym.NUMERO);  } 
 {DECIMAL}          { return symbol(ZCCSym.NUMERO);  }
 {DECIMAL2}         { return symbol(ZCCSym.NUMERO);  }
 {HEXVALUE}         { return symbol(ZCCSym.NUMERO);  }
 {ID}               { if (yycolumn == 0) {
                          char c = yytext().charAt(0);
                          if (c == 'D' || c == 'd') pushState(COMMENT);
                      }
                      else {     
                         if (yycolumn <= 4) {
                             return symbol(PARRAFO);
                         }
                         else {    
                           return symbol(ZCCSym.ID);
                         }
                      }   
                    }
 
  \n       { info.module.incLines(data); }
  \r       { /* do nothing */ }

  [^]               { print("JGG 1 encuentra -" + yytext() + "-"); }
  
}

/******************************************************************************/
/******************************************************************************/
/***                               otros                                    ***/
/******************************************************************************/
/******************************************************************************/

<FUNCTION> {
  {SPACES}           { /* EAT */ }
  {TABS}             { /* EAT */ }
  ^[\*\/]            { pushState(COMMENT);           }
  {ID}               { popState(); return symbol(INTRINSIC);  }
  \r                 { /* EAT */ }
  \n                 { /* EAT */ }
}

/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'\'          { cadena.append(yytext());  }    
  \'            { popState();  
                  return literal(true); 
                }  
                // Tiene que haber continuacion
  \n            { popState(); } 
  \r            { /* eat */ }

  [^]           { cadena.append(yytext()); }
}


<DQUOTE_STRING> {
  \"\"          { cadena.append(yytext());  }    
  \"            { popState(); 
                  return literal(true); 
                }
                // Tiene que haber continuacion
  \n            { popState(); }
  \r            { /* eat */   }

  [^]           { cadena.append(yytext()); }
}


/* 
  Casos en los que puede haber varios puntos
  Se come hasta fin de linea y devuelve punto
*/
<ENDLINE> {
  {SPACES}      { /* Nada */  }
  {TABS}        { checkSymbol(symbol("TAB")); }
  \n            { popState(); return symbol((info.inCode()) ? ENDP : ENDD); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  .             { /* comer */ }
}

// Se come hasta fin de linea

<EATLINE> {

  {SPACES}      { /* Nada */  }
  {TABS}        { checkSymbol(symbol("TAB")); }
  \n            { popState(); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  [^]             { /* comer */ }
}

<COMMENT> {
  {SPACES}      { if (inDesc) cadena.append(yytext());  }
  {TABS}        { if (inDesc) cadena.append(yytext()); 
                  checkSymbol(symbol("TAB")); 
                }  
  \n            { info.module.incComments(data);        
                  cadena.setLength(0);
                  popState();
                  if (inDesc) return literal("");
                }  
  \r            { }                
  [a-zA-Z0-9]+  { if (inDesc) cadena.append(yytext());
                  data = true;  
                }
   [^]            { if (inDesc) cadena.append(yytext()); 
                  data = true; 
                }    
}

/*
 * Caso especial para concatenar lineas de descripcion
 */
 /*
<COMMENT2> {
  {SPACES}      { if (inDesc) cadena.append(yytext());  }
  {TABS}        { checkSymbol(symbol("TAB")); 
                  if (inDesc) cadena.append(yytext());  
                }  
  \n            { info.module.incComments(data);
                  popState();
                  if (inDesc) return literalEx("");
                }  
\r              { /* do nothing */ }
  [a-zA-Z0-9]+  { if (inDesc) cadena.append(yytext());
                  data = true;  
                }
   .            { if (inDesc) cadena.append(yytext()); 
                  data = true; 
                }    
}
  
  */
<COPYS> {
  ^[\*\/]            { pushState(COMMENT);           }
  \.                 { popState(); return symbol(ENDCOPY);  }
  \r                 { info.buffer.append(yytext()); }
  \n                 { info.buffer.append(yytext()); }
  {WORD}             { info.buffer.append(yytext()); }
  .                  { info.buffer.append(yytext()); }  
}


// Transforma DFHRESP(xx) y DFHVALUE(xx) en DFHCICS

<CICSSYM> {
   ")"  { popState(); 
          if (info.inCode()) return symbol(ZCCSym.DFHCICS, cicsVerb);
          return symbol(ZCDSym.DFHCICS);
        }
   .    { /* do nothing */ }
   \r   { /* do nothing */ }
   \n   { info.module.incLines(data); }
   
}

<STEXEC> {
  ^[\*\/dD]       { pushState(COMMENT);       }
   CICS           { info.module.setCICS();
                    initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(CICSCODE);
                  } 
                      
   SQL            { info.module.setSQL(); 
                    initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(EXECSQL);
                  } 
   {SPACES}       { /* DO NOTHING */ }
  {TABS}          { checkSymbol(symbol("TAB")); }
   \n             { info.module.incLines(data); data = false; }
   \r             { /* do nothing */ }
    
}

<EMBEDDED> {
  ^[\*\/dD]          { pushState(COMMENT);       }
//  \'                 { info.buffer.append(yytext()); pushState(EMBEDDED_QUOTE); }
//  \"                 { info.buffer.append(yytext()); pushState(EMBEDDED_DQUOTE); }  
  END-EXEC[ ]*[\.]?  { popState(2); return symbol(ENDEXEC); }

  \r           { /* do nothing */ }
  \n           { info.buffer.append(yytext());
                 info.module.incLines(data); 
                 data = false;  
              }
   [^]        { info.buffer.append(yytext()); }                          
}

<EMBEDDED_QUOTE> {
  \'       { info.buffer.append(yytext()); popState(); }
  \r       { info.buffer.append(yytext()); }
  \n       { info.buffer.append(yytext());
             info.module.incLines(data); 
             data = false;  
           }
  [^]      { info.buffer.append(yytext()); }
}

<EMBEDDED_DQUOTE> {
  \"       { info.buffer.append(yytext()); popState(); }
  \r       { info.buffer.append(yytext()); }
  \n       { info.buffer.append(yytext());
             info.module.incLines(data); 
             data = false;  
           }
  [^]      { info.buffer.append(yytext()); }
}

<SDP> {
    .                  { /* Nada */ }
    \r                 { /* Nada */ }
    \n                 { info.module.incLines(data);
                         popState();
                       }
}

/*
[^]                      { throw new ParseException(MSG.EXCEPTION_TOKEN, 
                                   info.module.getName(), yyline + 1, yycolumn + 1, yytext()); 
                       }

*/