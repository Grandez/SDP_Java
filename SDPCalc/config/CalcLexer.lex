package com.jgg.sdp.calculator;

import java.math.BigDecimal;

import java_cup.runtime.Symbol;

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

%eofval{
   return new Symbol(EOF, yyline, yycolumn, "EOF");
%eofval}


%{

   private Symbol symbol(int id) {
//       System.out.println("Devuelve " + yytext()); 
       return new Symbol(id); 
   }
   
   private Symbol decimal(int id) {
//      System.out.println("Devuelve " + yytext());
      return new Symbol(id, yyline, yycolumn, new BigDecimal(yytext())); 
   }
%}


SPACES=[\ \t]+

ALPHA=[a-zA-Z]+
ALPHANUM=[a-zA-Z0-9]
SIGNED=[+|-]?[0-9]+
EXP=[eE]{SIGNED}
NUMBER={SIGNED}([\.]?[0-9]+)?({EXP})?

VAR = {ALPHANUM}+


%% 

  AND           { return symbol(AND); }
  OR            { return symbol(OR);  }
  
  "&&"          { return symbol(AND); }
  "||"          { return symbol(OR);  }  
  
  
  ">="          { return symbol(GE); }
  "<="          { return symbol(LE); }  
  "!="          { return symbol(NE); }

  "="           { return symbol(EQ); }
  ">"           { return symbol(GT); }
  "<"           { return symbol(LT); }  

  "("           { return symbol(LPAR);  }
  ")"           { return symbol(RPAR);  }  
  "**"          { return symbol(POW);   }
  "+"           { return symbol(PLUS);  }
  "-"           { return symbol(MINUS); }
  "*"           { return symbol(MULT);  }
  "/"           { return symbol(DIV);   }
  "%"           { return symbol(MOD);   }
  ";"           { return symbol(ENDS);  }

  ABS           { return symbol(ABS);     }
  LOG           { return symbol(LOG);     }
  {NUMBER}      { return decimal(NUMBER); }
  {SPACES}      { /* nada */              }
  \n            { return symbol(ENDS);    }
        
