package com.jgg.sdp.sql.lang;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;

%%

%public
%class      SQLExtract
%extends    SQLExtractBase
// %scanerror  SQLException

%public
%integer
%line
%column
%char
%full
%ignorecase

%xstate  ID_DIVISION , ENV_DIVISION , DATA_DIVISION , PROC_DIVISION
%xstate  CONF_SECT , IO_SECT

%xstate PIC , BLOBSIZE
%xstate ENDLINE , EATLINE 
%xstate STEXEC , EMBEDDED , SQL2
%xstate EMBEDDED_QUOTE , EMBEDDED_DQUOTE
%xstate CICSSYM

%xstate COMMENT     
// %xstate COMMENT2 
%xstate QUOTE_STRING , DQUOTE_STRING
  
%xstate SDP

// Estados para COPY REPLACING
%xstate COPYS  
%xstate COPYS_TOKEN

%{

  private void excepcion(int code) {
//      throw new NotSupportedException(code, module.getName(), yyline + 1, yycolumn + 1, yytext());
      throw new NotSupportedException(33, "pepe", yyline + 1, yycolumn + 1, yytext());
  }
  
%}

%init{
   initLexer();
%init}



SPACES=[ \t]+
ITEM=[a-zA-Z0-9\:\-\_]+
EXEC={SPACES}EXEC{SPACES} | {SPACES}EXECUTE{SPACES} 
%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO SOLO CONTROLA ID DIVISION                                     ***/
/******************************************************************************/
/******************************************************************************/

 ^[\*]        { pushState(COMMENT); }
 ^[\/]        { pushState(COMMENT); }  
 ^[dD]        { pushState(COMMENT); }
  END-EXEC    { /* do nothing */    }
  {EXEC}      { pushState(STEXEC);  }
 \'           { pushState(QUOTE_STRING);  }  
 \"           { pushState(DQUOTE_STRING); }
 \n           { /* do nothing */    }
  [^]         { /* do nothing */    }                           

/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'\'    { /* do nothing */  }    
  \'      { popState();       } 
  \n      { popState();       } 
  [^]   { /* do nothing */  }  
}

<DQUOTE_STRING> {
  \"\"   { /* do nothing */  }    
  \"     { popState();       }
  \n     { popState();       }
  [^]   { /* do nothing */  }
}


<COMMENT> {
  \n    { popState();       }  
  [^]   { /* do nothing */  }
}


<STEXEC> {
  ^[\*\/dD]       { pushState(COMMENT);       }
   CICS           { popState(); }
   SQL            { buffer = new StringBuilder(4095);
                    setBegLine(yyline);   
                    pushState(EMBEDDED);
                  } 
  {SPACES}        { /* do nothing */  }
   \n             { /* do nothing */  }
    
}

<EMBEDDED> {
  ^[\*\/dD]    { pushState(COMMENT);           }
  END-EXEC     { saveStatement(); popState(2); }
  INCLUDE      { popState(2);                  }
  {ITEM}       { buffer.append(yytext());      }
  {SPACES}     { buffer.append(yytext());      }


  \n           { buffer.append(yytext()); }
  [^]          { buffer.append(yytext()); }                      
}

