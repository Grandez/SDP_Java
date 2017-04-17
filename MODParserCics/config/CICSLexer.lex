package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.CICSSym.*;

%%

%public
%class      CICSLexer
%extends    GenericLexer
%implements GenericScanner

%line
%column
%char
%full
%ignorecase
%cup

%xstate STEXEC
%xstate STCICS
%xstate COMMENT
%xstate QUOTE
%xstate DQUOTE
%xstate QUOTE_STRING
%xstate DQUOTE_STRING

%{

   boolean litCont   = false;
   
   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline + info.getOffset();
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }

   public Symbol literal() {
      return literal(cadena.toString());
   }

   public Symbol literal(String text) {
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL - " + texto);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, texto);
      return symbolFactory.newSymbol(texto, LITERAL, s);
   }
               
   public Symbol symbol(int code){
      return symbol(code, yytext());
   }
   
   public Symbol symbol(int code, String txt) {
      data = true;
      int line = yyline + info.getOffset();
      print("Devuelve SYMBOL " + code + " (" + line  + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, line, yycolumn, txt);
      return symbolFactory.newSymbol(txt, code, s);
   }

%}

%init{
   initLexer();
%init}

%eofval{
   return symbolFactory.newSymbol("EOF", EOF);
%eofval}

SPACES=[\ \t]+

ALPHA=[a-zA-Z]+
ALPHANUM=[a-zA-Z0-9]
ENTERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,]?[0-9]+

ID = {ALPHANUM}({ALPHANUM}|\-|\_)*

EXEC = {SPACES}EXEC
EXECUTE = {SPACES}EXECUTE
CICS = {SPACES}CICS

%% 

/******************************************************************************/
/******************************************************************************/
/***                      CASOS GENERALES                                   ***/
/******************************************************************************/
/******************************************************************************/

  ^[\-]         { /* nada */              }
  IN            { return symbol(IN);      }
  LENGTH        { return symbol(LENGTH);  }
 
  OF            { return symbol(OF);      } 

  {ID}          { return symbol(ID);      }
  {ENTERO}      { return symbol(ENTERO);  }
  {DECIMAL}     { return symbol(DECIMAL); }
  "("           { return symbol(LPAR);    }
  ")"           { return symbol(RPAR);    }
  \'            { pushState(QUOTE_STRING);   }
  \"            { pushState(DQUOTE_STRING);  }  
  :             { /* nada */              }
  {SPACES}      { /* nada */              }
  \n            { /* nada */              }
  \r            { /* nada */              }
  .             { /* nada */              } 

 
<QUOTE_STRING> {
  \'\'         { cadena.append(yytext()); }
  \'           { popState(); return literal(); }
  \n           { popState();              }
  \r           { /* nada */               }
  [^]          { cadena.append(yytext()); }
}

<DQUOTE_STRING> {
  \"\"         { cadena.append(yytext()); }
  \"           { popState(); return literal(); }
  \n           { popState();              }
  \r           { /* nada */               }
  [^]          { cadena.append(yytext()); }
}

<COMMENT> {
 {SPACES}      { }
 \n            { popState(); }  
 \r            { }                
 [^]           { }

}
