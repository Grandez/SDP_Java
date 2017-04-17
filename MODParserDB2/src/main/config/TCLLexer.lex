package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.TCLSym.*;

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


SPACES=[\ \t]+
ID = [a-zA-Z][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*
ID2 = \.{ID}
HID=[a-zA-Z0-9][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*

ENTERO=[0-9]+
SIGNED=[+-]{1}{ENTERO}
DECNUM=[+|-]?[0-9]+[\.]?[0-9]+
DECFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
NUMGRAPHIC=[UuGg][Xx]

HOSTVAR  = :[ ]*{HID}

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
  