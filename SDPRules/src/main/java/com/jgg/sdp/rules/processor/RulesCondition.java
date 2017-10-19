package com.jgg.sdp.rules.processor;

import java.util.ArrayList;

import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.domain.services.rules.RULCondsService;
import com.jgg.sdp.rules.xml.jaxb.ConditionType;

public class RulesCondition {

	private RULCondsService condService = new RULCondsService();
	
	private ArrayList<RULCond> conds = new ArrayList<RULCond>();
	
    private static RulesCondition cond = null;
    
    private RulesCondition() {
    	
    }
    
    public static RulesCondition getInstance() {
    	if (cond == null) cond = new RulesCondition();
    	return cond;
    }

    public Long createCondition(ConditionType c) {
        return 0L;	
    }
    
    public void clear() {
    	conds = new ArrayList<RULCond>();
    }

}
