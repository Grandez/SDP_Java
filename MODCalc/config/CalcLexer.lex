package com.jgg.sdp.calculator;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;

import static com.jgg.sdp.calculator.CalcSym.*;

%%

%public
%class      CalcLexer

%line
%column
%char
%full
%ignorecase
%cup

%init{
   symbolFactory = new ComplexSymbolFactory();
%init}

%eofval{
   return new Symbol(EOF, yyline, yycolumn, "EOF");
%eofval}


%{
   
   ComplexSymbolFactory symbolFactory = null;
   
   private Symbol symbol(int id) {
//       System.out.println("Devuelve " + yytext()); 
       return symbolFactory.newSymbol(yytext(), id, new Symbol(id, yyline, yycolumn, yytext()));
   }
          
   private Symbol decimal(int id) {
      System.out.println("Devuelve " + yytext());
      return new Symbol(id, yyline, yycolumn, new CalcItem(yytext())); 
   }
%}


SPACES=[\ \t]+

ALPHA=[a-zA-Z]+
ALPHANUM=[a-zA-Z0-9\_]
SIGNED=[+|-]?[0-9]+
EXP=[eE]{SIGNED}
NUMBER={SIGNED}([\.]?[0-9]+)?({EXP})?

VARBASE = [a-zA-Z]{ALPHANUM}*
VAR = {VARBASE}(\.{VARBASE})*


%% 

  AND           { return symbol(AND);     }
  OR            { return symbol(OR);      }
  
  "&&"          { return symbol(AND);     }
  "||"          { return symbol(OR);      }  
  

  "=="          { return symbol(EQ);      }  
  ">="          { return symbol(GE);      }
  "<="          { return symbol(LE);      }  
  "!="          { return symbol(NE);      }

  "="           { return symbol(ASSIGN);  }
  ">"           { return symbol(GT);      }
  "<"           { return symbol(LT);      }  

  "("           { return symbol(LPAR);    }
  ")"           { return symbol(RPAR);    }   
  "**"          { return symbol(POW);     }
  "+"           { return symbol(PLUS);    }
  "-"           { return symbol(MINUS);   }
  "*"           { return symbol(MULT);    }
  "/"           { return symbol(DIV);     }
  "%"           { return symbol(MOD);     }
  ";"           { return symbol(ENDS);    }

  ABS           { return symbol(ABS);     }
  LOG           { return symbol(LOG);     }
  SUM           { return symbol(SUM);     }
  PROD          { return symbol(SUM);     }  
  {NUMBER}      { return decimal(NUMBER); }
  {SPACES}      { /* nada */              }
  STORE         { return symbol(STORE);   }
  \${VAR}       { return symbol(GLOBAL);  }
  {VAR}         { return symbol(LOCAL);   }
  \n            { return symbol(ENDS);    }
  .             { System.out.println("JGG " + yytext()); }      
