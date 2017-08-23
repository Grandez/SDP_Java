package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.ZCCSym.*;
import static com.jgg.sdp.parser.lang.ZCZSym.*;

%%

%public
%class      ZCCLexer
%extends    GenericLexer
%scanerror  ParseException

%line
%column
%char
%full
%ignorecase
%cup

%xstate ENDLINE , EATLINE 
%xstate STEXEC , EMBEDDED 
%xstate CICSSYM
%xstate ST_FUNCTION

%xstate COMMENT        
%xstate QUOTE_STRING   
%xstate DQUOTE_STRING 

%xstate COPYS  

%{

   String cicsVerb = null;
   
   int    lastSymbol = -1;
   int    prevSymbol = -1;

   Symbol begExec = null;
   
   public Symbol literal(boolean clean) { 
       String txt = cadena.toString();
       if (clean) cadena.setLength(0);
       return literal(txt); 
   }
   
   public Symbol literal(String txt) {     
      setLastSymbol(LITERAL);
      print("Devuelve LITERAL - " + txt);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, txt);
      Symbol x = symbolFactory.newSymbol(txt, LITERAL, s);

      // Espacio es el primer caracter no imprimible en ASCII
      // Character.codePointAt(new char[] {'a'},0)
      
      for (int idx = 0; idx < txt.length(); idx++) {
          if (txt.charAt(idx) < ' ') {
              ruleNoPrintable(litLine, litColumn);
              break;
          } 
      }
      return x;      
   }

   public Symbol symbolDummy(int code) {
      data = true;
      lastSymbol = 0;
      return symbol(code);
   }
                  
   public Symbol symbol(int code){
      return symbol(code, yytext());
   }

   public Symbol symbol(int code, String txt) {
      return symbol(code, txt, yyline, yycolumn);
   }

   public Symbol reservedStd() {
      return symbol(ID, yytext(), yyline, yycolumn);
   }
   public Symbol reservedFuture() {
      return reservedFuture(ID);
   }
   public Symbol reservedFuture(int code) {
      return symbol(code, yytext(), yyline, yycolumn);
   }

   private void setLastSymbol(int id) {
      prevSymbol = lastSymbol;
      lastSymbol = id;
   }

%}

%init{
   initLexer(COMMENT);
%init}

%eofval{

    if (yymoreStreams()) {
        info.unit.removeMember();
        yypopStream();
    }
    else {    
        return symbolFactory.newSymbol("EOF", ZCCSym.EOF);
    }
    info.removeOffset();    
%eofval}

SPACES=[ ]+
TABS=[\t]+
BLANKS=[ \t]+

// Generico para cargar buffer
WORD=[a-zA-Z0-9\_\-]+
ENDVERB=END-[a-zA-Z]+

ALPHA=[a-zA-Z]+

NUMERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,][0-9]+
DECIMAL2=[\.\,][0-9]+
HEXVALUE=[xX][\'\"][0-9aAbBcCdDeEfF]+[\'\"]

ALPHANUM=[a-zA-Z0-9]
ID = {ALPHANUM}({ALPHANUM}|\-|\_)*
SP=[ ]{1}
PARAGRAPH  = {SP}{ID} 

SIZE=\({BLANKS}*{NUMERO}[kKmM]?{BLANKS}*\)
PICLEN=[sS\+\-]?[aAxXzZ9]?{SIZE}

SDPD=DESC  | DESCRIPTION
SDPDESC=[>]?[\ \t]+SDP[\ \t]+{SDPD}
SDPEND=[>]?[\ \t]+SDP[\ \t]+END
SDPMASTER=[>]?[\ \t]+SDP[\ \t]+MASTER

%% 

/******************************************************************************/
/******************************************************************************/
/***   INICIO SOLO CONTROLA ID DIVISION                                     ***/
/******************************************************************************/
/******************************************************************************/

  ^[\*]         { commentInit(yytext(), yyline);  }
  ^[\/]         { commentInit(yytext(), yyline);  }  
  ^[dD]         { commentInit(yytext(), yyline);  }
  ^\-           { /* JGG, Pendiente de revisar */ }    

  ^[ \t]+EJECT[ ]*[\.]? { rulePrintDirective("EJECT", yyline); }
  ^[ \t]+SKIP[1-9]?     { rulePrintDirective("SKIP" , yyline); }  
  
  ^[ ]+EXECUTE { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }
  ^[ ]+EXEC    { begExec = symbolDummy(ZCZSym.EXEC); pushState(STEXEC);    }

  \'           { pushState(QUOTE_STRING);  }  
  \"           { pushState(DQUOTE_STRING); }

  PROCEDURE{BLANKS}DIVISION       { return symbol(PROC_DIV); }

  // Capturar COPY en una linea
  COPY              { initEmbedded(); 
                      pushState(COPYS);   
                      return symbol(ZCZSym.COPY); 
                    }

  EXIT[ \t]+PERFORM { return symbol(EXIT_PERFORM);     }
  EXIT[ \t]+PROGRAM { return symbol(EXIT_PGM);   }

  STOP[ ]+RUN       { return symbol(STOPRUN);    }  
  
  NEXT[ ]+SENTENCE  { return symbol(NEXT);       }
  ADDRESS[ ]+OF     { data = true; }   

  DFHRESP           { cicsVerb = "DFHRESP";  pushState(CICSSYM);   }  
  DFHVALUE          { cicsVerb = "DFHVALUE"; pushState(CICSSYM);   }

  DELIMITED[ ]+BY   { return symbol(DELIMITED);  }
  
  FUNCTION         { pushState(ST_FUNCTION); return symbol(FUNCTION); }
  SIZE[ ]+ERROR    { return symbol(SIZE_ERROR); }
        
  /* PALABRAS RESERVADAS */

  ACCEPT                      { return symbol(ACCEPT                  ); } 
  ACCESS                      { return symbol(ACCESS                  ); } 
  ADD                         { return symbol(ADD                     ); } 
  ADDRESS                     { return symbol(ADDRESS                 ); } 
  ADVANCING                   { return symbol(ADVANCING               ); } 
  AFTER                       { return symbol(AFTER                   ); } 
  ALL                         { return symbol(ALL                     ); } 
  ALPHABET                    { return symbol(ALPHABET                ); } 
  ALPHABETIC                  { return symbol(ALPHABETIC              ); } 
  ALPHABETIC-LOWER            { return symbol(ALPHABETIC_LOWER        ); } 
  ALPHABETIC-UPPER            { return symbol(ALPHABETIC_UPPER        ); } 
  ALPHANUMERIC                { return symbol(ALPHANUMERIC            ); } 
  ALPHANUMERIC-EDITED         { return symbol(ALPHANUMERIC_EDITED     ); } 
  ALSO                        { return symbol(ALSO                    ); } 
  ALTER                       { return symbol(ALTER                   ); } 
  ALTERNATE                   { return symbol(ALTERNATE               ); } 
  AND                         { return symbol(AND                     ); } 
  ANY                         { return symbol(ANY                     ); } 
  APPLY                       { return symbol(APPLY                   ); } 
  ARE                         { return symbol(ARE                     ); } 
  AREA                        { return symbol(AREA                    ); } 
  AREAS                       { return symbol(AREAS                   ); } 
  ASCENDING                   { return symbol(ASCENDING               ); } 
  ASSIGN                      { return symbol(ASSIGN                  ); } 
  AT                          { return symbol(AT                      ); } 
  AUTHOR                      { return symbol(AUTHOR                  ); } 
  BASIS                       { return symbol(BASIS                   ); } 
  BEFORE                      { return symbol(BEFORE                  ); } 
  BEGINNING                   { return symbol(BEGINNING               ); } 
  BINARY                      { return symbol(BINARY                  ); } 
  BLANK                       { return symbol(BLANK                   ); } 
  BLOCK                       { return symbol(BLOCK                   ); } 
  BOTTOM                      { return symbol(BOTTOM                  ); } 
  BY                          { return symbol(BY                      ); } 
  CALL                        { return symbol(CALL                    ); } 
  CANCEL                      { return symbol(CANCEL                  ); } 
  CBL                         { return symbol(CBL                     ); } 
  CHARACTER                   { return symbol(CHARACTER               ); } 
  CHARACTERS                  { return symbol(CHARACTERS              ); } 
  CLASS                       { return symbol(CLASS                   ); } 
  CLASS-ID                    { return symbol(CLASS-ID                ); } 
  CLOSE                       { return symbol(CLOSE                   ); } 
  COBOL                       { return symbol(COBOL                   ); } 
  CODE                        { return symbol(CODE                    ); } 
  CODE-SET                    { return symbol(CODE-SET                ); } 
  COLLATING                   { return symbol(COLLATING               ); } 
  COM-REG                     { return symbol(COM_REG                 ); } 
  COMMA                       { return symbol(COMMA                   ); } 
  COMMON                      { return symbol(COMMON                  ); } 
  COMP                        { return symbol(COMP                    ); } 
  COMP-1                      { return symbol(COMP1                   ); } 
  COMP-2                      { return symbol(COMP2                   ); } 
  COMP-3                      { return symbol(COMP3                   ); } 
  COMP-4                      { return symbol(COMP4                   ); } 
  COMP-5                      { return symbol(COMP5                   ); } 
  COMPUTATIONAL               { return symbol(COMP                    ); } 
  COMPUTATIONAL-1             { return symbol(COMP1                   ); } 
  COMPUTATIONAL-2             { return symbol(COMP2                   ); } 
  COMPUTATIONAL-3             { return symbol(COMP3                   ); } 
  COMPUTATIONAL-4             { return symbol(COMP4                   ); } 
  COMPUTATIONAL-5             { return symbol(COMP5                   ); } 
  COMPUTE                     { return symbol(COMPUTE                 ); } 
  CONFIGURATION               { return symbol(CONFIGURATION           ); } 
  CONTAINS                    { return symbol(CONTAINS                ); } 
  CONTENT                     { return symbol(CONTENT                 ); } 
  CONTINUE                    { return symbol(CONTINUE                ); } 
  CONVERTING                  { return symbol(CONVERTING              ); } 
  CORR                        { return symbol(CORR                    ); } 
  CORRESPONDING               { return symbol(CORRESPONDING           ); } 
  COUNT                       { return symbol(COUNT                   ); } 
  CURRENCY                    { return symbol(CURRENCY                ); } 
  DATA                        { return symbol(DATA                    ); } 
  DATE                        { return symbol(DATE                    ); } 
  DATE-COMPILED               { return symbol(DATE_COMPILED           ); } 
  DATE-WRITTEN                { return symbol(DATE_WRITTEN            ); } 
  DAY                         { return symbol(DAY                     ); } 
  DAY-OF-WEEK                 { return symbol(DAY_OF_WEEK             ); } 
  DBCS                        { return symbol(DBCS                    ); } 
  DEBUG-CONTENTS              { return symbol(DEBUG_CONTENTS          ); } 
  DEBUG-ITEM                  { return symbol(DEBUG_ITEM              ); } 
  DEBUG-LINE                  { return symbol(DEBUG_LINE              ); } 
  DEBUG-NAME                  { return symbol(DEBUG_NAME              ); } 
  DEBUG-SUB-[1-3]             { return symbol(DEBUG_SUB               ); } 
  DEBUGGING                   { return symbol(DEBUGGING               ); } 
  DECIMAL-POINT               { return symbol(DECIMAL_POINT           ); } 
  DECLARATIVES                { return symbol(DECLARATIVES            ); } 
  DELETE                      { return symbol(DELETE                  ); } 
  DELIMITED                   { return symbol(DELIMITED               ); } 
  DELIMITER                   { return symbol(DELIMITER               ); } 
  DEPENDING                   { return symbol(DEPENDING               ); } 
  DESCENDING                  { return symbol(DESCENDING              ); } 
  DISPLAY                     { return symbol(DISPLAY                 ); } 
  DISPLAY-1                   { return symbol(DISPLAY_1               ); } 
  DIVIDE                      { return symbol(DIVIDE                  ); } 
  DIVISION                    { return symbol(DIVISION                ); } 
  DOWN                        { return symbol(DOWN                    ); } 
  DUPLICATES                  { return symbol(DUPLICATES              ); } 
  DYNAMIC                     { return symbol(DYNAMIC                 ); } 
  EGCS                        { return symbol(EGCS                    ); } 
  ELSE                        { return symbol(ELSE                    ); } 
  END                         { return symbol(END                     ); } 
  END-ADD                     { return symbol(END_VERB                ); } 
  END-CALL                    { return symbol(END_VERB                ); } 
  END-COMPUTE                 { return symbol(END_VERB             ); } 
  END-DELETE                  { return symbol(END_VERB              ); } 
  END-DIVIDE                  { return symbol(END_VERB              ); } 
  END-EVALUATE                { return symbol(END_EVALUATE            ); } 
  END-EXEC                    { return symbol(END_EXEC                ); } 
  END-IF                      { return symbol(END_IF                  ); } 
  END-INVOKE                  { return symbol(END_VERB              ); } 
  END-MULTIPLY                { return symbol(END_VERB            ); } 
  END-OF-PAGE                 { return symbol(END_OF_PAGE             ); } 
  END-PERFORM                 { return symbol(END_PERFORM             ); } 
  END-READ                    { return symbol(END_VERB                ); } 
  END-RETURN                  { return symbol(END_VERB             ); } 
  END-REWRITE                 { return symbol(END_VERB             ); } 
  END-SEARCH                  { return symbol(END_SEARCH              ); } 
  END-START                   { return symbol(END_VERB               ); } 
  END-STRING                  { return symbol(END_VERB              ); } 
  END-SUBTRACT                { return symbol(END_VERB            ); } 
  END-UNSTRING                { return symbol(END_VERB            ); } 
  END-WRITE                   { return symbol(END_VERB               ); } 
  END-XML                     { return symbol(END_VERB                ); } 
  ENDING                      { return symbol(ENDING                  ); } 
  ENTER                       { return symbol(ENTER                   ); } 
  ENTRY                       { return symbol(ENTRY                   ); } 
  ENVIRONMENT                 { return symbol(ENVIRONMENT             ); } 
  EOP                         { return symbol(EOP                     ); } 
  EQUAL                       { return symbol(EQUAL                   ); } 
  ERROR                       { return symbol(ERROR                   ); } 
  EVALUATE                    { return symbol(EVALUATE                ); } 
  EVERY                       { return symbol(EVERY                   ); } 
  EXCEPTION                   { return symbol(EXCEPTION               ); } 
//EXEC                        { return symbol(EXEC                    ); } 
  EXECUTE                     { return symbol(EXECUTE                 ); } 
  EXIT                        { return symbol(EXIT                    ); } 
  EXTEND                      { return symbol(EXTEND                  ); } 
  EXTERNAL                    { return symbol(EXTERNAL                ); } 
  FACTORY                     { return symbol(FACTORY                 ); } 
  FALSE                       { return symbol(FALSE                   ); } 
//  FD                          { return symbol(FD                      ); } 
  FILE                        { return symbol(FILE                    ); } 
//  FILE-CONTROL                { return symbol(FILE_CONTROL            ); } 
  FILLER                      { return symbol(FILLER                  ); } 
  FIRST                       { return symbol(FIRST                   ); } 
  FOOTING                     { return symbol(FOOTING                 ); } 
  FOR                         { return symbol(FOR                     ); } 
  FROM                        { return symbol(FROM                    ); }
//  See FUNCTION
//  FUNCTION                    { return symbol(FUNCTION                ); } 
  FUNCTION-POINTER            { return symbol(FUNCTION_POINTER        ); } 
  GENERATE                    { return symbol(GENERATE                ); } 
  GIVING                      { return symbol(GIVING                  ); } 
  GLOBAL                      { return symbol(GLOBAL                  ); } 
  GO                          { return symbol(GOTO                    ); } 
  GOBACK                      { return symbol(GOBACK                  ); } 
  GREATER                     { return symbol(GREATER                 ); } 
  GROUP-USAGE                 { return symbol(GROUP_USAGE             ); } 
  HIGH-VALUE[Ss]?             { return symbol(HIGHVAL                 ); } 
  I-O                         { return symbol(IO                      ); } 
//  I-O-CONTROL                 { return symbol(IO_CONTROL              ); } 
  ID                          { return symbol(ID                      ); } 
  IDENTIFICATION              { return symbol(IDENTIFICATION          ); } 
  IF                          { return symbol(IF                      ); } 
  IN                          { return symbol(IN                      ); } 
  INDEX                       { return symbol(INDEX                   ); } 
  INDEXED                     { return symbol(INDEXED                 ); } 
  INHERITS                    { return symbol(INHERITS                ); } 
  INITIAL                     { return symbol(INITIAL                 ); } 
  INITIALIZE                  { return symbol(INITIALIZE              ); } 
  INPUT                       { return symbol(INPUT                   ); } 
  INPUT-OUTPUT                { return symbol(INPUT-OUTPUT            ); } 
  INSERT                      { return symbol(INSERT                  ); } 
  INSPECT                     { return symbol(INSPECT                 ); } 
  INSTALLATION                { return symbol(INSTALLATION            ); } 
  INTO                        { return symbol(INTO                    ); } 
  INVALID                     { return symbol(INVALID                 ); } 
  INVOKE                      { return symbol(INVOKE                  ); } 
  IS                          { data = true; } 
  JNIENVPTR                   { return symbol(JNIENVPTR               ); } 
  JUST                        { return symbol(JUST                    ); } 
  JUSTIFIED                   { return symbol(JUSTIFIED               ); } 
  KANJI                       { return symbol(KANJI                   ); } 
  KEY                         { return symbol(KEY                     ); } 
  LABEL                       { return symbol(LABEL                   ); } 
  LEADING                     { return symbol(LEADING                 ); } 
  LEFT                        { return symbol(LEFT                    ); } 
  LENGTH                      { return symbol(LENGTH                  ); } 
  LESS                        { return symbol(LESS                    ); } 
  LINAGE                      { return symbol(LINAGE                  ); } 
  LINAGE-COUNTER              { return symbol(LINAGE_COUNTER          ); } 
  LINE                        { return symbol(LINE                    ); } 
  LINES                       { return symbol(LINES                   ); } 
  LINKAGE                     { return symbol(LINKAGE                 ); } 
  LOCAL-STORAGE               { return symbol(LOCAL_STORAGE           ); } 
  LOCK                        { return symbol(LOCK                    ); } 
  LOW-VALUE[Ss]?              { return symbol(LOWVAL); } 
  MEMORY                      { return symbol(MEMORY                  ); } 
  MERGE                       { return symbol(MERGE                   ); } 
  METHOD                      { return symbol(METHOD                  ); } 
  METHOD-ID                   { return symbol(METHOD-ID               ); } 
  MODE                        { return symbol(MODE                    ); } 
  MODULES                     { return symbol(MODULES                 ); } 
  MORE-LABELS                 { return symbol(MORE_LABELS             ); } 
  MOVE                        { return symbol(MOVE                    ); } 
  MULTIPLE                    { return symbol(MULTIPLE                ); } 
  MULTIPLY                    { return symbol(MULTIPLY                ); } 
  NATIONAL                    { return symbol(NATIONAL                ); } 
  NATIONAL-EDITED             { return symbol(NATIONAL                ); } 
  NATIVE                      { return symbol(NATIVE                  ); } 
  NEGATIVE                    { return symbol(NEGATIVE                ); } 
  NEXT                        { return symbol(NEXT                    ); } 
  NO                          { return symbol(NO                      ); } 
  NOT                         { return symbol(NOT                     ); } 
  NULL                        { return symbol(NULL                    ); } 
  NULLS                       { return symbol(NULLS                   ); } 
  NUMERIC                     { return symbol(NUMERIC                 ); } 
  NUMERIC-EDITED              { return symbol(NUMERIC_EDITED          ); } 
  OBJECT                      { return symbol(OBJECT                  ); } 
  OBJECT-COMPUTER             { return symbol(OBJECT_COMPUTER         ); } 
  OCCURS                      { return symbol(OCCURS                  ); } 
  OF                          { return symbol(OF                      ); } 
  OFF                         { return symbol(OFF                     ); } 
  OMITTED                     { return symbol(OMITTED                 ); } 
  ON                          { return symbol(ON                      ); } 
  OPEN                        { return symbol(OPEN                    ); } 
  OPTIONAL                    { return symbol(OPTIONAL                ); } 
  OR                          { return symbol(OR                      ); } 
  ORDER                       { return symbol(ORDER                   ); } 
  ORGANIZATION                { return symbol(ORGANIZATION            ); } 
  OTHER                       { return symbol(OTHER                   ); } 
  OUTPUT                      { return symbol(OUTPUT                  ); } 
  OVERFLOW                    { return symbol(OVERFLOW                ); } 
  OVERRIDE                    { return symbol(OVERRIDE                ); } 
  PACKED-DECIMAL              { return symbol(PACKED_DECIMAL          ); } 
  PADDING                     { return symbol(PADDING                 ); } 
  PAGE                        { return symbol(PAGE                    ); } 
  PASSWORD                    { return symbol(PASSWORD                ); } 
  PERFORM                     { return symbol(PERFORM                 ); } 
  PIC                         { return symbol(PIC                     ); } 
  PICTURE                     { return symbol(PICTURE                 ); } 
  POINTER                     { return symbol(POINTER                 ); } 
  POSITION                    { return symbol(POSITION                ); } 
  POSITIVE                    { return symbol(POSITIVE                ); } 
  PROCEDURE                   { return symbol(PROCEDURE               ); } 
  PROCEDURE-POINTER           { return symbol(PROCEDURE_POINTER       ); } 
  PROCEDURES                  { return symbol(PROCEDURES              ); } 
  PROCEED                     { return symbol(PROCEED                 ); } 
  PROCESSING                  { return symbol(PROCESSING              ); } 
  PROGRAM                     { return symbol(PROGRAM                 ); } 
  PROGRAM-ID                  { return symbol(PROGRAM_ID              ); } 
  QUOTE                       { return symbol(QUOTE                   ); } 
  QUOTES                      { return symbol(QUOTES                  ); } 
  RANDOM                      { return symbol(RANDOM                  ); } 
  READ                        { return symbol(READ                    ); } 
  READY                       { return symbol(READY                   ); } 
  RECORD                      { return symbol(RECORD                  ); } 
  RECORDING                   { return symbol(RECORDING               ); } 
  RECORDS                     { return symbol(RECORDS                 ); } 
  RECURSIVE                   { return symbol(RECURSIVE               ); } 
  REDEFINES                   { return symbol(REDEFINES               ); } 
  REEL                        { return symbol(REEL                    ); } 
  REFERENCE                   { return symbol(REFERENCE               ); } 
  REFERENCES                  { return symbol(REFERENCES              ); } 
  RELATIVE                    { return symbol(RELATIVE                ); } 
  RELEASE                     { return symbol(RELEASE                 ); } 
  RELOAD                      { return symbol(RELOAD                  ); } 
  REMAINDER                   { return symbol(REMAINDER               ); } 
  REMOVAL                     { return symbol(REMOVAL                 ); } 
  RENAMES                     { return symbol(RENAMES                 ); } 
  REPLACE                     { return symbol(REPLACE                 ); } 
  REPLACING                   { return symbol(REPLACING               ); } 
  REPOSITORY                  { return symbol(REPOSITORY              ); } 
  RERUN                       { return symbol(RERUN                   ); } 
  RESERVE                     { return symbol(RESERVE                 ); } 
  RESET                       { return symbol(RESET                   ); } 
  RETURN                      { return symbol(RETURN                  ); } 
  RETURN-CODE                 { return symbol(RETURN-CODE             ); } 
  RETURNING                   { return symbol(RETURNING               ); } 
  REVERSED                    { return symbol(REVERSED                ); } 
  REWIND                      { return symbol(REWIND                  ); } 
  REWRITE                     { return symbol(REWRITE                 ); } 
  RIGHT                       { return symbol(RIGHT                   ); } 
  ROUNDED                     { return symbol(ROUNDED                 ); } 
  RUN                         { return symbol(RUN                     ); } 
  SAME                        { return symbol(SAME                    ); } 
  SD                          { return symbol(SD                      ); } 
  SEARCH                      { return symbol(SEARCH                  ); } 
  SECTION                     { return symbol(SECTION                 ); } 
  SECURITY                    { return symbol(SECURITY                ); } 
  SEGMENT-LIMIT               { return symbol(SEGMENT_LIMIT           ); } 
  SELECT                      { return symbol(SELECT                  ); } 
  SELF                        { return symbol(SELF                    ); } 
  SENTENCE                    { return symbol(SENTENCE                ); } 
  SEPARATE                    { return symbol(SEPARATE                ); } 
  SEQUENCE                    { return symbol(SEQUENCE                ); } 
  SEQUENTIAL                  { return symbol(SEQUENTIAL              ); } 
  SERVICE                     { return symbol(SERVICE                 ); } 
  SET                         { return symbol(SET                     ); } 
  SHIFT-IN                    { return symbol(SHIFT_IN                ); } 
  SHIFT-OUT                   { return symbol(SHIFT_OUT               ); } 
  SIGN                        { return symbol(SIGN                    ); } 
  SIZE                        { return symbol(SIZE                    ); } 
  SORT                        { return symbol(SORT                    ); } 
  SORT-CONTROL                { return symbol(SORT_CONTROL            ); } 
  SORT-CORE-SIZE              { return symbol(SORT_CORE_SIZE          ); } 
  SORT-FILE-SIZE              { return symbol(SORT_FILE_SIZE          ); } 
  SORT-MERGE                  { return symbol(SORT_MERGE              ); } 
  SORT-MESSAGE                { return symbol(SORT_MESSAGE            ); } 
  SORT-MODE-SIZE              { return symbol(SORT_MODE_SIZE          ); } 
  SORT-RETURN                 { return symbol(SORT_RETURN             ); } 
  SOURCE-COMPUTER             { return symbol(SOURCE_COMPUTER         ); } 
  SPACE                       { return symbol(SPACES                  ); } 
  SPACES                      { return symbol(SPACES                  ); } 
//  SPECIAL-NAMES               { return symbol(SPECIAL_NAMES           ); } 
//  SQL                         { return symbol(SQL                     ); } 
  STANDARD-[1-2]              { return symbol(STANDARD                ); }
  STANDARD                    { return symbol(STANDARD                ); } 
  START                       { return symbol(START                   ); } 
  STATUS                      { return symbol(STATUS                  ); } 
  STOP                        { return symbol(STOP                    ); } 
  STRING                      { return symbol(STRING                  ); } 
  SUBTRACT                    { return symbol(SUBTRACT                ); } 
  SUPER                       { return symbol(SUPER                   ); } 
  SUPPRESS                    { return symbol(SUPPRESS                ); } 
  SYMBOLIC                    { return symbol(SYMBOLIC                ); } 
  SYNC                        { return symbol(SYNC                    ); } 
  SYNCHRONIZED                { return symbol(SYNCHRONIZED            ); } 
  TALLY                       { return symbol(TALLY                   ); } 
  TALLYING                    { return symbol(TALLYING                ); } 
  TAPE                        { return symbol(TAPE                    ); } 
  TEST                        { return symbol(TEST                    ); } 
  THAN                        { data = true; } 
  THEN                        { data = true; } 
  THROUGH                     { return symbol(THROUGH                 ); } 
  THRU                        { return symbol(THRU                    ); } 
  TIME                        { return symbol(TIME                    ); } 
  TIMES                       { return symbol(TIMES                   ); } 
  TITLE                       { return symbol(TITLE                   ); } 
  TO                          { return symbol(TO                      ); } 
  TOP                         { return symbol(TOP                     ); } 
  TRACE                       { return symbol(TRACE                   ); } 
  TRAILING                    { return symbol(TRAILING                ); } 
  TRUE                        { return symbol(TRUE                    ); } 
  TYPE                        { return symbol(TYPE                    ); } 
  UNIT                        { return symbol(UNIT                    ); } 
  UNSTRING                    { return symbol(UNSTRING                ); } 
  UNTIL                       { return symbol(UNTIL                   ); } 
  UP                          { return symbol(UP                      ); } 
  UPON                        { return symbol(UPON                    ); } 
  USAGE                       { return symbol(USAGE                   ); } 
  USE                         { return symbol(USE                     ); } 
  USING                       { return symbol(USING                   ); } 
  VALUE                       { return symbol(VALUE                   ); } 
  VALUES                      { return symbol(VALUES                  ); } 
  VARYING                     { return symbol(VARYING                 ); } 
  WHEN                        { return symbol(WHEN                    ); } 
  WHEN-COMPILED               { return symbol(WHEN_COMPILED           ); } 
  WITH                        { return symbol(WITH                    ); } 
  WORDS                       { return symbol(WORDS                   ); } 
  WORKING-STORAGE             { return symbol(WORKING_STORAGE         ); } 
  WRITE                       { return symbol(WRITE                   ); } 
  WRITE-ONLY                  { return symbol(WRITE_ONLY              ); } 
  XML                         { return symbol(XML                     ); } 
  XML-CODE                    { return symbol(XML_CODE                ); } 
  XML-EVENT                   { return symbol(XML_EVENT               ); } 
  XML-NAMESPACE               { return symbol(XML_NAMESPACE           ); } 
  XML-NAMESPACE-PREFIX        { return symbol(XML_NAMESPACE_PREFIX    ); } 
  XML-NNAMESPACE              { return symbol(XML_NNAMESPACE          ); } 
  XML-NNAMESPACE-PREFIX       { return symbol(XML_NNAMESPACE_PREFIX   ); } 
  XML-NTEXT                   { return symbol(XML_NTEXT               ); } 
  XML-SCHEMA                  { return symbol(XML_SCHEMA              ); } 
  XML-TEXT                    { return symbol(XML_TEXT                ); } 
  ZEROES                      { return symbol(ZERO                    ); } 
  ZEROS                       { return symbol(ZERO                    ); } 
  ZERO                        { return symbol(ZERO                    ); }
  
  /* RESERVED WORDS IN ISO STANDARD  BUT NO IN ZCOBOL */
  
  CD                          { return reservedStd(); }
  CF                          { return reservedStd(); }
  CH                          { return reservedStd(); }
  CLOCK-UNITS                 { return reservedStd(); }
  COLUMN                      { return reservedStd(); }
  COMMUNICATION               { return reservedStd(); }
  CONTROL                     { return reservedStd(); }
  CONTROLS                    { return reservedStd(); }
  DE                          { return reservedStd(); }
  DESTINATION                 { return reservedStd(); }
  DETAIL                      { return reservedStd(); }
  DISABLE                     { return reservedStd(); }
  EGI                         { return reservedStd(); }
  EMI                         { return reservedStd(); }
  ENABLE                      { return reservedStd(); }
  END-RECEIVE                 { return reservedStd(); }
  ESI                         { return reservedStd(); }
  FINAL                       { return reservedStd(); }
  GROUP                       { return reservedStd(); }
  HEADING                     { return reservedStd(); }
  INDICATE                    { return reservedStd(); }
  INITIATE                    { return reservedStd(); }
  LAST                        { return reservedStd(); }
  LIMIT                       { return reservedStd(); }
  LIMITS                      { return reservedStd(); }
  LINE-COUNTER                { return reservedStd(); }
  MESSAGE                     { return reservedStd(); }
  NUMBER                      { return reservedStd(); }
  PAGE-COUNTER                { return reservedStd(); }
  PF                          { return reservedStd(); }
  PH                          { return reservedStd(); }
  PLUS                        { return reservedStd(); }
  PRINTING                    { return reservedStd(); }
  PURGE                       { return reservedStd(); }
  QUEUE                       { return reservedStd(); }
  RD                          { return reservedStd(); }
  RECEIVE                     { return reservedStd(); }
  REPORT                      { return reservedStd(); }
  REPORTING                   { return reservedStd(); }
  REPORTS                     { return reservedStd(); }
  RF                          { return reservedStd(); }
  RH                          { return reservedStd(); }
  SEGMENT                     { return reservedStd(); }
  SEND                        { return reservedStd(); }
  SOURCE                      { return reservedStd(); }
  SUB-QUEUE-1                 { return reservedStd(); }
  SUB-QUEUE-2                 { return reservedStd(); }
  SUB-QUEUE-3                 { return reservedStd(); }
  SUM                         { return reservedStd(); }
  TABLE                       { return reservedStd(); }
  TERMINAL                    { return reservedStd(); }
  TERMINATE                   { return reservedStd(); }
  TEXT                        { return reservedStd(); }

  /* POSSIBLE RESERVED WORDS */
  
  ACTIVE-CLASS                { return reservedFuture(); }            
  ALIGNED                     { return reservedFuture(); }
  ALLOCATE                    { return reservedFuture(); }
  ANYCASE                     { return reservedFuture(); }
  B-AND                       { return reservedFuture(); }
  B-NOT                       { return reservedFuture(); }
  B-OR                        { return reservedFuture(); }
  B-XOR                       { return reservedFuture(); }
  BASED                       { return reservedFuture(); }
  BINARY-CHAR                 { return reservedFuture(); }
  BINARY-DOUBLE               { return reservedFuture(); }
  BINARY-LONG                 { return reservedFuture(); }
  BINARY-SHORT                { return reservedFuture(); }
  BIT                         { return reservedFuture(); }
  BOOLEAN                     { return reservedFuture(); }
  COL                         { return reservedFuture(); }
  COLS                        { return reservedFuture(); }
  COLUMNS                     { return reservedFuture(); }
  CONDITION                   { return reservedFuture(); }
  CONSTANT                    { return reservedFuture(); }
  CRT                         { return reservedFuture(); }
  CURSOR                      { return reservedFuture(); }
  DATA-POINTER                { return reservedFuture(); }
  DEFAULT                     { return reservedFuture(); }
  EC                          { return reservedFuture(); }
  END-ACCEPT                  { return reservedFuture(); }
  END-DISPLAY                 { return reservedFuture(); }
  EO                          { return reservedFuture(); }
  EXCEPTION-OBJECT            { return reservedFuture(); }
  FLOAT-EXTENDED              { return reservedFuture(); }
  FLOAT-LONG                  { return reservedFuture(); }
  FLOAT-SHORT                 { return reservedFuture(); }
  FORMAT                      { return reservedFuture(); }
  FREE                        { return reservedFuture(); }
  FUNCTION-ID                 { return reservedFuture(); }
  GET                         { return reservedFuture(); }
  INTERFACE                   { return reservedFuture(); }
  INTERFACE-ID                { return reservedFuture(); }
  LOCALE                      { return reservedFuture(); }
  MINUS                       { return reservedFuture(); }
  NESTED                      { return reservedFuture(); }
  OBJECT-REFERENCE            { return reservedFuture(); }
  OPTIONS                     { return reservedFuture(); }
  PRESENT                     { return reservedFuture(); }
  PROGRAM-POINTER             { return reservedFuture(); }
  PROPERTY                    { return reservedFuture(); }
  PROTOTYPE                   { return reservedFuture(); }
  RAISE                       { return reservedFuture(); }
  RAISING                     { return reservedFuture(); }
  RESUME                      { return reservedFuture(); }
  RETRY                       { return reservedFuture(); }
  SCREEN                      { return reservedFuture(); }
  SHARING                     { return reservedFuture(); }
  SOURCES                     { return reservedFuture(); }
  SYSTEM-DEFAULT              { return reservedFuture(); }
  TYPEDEF                     { return reservedFuture(); }
  UNIVERSAL                   { return reservedFuture(); }
  UNLOCK                      { return reservedFuture(); }
  USER-DEFAULT                { return reservedFuture(); }
  VAL-STATUS                  { return reservedFuture(); }
  VALID                       { return reservedFuture(); }
  VALIDATE                    { return reservedFuture(); }
  VALIDATE-STATUS             { return reservedFuture(); }
  
  
/*******************************************************/  
/* Simbolos y operadores                               */
/*******************************************************/

 "("               { return symbol(ZCCSym.LPAR);  }
 ")"               { return symbol(ZCCSym.RPAR);  }

 ">="              { return symbol(REL_GE); }
 "<="              { return symbol(REL_LE); }
 "**"              { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_POW); }
 "*"               { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_MUL); }
 "/"               { if (yycolumn < 4) commentInit(yytext(), yyline); else return symbol(OP_DIV); }
 "+"               { return symbol(OP_ADD); }
 "-"               { return symbol(OP_SUB); }
 ">"               { return symbol(REL_GT); }
 "<"               { return symbol(REL_LT); }
 "="               { return symbol(REL_EQ); }
 ","               { return symbol(ZCCSym.COMMA); }
 "."               { return symbol(ZCCSym.ENDP); }

 ";"               { data = true; }
 ":"               { return symbol(OP_COL); }  

  {ENDVERB}        { return symbol(END_VERB);    }
  

/*******************************************************/  
/* Patrones                                            */
/*******************************************************/

  {SPACES}      { /* nada */ }
  {TABS}        { ruleTabs(yyline, yycolumn); }

  // Capturar  al inicio de linea
  ^[ ]+COPY{BLANKS}              { initEmbedded(); 
                                   pushState(COPYS);   
                                   return symbol(ZCZSym.COPY); 
                                 }

  ^{PARAGRAPH}                   { return symbol(PARRAFO); }
  ^[ ]{1}END{BLANKS}PROGRAM      { return symbol(END_PGM); }


 {NUMERO}           { return symbol(NUMERO);  } 
 {DECIMAL}          { return symbol(NUMERO);  }
 {DECIMAL2}         { return symbol(NUMERO);  }
 {HEXVALUE}         { return symbol(NUMERO);  }
 {ID}               { if (yycolumn == 0) {
                          char c = yytext().charAt(0);
                          if (c == 'D' || c == 'd') commentInit(yytext(), yyline); 
                      }
                      else {     
                         if (yycolumn <= 4) {
                             return symbol(PARRAFO);
                         }
                         else {    
                           return symbol(SYM);
                         }
                      }   
                    }
 
  \n       { info.module.incLines(data); }
  \r       { /* do nothing */ }

  [^]               { print("JGG 1 encuentra -" + yytext() + "-"); }
  


/******************************************************************************/
/******************************************************************************/
/***                               otros                                    ***/
/******************************************************************************/
/******************************************************************************/

<ST_FUNCTION> {
  {SPACES}           { /* EAT */ }
  {TABS}             { ruleTabs(yyline, yycolumn); }
  ^[\*\/]            { commentInit(yytext(), yyline);   }
  {ID}               { popState(); return symbol(INTRINSIC);  }
  \r                 { /* EAT */ }
  \n                 { /* EAT */ }
}

/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'\'          { cadena.append(yytext());  }    
  \'            { popState();  
                  return literal(true); 
                }  
                // Tiene que haber continuacion
  \n            { popState(); } 
  \r            { /* eat */ }

  [^]           { cadena.append(yytext()); }
}


<DQUOTE_STRING> {
  \"\"          { cadena.append(yytext());  }    
  \"            { popState(); 
                  return literal(true); 
                }
                // Tiene que haber continuacion
  \n            { popState(); }
  \r            { /* eat */   }

  [^]           { cadena.append(yytext()); }
}


/* 
  Casos en los que puede haber varios puntos
  Se come hasta fin de linea y devuelve punto
*/
<ENDLINE> {
  {SPACES}      { /* Nada */  }
  {TABS}        { ruleTabs(yyline, yycolumn); }
  \n            { popState(); return symbol(ENDP); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  .             { /* comer */ }
}

// Se come hasta fin de linea

<EATLINE> {

  {SPACES}      { /* Nada */  }
  {TABS}        { ruleTabs(yyline, yycolumn); }
  \n            { popState(); }  
  \r            { /* comer */ }
  \.            { /* comer */ }
  [^]             { /* comer */ }
}

<COMMENT> {
  {BLANKS}      { ruleTabsInText(yytext(), yyline, yycolumn);
                  commentAppend(yytext()); 
                }
  \n            { commentEnd(yyline);      }                 
  [a-zA-Z0-9]+  { commentAppend(yytext()); }
  [^]           { commentAppend(yytext()); }    
}
   
<COPYS> {
  ^[\*\/]            { pushState(COMMENT);           }
  \.                 { popState(); return symbol(ENDCOPY);  }
  \r                 { info.buffer.append(yytext()); }
  \n                 { info.buffer.append(yytext()); }
  {WORD}             { info.buffer.append(yytext()); }
  .                  { info.buffer.append(yytext()); }  
}


// Transforma DFHRESP(xx) y DFHVALUE(xx) en DFHCICS

<CICSSYM> {
   ")"  { popState(); return symbol(DFHCICS, cicsVerb); }
   .    { /* do nothing */ }
   \r   { /* do nothing */ }
   \n   { info.module.incLines(data); }
   
}

<STEXEC> {
  ^[\*\/dD]       { pushState(COMMENT);       }
   CICS           { info.module.setCICS();
                    initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(CICSCODE);
                  } 
                      
   SQL            { initEmbedded();  
                    pushState(EMBEDDED);
                    return symbol(SQLCODE);
                  } 
   {SPACES}       { /* DO NOTHING */ }
   {TABS}         { ruleTabs(yyline, yycolumn); }
   \n             { info.module.incLines(data); data = false; }
   \r             { /* do nothing */ }
    
}

<EMBEDDED> {
  ^[\*\/dD]          { pushState(COMMENT);       }
  END-EXEC[ ]*[\.]?  { popState(2); return symbol(ENDEXEC); }

  \r           { /* do nothing */ }
  \n           { info.buffer.append(yytext());
                 info.module.incLines(data); 
                 data = false;  
              }
   [^]        { info.buffer.append(yytext()); }                          
}
