
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Tue Sep 19 23:58:58 CEST 2017
//----------------------------------------------------

package com.jgg.sdp.parser.lang;

import java_cup.runtime.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.stmt.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Tue Sep 19 23:58:58 CEST 2017
  */
public class DCLParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public DCLParser() {super();}

  /** Constructor which sets the default scanner. */
  public DCLParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public DCLParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\075\000\002\002\003\000\002\002\004\000\002\003" +
    "\003\000\002\003\003\000\002\003\003\000\002\003\003" +
    "\000\002\003\003\000\002\033\002\000\002\004\006\000" +
    "\002\005\003\000\002\005\003\000\002\005\003\000\002" +
    "\005\003\000\002\005\003\000\002\006\005\000\002\007" +
    "\003\000\002\007\003\000\002\007\003\000\002\007\003" +
    "\000\002\010\006\000\002\011\003\000\002\011\003\000" +
    "\002\011\002\000\002\012\007\000\002\013\003\000\002" +
    "\013\003\000\002\014\003\000\002\014\005\000\002\017" +
    "\004\000\002\017\003\000\002\017\003\000\002\020\007" +
    "\000\002\021\003\000\002\021\002\000\002\022\004\000" +
    "\002\022\006\000\002\023\003\000\002\023\005\000\002" +
    "\024\003\000\002\024\004\000\002\024\003\000\002\026" +
    "\005\000\002\026\002\000\002\025\003\000\002\025\003" +
    "\000\002\025\003\000\002\025\003\000\002\025\003\000" +
    "\002\025\003\000\002\025\003\000\002\025\003\000\002" +
    "\025\003\000\002\027\003\000\002\027\003\000\002\027" +
    "\003\000\002\030\003\000\002\030\004\000\002\031\003" +
    "\000\002\031\003\000\002\031\003\000\002\031\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\125\000\014\016\007\020\016\022\014\023\013\041" +
    "\015\001\002\000\002\001\ufffb\000\002\001\ufffc\000\002" +
    "\001\ufffd\000\002\001\ufffa\000\002\001\001\000\002\001" +
    "\ufffe\000\002\001\uffff\000\002\001\002\000\006\011\106" +
    "\045\104\001\uffeb\000\002\001\002\000\012\006\022\034" +
    "\024\035\021\046\023\001\002\000\004\002\020\001\002" +
    "\000\002\001\000\000\002\001\ufff2\000\002\001\ufff1\000" +
    "\002\001\ufff0\000\002\001\uffef\000\012\004\027\010\030" +
    "\026\032\043\033\001\002\000\002\001\uffca\000\002\001" +
    "\uffc8\000\002\001\uffc5\000\012\004\027\010\030\026\032" +
    "\043\033\001\ufff3\000\002\001\uffc7\000\002\001\uffc6\000" +
    "\002\001\uffc9\000\002\001\uffe8\000\002\001\uffe9\000\004" +
    "\021\040\001\002\000\010\004\041\040\044\042\042\001" +
    "\002\000\002\001\uffe3\000\004\004\103\001\002\000\002" +
    "\001\uffe7\000\002\001\uffe4\000\006\007\052\010\046\001" +
    "\uffe0\000\010\004\041\040\044\042\042\001\002\000\002" +
    "\001\uffe1\000\004\024\077\001\002\000\002\001\uffe2\000" +
    "\034\004\067\005\057\006\062\013\054\014\070\027\060" +
    "\030\066\031\072\042\061\044\064\050\071\051\055\053" +
    "\073\001\002\000\002\001\uffdb\000\002\001\uffd0\000\002" +
    "\001\uffcf\000\002\001\uffdd\000\002\001\uffcc\000\002\001" +
    "\uffd5\000\030\004\067\005\057\013\054\014\070\027\060" +
    "\030\066\031\072\044\064\050\071\051\055\053\073\001" +
    "\002\000\002\001\uffd9\000\002\001\uffcb\000\002\001\uffce" +
    "\000\004\010\074\001\uffdf\000\002\001\uffd1\000\002\001" +
    "\uffcd\000\002\001\uffd6\000\002\001\uffd2\000\002\001\uffd4" +
    "\000\002\001\uffd3\000\034\004\067\005\057\006\062\013" +
    "\054\014\070\027\060\030\066\031\072\042\061\044\064" +
    "\050\071\051\055\053\073\001\002\000\002\001\uffdc\000" +
    "\002\001\uffda\000\004\015\100\001\002\000\004\036\101" +
    "\001\002\000\002\001\uffde\000\002\001\uffe6\000\002\001" +
    "\uffe5\000\002\001\uffec\000\004\017\107\001\002\000\002" +
    "\001\uffed\000\012\004\027\010\030\026\032\043\033\001" +
    "\002\000\012\004\027\010\030\026\032\043\033\001\uffee" +
    "\000\004\052\112\001\002\000\010\004\041\040\044\042" +
    "\042\001\002\000\006\010\046\054\114\001\uffd7\000\004" +
    "\023\116\001\002\000\002\001\uffea\000\004\032\117\001" +
    "\002\000\002\001\uffd8\000\014\012\125\025\124\033\121" +
    "\037\123\047\126\001\002\000\002\001\ufff6\000\012\004" +
    "\027\010\030\026\032\043\033\001\002\000\002\001\ufff5" +
    "\000\002\001\ufff7\000\002\001\ufff8\000\002\001\ufff4\000" +
    "\012\004\027\010\030\026\032\043\033\001\ufff9" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\125\000\020\002\016\003\007\004\011\006\010\010" +
    "\005\012\004\020\003\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\004\033\117\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\010" +
    "\013\110\015\035\016\034\001\001\000\004\011\104\001" +
    "\001\000\010\013\036\015\035\016\034\001\001\000\004" +
    "\007\024\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\030\030\031\025\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\031\033" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\006\014\044\017\042\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\010" +
    "\021\050\022\046\032\047\001\001\000\004\017\101\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\012\023\064\024\055\025\062\027\052\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\006\025" +
    "\062\027\075\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\010\024\074\025\062\027" +
    "\052\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\006\030\107\031\025\001\001\000" +
    "\004\031\033\001\001\000\002\001\001\000\006\014\112" +
    "\017\042\001\001\000\004\026\114\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\005\121\001\001\000\002\001\001\000\006\030" +
    "\126\031\025\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\031\033\001" +
    "\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$DCLParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$DCLParser$actions(this);
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
    return action_obj.CUP$DCLParser$do_action(act_num, parser, stack, top);
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
class CUP$DCLParser$actions {


   ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
   ParserInfo      info     = ParserInfo.getInstance();
   StmtSQL         stmt     = new StmtSQL();
   
   public void initActionClass() {
   }

   public void print(String txt) { 
      System.out.println(txt); 
   }


  private final DCLParser parser;

  /** Constructor */
  CUP$DCLParser$actions(DCLParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$DCLParser$do_action(
    int                        CUP$DCLParser$act_num,
    java_cup.runtime.lr_parser CUP$DCLParser$parser,
    java.util.Stack            CUP$DCLParser$stack,
    int                        CUP$DCLParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$DCLParser$result;

      /* select the action based on the action number */
      switch (CUP$DCLParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 60: // eat_token ::= COMMA 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat_token",23, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 59: // eat_token ::= RPAR 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat_token",23, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 58: // eat_token ::= LPAR 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat_token",23, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 57: // eat_token ::= ID 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat_token",23, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 56: // eat ::= eat eat_token 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat",22, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 55: // eat ::= eat_token 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("eat",22, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 54: // id ::= non_reserved_words 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("id",21, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 53: // id ::= LITERAL 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("id",21, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 52: // id ::= ID 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("id",21, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 51: // non_reserved_words ::= SMALLINT 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 50: // non_reserved_words ::= TIME 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 49: // non_reserved_words ::= DATE 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 48: // non_reserved_words ::= MESSAGE_TEXT 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 47: // non_reserved_words ::= TIMESTAMP 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 46: // non_reserved_words ::= VERSION 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 45: // non_reserved_words ::= MIN 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 44: // non_reserved_words ::= MAX 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 43: // non_reserved_words ::= DEC 
            {
              Symbol RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("non_reserved_words",19, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 42: // opt_grant ::= 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("opt_grant",20, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 41: // opt_grant ::= WITH GRANT OPTION 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("opt_grant",20, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 40: // clause_revoke_role ::= ALL 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_role",18, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 39: // clause_revoke_role ::= ROLE id 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_role",18, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 38: // clause_revoke_role ::= id 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_role",18, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // clause_revoke_role_lst ::= clause_revoke_role_lst COMMA clause_revoke_role 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_role_lst",17, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // clause_revoke_role_lst ::= clause_revoke_role 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_role_lst",17, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // clause_revoke_lst ::= opt_not INCLUDING DEPENDENT PRIVILEGES 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_lst",16, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // clause_revoke_lst ::= BY clause_revoke_role_lst 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_lst",16, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // clause_revoke_opt ::= 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_opt",15, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // clause_revoke_opt ::= clause_revoke_lst 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("clause_revoke_opt",15, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // sql_revoke ::= REVOKE grant_class FROM grant_target_lst clause_revoke_opt 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_revoke",14, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-4)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // grant_target ::= ID 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_target",13, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // grant_target ::= PUBLIC 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_target",13, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // grant_target ::= ROLE ID 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_target",13, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // grant_target_lst ::= grant_target_lst COMMA grant_target 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_target_lst",10, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // grant_target_lst ::= grant_target 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_target_lst",10, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // grant_class ::= grant_use 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_class",9, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // grant_class ::= grant_access_lst 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("grant_class",9, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // sql_grant ::= GRANT grant_class TO grant_target_lst opt_grant 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_grant",8, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-4)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // get_type ::= 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("get_type",7, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // get_type ::= STACKED 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("get_type",7, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // get_type ::= CURRENT 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("get_type",7, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // sql_get ::= GET get_type DIAGNOSTICS eat 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_get",6, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // explain_type ::= PACKAGE 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("explain_type",5, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // explain_type ::= STMTCACHE 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("explain_type",5, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // explain_type ::= ALL 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("explain_type",5, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // explain_type ::= PLAN 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("explain_type",5, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // sql_explain ::= EXPLAIN explain_type eat 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_explain",4, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-2)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // describe_verbs ::= TABLE 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("describe_verbs",3, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // describe_verbs ::= PROCEDURE 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("describe_verbs",3, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // describe_verbs ::= OUTPUT 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("describe_verbs",3, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // describe_verbs ::= INPUT 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("describe_verbs",3, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // describe_verbs ::= CURSOR 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("describe_verbs",3, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // sql_describe ::= DESCRIBE NT$0 describe_verbs eat 
            {
              Object RESULT =null;
              // propagate RESULT from NT$0
                RESULT = (Object) ((java_cup.runtime.Symbol) CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-2)).value;
		int vleft = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)).left;
		int vright = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)).right;
		Symbol v = (Symbol)((java_cup.runtime.Symbol) CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)).value;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_describe",2, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-3)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // NT$0 ::= 
            {
              Object RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()).left;
		int vright = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()).right;
		Symbol v = (Symbol)((java_cup.runtime.Symbol) CUP$DCLParser$stack.peek()).value;
 stmt.setVerb(v); 
              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("NT$0",25, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // sql_dcl ::= sql_revoke 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_dcl",1, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // sql_dcl ::= sql_grant 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_dcl",1, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // sql_dcl ::= sql_get 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_dcl",1, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // sql_dcl ::= sql_explain 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_dcl",1, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // sql_dcl ::= sql_describe 
            {
              Object RESULT =null;

              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql_dcl",1, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= sql EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)).right;
		Symbol start_val = (Symbol)((java_cup.runtime.Symbol) CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)).value;
		RESULT = start_val;
              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.elementAt(CUP$DCLParser$top-1)), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$DCLParser$parser.done_parsing();
          return CUP$DCLParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // sql ::= sql_dcl 
            {
              Symbol RESULT =null;
		 stmt.adjust(info.getLastSymbol()); 
                    String name = stmt.getVerbName();
                    int id =  stmt.getVerbId();
                    int line = stmt.getVerb().left;
                    int col = stmt.getVerb().right;
                    RESULT = symbolFactory.newSymbol(name, id, new Symbol(id, line, col, stmt));  
                 
              CUP$DCLParser$result = parser.getSymbolFactory().newSymbol("sql",0, ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$DCLParser$stack.peek()), RESULT);
            }
          return CUP$DCLParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

