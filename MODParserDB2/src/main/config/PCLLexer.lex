package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;
import com.jgg.sdp.parser.base.*;
import static com.jgg.sdp.parser.lang.PCLSym.*;

%%

%public
%class      PCLLexer
%extends    GenericLexer
%implements GenericScanner

%line
%column
%char
%full
%ignorecase
%cup

%xstate QUOTE_STRING, DQUOTE_STRING

%{
   public Symbol symbol(int code){
      return makeSymbol(code, yyline, yycolumn, yytext());
   }   
%}


%init{
   initLexer();
%init}

%eofval{
    return symbolFactory.newSymbol("EOF", EOF);
%eofval}


SPACES=[\ ]+
TABS=[\t]+
ID = [a-zA-Z0-9][a-zA-Z0-9\_\-\#]*

ENTERO=[0-9]+
SIGNED=[+-]{1}{ENTERO}
DECNUM=[+|-]?[0-9]+[\.]?[0-9]+
DECFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
NUMGRAPHIC=[UuGg][Xx]

HOSTVAR1    = :{ID}
HOSTVAR2    = :[ ]+{ID}
IDQUALIFIED = \.{ID} 
FULLTABLE   = {ID}\.\*


%% 

  \xB5          { /* eat */ }

  {ENTERO}          { return symbol(NUM_ENTERO);    }

  {HOSTVAR2}        { return symbol(HOSTVAR2);      }
  {HOSTVAR1}        { return symbol(HOSTVAR1);      }
  {IDQUALIFIED}     { return symbol(IDQUALIFIED);   }
  
   ABSOLUTE                 { return symbol(ABSOLUTE           ); }
   AFTER                    { return symbol(AFTER              ); }   
   ALLOCATE                 { return symbol(ALLOCATE           ); }
   ASSOCIATE                { return symbol(ASSOCIATE          ); }
   AT                       { return symbol(AT                 ); }   
   BEFORE                   { return symbol(BEFORE             ); }
   BEGIN                    { return symbol(BEGIN              ); }
   CALL                     { return symbol(CALL               ); }
   CLOSE                    { return symbol(CLOSE              ); }
   CONNECT                  { return symbol(CONNECT            ); }
   CONTINUE                 { return symbol(CONTINUE           ); }
   CURRENT                  { return symbol(CURRENT            ); }
   CURSOR                   { return symbol(CURSOR             ); }
   DECLARE                  { return symbol(DECLARE            ); }
   END                      { return symbol(END                ); }
   EXECUTE                  { return symbol(EXECUTE            ); }
   FETCH                    { return symbol(FETCH              ); }   
   FIRST                    { return symbol(FIRST              ); }   
   FOR                      { return symbol(FOR                ); }
   FOUND                    { return symbol(FOUND              ); }
   FREE                     { return symbol(FREE               ); }
   FROM                     { return symbol(FROM               ); }   
   GOTO                     { return symbol(GOTO               ); }
   HOLD                     { return symbol(HOLD               ); }
   IMMEDIATE                { return symbol(IMMEDIATE          ); }  
   INCLUDE                  { return symbol(INCLUDE            ); }
   INSENSITIVE              { return symbol(INSENSITIVE        ); }
   INTO                     { return symbol(INTO               ); }   
   LAST                     { return symbol(LAST               ); }
   LOCATOR                  { return symbol(LOCATOR            ); }
   LPAR                     { return symbol(LPAR               ); }
   NEXT                     { return symbol(NEXT               ); }      
   NOT                      { return symbol(NOT                ); }
   OPEN                     { return symbol(OPEN               ); }   
   PREPARE                  { return symbol(PREPARE            ); }
   PRIOR                    { return symbol(PRIOR              ); }   
   PROCEDURE                { return symbol(PROCEDURE          ); }
   RELATIVE                 { return symbol(RELATIVE           ); }   
   RESET                    { return symbol(RESET              ); }
   RESULT                   { return symbol(RESULT             ); }
   RPAR                     { return symbol(RPAR               ); }
   ROWSET                   { return symbol(ROWSET             ); }   
   ROWS                     { return symbol(ROWS               ); }
   SECTION                  { return symbol(SECTION            ); }
   SENSITIVE                { return symbol(SENSITIVE          ); }   
   SET                      { return symbol(SET                ); }
   SIGNAL                   { return symbol(SIGNAL             ); }
   SQLERROR                 { return symbol(SQLERROR           ); }
   SQLWARNING               { return symbol(SQLWARNING         ); }
   STARTING                 { return symbol(STARTING           ); }   
   TO                       { return symbol(TO                 ); }
   USER                     { return symbol(USER               ); }
   USING                    { return symbol(USING              ); }
   WHENEVER                 { return symbol(WHENEVER           ); }
   WITH                     { return symbol(WITH               ); }
 

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }
  

 {SPACES}          { /* DO NOTHING */              }
 {TABS}            { /* DO NOTHING */              } 
 {ID}              { return symbol(ID);            }

  ","              { return symbol(COMMA);     }

  "("              { return symbol(LPAR);     }  
  ")"              { return symbol(RPAR);     }

  // Caso : \n Host
  ":"              { return symbol(PREHOST); }

  \n            { /* eat */ } 
  \r            { /* eat */ }
  
 /*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'\'          { cadena.append(yytext());  }    
  \'            { return literal(LITERAL);  }  
  \n            { return literal(LITERAL);  } 
  \r            { /* eat */ }

  [^]           { cadena.append(yytext()); }
}

<DQUOTE_STRING> {
  \"\"          { cadena.append(yytext());  }    
  \"            { return literal(LITERAL);  }
  \n            { return literal(LITERAL);  }
  \r            { /* eat */   }

  [^]           { cadena.append(yytext()); }
}
 