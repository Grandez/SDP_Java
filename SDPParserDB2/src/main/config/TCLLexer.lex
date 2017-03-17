package com.jgg.sdp.parser.db2.lang;

import java.util.*;

import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.db2.lang.TCLSym.*;

%%

%public
%class      TCLLexer
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
   public Symbol literal(String txt) {
      return null;
   }

/*
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
*/

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

  ALL           { return symbol(ALL         ); }
  COMMIT        { return symbol(COMMIT      ); }
  CURRENT       { return symbol(CURRENT     ); }
  CURSORS       { return symbol(CURSORS     ); }
  DATE          { return symbol(DATE        ); }
  DEC           { return symbol(DEC         ); }
  EXCLUSIVE     { return symbol(EXCLUSIVE   ); }
  HOSTVAR1      { return symbol(HOSTVAR1    ); }
  HOSTVAR2      { return symbol(HOSTVAR2    ); }
  ID            { return symbol(ID          ); }
  IN            { return symbol(IN          ); }
  LOCK          { return symbol(LOCK        ); }
  LOCKS         { return symbol(LOCKS       ); }
  MAX           { return symbol(MAX         ); }

  MIN           { return symbol(MIN         ); }
  MODE          { return symbol(MODE        ); }

  ON            { return symbol(ON          ); }
  PARTITION     { return symbol(PARTITION   ); }
  QUERYNO       { return symbol(QUERYNO     ); }
  REFRESH       { return symbol(REFRESH     ); }
  RELEASE       { return symbol(RELEASE     ); }
  RETAIN        { return symbol(RETAIN      ); }
  ROLLBACK      { return symbol(ROLLBACK    ); }
  SAVEPOINT     { return symbol(SAVEPOINT   ); }
  SHARE         { return symbol(SHARE       ); }
  SMALLINT      { return symbol(SMALLINT    ); }
  TABLE         { return symbol(TABLE       ); }
  TIMESTAMP     { return symbol(TIMESTAMP   ); }
  TIME          { return symbol(TIME        ); }
  TO            { return symbol(TO          ); }
  VERSION       { return symbol(VERSION     ); }
  WORK          { return symbol(WORK        ); }

 {SPACES}          { /* DO NOTHING */              }
 {ID}              { return symbol(ID);            }
 {ENTERO}          { return symbol(NUM_ENTERO);    }
 

  \n            { /* eat */ } 
  \r            { /* eat */ }
  
 /*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 


 