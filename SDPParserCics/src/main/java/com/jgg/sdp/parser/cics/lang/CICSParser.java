
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Thu Mar 02 12:03:56 CET 2017
//----------------------------------------------------

package com.jgg.sdp.parser.cics.lang;

import java_cup.runtime.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.parser.cics.base.*;
import com.jgg.sdp.parser.cics.code.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Thu Mar 02 12:03:56 CET 2017
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
    "\000\037\000\002\002\003\000\002\002\004\000\002\002" +
    "\002\000\002\003\003\000\002\004\004\000\002\005\003" +
    "\000\002\006\003\000\002\006\002\000\002\007\003\000" +
    "\002\007\004\000\002\010\004\000\002\010\004\000\002" +
    "\011\005\000\002\011\002\000\002\012\003\000\002\012" +
    "\004\000\002\013\003\000\002\013\003\000\002\013\003" +
    "\000\002\013\005\000\002\013\005\000\002\014\003\000" +
    "\002\014\003\000\002\015\004\000\002\016\003\000\002" +
    "\016\002\000\002\017\003\000\002\017\004\000\002\020" +
    "\004\000\002\021\003\000\002\021\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\050\000\004\006\004\001\uffff\000\002\001\ufffc\000" +
    "\004\002\052\001\002\000\002\001\ufffe\000\002\001\001" +
    "\000\006\006\012\010\014\001\ufffa\000\002\001\ufffd\000" +
    "\004\012\020\001\ufff4\000\002\001\ufff9\000\004\012\020" +
    "\001\ufff4\000\006\006\012\010\014\001\ufffb\000\002\001" +
    "\ufff8\000\002\001\ufff6\000\016\004\021\005\024\006\031" +
    "\010\022\011\023\012\026\001\002\000\002\001\uffeb\000" +
    "\004\013\047\001\002\000\002\001\uffef\000\002\001\uffec" +
    "\000\002\001\ufff0\000\016\004\021\005\024\006\031\010" +
    "\022\011\023\012\026\001\002\000\002\001\ufff1\000\002" +
    "\001\ufff3\000\006\007\036\013\042\001\uffe8\000\020\004" +
    "\021\005\024\006\031\010\022\011\023\012\026\014\034" +
    "\001\002\000\002\001\ufff2\000\002\001\ufff5\000\006\007" +
    "\036\013\042\001\uffe9\000\002\001\uffe3\000\002\001\uffe7" +
    "\000\004\006\043\001\002\000\002\001\uffea\000\002\001" +
    "\uffe4\000\002\001\uffe5\000\002\001\uffe6\000\020\004\021" +
    "\005\024\006\031\010\022\011\023\012\026\014\046\001" +
    "\002\000\002\001\uffed\000\004\006\031\001\002\000\002" +
    "\001\uffee\000\002\001\ufff7\000\002\001\000" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\050\000\012\002\004\003\006\004\005\005\007\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\010\006\010\007\014\010\012\001" +
    "\001\000\002\001\001\000\004\011\050\001\001\000\002" +
    "\001\001\000\004\011\016\001\001\000\004\010\015\001" +
    "\001\000\002\001\001\000\002\001\001\000\012\012\031" +
    "\013\027\014\024\015\026\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\012\012\044\013\027\014\024\015\026\001" +
    "\001\000\002\001\001\000\002\001\001\000\012\016\040" +
    "\017\034\020\036\021\037\001\001\000\010\013\032\014" +
    "\024\015\026\001\001\000\002\001\001\000\002\001\001" +
    "\000\006\020\043\021\037\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\010\013" +
    "\032\014\024\015\026\001\001\000\002\001\001\000\004" +
    "\015\047\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001" });

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

  action_obj.initActionClass();

    }


      Configuration cfg  = Configuration.getInstance();
      ParserInfo    info = ParserInfo.getInstance();

      public void syntax_error(Symbol token) {
           
          Symbol s = (Symbol) token.value;
          int col = cfg.getInteger(CFG.MARGIN_LEFT,  0);
          col = col + s.right + 1;

          throw new ParseException(MSG.EXCEPTION_SYNTAX, 
                                   info.getModuleName(), 
                                   s.left + info.getOffset(), 
                                   col, 
                                   (String) s.value);
      }

      public void unrecovered_syntax_error(Symbol token) throws Exception {
          Symbol s = (Symbol) token.value;
          throw new ParseException(MSG.EXCEPTION_CUP, 
                                  info.getModuleName(), s.left + info.getOffset(), s.right + 1, (String) s.value); 
      }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$CICSParser$actions {



   CICSCode    code     = new CICSCode();
   StmtCICS    stmt     = null;
   ParserInfo      info     = ParserInfo.getInstance();
   ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();

   public void initActionClass() {
   }

   public String getSymbolName(Symbol s)    { return (String) s.value; }
   public void debug(String txt)            { System.err.println(txt); }


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
          case 30: // padre_indicator ::= IN 
            {
              Symbol RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padre_indicator",15, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // padre_indicator ::= OF 
            {
              Symbol RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padre_indicator",15, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // padre ::= padre_indicator ID 
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
          case 27: // padres ::= padres padre 
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
          case 26: // padres ::= padre 
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
          case 25: // padres_lst ::= 
            {
              Symbol RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("padres_lst",12, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // padres_lst ::= padres 
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
          case 23: // ident_base ::= ID padres_lst 
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
          case 22: // number ::= DECIMAL 
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
          case 21: // number ::= ENTERO 
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
          case 20: // cics_value ::= LPAR cics_values RPAR 
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
          case 19: // cics_value ::= LENGTH OF ident_base 
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
          case 18: // cics_value ::= LITERAL 
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
          case 17: // cics_value ::= number 
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
          case 16: // cics_value ::= ident_base 
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
          case 15: // cics_values ::= cics_values cics_value 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		 RESULT = c; /* Nos interesa el primero */ 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_values",8, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // cics_values ::= cics_value 
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
          case 13: // cics_parm ::= 
            {
              Symbol RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm",7, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // cics_parm ::= LPAR cics_values RPAR 
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
          case 11: // cics_parm_attr ::= LENGTH cics_parm 
            {
              Option RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = new Option(i,c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_attr",6, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // cics_parm_attr ::= ID cics_parm 
            {
              Option RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = new Option(i,c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_attr",6, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // cics_parm_list ::= cics_parm_list cics_parm_attr 
            {
              Option RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Option c = (Option)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = stmt.addParm(c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_list",5, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // cics_parm_list ::= cics_parm_attr 
            {
              Option RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Option c = (Option)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = stmt.addParm(c); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_parm_list",5, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // cics_options ::= 
            {
              Option RESULT =null;
		 RESULT = null; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_options",4, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // cics_options ::= cics_parm_list 
            {
              Option RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Option c = (Option)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = c;    
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_options",4, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // cics_stmt ::= ID 
            {
              StmtCICS RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Symbol i = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 stmt = new StmtCICS(i); RESULT = stmt; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_stmt",3, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // cics_statement ::= cics_stmt cics_options 
            {
              StmtCICS RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		StmtCICS s = (StmtCICS)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int oright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		Option o = (Option)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = s; 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_statement",2, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // cics_stmts ::= cics_statement 
            {
              StmtCICS RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		StmtCICS s = (StmtCICS)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = code.processCICSStatement(s); 
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics_stmts",1, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // cics ::= 
            {
              Symbol RESULT =null;

              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= cics EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).right;
		Symbol start_val = (Symbol)((java_cup.runtime.Symbol) CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)).value;
		RESULT = start_val;
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.elementAt(CUP$CICSParser$top-1)), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$CICSParser$parser.done_parsing();
          return CUP$CICSParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // cics ::= cics_stmts 
            {
              Symbol RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()).right;
		StmtCICS s = (StmtCICS)((java_cup.runtime.Symbol) CUP$CICSParser$stack.peek()).value;
		 RESULT = symbolFactory.newSymbol(stmt.getVerbName(), stmt.getVerbId(), stmt);  
              CUP$CICSParser$result = parser.getSymbolFactory().newSymbol("cics",0, ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$CICSParser$stack.peek()), RESULT);
            }
          return CUP$CICSParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

