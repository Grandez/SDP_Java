package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.COPYSym.*;

%%

%public
%class      COPYLexer
%extends    GenericLexer
%scanerror  ParseException

%line
%column
%char
%full
%ignorecase
%cup

%xstate  TOKEN , EQUALS, QUOTE_STRING, DQUOTE_STRING

%{

   Symbol symbol(int code){
      return symbol(code, yyline, yycolumn, yytext());
   }
   
   Symbol token(int code) {
        print("Devuelve SYMBOL " + code + " (" + (cadLine + 1) + "," + cadCol + ") " + cadena.toString());
       return symbol(code, cadLine + 1, cadCol, cadena.toString());
   }   

   Symbol symbol(int code, String txt) {
      data = true;
      int col = yycolumn + COLOFFSET;
      
      if (code != 0) {      
          print("Devuelve SYMBOL " + code + " (" + (yyline + 1) + "," + col + ") " + txt);
      }    
      return symbol(code, yyline + 1, col, txt);
   }

%}

%init{
   initLexer();
%init}


WORD=[a-zA-Z0-9]+
SPACES=[ ]+
TABS=[\t]+

%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO                                                               ***/
/******************************************************************************/
/******************************************************************************/

   SUPPRESS          { return symbol(SUPPRESS);  }
   REPLACING         { return symbol(REPLACING); }
   BY                { /* eat */        }
   IN                { return symbol(IN);        }
   OF                { return symbol(OF);        }
   "=="              { resetCadena(yyline, yycolumn); pushState(EQUALS);          }
   \n                { /* nothing */ }
   \'                { resetCadena(yyline, yycolumn); pushState(QUOTE_STRING);    }
   \"                { resetCadena(yyline, yycolumn); pushState(DQUOTE_STRING);   }
   {SPACES}          { /* nothing */ }
   {TABS}            { /* nothing */ }
   .                 { cadena.append(yytext()); pushState(TOKEN); }
   <<EOF>>           { return symbol(EOF); }

<TOKEN> {
  {SPACES}           { popState(); return token(ID);   }
  {SPACES}           { popState(); return token(ID);   }
  {WORD}             { cadena.append(yytext());        } 
  \n                 { popState(); return token(ID);   }
  <<EOF>>            { popState(); return token(ID);   }
  .                  { cadena.append(yytext());        }
}

<EQUALS> {
   "=="              { popState(); return token(ID);   }
   .                 { cadena.append(yytext());        }
 }

<QUOTE_STRING> {
  \'\'          { cadena.append(yytext());  }    
  \'            { popState();  return token(ID);    }  
  [^]           { cadena.append(yytext()); }
}


<DQUOTE_STRING> {
  \"\"          { cadena.append(yytext());  }    
  \"            { popState();  return token(ID);    }  
  [^]           { cadena.append(yytext()); }
}

