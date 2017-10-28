package com.jgg.sdp.rules.processor;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.rules.xml.jaxb.*;

public class RulesCondition {

	private ArrayList<RULCond> conds = new ArrayList<RULCond>();
	
    private static RulesCondition cond = null;
    
    private RulesCondition() {
    	
    }
    
    public static RulesCondition getInstance() {
    	if (cond == null) cond = new RulesCondition();
    	return cond;
    }

    public ArrayList<RULCond> getConditions() {
    	return conds;
    }
    
    public Long createCondition(long key, ConditionType c, Boolean active) {
    	Long idCond = ACTIVE;
    	if (active != null) idCond = (active) ? ACTIVE : INACTIVE;
    	if (c != null) return createCondition(key, c);
    	return idCond;
    }
    
    public Long createCondition(long key, ConditionType c) {
    	RULCond cond = new RULCond();
    	cond.setIdCond(key);
    	
    	if (createProperty(cond, c)) {
    		if (createType(cond, c)) {
    			if (createScript(cond, c, key)) {
    		        if (createAttribute(cond, c)) {
    			        createLValue(cond,c);
    		        }    
    		   }
    		}
    	}
    	
		cond.setUid(System.getProperty("user.name"));
		cond.setTms(new Timestamp(System.currentTimeMillis()));

		conds.add(cond);
		
        return key;	
    }
    
    private boolean createProperty(RULCond cond, ConditionType c) {
    	int neg = (c.isNegated()) ? OP_NEGATED : OP_POSITIVE;
    	String p = c.getProperty();
    	if (p == null) return true;
		cond.setLvalueType(TYPE_PROPERTY);
		cond.setLvalue(p);
		cond.setRvalueType(TYPE_BOOLEAN);
		cond.setRvalue("true");
		cond.setOperator(OP_EQ * neg);
		return false;
    }

    private boolean createType(RULCond cond, ConditionType c) {
    	ObjectType t = c.getType();
    	if (t == null) return true;

		cond.setLvalueType(RulesTypes.parseObjectType(t));
		cond.setLvalue(t.name());
		createOperator(cond, c);
		createRValue(cond, c);
		return false;
    }

    private boolean createScript(RULCond cond, ConditionType c, Long key) {
    	ScriptType script = c.getScript();
    	if (script == null) return true;

		cond.setLvalueType(TYPE_SCRIPT);
		cond.setLvalue(key.toString());
		createOperator(cond, c);
		createRValue(cond, c);
		
		RulesScript s = RulesScript.getInstance();
		s.createScript(key, script);
		return false;
    }
    
    private boolean createAttribute(RULCond cond, ConditionType c) {
        String a = c.getAttribute();
    	if (a == null) return true;
		cond.setLvalueType(TYPE_ATTRIBUTE);
		cond.setLvalue(a);

		createOperator(cond, c);
		createRValue(cond, c);
		return false;
    }

    private boolean createLValue(RULCond cond, ConditionType c) {
    	OperandType rval = c.getLvalue();
    	if (rval == null) return true;
		cond.setLvalueType(RulesTypes.parseOperandType(rval.getType()));
		cond.setLvalue(rval.getValue());

		createOperator(cond, c);
		createRValue(cond, c);
		return false;
    }
    
    private void createOperator(RULCond cond, ConditionType c) {
    	boolean negated = c.isNegated();
		int operator = RulesTypes.parseOperator(c.getOperator(), negated);		
		cond.setOperator(operator);
    }
    
    private void createRValue(RULCond cond, ConditionType c) {
		OperandType rval = c.getRvalue(); 
		cond.setRvalueType(RulesTypes.parseOperandType(rval.getType()));
		cond.setRvalue(rval.getValue());
    }
    
    public void clear() {
    	conds = new ArrayList<RULCond>();
    }

}
