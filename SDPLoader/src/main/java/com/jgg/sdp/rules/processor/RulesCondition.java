package com.jgg.sdp.rules.processor;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

import java.sql.Timestamp;
import java.util.List;

import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.domain.services.rules.RULCondsService;
import com.jgg.sdp.loader.jaxb.rules.*;

public class RulesCondition {

	private RULCondsService condService = new RULCondsService();
	
	private ADTBag<RULCond> conds = new ADTBag<RULCond>();
	
	private RulesScript  scripts = RulesScript.getInstance();
	private RulesMessage msgs    = RulesMessage.getInstance();
	
    private static RulesCondition cond = null;
    
    private RulesCondition() {
    	
    }
    
    public static RulesCondition getInstance() {
    	if (cond == null) cond = new RulesCondition();
    	return cond;
    }

    public Long processConditions(Long key, List<ConditionType> list, long active) {
    	return processConditions(key, list, active, 0L);
    }
    
    public Long processConditions(Long key, List<ConditionType> list, long active, long idMsg) {
    	int seq = 1;
    	for (ConditionType c : list) {
    		createCondition(key, c, seq++, idMsg);
    	}
    	return key * active;
    }

    public Long processCondition(Long key, ConditionType cond, long active) {
    	return processCondition(key, cond, active, 0L);
    }
    
    public Long processCondition(Long key, ConditionType cond, long active, long idMsg) {
    	createCondition(key, cond, 0, idMsg);
		return key * active;
    }
    
    public ADTBag<RULCond> getConditions() {
    	return conds;
    }
    
    public Long createCondition(long key, ConditionType c, int seq, long idMsg) {
    	RULCond cond = condService.getCondition(key,  seq);
    	if (cond == null) {
    		cond = new RULCond();
    		cond.setIdCond(key);
    		cond.setIdSeq(seq);
    	}
    	
    	createLValue(key, cond, c);
    	createRValue(key, cond, c);
    	createOperator(cond, c.getOperator(), c.isNegated());
    	
    	cond.setIdMsg(msgs.createMessage(key, c.getMessage(), idMsg));
		cond.setUid(System.getProperty("user.name"));
		cond.setTms(new Timestamp(System.currentTimeMillis()));

		conds.add(cond);
		
        return key;	
    }

    private void createLValue(Long key, RULCond cond, ConditionType c) {
    	OperandType o = c.getLvalue();
    	if (o != null) {
    		cond.setLvalueType(getLValueType(o.getType()));
    		cond.setLvalue(o.getValue());
    		return;
    	}
    	
    	ScriptType s = c.getScript();
    	if (s != null) {
    	    cond.setLvalueType(TYPE_SCRIPT);
            Long id = scripts.createScript((key * 10), c.getScript());      
    	    cond.setLvalue(id.toString());
    	    return;
    	}
    	
    	LvalueType t = c.getType();
    	if (t != null) {
    	   cond.setLvalueType(getLValueType(t.value()));
    	   return;
    	}
    	
    	// No hay nodo
    	cond.setLvalueType(TYPE_VALUE);
    }

    private void createOperator(RULCond cond, String o, boolean negated) {
    	int val = (cond.getOperator() == null) ? OP_EQ : cond.getOperator(); 
    	if (o != null) {
    		String O = o.toUpperCase();
    		if (O.compareTo("MANDATORY") == 0) {
    			val = OP_EXIST;
    		} else if (O.compareTo("EQUAL") == 0) {
    			val = OP_EQ;
    		} else if (O.compareTo("EQ") == 0) {
    			val = OP_EQ;
    		} else if (O.compareTo("GT") == 0) {
    			val = OP_GT;
    		} else if (O.compareTo("LT") == 0) {
    			val = OP_LT;
    		} else if (O.compareTo("GE") == 0) {
    			val = OP_GT + MASK_EQ;
    		} else if (O.compareTo("LE") == 0) {
    			val = OP_LT + MASK_EQ;
    		} else if (O.compareTo("START") == 0) {
    			val = OP_START;
    		} else if (O.compareTo("CONTAINS") == 0) {
    			val = OP_CONTAINS;
    		} else if (O.compareTo("MATCH") == 0) {
    			val = OP_MATCH;
    		} else if (O.compareTo("END") == 0) {
    			val = OP_END;
    		} else {
    			val = OP_EQ;
    		} 
       }		
       cond.setOperator((negated) ? val * OP_NEGATED : val);
    }

    private void createRValue(Long key, RULCond cond, ConditionType c) {
        int    type  = getRValueType(c);

        cond.setRvalueType(type);
    	
    	switch (type) {
            case TYPE_RSCRIPT:     
 		         Long id = scripts.createScript((key * 10) + 1, c.getScript());      
 		         cond.setRvalue(id.toString()); 
 	             break;
            case TYPE_PROPERTY:
            	cond.setOperator(OP_BOOLEAN);
 	       default: 
 		        cond.setRvalue(getValue(c, type)); 
 	    }
    }
    
    public void clear() {
    	conds = new ADTBag<RULCond>();
    }

	public static int getLValueType(String type) {
        
        if (type.compareToIgnoreCase("attribute")     == 0) return TYPE_ATTRIBUTE;
		if (type.compareToIgnoreCase("configuration") == 0) return TYPE_CONFIG;
		if (type.compareToIgnoreCase("expression")    == 0) return TYPE_EXPRESSION;
		if (type.compareToIgnoreCase("function")      == 0) return TYPE_FUNCTION;
		if (type.compareToIgnoreCase("property")      == 0) return TYPE_PROPERTY;
		if (type.compareToIgnoreCase("value")         == 0) return TYPE_VALUE;
		if (type.compareToIgnoreCase("method")        == 0) return TYPE_METHOD;
		if (type.compareToIgnoreCase("variable")      == 0) return TYPE_VARIABLE;
		if (type.compareToIgnoreCase("option")        == 0) return TYPE_OPTION;
		
		if (type.compareToIgnoreCase("string")     == 0) return TYPE_STRING;
		if (type.compareToIgnoreCase("integer")    == 0) return TYPE_LONG;
		if (type.compareToIgnoreCase("decimal")    == 0) return TYPE_DECIMAL;
		if (type.compareToIgnoreCase("boolean")    == 0) return TYPE_BOOLEAN;
		if (type.compareToIgnoreCase("date")       == 0) return TYPE_DATE;
		if (type.compareToIgnoreCase("time")       == 0) return TYPE_TIME;
		if (type.compareToIgnoreCase("timestmap")  == 0) return TYPE_TIMESTAMP;
		
		
		if (type.compareToIgnoreCase("verb")       == 0) return TYPE_VERB;
		
		if (type.compareToIgnoreCase("lvalue")     == 0) return TYPE_LVALUE;
		if (type.compareToIgnoreCase("rvalue")     == 0) return TYPE_RVALUE;
		
//		if (type.compareToIgnoreCase("property")   == 0) return TYPE_PROPERTY;		
//		if (type.compareToIgnoreCase("expression") == 0) return TYPE_EXPRESSION;
//		if (type.compareToIgnoreCase("string")     == 0) return TYPE_STRING;
			
		return TYPE_VALUE;
	}
    
	private int getRValueType(ConditionType cond) {
		
		if (cond.getAttribute()  != null) return TYPE_ATTRIBUTE;
		if (cond.getProperty()   != null) return TYPE_PROPERTY;
		if (cond.getMethod()     != null) return TYPE_METHOD;
		if (cond.getExpression() != null) return TYPE_EXPRESSION;
		if (cond.getValue()      != null) return TYPE_VALUE;
		if (cond.getRvalue()     != null) return TYPE_RVALUE;
		if (cond.getRscript()    != null) return TYPE_RSCRIPT;
		
		return TYPE_NONE;
	}
	
	private String getValue(ConditionType cond, int type) {
		
		switch (type) {
		    case TYPE_PROPERTY:   return cond.getProperty();
		    case TYPE_ATTRIBUTE:  return cond.getAttribute();
		    case TYPE_METHOD:     return cond.getMethod();
		    case TYPE_EXPRESSION: return cond.getExpression();
		    case TYPE_SCRIPT:     return cond.getScript().getName();
		    case TYPE_RSCRIPT:    return cond.getRscript().getName();
		    case TYPE_VALUE:      return cond.getValue();
		    case TYPE_RVALUE:     return cond.getRvalue();
		}
		return null;
	}
	
}
