package com.jgg.sdp.parser.lang;

import java_cup.runtime.Symbol;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.parser.base.*;

import static com.jgg.sdp.parser.lang.DB2Sym.*;

%%

%public
%class      DB2Lexer
%extends    GenericLexer
%implements GenericScanner , ILexer

%line
%column
%char
%full
%ignorecase
%cup

%xstate COMMENT , QUOTE_STRING

%{

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
      print("Devuelve SYMBOL(" + code + ") - (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(code, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, code, s);
   }

   public Symbol symbolic(int value) {
      data = true;
      String txt = Integer.toString(value);
      print("Devuelve SYMBOL (" + (yyline + 1) + "," + (yycolumn + 1) + ") " + txt);
      Symbol s = new Symbol(NUMERO, yyline, yycolumn, txt);
      return symbolFactory.newSymbol(txt, NUMERO, s);
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
NUMERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,]?[0-9]+

//ID = ({ALPHA}|{NUMERO}|\_)+
ID = [a-zA-Z0-9\_\-]+[\.[a-zA-Z0-9\_\-]+]+

SP=[ ]{1}

%% 

/******************************************************************************/
/******************************************************************************/
/***                      CASOS GENERALES                                   ***/
/******************************************************************************/
/******************************************************************************/


{SPACES}                        { /* nada */        }

\n                              { /* do nothing */ }
\r                              { /* do nothing */ }

COMMIT             { return symbol(COMMIT);    }
CURSOR             { return symbol(CURSOR);    }

DECLARE            { return symbol(DECLARE);   }

EXCLUSIVE          { return symbol(EXCLUSIVE); }

INCLUDE            { return symbol(INCLUDE);   }
IN                 { return symbol(IN);        }

LOCK               { return symbol(LOCK);      }

MODE               { return symbol(MODE);      }

ROLLBACK           { return symbol(ROLLBACK);  }

SAVEPOINT          { return symbol(SAVEPOINT); }
SHARE              { return symbol(SHARE);     }
STATEMENT          { return symbol(STATEMENT);     }

TABLE              { return symbol(TABLE);     }
TO                 { /* eat */                 }

WORK               { return symbol(WORK);      }

{ID}               { return symbol(ID);        }

[^]                { /* nada */                }


/*

A{SP}+SET                         { return symbol(A_SET); }
ABS                               { return symbol(ABS                             ); } 
ABSOLUTE                          { return symbol(ABSOLUTE                        ); }
ACTION                            { return symbol(ACTION                          ); }
ADA                               { return symbol(ADA                             ); }
ADD                               { return symbol(ADD                             ); }
ADMIN                             { return symbol(ADMIN                           ); }
AFTER                             { return symbol(AFTER                           ); }
ALL                               { return symbol(ALL                             ); }
ALLOCATE                          { return symbol(ALLOCATE                        ); }
ALTER                             { return symbol(ALTER                           ); }
ALWAYS                            { return symbol(ALWAYS                          ); }
AND                               { return symbol(AND                             ); }
ANY                               { return symbol(ANY                             ); }
ARE                               { return symbol(ARE                             ); }
ARRAY                             { return symbol(ARRAY                           ); }
AS                                { return symbol(AS                              ); }
ASC                               { return symbol(ASC                             ); }
ASENSITIVE                        { return symbol(ASENSITIVE                      ); }
ASSERTION                         { return symbol(ASSERTION                       ); }
ASSIGNMENT                        { return symbol(ASSIGNMENT                      ); }
ASYMMETRIC                        { return symbol(ASYMMETRIC                      ); }
AT                                { return symbol(AT                              ); }
ATOMIC                            { return symbol(ATOMIC                          ); }
ATTRIBUTE                         { return symbol(ATTRIBUTE                       ); }
ATTRIBUTES                        { return symbol(ATTRIBUTES                      ); }
AUTHORIZATION                     { return symbol(AUTHORIZATION                   ); }
AVG                               { return symbol(AVG                             ); }
BEFORE                            { return symbol(BEFORE                          ); }
BEGIN                             { return symbol(BEGIN                           ); }
BERNOULLI                         { return symbol(BERNOULLI                       ); }
BETWEEN                           { return symbol(BETWEEN                         ); }
BIGINT                            { return symbol(BIGINT                          ); }
BIN                               { return symbol(BIN                             ); }
BINARY                            { return symbol(BINARY                          ); }
BLOB                              { return symbol(BLOB                            ); }
BOOLEAN                           { return symbol(BOOLEAN                         ); }
BOTH                              { return symbol(BOTH                            ); }
BREADTH                           { return symbol(BREADTH                         ); }
BY                                { return symbol(BY                              ); }
C                                 { return symbol(C                               ); }
CALL                              { return symbol(CALL                            ); }
CALLED                            { return symbol(CALLED                          ); }
CARDINALITY                       { return symbol(CARDINALITY                     ); }
CASCADE                           { return symbol(CASCADE                         ); }
CASCADED                          { return symbol(CASCADED                        ); }
CASE                              { return symbol(CASE                            ); }
CAST                              { return symbol(CAST                            ); }
CATALOG                           { return symbol(CATALOG                         ); }
CATALOG_NAME                      { return symbol(CATALOG_NAME                    ); }
CEIL                              { return symbol(CEIL                            ); }
CEILING                           { return symbol(CEILING                         ); }
CHAIN                             { return symbol(CHAIN                           ); }
CHAR                              { return symbol(CHAR                            ); }
CHARACTER                         { return symbol(CHARACTER                       ); }
CHARACTERISTICS                   { return symbol(CHARACTERISTICS                 ); }
CHARACTERS                        { return symbol(CHARACTERS                      ); }
CHARACTER_LENGTH                  { return symbol(CHARACTER_LENGTH                ); }
CHARACTER_SET_CATALOG             { return symbol(CHARACTER_SET_CATALOG           ); }
CHARACTER_SET_NAME                { return symbol(CHARACTER_SET_NAME              ); }
CHARACTER_SET_SCHEMA              { return symbol(CHARACTER_SET_SCHEMA            ); }
CHAR_LENGTH                       { return symbol(CHAR_LENGTH                     ); }
CHECK                             { return symbol(CHECK                           ); }
CHECKED                           { return symbol(CHECKED                         ); }
CLASS_ORIGIN                      { return symbol(CLASS_ORIGIN                    ); }
CLOB                              { return symbol(CLOB                            ); }
CLOSE                             { return symbol(CLOSE                           ); }
COALESCE                          { return symbol(COALESCE                        ); }
COBOL                             { return symbol(COBOL                           ); }
CODE_UNITS                        { return symbol(CODE_UNITS                      ); }
COLLATE                           { return symbol(COLLATE                         ); }
COLLATION                         { return symbol(COLLATION                       ); }
COLLATION_CATALOG                 { return symbol(COLLATION_CATALOG               ); }
COLLATION_NAME                    { return symbol(COLLATION_NAME                  ); }
COLLATION_SCHEMA                  { return symbol(COLLATION_SCHEMA                ); }
COLLECT                           { return symbol(COLLECT                         ); }
COLUMN                            { return symbol(COLUMN                          ); }
COLUMN_NAME                       { return symbol(COLUMN_NAME                     ); }
COMMAND_FUNCTION                  { return symbol(COMMAND_FUNCTION                ); }
COMMAND_FUNCTION_CODE             { return symbol(COMMAND_FUNCTION_CODE           ); }
COMMIT                            { return symbol(COMMIT                          ); }
COMMITTED                         { return symbol(COMMITTED                       ); }
CONDITION                         { return symbol(CONDITION                       ); }
CONDITION_NUMBER                  { return symbol(CONDITION_NUMBER                ); }
CONNECT                           { return symbol(CONNECT                         ); }
CONNECTION                        { return symbol(CONNECTION                      ); }
CONNECTION_NAME                   { return symbol(CONNECTION_NAME                 ); }
const                             { return symbol(CONST                           ); }
CONSTRAINT                        { return symbol(CONSTRAINT                      ); }
CONSTRAINTS                       { return symbol(CONSTRAINTS                     ); }
CONSTRAINT_CATALOG                { return symbol(CONSTRAINT_CATALOG              ); }
CONSTRAINT_NAME                   { return symbol(CONSTRAINT_NAME                 ); }
CONSTRAINT_SCHEMA                 { return symbol(CONSTRAINT_SCHEMA               ); }
CONSTRUCTOR                       { return symbol(CONSTRUCTOR                     ); }
CONSTRUCTORS                      { return symbol(CONSTRUCTORS                    ); }
CONTAINS                          { return symbol(CONTAINS                        ); }
CONTINUE                          { return symbol(CONTINUE                        ); }
CONVERT                           { return symbol(CONVERT                         ); }
CORR                              { return symbol(CORR                            ); }
CORRESPONDING                     { return symbol(CORRESPONDING                   ); }
COUNT                             { return symbol(COUNT                           ); }
COVAR_POP                         { return symbol(COVAR_POP                       ); }
COVAR_SAMP                        { return symbol(COVAR_SAMP                      ); }
CREATE                            { return symbol(CREATE                          ); }
CROSS                             { return symbol(CROSS                           ); }
CUBE                              { return symbol(CUBE                            ); }
CUME_DIST                         { return symbol(CUME_DIST                       ); }
CURRENT                           { return symbol(CURRENT                         ); }
CURRENT_COLLATION                 { return symbol(CURRENT_COLLATION               ); }
CURRENT_DATE                      { return symbol(CURRENT_DATE                    ); }
CURRENT_DEFAULT_TRANSFORM_GROUP   { return symbol(CURRENT_DEFAULT_TRANSFORM_GROUP ); }
CURRENT_PATH                      { return symbol(CURRENT_PATH                    ); }
CURRENT_ROLE                      { return symbol(CURRENT_ROLE                    ); }
CURRENT_TIME                      { return symbol(CURRENT_TIME                    ); }
CURRENT_TIMESTAMP                 { return symbol(CURRENT_TIMESTAMP               ); }
CURRENT_TRANSFORM_GROUP_FOR_TYPE  { return symbol(CURRENT_TRANSFORM_GROUP_FOR_TYPE); }
CURRENT_USER                      { return symbol(CURRENT_USER                    ); }
CURSOR                            { return symbol(CURSOR                          ); }
CURSOR_NAME                       { return symbol(CURSOR_NAME                     ); }
CYCLE                             { return symbol(CYCLE                           ); }
DATA                              { return symbol(DATA                            ); }
DATE                              { return symbol(DATE                            ); }
DATETIME_INTERVAL_CODE            { return symbol(DATETIME_INTERVAL_CODE          ); }
DATETIME_INTERVAL_PRECISION       { return symbol(DATETIME_INTERVAL_PRECISION     ); }
DAY                               { return symbol(DAY                             ); }
DCL                               { return symbol(DCL                             ); }
DEALLOCATE                        { return symbol(DEALLOCATE                      ); }
DEC                               { return symbol(DEC                             ); }
DECIMAL                           { return symbol(DECIMAL                         ); }
DECLARE                           { return symbol(DECLARE                         ); }
DEFAULT                           { return symbol(DEFAULT                         ); }
DEFAULTS                          { return symbol(DEFAULTS                        ); }
DEFERRABLE                        { return symbol(DEFERRABLE                      ); }
DEFERRED                          { return symbol(DEFERRED                        ); }
DEFINED                           { return symbol(DEFINED                         ); }
DEFINER                           { return symbol(DEFINER                         ); }
DEGREE                            { return symbol(DEGREE                          ); }
DELETE                            { return symbol(DELETE                          ); }
DENSE_RANK                        { return symbol(DENSE_RANK                      ); }
DEPTH                             { return symbol(DEPTH                           ); }
DEREF                             { return symbol(DEREF                           ); }
DERIVED                           { return symbol(DERIVED                         ); }
DESC                              { return symbol(DESC                            ); }
DESCRIBE                          { return symbol(DESCRIBE                        ); }
DESCRIPTOR                        { return symbol(DESCRIPTOR                      ); }
DETERMINISTIC                     { return symbol(DETERMINISTIC                   ); }
DIAGNOSTICS                       { return symbol(DIAGNOSTICS                     ); }
DISCONNECT                        { return symbol(DISCONNECT                      ); }
DISPATCH                          { return symbol(DISPATCH                        ); }
DISPLAY                           { return symbol(DISPLAY                         ); }
DISTINCT                          { return symbol(DISTINCT                        ); }
DOMAIN                            { return symbol(DOMAIN                          ); }
DOUBLE                            { return symbol(DOUBLE                          ); }
DOUBLE_PRECISION                  { return symbol(DOUBLE_PRECISION                ); }
DROP                              { return symbol(DROP                            ); }
DYNAMIC                           { return symbol(DYNAMIC                         ); }
DYNAMIC_FUNCTION                  { return symbol(DYNAMIC_FUNCTION                ); }
DYNAMIC_FUNCTION_CODE             { return symbol(DYNAMIC_FUNCTION_CODE           ); }
EACH                              { return symbol(EACH                            ); }
ELEMENT                           { return symbol(ELEMENT                         ); }
ELSE                              { return symbol(ELSE                            ); }
END                               { return symbol(END                             ); }
END-EXEC                          { return symbol(END-EXEC                        ); }
EQUALS                            { return symbol(EQUALS                          ); }
ESCAPE                            { return symbol(ESCAPE                          ); }
EVERY                             { return symbol(EVERY                           ); }
EXCEPT                            { return symbol(EXCEPT                          ); }
EXCEPTION                         { return symbol(EXCEPTION                       ); }
EXCLUDE                           { return symbol(EXCLUDE                         ); }
EXCLUDING                         { return symbol(EXCLUDING                       ); }
EXEC                              { return symbol(EXEC                            ); }
EXECUTE                           { return symbol(EXECUTE                         ); }
EXISTS                            { return symbol(EXISTS                          ); }
EXP                               { return symbol(EXP                             ); }
EXTERNAL                          { return symbol(EXTERNAL                        ); }
EXTRACT                           { return symbol(EXTRACT                         ); }
FALSE                             { return symbol(FALSE                           ); }
FETCH                             { return symbol(FETCH                           ); }
FILTER                            { return symbol(FILTER                          ); }
FINAL                             { return symbol(FINAL                           ); }
FIRST                             { return symbol(FIRST                           ); }
FIXED                             { return symbol(FIXED                           ); }
FLOAT                             { return symbol(FLOAT                           ); }
FLOOR                             { return symbol(FLOOR                           ); }
FOLLOWING                         { return symbol(FOLLOWING                       ); }
FOR                               { return symbol(FOR                             ); }
FOREIGN                           { return symbol(FOREIGN                         ); }
FORTRAN                           { return symbol(FORTRAN                         ); }
FOUND                             { return symbol(FOUND                           ); }
FREE                              { return symbol(FREE                            ); }
FROM                              { return symbol(FROM                            ); }
FULL                              { return symbol(FULL                            ); }
FUNCTION                          { return symbol(FUNCTION                        ); }
FUSION                            { return symbol(FUSION                          ); }
GENERAL                           { return symbol(GENERAL                         ); }
GENERATED                         { return symbol(GENERATED                       ); }
GET                               { return symbol(GET                             ); }
GLOBAL                            { return symbol(GLOBAL                          ); }
GO                                { return symbol(GO                              ); }
GOTO                              { return symbol(GOTO                            ); }
GRANT                             { return symbol(GRANT                           ); }
GRANTED                           { return symbol(GRANTED                         ); }
GROUP                             { return symbol(GROUP                           ); }
GROUPING                          { return symbol(GROUPING                        ); }
HAVING                            { return symbol(HAVING                          ); }
HIERARCHY                         { return symbol(HIERARCHY                       ); }
HOLD                              { return symbol(HOLD                            ); }
HOUR                              { return symbol(HOUR                            ); }
IDENTITY                          { return symbol(IDENTITY                        ); }
IMMEDIATE                         { return symbol(IMMEDIATE                       ); }
IMPLEMENTATION                    { return symbol(IMPLEMENTATION                  ); }
IN                                { return symbol(IN                              ); }
INCLUDING                         { return symbol(INCLUDING                       ); }
INCREMENT                         { return symbol(INCREMENT                       ); }
INDICATOR                         { return symbol(INDICATOR                       ); }
INDICATOR_TYPE                    { return symbol(INDICATOR_TYPE                  ); }
INITIALLY                         { return symbol(INITIALLY                       ); }
INNER                             { return symbol(INNER                           ); }
INOUT                             { return symbol(INOUT                           ); }
INPUT                             { return symbol(INPUT                           ); }
INSENSITIVE                       { return symbol(INSENSITIVE                     ); }
INSERT                            { return symbol(INSERT                          ); }
INSTANCE                          { return symbol(INSTANCE                        ); }
INSTANTIABLE                      { return symbol(INSTANTIABLE                    ); }
INT                               { return symbol(INT                             ); }
INTEGER                           { return symbol(INTEGER                         ); }
INTERSECT                         { return symbol(INTERSECT                       ); }
INTERSECTION                      { return symbol(INTERSECTION                    ); }
INTERVAL                          { return symbol(INTERVAL                        ); }
INTO                              { return symbol(INTO                            ); }
INVOKER                           { return symbol(INVOKER                         ); }
IS                                { return symbol(IS                              ); }
ISOLATION                         { return symbol(ISOLATION                       ); }
JOIN                              { return symbol(JOIN                            ); }
KEY                               { return symbol(KEY                             ); }
KEY_MEMBER                        { return symbol(KEY_MEMBER                      ); }
KEY_TYPE                          { return symbol(KEY_TYPE                        ); }
KIND                              { return symbol(KIND                            ); }
LANGUAGE                          { return symbol(LANGUAGE                        ); }
LARGE                             { return symbol(LARGE                           ); }
LAST                              { return symbol(LAST                            ); }
LATERAL                           { return symbol(LATERAL                         ); }
LEADING                           { return symbol(LEADING                         ); }
LEFT                              { return symbol(LEFT                            ); }
LENGTH                            { return symbol(LENGTH                          ); }
LEVEL                             { return symbol(LEVEL                           ); }
LIKE                              { return symbol(LIKE                            ); }
LN                                { return symbol(LN                              ); }
LOCAL                             { return symbol(LOCAL                           ); }
LOCALTIME                         { return symbol(LOCALTIME                       ); }
LOCALTIMESTAMP                    { return symbol(LOCALTIMESTAMP                  ); }
LOCATOR                           { return symbol(LOCATOR                         ); }
LOGICAL                           { return symbol(LOGICAL                         ); }
LOWER                             { return symbol(LOWER                           ); }
MAP                               { return symbol(MAP                             ); }
MATCH                             { return symbol(MATCH                           ); }
MATCHED                           { return symbol(MATCHED                         ); }
MAX                               { return symbol(MAX                             ); }
MAXVALUE                          { return symbol(MAXVALUE                        ); }
MEMBER                            { return symbol(MEMBER                          ); }
MERGE                             { return symbol(MERGE                           ); }
MESSAGE_LENGTH                    { return symbol(MESSAGE_LENGTH                  ); }
MESSAGE_OCTET_LENGTH              { return symbol(MESSAGE_OCTET_LENGTH            ); }
MESSAGE_TEXT                      { return symbol(MESSAGE_TEXT                    ); }
METHOD                            { return symbol(METHOD                          ); }
MIN                               { return symbol(MIN                             ); }
MINUTE                            { return symbol(MINUTE                          ); }
MINVALUE                          { return symbol(MINVALUE                        ); }
MOD                               { return symbol(MOD                             ); }
MODIFIES                          { return symbol(MODIFIES                        ); }
MODULE                            { return symbol(MODULE                          ); }
MONTH                             { return symbol(MONTH                           ); }
MORE                              { return symbol(MORE                            ); }
MULTISET                          { return symbol(MULTISET                        ); }
MUMPS                             { return symbol(MUMPS                           ); }
NAME                              { return symbol(NAME                            ); }
NAMES                             { return symbol(NAMES                           ); }
NATIONAL                          { return symbol(NATIONAL                        ); }
NATURAL                           { return symbol(NATURAL                         ); }
NCHAR                             { return symbol(NCHAR                           ); }
NCLOB                             { return symbol(NCLOB                           ); }
NESTING                           { return symbol(NESTING                         ); }
NEW                               { return symbol(NEW                             ); }
NEXT                              { return symbol(NEXT                            ); }
NO                                { return symbol(NO                              ); }
NONE                              { return symbol(NONE                            ); }
NORMALIZE                         { return symbol(NORMALIZE                       ); }
NORMALIZED                        { return symbol(NORMALIZED                      ); }
NOT                               { return symbol(NOT                             ); }
NULL                              { return symbol(NULL                            ); }
NULLABLE                          { return symbol(NULLABLE                        ); }
NULLIF                            { return symbol(NULLIF                          ); }
NULLS                             { return symbol(NULLS                           ); }
NUMBER                            { return symbol(NUMBER                          ); }
NUMERIC                           { return symbol(NUMERIC                         ); }
OBJECT                            { return symbol(OBJECT                          ); }
OCTETS                            { return symbol(OCTETS                          ); }
OCTET_LENGTH                      { return symbol(OCTET_LENGTH                    ); }
OF                                { return symbol(OF                              ); }
OLD                               { return symbol(OLD                             ); }
ON                                { return symbol(ON                              ); }
ONLY                              { return symbol(ONLY                            ); }
OPEN                              { return symbol(OPEN                            ); }
OPTION                            { return symbol(OPTION                          ); }
OPTIONS                           { return symbol(OPTIONS                         ); }
OR                                { return symbol(OR                              ); }
ORDER                             { return symbol(ORDER                           ); }
ORDERING                          { return symbol(ORDERING                        ); }
ORDINALITY                        { return symbol(ORDINALITY                      ); }
OTHERS                            { return symbol(OTHERS                          ); }
OUT                               { return symbol(OUT                             ); }
OUTER                             { return symbol(OUTER                           ); }
OUTPUT                            { return symbol(OUTPUT                          ); }
OVER                              { return symbol(OVER                            ); }
OVERLAPS                          { return symbol(OVERLAPS                        ); }
OVERLAY                           { return symbol(OVERLAY                         ); }
OVERRIDING                        { return symbol(OVERRIDING                      ); }
PACKED                            { return symbol(PACKED                          ); }
PAD                               { return symbol(PAD                             ); }
PARAMETER                         { return symbol(PARAMETER                       ); }
PARAMETER_MODE                    { return symbol(PARAMETER_MODE                  ); }
PARAMETER_NAME                    { return symbol(PARAMETER_NAME                  ); }
PARAMETER_ORDINAL_POSITION        { return symbol(PARAMETER_ORDINAL_POSITION      ); }
PARAMETER_SPECIFIC_CATALOG        { return symbol(PARAMETER_SPECIFIC_CATALOG      ); }
PARAMETER_SPECIFIC_NAME           { return symbol(PARAMETER_SPECIFIC_NAME         ); }
PARAMETER_SPECIFIC_SCHEMA         { return symbol(PARAMETER_SPECIFIC_SCHEMA       ); }
PARTIAL                           { return symbol(PARTIAL                         ); }
PARTITION                         { return symbol(PARTITION                       ); }
PASCAL                            { return symbol(PASCAL                          ); }
PATH                              { return symbol(PATH                            ); }
PERCENTILE_CONT                   { return symbol(PERCENTILE_CONT                 ); }
PERCENTILE_DISC                   { return symbol(PERCENTILE_DISC                 ); }
PERCENT_RANK                      { return symbol(PERCENT_RANK                    ); }
PIC                               { return symbol(PIC                             ); }
PICTURE                           { return symbol(PICTURE                         ); }
PLACING                           { return symbol(PLACING                         ); }
PLI                               { return symbol(PLI                             ); }
POSITION                          { return symbol(POSITION                        ); }
POWER                             { return symbol(POWER                           ); }
PRECEDING                         { return symbol(PRECEDING                       ); }
PRECISION                         { return symbol(PRECISION                       ); }
PREPARE                           { return symbol(PREPARE                         ); }
PRESERVE                          { return symbol(PRESERVE                        ); }
PRIMARY                           { return symbol(PRIMARY                         ); }
PRIOR                             { return symbol(PRIOR                           ); }
PRIVILEGES                        { return symbol(PRIVILEGES                      ); }
PROCEDURE                         { return symbol(PROCEDURE                       ); }
PUBLIC                            { return symbol(PUBLIC                          ); }
RANGE                             { return symbol(RANGE                           ); }
RANK                              { return symbol(RANK                            ); }
READ                              { return symbol(READ                            ); }
READS                             { return symbol(READS                           ); }
REAL                              { return symbol(REAL                            ); }
RECURSIVE                         { return symbol(RECURSIVE                       ); }
REF                               { return symbol(REF                             ); }
REFERENCES                        { return symbol(REFERENCES                      ); }
REFERENCING                       { return symbol(REFERENCING                     ); }
REGR_AVGX                         { return symbol(REGR_AVGX                       ); }
REGR_AVGY                         { return symbol(REGR_AVGY                       ); }
REGR_COUNT                        { return symbol(REGR_COUNT                      ); }
REGR_INTERCEPT                    { return symbol(REGR_INTERCEPT                  ); }
REGR_R2                           { return symbol(REGR_R2                         ); }
REGR_SLOPE                        { return symbol(REGR_SLOPE                      ); }
REGR_SXX                          { return symbol(REGR_SXX                        ); }
REGR_SXY                          { return symbol(REGR_SXY                        ); }
REGR_SYY                          { return symbol(REGR_SYY                        ); }
RELATIVE                          { return symbol(RELATIVE                        ); }
RELEASE                           { return symbol(RELEASE                         ); }
REPEATABLE                        { return symbol(REPEATABLE                      ); }
RESTART                           { return symbol(RESTART                         ); }
RESTRICT                          { return symbol(RESTRICT                        ); }
RESULT                            { return symbol(RESULT                          ); }
RETURN                            { return symbol(RETURN                          ); }
RETURNED_CARDINALITY              { return symbol(RETURNED_CARDINALITY            ); }
RETURNED_LENGTH                   { return symbol(RETURNED_LENGTH                 ); }
RETURNED_OCTET_LENGTH             { return symbol(RETURNED_OCTET_LENGTH           ); }
RETURNED_SQLSTATE                 { return symbol(RETURNED_SQLSTATE               ); }
RETURNS                           { return symbol(RETURNS                         ); }
REVOKE                            { return symbol(REVOKE                          ); }
RIGHT                             { return symbol(RIGHT                           ); }
ROLE                              { return symbol(ROLE                            ); }
ROLLBACK                          { return symbol(ROLLBACK                        ); }
ROLLUP                            { return symbol(ROLLUP                          ); }
ROUTINE                           { return symbol(ROUTINE                         ); }
ROUTINE_CATALOG                   { return symbol(ROUTINE_CATALOG                 ); }
ROUTINE_NAME                      { return symbol(ROUTINE_NAME                    ); }
ROUTINE_SCHEMA                    { return symbol(ROUTINE_SCHEMA                  ); }
ROW                               { return symbol(ROW                             ); }
ROWS                              { return symbol(ROWS                            ); }
ROW_COUNT                         { return symbol(ROW_COUNT                       ); }
ROW_NUMBER                        { return symbol(ROW_NUMBER                      ); }
SAVEPOINT                         { return symbol(SAVEPOINT                       ); }
SCALE                             { return symbol(SCALE                           ); }
SCHEMA                            { return symbol(SCHEMA                          ); }
SCHEMA_NAME                       { return symbol(SCHEMA_NAME                     ); }
SCOPE                             { return symbol(SCOPE                           ); }
SCOPE_CATALOG                     { return symbol(SCOPE_CATALOG                   ); }
SCOPE_NAME                        { return symbol(SCOPE_NAME                      ); }
SCOPE_SCHEMA                      { return symbol(SCOPE_SCHEMA                    ); }
SCROLL                            { return symbol(SCROLL                          ); }
SEARCH                            { return symbol(SEARCH                          ); }
SECOND                            { return symbol(SECOND                          ); }
SECTION                           { return symbol(SECTION                         ); }
SECURITY                          { return symbol(SECURITY                        ); }
SELECT                            { return symbol(SELECT                          ); }
SELF                              { return symbol(SELF                            ); }
SENSITIVE                         { return symbol(SENSITIVE                       ); }
SEPARATE                          { return symbol(SEPARATE                        ); }
SEQUENCE                          { return symbol(SEQUENCE                        ); }
SERIALIZABLE                      { return symbol(SERIALIZABLE                    ); }
SERVER_NAME                       { return symbol(SERVER_NAME                     ); }
SESSION                           { return symbol(SESSION                         ); }
SESSION_USER                      { return symbol(SESSION_USER                    ); }
SET                               { return symbol(SET                             ); }
SETS                              { return symbol(SETS                            ); }
SIGN                              { return symbol(SIGN                            ); }
SIMILAR                           { return symbol(SIMILAR                         ); }
SIMPLE                            { return symbol(SIMPLE                          ); }
SIZE                              { return symbol(SIZE                            ); }
SMALLINT                          { return symbol(SMALLINT                        ); }
SOME                              { return symbol(SOME                            ); }
SOURCE                            { return symbol(SOURCE                          ); }
SPACE                             { return symbol(SPACE                           ); }
SPECIFIC                          { return symbol(SPECIFIC                        ); }
SPECIFICTYPE                      { return symbol(SPECIFICTYPE                    ); }
SPECIFIC_NAME                     { return symbol(SPECIFIC_NAME                   ); }
SQL                               { return symbol(SQL                             ); }
SQLEXCEPTION                      { return symbol(SQLEXCEPTION                    ); }
SQLSTATE                          { return symbol(SQLSTATE                        ); }
SQLSTATE_TYPE                     { return symbol(SQLSTATE_TYPE                   ); }
SQLWARNING                        { return symbol(SQLWARNING                      ); }
SQRT                              { return symbol(SQRT                            ); }
START                             { return symbol(START                           ); }
STATE                             { return symbol(STATE                           ); }
STATEMENT                         { return symbol(STATEMENT                       ); }
STATIC                            { return symbol(STATIC                          ); }
STDDEV_POP                        { return symbol(STDDEV_POP                      ); }
STDDEV_SAMP                       { return symbol(STDDEV_SAMP                     ); }
STRUCTURE                         { return symbol(STRUCTURE                       ); }
STYLE                             { return symbol(STYLE                           ); }
SUBCLASS_ORIGIN                   { return symbol(SUBCLASS_ORIGIN                 ); }
SUBMULTISET                       { return symbol(SUBMULTISET                     ); }
SUBSTRING                         { return symbol(SUBSTRING                       ); }
SUM                               { return symbol(SUM                             ); }
SYMMETRIC                         { return symbol(SYMMETRIC                       ); }
SYSTEM                            { return symbol(SYSTEM                          ); }
SYSTEM_USER                       { return symbol(SYSTEM_USER                     ); }
TABLE                             { return symbol(TABLE                           ); }
TABLESAMPLE                       { return symbol(TABLESAMPLE                     ); }
TEMPORARY                         { return symbol(TEMPORARY                       ); }
THEN                              { return symbol(THEN                            ); }
TIES                              { return symbol(TIES                            ); }
TIME                              { return symbol(TIME                            ); }
TIMESTAMP                         { return symbol(TIMESTAMP                       ); }
TIMEZONE_HOUR                     { return symbol(TIMEZONE_HOUR                   ); }
TIMEZONE_MINUTE                   { return symbol(TIMEZONE_MINUTE                 ); }
TO                                { return symbol(TO                              ); }
TOP_LEVEL_COUNT                   { return symbol(TOP_LEVEL_COUNT                 ); }
TRAILING                          { return symbol(TRAILING                        ); }
TRANSACTION                       { return symbol(TRANSACTION                     ); }
TRANSACTIONS_COMMITTED            { return symbol(TRANSACTIONS_COMMITTED          ); }
TRANSACTIONS_ROLLED_BACK          { return symbol(TRANSACTIONS_ROLLED_BACK        ); }
TRANSACTION_ACTIVE                { return symbol(TRANSACTION_ACTIVE              ); }
TRANSFORM                         { return symbol(TRANSFORM                       ); }
TRANSFORMS                        { return symbol(TRANSFORMS                      ); }
TRANSLATE                         { return symbol(TRANSLATE                       ); }
TRANSLATION                       { return symbol(TRANSLATION                     ); }
TREAT                             { return symbol(TREAT                           ); }
TRIGGER                           { return symbol(TRIGGER                         ); }
TRIGGER_CATALOG                   { return symbol(TRIGGER_CATALOG                 ); }
TRIGGER_NAME                      { return symbol(TRIGGER_NAME                    ); }
TRIGGER_SCHEMA                    { return symbol(TRIGGER_SCHEMA                  ); }
TRIM                              { return symbol(TRIM                            ); }
TRUE                              { return symbol(TRUE                            ); }
TYPE                              { return symbol(TYPE                            ); }
UESCAPE                           { return symbol(UESCAPE                         ); }
UNBOUNDED                         { return symbol(UNBOUNDED                       ); }
UNCOMMITTED                       { return symbol(UNCOMMITTED                     ); }
UNDER                             { return symbol(UNDER                           ); }
UNION                             { return symbol(UNION                           ); }
UNIQUE                            { return symbol(UNIQUE                          ); }
UNKNOWN                           { return symbol(UNKNOWN                         ); }
UNNAMED                           { return symbol(UNNAMED                         ); }
UNNEST                            { return symbol(UNNEST                          ); }
UPDATE                            { return symbol(UPDATE                          ); }
UPPER                             { return symbol(UPPER                           ); }
USAGE                             { return symbol(USAGE                           ); }
USER                              { return symbol(USER                            ); }
USER_DEFINED_TYPE_CATALOG         { return symbol(USER_DEFINED_TYPE_CATALOG       ); }
USER_DEFINED_TYPE_CODE            { return symbol(USER_DEFINED_TYPE_CODE          ); }
USER_DEFINED_TYPE_NAME            { return symbol(USER_DEFINED_TYPE_NAME          ); }
USER_DEFINED_TYPE_SCHEMA          { return symbol(USER_DEFINED_TYPE_SCHEMA        ); }
USING                             { return symbol(USING                           ); }
VALUE                             { return symbol(VALUE                           ); }
VALUES                            { return symbol(VALUES                          ); }
VARCHAR                           { return symbol(VARCHAR                         ); }
VARYING                           { return symbol(VARYING                         ); }
VAR_POP                           { return symbol(VAR_POP                         ); }
VAR_SAMP                          { return symbol(VAR_SAMP                        ); }
VIEW                              { return symbol(VIEW                            ); }
WHEN                              { return symbol(WHEN                            ); }
WHENEVER                          { return symbol(WHENEVER                        ); }
WHERE                             { return symbol(WHERE                           ); }
WIDTH_BUCKET                      { return symbol(WIDTH_BUCKET                    ); }
WINDOW                            { return symbol(WINDOW                          ); }
WITH                              { return symbol(WITH                            ); }
WITHIN                            { return symbol(WITHIN                          ); }
WITHOUT                           { return symbol(WITHOUT                         ); }
WORK                              { return symbol(WORK                            ); }
WRITE                             { return symbol(WRITE                           ); }
YEAR                              { return symbol(YEAR                            ); }
ZONE                              { return symbol(ZONE                            ); }

"<>"                   { return symbol(NE);      }
">="                   { return symbol(GE);      }
"<="                   { return symbol(LE);      }
\"                     { return symbol(DQOUOTE); }
\%                     { return symbol(PERCENT); }
\&                     { return symbol(AMPERSAND); }
\'                     { return symbol(QUOTE); }

*/


/*
\(                     { return symbol(LPAR); }
\)                     { return symbol(RPAR); }



// Comemos los parentesis para evitar los anidamientos 
\(                     { /* do nothing */ }
\)                     { /* do nothing */ }


\*                     { return symbol(ASTERISK); }
\+                     { return symbol(PLUS); }
\,                     { return symbol(COMMA); }
\-                     { return symbol(MINUS); }
\.                     { return symbol(DOT); }
\/                     { return symbol(SLASH); }
\:                     { return symbol(COLON); }
\;                     { return symbol(SEMICOLON); }
\<                     { return symbol(LT); } 
\=                     { return symbol(EQUAL); }    
\>                     { return symbol(GT); }
\?                     { return symbol(QUESTION); }
\[                     { return symbol(LCOR); }
"??("                  { return symbol(LCOR); }
\]                     { return symbol(RCOR); }
"??)"                  { return symbol(RCOR); }
\^                     { return symbol(CIRCUMFLEX); }
\_                     { return symbol(UNDERSCORE); }
\|                     { return symbol(VBAR); }
\{                     { return symbol(LBRA); }
\}                     { return symbol(RBRA); }

*/



// {ID_FULL}              { return symbol(ID_FULL); }

/******************************************************************************/
/******************************************************************************/
/***                               otros                                    ***/
/******************************************************************************/
/******************************************************************************/

/*
 * Los literales pueden ser con comillas simples o dobles
 * Puede ser "O'Donell" o bien 'dijo: "Hola"'
 * Por eso hay que distinguir las dos formas
 */
 

<QUOTE_STRING> {
  \'               { if (litQuote == true) {
                         popState();
                         return literal("");
                     }
                     else {
                        cadena.append(yytext());
                     }    
                   }
  \"               { if (litQuote == false) {
                         popState();
                         return literal("");
                     }
                     else {
                        cadena.append(yytext());
                     }    
                   }

  \n               { module.incLines(data);
                     data = false;
                     popState(); 
                   }
  \r               {  }

   .               { cadena.append(yytext()); }
}

