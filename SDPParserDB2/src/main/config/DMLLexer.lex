package com.jgg.sdp.parser.db2.lang;

import java.util.*;
import java_cup.runtime.Symbol;

import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.db2.lang.DMLSym.*;

%%

%public
%class      DMLLexer
%extends    GenericLexer

%line
%column
%char
%full
%ignorecase
%cup

%xstate QUOTE_STRING, DQUOTE_STRING
%xstate COMMENT
%xstate CHECK_FUNCTION

%{

   Stack<Integer> pars = new Stack<Integer>();
   HashSet<Integer> words = new HashSet<Integer>();

   Symbol symWord;
   Symbol symFunction;
         
   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline;
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }

   public Symbol literal(boolean clean) { 
       String txt = cadena.toString();
       if (clean) cadena.setLength(0);
       return literal(txt); 
   }

   public Symbol literal(String txt) {
      lastID = LITERAL;
      cadena.append(txt);
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL (" + LITERAL + ") - " + texto);
      Symbol s = new Symbol(LITERAL, litLine, litColumn, texto);
//      if (texto.contains("%")) {
//          return symbolFactory.newSymbol(texto, MASK, s);
//      }
      return symbolFactory.newSymbol(texto, LITERAL, s);
   }

   public Symbol symbol(int code){
      return symbol(code, yytext());
   }
   
   public Symbol symbol(int code, String txt) {
      data = true;
      lastID = code;
      print("Devuelve SYMBOL(" + code + ") - (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, code, s);
   }
   
   private void cacheSymbol(int codeWord, int codeFunction) {
      symWord     = symbol(codeWord);
      symFunction = symbol(codeFunction);
      pushState(CHECK_FUNCTION);
   } 
   
   private Symbol getCacheSymbol(boolean function) {
      popState();
//      if (function) print("DEVUELVE FUNCTION"); else print("DEVUELVE ID");
      return (function) ? symFunction : symWord;
   }   
   
/*
   public Symbol symbolic(int value) {
      data = true;
      String txt = Integer.toString(value);
      print("Devuelve SYMBOL (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(ENTERO, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, ENTERO, s);
   }
*/
%}


%init{
   initLexer();
/*
   words.add(LPAR);
   words.add(WHERE);
   words.add(WHEN);   
   words.add(AND);
   words.add(OR);
*/   
%init}

%eofval{
    return symbolFactory.newSymbol("EOF", EOF);
%eofval}


SPACES=[\ ]+
TABS=[\t]+
ID = [a-zA-Z0-9][a-zA-Z0-9\_\-\#]*

ENTERO=[0-9]+
SIGNED=[-]{1}{ENTERO}
DECNUM=[|-]?[0-9]+[\.]?[0-9]+
NUMFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
//\'[0-9A-Fa-f]+\'
NUMGRAPHIC=[UuGg][Xx]
// \'[0-9A-Fa-f]+\'

HOSTVAR1    = :{ID}
HOSTVAR2    = :[ ]+{ID}
IDQUALIFIED = \.{ID} 
FULLTABLE   = {ID}\.\*

%% 

  \xB5          { /* eat */ }

  {ENTERO}          { return symbol(NUM_ENTERO);    }
  {SIGNED}          { return symbol(NUM_SIGNED);    }
  {DECNUM}          { return symbol(NUM_DECIMAL);   }
  {NUMFLOAT}        { return symbol(NUM_FLOAT);     }

  {FULLTABLE}       { return symbol(FULLTABLE);     }
  {HOSTVAR2}        { return symbol(HOSTVAR2);      }
  {HOSTVAR1}        { return symbol(HOSTVAR1);      }
  {IDQUALIFIED}     { return symbol(IDQUALIFIED);   }

   ACCELERATION               { return symbol(ACCELERATION        ); }    
   ADD                        { return symbol(ADD                 ); }
   AFTER                      { return symbol(AFTER               ); }
   AGE                        { return symbol(AGE                 ); }   
   ALLOCATE                   { return symbol(ALLOCATE            ); }
   ALLOW                      { return symbol(ALLOW               ); }
   ALL                        { return symbol(ALL                 ); }
   ALTER                      { return symbol(ALTER               ); }
   AND                        { return symbol(AND                 ); }
   ANY                        { return symbol(ANY                 ); }
   APPLICATION                { return symbol(APPLICATION         ); }   
   AS                         { return symbol(AS                  ); }
   ASENSITIVE                 { return symbol(ASENSITIVE          ); }
   ASSOCIATE                  { return symbol(ASSOCIATE           ); }
   ASUTIME                    { return symbol(ASUTIME             ); }
   AT                         { return symbol(AT                  ); }
   AUDIT                      { return symbol(AUDIT               ); }
   AUXILIARY                  { return symbol(AUXILIARY           ); }
   AUX                        { return symbol(AUX                 ); }
   BEFORE                     { return symbol(BEFORE              ); }
   BEGIN                      { return symbol(BEGIN               ); }
   BETWEEN                    { return symbol(BETWEEN             ); }
   BUFFERPOOL                 { return symbol(BUFFERPOOL          ); }
   BY                         { return symbol(BY                  ); }
   CALL                       { return symbol(CALL                ); }
   CAPTURE                    { return symbol(CAPTURE             ); }
   CARDINALITY                { return symbol(CARDINALITY         ); }
   CASCADED                   { return symbol(CASCADED            ); }
   CASE                       { return symbol(CASE                ); }
   CAST                       { return symbol(CAST                ); }
   CCSID                      { return symbol(CCSID               ); }
   CHARACTER                  { return symbol(CHARACTER           ); }
   CHECK                      { return symbol(CHECK               ); }
   CLIENT_ACCTNG              { return symbol(CLIENT_ACCTNG       ); }   
   CLIENT_APPLNAME            { return symbol(CLIENT_APPLNAME     ); }   
   CLIENT_USERID              { return symbol(CLIENT_USERID       ); }   
   CLIENT_WRKSTNNAME          { return symbol(CLIENT_WRKSTNNAME   ); }   
   CLONE                      { return symbol(CLONE               ); }
   CLOSE                      { return symbol(CLOSE               ); }
   CLUSTER                    { return symbol(CLUSTER             ); }
   COLLECTION                 { return symbol(COLLECTION          ); }
   COLLID                     { return symbol(COLLID              ); }
   COLUMN                     { return symbol(COLUMN              ); }
   COMMENT                    { return symbol(COMMENT             ); }
   COMMIT                     { return symbol(COMMIT              ); }
   CONDITION                  { return symbol(CONDITION           ); }
   CONNECTION                 { return symbol(CONNECTION          ); }
   CONNECT                    { return symbol(CONNECT             ); }
   CONSTRAINT                 { return symbol(CONSTRAINT          ); }
   CONTENT                    { return symbol(CONTENT             ); }
   CONTINUE                   { return symbol(CONTINUE            ); }
   CREATE                     { return symbol(CREATE              ); }
   CURRENT                    { return symbol(CURRENT             ); }
   CURRENT_DATE               { return symbol(CURRENT_DATE        ); }
   CURRENT_LC_CTYPE           { return symbol(CURRENT_LC_CTYPE    ); }
   CURRENT_PATH               { return symbol(CURRENT_PATH        ); }
   CURRENT_SCHEMA             { return symbol(CURRENT_SCHEMA      ); }
   CURRENT_TIME               { return symbol(CURRENT_TIME        ); }
   CURRENT_TIMESTAMP          { return symbol(CURRENT_TIMESTAMP   ); }
   CURRVAL                    { return symbol(CURRVAL             ); }
   CURSOR                     { return symbol(CURSOR              ); }
   DATABASE                   { return symbol(DATABASE            ); }
   DATA                       { return symbol(DATA                ); }
   DAY[Ss]?                   { return symbol(DAYS                ); }
   DBINFO                     { return symbol(DBINFO              ); }
   DEBUG                      { return symbol(DEBUG               ); }   
   DECLARE                    { return symbol(DECLARE             ); }
   DEFAULT                    { return symbol(DEFAULT             ); }
   DEGREE                     { return symbol(DEGREE              ); }   
   DELETE                     { return symbol(DELETE              ); }
   DESCRIPTOR                 { return symbol(DESCRIPTOR          ); }
   DETERMINISTIC              { return symbol(DETERMINISTIC       ); }
   DISABLE                    { return symbol(DISABLE             ); }
   DISALLOW                   { return symbol(DISALLOW            ); }
   DISTINCT                   { return symbol(DISTINCT            ); }
   DOCUMENT                   { return symbol(DOCUMENT            ); }
   DO                         { return symbol(DO                  ); }
   DROP                       { return symbol(DROP                ); }
   DSSIZE                     { return symbol(DSSIZE              ); }
   DYNAMIC                    { return symbol(DYNAMIC             ); }
   EDITPROC                   { return symbol(EDITPROC            ); }
   ELIGIBLE                   { return symbol(ELIGIBLE            ); }   
   ELSE                       { return symbol(ELSE                ); }
   ELSEIF                     { return symbol(ELSEIF              ); }
   ENABLE                     { return symbol(ENABLE              ); }   
   ENCODING                   { return symbol(ENCODING            ); }
   ENCRYPTION                 { return symbol(ENCRYPTION          ); }
   ENDING                     { return symbol(ENDING              ); }
   END                        { return symbol(END                 ); }
   END_EXEC                   { return symbol(END_EXEC            ); }
   ERASE                      { return symbol(ERASE               ); }
   ESCAPE                     { return symbol(ESCAPE              ); }
   EXCEPTION                  { return symbol(EXCEPTION           ); }
   EXCEPT                     { return symbol(EXCEPT              ); }
   EXECUTE                    { return symbol(EXECUTE             ); }
   EXISTS                     { return symbol(EXISTS              ); }
   EXIT                       { return symbol(EXIT                ); }
   EXPLAIN                    { return symbol(EXPLAIN             ); }
   EXTERNAL                   { return symbol(EXTERNAL            ); }
   FAILBACK                   { return symbol(FAILBACK            ); }   
   FENCED                     { return symbol(FENCED              ); }
   FETCH                      { return symbol(FETCH               ); }
   FIELDPROC                  { return symbol(FIELDPROC           ); }
   FINAL                      { return symbol(FINAL               ); }
   FIRST                      { return symbol(FIRST               ); }
   FOR                        { return symbol(FOR                 ); }
   FREE                       { return symbol(FREE                ); }
   FROM                       { return symbol(FROM                ); }
   FULL                       { return symbol(FULL                ); }
   FUNCTION                   { return symbol(FUNCTION            ); }
   GENERATED                  { return symbol(GENERATED           ); }
   GET_ACCEL_ARCHIVE          { return symbol(GET_ACCEL_ARCHIVE   ); }
   GET                        { return symbol(GET                 ); }   
   GLOBAL                     { return symbol(GLOBAL              ); }
   GO                         { return symbol(GO                  ); }
   GOTO                       { return symbol(GOTO                ); }
   GRANT                      { return symbol(GRANT               ); }
   GROUP                      { return symbol(GROUP               ); }
   HANDLER                    { return symbol(HANDLER             ); }
   HAVING                     { return symbol(HAVING              ); }
   HINT                       { return symbol(HINT                ); }   
   HOLD                       { return symbol(HOLD                ); }
   HOUR[Ss]?                  { return symbol(HOURS               ); }     
   IF                         { return symbol(IF                  ); }
   IMMEDIATE                  { return symbol(IMMEDIATE           ); }
   IN                         { return symbol(IN                  ); }
   INCLUSIVE                  { return symbol(INCLUSIVE           ); }
   INDEX                      { return symbol(INDEX               ); }
   INHERIT                    { return symbol(INHERIT             ); }
   INNER                      { return symbol(INNER               ); }
   INOUT                      { return symbol(INOUT               ); }
   INSENSITIVE                { return symbol(INSENSITIVE         ); }
   INTERSECT                  { return symbol(INTERSECT           ); }
   INTO                       { return symbol(INTO                ); }
   ISOBID                     { return symbol(ISOBID              ); }
   IS                         { return symbol(IS                  ); }
   ITERATE                    { return symbol(ITERATE             ); }
   JAR                        { return symbol(JAR                 ); }
   JOIN                       { return symbol(JOIN                ); }
   KEEP                       { return symbol(KEEP                ); }
   KEY                        { return symbol(KEY                 ); }
   LABEL                      { return symbol(LABEL               ); }
   LANGUAGE                   { return symbol(LANGUAGE            ); }
   LAST1                      { return symbol(LAST1               ); }
   LC_CTYPE                   { return symbol(LC_CTYPE            ); }
   LC_TYPE                    { return symbol(LC_TYPE             ); }   
   LEAVE                      { return symbol(LEAVE               ); }
   LIKE                       { return symbol(LIKE                ); }
   LOCALE                     { return symbol(LOCALE              ); }
   LOCAL                      { return symbol(LOCAL               ); }
   LOCATORS                   { return symbol(LOCATORS            ); }
   LOCATOR                    { return symbol(LOCATOR             ); }
   LOCKMAX                    { return symbol(LOCKMAX             ); }
   LOCKSIZE                   { return symbol(LOCKSIZE            ); }
   LOCK                       { return symbol(LOCK                ); }
   LONG                       { return symbol(LONG                ); }
   LOOP                       { return symbol(LOOP                ); }
   MAINTAINED                 { return symbol(MAINTAINED          ); }
   MATERIALIZED               { return symbol(MATERIALIZED        ); }
   MEMBER                     { return symbol(MEMBER              ); }
   MICROSECOND[Ss]?           { return symbol(MICROSECONDS        ); }   
   MINUTE[Ss]?                { return symbol(MINUTES             ); }      
   MODE                       { return symbol(MODE                ); }   
   MODIFIES                   { return symbol(MODIFIES            ); }
   MONTH[Ss]?                 { return symbol(MONTHS              ); }     
   NEXTVAL                    { return symbol(NEXTVAL             ); }
   NEXT                       { return symbol(NEXT                ); }
   NO                         { return symbol(NO                  ); }
   NONE                       { return symbol(NONE                ); }
   NOT                        { return symbol(NOT                 ); }
   NULLS                      { return symbol(NULLS               ); }
   NULL                       { return symbol(NULL                ); }
   NUMPARTS                   { return symbol(NUMPARTS            ); }
   OBID                       { return symbol(OBID                ); }
   OF                         { return symbol(OF                  ); }
   OLD                        { return symbol(OLD                 ); }
   ON                         { return symbol(ON                  ); }
   OPEN                       { return symbol(OPEN                ); }
   OPTIMIZATION               { return symbol(OPTIMIZATION        ); }
   OPTIMIZE                   { return symbol(OPTIMIZE            ); }
   ORDER                      { return symbol(ORDER               ); }
   OR                         { return symbol(OR                  ); }
   ORGANIZATION1              { return symbol(ORGANIZATION1       ); }
   OUTER                      { return symbol(OUTER               ); }
   OUT                        { return symbol(OUT                 ); }
   PACKAGESET                 { return symbol(PACKAGESET          ); }   
   PACKAGE                    { return symbol(PACKAGE             ); }
   PARAMETER                  { return symbol(PARAMETER           ); }
   PART                       { return symbol(PART                ); }
   PADDED                     { return symbol(PADDED              ); }
   PARTITIONED                { return symbol(PARTITIONED         ); }
   PARTITIONING               { return symbol(PARTITIONING        ); }
   PARTITION                  { return symbol(PARTITION           ); }
   PASSWORD                   { return symbol(PASSWORD            ); }   
   PATH                       { return symbol(PATH                ); }
   PIECESIZE                  { return symbol(PIECESIZE           ); }
   PERIOD1                    { return symbol(PERIOD1             ); }
   PLAN                       { return symbol(PLAN                ); }
   PRECISION                  { return symbol(PRECISION           ); }
   PREPARE                    { return symbol(PREPARE             ); }
   PREVIOUS                   { return symbol(PREVIOUS            ); }   
   PREVVAL                    { return symbol(PREVVAL             ); }
   PRIOR                      { return symbol(PRIOR               ); }
   PRIQTY                     { return symbol(PRIQTY              ); }
   PRIVILEGES                 { return symbol(PRIVILEGES          ); }
   PROCEDURE                  { return symbol(PROCEDURE           ); }
   PROGRAM                    { return symbol(PROGRAM             ); }
   PSID                       { return symbol(PSID                ); }
   PUBLIC                     { return symbol(PUBLIC              ); }
   QUERYNO                    { return symbol(QUERYNO             ); }
   QUERY                      { return symbol(QUERY               ); }
   READS                      { return symbol(READS               ); }
   REFERENCES                 { return symbol(REFERENCES          ); }
   REFRESH                    { return symbol(REFRESH             ); }
   RESIGNAL                   { return symbol(RESIGNAL            ); }
   RELEASE                    { return symbol(RELEASE             ); }
   RENAME                     { return symbol(RENAME              ); }
   RESTRICT                   { return symbol(RESTRICT            ); }
   RESULT                     { return symbol(RESULT              ); }
   RESULT_SET_LOCATOR         { return symbol(RESULT_SET_LOCATOR  ); }
   RETURNS                    { return symbol(RETURNS             ); }
   RETURN                     { return symbol(RETURN              ); }
   REVOKE                     { return symbol(REVOKE              ); }
   ROLE                       { return symbol(ROLE                ); }
   ROLLBACK                   { return symbol(ROLLBACK            ); }
   ROUND_CEILING              { return symbol(ROUND_MODE          ); }
   ROUND_DOWN                 { return symbol(ROUND_MODE          ); }
   ROUND_FLOOR                { return symbol(ROUND_MODE          ); }
   ROUND_HALF_DOWN            { return symbol(ROUND_MODE          ); }
   ROUND_HALF_EVEN            { return symbol(ROUND_MODE          ); }
   ROUND_HALF_UP              { return symbol(ROUND_MODE          ); }
   ROUND_UP                   { return symbol(ROUND_MODE          ); }
   ROUNDING                   { return symbol(ROUNDING            ); }   
   ROUTINE                    { return symbol(ROUTINE             ); }   
   ROWSET                     { return symbol(ROWSET              ); }
   ROW[Ss]?                   { return symbol(ROWS                ); }
   RULES                      { return symbol(RULES               ); }   
   RUN                        { return symbol(RUN                 ); }
   SAVEPOINT                  { return symbol(SAVEPOINT           ); }
   SCHEMA                     { return symbol(SCHEMA              ); }
   SCHEME                     { return symbol(SCHEME              ); }   
   SCRATCHPAD                 { return symbol(SCRATCHPAD          ); }
   SECOND[Ss]?                { return symbol(SECONDS             ); }     
   SECQTY                     { return symbol(SECQTY              ); }
   SECURITY                   { return symbol(SECURITY            ); }
   SEQUENCE                   { return symbol(SEQUENCE            ); }
   SELECT                     { return symbol(SELECT              ); }
   SELECTIVITY                { return symbol(SELECTIVITY         ); }   
   SENSITIVE                  { return symbol(SENSITIVE           ); }
   SERVER                     { return symbol(SERVER              ); }   
   SESSION_USER               { return symbol(SESSION_USER        ); }
   SET                        { return symbol(SET                 ); }
   SIGNAL                     { return symbol(SIGNAL              ); }
   SIMPLE                     { return symbol(SIMPLE              ); }
   SOME                       { return symbol(SOME                ); }
   SOURCE                     { return symbol(SOURCE              ); }
   SPECIFIC                   { return symbol(SPECIFIC            ); }
   SQLID                      { return symbol(SQLID               ); }   
   STANDARD                   { return symbol(STANDARD            ); }
   STATIC                     { return symbol(STATIC              ); }
   STATEMENT                  { return symbol(STATEMENT           ); }
   STAY                       { return symbol(STAY                ); }
   STOGROUP                   { return symbol(STOGROUP            ); }
   STORES                     { return symbol(STORES              ); }
   STYLE                      { return symbol(STYLE               ); }
   SUMMARY                    { return symbol(SUMMARY             ); }
   SYNONYM                    { return symbol(SYNONYM             ); }
   SYSDATE                    { return symbol(SYSDATE             ); }
   SYSTEM                     { return symbol(SYSTEM              ); }
   SYSTIMESTAMP               { return symbol(SYSTIMESTAMP        ); }
   TABLESPACE                 { return symbol(TABLESPACE          ); }
   TABLE                      { return symbol(TABLE               ); }
   THEN                       { return symbol(THEN                ); }
   TO                         { return symbol(TO                  ); }
   TRIGGER                    { return symbol(TRIGGER             ); }
   TYPE[Ss]?                  { return symbol(TYPES               ); }
   UNDO                       { return symbol(UNDO                ); }
   UNION                      { return symbol(UNION               ); }
   UNIQUE                     { return symbol(UNIQUE              ); }
   UNTIL                      { return symbol(UNTIL               ); }
   UPDATE                     { return symbol(UPDATE              ); }
   USER                       { return symbol(USER                ); }
   USING                      { return symbol(USING               ); }
   VALIDPROC                  { return symbol(VALIDPROC           ); }
   VARIABLE                   { return symbol(VARIABLE            ); }
   VARIANT                    { return symbol(VARIANT             ); }
   VCAT                       { return symbol(VCAT                ); }
   VIEW                       { return symbol(VIEW                ); }
   VOLATILE                   { return symbol(VOLATILE            ); }
   VOLUMES                    { return symbol(VOLUMES             ); }
   WHENEVER                   { return symbol(WHENEVER            ); }
   WHEN                       { return symbol(WHEN                ); }
   WHERE                      { return symbol(WHERE               ); }
   WHILE                      { return symbol(WHILE               ); }
   WITH                       { return symbol(WITH                ); }
   WLM                        { return symbol(WLM                 ); }
   XMLEXISTS                  { return symbol(XMLEXISTS           ); }
   XMLCAST                    { return symbol(XMLCAST             ); }
   ZONE                       { return symbol(ZONE                ); }
   
   /*************************************************************/
   /*************************************************************/
   /***   PALABRAS NO RESERVADAS                              ***/
   /*************************************************************/
   /*************************************************************/
   
   ASC                     { return symbol(ASC              ); }
   CALLER                  { return symbol(CALLER           ); }
   CLIENT                  { return symbol(CLIENT           ); }
   DESC                    { return symbol(DESC             ); }
   DOT                     { return symbol(DOT              ); }
   EXCHANGE                { return symbol(EXCHANGE         ); }
   INCLUDE                 { return symbol(INCLUDE          ); }
   LOCKED                  { return symbol(LOCKED           ); }
   MERGE                   { return symbol(MERGE            ); }
   OVERRIDING              { return symbol(OVERRIDING       ); }
   POSITIONING             { return symbol(POSITIONING      ); }
   READ                    { return symbol(READ             ); }
   SCROLL                  { return symbol(SCROLL           ); }
   SESSION                 { return symbol(SESSION          ); }
   TEMPORARY               { return symbol(TEMPORARY        ); }
   WORK                    { return symbol(WORK             ); }
   BUSINESS_TIME           { return symbol(BUSINESS_TIME    ); }
   CROSS                   { return symbol(CROSS            ); }
   CS                      { return symbol(CS               ); }
   INDICATOR               { return symbol(INDICATOR        ); }
   INPUT                   { return symbol(INPUT            ); }
   NUMERIC                 { return symbol(NUMERIC          ); }
   ONLY                    { return symbol(ONLY             ); }
   PORTION                 { return symbol(PORTION          ); }
   RR                      { return symbol(RR               ); }
   RS                      { return symbol(RS               ); }   
   SKIP                    { return symbol(SKIP             ); }
   SQL                     { return symbol(SQL              ); }
   UR                      { return symbol(UR               ); }
   VALUES                  { return symbol(VALUES           ); }   
   VERSION                 { return symbol(VERSION          ); }
   WITHOUT                 { return symbol(WITHOUT          ); }
   YEAR[Ss]?               { return symbol(YEARS            ); }      
   YES                     { return symbol(YES              ); }

   /*************************************************************/
   /*************************************************************/
   /***   FUNCIONES                                           ***/
   /*************************************************************/
   /*************************************************************/

   MAX                                            { return symbol(FUNCTION_BOTH); }
   MIN                                            { return symbol(FUNCTION_BOTH); }
   AVG                                            { return symbol(FUNCTION_AGGREGATE); }
   CORRELATION                                    { return symbol(FUNCTION_AGGREGATE); }
   COUNT                                          { return symbol(FUNCTION_AGGREGATE); }
   COUNT_BIG                                      { return symbol(FUNCTION_AGGREGATE); }
   COVARIANCE_SAMP                                { return symbol(FUNCTION_AGGREGATE); }
   COVARIANCE                                     { return symbol(FUNCTION_AGGREGATE); }
   MEDIAN                                         { return symbol(FUNCTION_AGGREGATE); }
   STDDEV_SAMP                                    { return symbol(FUNCTION_AGGREGATE); }
   STDDEV                                         { return symbol(FUNCTION_AGGREGATE); }
   SUM                                            { return symbol(FUNCTION_AGGREGATE); }
   VARIANCE_SAMP                                  { return symbol(FUNCTION_AGGREGATE); }
   VARIANCE                                       { return symbol(FUNCTION_AGGREGATE); }
   XMLAGG                                         { return symbol(FUNCTION_AGGREGATE); }
   ABS                                            { return symbol(FUNCTION_SCALAR);    }
   ACOS                                           { return symbol(FUNCTION_SCALAR);    }
   ADD_MONTHS                                     { return symbol(FUNCTION_SCALAR);    }
   ASCII                                          { return symbol(FUNCTION_SCALAR);    }
   ASCII_CHR                                      { return symbol(FUNCTION_SCALAR);    }
   ASCII_STR                                      { return symbol(FUNCTION_SCALAR);    }
   ASIN                                           { return symbol(FUNCTION_SCALAR);    }
   ATAN                                           { return symbol(FUNCTION_SCALAR);    }
   ATANH                                          { return symbol(FUNCTION_SCALAR);    }
   ATAN2                                          { return symbol(FUNCTION_SCALAR);    }
   BIGINT                                         { return symbol(FUNCTION_SCALAR);    }
   BINARY                                         { cacheSymbol(BINARY                              , FUNCTION_SCALAR);    }
   BITAND                                         { return symbol(FUNCTION_SCALAR);    }
   BITANDNOT                                      { return symbol(FUNCTION_SCALAR);    }
   BITOR                                          { return symbol(FUNCTION_SCALAR);    }
   BITXOR                                         { return symbol(FUNCTION_SCALAR);    }
   BITNOT                                         { return symbol(FUNCTION_SCALAR);    }
   BLOB                                           { return symbol(FUNCTION_SCALAR);    }
   CCSID_ENCODING                                 { return symbol(FUNCTION_SCALAR);    }
   CEILING                                        { return symbol(FUNCTION_SCALAR);    }
   CHAR                                           { cacheSymbol(CHAR                                , FUNCTION_SCALAR);    }
   CHARACTER_LENGTH                               { return symbol(FUNCTION_SCALAR);    }
   CLOB                                           { return symbol(FUNCTION_SCALAR);    }
   COALESCE                                       { return symbol(FUNCTION_SCALAR);    }
   COLLATION_KEY                                  { return symbol(FUNCTION_SCALAR);    }
   COMPARE_DECFLOAT                               { return symbol(FUNCTION_SCALAR);    }
   CONCAT                                         { cacheSymbol(CONCAT                              , FUNCTION_SCALAR);    }
   CONTAINS                                       { cacheSymbol(CONTAINS                            , FUNCTION_SCALAR);    }
   COS                                            { return symbol(FUNCTION_SCALAR);    }
   COSH                                           { return symbol(FUNCTION_SCALAR);    }
   DATE                                           { cacheSymbol(DATE                                , FUNCTION_SCALAR);    }
   DAYOFMONTH                                     { return symbol(FUNCTION_SCALAR);    }
   DAYOFWEEK                                      { return symbol(FUNCTION_SCALAR);    }
   DAYOFWEEK_ISO                                  { return symbol(FUNCTION_SCALAR);    }
   DAYOFYEAR                                      { return symbol(FUNCTION_SCALAR);    }
   DBCLOB                                         { return symbol(FUNCTION_SCALAR);    }
   DECFLOAT                                       { cacheSymbol(DECFLOAT                            , FUNCTION_SCALAR);    }
   DECFLOAT_FORMAT                                { return symbol(FUNCTION_SCALAR);    }
   DECFLOAT_SORTKEY                               { return symbol(FUNCTION_SCALAR);    }
   DECIMAL                                        { cacheSymbol(DECIMAL                             , FUNCTION_SCALAR);    }
   DEC                                            { cacheSymbol(DECIMAL                             , FUNCTION_SCALAR);    }   
   DECODE                                         { return symbol(FUNCTION_SCALAR);    }
   DECRYPT_BINARY                                 { return symbol(FUNCTION_SCALAR);    }
   DECRYPT_BIT                                    { return symbol(FUNCTION_SCALAR);    }
   DECRYPT_CHAR                                   { return symbol(FUNCTION_SCALAR);    }
   DECRYPT_DB                                     { return symbol(FUNCTION_SCALAR);    }
   DEGREES                                        { return symbol(FUNCTION_SCALAR);    }
   DIFFERENCE                                     { return symbol(FUNCTION_SCALAR);    }
   DIGITS                                         { return symbol(FUNCTION_SCALAR);    }
   DOUBLE_PRECISION                               { return symbol(FUNCTION_SCALAR);    }
   DOUBLE                                         { cacheSymbol(DOUBLE                              , FUNCTION_SCALAR);    }
   DSN_XMLVALIDATE                                { return symbol(FUNCTION_SCALAR);    }
   EBCDIC_CHR                                     { return symbol(FUNCTION_SCALAR);    }
   EBCDIC_STR                                     { return symbol(FUNCTION_SCALAR);    }
   ENCRYPT_TDES                                   { return symbol(FUNCTION_SCALAR);    }
   EXP                                            { return symbol(FUNCTION_SCALAR);    }
   EXTRACT                                        { return symbol(FUNCTION_SCALAR);    }
   FLOAT                                          { cacheSymbol(FLOAT                               , FUNCTION_SCALAR);    }
   FLOOR                                          { return symbol(FUNCTION_SCALAR);    }
   GENERATE_UNIQUE                                { return symbol(FUNCTION_SCALAR);    }
   GETHINT                                        { return symbol(FUNCTION_SCALAR);    }
   GETVARIABLE                                    { return symbol(FUNCTION_SCALAR);    }
   GRAPHIC                                        { cacheSymbol(GRAPHIC                             , FUNCTION_SCALAR);    }
   HEX                                            { return symbol(FUNCTION_SCALAR);    }
   IDENTITY_VAL_LOCAL                             { return symbol(FUNCTION_SCALAR);    }
   IFNULL                                         { return symbol(FUNCTION_SCALAR);    }
   INSERT                                         { cacheSymbol(INSERT                              , FUNCTION_SCALAR);    }
   INTEGER                                        { cacheSymbol(INTEGER                             , FUNCTION_SCALAR);    }
   INT                                            { cacheSymbol(INT                                 , FUNCTION_SCALAR);    }
   JULIAN_DAY                                     { return symbol(FUNCTION_SCALAR);    }
   LAST_DAY                                       { return symbol(FUNCTION_SCALAR);    }
   LCASE                                          { return symbol(FUNCTION_SCALAR);    }
   LEFT                                           { cacheSymbol(LEFT                                , FUNCTION_SCALAR);    }
   LENGTH                                         { return symbol(FUNCTION_SCALAR);    }
   LN                                             { return symbol(FUNCTION_SCALAR);    }
   LOCATE                                         { return symbol(FUNCTION_SCALAR);    }
   LOCATE_IN_STRING                               { return symbol(FUNCTION_SCALAR);    }
   LOG10                                          { return symbol(FUNCTION_SCALAR);    }
   LOWER                                          { return symbol(FUNCTION_SCALAR);    }
   LPAD                                           { return symbol(FUNCTION_SCALAR);    }
   LTRIM                                          { return symbol(FUNCTION_SCALAR);    }
   MIDNIGHT_SECONDS                               { return symbol(FUNCTION_SCALAR);    }
   MOD                                            { return symbol(FUNCTION_SCALAR);    }
   MONTHS_BETWEEN                                 { return symbol(FUNCTION_SCALAR);    }
   MQREAD                                         { return symbol(FUNCTION_SCALAR);    }
   MQREADCLOB                                     { return symbol(FUNCTION_SCALAR);    }
   MQRECEIVE                                      { return symbol(FUNCTION_SCALAR);    }
   MQRECEIVECLOB                                  { return symbol(FUNCTION_SCALAR);    }
   MQSEND                                         { return symbol(FUNCTION_SCALAR);    }
   MULTIPLY_ALT                                   { return symbol(FUNCTION_SCALAR);    }
   NEXT_DAY                                       { return symbol(FUNCTION_SCALAR);    }
   NORMALIZE_DECFLOAT                             { return symbol(FUNCTION_SCALAR);    }
   NORMALIZE_STRING                               { return symbol(FUNCTION_SCALAR);    }
   NULLIF                                         { return symbol(FUNCTION_SCALAR);    }
   NVL                                            { return symbol(FUNCTION_SCALAR);    }
   OVERLAY                                        { return symbol(FUNCTION_SCALAR);    }
   PACK                                           { return symbol(FUNCTION_SCALAR);    }
   POSITION                                       { return symbol(FUNCTION_SCALAR);    }
   POSSTR                                         { return symbol(FUNCTION_SCALAR);    }
   POWER                                          { return symbol(FUNCTION_SCALAR);    }
   QUANTIZE                                       { return symbol(FUNCTION_SCALAR);    }
   QUARTER                                        { return symbol(FUNCTION_SCALAR);    }
   RADIANS                                        { return symbol(FUNCTION_SCALAR);    }
   RAISE_ERROR                                    { return symbol(FUNCTION_SCALAR);    }
   RAND                                           { return symbol(FUNCTION_SCALAR);    }
   REAL                                           { cacheSymbol(REAL                                , FUNCTION_SCALAR);    }
   REPEAT                                         { cacheSymbol(REPEAT                              , FUNCTION_SCALAR);    }
   REPLACE                                        { return symbol(FUNCTION_SCALAR);    }
   RID                                            { return symbol(FUNCTION_SCALAR);    }
   RIGHT                                          { cacheSymbol(RIGHT                               , FUNCTION_SCALAR);    }
   ROUND_TIMESTAMP                                { return symbol(FUNCTION_SCALAR);    }
   ROUND                                          { return symbol(FUNCTION_SCALAR);    }   
   ROWID                                          { return symbol(FUNCTION_SCALAR);    }
   RPAD                                           { return symbol(FUNCTION_SCALAR);    }
   RTRIM                                          { return symbol(FUNCTION_SCALAR);    }
   SCORE                                          { return symbol(FUNCTION_SCALAR);    }
   SIGN                                           { return symbol(FUNCTION_SCALAR);    }
   SIN                                            { return symbol(FUNCTION_SCALAR);    }
   SINH                                           { return symbol(FUNCTION_SCALAR);    }
   SMALLINT                                       { cacheSymbol(SMALLINT                            , FUNCTION_SCALAR);    }
   SOUNDEX                                        { return symbol(FUNCTION_SCALAR);    }
   SOAPHTTPC                                      { return symbol(FUNCTION_SCALAR);    }
   SOAPHTTPV                                      { return symbol(FUNCTION_SCALAR);    }
   SOAPHTTPNC                                     { return symbol(FUNCTION_SCALAR);    }
   SOAPHTTPNV                                     { return symbol(FUNCTION_SCALAR);    }
   SPACE                                          { return symbol(FUNCTION_SCALAR);    }
   SQRT                                           { return symbol(FUNCTION_SCALAR);    }
   STRIP                                          { return symbol(FUNCTION_SCALAR);    }
   SUBSTR                                         { return symbol(FUNCTION_SCALAR);    }
   SUBSTRING                                      { return symbol(FUNCTION_SCALAR);    }
   TAN                                            { return symbol(FUNCTION_SCALAR);    }
   TANH                                           { return symbol(FUNCTION_SCALAR);    }
   TIME                                           { cacheSymbol(TIME                                , FUNCTION_SCALAR);    }
   TIMESTAMP                                      { cacheSymbol(TIMESTAMP                           , FUNCTION_SCALAR);    }
   TIMESTAMPADD                                   { return symbol(FUNCTION_SCALAR);    }
   TIMESTAMP_FORMAT                               { return symbol(FUNCTION_SCALAR);    }
   TIMESTAMP_ISO                                  { return symbol(FUNCTION_SCALAR);    }
   TIMESTAMPDIFF                                  { return symbol(FUNCTION_SCALAR);    }
   TIMESTAMP_TZ                                   { return symbol(FUNCTION_SCALAR);    }
   TO_CHAR                                        { return symbol(FUNCTION_SCALAR);    }
   TO_DATE                                        { return symbol(FUNCTION_SCALAR);    }
   TO_NUMBER                                      { return symbol(FUNCTION_SCALAR);    }
   TOTALORDER                                     { return symbol(FUNCTION_SCALAR);    }
   TRANSLATE                                      { return symbol(FUNCTION_SCALAR);    }
   TRIM                                           { return symbol(FUNCTION_SCALAR);    }
   TRUNCATE                                       { cacheSymbol(TRUNCATE                            , FUNCTION_SCALAR);    }
   TRUNC                                          { return symbol(FUNCTION_SCALAR);    }
   TRUNC_TIMESTAMP                                { return symbol(FUNCTION_SCALAR);    }
   UCASE                                          { return symbol(FUNCTION_SCALAR);    }
   UNICODE                                        { return symbol(FUNCTION_SCALAR);    }
   UNICODE_STR                                    { return symbol(FUNCTION_SCALAR);    }
   UPPER                                          { return symbol(FUNCTION_SCALAR);    }
   VALUE                                          { return symbol(VALUE);              }
   VARBINARY                                      { cacheSymbol(VARBINARY                           , FUNCTION_SCALAR);    }
   VARCHAR                                        { cacheSymbol(VARCHAR                             , FUNCTION_SCALAR);    }
   VARCHAR_FORMAT                                 { return symbol(FUNCTION_SCALAR);    }
   VARGRAPHIC                                     { cacheSymbol(VARGRAPHIC                          , FUNCTION_SCALAR);    }
   VERIFY_GROUP_FOR_USER                          { return symbol(FUNCTION_SCALAR);    }
   VERIFY_ROLE_FOR_USER                           { return symbol(FUNCTION_SCALAR);    }
   VERIFY_TRUSTED_CONTEXT_ROLE_FOR_USER           { return symbol(FUNCTION_SCALAR);    }
   WEEK                                           { return symbol(FUNCTION_SCALAR);    }
   WEEK_ISO                                       { return symbol(FUNCTION_SCALAR);    }
   XMLATTRIBUTES                                  { return symbol(FUNCTION_SCALAR);    }
   XMLCOMMENT                                     { return symbol(FUNCTION_SCALAR);    }
   XMLCONCAT                                      { return symbol(FUNCTION_SCALAR);    }
   XMLDOCUMENT                                    { return symbol(FUNCTION_SCALAR);    }
   XMLELEMENT                                     { return symbol(FUNCTION_SCALAR);    }
   XMLFOREST                                      { return symbol(FUNCTION_SCALAR);    }
   XMLMODIFY                                      { return symbol(FUNCTION_SCALAR);    }
   XMLNAMESPACES                                  { return symbol(FUNCTION_SCALAR);    }
   XMLPARSE                                       { return symbol(FUNCTION_SCALAR);    }
   XMLPI                                          { return symbol(FUNCTION_SCALAR);    }
   XMLQUERY                                       { return symbol(FUNCTION_SCALAR);    }
   XMLSERIALIZE                                   { return symbol(FUNCTION_SCALAR);    }
   XMLTABLE                                       { return symbol(XMLTABLE);          }
   XMLTEXT                                        { return symbol(FUNCTION_SCALAR);    }
   XMLXSROBJECTID                                 { return symbol(FUNCTION_SCALAR);    }
   XSLTRANSFORM                                   { return symbol(FUNCTION_SCALAR);    }
   ADMIN_TASK_LIST                                { return symbol(FUNCTION_TABLE);     }
   ADMIN_TASK_OUTPUT                              { return symbol(FUNCTION_TABLE);     }
   ADMIN_TASK_STATUS                              { return symbol(FUNCTION_TABLE);     }
   MQREADALL                                      { return symbol(FUNCTION_TABLE);     }
   MQREADALLCLOB                                  { return symbol(FUNCTION_TABLE);     }
   MQRECEIVEALL                                   { return symbol(FUNCTION_TABLE);     }
   MQRECEIVEALLCLOB                               { return symbol(FUNCTION_TABLE);     }

   UNPACK                                         { return symbol(FUNCTION_ROW);       } 

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }
  

 {SPACES}          { /* DO NOTHING */              }
 {TABS}            { /* DO NOTHING */              } 
 {ID}              { return symbol(ID);            }
 
// {NUMBIN}          { return symbol{NUM_BINARY);  }
// {NUMGRAPHIC}      { return symbol{NUM_GRAPHIC); }

  "--"             { pushState(COMMENT); }    
  "??("            { /* eat [ */ }
  "??)"            { /* eat ] */ }
  "??<"            { /* eat { */ }
  "??>"            { /* eat } */ }
  "??="            { /* eat pound */ }
  "??/"            { /* eat \ */ }
  
  "||"             { return symbol(CONCAT_SYM);    }
  "<>"             { return symbol(NE);        }
  "^="             { return symbol(NE2);       }  
  "^>"             { return symbol(NGT);       }
  "Âµ>"             { return symbol(NGT);       }
  "Âµ<"             { return symbol(NGT);       }
  "Âµ="             { return symbol(NE2);       }
  "^<"             { return symbol(NLT);       }
  ">="             { return symbol(GE);        }  
  "<="             { return symbol(LE);        }  
  "="              { return symbol(EQ);        }
  ">"              { return symbol(GT);        }
  "<"              { return symbol(LT);        }  
  "+"              { return symbol(PLUS);      }
  "-"              { return symbol(SUBTRACT);  }
  "/"              { return symbol(DIVIDE);    }  
  "*"              { return symbol(MULTIPLY);  }
  ","              { return symbol(COMMA);     }
  "?"              { return symbol(QUESTION_MARK);     }
  "("              { return symbol(LPAR);
  /* 
                    if (words.contains(lastID)) {
                         info.setInSearch();
                      }
                      if (info.isInSearch() && words.contains(lastID)) {
                            pars.push(0);
                      }    
                      else { 
                        pars.push(1); // Pila de parentesis
                        return symbol(LPAR);      
                     }
*/                     
                   }   
  ")"              { return symbol(RPAR);
/*
                     int idPar = pars.pop(); // Quita el ( de la pila
                     if (idPar == 1) return symbol(RPAR);
*/
                   }
  // Caso : \n Host
  ":"              { return symbol(PREHOST); }

  \n            { /* eat */ } 
  \r            { /* eat */ }

/*
 * Algunas funciones son ambiguas: 
 * Ejemplo: TIMESTAMP o TIMESTAMP (....)
 * Aqui buscamos el siguiente caracter que no es espacios
 * Si es LPAR  devuelve FUNCTION_ ....
 * Si no es LPAR devuelve el simbolo
 * en ambos casos retrocede el caracter leido
 */
 
<CHECK_FUNCTION> {
   {SPACES}     { /* eat */ }
   {TABS}       { /* eat */ }
   "("          { yypushback(1); return getCacheSymbol(true);  }
   \r           { return getCacheSymbol(false);                }
   \n           { return getCacheSymbol(false);                } 
   [^]          { yypushback(1); return getCacheSymbol(false); }
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

<COMMENT> {
  \n            { popState(); }
  \r            { /* eat */   }

  [^]           { /* eat */   }
}
