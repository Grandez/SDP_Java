package com.jgg.sdp.parser.db2.lang;

import java.util.*;
import java_cup.runtime.Symbol;
import com.jgg.sdp.parser.base.*;
import static com.jgg.sdp.parser.db2.lang.PCLSym.*;

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

   Stack<Integer> pars = new Stack<Integer>();
   HashSet<Integer> words = new HashSet<Integer>();
      
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
      lastID = LITERAL;
      cadena.append(txt);
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL (" + LITERAL + ") - " + texto);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, texto);
//      if (texto.contains("%")) {
//          return symbolFactory.newSymbol(texto, MASK, s);
//      }
      return symbolFactory.newSymbol(texto, LITERAL, s);
   }

   public Symbol symbol(int code){
      return symbol(code, yytext());
   }
   
   public Symbol symbol(int code, String txt) {
      data = true;
      lastID = code;
      print("Devuelve SYMBOL(" + code + ") - (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, code, s);
   }
/*
   public Symbol symbolic(int value) {
      data = true;
      String txt = Integer.toString(value);
      print("Devuelve SYMBOL (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(ENTERO, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, ENTERO, s);
   }
*/
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
//\'[0-9A-Fa-f]+\'
NUMGRAPHIC=[UuGg][Xx]
// \'[0-9A-Fa-f]+\'

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
 