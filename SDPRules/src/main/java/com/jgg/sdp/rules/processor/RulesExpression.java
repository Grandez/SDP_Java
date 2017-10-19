package com.jgg.sdp.rules.processor;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.jgg.sdp.domain.rules.RULFormula;
import com.jgg.sdp.domain.services.rules.RULFormulaService;
import com.jgg.sdp.rules.xml.jaxb.ExpressionType;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesExpression {

	private RULFormulaService formulaService = new RULFormulaService();
	
	private ArrayList<RULFormula> exprs = new ArrayList<RULFormula>();
	
    private static RulesExpression expr = null;
    
    private RulesExpression() {
    	
    }
    
    public static RulesExpression getInstance() {
    	if (expr == null) expr = new RulesExpression();
    	return expr;
    }

    public Long processExpression(Long key, ExpressionType ex) {
    	RULFormula formula = null;
    	int order = 0;
        
    	formulaService.deleteFormula(key);
    	
    	// Primer elemento es el name
   		formula = new RULFormula();
   		formula.setIdFormula(key);
   		formula.setIdSeq(order++);
   		formula.setIdType(TYPE_FORMULA);
   		formula.setFormula(ex.getName());
   		exprs.add(formula);

   		for (String code : ex.getCode()) {
   	   		formula = new RULFormula();
   	   		formula.setIdFormula(key);
   	   		formula.setIdSeq(order++);
   	   		formula.setIdType(TYPE_FORMULA);
   	   		formula.setFormula(code);
   	   		exprs.add(formula);   			
   		}
   		
    	return key;
    }

    public List<RULFormula> getFormulas() {
    	return exprs;
    }
    public void clear() {
    	
    }
}
