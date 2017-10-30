package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.*;

import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.rules.RULRulesService;
import com.jgg.sdp.rules.xml.jaxb.*;

public class RulesRule {

	private RULRulesService    rulesService = new RULRulesService();	
	private ArrayList<RULRule> rules        = new ArrayList<RULRule>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	private RulesCondition   ruleCond = RulesCondition.getInstance();
	private RulesSample      ruleSamp = RulesSample.getInstance();
	
	public RulesRule() {
		RULRulesService.setLastId(0L);
	}

	public RULRule createRule(Long idGroup, Long idItem, Rule xmlRule) {
		long key = 0L;		
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
		rule.setPriority(xmlRule.getPriority() == null ? 0 : xmlRule.getPriority());
		rule.setSeverity(xmlRule.getSeverity());

		key = Long.parseLong(String.format("%02d%02d%02d", rule.getIdGroup(), 
				                                           rule.getIdItem(), 
				                                           rule.getIdRule()));
		
		ConditionType cond = xmlRule.getActivateOnCondition();

		rule.setActive(ruleCond.createCondition( key * 10,      cond, xmlRule.isActive()));
		rule.setIdCond(ruleCond.createCondition((key * 10) + 1, xmlRule.getCondition()));		
		rule.setIdDesc(ruleDesc.createDescription(key, xmlRule.getDescription()));
		rule.setIdSample(ruleSamp.createSample(key, xmlRule.getSample()));
		rule.setUid(System.getProperty("user.name"));
		rule.setTms(new Timestamp(System.currentTimeMillis()));
		rules.add(rule);
		return rule;
	}
	
	public List<RULRule> getRules() {
		return rules;
	}
	
}
