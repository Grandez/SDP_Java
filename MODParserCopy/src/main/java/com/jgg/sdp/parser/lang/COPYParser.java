
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Jan 15 11:31:11 CET 2018
//----------------------------------------------------

package com.jgg.sdp.parser.lang;

import java_cup.runtime.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.blocks.symbols.*;
import com.jgg.sdp.blocks.stmt.*;
import com.jgg.sdp.parser.stmt.*;
import com.jgg.sdp.common.ctes.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Jan 15 11:31:11 CET 2018
  */
public class COPYParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public COPYParser() {super();}

  /** Constructor which sets the default scanner. */
  public COPYParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public COPYParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\021\000\002\002\003\000\002\002\004\000\002\003" +
    "\004\000\002\004\004\000\002\012\004\000\002\012\004" +
    "\000\002\012\002\000\002\005\004\000\002\011\003\000" +
    "\002\006\003\000\002\006\002\000\002\007\003\000\002" +
    "\007\002\000\002\010\004\000\002\013\003\000\002\013" +
    "\004\000\002\014\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\026\000\004\006\004\001\002\000\002\001\ufff9\000" +
    "\002\001\001\000\006\007\024\010\026\001\ufffb\000\004" +
    "\004\012\001\ufff7\000\004\002\011\001\002\000\002\001" +
    "\000\000\002\001\ufff8\000\002\001\uffff\000\004\005\017" +
    "\001\ufff5\000\002\001\ufff6\000\002\001\ufffa\000\004\006" +
    "\021\001\002\000\002\001\ufff3\000\002\001\ufff1\000\004" +
    "\006\021\001\ufff4\000\002\001\ufff2\000\004\006\004\001" +
    "\002\000\002\001\ufffe\000\004\006\004\001\002\000\002" +
    "\001\ufffc\000\002\001\ufffd" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\026\000\012\002\007\003\004\004\006\011\005\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\012\024" +
    "\001\001\000\006\005\012\006\013\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\006\007\015\010\014\001\001\000\002\001\001\000" +
    "\002\001\001\000\006\013\021\014\017\001\001\000\002" +
    "\001\001\000\002\001\001\000\004\014\022\001\001\000" +
    "\002\001\001\000\004\011\027\001\001\000\002\001\001" +
    "\000\004\011\026\001\001\000\002\001\001\000\002\001" +
    "\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$COPYParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$COPYParser$actions(this);
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
    return action_obj.CUP$COPYParser$do_action(act_num, parser, stack, top);
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
class CUP$COPYParser$actions {


   ParserInfo    info    = ParserInfo.getInstance();      
   
   ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
         
   StmtCopy stmt = null;  // Instruccion en proceso
   
   public void initActionClass() {
      
   }

   public void debug(String txt) { System.err.println(txt); }
   public void print(String txt) { 
      System.out.println(txt); 
   }


  private final COPYParser parser;

  /** Constructor */
  CUP$COPYParser$actions(COPYParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$COPYParser$do_action(
    int                        CUP$COPYParser$act_num,
    java_cup.runtime.lr_parser CUP$COPYParser$parser,
    java.util.Stack            CUP$COPYParser$stack,
    int                        CUP$COPYParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$COPYParser$result;

      /* select the action based on the action number */
      switch (CUP$COPYParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // cpy_replacing_token ::= ID 
            {
              Symbol RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = s; 
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing_token",10, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // cpy_replacing_lst ::= cpy_replacing_lst cpy_replacing_token 
            {
              SymbolList RESULT =null;
		int lleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int lright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		SymbolList l = (SymbolList)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = l.add(c);          
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing_lst",9, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // cpy_replacing_lst ::= cpy_replacing_token 
            {
              SymbolList RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = new SymbolList(c); 
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing_lst",9, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // cpy_replacing ::= REPLACING cpy_replacing_lst 
            {
              Object RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		int lleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int lright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		SymbolList l = (SymbolList)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 Option opt = new Option(s);
                                                      opt.add(l);
                                                      stmt.addOption(opt); 
                                                    
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing",6, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // cpy_replacing_opt ::= 
            {
              Object RESULT =null;

              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing_opt",5, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // cpy_replacing_opt ::= cpy_replacing 
            {
              Object RESULT =null;

              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_replacing_opt",5, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // cpy_supress_opt ::= 
            {
              Object RESULT =null;

              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_supress_opt",4, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // cpy_supress_opt ::= SUPPRESS 
            {
              Object RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 stmt.addOption(new Option(s)); 
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_supress_opt",4, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // cpy_member_id ::= ID 
            {
              Symbol RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol s = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = s; 
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_member_id",7, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // cpy_options ::= cpy_supress_opt cpy_replacing_opt 
            {
              Object RESULT =null;

              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_options",3, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // cpy_lib_opt ::= 
            {
              Option RESULT =null;
		 RESULT = null; 
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_lib_opt",8, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // cpy_lib_opt ::= OF cpy_member_id 
            {
              Option RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol p = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = new Option(c, p);  
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_lib_opt",8, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // cpy_lib_opt ::= IN cpy_member_id 
            {
              Option RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Symbol p = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 RESULT = new Option(c, p);  
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_lib_opt",8, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // cpy_member ::= cpy_member_id cpy_lib_opt 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol c = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		Option p = (Option)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 stmt = new StmtCopy(c); 
                                                  stmt.addOption(p); 
                                                
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_member",2, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // cpy_stmt ::= cpy_member cpy_options 
            {
              StmtCopy RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol m = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;

              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("cpy_stmt",1, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= copy EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).right;
		Symbol start_val = (Symbol)((java_cup.runtime.Symbol) CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)).value;
		RESULT = start_val;
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.elementAt(CUP$COPYParser$top-1)), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$COPYParser$parser.done_parsing();
          return CUP$COPYParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // copy ::= cpy_stmt 
            {
              Symbol RESULT =null;
		int cleft = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()).right;
		StmtCopy c = (StmtCopy)((java_cup.runtime.Symbol) CUP$COPYParser$stack.peek()).value;
		 stmt.setGroup(CDG.STMT_COPY);
                         RESULT = symbolFactory.newSymbol(stmt.getVerbName(), stmt.getVerbId(), stmt);  
                      
              CUP$COPYParser$result = parser.getSymbolFactory().newSymbol("copy",0, ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$COPYParser$stack.peek()), RESULT);
            }
          return CUP$COPYParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

