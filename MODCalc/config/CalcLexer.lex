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

   private Symbol varLocal(int id) {
       return symbol(id, yytext().substring(1));
   }   

   private Symbol varGlobal(int id) {
       String str = yytext();
       return symbol(id, str.substring(1, str.length() - 1));
   }   
   
   private Symbol symbol(int id) {
       return symbol(id, yytext());
   }
   
   private Symbol symbol(int id, String txt) {
//       System.out.println("Devuelve " + txt); 
       return symbolFactory.newSymbol(txt, id, new Symbol(id, yyline, yycolumn, txt));
   }
          
   private Symbol decimal(int id) {
//      System.out.println("Devuelve " + yytext());
      return new Symbol(id, yyline, yycolumn, new CalcItem(yytext())); 
   }
%}

SPACES=[\ \t]+

ALPHANUM=[a-zA-Z0-9\_]
SIGNED=[+|-]?[0-9]+
EXP=[eE]{SIGNED}
NUMBER={SIGNED}([\.]?[0-9]+)?({EXP})?

VARBASE = [a-zA-Z]{ALPHANUM}*
VAR = {VARBASE}(\.{VARBASE})*
LOCAL = \${VAR}
GLOBAL=\{{VAR}\}

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
  {GLOBAL}      { return varGlobal(GLOBAL); }
  {LOCAL}       { return varLocal(LOCAL);   }
  \n            { return symbol(ENDS);      }
      
