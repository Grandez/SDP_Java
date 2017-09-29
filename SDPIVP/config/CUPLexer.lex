package com.jgg.sdp.ivp.lang;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.ivp.lang.CUPSym.*;

%%

%public
%class      CUPLexer
// %extends    GenericLexer
%scanerror  ParseException

%line
%column
%char
%full
%cup

%xstate COBOLDEF, ACTION 

%{

   private ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
    
   private Symbol symbol(int code) {
//   System.out.println("Devuelve: " + yyline + "-" + yycolumn + ": " + yytext());
         Symbol s = new Symbol(code, yyline+1, yycolumn+1, yytext());
         return symbolFactory.newSymbol(yytext(), code, s);
   }

%}


%eofval{
    return symbolFactory.newSymbol("EOF",EOF);
%eofval}

Newline = \r | \n | \r\n
Whitespace = [ \t\f] | {Newline}

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" {CommentContent} \*+ "/"
EndOfLineComment = "//" [^\r\n]* {Newline}
CommentContent = ( [^*] | \*+[^*/] )*
AllChars =  (.|\r|\n)+
Action = ("{:"){AllChars}(":}")
ident = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*

%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO                                                               ***/
/***   SE POSICIONA EN EL PRIMER TERMINAL                                   ***/
/******************************************************************************/
/******************************************************************************/

<YYINITIAL> {
  {Comment}     {                                              }
  {Whitespace}  {                                              }
  "terminal"    { yybegin(COBOLDEF); return symbol(TERMINAL);  }

  {ident}       {                                              }
   \n           { /* eat */              }
   \r           { /* eat */              }
   .            { /* eat */              }       
}

<COBOLDEF> {

  {Whitespace}  {                                    }
  "{:"          { yybegin(ACTION);                   }
  {Comment}     {                                    }  
  ";"           { return symbol(SEMI);               }
  ","           { return symbol(COMMA);              }
  "."           { return symbol(DOT);                }
  "|"           { return symbol(BAR);                }
  "["           { return symbol(LBRACK);             }
  "]"           { return symbol(RBRACK);             }
  ":"           { return symbol(COLON);              }
  "::="         { return symbol(COLON_COLON_EQUALS); }
  "%prec"       { return symbol(PERCENT_PREC);       }
  "terminal"    { return symbol(TERMINAL);           }
  "non"         { return symbol(NON);                }
  "nonterminal" { return symbol(NONTERMINAL);        }
  "precedence"  { return symbol(PRECEDENCE);         }
  "left"        { return symbol(LEFT);               }
  "right"       { return symbol(RIGHT);              }
  "nonassoc"    { return symbol(NONASSOC);           }
  {ident}       { return symbol(ID);                 }
  
}

<ACTION> {
  {Comment}     {                                              }
   ":}"   { yybegin(COBOLDEF); }
      \n           { /* eat */              }
   \r           { /* eat */              }
   .            { /* eat */              }       

}   