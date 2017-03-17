package com.jgg.sdp.parser.db2.lang;

import java.util.*;
import java_cup.runtime.Symbol;
import com.jgg.sdp.parser.base.*;
import static com.jgg.sdp.parser.db2.lang.DCLSym.*;

%%

%public
%class      DCLLexer
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
      return null;
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


SPACES=[\ \t]+
ID = [a-zA-Z][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*
ID2 = \.{ID}
HID=[a-zA-Z0-9][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*

ENTERO=[0-9]+
SIGNED=[+-]{1}{ENTERO}
DECNUM=[+|-]?[0-9]+[\.]?[0-9]+
DECFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
//\'[0-9A-Fa-f]+\'
NUMGRAPHIC=[UuGg][Xx]
// \'[0-9A-Fa-f]+\'

HOSTVAR  = :[ ]*{HID}

ATTR = MONTHS
     | MONTH
     | YEARS
     | YEAR
     | {HOSTVAR}
     
     
HOSTVAR_ATTR = {HOSTVAR}{SPACES}{ATTR}

%% 

  \xB5          { /* eat */ }

   ALL            { return symbol(ALL           ); }
   BY             { return symbol(BY            ); }
   COMMA          { return symbol(COMMA         ); }
   CURRENT        { return symbol(CURRENT       ); }
   CURSOR         { return symbol(CURSOR        ); }
   DATE           { return symbol(DATE          ); }
   DEC            { return symbol(DEC           ); }
   DEPENDENT      { return symbol(DEPENDENT     ); }
   DESCRIBE       { return symbol(DESCRIBE      ); }
   DIAGNOSTICS    { return symbol(DIAGNOSTICS   ); }
   EXPLAIN        { return symbol(EXPLAIN       ); }
   FROM           { return symbol(FROM          ); }
   GET            { return symbol(GET           ); }
   GRANT          { return symbol(GRANT         ); }
   INCLUDING      { return symbol(INCLUDING     ); }
   INPUT          { return symbol(INPUT         ); }
   LPAR           { return symbol(LPAR          ); }
   MAX            { return symbol(MAX           ); }
   MESSAGE_TEXT   { return symbol(MESSAGE_TEXT  ); }
   MIN            { return symbol(MIN           ); }
   OPTION         { return symbol(OPTION        ); }
   OUTPUT         { return symbol(OUTPUT        ); }
   PACKAGE        { return symbol(PACKAGE       ); }
   PLAN           { return symbol(PLAN          ); }
   PRIVILEGES     { return symbol(PRIVILEGES    ); }
   PROCEDURE      { return symbol(PROCEDURE     ); }
   PUBLIC         { return symbol(PUBLIC        ); }
   REVOKE         { return symbol(REVOKE        ); }
   ROLE           { return symbol(ROLE          ); }
   RPAR           { return symbol(RPAR          ); }
   SMALLINT       { return symbol(SMALLINT      ); }
   STACKED        { return symbol(STACKED       ); }
   STMTCACHE      { return symbol(STMTCACHE     ); }
   TABLE          { return symbol(TABLE         ); }
   TIMESTAMP      { return symbol(TIMESTAMP     ); }
   TIME           { return symbol(TIME          ); }
   TO             { return symbol(TO            ); }
   VERSION        { return symbol(VERSION       ); }
   WITH           { return symbol(WITH          ); }
 

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }
  

 {SPACES}          { /* DO NOTHING */              }
 {ID}              { return symbol(ID);            }

  // Caso : \n Host
  // ":"              { return symbol(PREHOST); }

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
 