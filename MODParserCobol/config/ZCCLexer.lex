package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.ZCCSym.*;
import static com.jgg.sdp.parser.lang.ZCZSym.*;

%%

%public
%class      ZCCLexer
%extends    GenericLexer
%implements GenericScanner
%scanerror  ParseException

%line
%column
%char
%full
%ignorecase
%cup

%xstate ENDLINE , EATLINE 
%xstate STEXEC , EMBEDDED 
%xstate CICSSYM
%xstate ST_FUNCTION

%xstate COMMENT, COMMENT2        
%xstate QUOTE_STRING   
%xstate DQUOTE_STRING 

%xstate COPYS  

%{

   String cicsVerb = null;
   
   int    lastSymbol = -1;
   int    prevSymbol = -1;

   Symbol begExec = null;
   
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
      setLastSymbol(LITERAL);
      print("Devuelve LITERAL - " + txt);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, txt);
      Symbol x = symbolFactory.newSymbol(txt, LITERAL, s);
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
                  
   public void checkCharacters() {
      //JGG mirar tabs  No hace nada
   }
     
   public void checkSymbol() {
      // Chequea el uso de SKIP, EJECT, etc
   }                  
   
   public Symbol symbol(int code){
      return symbol(code, yytext());
   }

   public Symbol symbolCheck(int code) {
      checkSymbol();
      return symbol(code);
   }
   
   public Symbol symbol(int code, String txt) {
      setLastSymbol(code);
      data = true;
      int col = yycolumn + COLOFFSET;
      int line = yyline + offset;
      
      if (code != 0) {      
          print("Devuelve SYMBOL " + code + " (" + (line + 1) + "," + col + ") " + txt);
      }    
      return symbolFactory.newSymbol(txt, code, new Symbol(code, line + 1, col, txt));
   }

   private void setLastSymbol(int id) {
      prevSymbol = lastSymbol;
      lastSymbol = id;
   }

  private void excepcion(int code) {
      int col = yycolumn + COLOFFSET;
      int line = yyline + offset;  
      throw new NotSupportedException(code, info.module.getName(), line + 1, col + 1, yytext());
  }
  
%}

%init{
   initLexer(COMMENT);
%init}

%eofval{

    if (yymoreStreams()) {
        info.unit.removeSource();
        info.removeOffset();
        yypopStream();
    }
    else {    
        return symbolFactory.newSymbol("EOF", ZCCSym.EOF);
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

  ^[\*]               { commentInit(yytext(), yyline);  }
  ^[\/]               { commentInit(yytext(), yyline);  }  
  ^[dD]               { commentInit(yytext(), yyline);  }
  ^[ ]+SKIP[1-9]?     { checkSymbol();                  }
  ^[ ]+EJECT[ ]*[\.]? { checkSymbol();                  }
  ^\-                 { checkSymbol();                  }  

  ^[ ]+EXECUTE        { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  ^[ ]+EXEC           { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }

  PROCEDURE{BLANKS}DIVISION       { return symbol(PROC_DIV); }
  
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
  DCBS              { return symbol(DCBS);       }
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
  FUNCTION         { pushState(ST_FUNCTION); return symbol(FUNCTION); }
 
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

  NEGATIVE         { return symbol(NEGATIVE); }
  NEXT             { data = true; }
  NOT              { data = true; }

  OF               { return symbol(ZCCSym.OF); }
  ON               { data = true;  }     
  OR               { return symbol(OR);        }
  OTHER            { return symbol(OTHER);     }
  OUTPUT           { return symbol(OUTPUT);    }
  OVERFLOW         { return symbol(OVERFLOW);  }

  PAGE             { data = true;  }
  POINTER          { return symbol(ZCCSym.POINTER);  }
  POSITIVE         { return symbol(POSITIVE);        }
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

 "("               { return symbol(ZCCSym.LPAR);  }
 ")"               { return symbol(ZCCSym.RPAR);  }

 ">="              { return symbol(REL_GE); }
 "<="              { return symbol(REL_LE); }
 "**"              { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_POW); }
 "*"               { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_MUL); }
 "/"               { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_DIV); }
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
  {TABS}        { checkCharacters();   }

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
                          if (c == 'D' || c == 'd') commentInit(yytext(), yyline); 
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
  


/******************************************************************************/
/******************************************************************************/
/***                               otros                                    ***/
/******************************************************************************/
/******************************************************************************/

<ST_FUNCTION> {
  {SPACES}           { /* EAT */ }
  {TABS}             { checkCharacters();   }
  ^[\*\/]            { commentInit(yytext(), yyline, true);   }
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
  {TABS}        { checkCharacters();   }
  \n            { popState(); return symbol(ENDP); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  .             { /* comer */ }
}

// Se come hasta fin de linea

<EATLINE> {

  {SPACES}      { /* Nada */  }
  {TABS}        { checkCharacters();   }
  \n            { popState(); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  [^]             { /* comer */ }
}

<COMMENT> {
  {BLANKS}      { commentAppend(yytext()); }
  \n            { commentEnd();            }                 
  [a-zA-Z0-9]+  { commentAppend(yytext()); }
  [^]           { commentAppend(yytext()); }    
}

/*
 * Caso especial para concatenar lineas de descripcion
 */
 
<COMMENT2> {
  {SPACES}      { if (inDesc) cadena.append(yytext());  }
  {TABS}        { checkCharacters();   
                  if (inDesc) cadena.append(yytext());  
                }  
  \n            { info.module.incComments(data);
                  popState();
                  if (inDesc) return literal("");
                }  
\r              { /* do nothing */ }
  [a-zA-Z0-9]+  { if (inDesc) cadena.append(yytext());
                  data = true;  
                }
   .            { if (inDesc) cadena.append(yytext()); 
                  data = true; 
                }    
}
  
 
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
   ")"  { popState(); return symbol(DFHCICS, cicsVerb); }
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
                      
   SQL            { initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(SQLCODE);
                  } 
   {SPACES}       { /* DO NOTHING */ }
   {TABS}         { checkCharacters(); }
   \n             { info.module.incLines(data); data = false; }
   \r             { /* do nothing */ }
    
}

<EMBEDDED> {
  ^[\*\/dD]          { pushState(COMMENT);       }
  END-EXEC[ ]*[\.]?  { popState(2); return symbol(ENDEXEC); }

  \r           { /* do nothing */ }
  \n           { info.buffer.append(yytext());
                 info.module.incLines(data); 
                 data = false;  
              }
   [^]        { info.buffer.append(yytext()); }                          
}
