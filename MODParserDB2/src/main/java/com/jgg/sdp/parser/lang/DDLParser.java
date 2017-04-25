
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Apr 24 14:21:28 CEST 2017
//----------------------------------------------------

package com.jgg.sdp.parser.lang;

import java_cup.runtime.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.stmt.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Apr 24 14:21:28 CEST 2017
  */
public class DDLParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public DDLParser() {super();}

  /** Constructor which sets the default scanner. */
  public DDLParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public DDLParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\076\000\002\002\003\000\002\002\004\000\002\003" +
    "\003\000\002\003\003\000\002\003\003\000\002\003\003" +
    "\000\002\003\003\000\002\003\003\000\002\004\005\000" +
    "\002\005\003\000\002\005\003\000\002\005\003\000\002" +
    "\005\003\000\002\005\003\000\002\005\003\000\002\005" +
    "\003\000\002\005\003\000\002\005\003\000\002\005\003" +
    "\000\002\005\003\000\002\005\004\000\002\005\003\000" +
    "\002\006\004\000\002\007\005\000\002\010\003\000\002" +
    "\010\004\000\002\010\003\000\002\010\003\000\002\010" +
    "\005\000\002\010\003\000\002\010\003\000\002\010\003" +
    "\000\002\010\003\000\002\010\003\000\002\010\003\000" +
    "\002\010\003\000\002\010\003\000\002\010\003\000\002" +
    "\010\003\000\002\010\003\000\002\010\004\000\002\010" +
    "\003\000\002\010\003\000\002\011\005\000\002\013\007" +
    "\000\002\014\003\000\002\014\003\000\002\014\002\000" +
    "\002\016\003\000\002\016\003\000\002\015\003\000\002" +
    "\015\003\000\002\015\004\000\002\017\003\000\002\017" +
    "\004\000\002\021\003\000\002\021\002\000\002\020\003" +
    "\000\002\020\004\000\002\022\003\000\002\022\003\000" +
    "\002\022\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\115\000\014\013\007\015\017\017\005\021\004\030" +
    "\006\001\002\000\050\012\072\014\101\020\102\022\074" +
    "\023\104\024\110\025\070\026\075\027\066\031\073\032" +
    "\067\033\065\034\103\035\076\036\107\041\071\042\106" +
    "\043\100\044\105\001\002\000\050\012\072\014\101\020" +
    "\102\022\074\023\104\024\110\025\070\026\075\027\066" +
    "\031\073\032\067\033\065\034\103\035\076\036\107\041" +
    "\071\042\106\043\100\044\105\001\002\000\006\024\051" +
    "\035\053\001\uffd2\000\034\020\031\022\041\024\032\025" +
    "\044\026\030\027\033\032\034\033\035\035\040\036\042" +
    "\041\036\042\043\044\037\001\002\000\002\001\001\000" +
    "\002\001\ufffa\000\002\001\ufffe\000\002\001\uffff\000\002" +
    "\001\ufffd\000\002\001\ufffc\000\002\001\ufffb\000\010\004" +
    "\023\005\025\006\022\001\002\000\004\002\021\001\002" +
    "\000\002\001\000\000\002\001\uffc4\000\002\001\uffc6\000" +
    "\002\001\uffc8\000\002\001\uffc5\000\010\004\023\005\025" +
    "\006\022\001\uffeb\000\002\001\uffc7\000\002\001\ufff4\000" +
    "\002\001\ufff8\000\002\001\ufff6\000\002\001\ufff3\000\002" +
    "\001\ufff2\000\002\001\ufff1\000\002\001\uffee\000\002\001" +
    "\uffec\000\002\001\ufff0\000\002\001\ufff7\000\002\001\uffef" +
    "\000\004\016\050\001\002\000\002\001\ufff5\000\010\004" +
    "\023\005\025\006\022\001\uffc9\000\002\001\ufff9\000\010" +
    "\004\023\005\025\006\022\001\uffca\000\002\001\uffed\000" +
    "\002\001\uffd3\000\012\004\055\007\057\010\056\011\061" +
    "\001\002\000\002\001\uffd4\000\002\001\uffd1\000\002\001" +
    "\uffd0\000\002\001\uffce\000\002\001\uffcf\000\004\040\063" +
    "\001\002\000\004\004\062\001\002\000\002\001\uffcd\000" +
    "\012\004\055\007\057\010\056\011\061\001\002\000\002" +
    "\001\uffd5\000\002\001\uffde\000\002\001\uffe1\000\002\001" +
    "\uffdf\000\002\001\uffe3\000\002\001\uffda\000\002\001\uffe9" +
    "\000\002\001\uffe0\000\002\001\uffe6\000\002\001\uffe2\000" +
    "\002\001\uffdc\000\010\004\023\005\025\006\022\001\uffc9" +
    "\000\002\001\uffd8\000\004\035\114\001\002\000\002\001" +
    "\uffe7\000\002\001\uffdd\000\004\037\112\001\002\000\002" +
    "\001\uffd7\000\004\016\111\001\002\000\002\001\uffdb\000" +
    "\002\001\uffe4\000\002\001\uffd9\000\004\035\113\001\002" +
    "\000\002\001\uffe5\000\002\001\uffe8\000\002\001\uffea\000" +
    "\010\004\023\005\025\006\022\001\uffc9\000\002\001\uffd6" +
    "" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\115\000\022\002\017\003\007\004\012\006\011\007" +
    "\013\011\014\012\015\013\010\001\001\000\004\010\115" +
    "\001\001\000\004\010\076\001\001\000\004\014\051\001" +
    "\001\000\004\005\044\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\020\025" +
    "\022\023\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\004\022\026\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\010\020\046\021\045\022\023\001\001\000\002\001\001" +
    "\000\004\022\026\001\001\000\002\001\001\000\002\001" +
    "\001\000\006\015\053\016\057\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\015\053\016\063\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\010\020\046\021\114\022\023\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\010\020\046\021\116\022\023\001\001" +
    "\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$DDLParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$DDLParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$DDLParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** User initialization code. */
  public void user_init() throws java.lang.Exception
    {

  action_obj.initActionClass();

    }


   ParserInfo    info     = ParserInfo.getInstance();
   public void syntax_error(Symbol token) throws ParseException {
          info.syntax_error(token);
   }

   public void unrecovered_syntax_error(Symbol token) throws ParseException {
      info.unrecovered_syntax_error(token);
   }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$DDLParser$actions {


   ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
   ParserInfo      info     = ParserInfo.getInstance();
   StmtSQL         stmt     = new StmtSQL();
   
   public void initActionClass() {
   }

   public void print(String txt) { 
      System.out.println(txt); 
   }


  private final DDLParser parser;

  /** Constructor */
  CUP$DDLParser$actions(DDLParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$DDLParser$do_action(
    int                        CUP$DDLParser$act_num,
    java_cup.runtime.lr_parser CUP$DDLParser$parser,
    java.util.Stack            CUP$DDLParser$stack,
    int                        CUP$DDLParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$DDLParser$result;

      /* select the action based on the action number */
      switch (CUP$DDLParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 61: // eat_token ::= COMMA 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat_token",16, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 60: // eat_token ::= LITERAL 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat_token",16, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 59: // eat_token ::= ID 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat_token",16, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 58: // eat ::= eat eat_token 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat",14, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 57: // eat ::= eat_token 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat",14, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 56: // eat_opt ::= 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat_opt",15, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 55: // eat_opt ::= eat 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("eat_opt",15, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 54: // literal ::= literal LITERAL 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("literal",13, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 53: // literal ::= LITERAL 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("literal",13, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 52: // host_variable ::= PREHOST ID 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("host_variable",11, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 51: // host_variable ::= HOSTVAR2 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("host_variable",11, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 50: // host_variable ::= HOSTVAR1 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("host_variable",11, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 49: // var_id ::= ID 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("var_id",12, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 48: // var_id ::= host_variable 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("var_id",12, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 47: // object_opt ::= 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_opt",10, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 46: // object_opt ::= INDEX 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_opt",10, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 45: // object_opt ::= TABLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_opt",10, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 44: // sql_rename ::= RENAME object_opt var_id TO var_id 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_rename",9, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-4)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 43: // sql_drop ::= DROP object_type eat_opt 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_drop",7, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 42: // object_type ::= VIEW 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 41: // object_type ::= TYPE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 40: // object_type ::= TRUSTED CONTEXT 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 39: // object_type ::= TRIGGER 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 38: // object_type ::= TABLESPACE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // object_type ::= TABLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // object_type ::= SYNONIM 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // object_type ::= STOGROUP 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // object_type ::= SEQUENCE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // object_type ::= ROLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // object_type ::= PROCEDURE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // object_type ::= PERMISSION 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // object_type ::= MASK 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // object_type ::= INDEX 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // object_type ::= GLOBAL TEMPORARY TABLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // object_type ::= FUNCTION 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // object_type ::= DATABASE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // object_type ::= AUXILIARY TABLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // object_type ::= ALIAS 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("object_type",6, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // sql_create ::= CREATE object_type eat_opt 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_create",5, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // sql_comment ::= COMMENT eat 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_comment",4, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // alter_type ::= VIEW 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // alter_type ::= TRUSTED CONTEXT 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // alter_type ::= TRIGGER 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // alter_type ::= TABLESPACE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // alter_type ::= TABLE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // alter_type ::= STOGROUP 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // alter_type ::= SEQUENCE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // alter_type ::= PROCEDURE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // alter_type ::= PERMISSION 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // alter_type ::= MASK 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // alter_type ::= INDEX 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // alter_type ::= FUNCTION 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // alter_type ::= DATABASE 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("alter_type",3, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // sql_alter ::= ALTER alter_type eat_opt 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_alter",2, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // sql_ddl ::= sql_rename 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // sql_ddl ::= sql_label 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // sql_ddl ::= sql_drop 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // sql_ddl ::= sql_create 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // sql_ddl ::= sql_comment 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // sql_ddl ::= sql_alter 
            {
              Object RESULT =null;

              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql_ddl",1, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= sql EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)).right;
		Symbol start_val = (Symbol)((java_cup.runtime.Symbol) CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)).value;
		RESULT = start_val;
              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.elementAt(CUP$DDLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$DDLParser$parser.done_parsing();
          return CUP$DDLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // sql ::= sql_ddl 
            {
              Symbol RESULT =null;
		 stmt.adjust(info.getLastSymbol()); 
                    String name = stmt.getVerbName();
                    int id =  stmt.getVerbId();
                    int line = stmt.getVerb().left;
                    int col = stmt.getVerb().right;
                    RESULT = symbolFactory.newSymbol(name, id, new Symbol(id, line, col, stmt));  
                
              CUP$DDLParser$result = parser.getSymbolFactory().newSymbol("sql",0, ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DDLParser$stack.peek()), RESULT);
            }
          return CUP$DDLParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

