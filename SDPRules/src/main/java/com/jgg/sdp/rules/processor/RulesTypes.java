package com.jgg.sdp.rules.processor;

import static com.jgg.sdp.rules.CDGRules.*;

import com.jgg.sdp.rules.xml.jaxb.ComparatorType;
import com.jgg.sdp.rules.xml.jaxb.ObjectType;

public class RulesTypes {

	public static int parseComparator(ComparatorType comp, boolean negated) {
		int neg = (negated) ? OP_NEGATED : 0;
		int val = 0;
		switch (comp) {
		   case MANDATORY: val = OP_EXIST;            break;
		   case EQUAL:     val = OP_EQ;               break;
		   case GT:        val = OP_GT;               break;
		   case LT:        val = OP_LT;               break;
		   case GE:        val = OP_EQ_TOO + OP_GT;	  break;	    
		   case LE:        val = OP_EQ_TOO + OP_LT;   break;
		   case START:     val = OP_START;            break;
		   case CONTAINS:  val = OP_CONTAINS;         break;
		   case MATCH:     val = OP_MATCH;            break;
		   case END:       val = OP_MATCH;            break;
           default:                                   break;
		}
		return neg + val;
	}

	public static int parseType(ObjectType type) {
		if (type == null) return TYPE_NONE;
		
		switch (type) {
		   case VERB:       return TYPE_VERB;
		   case WORD:       return TYPE_VERB;
		   case OPTION:     return TYPE_OPTION;
		   case LVALUE:     return TYPE_LVALUE;
		   case RVALUE:     return TYPE_RVALUE;
		   case VALUE:      return TYPE_VALUE;
		   case SPECIAL:    return TYPE_SPECIAL;
		   default:         return 0;
		}
	}
	
}
