package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.rules.components.RulesData;

import com.jgg.sdp.parser.symbols.SDPSymbol;

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
%xstate  ID_OPTIONS
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

   StringBuilder sdpData;
   RulesData rules = new RulesData();
   
   int    lastSymbol = -1;
   int    pushBack = 0;
      
   boolean beginPic  = true;   

   public Symbol levelOrNum() {
       int val = Integer.parseInt(yytext().trim());
       if (val == 66) return symbol(LEVEL66);
       if (val == 77) return symbol(LEVEL77);
       if (val == 88) return symbol(LEVEL88);
       if (val >  49) return symbol(NUMERO);
       
       if (lastSymbol == NUMERO) return symbol(NUMERO);
       return symbol(LEVELXX);
   }

   public Symbol symbolID(int code) {
       inDesc = false; 
       return symbol(code); 
   }                              
   
   public Symbol idToken() {
       String text = cadena.toString().trim();
       cadena.setLength(0);
       return symbol(IDTOKEN, text);
   } 
   public Symbol idPoint() {
       if (cadena.toString().trim().length() > 0) {
           cadena.append(yytext());
           return idToken();
       }
       return symbol(ENDP);
   } 
   
   public SDPSymbol sdpsymbol (int code)              { return (SDPSymbol) symbol(code, yyline, yycolumn, yytext()).value; }
   public SDPSymbol sdpsymbol (int code, String txt)  { return (SDPSymbol) symbol(code, yyline, yycolumn, txt).value;      }
   public Symbol    symbol    (int code)              { return symbol(code, yyline, yycolumn, yytext()); }
   public Symbol    symbol    (int code, String txt)  { return symbol(code, yyline, yycolumn, txt);      }
   
  
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
POINTS=\.[ \t]*\.
WORD=[a-zA-Z0-9\_\-]+
PGMNAME=[a-zA-Z0-9]+

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

 ^[\*]>[ \t]+SDP     { sdpData = new StringBuilder(); pushState(SDP); }
 ^[\*\/dD]           { pushState(COMMENT); 
                       commentInit(yytext(), yyline);  
                     }
 ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP, "SKIP"));  }
 ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }

IDENTIFICATION{BLANKS}DIVISION  { inDesc = false;
                                  resetState(ID_DIVISION);
                                  return symbol(DIV_ID);
                                }
ID{BLANKS}DIVISION              { inDesc = false;
                                  resetState(ID_DIVISION);
                                  return symbol(DIV_ID);
                                }

COPY         { initEmbedded(); 
               pushState(COPYS); 
               return symbol(COPY);     
             }
EXECUTE      { pushState(STEXEC);    }
EXEC         { pushState(STEXEC);    }

  
CBL                { rules.checkCompileDirective(sdpsymbol(DIRECTIVE)); pushState(EATLINE);  }
REPLACE            { excepcion(MSG.EXCEPTION_NOT_ALLOW, yytext(), yyline, yycolumn); }
{SPACES}           { /* nada */ }
{TABS}             { rules.checkTab(sdpsymbol(TAB)); }
{ID}               { rules.checkCompileDirective(sdpsymbol(DIRECTIVE)); pushState(EATLINE);  }

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
  ^[\*]>[ \t]+SDP     { sdpData = new StringBuilder(); pushState(SDP);  }
  ^[\*\/dD]           { pushState(COMMENT); 
                        commentInit(yytext(), yyline);  
                      }
  ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }

  PROGRAM-ID    { return symbolID(PGMID);        }
  AUTHOR        { pushState(ID_OPTIONS); return symbolID(AUTHOR);       }
  DATE-WRITTEN  { pushState(ID_OPTIONS); return symbolID(DATEW);        }
  DATE-COMPILED { pushState(ID_OPTIONS); return symbolID(DATEC);        }   
  INSTALLATION  { pushState(ID_OPTIONS); return symbolID(INSTALLATION); }
  SECURITY      { pushState(ID_OPTIONS); return symbolID(SECURITY);     }  
  
  PROGRAM       { data = true; }  

  REMARKS       { excepcion(MSG.EXCEPTION_NOT_ALLOW, yytext(), yyline, yycolumn); }
  
  COPY          { initEmbedded(); 
                  pushState(COPYS); 
                  return symbol(COPY);           
                }
  CLASSID       { return symbolID(CLASSID);   }
  FACTORYID     { return symbolID(FACTORYID); }
  OBJECTID      { return symbolID(OBJECTID);  }    
  METHODID      { return symbolID(METHODID);  }
  RECURSIVE     { return symbolID(RECURSIVE); }
  COMMON        { return symbolID(COMMON);    }
  INITIAL       { return symbolID(INITIAL);   }
                   
  EXECUTE       { pushState(STEXEC);          }
  EXEC          { pushState(STEXEC);          }

  ENVIRONMENT{BLANKS}DIVISION     { inDesc = false;
                                    resetState(ENV_DIVISION); 
                                    return tabsInText(symbol(DIV_ENV)); 
                                  }

  DATA{BLANKS}DIVISION            { resetState(DATA_DIVISION);
                                    inDesc = false;   
                                    return tabsInText(symbol(DIV_DATA));
                                  }

  PROCEDURE{BLANKS}DIVISION       { pushBack = yytext().length(); yyclose(); }
  \.               { return symbol(ENDP);   }
  {PGMNAME}        { return symbol(PGMNAME);   }
  {NUMERO}         { System.out.println("JGGERR INIT caracter: " + yytext() + "(" + yyline + "," + yycolumn + ")"); }
  {SPACES}         { /* nada */ }
  {TABS}           { rules.checkTab(sdpsymbol(TAB)); }

  \n               { info.module.incLines(data); data = false; }
  \r               { /* do nothing */ }
  [^]              { System.out.println("JGGERR INIT caracter: " + yytext() + "(" + yyline + "," + yycolumn + ")"); }
} 

/******************************************************************************/
/******************************************************************************/
/***    TOKENS DE IDENTIFICATION                                            ***/
/*** PUENDEN CONTENER VARIOS PUNTOS, LINEAS, ETC.                           ***/
/******************************************************************************/
/******************************************************************************/

<ID_OPTIONS> {
  ^[\*\/dD]           { pushState(COMMENT); 
                        commentInit(yytext(), yyline);  
                      }

  AUTHOR                       { yypushback(yytext().length()); popState();       }
  DATE-WRITTEN                 { yypushback(yytext().length()); popState();       }
  DATE-COMPILED                { yypushback(yytext().length()); popState();       }
  INSTALLATION                 { yypushback(yytext().length()); popState();       }
  REMARKS                      { yypushback(yytext().length()); popState();       }
  SECURITY                     { yypushback(yytext().length()); popState();       }
  ENVIRONMENT{BLANKS}DIVISION  { yypushback(yytext().length()); popState();       }
  DATA{BLANKS}DIVISION         { yypushback(yytext().length()); popState();       }
  PROCEDURE{BLANKS}DIVISION    { yypushback(yytext().length()); popState();       } 

 ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP")); }
 ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }

  {WORD}       { cadena.append(yytext()); }
  {NUMERO}     { cadena.append(yytext()); }
  {SPACES}     { cadena.append(yytext()); }
  {TABS}       { rules.checkTab(sdpsymbol(TAB));
                 cadena.append(" "); 
               }
  {POINTS}     { return idPoint();   }
  \.           { return idPoint();   }
  \n           { if (cadena.toString().length() > 0) return idToken();   }
  \r           { /* eat */ }

  [^]          { cadena.append(yytext()); }

}

/******************************************************************************/
/******************************************************************************/
/***             ENVIRONMENT DIVISION                                       ***/
/******************************************************************************/
/******************************************************************************/

<ENV_DIVISION> {

  ^[\*\/dD]           { pushState(COMMENT); 
                        commentInit(yytext(), yyline);  
                      }
  ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }
                                   
  CONFIGURATION{BLANKS}SECTION   { pushState(CONF_SECT); return tabsInText(symbol(CONF_SECTION)); }
  INPUT-OUTPUT{BLANKS}SECTION    { pushState(IO_SECT);   return tabsInText(symbol(IO_SECTION));   }
  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);   inDesc = false;
                                   return tabsInText(symbol(DIV_DATA));
                                 }

  PROCEDURE{BLANKS}DIVISION      { pushBack = yytext().length(); yyclose(); }

  COPY         { initEmbedded(); 
                 pushState(COPYS);   
                 return symbol(COPY);         
               }
  EXECUTE      { pushState(STEXEC);    }
  EXEC         { pushState(STEXEC);    }

  \.               { return symbol(ENDP); }                                   
 
  {SPACES}         { /* nada */          }
  {TABS}           { rules.checkTab(sdpsymbol(TAB)); }
  \n               { info.module.incLines(data); data = false; }

  [^]              { data = true; 
                     System.err.println("JGG: " + yyline + "-" + yycolumn + " -" + yytext() + "-"); }

 }


/******************************************************************************/
/******************************************************************************/
/***    CONFIGURATION SECTION                                               ***/
/******************************************************************************/
/******************************************************************************/

<CONF_SECT> {
  ^[\*\/dD]           { pushState(COMMENT); 
                        commentInit(yytext(), yyline);  
                      }
  ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  ALPHABET         { return symbol(ALPHABET);        }
  CHARACTER[Ss]?   { return symbol(CHARACTERS);      }
  CLASS            { return symbol(CLASS);           }
  COMMA            { return symbol(COMMA);           }
  CURRENCY         { return symbol(CURRENCY);        }
  DEBUGGING        { return symbol(DEBUGGING);       }
  DECIMAL-POINT    { return symbol(DECIMALPOINT);    }
  EBCDIC           { return symbol(EBCDIC);          }  
  IN               { return symbol(IN);              }  
  IS               { return symbol(IS);              }
  MEMORY           { return symbol(MEMORY);          }
  MODE             { return symbol(MODE);            }
  MODULES          { return symbol(MODULES);         }
  NATIVE           { return symbol(NATIVE);          }
  OBJECT-COMPUTER  { return symbol(OBJECT_COMPUTER); }  
  PICTURE          { return symbol(PICTURE);         }  
  REPOSITORY       { return symbol(REPOSITORY);      }
  SEGMENT          { return symbol(SEGMENT);         }
  SEQUENCE         { return symbol(SEQUENCE);        }
  SOURCE-COMPUTER  { return symbol(SOURCE_COMPUTER); }
  SPECIAL-NAMES    { return symbol(SPECIAL_NAMES);   }
  STANDARD         { return symbol(STANDARD);        }  
  SYMBOLIC         { return symbol(SYMBOLIC);        }
  SYMBOL           { return symbol(SYMBOL);          }  
  THRU             { return symbol(THRU);            }
  WITH             { return symbol(WITH);            }
  WORDS            { return symbol(WORDS);           }
  XMLSCHEMA        { return symbol(XMLSCHEMA);       }

  COPY         { initEmbedded(); 
                 pushState(COPYS); 
                 return symbol(COPY);     
               }
  EXECUTE      { pushState(STEXEC);    }
  EXEC         { pushState(STEXEC);    }

  INPUT-OUTPUT{BLANKS}SECTION    { popState();
                                   pushState(IO_SECT);
                                   return tabsInText(symbol(IO_SECTION));   
                                 }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   return tabsInText(symbol(DIV_DATA));
                                 }

  PROCEDURE{BLANKS}DIVISION      { pushBack = yytext().length(); yyclose(); }

  {NUMERO}         { return symbol(NUMERO); }   
  {ID}             { return symbol(ZCDSym.ID);     }
  {SPACES}         { /* nada */ }
  {TABS}           { rules.checkTab(sdpsymbol(TAB)); }
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
  ^[\*\/dD]           { pushState(COMMENT); 
                        commentInit(yytext(), yyline);  
                      }
  ^[ ]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP"));  }
  ^[ ]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT")); }

  ACCESS         { return symbol(ACCESS);       }
  ALTERNATE      { return symbol(ALTERNATE);    }
  AREA[Ss]?      { return symbol(AREAS);        }
  ASSIGN         { return symbol(ASSIGN);       }
  CHARACTER[Ss]? { return symbol(CHARACTERS);   }  
  DYNAMIC        { return symbol(DYNAMIC);      }
  DELIMITER      { return symbol(DELIMITER);    }  
  DISPLAY        { return symbol(ZCDSym.DISPLAY);      }
  DUPLICATES     { return symbol(DUPLICATES);   }  
  INDEXED        { return symbol(INDEXED);      }
  FILE-CONTROL   { return symbol(FILE_CONTROL); }
  FILE           { return symbol(FILE);         }
  IS             { data = true;                 }
  KEY            { return symbol(KEY);          }
  LINE           { return symbol(LINE);         }
  MODE           { data = true;                 }
  OPTIONAL       { return symbol(OPTIONAL);     }
  ORGANIZATION   { return symbol(ORGANIZATION); }
  PADDING        { return symbol(PADDING);      }  
  PASSWORD       { return symbol(PASSWORD);     }  
  RANDOM         { return symbol(RANDOM);       }
  RECORDING      { return symbol(RECORDING);    }
  RECORD         { return symbol(RECORD);       }
  RELATIVE       { return symbol(RELATIVE);     }
  RESERVE        { return symbol(RESERVE);      }
  SELECT         { return symbol(SELECT);       }
  SEQUENTIAL     { return symbol(SEQUENTIAL);   }
  STATUS         { return symbol(STATUS);       }
  STANDARD-1     { return symbol(STANDARD1);    }
  TO             { data = true;                 }
  WITH           { data = true;                 }

  COPY         { initEmbedded(); 
                 pushState(COPYS);  
                 return symbol(COPY);          
               }
  EXECUTE      { pushState(STEXEC);    }
  EXEC         { pushState(STEXEC);    }

  DATA{BLANKS}DIVISION           { resetState(DATA_DIVISION);
                                   inDesc = false;   
                                   return tabsInText(symbol(DIV_DATA));
                                 }

  PROCEDURE{BLANKS}DIVISION      { pushBack = yytext().length(); yyclose(); }
  
 ^\*{SDPMASTER}    { pushState(SDP);
                     // info.module.incComments(true);
                     inDesc = false;
                     data = true;
                     return symbol(SDPMASTER);   
                   }

  {SPACES}         { /* nada */ }
  {TABS}           { rules.checkTab(sdpsymbol(TAB)); }
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

  ^[\*\/dD]             { pushState(COMMENT); commentInit(yytext(), yyline);      }
  ^[ \t]+SKIP[1-9]?     { rules.checkPrintDirective(sdpsymbol(SKIP,  "SKIP"));    }
  ^[ \t]+EJECT[ ]*[\.]? { rules.checkPrintDirective(sdpsymbol(EJECT, "EJECT"));   }
  ^\-                   { /* JGG, Pendiente de revisar */                         }  
  ^[ \t]+[0-9]+         { tabsInText(symbol(ZCZSym.BLANKS)); return levelOrNum(); }
  
  FILE{BLANKS}SECTION            { return tabsInText(symbol(FILE_SECTION));    }
  WORKING-STORAGE{BLANKS}SECTION { return tabsInText(symbol(WORKING_SECTION)); }
  LOCAL-STORAGE{BLANKS}SECTION   { return tabsInText(symbol(LOCAL_SECTION));   }
  LINKAGE{BLANKS}SECTION         { return tabsInText(symbol(LINKAGE_SECTION)); }
  PROCEDURE{BLANKS}DIVISION      { pushBack = yytext().length(); yyclose();    }
  
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
  DFHRESP                        { pushState(CICSSYM);       }  
  DFHVALUE                       { pushState(CICSSYM);       }
  DISPLAY                        { return symbol(ZCDSym.DISPLAY);   }
           
  EXTERNAL                       { data = true;              }
  
  FD                             { return symbol(FD);        } 
  FILLER                         { return symbol(FILLER);    }
  FROM                           { data = true;              }  
  F                              { return symbol(FILLER);    }

  GLOBAL                         { data = true;              }
    
  INDEXED                        { return symbol(INDEXED);   }
  INDEX                          { return symbol(INDEX);     }
  IN                             { data = true;              }  
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
  OF                             { data = true;              }
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
  STANDARD-2                     { data = true;            }
  STANDARD-1                     { data = true;            }
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
  EXECUTE      { pushState(STEXEC);    }
  EXEC         { pushState(STEXEC);    }

  {DECIMAL}        { return symbol(NUMERO);  }
  {DECIMAL2}       { return symbol(NUMERO);  }  
  {NUMERO}         { return symbol(NUMERO);  }
  {HEXVALUE}       { return symbol(HEX_VAL); }
  {ID}             { return symbol(ID);      }
  
  {SPACES}         {  /* nada */ }
  {TABS}           { rules.checkTab(sdpsymbol(TAB)); }
  
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
  ^[\*\/dD]     { pushState(COMMENT); 
                  commentInit(yytext(), yyline);    
                }

  {SPACES}      { if (beginPic == false) popState();  }
  {TABS}        { rules.checkTab(sdpsymbol(TAB));   
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
  {TABS}        { rules.checkTab(sdpsymbol(TAB)); }
  \n            { popState(); return symbol(ENDP); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  .             { /* comer */ }
}

// Se come hasta fin de linea

<EATLINE> {

  {SPACES}      { /* Nada */  }
  {TABS}        { rules.checkTab(sdpsymbol(TAB)); }
  \n            { popState(); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  [^]           { /* comer */ }
}

<COMMENT> {
  {BLANKS}      { tabsInText(symbol(ZCZSym.BLANKS)); commentAppend(yytext()); }
  \n            { commentEnd(yyline);      }                 
  [a-zA-Z0-9]+  { commentAppend(yytext()); }
  [^]           { commentAppend(yytext()); }    
}
  
 
<COPYS> {
  ^[\*\/dD]          { pushState(COMMENT); 
                       commentInit(yytext(), yyline);  
                     }
  REPLACING          { appendEmbedded(yytext()); popState(ST_REPLACING); }
  \.                 { popState(); return symbol(ENDCOPY);  }
  \r                 { /* eat */ }
  \n                 { appendEmbedded(yytext()); }
  {WORD}             { appendEmbedded(yytext()); }
  .                  { appendEmbedded(yytext()); }  
}

<ST_REPLACING> {
  ^[\*\/dD]          { pushState(COMMENT); 
                       commentInit(yytext(), yyline);  
                     }
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
  ^[\*\/dD]       { pushState(COMMENT); 
                    commentInit(yytext(), yyline);  
                  }
   SQL            { info.module.setSQL(); 
                    initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(SQLDATA);
                  } 
   {SPACES}       { /* DO NOTHING */ }
   {TABS}         { rules.checkTab(sdpsymbol(TAB)); }
   \n             { info.module.incLines(data); data = false; }
   \r             { /* do nothing */ }
    
}

<EMBEDDED> {
  ^[\*\/dD]          { pushState(COMMENT); 
                       commentInit(yytext(), yyline);      
                     }
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
   {WORD}        { sdpData.append(yytext());  }
   " - "         { sdpData.append(yytext());  }
  {SPACES}       { sdpData.append(yytext());  }
    \.           { sdpData.append(yytext());  }
    .            { sdpData.append(yytext());  }
    \r           { /* Nada */ }
    \n           { info.module.incLines(data);  
                   info.prepareCase(sdpData.toString());
                   popState(2); 
                 }
}

<SDPDESC> {
   {WORD}        { sdpData.append(yytext());  }
   {SPACES}      { sdpData.append(yytext());  }
    \.           { sdpData.append(yytext());  }   
    .            { sdpData.append(yytext());  }
    \r           { /* Nada */ }
    \n           { info.module.incLines(data);  
                   info.module.setDescription(sdpData.toString());
                   popState(2); 
                 }
}
