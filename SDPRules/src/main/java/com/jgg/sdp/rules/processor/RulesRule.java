package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.*;

import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.rules.RULRulesService;
import com.jgg.sdp.rules.xml.jaxb.*;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesRule {

	private RULRulesService    rulesService = new RULRulesService();	
	private ArrayList<RULRule> rules        = new ArrayList<RULRule>();
	private RulesDescription   ruleDesc     = RulesDescription.getInstance();


	public RULRule createRule(Long idGroup, Long idItem, Rule xmlRule) {
		boolean negated = false;
		
		RULRule rule =  null;
		Long idRule = xmlRule.getIdRule();
		if (idRule == null || idRule == 0) {
			idRule = rulesService.getNextId(idGroup, idItem);
		}
		else {
			rule = rulesService.getById(idGroup, idItem, idRule);
		}
		if (rule == null) rule = new RULRule();
		
		rule.setIdGroup(idGroup);
		rule.setIdItem(idItem);
		rule.setIdRule(idRule);
		rule.setPriority(xmlRule.getPriority());
		rule.setSeverity(xmlRule.getSeverity());

		ConditionType cond = xmlRule.getActivateOnCondition();
		if (cond != null) {
			rule.setActive(processActivateOnCondition(cond));
		}
		else {
			rule.setActive(xmlRule.isActive() ? ACTIVE : INACTIVE);
		}

		processCondition(rule, xmlRule.getCondition());
		
		
		rule.setIdDesc(ruleDesc.createDescription(mountKey(rule), xmlRule.getDescription()));
		rule.setUid(System.getProperty("user.name"));
		rule.setTms(new Timestamp(System.currentTimeMillis()));
		rules.add(rule);
		return rule;
	}
	
	public List<RULRule> getRules() {
		return rules;
	}

	private void processCondition(RULRule rule, ConditionType condition) {
		boolean negated = condition.isNegated();
		int comparator = RulesTypes.parseComparator(condition.getComparator(), negated);
		rule.setComparator(comparator);
        rule.setValor(condition.getValue());
        
        int type = RulesTypes.parseType(condition.getObjectType());
        if (type == TYPE_NONE) {
        	String method = condition.getMethod();
        	if (method != null) {
        		rule.setTipo(TYPE_METHOD);
        		rule.setProperty(method);
        	}
        	else {
        		Long idFormula = processExpression(rule, condition.getExpression());
        		rule.setTipo(TYPE_FORMULA);
        		rule.setProperty(idFormula.toString());
        	}
        }
  
	}
	
	private Long processActivateOnCondition(ConditionType cond) {
		RulesCondition c = RulesCondition.getInstance();
		return c.createCondition(cond);
	}
	
	private Long processExpression(RULRule rule, ExpressionType expression ) {
		RulesExpression e = RulesExpression.getInstance();
		return e.processExpression(mountKey(rule), expression);
	}
	
	private Long mountKey(RULRule rule) {
		return Long.parseLong(String.format("%02d%02d%02d", rule.getIdGroup(), rule.getIdItem(), rule.getIdRule()));
	}
}
