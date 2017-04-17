package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;
import com.jgg.sdp.parser.base.*;
import static com.jgg.sdp.parser.lang.DDLSym.*;

%%

%public
%class      DDLLexer
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
HOSTVAR1  = :{HID}
HOSTVAR2  = :[ ]+{HID}

ENTERO=[0-9]+
SIGNED=[+-]{1}{ENTERO}
DECNUM=[+|-]?[0-9]+[\.]?[0-9]+
DECFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
NUMGRAPHIC=[UuGg][Xx]


ATTR = MONTHS
     | MONTH
     | YEARS
     | YEAR
     | {HOSTVAR}
     
     
HOSTVAR_ATTR = {HOSTVAR}{SPACES}{ATTR}

%% 

  \xB5          { /* eat */ }

  ALIAS           { return symbol(ALIAS        ); }
  ALTER           { return symbol(ALTER        ); }
  AUXILIARY       { return symbol(AUXILIARY    ); }
  COMMENT         { return symbol(COMMENT      ); }
  CONTEXT         { return symbol(CONTEXT      ); }
  CREATE          { return symbol(CREATE       ); }
  DATABASE        { return symbol(DATABASE     ); }
  DROP            { return symbol(DROP         ); }
  FUNCTION        { return symbol(FUNCTION     ); }
  GLOBAL          { return symbol(GLOBAL       ); }
  INDEX           { return symbol(INDEX        ); }
  MASK            { return symbol(MASK         ); }
  PERMISSION      { return symbol(PERMISSION   ); }
  PROCEDURE       { return symbol(PROCEDURE    ); } 
  RENAME          { return symbol(RENAME       ); }
  ROLE            { return symbol(ROLE         ); }
  SEQUENCE        { return symbol(SEQUENCE     ); }
  STOGROUP        { return symbol(STOGROUP     ); }
  SYNONIM         { return symbol(SYNONIM      ); }
  TABLE           { return symbol(TABLE        ); }
  TABLESPACE      { return symbol(TABLESPACE   ); }
  TEMPORARY       { return symbol(TEMPORARY    ); }
  TO              { return symbol(TO           ); }  
  TRIGGER         { return symbol(TRIGGER      ); }
  TRUSTED         { return symbol(TRUSTED      ); }
  TYPE            { return symbol(TYPE         ); }
  VIEW            { return symbol(VIEW         ); }
 

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }
  
  // Caso : \n Host
  ":"              { return symbol(PREHOST); }

 {SPACES}          { /* DO NOTHING */              }
 {ID}              { return symbol(ID);            }
 {HOSTVAR1}        { return symbol(HOSTVAR1);      }
 {HOSTVAR2}        { return symbol(HOSTVAR2);      }    
  ","              { return symbol(COMMA);         }

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
 