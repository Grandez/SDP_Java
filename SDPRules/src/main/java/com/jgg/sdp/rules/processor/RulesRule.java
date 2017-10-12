package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.*;

import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.rules.RULRulesService;
import com.jgg.sdp.rules.xml.*;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesRule {

	private RULRulesService    rulesService = new RULRulesService();	
	private ArrayList<RULRule> rules        = new ArrayList<RULRule>();
	private RulesDescription   ruleDesc     = RulesDescription.getInstance();


	public RULRule createRule(Long idGroup, Long idItem, Rule xmlRule) {
		ConditionType cond = null;
		boolean negated = false;
		
		RULRule rule =  null;
		Long idRule = xmlRule.getId();
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
		
		rule.setActivo(xmlRule.isActive() ? ACTIVE : INACTIVE);
		rule.setPriority(xmlRule.getPriority());
		rule.setSeverity(xmlRule.getSeverity());
		
		cond = xmlRule.getConditionNegative();
		
		if (cond != null) {
			negated = true;
		} else {
			cond = xmlRule.getConditionPositive();
			if (cond == null) cond = xmlRule.getCondition();
		}
		
		rule.setComparador(RulesTypes.parseComparator(cond.getComparator(), negated));
		rule.setTipo(RulesTypes.parseType(cond.getObjectType()));
		
		rule.setIdDesc(ruleDesc.createDescription(mountKey(rule), xmlRule.getDescription()));
		rule.setUid(System.getProperty("user.name"));
		rule.setTms(new Timestamp(System.currentTimeMillis()));
		rules.add(rule);
		return rule;
	}
	
	public List<RULRule> getRules() {
		return rules;
	}

	private Long mountKey(RULRule rule) {
		return Long.parseLong(String.format("%02d%02d%02d", rule.getIdGroup(), rule.getIdItem(), rule.getIdRule()));
	}
}
