package com.jgg.sdp.parser.sql;

import java.util.*;
import java_cup.runtime.Symbol;
import com.jgg.sdp.parser.base.*;
import static com.jgg.sdp.parser.sql.SQLSym.*;

%%

%public
%class      SQLLexer
%extends    GenericLexer
// %implements ILexer

%line
%column
%char
%full
%ignorecase
%cup

%xstate QUOTE_STRING, DQUOTE_STRING

%{

   Stack<Integer> pars = new Stack<Integer>();
   HashSet<Integer> words = new HashSet<Integer>();
      
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
   words.add(LPAR);
   words.add(WHERE);
   words.add(WHEN);   
   words.add(AND);
   words.add(OR);
%init}

%eofval{
    return symbolFactory.newSymbol("EOF", EOF);
%eofval}


SPACES=[\ \t]+
ID = [a-zA-Z][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*
ID2 = \.{ID}
HID=[a-zA-Z0-9][a-zA-Z0-9\_\-\#]*[\.[a-zA-Z0-9\_\-]+]*

ENTERO=[0-9]+
SIGNED=[+-]{1}{ENTERO}
DECNUM=[+|-]?[0-9]+[\.]?[0-9]+
DECFLOAT={DECNUM}[Ee]{ENTERO}
NUMBIN=[Bb][Xx]
//\'[0-9A-Fa-f]+\'
NUMGRAPHIC=[UuGg][Xx]
// \'[0-9A-Fa-f]+\'

HOSTVAR  = :[ ]*{HID}

ATTR = MONTHS
     | MONTH
     | YEARS
     | YEAR
     | {HOSTVAR}
     
     
HOSTVAR_ATTR = {HOSTVAR}{SPACES}{ATTR}

%% 

  \xB5          { /* eat */ }

 {HOSTVAR_ATTR}    { return symbol(HOSTVAR_ATTR); }
 {HOSTVAR}         { return symbol(HOSTVAR);      } 
  
 ADMIN_TASK_LIST                                                      { return symbol(ADMIN_TASK_LIST);} 
 ADMIN_TASK_OUTPUT                                                    { return symbol(ADMIN_TASK_OUTPUT);} 
 ADMIN_TASK_STATUS                                                    { return symbol(ADMIN_TASK_STATUS);} 
 AUTHORIZATION                                                        { return symbol(AUTHORIZATION);}                                                                      
 ACCELERATION                                                         { return symbol(ACCELERATION);}
 APPLICATION                                                          { return symbol(APPLICATION);} 
 ASENSITIVE                                                           { return symbol(ASENSITIVE);}
 ADD_MONTHS                                                           { return symbol(ADD_MONTHS);}    
 ASSIGNMENT                                                           { return symbol(ASSIGNMENT);}                                                                      
 ASYMMETRIC                                                           { return symbol(ASYMMETRIC);}                                                                      
 ATTRIBUTES                                                           { return symbol(ATTRIBUTES);}                                                                                                                
 ASSERTION                                                            { return symbol(ASSERTION);}                                                                      
 ASSOCIATE                                                            { return symbol(ASSOCIATE);} 
 ATTRIBUTE                                                            { return symbol(ATTRIBUTE);}                                                                      
 AUXILIARY                                                            { return symbol(AUXILIARY);} 
 ABSOLUTE                                                             { return symbol(ABSOLUTE);}                                                                      
 ALLOCATE                                                             { return symbol(ALLOCATE);}
 ASUTIME                                                              { return symbol(ASUTIME);}                                                                           
 ACTION                                                               { return symbol(ACTION);}                                                                      
 ALWAYS                                                               { return symbol(ALWAYS);}                                                                      
 ALLOW                                                                { return symbol(ALLOW);}
 ATOMIC                                                               { return symbol(ATOMIC);}                                                                      
 ADMIN                                                                { return symbol(ADMIN);}                                                                      
 AFTER                                                                { return symbol(AFTER);}                                                                      
 ALTER                                                                { return symbol(ALTER);}   
 ASCII                                                                { return symbol(ASCII);}                                                                      
 ASCII_CHR                                                            { return symbol(ASCII_CHR);} 
 ASCII_STR                                                            { return symbol(ASCII_STR);}
 ALIAS                                                                { return symbol(ALIAS);} 
 ASIN                                                                 { return symbol(ASIN);}
 ATAN                                                                 { return symbol(ATAN);}
 ATANH                                                                { return symbol(ATANH);} 
 ATAN2                                                                { return symbol(ATAN2);} 
 ARRAY                                                                { return symbol(ARRAY);}
 AUDIT                                                                { return symbol(AUDIT);} 
 ACOS                                                                 { return symbol(ACOS);}                                                                        
 ABS                                                                  { return symbol(ABS);}                                                                      
 ADA                                                                  { return symbol(ADA);}                                                                      
 ADD                                                                  { return symbol(ADD);}
 AGE                                                                  { return symbol(AGE);}                                                                       
 ALL                                                                  { return symbol(ALL);}                                                                      
 AND                                                                  { return symbol(AND);}                                                                      
 ANY                                                                  { return symbol(ANY);}                                                                      
 ARE                                                                  { return symbol(ARE);}
 AUX                                                                  { return symbol(AUX);}
 ASC                                                                  { return symbol(ASC);}                                                                      
 AVG                                                                  { return symbol(AVG);}                                                                      
 AS                                                                   { return symbol(AS);}                                                                      
 AT                                                                   { return symbol(AT);}                                                                      
// A                                                                    { return symbol(A);}                                                                      

 BUSINESS_TIME                                                        { return symbol(BUSINESS_TIME);}
 BUFFERPOOL                                                           { return symbol(BUFFERPOOL);}
 BERNOULLI                                                            { return symbol(BERNOULLI);}    
 BETWEEN                                                              { return symbol(BETWEEN);}                                                                      
 BOOLEAN                                                              { return symbol(BOOLEAN);}                                                                      
 BREADTH                                                              { return symbol(BREADTH);}                                                                      
 BEFORE                                                               { return symbol(BEFORE);}                                                                      
 BIGINT                                                               { return symbol(BIGINT);}                                                                      
 BINARY                                                               { return symbol(BINARY);}                                                                      
 BEGIN                                                                { return symbol(BEGIN);}                                                                      
 BLOB                                                                 { return symbol(BLOB);}                                                                      
 BOTH                                                                 { return symbol(BOTH);}                                                                      
 BY                                                                   { return symbol(BY);}                                                                      
 BITAND                                                               { return symbol(BITAND);}   
 BITOR                                                                { return symbol(BITOR);} 
 BITANDNOT                                                            { return symbol(BITANDNOT);}
 BITXOR                                                               { return symbol(BITXOR);}
 BITNOT                                                               { return symbol(BITNOT);}

 CURRENT_TRANSFORM_GROUP_FOR_TYPE                                     { return symbol(CURRENT_TRANSFORM_GROUP_FOR_TYPE);}                                                                      
 CURRENT_DEFAULT_TRANSFORM_GROUP                                      { return symbol(CURRENT_DEFAULT_TRANSFORM_GROUP);}
 COMMAND_FUNCTION_CODE                                                { return symbol(COMMAND_FUNCTION_CODE);}                                                                      
 CHARACTER_SET_CATALOG                                                { return symbol(CHARACTER_SET_CATALOG);}                                                                      
 CHARACTER_SET_SCHEMA                                                 { return symbol(CHARACTER_SET_SCHEMA);}                                                                       
 CHARACTER_SET_NAME                                                   { return symbol(CHARACTER_SET_NAME);}                                                                      
 CONSTRAINT_CATALOG                                                   { return symbol(CONSTRAINT_CATALOG);} 
 COLLATION_CATALOG                                                    { return symbol(COLLATION_CATALOG);}                                                                      
 CONSTRAINT_SCHEMA                                                    { return symbol(CONSTRAINT_SCHEMA);}                                                                      
 CURRENT_COLLATION                                                    { return symbol(CURRENT_COLLATION);}                                                                      
 CURRENT_TIMESTAMP                                                    { return symbol(CURRENT_TIMESTAMP);}                                                                      
 CLIENT_WRKSTNNAME                                                    { return symbol(CLIENT_WRKSTNNAME);}
 CURRENT_LC_CTYPE                                                     { return symbol(CURRENT_LC_CTYPE);}
 COMPARE_DECFLOAT                                                     { return symbol(COMPARE_DECFLOAT);}
 COMMAND_FUNCTION                                                     { return symbol(COMMAND_FUNCTION);}                                                                      
 CHARACTER_LENGTH                                                     { return symbol(CHARACTER_LENGTH);}                                                                      
 COLLATION_SCHEMA                                                     { return symbol(COLLATION_SCHEMA);}                                                                      
 CONDITION_NUMBER                                                     { return symbol(CONDITION_NUMBER);}                                                                      
 CHARACTERISTICS                                                      { return symbol(CHARACTERISTICS);}
 CLIENT_APPLNAME                                                      { return symbol(CLIENT_APPLNAME);}  
 CONNECTION_NAME                                                      { return symbol(CONNECTION_NAME);}                                                                      
 CONSTRAINT_NAME                                                      { return symbol(CONSTRAINT_NAME);}                                                                      
 COVARIANCE_SAMP                                                      { return symbol(COVARIANCE_SAMP);}
 CURRENT_LC_TYPE                                                      { return symbol(CURRENT_LC_TYPE);}
 COLLATION_NAME                                                       { return symbol(COLLATION_NAME);}  
 CCSID_ENCODING                                                       { return symbol(CCSID_ENCODING);}     
 CURRENT_SCHEMA                                                       { return symbol(CURRENT_SCHEMA);}
 CLIENT_ACCTNG                                                        { return symbol(CLIENT_ACCTNG);}
 CLIENT_USERID                                                        { return symbol(CLIENT_USERID);}
 COLLATION_KEY                                                        { return symbol(COLLATION_KEY);}
 CORRESPONDING                                                        { return symbol(CORRESPONDING);}                                                                      
 CATALOG_NAME                                                         { return symbol(CATALOG_NAME);}                                                                      
 CLASS_ORIGIN                                                         { return symbol(CLASS_ORIGIN);} 
 CONSTRUCTORS                                                         { return symbol(CONSTRUCTORS);}
 CURRENT_DATE                                                         { return symbol(CURRENT_DATE);}                                                                      
 CURRENT_PATH                                                         { return symbol(CURRENT_PATH);}                                                                      
 CURRENT_ROLE                                                         { return symbol(CURRENT_ROLE);}                                                                      
 CURRENT_TIME                                                         { return symbol(CURRENT_TIME);}                                                                      
 CURRENT_USER                                                         { return symbol(CURRENT_USER);}
 CARDINALITY                                                          { return symbol(CARDINALITY);}                                                                      
 CHAR_LENGTH                                                          { return symbol(CHAR_LENGTH);}                                                                      
 CODEUNITS16                                                          { return symbol(CODEUNITS16);}
 CODEUNITS32                                                          { return symbol(CODEUNITS32);}
 COLUMN_NAME                                                          { return symbol(COLUMN_NAME);}                                                                      
 CONSTRAINTS                                                          { return symbol(CONSTRAINTS);}
 CORRELATION                                                          { return symbol(CORRELATION);}                                                                       
 CURSOR_NAME                                                          { return symbol(CURSOR_NAME);}
 CHARACTERS                                                           { return symbol(CHARACTERS);}                                                                      
 CODE_UNITS                                                           { return symbol(CODE_UNITS);}
 COLLECTION                                                           { return symbol(COLLECTION);}                                                                       
 CONNECTION                                                           { return symbol(CONNECTION);}
 CONSTRAINT                                                           { return symbol(CONSTRAINT);}                                                                      
 COVAR_SAMP                                                           { return symbol(COVAR_SAMP);}                                                                      
 COVARIANCE                                                           { return symbol(COVARIANCE);}
 CHARACTER                                                            { return symbol(CHARACTER);}                                                                      
 COLLATION                                                            { return symbol(COLLATION);}                                                                      
 COMMITTED                                                            { return symbol(COMMITTED);}                                                                      
 CONDITION                                                            { return symbol(CONDITION);}                                                                      
 COVAR_POP                                                            { return symbol(COVAR_POP);}   
 COUNT_BIG                                                            { return symbol(COUNT_BIG);}
 CASCADED                                                             { return symbol(CASCADED);}
 COMPRESS                                                             { return symbol(COMPRESS);}
 COALESCE                                                             { return symbol(COALESCE);}                                                                      
 CONTAINS                                                             { return symbol(CONTAINS);}                                                                      
 CONTINUE                                                             { return symbol(CONTINUE);}
 CUME_DIST                                                            { return symbol(CUME_DIST);}
 CHANGED                                                              { return symbol(CHANGED);}
 CHECKED                                                              { return symbol(CHECKED);}                                                                      
 COLLATE                                                              { return symbol(COLLATE);}                                                                      
 COLLECT                                                              { return symbol(COLLECT);}                                                                      
 CONNECT                                                              { return symbol(CONNECT);}                                                                      
 CASCADE                                                              { return symbol(CASCADE);}                                                                      
 CATALOG                                                              { return symbol(CATALOG);}                                                                      
 CEILING                                                              { return symbol(CEILING);}
 CLUSTER                                                              { return symbol(CLUSTER);} 
 CONTEXT                                                              { return symbol(CONTEXT);}                                                                      
 CONVERT                                                              { return symbol(CONVERT);}                                                                      
 COMMENT                                                              { return symbol(COMMENT);} 
 CONTENT                                                              { return symbol(CONTENT);} 
 CURRENT                                                              { return symbol(CURRENT);}
 CURRVAL                                                              { return symbol(CURRVAL);} 
 CURSORS                                                              { return symbol(CURSORS);}
 CAPTURE                                                              { return symbol(CAPTURE);}
 CALLED                                                               { return symbol(CALLED);}
 COMMIT                                                               { return symbol(COMMIT);}                                                                      
 COLLID                                                               { return symbol(COLLID);}                                                                      
 COLUMN                                                               { return symbol(COLUMN);}
 CREATE                                                               { return symbol(CREATE);}                                                                      
 CURSOR\-{ID}                                                         { return symbol(ID);}
 CURSOR                                                               { return symbol(CURSOR);} 
 CCSID                                                                { return symbol(CCSID);}
 CHAIN                                                                { return symbol(CHAIN);}                                                                      
 CHECK                                                                { return symbol(CHECK);}                                                                      
 CLONE                                                                { return symbol(CLONE);}
 CLOSE                                                                { return symbol(CLOSE);}                                                                      
 COBOL                                                                { return symbol(COBOL);}                                                                      
 COUNT                                                                { return symbol(COUNT);}                                                                      
 CROSS                                                                { return symbol(CROSS);}                                                                      
 CYCLE                                                                { return symbol(CYCLE);}
 COSH                                                                 { return symbol(COSH);}
 CALL                                                                 { return symbol(CALL);}                                                                      
 CASE                                                                 { return symbol(CASE);}                                                                      
 CAST                                                                 { return symbol(CAST);}                                                                      
 CEIL                                                                 { return symbol(CEIL);}                                                                      
 CHAR                                                                 { return symbol(CHAR);}                                                                      
 CLOB                                                                 { return symbol(CLOB);}
 COPY                                                                 { return symbol(COPY);}
 CORR                                                                 { return symbol(CORR);}                                                                      
 CUBE                                                                 { return symbol(CUBE);}
 COS                                                                  { return symbol(COS);}
 CS                                                                   { return symbol(CS);} 
   
 DATETIME_INTERVAL_PRECISION                                          { return symbol(DATETIME_INTERVAL_PRECISION);}                                                                                                     
 DATETIME_INTERVAL_CODE                                               { return symbol(DATETIME_INTERVAL_CODE);}                                                                      
 DYNAMIC_FUNCTION_CODE                                                { return symbol(DYNAMIC_FUNCTION_CODE);}                                                                      
 DYNAMIC_FUNCTION                                                     { return symbol(DYNAMIC_FUNCTION);}                                                                      
 DETERMINISTIC                                                        { return symbol(DETERMINISTIC);}
 DAYOFYEAR                                                            { return symbol(DAYOFYEAR);}
 DAYOFMONTH                                                           { return symbol(DAYOFMONTH);}
 DAYOFWEEK_ISO                                                        { return symbol(DAYOFWEEK_ISO);}                                                                      
 DAYOFWEEK                                                            { return symbol(DAYOFWEEK);}
 DIAGNOSTICS                                                          { return symbol(DIAGNOSTICS);}                                                                      
 DEALLOCATE                                                           { return symbol(DEALLOCATE);}                                                                      
 DEFERRABLE                                                           { return symbol(DEFERRABLE);}                                                                      
 DENSE_RANK                                                           { return symbol(DENSE_RANK);}                                                                      
 DESCRIPTOR                                                           { return symbol(DESCRIPTOR);}                                                                      
 DISCONNECT                                                           { return symbol(DISCONNECT);}
 DATABASE                                                             { return symbol(DATABASE);}                                                                      
 DEFAULTS                                                             { return symbol(DEFAULTS);}                                                                      
 DECFLOAT                                                             { return symbol(DECFLOAT);}
 DEFERRED                                                             { return symbol(DEFERRED);}                                                                      
 DESCRIBE                                                             { return symbol(DESCRIBE);}                                                                      
 DISPATCH                                                             { return symbol(DISPATCH);}                                                                      
 DISTINCT                                                             { return symbol(DISTINCT);}                                                                      
 DOCUMENT                                                             { return symbol(DOCUMENT);}                                                                     
 DECIMAL                                                              { return symbol(DECIMAL);}                                                                      
 DECLARE                                                              { return symbol(DECLARE);}                                                                      
 DEFAULT                                                              { return symbol(DEFAULT);}                                                                      
 DEFINED                                                              { return symbol(DEFINED);}                                                                      
 DEFINER                                                              { return symbol(DEFINER);}                                                                      
 DERIVED                                                              { return symbol(DERIVED);}                                                                      
 DISABLE                                                              { return symbol(DISABLE);}
 DYNAMIC                                                              { return symbol(DYNAMIC);}                                                                                                                                           
 DOMAIN                                                               { return symbol(DOMAIN);}                                                                      
 DOUBLE                                                               { return symbol(DOUBLE);}                                                                      
 DEGREE                                                               { return symbol(DEGREE);}                                                                      
 DELETE                                                               { return symbol(DELETE);}           
 DSSIZE                                                               { return symbol(DSSIZE);} 
 DEBUG                                                                { return symbol(DEBUG);}
 DEPTH                                                                { return symbol(DEPTH);}                                                                      
 DEREF                                                                { return symbol(DEREF);}                                                                      
 DATA                                                                 { return symbol(DATA);}                                                                      
 DATE                                                                 { return symbol(DATE);}                                                                      
 DESC                                                                 { return symbol(DESC);}                                                                      
 DROP                                                                 { return symbol(DROP);}
 DCBLOB                                                               { return symbol(DCBLOB);}                                                                         
 DAY[Ss]?                                                             { return symbol(DAY);}                                                                      
 DEC                                                                  { return symbol(DEC);}   
 DBINFO                                                               { return symbol(DBINFO);}
 DECODE                                                               { return symbol(DECODE);}
 DECFLOAT_FORMAT                                                      { return symbol(DECFLOAT_FORMAT);}            
 DECFLOAT_SORTKEY                                                     { return symbol(DECFLOAT_SORTKEY);}
 DECRYPT_BINARY                                                       { return symbol(DECRYPT_BINARY);}
 DECRYPT_BIT                                                          { return symbol(DECRYPT_BIT);}
 DECRYPT_CHAR                                                         { return symbol(DECRYPT_CHAR);}
 DECRYPT_DB                                                           { return symbol(DECRYPT_DB);}  
 DEGREES                                                              { return symbol(DEGREES);}  
 DIFFERENCE                                                           { return symbol(DIFFERENCE);} 
 DISALLOW                                                             { return symbol(DISALLOW);} 
 DIGITS                                                               { return symbol(DIGITS);} 
 DOUBLE_PRECISION                                                     { return symbol(DOUBLE_PRECISION);}
 DSN_XMLVALIDATE                                                      { return symbol(DSN_XMLVALIDATE);}
 DO                                                                   { return symbol(DO);}
 
   
 EBCDIC_CHR                                                           { return symbol(EBCDIC_CHR);}
 EBCDIC_STR                                                           { return symbol(EBCDIC_STR);}
 ELIGIBLE                                                             { return symbol(ELIGIBLE);}
 ELEMENT                                                              { return symbol(ELEMENT);}
 ENABLE                                                               { return symbol(ENABLE);}
 ENCRYPT_TDES                                                         { return symbol(ENCRYPT_TDES);} 
 ENCRYPTION                                                           { return symbol(ENCRYPTION);}              
 EXCEPTION                                                            { return symbol(EXCEPTION);}                                                                      
 EXCLUDING                                                            { return symbol(EXCLUDING);}
 EXCLUSIVE                                                            { return symbol(EXCLUSIVE);}                
 EDITPROC                                                             { return symbol(EDITPROC);}
 END-EXEC                                                             { return symbol(ENDEXEC);} 
 ENCODING                                                             { return symbol(ENCODING);} 
 EXCHANGE                                                             { return symbol(EXCHANGE);}
 EXTERNAL                                                             { return symbol(EXTERNAL);} 
 EXPLAIN                                                              { return symbol(EXPLAIN);}                                                                      
 EXCLUDE                                                              { return symbol(EXCLUDE);}                                                                      
 EXECUTE                                                              { return symbol(EXECUTE);}                                                                      
 EXTRACT                                                              { return symbol(EXTRACT);}
 ELSEIF                                                               { return symbol(ELSEIF);}
 ENDING                                                               { return symbol(ENDING);}
 EQUALS                                                               { return symbol(EQUALS);}
 ESCAPE                                                               { return symbol(ESCAPE);}                                                                      
 EXCEPT                                                               { return symbol(EXCEPT);}                                                                      
 EXISTS                                                               { return symbol(EXISTS);}                                                                      
 ERASE                                                                { return symbol(ERASE);}
 EVERY                                                                { return symbol(EVERY);}                                                                      
 EXEC                                                                 { return symbol(EXEC);}                                                                      
 EACH                                                                 { return symbol(EACH);}                                                                      
 ELSE                                                                 { return symbol(ELSE);}
 EXIT                                                                 { return symbol(EXIT);}                                                                       
 END                                                                  { return symbol(END);}                                                                      
 EXP                                                                  { return symbol(EXP);}                                                                      

 FALLBACK                                                             { return symbol(FALLBACK);}                                                                                  
 FIELDPROC                                                            { return symbol(FIELDPROC);}
 FOLLOWING                                                            { return symbol(FOLLOWING);}
 FREEPAGE                                                             { return symbol(FREEPAGE);}
 FUNCTION                                                             { return symbol(FUNCTION);}
 FOREIGN                                                              { return symbol(FOREIGN);}                                                                      
 FORTRAN                                                              { return symbol(FORTRAN);}
 FENCED                                                               { return symbol(FENCED);}                                                                       
 FILTER                                                               { return symbol(FILTER);}                                                                      
 FUSION                                                               { return symbol(FUSION);}                                                                      
 FALSE                                                                { return symbol(FALSE);}                                                                      
 FETCH                                                                { return symbol(FETCH);}                                                                      
 FINAL                                                                { return symbol(FINAL);}                                                                      
 FIRST                                                                { return symbol(FIRST);}                                                                      
 FLOAT                                                                { return symbol(FLOAT);}                                                                      
 FLOOR                                                                { return symbol(FLOOR);}                                                                      
 FOUND                                                                { return symbol(FOUND);}                                                                      
 FREE                                                                 { return symbol(FREE);}                                                                      
 FROM                                                                 { return symbol(FROM);}                                                                      
 FULL                                                                 { return symbol(FULL);}                                                                      
 FOR                                                                  { return symbol(FOR);}                                                                      
 
 GENERATE_UNIQUE_BINARY                                               { return symbol(GENERATE_UNIQUE_BINARY);} 
 GETVARIABLE                                                          { return symbol(GETVARIABLE);}
 GENERATE_UNIQUE                                                      { return symbol(GENERATE_UNIQUE);}
 GET_ACCEL_ARCHIVE                                                    { return symbol(GET_ACCEL_ARCHIVE);}
 GENERATED                                                            { return symbol(GENERATED);}
 GETHINT                                                              { return symbol(GETHINT);} 
 GRAPHIC                                                              { return symbol(GRAPHIC);}
 GBPCACHE                                                             { return symbol(GBPCACHE);}                     
 GROUPING                                                             { return symbol(GROUPING);}                                                                      
 GENERAL                                                              { return symbol(GENERAL);}                                                                      
 GRANTED                                                              { return symbol(GRANTED);}                                                                      
 GLOBAL                                                               { return symbol(GLOBAL);}                                                                      
 GRANT                                                                { return symbol(GRANT);}                                                                      
 GROUP                                                                { return symbol(GROUP);}
 GO[ ]+TO                                                             { return symbol(GOTO);}
 GOTO                                                                 { return symbol(GOTO);}                                                                      
 GET                                                                  { return symbol(GET);}                                                                      
 GO                                                                   { return symbol(GO);}
                                                                                  
 HIERARCHY                                                            { return symbol(HIERARCHY);}    
 HANDLER                                                              { return symbol(HANDLER);}                                                                                                             
 HAVING                                                               { return symbol(HAVING);}                                                                      
 HEX                                                                  { return symbol(HEX);}  
 HINT                                                                 { return symbol(HINT);}  
 HOLD                                                                 { return symbol(HOLD);}                                                                      
 HOUR[Ss]?                                                            { return symbol(HOUR);}                                                                      

 IDENTITY_VAL_LOCAL                                                   { return symbol(IDENTITY_VAL_LOCAL);}
 IMPLEMENTATION                                                       { return symbol(IMPLEMENTATION);}                                                                      
 INSTANTIABLE                                                         { return symbol(INSTANTIABLE);}                                                                      
 INTERSECTION                                                         { return symbol(INTERSECTION);}                                                                      
 INSENSITIVE                                                          { return symbol(INSENSITIVE);}                                                                      
 IMMEDIATE                                                            { return symbol(IMMEDIATE);}                                                                      
 INCLUDING                                                            { return symbol(INCLUDING);}
 INCLUSIVE                                                            { return symbol(INCLUSIVE);}
 INCREMENT                                                            { return symbol(INCREMENT);}                                                                      
 INDICATOR                                                            { return symbol(INDICATOR);}                                                                      
 INITIALLY                                                            { return symbol(INITIALLY);}                                                                      
 ISOLATION                                                            { return symbol(ISOLATION);}                                                                      
 INTERSECT                                                            { return symbol(INTERSECT);}
 IDENTITY                                                             { return symbol(IDENTITY);}                                                                      
 INSTANCE                                                             { return symbol(INSTANCE);}                                                                      
 INTERVAL                                                             { return symbol(INTERVAL);}
 INCLUDE                                                              { return symbol(INCLUDE);}
 INHERIT                                                              { return symbol(INHERIT);}
 INTEGER                                                              { return symbol(INTEGER);}                                                                      
 INVOKER                                                              { return symbol(INVOKER);}                                                                      
 ITERATE                                                              { return symbol(ITERATE);} 
 INSERT                                                               { return symbol(INSERT);}
 INDEX                                                                { return symbol(INDEX);}                                                                      
 INNER                                                                { return symbol(INNER);} 
 IFNULL                                                               { return symbol(IFNULL);}                                                            
 ISOBID                                                               { return symbol(ISOBID);}  
 INOUT                                                                { return symbol(INOUT);}                                                                      
 INPUT                                                                { return symbol(INPUT);}                                                                      
 INTO                                                                 { return symbol(INTO);}                                                                      
 INT                                                                  { return symbol(INTEGER);}
 IF                                                                   { return symbol(IF);}
 IN                                                                   { return symbol(IN);}                                                                      
 IS                                                                   { return symbol(IS);}                                                                      
 
 JULIAN_DAY                                                           { return symbol(JULIAN_DAY);}                                                                                 
 JOIN                                                                 { return symbol(JOIN);}  
 JAR                                                                  { return symbol(JAR);} 
             
                                                                                  
 KEY_MEMBER                                                           { return symbol(KEY_MEMBER);}                                                                      
 KEY_TYPE                                                             { return symbol(KEY_TYPE);} 
 KEEP                                                                 { return symbol(KEEP);} 
 KEY                                                                  { return symbol(KEY);}                                                                      
       
 LAST_DAY                                                             { return symbol(LAST_DAY);}
 LOCALTIMESTAMP                                                       { return symbol(LOCALTIMESTAMP);}
 LOCALTIME                                                            { return symbol(LOCALTIME);}                                                                      
 LANGUAGE                                                             { return symbol(LANGUAGE);}
 LC_CTYPE                                                             { return symbol(LC_CTYPE);}
 LCASE                                                                { return symbol(LCASE);}      
 LATERAL                                                              { return symbol(LATERAL);}                                                                      
 LEADING                                                              { return symbol(LEADING);}       
 LOCATOR[Ss]?                                                         { return symbol(LOCATOR);}                                                                      
 LOCKMAX                                                              { return symbol(LOCKMAX);}
 LOCKSIZE                                                             { return symbol(LOCKSIZE);} 
 LOCATE                                                               { return symbol(LOCATE);}
 LOCATE_IN_STRING                                                     { return symbol(LOCATE_IN_STRING);}
 LABEL                                                                { return symbol(LABEL);}
 LOG10                                                                { return symbol(LOG10);}
 LOCK                                                                 { return symbol(LOCK);}
 LONG                                                                 { return symbol(LONG);} 
 LOOP                                                                 { return symbol(LOOP);} 
 LPAD                                                                 { return symbol(LPAD);}
 LEAVE                                                                { return symbol(LEAVE);} 
 LTRIM                                                                { return symbol(LTRIM);}
 LENGTH                                                               { return symbol(LENGTH);}                                                                      
 LARGE                                                                { return symbol(LARGE);}                                                                      
 LEVEL                                                                { return symbol(LEVEL);}                                                                      
 LOCAL                                                                { return symbol(LOCAL);}                                                                      
 LOCALE                                                               { return symbol(LOCALE);}
 LOCKED                                                               { return symbol(LOCKED);}
 LOCKS                                                                { return symbol(LOCKS);} 
 LOWER                                                                { return symbol(LOWER);}                                                                      
 LAST                                                                 { return symbol(LAST);}  
 LEFT                                                                 { return symbol(LEFT);}                                                                      
 LIKE                                                                 { return symbol(LIKE);}                                                                      
 LN                                                                   { return symbol(LN);}                                                                      
                                                                                  
 MESSAGE_OCTET_LENGTH                                                 { return symbol(MESSAGE_OCTET_LENGTH);}                                                                      
 MIDNIGHT_SECONDS                                                     { return symbol(MIDNIGHT_SECONDS);}
 MESSAGE_LENGTH                                                       { return symbol(MESSAGE_LENGTH);}                                                                      
 MONTHS_BETWEEN                                                       { return symbol(MONTHS_BETWEEN);}
 MQRECEIVECLOB                                                        { return symbol(MQRECEIVECLOB);}
 MQRECEIVEALLB                                                        { return symbol(MQRECEIVEALLCLOB);}
 MQREADALLCLOB                                                        { return symbol(MQREADALLCLOB);}
 MATERIALIZED                                                         { return symbol(MATERIALIZED);}
 MESSAGE_TEXT                                                         { return symbol(MESSAGE_TEXT);}
 MICROSECOND[Ss]?                                                     { return symbol(MICROSECOND);}
 MQRECEIVEALL                                                         { return symbol(MQRECEIVEALL);}
 MULTIPLY_ALT                                                         { return symbol(MULTIPLY_ALT);} 
 MAINTAINED                                                           { return symbol(MAINTAINED);}           
 MQREADCLOB                                                           { return symbol(MQREADCLOB);}
 
 MULTIPLIER                                                           { return symbol(MULTIPLIER);}
 MQREADALL                                                            { return symbol(MQREADALL);}
 MQRECEIVE                                                            { return symbol(MQRECEIVE);}
 MAXVALUE                                                             { return symbol(MAXVALUE);}                                                                      
 MINVALUE                                                             { return symbol(MINVALUE);}                                                                      
 MODIFIES                                                             { return symbol(MODIFIES);}                                                                      
 MULTISET                                                             { return symbol(MULTISET);}                                                                      
 MATCHED                                                              { return symbol(MATCHED);}                                                                      
 MEMBER                                                               { return symbol(MEMBER);}                                                                      
 METHOD                                                               { return symbol(METHOD);}
 MEDIAN                                                               { return symbol(MEDIAN);}                                                                       
 MINUTE[Ss]?                                                          { return symbol(MINUTE);}                                                                      
 MODULE                                                               { return symbol(MODULE);}                                                                      
 MQREAD                                                               { return symbol(MQREAD);}
 MQSEND                                                               { return symbol(MQSEND);} 
 MERGE                                                                { return symbol(MERGE);}                                                                      
 MATCH                                                                { return symbol(MATCH);}                                                                      
 MONTHS                                                               { return symbol(MONTH);}
 MONTH                                                                { return symbol(MONTH);}                                                                      
 MUMPS                                                                { return symbol(MUMPS);}
 MODE                                                                 { return symbol(MODE);}     
 MORE                                                                 { return symbol(MORE);}                                                                      
 MAP                                                                  { return symbol(MAP);}                                                                      
 MAX                                                                  { return symbol(MAX);}                                                                      
 MIN                                                                  { return symbol(MIN);}                                                                      
 MOD                                                                  { return symbol(MOD);}                                                                     
                                                                                  
 NORMALIZED                                                           { return symbol(NORMALIZED);}                                                                      
 NORMALIZE                                                            { return symbol(NORMALIZE);}                                                                      
 NATIONAL                                                             { return symbol(NATIONAL);}                                                                      
 NULLABLE                                                             { return symbol(NULLABLE);}                                                                      
 NATURAL                                                              { return symbol(NATURAL);}
 NEXT_DAY                                                             { return symbol(NEXT_DAY);}
 NUMPARTS                                                             { return symbol(NUMPARTS);}
 NESTING                                                              { return symbol(NESTING);}                                                                      
 NEXTVAL                                                              { return symbol(NEXTVAL);} 
 NUMERIC                                                              { return symbol(NUMERIC);}                                                                      
 NULLIF                                                               { return symbol(NULLIF);}                                                                      
 NUMBER                                                               { return symbol(NUMBER);}                                                                      
 NAMES                                                                { return symbol(NAMES);}                                                                      
 NCHAR                                                                { return symbol(NCHAR);}                                                                      
 NCLOB                                                                { return symbol(NCLOB);}                                                                      
 NULLS                                                                { return symbol(NULLS);}                                                                      
 NAME                                                                 { return symbol(NAME);}                                                                      
 NEXT                                                                 { return symbol(NEXT);}  
 NORMALIZE_DECFLOAT                                                   { return symbol(NORMALIZE_DECFLOAT);}                 
 NORMALIZE_STRING                                                     { return symbol(NORMALIZE_STRING);}
 NFKC                                                                 { return symbol(NFKC);}
 NFKD                                                                 { return symbol(NFKD);}
 NONE                                                                 { return symbol(NONE);}                                                                      
 NULL                                                                 { return symbol(NULL);}                                                                      
 NEW                                                                  { return symbol(NEW);}                                                                      
 NFC                                                                  { return symbol(NFC);}
 NFD                                                                  { return symbol(NFD);}
 NVL                                                                  { return symbol(NVL);}
 NOT                                                                  { return symbol(NOT);}                                                                      
 NO                                                                   { return symbol(NO);}                                                                      
                                                                      
 OBJECT                                                               { return symbol(OBJECT);}                                                                      
 OCTETS                                                               { return symbol(OCTETS);}
 ORGANIZATION                                                         { return symbol(ORGANIZATION);}
 OPTIMIZE                                                             { return symbol(OPTIMIZE);}                                                                      
 OCTET_LENGTH                                                         { return symbol(OCTET_LENGTH);}                                                                      
 OF                                                                   { return symbol(OF);}                                                                      
 OFF                                                                  { return symbol(OFF);}
 OLD                                                                  { return symbol(OLD);}                                                                      
 ON                                                                   { return symbol(ON);}                                                                      
 ONLY                                                                 { return symbol(ONLY);}                                                                      
 OPEN                                                                 { return symbol(OPEN);}                                                                      
 OPTION                                                               { return symbol(OPTION);}                                                                      
 OPTIONS                                                              { return symbol(OPTIONS);}                                                                      
 OR                                                                   { return symbol(OR);}                                                                      
 ORDER                                                                { return symbol(ORDER);}                                                                      
 ORDERING                                                             { return symbol(ORDERING);}                                                                      
 ORDINALITY                                                           { return symbol(ORDINALITY);}                                                                      
 OTHERS                                                               { return symbol(OTHERS);}                                                                      
 OUT                                                                  { return symbol(OUT);}                                                                      
 OUTER                                                                { return symbol(OUTER);}                                                                      
 OUTPUT                                                               { return symbol(OUTPUT);}                                                                      
 OVER                                                                 { return symbol(OVER);}                                                                      
 OVERLAPS                                                             { return symbol(OVERLAPS);}                                                                      
 OVERLAY                                                              { return symbol(OVERLAY);}                                                                      
 OVERRIDING                                                           { return symbol(OVERRIDING);}
 OPTIMIZATION                                                         { return symbol(OPTIMIZATION);}
 OBID                                                                 { return symbol(OBID);}                                                                       

 PARAMETER_ORDINAL_POSITION                                           { return symbol(PARAMETER_ORDINAL_POSITION);}                                                                      
 PARAMETER_SPECIFIC_CATALOG                                           { return symbol(PARAMETER_SPECIFIC_CATALOG);}                                                                      
 PARAMETER_SPECIFIC_SCHEMA                                            { return symbol(PARAMETER_SPECIFIC_SCHEMA);}                                                                      
 PARAMETER_SPECIFIC_NAME                                              { return symbol(PARAMETER_SPECIFIC_NAME);}                                                                      
 PERCENTILE_CONT                                                      { return symbol(PERCENTILE_CONT);}                                                                      
 PERCENTILE_DISC                                                      { return symbol(PERCENTILE_DISC);}                                                                      
 PARAMETER_MODE                                                       { return symbol(PARAMETER_MODE);}                                                                      
 PARAMETER_NAME                                                       { return symbol(PARAMETER_NAME);}                                                                      
 PERCENT_RANK                                                         { return symbol(PERCENT_RANK);}
 PACKAGESET                                                           { return symbol(PACKAGESET);}   
 PARTITIONED                                                          { return symbol(PARTITIONED);}
 PARTITIONATED                                                        { return symbol(PARTITIONATED);}
 PARTITIONING                                                         { return symbol(PARTITIONING);} 
 PERMISSION                                                           { return symbol(PERMISSION);}
 PRIVILEGES                                                           { return symbol(PRIVILEGES);}                                                                      
 PARAMETER                                                            { return symbol(PARAMETER);}                                                                      
 PARTITION                                                            { return symbol(PARTITION);} 
 PIECESIZE                                                            { return symbol(PIECESIZE);}
 PRECEDING                                                            { return symbol(PRECEDING);}
 PRECISION                                                            { return symbol(PRECISION);}  
 PASSWORD                                                             { return symbol(PASSWORD);} 
 PROCEDURE                                                            { return symbol(PROCEDURE);}                                                                      
 POSITION                                                             { return symbol(POSITION);}                                                                      
 PRESERVE                                                             { return symbol(PRESERVE);}                                                                      
 PREVIOUS                                                             { return symbol(PREVIOUS);}
 PREVVAL                                                              { return symbol(PREVVAL);} 
 PACKAGE                                                              { return symbol(PACKAGE);} 
 PARTIAL                                                              { return symbol(PARTIAL);}
 PCTFREE                                                              { return symbol(PCTFREE);}                                            
 PLACING                                                              { return symbol(PLACING);}
 PORTION                                                              { return symbol(PORTION);}                                                                      
 PREPARE                                                              { return symbol(PREPARE);}                                                                      
 PRIMARY                                                              { return symbol(PRIMARY);}
 PROGRAM                                                              { return symbol(PROGRAM);} 
 PADDED                                                               { return symbol(PADDED);}
 PASCAL                                                               { return symbol(PASCAL);}
 PERIOD                                                               { return symbol(PERIOD);}
 PRIQTY                                                               { return symbol(PRIQTY);}
 PUBLIC                                                               { return symbol(PUBLIC);}                                                                      
 POWER                                                                { return symbol(POWER);}                                                                      
 PRIOR                                                                { return symbol(PRIOR);}
 PART                                                                 { return symbol(PART);}
 PATH                                                                 { return symbol(PATH);}                                                                      
 PACK                                                                 { return symbol(PACK);}
 PLAN                                                                 { return symbol(PLAN);} 
 PSID                                                                 { return symbol(PSID);}
 PAD                                                                  { return symbol(PAD);}                                                                      
 PLI                                                                  { return symbol(PLI);}
 POSSTR                                                               { return symbol(POSSTR);}

 QUERYLANGUAGE                                                        { return symbol(QUERYLANGUAGE);}
 QUANTIZE                                                             { return symbol(QUANTIZE);}
 QUARTER                                                              { return symbol(QUARTER);}
 QUERYNO                                                              { return symbol(QUERYNO);}                                                                        
 QUERY                                                                { return symbol(QUERY);}

 RETURNED_OCTET_LENGTH                                                { return symbol(RETURNED_OCTET_LENGTH);}                                                                      
 RETURNED_CARDINALITY                                                 { return symbol(RETURNED_CARDINALITY);}                                                                      
 RETURNED_SQLSTATE                                                    { return symbol(RETURNED_SQLSTATE);}                                                                      
 RETURNED_LENGTH                                                      { return symbol(RETURNED_LENGTH);}                                                                      
 ROUTINE_CATALOG                                                      { return symbol(ROUTINE_CATALOG);}                                                                      
 ROUND_HALF_DOWN                                                      { return symbol(ROUND_HALF_DOWN);} 
 ROUND_HALF_EVEN                                                      { return symbol(ROUND_HALF_EVEN);}
 ROUND_TIMESTAMP                                                      { return symbol(ROUND_TIMESTAMP);}
 RESULT_SET_LOCATOR                                                   { return symbol(RESULT_SET_LOCATOR);} 
 REGR_INTERCEPT                                                       { return symbol(REGR_INTERCEPT);}
 ROUTINE_SCHEMA                                                       { return symbol(ROUTINE_SCHEMA);}
 ROUND_HALF_UP                                                        { return symbol(ROUND_HALF_UP);}
 ROUND_CEILING                                                        { return symbol(ROUND_CEILING);}                                                                      
 ROUTINE_NAME                                                         { return symbol(ROUTINE_NAME);} 
 RAISE_ERROR                                                          { return symbol(RAISE_ERROR);}
 REFERENCING                                                          { return symbol(REFERENCING);}
 ROUND_FLOOR                                                          { return symbol(ROUND_FLOOR);}
 RESULTLIMIT                                                          { return symbol(RESULTLIMIT);}
 ROUND_DOWN                                                           { return symbol(ROUND_DOWN);}
 REFERENCES                                                           { return symbol(REFERENCES);}                                                                      
 REGENERATE                                                           { return symbol(REGENERATE);} 
 REGR_COUNT                                                           { return symbol(REGR_COUNT);}                                                                      
 REGR_SLOPE                                                           { return symbol(REGR_SLOPE);}                                                                      
 REPEATABLE                                                           { return symbol(REPEATABLE);}                                                                      
 ROW_NUMBER                                                           { return symbol(ROW_NUMBER);}                                                                      
 RESIGNAL                                                             { return symbol(RESIGNAL);}
 RECURSIVE                                                            { return symbol(RECURSIVE);}   
 REGR_AVGX                                                            { return symbol(REGR_AVGX);}                                                                      
 REGR_AVGY                                                            { return symbol(REGR_AVGY);}                                                                      
 ROW_COUNT                                                            { return symbol(ROW_COUNT);}                                                                      
 REGR_SXX                                                             { return symbol(REGR_SXX);}                                                                      
 REGR_SXY                                                             { return symbol(REGR_SXY);}                                                                      
 REGR_SYY                                                             { return symbol(REGR_SYY);}                                                                      
 RELATIVE                                                             { return symbol(RELATIVE);} 
 RESTRICT                                                             { return symbol(RESTRICT);} 
 ROLLBACK                                                             { return symbol(ROLLBACK);}
 ROUNDING                                                             { return symbol(ROUNDING);}                                                                      
 ROUND_UP                                                             { return symbol(ROUND_UP);}
 RADIANS                                                              { return symbol(RADIANS);} 
 REPLACE                                                              { return symbol(REPLACE);}
 REGR_R2                                                              { return symbol(REGR_R2);}                                                                      
 RELEASE                                                              { return symbol(RELEASE);}     
 REFRESH                                                              { return symbol(REFRESH);}                                                                 
 RESTART                                                              { return symbol(RESTART);}                                                                       
 RETURNS                                                              { return symbol(RETURNS);}                                                                      
 ROUTINE                                                              { return symbol(ROUTINE);}                                                                      
 RENAME                                                               { return symbol(RENAME);}
 REPEAT                                                               { return symbol(REPEAT);}
 RETAIN                                                               { return symbol(RETAIN);}
 RESULT                                                               { return symbol(RESULT);}                                                                      
 RETURN                                                               { return symbol(RETURN);}                                                                      
 REVOKE                                                               { return symbol(REVOKE);}                                                                      
 ROLLUP                                                               { return symbol(ROLLUP);}                                                                      
 ROWSET                                                               { return symbol(ROWSET);} 
 RANGE                                                                { return symbol(RANGE);}                                                                      
 READS                                                                { return symbol(READS);}                                                                      
 RESET                                                                { return symbol(RESET);}
 RIGHT                                                                { return symbol(RIGHT);}                                                                      
 ROUND                                                                { return symbol(ROUND);}
 ROWID                                                                { return symbol(ROWID);}
 RTRIM                                                                { return symbol(RTRIM);} 
 RULES                                                                { return symbol(RULES);} 
 RAND                                                                 { return symbol(RAND);}
 RANK                                                                 { return symbol(RANK);}                                                                      
 READ                                                                 { return symbol(READ);}                                                                      
 REAL                                                                 { return symbol(REAL);}                                                                      
 RPAD                                                                 { return symbol(RPAD);}
 ROLE                                                                 { return symbol(ROLE);}                                                                      
 ROWS                                                                 { return symbol(ROWS);}                                                                      
 REF                                                                  { return symbol(REF);}                                                                      
 RID                                                                  { return symbol(RID);}
 ROW                                                                  { return symbol(ROWS);}                                                                      
 RUN                                                                  { return symbol(RUN);} 
 RR                                                                   { return symbol(RR);}
 RS                                                                   { return symbol(RS);} 
                                                                       
 SUBCLASS_ORIGIN                                                      { return symbol(SUBCLASS_ORIGIN);}                                                                      
 SCOPE_CATALOG                                                        { return symbol(SCOPE_CATALOG);}                                                                      
 SPECIFIC_NAME                                                        { return symbol(SPECIFIC_NAME);}                                                                      
 SCOPE_SCHEMA                                                         { return symbol(SCOPE_SCHEMA);}                                                                      
 SERIALIZABLE                                                         { return symbol(SERIALIZABLE);}                                                                      
 SESSION_USER                                                         { return symbol(SESSION_USER);}                                                                      
 SPECIFICTYPE                                                         { return symbol(SPECIFICTYPE);}                                                                      
 SQLEXCEPTION                                                         { return symbol(SQLEXCEPTION);}                                                                      
 SCHEMA_NAME                                                          { return symbol(SCHEMA_NAME);}                                                                      
 SCORE                                                                { return symbol(SCORE);} 
 SERVER_NAME                                                          { return symbol(SERVER_NAME);}
 SIGN                                                                 { return symbol(SIGN);}                                                                       
 SINH                                                                 { return symbol(SINH);}
 SIN                                                                  { return symbol(SIN);}
 SOAPHTTPC                                                            { return symbol(SOAPHTTPC);}
 SOAPHTTPV                                                            { return symbol(SOAPHTTPV);}
 SOAPHTTPNC                                                           { return symbol(SOAPHTTPNC);}
 SOAPHTTPNV                                                           { return symbol(SOAPHTTPNV);}
 SOUNDEX                                                              { return symbol(SOUNDEX);}
 STRIP                                                                { return symbol(STRIP);}
 SUBSTR                                                               { return symbol(SUBSTR);} 
 STDDEV_SAMP                                                          { return symbol(STDDEV_SAMP);}                                                                      
 SUBMULTISET                                                          { return symbol(SUBMULTISET);}                                                                      
 SYSTEM_USER                                                          { return symbol(SYSTEM_USER);}
 SYSTEM_TIME                                                          { return symbol(SYSTEM_TIME);}            
 SCOPE_NAME                                                           { return symbol(SCOPE_NAME);}
 SCRATCHPAD                                                           { return symbol(SCRATCHPAD);}
 SQLWARNING                                                           { return symbol(SQLWARNING);}                                                                      
 STDDEV_POP                                                           { return symbol(STDDEV_POP);}
 STORGROUP                                                            { return symbol(STORGROUP);}                                                                      
 SAVEPOINT                                                            { return symbol(SAVEPOINT);}                                                                      
 SENSITIVE                                                            { return symbol(SENSITIVE);}                                                                      
 STATEMENT                                                            { return symbol(STATEMENT);}                                                                      
 STANDARD                                                             { return symbol(STANDARD);} 
 STRUCTURE                                                            { return symbol(STRUCTURE);}                                                                      
 SUBSTRING                                                            { return symbol(SUBSTRING);}                                                                      
 SYSDATESYSTEM                                                        { return symbol(SYSDATESYSTEM);}
 SYSTIMESTAMP                                                         { return symbol(SYSTIMESTAMP);} 
 SYMMETRIC                                                            { return symbol(SYMMETRIC);} 
 STOGROUP                                                             { return symbol(STOGROUP);}                                                                      
 SECURITY                                                             { return symbol(SECURITY);}                                                                      
 SEQUENCE                                                             { return symbol(SEQUENCE);}                                                                      
 SMALLINT                                                             { return symbol(SMALLINT);}                                                                      
 SYNONYM                                                              { return symbol(SYNONYM);}
 SPECIFIC                                                             { return symbol(SPECIFIC);}                                                                      
 SQLERROR                                                             { return symbol(SQLERROR);}
 SQLSTATE                                                             { return symbol(SQLSTATE);}                                                                      
 SECTION                                                              { return symbol(SECTION);}                                                                      
 SESSION                                                              { return symbol(SESSION);}                                                                      
 SUMMARY                                                              { return symbol(SUMMARY);}
 SIMILAR                                                              { return symbol(SIMILAR);}                                                                      
 STACKED                                                              { return symbol(STACKED);}
 SCHEMA                                                               { return symbol(SCHEMA);}                                                                      
 SCHEME                                                               { return symbol(SCHEME);}
 SCROLL                                                               { return symbol(SCROLL);}                                                                      
 STDDEV                                                               { return symbol(STDDEV);} 
 SEARCH                                                               { return symbol(SEARCH);}                                                                      
 SECOND[Ss]?                                                          { return symbol(SECOND);}
 SECQTY                                                               { return symbol(SECQTY);}                                                                       
 SELECT                                                               { return symbol(SELECT);}                                                                      
 SERVER                                                               { return symbol(SERVER);} 
 SIMPLE                                                               { return symbol(SIMPLE);}                                                                      
 SOURCE                                                               { return symbol(SOURCE);}                                                                      
 STATIC                                                               { return symbol(STATIC);}
 SYNONIM                                                              { return symbol(SYNONIM);}
 SIGNAL                                                               { return symbol(SIGNAL);}
 SYSTEM                                                               { return symbol(SYSTEM);}                                                                      
 STORES                                                               { return symbol(STORES);} 
 SCALE                                                                { return symbol(SCALE);}                                                                      
 SHARE                                                                { return symbol(SHARE);}
 SPACE                                                                { return symbol(SPACE);} 
 SQLID                                                                { return symbol(SQLID);}                                                                     
 START                                                                { return symbol(START);}                                                                      
 STATE                                                                { return symbol(STATE);}                                                                      
 STYLE                                                                { return symbol(STYLE);}                                                                      
 SELF                                                                 { return symbol(SELF);}                                                                      
 SETS                                                                 { return symbol(SETS);}                                                                      
 SIZE                                                                 { return symbol(SIZE);}
 SKIP                                                                 { return symbol(SKIP);}                                                                        
 SOME                                                                 { return symbol(SOME);}                                                                      
 SQRT                                                                 { return symbol(SQRT);}                                                                      
 STAY                                                                 { return symbol(STAY);} 
 SET                                                                  { return symbol(SET);}                                                                      
 SQL                                                                  { return symbol(SQL);}                                                                      
 SUM                                                                  { return symbol(SUM);}                                                                      

 TRANSACTIONS_ROLLED_BACK                                             { return symbol(TRANSACTIONS_ROLLED_BACK);}                                                                                                              
 TRANSACTIONS_COMMITTED                                               { return symbol(TRANSACTIONS_COMMITTED);}                                                                      
 TRANSACTION_ACTIVE                                                   { return symbol(TRANSACTION_ACTIVE);}                                                                      
 TIMESTAMP_FORMAT                                                     { return symbol(TIMESTAMP_FORMAT);}
 TIMEZONE_MINUTE                                                      { return symbol(TIMEZONE_MINUTE);}                                                                      
 TOP_LEVEL_COUNT                                                      { return symbol(TOP_LEVEL_COUNT);}   
 TRIGGER_CATALOG                                                      { return symbol(TRIGGER_CATALOG);}                                                                      
 TRUNC_TIMESTAMP                                                      { return symbol(TRUNC_TIMESTAMP);}
 TIMESTAMP_DIFF                                                       { return symbol(TIMESTAMP_DIFF);}
 TRIGGER_SCHEMA                                                       { return symbol(TRIGGER_SCHEMA);}                                                                      
 TIMESTAMP_ISO                                                        { return symbol(TIMESTAMP_ISO);}
 TIMEZONE_HOUR                                                        { return symbol(TIMEZONE_HOUR);}                                                                      
 TIMESTAMPADD                                                         { return symbol(TIMESTAMPADD);}
 TIMESTAMP_TZ                                                         { return symbol(TIMESTAMP_TZ);}
 TRIGGER_NAME                                                         { return symbol(TRIGGER_NAME);}  
 TABLESAMPLE                                                          { return symbol(TABLESAMPLE);}                                                                      
 TRANSACTION                                                          { return symbol(TRANSACTION);}                                                                      
 TRANSLATION                                                          { return symbol(TRANSLATION);}   
 TABLE_NAME                                                           { return symbol(TABLE_NAME);}
 TABLESPACE                                                           { return symbol(TABLESPACE);}                                                                      
 TOTALORDER                                                           { return symbol(TOTALORDER);}
 TRANSFORMS                                                           { return symbol(TRANSFORMS);}
 TEMPORARY                                                            { return symbol(TEMPORARY);}
 TIMESTAMP                                                            { return symbol(TIMESTAMP);}
 TRANSFORM                                                            { return symbol(TRANSFORM);}                                                                      
 TRANSLATE                                                            { return symbol(TRANSLATE);}     
 TO_NUMBER                                                            { return symbol(TO_NUMBER);}
 TRAILING                                                             { return symbol(TRAILING);}
 TRUNCATE                                                             { return symbol(TRUNCATE);}
 TO_CHAR                                                              { return symbol(TO_CHAR);} 
 TO_DATE                                                              { return symbol(TO_DATE);}                                                                     
 TRUSTED                                                              { return symbol(TRUSTED);}
 TRIGGER                                                              { return symbol(TRIGGER);}                                                                      
 TRUNC                                                                { return symbol(TRUNCATE);}
 TABLE                                                                { return symbol(TABLE);}  
 TREAT                                                                { return symbol(TREAT);}
 TYPES                                                                { return symbol(TYPES);}                                              
 THEN                                                                 { return symbol(THEN);}                                                                      
 TIES                                                                 { return symbol(TIES);}                                                                      
 TIME                                                                 { return symbol(TIME);}                                                                      
 TRIM                                                                 { return symbol(TRIM);}                                                                      
 TRUE                                                                 { return symbol(TRUE);}                                                                      
 TYPE                                                                 { return symbol(TYPE);}
 TANH                                                                 { return symbol(TANH);}                                                                      
 TAN                                                                  { return symbol(TAN);}
 TO                                                                   { return symbol(TO);}                                                                      

 USER_DEFINED_TYPE_CATALOG                                            { return symbol(USER_DEFINED_TYPE_CATALOG);}                                                                      
 USER_DEFINED_TYPE_SCHEMA                                             { return symbol(USER_DEFINED_TYPE_SCHEMA);}                                                                      
 USER_DEFINED_TYPE_CODE                                               { return symbol(USER_DEFINED_TYPE_CODE);}                                                                      
 USER_DEFINED_TYPE_NAME                                               { return symbol(USER_DEFINED_TYPE_NAME);}                                                                      
 UNICODE_STR                                                          { return symbol(UNICODE_STR);}
 UNCOMMITTED                                                          { return symbol(UNCOMMITTED);}  
 UNBOUNDED                                                            { return symbol(UNBOUNDED);}                                                                      
 UNICODE                                                              { return symbol(UNICODE);}                                                                    
 UNNAMED                                                              { return symbol(UNNAMED);}                                                                      
 UESCAPE                                                              { return symbol(UESCAPE);}                                                                      
 UNPACK                                                               { return symbol(UNPACK);}
 UNIQUE                                                               { return symbol(UNIQUE);}                                                                      
 UNNEST                                                               { return symbol(UNNEST);}                                                                      
 UPDATE                                                               { return symbol(UPDATE);}                                                                      
 UNDER                                                                { return symbol(UNDER);}                                                                      
 UNION                                                                { return symbol(UNION);}       
 UCASE                                                                { return symbol(UCASE);}
 UNTIL                                                                { return symbol(UNTIL);} 
 UPPER                                                                { return symbol(UPPER);}                                                                      
 USAGE                                                                { return symbol(USAGE);}                                                                      
 USING                                                                { return symbol(USING);}
 UTF16                                                                { return symbol(UTF16);}
 UNDO                                                                 { return symbol(UNDO);}
 USER                                                                 { return symbol(USER);}                                                                      
 UTF8                                                                 { return symbol(UTF8);}
 UR                                                                   { return symbol(UR);}

 VERIFY_TRUSTED_CONTEXT_FOR_USER                                      { return symbol(VERIFY_TRUSTED_CONTEXT_FOR_USER);} 
 VERIFY_GROUP_FOR_USER                                                { return symbol(VERIFY_GROUP_FOR_USER);}
 VERIFY_ROLE_FOR_USER                                                 { return symbol(VERIFY_ROLE_FOR_USER);}
 VARCHAR_FORMAT                                                       { return symbol(VARCHAR_FORMAT);}
 VARIANCE_SAMP                                                        { return symbol(VARIANCE_SAMP);}
 VARGRAPHIC                                                           { return symbol(VARGRAPHIC);} 
 VARBINARY                                                            { return symbol(VARBINARY);}
 VALIDPROC                                                            { return symbol(VALIDPROC);}
 VAR_SAMP                                                             { return symbol(VAR_SAMP);}                                                                                                                        
 VARIABLE                                                             { return symbol(VARIABLE);} 
 VOLATILE                                                             { return symbol(VOLATILE);} 
 VARIANCE                                                             { return symbol(VARIANCE);}                                                                      
 VARCHAR                                                              { return symbol(VARCHAR);} 
 VARYING                                                              { return symbol(VARYING);}                                                                      
 VAR_POP                                                              { return symbol(VAR_POP);}
 VARIANT                                                              { return symbol(VARIANT);} 
 VERSION                                                              { return symbol(VERSION);} 
 VOLUMES                                                              { return symbol(VOLUMES);} 
 VALUES                                                               { return symbol(VALUES);}                                                                      
 VALUE                                                                { return symbol(VALUE);}
 VCAT                                                                 { return symbol(VCAT);} 
 VIEW                                                                 { return symbol(VIEW);}                                                                      

 WIDTH_BUCKET                                                         { return symbol(WIDTH_BUCKET);}                                                                      
 WEEK_ISO                                                             { return symbol(WEEK_ISO);}
 WHENEVER                                                             { return symbol(WHENEVER);}  
 WITHOUT                                                              { return symbol(WITHOUT);}                                                                      
 WINDOW                                                               { return symbol(WINDOW);}                                                                      
 WITHIN                                                               { return symbol(WITHIN);}                                                                      
 WHERE                                                                { return symbol(WHERE);}                                                                      
 WRITE                                                                { return symbol(WRITE);}
 WEEK                                                                 { return symbol(WEEK);}                                                                      
 WHEN                                                                 { return symbol(WHEN);}
 WHILE                                                                { return symbol(WHILE);}
 WITH                                                                 { return symbol(WITH);}                                                                      
 WORK                                                                 { return symbol(WORK);}                                                                      
 WLM                                                                  { return symbol(WLM);}
 
 YEAR[Ss]?                                                            { return symbol(YEAR);}      

 XMLXSROBJECTID                                                       { return symbol(XMLXSROBJECTID);}                                                                 
 XMLATTRIBUTES                                                        { return symbol(XMLATTRIBUTES);}
 XMLNAMESPACES                                                        { return symbol(XMLNAMESPACES);}
 XMLSERIALIZE                                                         { return symbol(XMLSERIALIZE);}
 XSLTRANSFORM                                                         { return symbol(XSLTRANSFORM);}
 XMLDOCUMENT                                                          { return symbol(XMLDOCUMENT);}
 XMLCOMMENT                                                           { return symbol(XMLCOMMENT);}
 XMLELEMENT                                                           { return symbol(XMLELEMENT);}
 XMLCONCAT                                                            { return symbol(XMLCONCAT);}
 XMLEXISTS                                                            { return symbol(XMLEXISTS);}
 XMLFOREST                                                            { return symbol(XMLFOREST);}
 XMLMODIFY                                                            { return symbol(XMLMODIFY);}
 XMLPARSE                                                             { return symbol(XMLPARSE);}
 XMLQUERY                                                             { return symbol(XMLQUERY);}
 XMLTABLE                                                             { return symbol(XMLTABLE);}
 XMLCAST                                                              { return symbol(XMLCAST);} 
 XMLTEXT                                                              { return symbol(XMLTEXT);}
 XMLAGG                                                               { return symbol(XMLAGG);}
 XMLPI                                                                { return symbol(XMLPI);}

 
 YES                                                                  { return symbol(YES);}
 ZONE                                                                 { return symbol(ZONE);}                                                                      
 

  \'               { pushState(QUOTE_STRING);  }  
  \"               { pushState(DQUOTE_STRING); }
  

 {SPACES}          { /* DO NOTHING */              }
 {ID}              { return symbol(ID);            }
 {ID2}             { return symbol(ID2);           } 
 {ENTERO}          { return symbol(NUM_ENTERO);    }
 {SIGNED}          { return symbol(NUM_SIGNED);    }
 {DECNUM}          { return symbol(NUM_DECIMAL);   }
 {DECFLOAT}        { return symbol(NUM_FLOAT);     }
// {NUMBIN}          { return symbol{NUM_BINARY);  }
// {NUMGRAPHIC}      { return symbol{NUM_GRAPHIC); }
    
  "??("            { /* eat [ */ }
  "??)"            { /* eat ] */ }
  "??<"            { /* eat { */ }
  "??>"            { /* eat } */ }
  "??="            { /* eat pound */ }
  "??/"            { /* eat \ */ }
  
  "||"             { return symbol(CONCAT);    }
  "<>"             { return symbol(NE);        }
  "^="             { return symbol(NE2);       }  
  "^>"             { return symbol(NGT);       }
  "µ>"             { return symbol(NGT);       }
  "µ<"             { return symbol(NGT);       }
  "µ="             { return symbol(NE2);       }
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
  "("              { if (words.contains(lastID)) {
                         info.setInSearch();
                      }
                      if (info.isInSearch() && words.contains(lastID)) {
                            pars.push(0);
                      }    
                      else { 
                        pars.push(1); // Pila de parentesis
                        return symbol(LPAR);      
                     }
                   }   
  ")"              { int idPar = pars.pop(); // Quita el ( de la pila
                     if (idPar == 1) return symbol(RPAR);      
                   }
  // Caso : \n Host
  ":"              { return symbol(PREHOST); }

  \n            { /* eat */ } 
  \r            { /* eat */ }
  
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
 