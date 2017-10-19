package com.jgg.sdp.rules;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.jgg.sdp.calc.Calculator;
import com.jgg.sdp.domain.services.rules.RULFormulaService;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.rules.objects.*;
import com.jgg.sdp.tools.*;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesProcessor {

	private final int MASK_EQ = 1;
	private final int MASK_LT = 2;
	private final int MASK_GT = 4;
	
	private RulesTree tree = RulesTree.getInstance();

	// Al modulo se pasan al final
	// Los issues del lexer pueden que no sepan el modulo
	private ArrayList<Issue> issues = new ArrayList<Issue>();
	
	/***************************************************************/
	/* Manejadores de reglas                                       */
	/***************************************************************/
	
	public RulesProcessor() {
		
	}

	ArrayList<Issue> getIssues() { return issues; }
	
	RuleItem getRuleItemByName(int group, String name) {
        RuleGroup grp = tree.getGroupById(group);
        if (grp == null) return null;
        
        RuleItem item = grp.getItemByName(name);
        return item;
	}
	
    void processRuleByName(int group, String name, RuleObject obj) {
    	RuleItem item = getRuleItemByName(group, name);
    	if (item != null) processItem(item, obj);
    }
    
	private void processItem(RuleItem item, RuleObject obj) {
		for (RuleRule rule : item.getRules()) {
			if (processRule(rule, obj)) {
				applyIssue(rule, obj);
				if (rule.getPriority() > 0) return;
			}
		}		
	}
	
	boolean processRule(RuleRule rule, RuleObject obj) {
		switch (rule.getType()) {
		    case TYPE_VERB:    return processRuleVerb(rule, obj);
		    case TYPE_METHOD:  return processRuleMethod(rule, obj);
		    case TYPE_VALUE:   return processRuleValue(rule, obj);		    
		    case TYPE_FORMULA: return processRuleFormula(rule, obj);
		}
		return true;
	}
	
	private boolean processRuleVerb(RuleRule rule, RuleObject obj) {
		boolean res = false;
		
		switch (rule.getComparator() % 100) {
		   case OP_EXIST: return checkExist(rule, obj);
		   case OP_START: return checkTypeStart(rule, obj);           
		}
		return res;
	}
	
	private boolean processRuleMethod(RuleRule rule, RuleObject obj) {
		System.out.println("JGG processRuleMethod");
/*		
		String method = rule.getProperty();
		method = "get" + method.substring(0,1).toUpperCase() + method.substring(1);
		Object result = JGGJava.executeMethod(obj, method);
		if (result instanceof Integer) return  matchInteger((Integer) result, rule);
*/		
		return false;
	}

	private boolean processRuleValue(RuleRule rule, RuleObject obj) {
        Object o = obj.getValue();		
		if (o instanceof Integer)    return  matchInteger((Integer) o, rule);
		if (o instanceof BigDecimal) return  matchBigDecimal((BigDecimal) o, rule);
		return false;
	}

	private boolean processRuleFormula(RuleRule rule, RuleObject obj) {
		System.out.println("JGG processRuleFormula");
/*		
		RULFormulaService service = new RULFormulaService();
		String formula = service.getFormula(Long.parseLong(rule.getProperty()));
        Calculator c = new Calculator(formula);
		c.setObjectBase(obj.getComponent());
		c.setObjectRoot(obj.getRoot());
		
		try {
			obj.setValue(c.evaluateExpression());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processRuleValue(rule, obj);
		*/
		return false;
	}
	
	/**
	 * Realmente existe, miramos si esta prohibido
	 * @param rule
	 * @param obj
	 * @return
	 */
	private boolean checkExist(RuleRule rule, RuleObject obj) {
		return (rule.getComparator() > 100);
	}
	
	/**
	 * Chequea si el objeto como cadena empieza con una subcadena
	 * @param rule La regla
	 * @param obj  El objeto asociado
	 * @return false si cierto
	 *         true  error
	 */
	private boolean checkTypeStart(RuleRule rule, RuleObject obj) {
		String value = obj.getObjectAsString();
		String target = rule.getValor();
		String src = Cadena.left(value, target.length());
		if (src == null) return true;
		
		//  Tabla de verdad
		//  Condicion Negada Issue
		//     F        F     T
		//     F        T     F
		//     T        F     F
		//     T        T     T
        //     XOR
		
		return (src.compareToIgnoreCase(target) == 0 ^ rule.getComparator() > 100);
			   
	}

	private boolean matchInteger (Integer source, RuleRule rule) {
		int aux = source - Integer.parseInt(rule.getValor());
		return matchMask(aux, rule.getComparator());
		
	}

	private boolean matchBigDecimal (BigDecimal source, RuleRule rule) {
		int res = source.compareTo(new BigDecimal(rule.getValor()));
		return matchMask(res, rule.getComparator());
	}
	
	private boolean matchMask(int value, int comp) {
		int res = (value == 0) ? 0 : value / Math.abs(value);
		int mask = 0;

		switch (res) {
		   case  0: mask = MASK_EQ; break;
		   case  1: mask = MASK_LT; break;
		   default: mask = MASK_GT;
		}
		
		// Cuidado con el XOR
		return !(((mask & (comp % 100)) > 0) ^ comp > 100);
	}

	private void applyIssue(RuleRule rule, RuleObject obj) {
		Issue issue = new Issue(rule.getIdGroup(), rule.getIdItem(), rule.getIdRule());
		issue.setBegLine(obj.getBegLine());
		issue.setBegColumn(obj.getBegColumn());
		issue.setEndLine(obj.getEndLine());
		issue.setEndColumn(obj.getEndColumn());
		issue.setSeverity(rule.getSeverity());
		issue.setBloque(obj.getBloque());
		issue.setFirma(Firma.createDefault());
		issues.add(issue);
	}
	
}
