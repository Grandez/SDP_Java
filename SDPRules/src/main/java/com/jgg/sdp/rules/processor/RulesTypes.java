package com.jgg.sdp.rules.processor;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

import com.jgg.sdp.rules.xml.jaxb.ObjectType;
import com.jgg.sdp.rules.xml.jaxb.OperatorType;

public class RulesTypes {

	public static int parseOperator(OperatorType oper, boolean negated) {
		int neg = (negated) ? OP_NEGATED : OP_POSITIVE;
		int val = 0;
		switch (oper) {
		   case MANDATORY: val = OP_EXIST;            break;
		   case EQUAL:     val = OP_EQ;               break;
		   case EQ:        val = OP_EQ;               break;		   
		   case GT:        val = OP_GT;               break;
		   case LT:        val = OP_LT;               break;
		   case GE:        val = OP_GT + MASK_EQ;	  break;	    
		   case LE:        val = OP_LT + MASK_EQ;     break;
		   case START:     val = OP_START;            break;
		   case CONTAINS:  val = OP_CONTAINS;         break;
		   case MATCH:     val = OP_MATCH;            break;
		   case END:       val = OP_MATCH;            break;
           default:                                   break;
		}
		return neg * val;
	}

	public static int parseObjectType(ObjectType type) {
		if (type == null) return TYPE_NONE;
		
		switch (type) {
		   case STRING:     return TYPE_STRING;
		   case OPTION:     return TYPE_OPTION;
		   case LVALUE:     return TYPE_LVALUE;
		   case RVALUE:     return TYPE_RVALUE;
		   case VALUE:      return TYPE_VALUE;
		   default:      
			   System.out.println("Para");
			   return 0;
		}
	}

	public static int parseOperandType(String type) {
		if (type.compareToIgnoreCase("string")    == 0) return TYPE_STRING;
		if (type.compareToIgnoreCase("value")     == 0) return TYPE_VALUE;
		if (type.compareToIgnoreCase("function")  == 0) return TYPE_FUNCTION;
		if (type.compareToIgnoreCase("attribute") == 0) return TYPE_ATTRIBUTE;
		if (type.compareToIgnoreCase("method")    == 0) return TYPE_METHOD;
		if (type.compareToIgnoreCase("variable")  == 0) return TYPE_VARIABLE;
		
		return TYPE_VALUE;
	}
	
}
