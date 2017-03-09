package com.jgg.sdp.parser.lang;

import java_cup.runtime.ComplexSymbolFactory.*;
import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.OCSym.*;

%%

%public
%class      OCLexer
%extends    GenericLexer
%implements CobolLexer

%line
%column
%char
%full
%ignorecase
%cup

%state  ID_DIVISION , ENV_DIVISION , DATA_DIVISION , PROC_DIVISION

%state  CONF_SECT , IO_SECT

%xstate COMMENT , COMMENT2 , QUOTE_STRING , DQUOTE_STRING
%xstate COPYS   
%xstate SDP

%{

   private Module module   = null;
   private Summary summary = null;
   private boolean data = false;
   private StringBuilder cadena = null;
   private int           litLine;
   private int           litColumn;
   
   
   private boolean inCode      = false;  // Estamos en PROCEDURE DIVISION?
   private boolean inDesc      = false;  // Procesando Descripcion?
   private boolean pendingEndP = false;  // Falta un punto?
    
   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline;
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }
         
   public void setFullName(String fullName) {
      module = ModulesFactory.getModule(fullName);
      summary = module.getSummary();
   }
   
   public String getFullName() {
      return module.getFullName();
   }
      
   public void print(String txt) {
//      System.out.println(txt);
   }   

   public Symbol literal(String txt) {
      cadena.append(txt);
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL - " + texto);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, texto);
      return symbolFactory.newSymbol(texto, LITERAL, s);
   }

   public Symbol literalEx(String txt) {
      cadena.append(txt);
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL EX - " + texto);
      Symbol s = new Symbol(LITCONCAT, litLine, litColumn, texto);
      return symbolFactory.newSymbol(texto, LITCONCAT, s);
   }
            
   public Symbol symbol(int code){
      return symbol(code, yytext());
   }
   
   public Symbol symbol(int code, String txt) {
      data = true;
      print("Devuelve SYMBOL (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, code, s);
   }

%}

%init{
   data = false;
   stack.push(0);
   
%init}

%eofval{
    return symbolFactory.newSymbol("EOF", EOF);
%eofval}

SPACES=[\ \t]+

ALPHA=[a-zA-Z]+
NUMERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,]?[0-9]+

id = {ALPHA}({ALPHA}|{NUMERO}|\-)*
SP=[ ]{1}
bloque = {SP}{id} 

SDPD=DESC  | DESCRIPTION
SDPDESC=[>]?[\ \t]+SDP[\ \t]+{SDPD}
SDPEND=[>]?[\ \t]+SDP[\ \t]+END
SDPMASTER=[>]?[\ \t]+SDP[\ \t]+MASTER

%% 

/******************************************************************************/
/******************************************************************************/
/***                      CASOS GENERALES                                   ***/
/******************************************************************************/
/******************************************************************************/


IDENTIFICATION{SPACES}DIVISION  { resetState(ID_DIVISION);
                                  inDesc = false;
                                  return symbol(DIV_ID);
                                }
ENVIRONMENT{SPACES}DIVISION     { pendingEndP = true;
                                  inDesc = false;
                                  resetState(ENV_DIVISION); 
                                  return symbol(DIV_ENV); 
                                }

DATA{SPACES}DIVISION            { resetState(DATA_DIVISION);
                                  inDesc = false;   
                                  return symbol(DIV_DATA);
                                }

PROCEDURE{SPACES}DIVISION       { resetState(PROC_DIVISION);
                                  inCode = true;
                                  inDesc = false;                                  
                                  return symbol(DIV_PROC);
                                }

COPY                            { if (inCode) return symbol(COPY);
                                  pushState(COPYS);  
                                }

{SPACES}                        { /* nada */        }

 ^\*{SDPEND}       { summary.incComments(true);
                     inDesc = false;
                     data = true;
                   }
                    

 ^\*{SDPDESC}      { resetLiteral("");
                     pushState(SDP);
                     summary.incComments(true);
                     inDesc = true;
                     data = true;
                     return symbol(SDPDESC);   
                   }

^\*>                            {        print("Entra en *>");pushState(COMMENT2);  }
^\*                             {        print("Entra en *");pushState(COMMENT);   }

\'                              { resetLiteral(""); 

                                  pushState(QUOTE_STRING);   
                                }
\"                              { resetLiteral("");
                                  pushState(DQUOTE_STRING); 
                                }  

\n                              { summary.incLines(data);
                                  data = false; 
                                }
\r                              {  }

/******************************************************************************/
/******************************************************************************/
/***               IDENTIFICATION DIVISION                                  ***/
/******************************************************************************/
/******************************************************************************/

<ID_DIVISION> {

  PROGRAM-ID        { inDesc = false; return symbol(PGMID);  }
  AUTHOR            { inDesc = false; return symbol(AUTHOR); }
  {id}              { return symbol(ID);     }

  \.                { return symbol(ENDP);     }
  .                 { System.err.println("IDENT ERROR Encuentra -" + yytext() + "-"); }

} 

/******************************************************************************/
/******************************************************************************/
/***             ENVIRONMENT DIVISION                                       ***/
/******************************************************************************/
/******************************************************************************/

<ENV_DIVISION> {

  CONFIGURATION{SPACES}SECTION   { pendingEndP = true;
                                   pushState(CONF_SECT);   
                                   return symbol(CONF_SECTION); 
                                 }
  INPUT-OUTPUT{SPACES}SECTION    { pendingEndP = true;
                                   pushState(IO_SECT);
                                   return symbol(IO_SECTION);   
                                 }
  \.                             { data = true;
                                   if (pendingEndP == true) {
                                       pendingEndP = false;
                                       return symbol(ENDP);
                                   }
                                 }                                   
 }

/******************************************************************************/
/***    CONFIGURATION SECTION                                               ***/
/***    Nos lo comemos                                                      ***/
/******************************************************************************/

<CONF_SECT> {
   SOURCE-COMPUTER  { data = true; }
   OBJECT-COMPUTER  { data = true; }
   INPUT-OUTPUT{SPACES}SECTION    { popState();
                                    pushState(IO_SECT);
                                    return symbol(IO_SECTION);   
                                  }
   {id}             { data = true; }
   \.               { data = true;
                      if (pendingEndP == true) {
                          pendingEndP = false;
                          return symbol(ENDP);
                      }  
                    }
}

/******************************************************************************/
/***    INPUT-OUTPUT SECTION                                                ***/
/******************************************************************************/

<IO_SECT> {

  FILE-CONTROL                   { return symbol(FILE_CONTROL); }
  DISPLAY                        { return symbol(DISPLAY);      }
  ASSIGN                         { return symbol(ASSIGN);       }
  SELECT                         { return symbol(SELECT);       }
  STATUS                         { return symbol(STATUS);       }

  ORGANIZATION                   { return symbol(ORGANIZATION); }
  SEQUENTIAL                     { return symbol(SEQUENTIAL); }
  ACCESS                         { return symbol(ACCESS); }
  DYNAMIC                        { return symbol(DYNAMIC); }
  RANDOM                         { return symbol(RANDOM); }
  RELATIVE                       { return symbol(RELATIVE); }
  INDEXED                        { return symbol(INDEXED); }
  RECORD                         { return symbol(RECORD); }
    
  OPTIONAL                       { data = true; }
  KEY                            { data = true; }
  FILE                           { data = true; }  
  LINE                           { data = true; }
  MODE                           { data = true; }
  TO                             { data = true; }
  IS                             { data = true; }

 ^\*{SDPMASTER}    { pushState(SDP);
                     summary.incComments(true);
                     inDesc = false;
                     data = true;
                     return symbol(SDPMASTER);   
                   }

  {id}                           { return symbol(ID);   }     
  \.                             { return symbol(ENDP); }
    
  .       { System.err.println("I-O ERROR Encuentra en (" + yyline + "," + yycolumn + " -" + yytext() + "-"); }
}


/******************************************************************************/
/******************************************************************************/
/***                    DATA DIVISION                                       ***/
/******************************************************************************/
/******************************************************************************/

<DATA_DIVISION> {
  FILE{SPACES}SECTION            { return symbol(FILE_SECTION);    }
  WORKING-STORAGE{SPACES}SECTION { return symbol(WORKING_SECTION); }
  LOCAL-STORAGE{SPACES}SECTION   { return symbol(LOCAL_SECTION);   }
  LINKAGE{SPACES}SECTION         { return symbol(LINKAGE_SECTION); }
  SCREEN{SPACES}SECTION          { return symbol(SCREEN_SECTION);  }
 
 ^{SPACES}77                     { return symbol(LEVEL_77);        }
 ^{SPACES}78                     { return symbol(LEVEL_78);        }
 ^{SPACES}88                     { return symbol(LEVEL_88);        }
  ^{SPACES}[0-9]{2}              { return symbol(LEVEL);           }
  FILLER                         { return symbol(FILLER); }
  FD                             { return symbol(FD);     }
  F                              { return symbol(FILLER); }
  IS                             { data = true;            }
  EXTERNAL                       { data = true;            }
  GLOBAL                         { data = true;            }
  REDEFINES                      { return symbol(REDEFINES); }
  PICTURE                        { return symbol(PICTURE);   }
  PIC                            { return symbol(PICTURE);   }
  SPACE                          { return symbol(SPACES);    }
  SPACES                         { return symbol(SPACES);    }
  SYNCHRONIZED                   { data = true;              }
  SYNC                           { data = true;              }
  USAGE                          { data = true;              }
  JUSTIFIED                      { data = true;              }
  JUST                           { data = true;              }
  RIGHT                          { data = true;              }
  BLANK[ ]+WHEN[ ]+ZERO          { data = true;              }
  BLANK[ ]+ZERO                  { data = true;              }
  BASED                          { data = true;              }
  ANY[ ]+LENGTH                  { data = true;              }
  VALUE                          { return symbol(VALUE);     }
  ALL                            { data = true;              }
  SIGN                           { data = true;              }
  LEADING                        { data = true;              }
  TRAILING                       { data = true;              }
  SEPARATE                       { data = true;              }
  OCCURS                         { return symbol(OCCURS);    }
  TIMES                          { data = true;              }
  DEPENDING                      { data = true;              }
  ON                             { data = true;              }
  TO                             { return symbol(TO);        }
  ASCENDING                      { data = true;              }
  DESCENDING                     { data = true;              }
  KEY                            { data = true;              }
  IS                             { data = true;              }
  INDEXED                        { return symbol(INDEXED);   }
  BY                             { data = true;              }
  ZERO[S]?                       { return symbol(ZERO);      }
  ZEROES                         { return symbol(ZERO);      }
  (S)?[9]+(V[9]+)?               { return symbol(PIC_NUMERIC);  }
  [X]+                           { return symbol(PIC_ALPHANUM); }
  A                              { return symbol(PIC_ALPHA);    }
  "("                            { return symbol(LPAR);         }
  ")"                            { return symbol(RPAR);         }  
  {id}                           { return symbol(ID);      }
  {NUMERO}                       { return symbol(NUMERO); }    

  \.                             { return symbol(ENDP); }
}

/******************************************************************************/
/******************************************************************************/
/***                    PROCEDURE DIVISION                                  ***/
/******************************************************************************/
/******************************************************************************/

<PROC_DIVISION> {
  ^{bloque}         { return symbol(PARRAFO); }
  ^[ ]{1}END{SPACES}PROGRAM         {   return symbol(END_PGM); }
  /*
  ^\*>[\ \t]+SDP[\-\_]BEG { resetLiteral("");
                             pushState(SDP);
                             summary.incComments(true);
                             return symbol(SDPBEG);   
                      }
  ^\*>[\ \t]+SDP[\-\_]END { resetLiteral("");
                             pushState(SDP);
                             summary.incComments(true);
                             return symbol(SDPEND);   
                      }
  */
  /* VERBOS */
  
  INITIALIZE        { return symbol(INITIALIZE); }                                       
  TRANSFORM         { return symbol(TRANSFORM);  }                                           
  ALLOCATE          { return symbol(ALLOCATE);   }                                           
  CONTINUE          { return symbol(CONTINUE);   }                                           
  EVALUATE          { return symbol(EVALUATE);   }                                           
  MULTIPLY          { return symbol(MULTIPLY);   }                                           
  SUBTRACT          { return symbol(SUBTRACT);   }                                           
  UNSTRING          { return symbol(UNSTRING);   }                                           
  COMPUTE           { return symbol(COMPUTE);    }                                           
  DISPLAY           { return symbol(DISPLAY);    }                                           
  INSPECT           { return symbol(INSPECT);    }
  SECTION           { return symbol(SECTION);    }                                           
  PERFORM           { return symbol(PERFORM);    }
  RELEASE           { return symbol(RELEASE);    }                                           
  REWRITE           { return symbol(REWRITE);    }
  SECTION           { return symbol(SECTION); }                                            
  ACCEPT            { return symbol(ACCEPT);  }                                           
  CANCEL            { return symbol(CANCEL);  }                                           
  CLOSE             { return symbol(CLOSE );  }                                           
  COMMIT            { return symbol(COMMIT);  }                                           
  DELETE            { return symbol(DELETE);  }                                           
  DIVIDE            { return symbol(DIVIDE);  }
  GOBACK            { return symbol(GOBACK);  }                                           
  GO[ ]+TO          { return symbol(GOTO, "GOTO");    }                                           
  RETURN            { return symbol(RETURN);  }                                           
  SEARCH            { return symbol(SEARCH);  }                                           
  STRING            { return symbol(STRING);  }                                           
  UNLOCK            { return symbol(UNLOCK);  }                                           
  WRITE             { return symbol(WRITE);   }                                           
  MERGE             { return symbol(MERGE);   }                                           
  START             { return symbol(START);   }
  USING             { return symbol(USING);   }                                           
  CALL              { return symbol(CALL);    }
  EXIT[ \t]+PERFORM { return symbol(EXITP);   }
  EXIT              { return symbol(EXIT);    }  
  FREE              { return symbol(FREE);    }                                           
  MOVE              { return symbol(MOVE);    }                                           
  NEXT[ ]+SENTENCE  { return symbol(NEXT);    }                                           
  OPEN              { return symbol(OPEN);    }                                           
  READ              { return symbol(READ);    }                                           
  STOP[ ]+RUN       { return symbol(STOPRUN); }
  ADD               { return symbol(ADD);     }
  SET               { return symbol(SET);     }                                           
  IF                { return symbol(IF);      }

  /* CALIFICADORES */
  
  INPUT             { return symbol(INPUT);   }
  OUTPUT            { return symbol(OUTPUT);  }
  EXTEND            { return symbol(EXTEND);  }
  "I-O"             { return symbol(IO);      }

  THRU              { return symbol(THRU);    }
  THROUGH           { return symbol(THRU);    }
  FOREVER           { return symbol(FOREVER); }
  VARYING           { return symbol(VARYING); }
  TIMES             { return symbol(TIMES);   }

  UNTIL             { return symbol(UNTIL);   }                                                                                   
  AND               { return symbol(AND);     }
  OR                { return symbol(OR);      }
  
  ELSE              { return symbol(ELSE);    }
  WHEN              { return symbol(WHEN);    }

  INVALID           { return symbol(CHECK);      }
  /*
  "AT"{SPACES}"END" { return symbol(ATEND, "ATEND"); }
*/
"AT"{SPACES}"END"   { throw new ParseException(MSG.EXCEPTION_NOT_ALLOW, 
                                   module.getName(), yyline + 1, yycolumn + 1, yytext()); 
                    }
  "END-IF"          { return symbol(ENDIF);      }    
  "END-PERFORM"     { return symbol(ENDPERFORM); }
  "END-EVALUATE"    { return symbol(ENDEVAL);    }
  EOP               { return symbol(CHECK);      }
  END\-[A-Z]+       { return symbol(ENDVERB);    }
  END               { return symbol(CHECK);      }
        
  "("               { return symbol(LPAR);    }
  ")"               { return symbol(RPAR);    }
 
/*******************************************************/  
/* Palabras reservadas y operadores que se van a comer */
/*******************************************************/

 "END-OF-PAGE"     { data = true; }
 DELIMITER         { data = true; }
 FUNCTION          { data = true; }
 EQUAL             { data = true; }
 RECORD            { data = true; }
 SECTION           { data = true; }
 CYCLE             { data = true; }
 AFTER             { data = true; }
 BEFORE            { data = true; }
 TO                { data = true; }
 REEL              { data = true; }
 UNIT              { data = true; }
 REMOVAL           { data = true; }
 REWIND            { data = true; }
 LOCK              { data = true; }
 THEN              { data = true; }
 KEY               { data = true; }
 NOT               { data = true; } 
 AT                { data = true; }
 TO                { data = true; }

 "**"              { data = true; }
 "*"               { data = true; }  
 ">="              { data = true; }
 "<="              { data = true; }  
 "="               { data = true; }
 "+"               { data = true; }
 "-"               { data = true; }
 ">"               { data = true; }
 "<"               { data = true; }
 "/"               { data = true; }
 ","               { data = true; }
 ":"               { data = true; }
 
 {id}               { return symbol(ID);      }
 {DECIMAL}          { return symbol(NUMERO);  }
 {NUMERO}           { return symbol(NUMERO);  }
  \.                { return symbol(ENDP);    }
  

}

/******************************************************************************/
/******************************************************************************/
/***                               otros                                    ***/
/******************************************************************************/
/******************************************************************************/

/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'               { popState();
                     return literal("");
                   }
   .               { cadena.append(yytext()); }
}

<DQUOTE_STRING> {
  \"               { popState();
                     return literal("");
                   }
   .               { cadena.append(yytext()); }
}


<COMMENT> {
  {SPACES}      { if (inDesc) cadena.append(yytext());  }
  \n            { summary.incComments(data);
                  popState();
                  if (inDesc) return literal("");
                }  
  [a-zA-Z0-9]+  { if (inDesc) cadena.append(yytext());
                  data = true;  
                }
   .            { if (inDesc) cadena.append(yytext()); 
                  data = true; 
                }    
}

/*
 * Caso especial para concatenar lineas de descripcion
 */
<COMMENT2> {
  {SPACES}      { if (inDesc) cadena.append(yytext());  }
  \n            { summary.incComments(data);
                  popState();
                  if (inDesc) return literalEx("");
                }  
  [a-zA-Z0-9]+  { if (inDesc) cadena.append(yytext());
                  data = true;  
                }
   .            { if (inDesc) cadena.append(yytext()); 
                  data = true; 
                }    
}
  
<COPYS> {
    {id}               { data = true;
                         module.addCopy(yytext());
                       }
    \.                 { data = true; popState(); }                   
    \n                 { summary.incLines(data);  }
    {SPACES}           { /* DO NOTHING */ }
}

<SDP> {
    .                  { /* Nada */ }
    \n                 { summary.incLines(data);
                         popState();
                       }
}


.                      { throw new ParseException(MSG.EXCEPTION_TOKEN, 
                                   module.getName(), yyline + 1, yycolumn + 1, yytext()); 
                       }

