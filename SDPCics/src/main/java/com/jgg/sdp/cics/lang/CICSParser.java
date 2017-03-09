
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Sun Feb 05 13:53:56 CET 2017
//----------------------------------------------------

package com.jgg.sdp.cics.lang;

import java_cup.runtime.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.parser.base.ParseException;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.cics.base.*;
import com.jgg.sdp.cics.code.*;
import com.jgg.sdp.cics.parser.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Sun Feb 05 13:53:56 CET 2017
  */
public class CICSParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public CICSParser() {super();}

  /** Constructor which sets the default scanner. */
  public CICSParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public CICSParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\040\000\002\002\003\000\002\002\004\000\002\002" +
    "\002\000\002\003\003\000\002\003\004\000\002\004\005" +
    "\000\002\005\003\000\002\006\003\000\002\006\002\000" +
    "\002\007\003\000\002\007\004\000\002\010\004\000\002" +
    "\010\004\000\002\011\005\000\002\011\002\000\002\012" +
    "\003\000\002\012\004\000\002\013\003\000\002\013\003" +
    "\000\002\013\003\000\002\013\005\000\002\013\005\000" +
    "\002\014\003\000\002\014\003\000\002\015\004\000\002" +
    "\016\003\000\002\016\002\000\002\017\003\000\002\017" +
    "\004\000\002\020\004\000\002\021\003\000\002\021\003" +
    "" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\052\000\006\002\uffff\006\004\001\002\000\010\006" +
    "\ufffb\010\ufffb\015\ufffb\001\002\000\006\002\ufffe\006\ufffe" +
    "\001\002\000\006\002\001\006\004\001\002\000\004\002" +
    "\053\001\002\000\010\006\011\010\014\015\ufff9\001\002" +
    "\000\012\006\ufff3\010\ufff3\012\020\015\ufff3\001\002\000" +
    "\010\006\ufff8\010\ufff8\015\ufff8\001\002\000\004\015\051" +
    "\001\002\000\012\006\ufff3\010\ufff3\012\020\015\ufff3\001" +
    "\002\000\010\006\011\010\014\015\ufffa\001\002\000\010" +
    "\006\ufff7\010\ufff7\015\ufff7\001\002\000\010\006\ufff5\010" +
    "\ufff5\015\ufff5\001\002\000\016\004\021\005\024\006\031" +
    "\010\022\011\023\012\026\001\002\000\020\004\uffea\005" +
    "\uffea\006\uffea\010\uffea\011\uffea\012\uffea\014\uffea\001\002" +
    "\000\004\013\047\001\002\000\020\004\uffee\005\uffee\006" +
    "\uffee\010\uffee\011\uffee\012\uffee\014\uffee\001\002\000\020" +
    "\004\uffeb\005\uffeb\006\uffeb\010\uffeb\011\uffeb\012\uffeb\014" +
    "\uffeb\001\002\000\020\004\uffef\005\uffef\006\uffef\010\uffef" +
    "\011\uffef\012\uffef\014\uffef\001\002\000\016\004\021\005" +
    "\024\006\031\010\022\011\023\012\026\001\002\000\020" +
    "\004\ufff0\005\ufff0\006\ufff0\010\ufff0\011\ufff0\012\ufff0\014" +
    "\ufff0\001\002\000\020\004\ufff2\005\ufff2\006\ufff2\010\ufff2" +
    "\011\ufff2\012\ufff2\014\ufff2\001\002\000\024\004\uffe7\005" +
    "\uffe7\006\uffe7\007\036\010\uffe7\011\uffe7\012\uffe7\013\042" +
    "\014\uffe7\001\002\000\020\004\021\005\024\006\031\010" +
    "\022\011\023\012\026\014\034\001\002\000\020\004\ufff1" +
    "\005\ufff1\006\ufff1\010\ufff1\011\ufff1\012\ufff1\014\ufff1\001" +
    "\002\000\010\006\ufff4\010\ufff4\015\ufff4\001\002\000\024" +
    "\004\uffe8\005\uffe8\006\uffe8\007\036\010\uffe8\011\uffe8\012" +
    "\uffe8\013\042\014\uffe8\001\002\000\004\006\uffe2\001\002" +
    "\000\024\004\uffe6\005\uffe6\006\uffe6\007\uffe6\010\uffe6\011" +
    "\uffe6\012\uffe6\013\uffe6\014\uffe6\001\002\000\004\006\043" +
    "\001\002\000\020\004\uffe9\005\uffe9\006\uffe9\010\uffe9\011" +
    "\uffe9\012\uffe9\014\uffe9\001\002\000\004\006\uffe3\001\002" +
    "\000\024\004\uffe4\005\uffe4\006\uffe4\007\uffe4\010\uffe4\011" +
    "\uffe4\012\uffe4\013\uffe4\014\uffe4\001\002\000\024\004\uffe5" +
    "\005\uffe5\006\uffe5\007\uffe5\010\uffe5\011\uffe5\012\uffe5\013" +
    "\uffe5\014\uffe5\001\002\000\020\004\021\005\024\006\031" +
    "\010\022\011\023\012\026\014\046\001\002\000\020\004" +
    "\uffec\005\uffec\006\uffec\010\uffec\011\uffec\012\uffec\014\uffec" +
    "\001\002\000\004\006\031\001\002\000\020\004\uffed\005" +
    "\uffed\006\uffed\010\uffed\011\uffed\012\uffed\014\uffed\001\002" +
    "\000\006\002\ufffc\006\ufffc\001\002\000\010\006\ufff6\010" +
    "\ufff6\015\ufff6\001\002\000\004\002\000\001\002\000\006" +
    "\002\ufffd\006\ufffd\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\052\000\012\002\006\003\005\004\004\005\007\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\004\053" +
    "\005\007\001\001\000\002\001\001\000\010\006\012\007" +
    "\014\010\011\001\001\000\004\011\051\001\001\000\002" +
    "\001\001\000\002\001\001\000\004\011\016\001\001\000" +
    "\004\010\015\001\001\000\002\001\001\000\002\001\001" +
    "\000\012\012\031\013\027\014\024\015\026\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\012\012\044\013\027\014" +
    "\024\015\026\001\001\000\002\001\001\000\002\001\001" +
    "\000\012\016\040\017\034\020\036\021\037\001\001\000" +
    "\010\013\032\014\024\015\026\001\001\000\002\001\001" +
    "\000\002\001\001\000\006\020\043\021\037\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\010\013\032\014\024\015\026\001\001\000\002" +
    "\001\001\000\004\015\047\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$CICSParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$CICSParser$actions(this);
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
    return action_obj.CUP$CICSParser$do_action(act_num, parser, stack, top);
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

  action_obj.initActionClass(fullName);

    }


      String fullName   = ((ILexer) getScanner()).getFullName();
      Module module     = ModulesFactory.getMainModule();
      Configuration cfg = Configuration.getInstance();
          
      public String getFullName() { return fullName; }

      public void syntax_error(Symbol token) {
           
          Symbol s = (Symbol) token.value;
          int col = cfg.getInteger(CFG.MARGIN_LEFT,  0);
          col = col + s.right + 1;

          throw new ParseException(MSG.EXCEPTION_SYNTAX, 
                                   module.getName(), 
                                   s.left + 1, 
                                   col, 
                                   (String) s.value);
      }

      public void unrecovered_syntax_error(Symbol token) throws Exception {
          Symbol s = (Symbol) token.value;
          throw new ParseException(MSG.EXCEPTION_CUP, 
                                  module.getName(), s.left + 1, s.right + 1, (String) s.value); 
      }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$CICSParser$actions {


   Module      module   = null;
   CICSCode    code     = null;
   CICSStmt    stmt     = null;
         
   public void initActionClass(String name) {
      module = ModulesFactory.getMainModule();
      code = new CICSCode(module);
   }

   public String getSymbolName(Symbol s)    { return (String) s.value; }
   public void debug(String txt) { System.err.println(txt); }


  private final CICSParser parser;

  /** Constructor */
  CUP$CICSParser$actions(CICSParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$CICSParser$do_action(
    int                        CUP$CICSParser$act_num,
    java_cup.runtime.lr_parser CUP$CICSParser$parser,
    java.util.Stack            CUP$CICSParser$stack,
    int                        CUP$CICSParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$CICSParser$result;

      /* select the action based on the action number */
      switch (CUP$CICSParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // padre_indicator ::= IN 
            {
              Symbol RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padre_indicator",15, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // padre_indicator ::= OF 
            {
              Symbol RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padre_indicator",15, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // padre ::= padre_indicator ID 
            {
              Symbol RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = s;    
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padre",14, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // padres ::= padres padre 
            {
              Symbol RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol p = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = i; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padres",13, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // padres ::= padre 
            {
              Symbol RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol p = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = p; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padres",13, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // padres_lst ::= 
            {
              Symbol RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padres_lst",12, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // padres_lst ::= padres 
            {
              Symbol RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol p = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = p;    
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padres_lst",12, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // ident_base ::= ID padres_lst 
            {
              Symbol RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = i; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("ident_base",11, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // number ::= DECIMAL 
            {
              Symbol RESULT =null;
		int dleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int dright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol d = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = d; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("number",10, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // number ::= ENTERO 
            {
              Symbol RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol e = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = e; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("number",10, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // cics_value ::= LPAR cics_values RPAR 
            {
              Symbol RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		 RESULT = i; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_value",9, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // cics_value ::= LENGTH OF ident_base 
            {
              Symbol RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = i; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_value",9, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // cics_value ::= LITERAL 
            {
              Symbol RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int lright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol l = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = l; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_value",9, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // cics_value ::= number 
            {
              Symbol RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol n = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = n; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_value",9, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // cics_value ::= ident_base 
            {
              Symbol RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = i; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_value",9, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // cics_values ::= cics_values cics_value 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		 RESULT = c; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_values",8, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // cics_values ::= cics_value 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = c; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_values",8, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // cics_parm ::= 
            {
              Symbol RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm",7, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // cics_parm ::= LPAR cics_values RPAR 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		 RESULT = c;    
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm",7, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // cics_parm_attr ::= LENGTH cics_parm 
            {
              CICSParm RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = new CICSParm(i,c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_attr",6, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // cics_parm_attr ::= ID cics_parm 
            {
              CICSParm RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = new CICSParm(i,c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_attr",6, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // cics_parm_list ::= cics_parm_list cics_parm_attr 
            {
              CICSParm RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSParm c = (CICSParm)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = stmt.addParm(c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_list",5, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // cics_parm_list ::= cics_parm_attr 
            {
              CICSParm RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSParm c = (CICSParm)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = stmt.addParm(c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_list",5, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // cics_options ::= 
            {
              CICSParm RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_options",4, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // cics_options ::= cics_parm_list 
            {
              CICSParm RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSParm c = (CICSParm)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = c;    
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_options",4, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // cics_stmt ::= ID 
            {
              CICSStmt RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 stmt = new CICSStmt((String) i.value); RESULT = stmt; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_stmt",3, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // cics_statement ::= cics_stmt cics_options END_EXEC 
            {
              CICSStmt RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)).right;
		CICSStmt s = (CICSStmt)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		CICSParm o = (CICSParm)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		 RESULT = s; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_statement",2, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-2)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // cics_stmts ::= cics_stmts cics_statement 
            {
              CICSStmt RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSStmt s = (CICSStmt)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = code.processCICSStatement(s); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_stmts",1, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // cics_stmts ::= cics_statement 
            {
              CICSStmt RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSStmt s = (CICSStmt)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = code.processCICSStatement(s); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_stmts",1, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // ax ::= 
            {
              CICSStmt RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("ax",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= ax EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		CICSStmt start_val = (CICSStmt)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		RESULT = start_val;
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$CICSParser$parser.done_parsing();
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // ax ::= cics_stmts 
            {
              CICSStmt RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		CICSStmt s = (CICSStmt)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("ax",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

