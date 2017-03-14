package com.jgg.sdp.cics.lang;

import java_cup.runtime.*;

import com.jgg.sdp.cics.base.*;
import com.jgg.sdp.core.exceptions.*;
import static com.jgg.sdp.cics.lang.CICSSym.*;
import com.jgg.sdp.parser.base.*;
%%

%public
%class      CICSLexer
%extends    GenericLexer
%implements GenericScanner, ILexer
%scanerror  ParseException
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

   boolean litCont = false;
   
   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline;
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }

   public Symbol literal(String txt) {
      cadena.append(txt);
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
      print("Devuelve SYMBOL " + code + " (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, yyline, yycolumn, txt);
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


^[\*\/dD] { if (yytext().length() < 5) pushState(COMMENT); }  
\'            { pushState(QUOTE);  }
\"            { pushState(DQUOTE); }
{EXEC}        { pushState(STEXEC); }
{EXECUTE}        { pushState(STEXEC); }
\n            { /* eat */ }
\r            { /* eat */ }
[^]           { /* eat */ }
 
/******************************************************************************/
/******************************************************************************/
/***                            INICIO                                      ***/
/******************************************************************************/
/******************************************************************************/

<STEXEC> {
  ^[\*\/dD] { if (yytext().length() < 5) pushState(COMMENT); }
   {CICS}       { pushState(STCICS); }
   {SPACES}     { /* do nothing */ }                   
    \n          { /* do nothing */ }
    \r          { /* do nothing */ }

   [^]          { popState(); }
} 

<STCICS> {
  ^[\*\/dD] { if (yytext().length() < 5) pushState(COMMENT); }
  END-EXEC      { popState(2); return symbol(END_EXEC);          }
  IN            { return symbol(IN);      }
  LENGTH        { return symbol(LENGTH);  }
  OF            { return symbol(OF);      }

  {ID}          { return symbol(ID);      }
  {ENTERO}      { return symbol(ENTERO);  }
  {DECIMAL}     { return symbol(DECIMAL); }
  "("           { return symbol(LPAR);    }
  ")"           { return symbol(RPAR);    }
  \'            { trimLiteral(true);  pushState(QUOTE_STRING);   }
  \"            { trimLiteral(true);  pushState(DQUOTE_STRING);  }  
  :             { /* nada */              }
  {SPACES}      { /* nada */              }
  \n            { /* nada */              }
  \r            { /* nada */              }
  .             { /* nada */              } 
}
 
<QUOTE> {
  ^[\-]     { if (yytext().length() < 5) litCont = true; }
  ^[\*\/dD] { if (yytext().length() < 5) pushState(COMMENT); }  
   \'           { if (litCont == false) popState();
                  litCont = false; 
                }
   \n           { /* nada */  }
   \r           { /* nada */  }
   [^]          { /* nada */  }
}

<DQUOTE> {
  ^[\-]     { if (yytext().length() < 5) litCont = true; }
  ^[\*\/dD] { if (yytext().length() < 5) pushState(COMMENT); }  
   \"           { if (litCont == false) popState();
                  litCont = false; 
                }
   \n           { /* nada */  }
   \r           { /* nada */  }
   [^]          { /* nada */  }
}
     
<QUOTE_STRING> {
  \'\'         { cadena.append(yytext()); }
  \'           { popState(); return literal(""); }
  [^]          { cadena.append(yytext()); }
}

<DQUOTE_STRING> {
  \"\"         { cadena.append(yytext()); }
  \"           { popState(); return literal(""); }
  [^]          { cadena.append(yytext()); }
}

<COMMENT> {
 {SPACES}      { if (inDesc) cadena.append(yytext());  }
 \n            { popState(); }  
 \r            { }                
 [^]           { }

}


