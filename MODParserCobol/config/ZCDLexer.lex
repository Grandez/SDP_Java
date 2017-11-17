package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.ZCDSym.*;
import static com.jgg.sdp.parser.lang.ZCZSym.*;

%%

%public
%class      ZCDLexer
%extends    GenericLexer
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
%xstate ST_FUNCTION

%xstate COMMENT        
%xstate QUOTE_STRING   
%xstate DQUOTE_STRING 
  
%xstate SDP, SDPIVP, SDPIVPDESC, SDPDESC

// Estados para COPY REPLACING
%xstate COPYS , ST_REPLACING, REP_EQUALS, REP_DQUOTE, REP_QUOTE  

%{

   StringBuilder tmp;
   
   String cpyName = null;
   String cicsVerb = null;
   int    lastSymbol = -1;
   int    prevSymbol = -1;
   int    nPars = 0;
   int    inIndex = 0;
   Symbol begExec = null;

   int    pushBack = 0;
      
   boolean beginPic  = true;   
   boolean prevSpace = false;

/*
   public Symbol literal(boolean clean) { 
       String txt = cadena.toString();
       if (clean) cadena.setLength(0);
       return literal(txt); 
   }
   
   public Symbol literal(String txt) {
      setLastSymbol(LITERAL);
      print("Devuelve LITERAL - " + txt);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, txt);
      Symbol x = symbolFactory.newSymbol(txt, LITERAL, s);
      
      // Espacio es el primer caracter no imprimible en ASCII
      // Character.codePointAt(new char[] {'a'},0)
      
      for (int idx = 0; idx < txt.length(); idx++) {
          if (txt.charAt(idx) < ' ') {
              ruleNoPrintable(litLine, litColumn);
              break;
          } 
      }
      return x;      
   }

*/
   public Symbol levelOrNum() {
       if (lastSymbol == NUMERO) return symbol(NUMERO);
       return symbol(LEVEL);
   }

                              
   public Symbol symbol(int code)             { return symbol(code, yytext(), yyline, yycolumn); }
   public Symbol symbol(int code, String txt) { return symbol(code, yytext(), yyline, yycolumn); }

/*
   public Symbol symbol(int code, String txt) {
      setLastSymbol(code);
      data = true;
      int col = yycolumn + COLOFFSET;
      
      if (code != 0) {      
          print("Devuelve SYMBOL " + code + " (" + (yyline + 1) + "," + col + ") " + txt);
      }    
      return symbolFactory.newSymbol(txt, code, new Symbol(code, yyline + 1, col, txt));
   }

   private void setLastSymbol(int id) {
      prevSymbol = lastSymbol;
      lastSymbol = id;
   }

  private void excepcion(int code) {
      throw new NotSupportedException(code, info.module.getName(), yyline + 1, yycolumn + 1, yytext());
  }
*/
  
%}

%init{
   initLexer(COMMENT);
%init}

%eofval{

    if (yymoreStreams()) {
        info.unit.removeMember();
        yypopStream();
    }
    else {
         info.addOffset(yyline + 1);
         info.addOffset(yychar - pushBack);
         return new java_cup.runtime.Symbol(ZCDSym.EOF);
    }
    info.removeOffset();    
%eofval}

SPACES=[ ]+
TABS=[\t]+
BLANKS=[ \t]+

// Generico para cargar buffer
WORD=[a-zA-Z0-9\_\-]+

NUMERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,][0-9]+
DECIMAL2=[\.\,][0-9]+
HEXVALUE=[xX][\'\"][0-9aAbBcCdDeEfF]+[\'\"]

ALPHANUM=[a-zA-Z0-9]
ID = {ALPHANUM}({ALPHANUM}|\-|\_)*

SIZE=\({BLANKS}*{NUMERO}[kKmM]?{BLANKS}*\)
PICLEN=[sS\+\-]?[aAxXzZ9]?{SIZE}

SDPMASTER=[>]?[\ \t]+SDP[\ \t]+MASTER

%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO SOLO CONTROLA ID DIVISION                                     ***/
/******************************************************************************/
/******************************************************************************/

 ^[\*]>[ \t]+SDP     { tmp = new StringBuilder(); pushState(SDP); }
 ^[\*\/dD]           { commentInit(yytext(), yyline);  }
 ^[ ]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
 ^[ ]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }

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
               return symbol(COPY);     
             }
EXECUTE      { begExec = symbolDummy(EXEC);   pushState(STEXEC);    }
EXEC         { begExec = symbolDummy(EXEC);   pushState(STEXEC);    }

  
CBL                { ruleCompileDirective(yyline + 1); pushState(EATLINE);  }
REPLACE            { excepcion(MSG.EXCEPTION_NOT_ALLOW, yytext(), yyline, yycolumn); }
{SPACES}           { /* nada */ }
{TABS}             { ruleTabs(yyline, yycolumn); }
{ID}               { ruleCompileDirective(yyline + 1);   pushState(EATLINE);    }



\n                 { info.module.incLines(data); data = false; }
\r                 { /* do nothing */ }

  [^]              { System.out.println("JGGERR INIT caracter: " + yytext() + "(" + yyline + "," + yycolumn + ")"); }
//  .              { System.out.println("JGGERR INIT caracter: " + yytext()); }                             


/******************************************************************************/
/******************************************************************************/
/***               IDENTIFICATION DIVISION                                  ***/
/******************************************************************************/
/******************************************************************************/

<ID_DIVISION> {
  ^[\*]>[ \t]+SDP     { tmp = new StringBuilder(); pushState(SDP);  }
  ^[\*\/dD]           { commentInit(yytext(), yyline);  }
  ^[ ]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
  ^[ ]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }

  AUTHOR           { inDesc = false; return symbol(AUTHOR); }
  COMMON           { data = true; }
  
  DATE-WRITTEN     { return symbol(DATEW);    }
  DATE-COMPILED    { return symbol(DATEC);    }  
  
  INITIAL          { data = true; }  
  INSTALLATION     { inDesc = false; return symbol(INSTALLATION); }
  
  PROGRAM-ID       { inDesc = false; return symbol(PGMID);  }
  PROGRAM          { data = true; }  

  RECURSIVE        { data = true; }
  REMARKS          { excepcion(MSG.EXCEPTION_NOT_ALLOW, yytext(), yyline, yycolumn); }
  
  COPY         { initEmbedded(); 
                 pushState(COPYS); 
                 return symbol(COPY);           
               }
  EXECUTE      { begExec = symbolDummy(EXEC);  pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(EXEC);     pushState(STEXEC);    }

  ENVIRONMENT{BLANKS}DIVISION     { inDesc = false;
                                    resetState(ENV_DIVISION); 
                                    ruleTabsInText(yytext(), yyline, yycolumn);
                                    return symbol(DIV_ENV); 
                                  }

  DATA{BLANKS}DIVISION            { resetState(DATA_DIVISION);
                                    inDesc = false;   
                                    ruleTabsInText(yytext(), yyline, yycolumn);
                                    return symbol(DIV_DATA);
                                  }

  PROCEDURE{BLANKS}DIVISION       { ruleTabsInText(yytext(), yyline, yycolumn); 
                                    pushBack = yytext().length(); 
                                    yyclose(); 
                                  }
  
  {ID}             { return symbol(ZCDSym.ID);     }
  {NUMERO}         { return symbol(NUMERO); }
  {SPACES}         { /* nada */ }
  {TABS}           { ruleTabs(yyline, yycolumn); }

  \.               { return symbol(ENDP);   }
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

  ^[\*\/dD]           { commentInit(yytext(), yyline);  }
  ^[ ]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
  ^[ ]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }
                                   
  CONFIGURATION{BLANKS}SECTION   { pushState(CONF_SECT);   
                                   ruleTabsInText(yytext(), yyline, yycolumn);
                                   return symbol(CONF_SECTION); 
                                 }
                                 
  INPUT-OUTPUT{BLANKS}SECTION    { pushState(IO_SECT);
                                   ruleTabsInText(yytext(), yyline, yycolumn);
                                   return symbol(IO_SECTION);   
                                 }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;
                                   ruleTabsInText(yytext(), yyline, yycolumn);   
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { ruleTabsInText(yytext(), yyline, yycolumn);
                                   pushBack = yytext().length(); 
                                   yyclose(); 
                                 }

  COPY         { initEmbedded(); 
                 pushState(COPYS);   
                 return symbol(COPY);         
               }
  EXECUTE      { begExec = symbolDummy(EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(EXEC);    pushState(STEXEC);    }

  \.               { return symbol(ENDP); }                                   
 
  {SPACES}         { /* nada */          }
  {TABS}           { ruleTabs(yyline, yycolumn); }
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
  ^[\*\/dD]           { commentInit(yytext(), yyline);  }
  ^[ ]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
  ^[ ]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }

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
                 return symbol(COPY);     
               }
  EXECUTE      { begExec = symbolDummy(EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(EXEC); pushState(STEXEC);    }

  INPUT-OUTPUT{BLANKS}SECTION    { popState();
                                   pushState(IO_SECT);
                                   ruleTabsInText(yytext(), yyline, yycolumn);
                                   return symbol(IO_SECTION);   
                                 }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   ruleTabsInText(yytext(), yyline, yycolumn);
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { ruleTabsInText(yytext(), yyline, yycolumn);
                                   pushBack = yytext().length(); 
                                   yyclose(); 
                                 }

  {NUMERO}         { return symbol(NUMERO); }   
  {ID}             { return symbol(ZCDSym.ID);     }
  {SPACES}         { /* nada */ }
  {TABS}           { ruleTabs(yyline, yycolumn); }
  \.               { return symbol(ENDP);   }
  \,               { data = true; }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }

  .       { System.err.println("I-O ERROR Configuration Encuentra en (" + yyline + "," + yycolumn + " -" + yytext() + "-"); }
}

/******************************************************************************/
/***    INPUT-OUTPUT SECTION                                                ***/
/******************************************************************************/

<IO_SECT> {
  ^[\*\/dD]           { commentInit(yytext(), yyline);  }
  ^[ ]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
  ^[ ]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }

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
  STATUS         { return symbol(STATUS);       }
  TO             { data = true;                 }
  WITH           { data = true;                 }

  COPY         { initEmbedded(); 
                 pushState(COPYS);  
                 return symbol(COPY);          
               }
  EXECUTE      { begExec = symbolDummy(EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(EXEC); pushState(STEXEC);    }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   ruleTabsInText(yytext(), yyline, yycolumn);
                                   return symbol(DIV_DATA);
                                 }

  PROCEDURE{BLANKS}DIVISION      { ruleTabsInText(yytext(), yyline, yycolumn);
                                   pushBack = yytext().length(); 
                                   yyclose(); 
                                 }
  
 ^\*{SDPMASTER}    { pushState(SDP);
                     // info.module.incComments(true);
                     inDesc = false;
                     data = true;
                     return symbol(SDPMASTER);   
                   }

  {SPACES}         { /* nada */ }
  {TABS}           { ruleTabs(yyline, yycolumn); }
  {NUMERO}         { return symbol(NUMERO); }   
  {ID}             { return symbol(ID);     }
  \.               { return symbol(ENDP);          }
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

  ^[\*\/dD]             { commentInit(yytext(), yyline);  }
  ^[ \t]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }
  ^[ \t]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }
  ^\-                   { /* JGG, Pendiente de revisar */ }  
  ^[ \t]+[0-9]+         { return levelOrNum(); }
  
  FILE{BLANKS}SECTION            { ruleTabsInText(yytext(), yyline, yycolumn); return symbol(FILE_SECTION);    }
  WORKING-STORAGE{BLANKS}SECTION { ruleTabsInText(yytext(), yyline, yycolumn); return symbol(WORKING_SECTION); }
  LOCAL-STORAGE{BLANKS}SECTION   { ruleTabsInText(yytext(), yyline, yycolumn); return symbol(LOCAL_SECTION);   }
  LINKAGE{BLANKS}SECTION         { ruleTabsInText(yytext(), yyline, yycolumn); return symbol(LINKAGE_SECTION); }
  PROCEDURE{BLANKS}DIVISION      { ruleTabsInText(yytext(), yyline, yycolumn);
                                   pushBack = yytext().length(); 
                                   yyclose(); 
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
  DEPENDING                      { return symbol(DEPENDING); }
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
  IN                             { return symbol(IN);        }  
  IS                             { data = true;              }
  
  JUSTIFIED                      { data = true;              }
  JUST                           { data = true;              }

  KEY                            { data = true;              }
  
  LABEL                          { return symbol(LABEL);     }
  LEADING                        { data = true;              }
  LINAGE                         { return symbol(LINAGE);    }  
  LINE[sS]?                      { data = true;              }
  
  MODE                           { data = true;              }
  
  NULL[sS]?                      { return symbol(NULL);      }

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
  POINTER                        { return symbol(POINTER); }

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
  TO                             { return symbol(TO);      }
  TRAILING                       { data = true;            }
  THROUGH                        { return symbol(THRU);    }
  THRU                           { return symbol(THRU);    }
  TYPE                           { data = true;            }
  
  USAGE                          { return symbol(USAGE);   }
    
  VALUE[sS]?                     { return symbol(VALUE);   }
  VARYING                        { data = true;            }  

  ZERO[sS]?                      { return symbol(ZERO);    }
  ZEROES                         { return symbol(ZERO);    }
  SPACE[sS]?                     { return symbol(SPACES);  }
  LOW\-VALUE[sS]?                { return symbol(LOWVAL);  }
  HIGH\-VALUE[sS]?               { return symbol(HIGHVAL); } 
  QUOTE[sS]?                     { return symbol(QUOTE);   }

  COPY         { initEmbedded(); 
                 pushState(COPYS);         
                 return symbol(COPY);  
               }
  EXECUTE      { begExec = symbolDummy(EXEC); pushState(STEXEC);    }
  EXEC         { begExec = symbolDummy(EXEC); pushState(STEXEC);    }

  {DECIMAL}        { return symbol(NUMERO);  }
  {DECIMAL2}       { return symbol(NUMERO);  }  
  {NUMERO}         { return symbol(NUMERO);  }
  {HEXVALUE}       { return symbol(HEX_VAL); }
  {ID}             { return symbol(ID);      }
  
  {SPACES}         {  /* nada */ }
  {TABS}           { ruleTabs(yyline, yycolumn); }
  
  "("              { return symbol(LPAR);    }
  ")"              { return symbol(RPAR);    }
  \.               { return symbol(ENDP);    }
  \,               { data = true; }
  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  \n               { info.module.incLines(data); data = false;  }
  \r               { /* do nothing */ }

}

/* PICTURE es PIC espacios formato [espacios|punto] con comentarios o sin ellos */
<PIC> {
  ^[\*\/dD]     { commentInit(yytext(), yyline);    }

  {SPACES}      { if (beginPic == false) popState();  }
  {TABS}        { ruleTabs(yyline, yycolumn);   
                  if (beginPic == false) popState(); 
                }  
  {PICLEN}      { beginPic = false; return symbol(PIC_LEN);      }

  [\.\,\$][\-\+\*\$zZ09]+        { beginPic = false; return symbol(PIC_FMT);      }
  [sS\+\-]?[09bBaAxXzZ\-\+\$]+   { beginPic = false; return symbol(PIC_FMT);      }              
  [Ee][+-]?[9]+                  { beginPic = false; return symbol(PIC_FMT);      }
  [sSvV]+                        { beginPic = false; return symbol(PIC_DEC_EMPTY);      }
  [vV]{NUMERO}                   { beginPic = false; return symbol(PIC_V);        }
  V                              { beginPic = false; return symbol(PIC_DEC_EMPTY);      }

  \.                             { popState(); return symbol(ENDP); }
  
  \n                             { info.module.incLines(data); }
  \r                             { /* do nothing */ }
  [^]                            { System.out.println("JGGERR DATA caracter: " + yytext()); }
  .                            { System.out.println("JGGERR DATA caracter: " + yytext()); }                             
}

<BLOBSIZE> {
  {SIZE}                       { popState(); return symbol(PIC_LEN);      }
}


/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'\'          { cadena.append(yytext());  }    
  \'            { popState();  
                  return literal(LITERAL, true); 
                }  
                // Tiene que haber continuacion
  \n            { popState(); } 
  \r            { /* eat */ }

  [^]           { cadena.append(yytext()); }
}


<DQUOTE_STRING> {
  \"\"          { cadena.append(yytext());  }    
  \"            { popState(); 
                  return literal(LITERAL, true); 
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
  {TABS}        { ruleTabs(yyline, yycolumn); }
  \n            { popState(); return symbol(ENDP); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  .             { /* comer */ }
}

// Se come hasta fin de linea

<EATLINE> {

  {SPACES}      { /* Nada */  }
  {TABS}        { ruleTabs(yyline, yycolumn); }
  \n            { popState(); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  [^]           { /* comer */ }
}

<COMMENT> {
  {BLANKS}      { ruleTabsInText(yytext(), yyline, yycolumn);
                  commentAppend(yytext()); 
                }
  \n            { commentEnd(yyline);      }                 
  [a-zA-Z0-9]+  { commentAppend(yytext()); }
  [^]           { commentAppend(yytext()); }    
}
  
 
<COPYS> {
  ^[\*\/dD]          { commentInit(yytext(), yyline);  }
  REPLACING          { appendEmbedded(yytext()); popState(ST_REPLACING); }
  \.                 { popState(); return symbol(ENDCOPY);  }
  \r                 { /* eat */ }
  \n                 { appendEmbedded(yytext()); }
  {WORD}             { appendEmbedded(yytext()); }
  .                  { appendEmbedded(yytext()); }  
}

<ST_REPLACING> {
  ^[\*\/dD]          { commentInit(yytext(), yyline);  }
 "=="                { appendEmbedded(yytext()); pushState(REP_EQUALS); }
  \"                 { appendEmbedded(yytext()); pushState(REP_DQUOTE); } 
  \'                 { appendEmbedded(yytext()); pushState(REP_QUOTE); }  
  \.                 { popState(2); return symbol(ENDCOPY);  }
  \r                 { /* eat */ }
  \n                 { appendEmbedded(yytext()); }
  {WORD}             { appendEmbedded(yytext()); }
  .                  { appendEmbedded(yytext()); }  
}

<REP_EQUALS> {
 "=="                { appendEmbedded(yytext()); popState(); }
  \n                 { appendEmbedded(yytext()); } 
  .                  { appendEmbedded(yytext()); }  
}

<REP_DQUOTE> {
  \"                 { appendEmbedded(yytext()); popState(); } 
  .                  { appendEmbedded(yytext()); }  
}
<REP_QUOTE> {
  \'                 { appendEmbedded(yytext()); popState(); } 
  .                  { appendEmbedded(yytext()); }  
}

// Transforma DFHRESP(xx) y DFHVALUE(xx) en DFHCICS

<CICSSYM> {
   ")"  { popState(); return symbol(ZCDSym.DFHCICS); }
   .    { /* do nothing */ }
   \r   { /* do nothing */ }
   \n   { info.module.incLines(data); }
   
}

<STEXEC> {
  ^[\*\/dD]       { commentInit(yytext(), yyline);  }
   SQL            { info.module.setSQL(); 
                    initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(SQLDATA);
                  } 
   {SPACES}       { /* DO NOTHING */ }
   {TABS}         { ruleTabs(yyline, yycolumn); }
   \n             { info.module.incLines(data); data = false; }
   \r             { /* do nothing */ }
    
}

<EMBEDDED> {
  ^[\*\/dD]          { commentInit(yytext(), yyline);      }
  END-EXEC[ ]*[\.]?  { popState(2); return symbol(ENDSQL); }

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
   IVP           { pushState(SDPIVP);  }
   DESCRIPTION   { pushState(SDPDESC); }
   DESC          { pushState(SDPDESC); }
   
  {SPACES}       { /* Nada */          }
     
    .            { /* Nada */ }
    \r           { /* Nada */ }
    \n           { info.module.incLines(data);  popState(); }
}

<SDPIVP> {
   {WORD}        { tmp.append(yytext());  }
   " - "         { tmp.append(yytext());  }
  {SPACES}       { tmp.append(yytext());  }
    \.           { tmp.append(yytext());  }
    .            { tmp.append(yytext());  }
    \r           { /* Nada */ }
    \n           { info.module.incLines(data);  
                   info.prepareCase(tmp.toString());
                   popState(2); 
                 }
}

<SDPDESC> {
   {WORD}        { tmp.append(yytext());  }
   {SPACES}      { tmp.append(yytext());  }
    \.           { tmp.append(yytext());  }   
    .            { tmp.append(yytext());  }
    \r           { /* Nada */ }
    \n           { info.module.incLines(data);  
                   info.module.setDescription(tmp.toString());
                   popState(2); 
                 }
}
