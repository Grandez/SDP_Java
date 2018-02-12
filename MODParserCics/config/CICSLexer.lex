package com.jgg.sdp.parser.lang;

import java_cup.runtime.*;

import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.symbols.SDPSymbol;
import static com.jgg.sdp.parser.lang.CICSSym.*;

%%

%public
%class      CICSLexer
%extends    GenericLexer

%line
%column
%char
%full
%ignorecase
// %implements java_cup.runtime.Scanner
%function next_token
%cup

%xstate COMMENT
%xstate QUOTE
%xstate DQUOTE
%xstate QUOTE_STRING
%xstate DQUOTE_STRING

%{

   public void resetLiteral(String txt) {
      data = true;
      litLine = yyline + info.getOffset();
      litColumn = yycolumn;
      cadena = new StringBuilder(txt);
   }

   public Symbol literal() {
      String texto = cadena.toString();
      cadena.setLength(0);
      print("Devuelve LITERAL - " + texto);
      return symbolFactory.newSymbol(LITERAL, litLine, litColumn, texto);
   }
               
   public Symbol sym(int code) {
      return sym(code, yytext());
   }
   
   public Symbol sym(int code, String txt) {
      data = true;
      int line = yyline + info.getOffset();
      print("Devuelve SYMBOL " + code + " (" + line  + "," + (yycolumn + 1) + ") " + txt);
      
      return symbolFactory.newSymbol(code, line, yycolumn + 1, txt);
   }

%}

%init{
   initLexer();
%init}

%eofval{
     return symbolFactory.newSymbol("EOF", CICSSym.EOF);
%eofval}

SPACES=[\ \t]+

ALPHANUM=[a-zA-Z0-9]
ENTERO=[+|-]?[0-9]+
DECIMAL=[+|-]?[0-9]+[\.\,]?[0-9]+

ID = {ALPHANUM}({ALPHANUM}|\-|\_)*

%% 

/******************************************************************************/
/******************************************************************************/
/***                      CASOS GENERALES                                   ***/
/******************************************************************************/
/******************************************************************************/

^[\-]                  { /* nada */              }
  
ABEND                  { return sym(ABEND         ); }       
ABORT                  { return sym(ABORT         ); }
ACQPROCESS             { return sym(ACQPROCESS    ); }
ACQUIRE                { return sym(ACQUIRE       ); }
ACTIVITY               { return sym(ACTIVITY      ); }
ACTIVITYID             { return sym(ACTIVITYID    ); }
ADD                    { return sym(ADD           ); }
ADDRESS                { return sym(ADDRESS       ); }
AID                    { return sym(AID           ); }
ALLOCATE               { return sym(ALLOCATE      ); }
ASKTIME                { return sym(ASKTIME       ); }
ASSIGN                 { return sym(ASSIGN        ); }
ATTACH                 { return sym(ATTACH        ); }
ATTRIBUTES             { return sym(ATTRIBUTES    ); }
AUTINSTMODEL           { return sym(AUTINSTMODEL  ); }
AUTOINSTALL            { return sym(AUTOINSTALL   ); }
BEAN                   { return sym(BEAN          ); }
BIF                    { return sym(BIF           ); }
BREXIT                 { return sym(BREXIT        ); }
BRFACILITY             { return sym(BRFACILITY    ); }
BUILD                  { return sym(BUILD         ); }
CANCEL                 { return sym(CANCEL        ); }
CERTIFICATE            { return sym(CERTIFICATE   ); }
CFDTPOOL               { return sym(CFDTPOOL      ); }
CHANGE                 { return sym(CHANGE        ); }
CHANNEL                { return sym(CHANNEL       ); }
CHECK                  { return sym(CHECK         ); }
CLASSCACHE             { return sym(CLASSCACHE    ); }
CLOSE                  { return sym(CLOSE         ); }
COLLECT                { return sym(COLLECT       ); }
COMPOSITE              { return sym(COMPOSITE     ); }
CONDITION              { return sym(CONDITION     ); }
CONFIRMATION           { return sym(CONFIRMATION  ); }
CONNECT                { return sym(CONNECT       ); }
CONNECTION             { return sym(CONNECTION    ); }
CONTAINER              { return sym(CONTAINER     ); }
CONTROL                { return sym(CONTROL       ); }
CONVERSE               { return sym(CONVERSE      ); }
CONVERTTIME            { return sym(CONVERTTIME   ); }
CONVID                 { return sym(CONVID        ); }
COPY                   { return sym(COPY          ); }
CORBASERVER            { return sym(CORBASERVER   ); }
COUNTER                { return sym(COUNTER       ); }
CREATE                 { return sym(CREATE        ); }
DATATOXML              { return sym(DATATOXML     ); }
DB2CONN                { return sym(DB2CONN       ); }
DB2ENTRY               { return sym(DB2ENTRY      ); }
DB2TRAN                { return sym(DB2TRAN       ); }
DCOUNTER               { return sym(DCOUNTER      ); }
DEEDIT                 { return sym(DEEDIT        ); }
DEFINE                 { return sym(DEFINE        ); }
DELAY                  { return sym(DELAY         ); }
DELETE                 { return sym(DELETE        ); }
DELETEQ                { return sym(DELETEQ       ); }
DELETSHIPPED           { return sym(DELETSHIPPED  ); }
DEQ                    { return sym(DEQ           ); }
DIGEST                 { return sym(DIGEST        ); }
DISABLE                { return sym(DISABLE       ); }
DISCARD                { return sym(DISCARD       ); }
DISCONNECT             { return sym(DISCONNECT    ); }
DISPATCHER             { return sym(DISPATCHER    ); }
DJAR                   { return sym(DJAR          ); }
DOCTEMPLATE            { return sym(DOCTEMPLATE   ); }
DOCUMENT               { return sym(DOCUMENT      ); }
DSNAME                 { return sym(DSNAME        ); }
DUMP                   { return sym(DUMP          ); }
DUMPDS                 { return sym(DUMPDS        ); }
ENABLE                 { return sym(ENABLE        ); }
END                    { return sym(END           ); }
ENDAFFINITY            { return sym(ENDAFFINITY   ); }
ENDBR                  { return sym(ENDBR         ); }
ENDBROWSE              { return sym(ENDBROWSE     ); }
ENDFILE                { return sym(ENDFILE       ); }
ENDOUTPUT              { return sym(ENDOUTPUT     ); }
ENQ                    { return sym(ENQ           ); }
ENQMODEL               { return sym(ENQMODEL      ); }
ENTER                  { return sym(ENTER         ); }
ENTRYNAME              { return sym(ENTRYNAME     ); }
EODS                   { return sym(EODS          ); }
ERASE                  { return sym(ERASE         ); }
ERASEAUP               { return sym(ERASEAUP      ); }
ERROR                  { return sym(ERROR         ); }
EVENT                  { return sym(EVENT         ); }
EXCI                   { return sym(EXCI          ); }
EXIT                   { return sym(EXIT          ); }
EXITPROGRAM            { return sym(EXITPROGRAM   ); }
EXTERNAL               { return sym(EXTERNAL      ); }
EXTRACT                { return sym(EXTRACT       ); }
FILE                   { return sym(FILE          ); }
FORCE                  { return sym(FORCE         ); }
FORMATTIME             { return sym(FORMATTIME    ); }
FORMFIELD              { return sym(FORMFIELD     ); }
FREE                   { return sym(FREE          ); }
FREEMAIN               { return sym(FREEMAIN      ); }
GDS                    { return sym(GDS           ); }
GET                    { return sym(GET           ); }
GETMAIN                { return sym(GETMAIN       ); }
GETNEXT                { return sym(GETNEXT       ); }
HANDLE                 { return sym(HANDLE        ); }
HOST                   { return sym(HOST          ); }
HTTPHEADER             { return sym(HTTPHEADER    ); }
IGNORE                 { return sym(IGNORE        ); }
INPUT                  { return sym(INPUT         ); }
INQUIRE                { return sym(INQUIRE       ); }
INSERT                 { return sym(INSERT        ); }
INVOKE                 { return sym(INVOKE        ); }
IRC                    { return sym(IRC           ); }
ISSUE                  { return sym(ISSUE         ); }
JOURNALMODEL           { return sym(JOURNALMODEL  ); }
JOURNALNAME            { return sym(JOURNALNAME   ); }
JOURNALNUM             { return sym(JOURNALNUM    ); }
JVM                    { return sym(JVM           ); }
JVMPOOL                { return sym(JVMPOOL       ); }
JVMPROFILE             { return sym(JVMPROFILE    ); }
LINK                   { return sym(LINK          ); }
LIST                   { return sym(LIST          ); }
LOAD                   { return sym(LOAD          ); }
LOGONMSG               { return sym(LOGONMSG      ); }
LSRPOOL                { return sym(LSRPOOL       ); }
MAP                    { return sym(MAP           ); }
MAPPED                 { return sym(MAPPED        ); }
MAPPINGDEV             { return sym(MAPPINGDEV    ); }
MAPSET                 { return sym(MAPSET        ); }
MESSAGE                { return sym(MESSAGE       ); }
MODENAME               { return sym(MODENAME      ); }
MONITOR                { return sym(MONITOR       ); }
MOVE                   { return sym(MOVE          ); }
MVSTCB                 { return sym(MVSTCB        ); }
NETNAME                { return sym(NETNAME       ); }
NOEDIT                 { return sym(NOEDIT        ); }
NOTE                   { return sym(NOTE          ); }
OPEN                   { return sym(OPEN          ); }
OPERATOR               { return sym(OPERATOR      ); }
OUTPUT                 { return sym(OUTPUT        ); }
PAGE                   { return sym(PAGE          ); }
PARSE                  { return sym(PARSE         ); }
PARTITIONSET           { return sym(PARTITIONSET  ); }
PARTN                  { return sym(PARTN         ); }
PARTNER                { return sym(PARTNER       ); }
PARTNSET               { return sym(PARTNSET      ); }
PASS                   { return sym(PASS          ); }
PASSWORD               { return sym(PASSWORD      ); }
PERFORM                { return sym(PERFORM       ); }
PIPELINE               { return sym(PIPELINE      ); }
POINT                  { return sym(POINT         ); }
POP                    { return sym(POP           ); }
POST                   { return sym(POST          ); }
PREPARE                { return sym(PREPARE       ); }
PRINT                  { return sym(PRINT         ); }
PROCESS                { return sym(PROCESS       ); }
PROCESSTYPE            { return sym(PROCESSTYPE   ); }
PROFILE                { return sym(PROFILE       ); }
PROGRAM                { return sym(PROGRAM       ); }
PURGE                  { return sym(PURGE         ); }
PUSH                   { return sym(PUSH          ); }
PUT                    { return sym(PUT           ); }
QUERY                  { return sym(QUERY         ); }
READ                   { return sym(READ          ); }
READNEXT               { return sym(READNEXT      ); }
READPREV               { return sym(READPREV      ); }
READQ                  { return sym(READQ         ); }
REATTACH               { return sym(REATTACH      ); }
REBUILD                { return sym(REBUILD       ); }
RECEIVE                { return sym(RECEIVE       ); }
RECORD                 { return sym(RECORD        ); }
RELEASE                { return sym(RELEASE       ); }
REMOVE                 { return sym(REMOVE        ); }
REPLACE                { return sym(REPLACE       ); }
REQID                  { return sym(REQID         ); }
REQUESTMODEL           { return sym(REQUESTMODEL  ); }
RESET                  { return sym(RESET         ); }
RESETBR                { return sym(RESETBR       ); }
RESETTIME              { return sym(RESETTIME     ); }
RESUME                 { return sym(RESUME        ); }
RESYNC                 { return sym(RESYNC        ); }
RETRIEVE               { return sym(RETRIEVE      ); }
RETURN                 { return sym(RETURN        ); }
REWIND                 { return sym(REWIND        ); }
REWRITE                { return sym(REWRITE       ); }
ROLLBACK               { return sym(ROLLBACK      ); }
ROUTE                  { return sym(ROUTE         ); }
RRMS                   { return sym(RRMS          ); }
RUN                    { return sym(RUN           ); }
SECURITY               { return sym(SECURITY      ); }
SEND                   { return sym(SEND          ); }
SERVICE                { return sym(SERVICE       ); }
SESSIONS               { return sym(SESSIONS      ); }
SET                    { return sym(SET           ); }
SHUTDOWN               { return sym(SHUTDOWN      ); }
SIGNAL                 { return sym(SIGNAL        ); }
SIGNOFF                { return sym(SIGNOFF       ); }
SIGNON                 { return sym(SIGNON        ); }
SOAPFAULT              { return sym(SOAPFAULT     ); }
SPOOLCLOSE             { return sym(SPOOLCLOSE    ); }
SPOOLOPEN              { return sym(SPOOLOPEN     ); }
SPOOLREAD              { return sym(SPOOLREAD     ); }
SPOOLWRITE             { return sym(SPOOLWRITE    ); }
START                  { return sym(START         ); }
STARTBR                { return sym(STARTBR       ); }
STARTBROWSE            { return sym(STARTBROWSE   ); }
STATISTICS             { return sym(STATISTICS    ); }
STORAGE                { return sym(STORAGE       ); }
STREAMNAME             { return sym(STREAMNAME    ); }
SUBEVENT               { return sym(SUBEVENT      ); }
SUBPOOL                { return sym(SUBPOOL       ); }
SUSPEND                { return sym(SUSPEND       ); }
SYNCPOINT              { return sym(SYNCPOINT     ); }
SYSDUMPCODE            { return sym(SYSDUMPCODE   ); }
SYSTEM                 { return sym(SYSTEM        ); }
TASK                   { return sym(TASK          ); }
TCLASS                 { return sym(TCLASS        ); }
TCPIP                  { return sym(TCPIP         ); }
TCPIPSERVICE           { return sym(TCPIPSERVICE  ); }
TCT                    { return sym(TCT           ); }
TD                     { return sym(TD            ); }
TDQUEUE                { return sym(TDQUEUE       ); }
TERMINAL               { return sym(TERMINAL      ); }
TEST                   { return sym(TEST          ); }
TEXT                   { return sym(TEXT          ); }
TIMER                  { return sym(TIMER         ); }
TRACEDEST              { return sym(TRACEDEST     ); }
TRACEFLAG              { return sym(TRACEFLAG     ); }
TRACENUM               { return sym(TRACENUM      ); }
TRACETYPE              { return sym(TRACETYPE     ); }
TRANCLASS              { return sym(TRANCLASS     ); }
TRANDUMPCODE           { return sym(TRANDUMPCODE  ); }
TRANSACTION            { return sym(TRANSACTION   ); }
TRANSFORM              { return sym(TRANSFORM     ); }
TRANSID                { return sym(TRANSID       ); }
TS                     { return sym(TS            ); }
TSMODEL                { return sym(TSMODEL       ); }
TSPOOL                 { return sym(TSPOOL        ); }
TSQNAME                { return sym(TSQNAME       ); }
TSQUEUE                { return sym(TSQUEUE       ); }
TYPETERM               { return sym(TYPETERM      ); }
UNLOCK                 { return sym(UNLOCK        ); }
UOW                    { return sym(UOW           ); }
UOWDSNFAIL             { return sym(UOWDSNFAIL    ); }
UOWENQ                 { return sym(UOWENQ        ); }
UOWLINK                { return sym(UOWLINK       ); }
UPDATE                 { return sym(UPDATE        ); }
URIMAP                 { return sym(URIMAP        ); }
URL                    { return sym(URL           ); }
VERIFY                 { return sym(VERIFY        ); }
VOLUME                 { return sym(VOLUME        ); }
VTAM                   { return sym(VTAM          ); }
WAIT                   { return sym(WAIT          ); }
WAITCICS               { return sym(WAITCICS      ); }
WEB                    { return sym(WEB           ); }
WEBSERVICE             { return sym(WEBSERVICE    ); }
WORKREQUEST            { return sym(WORKREQUEST   ); }
WRITE                  { return sym(WRITE         ); }
WRITEQ                 { return sym(WRITEQ        ); }
WSACONTEXT             { return sym(WSACONTEXT    ); }
WSAEPR                 { return sym(WSAEPR        ); }
XCTL                   { return sym(XCTL          ); }
XMLTODATA              { return sym(XMLTODATA     ); }          



IN            { return sym(IN);         }
LENGTH        { return sym(LENGTH);     }
OF            { return sym(OF);         } 

{ID}          { return sym(ID);         }
{ENTERO}      { return sym(ENTERO);     }
{DECIMAL}     { return sym(DECIMAL);    }
"("           { return sym(LPAR);       }
")"           { return sym(RPAR);       }
\'            { pushState(QUOTE_STRING);   }
\"            { pushState(DQUOTE_STRING);  }  
:             { /* nada */                 }
{SPACES}      { /* nada */                 }
\n            { /* nada */                 }
\r            { /* nada */                 }
.             { /* nada */                 } 

 
<QUOTE_STRING> {
  \'\'         { cadena.append(yytext()); }
  \'           { popState(); return literal(); }
  \n           { popState();              }
  \r           { /* nada */               }
  [^]          { cadena.append(yytext()); }
}

<DQUOTE_STRING> {
  \"\"         { cadena.append(yytext()); }
  \"           { popState(); return literal(); }
  \n           { popState();              }
  \r           { /* nada */               }
  [^]          { cadena.append(yytext()); }
}

<COMMENT> {
 {SPACES}      { }
 \n            { popState(); }  
 \r            { }                
 [^]           { }

}
