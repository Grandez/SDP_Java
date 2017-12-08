package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.rules.RULRulesService;
import com.jgg.sdp.loader.jaxb.rules.*;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

public class RulesRule {

	private RULRulesService  rulesService = new RULRulesService();	
	private ADTBag<RULRule>  rules        = new ADTBag<RULRule>();

	private RulesDescription descs = RulesDescription.getInstance();
	private RulesCondition   conds = RulesCondition.getInstance();
	private RulesSample      samps = RulesSample.getInstance();
	
	private static RulesRule rule = null;
	
	private RulesRule() {
		RULRulesService.setLastId(0L);
	}
	
	public static RulesRule getInstance() {
		if (rule == null) rule = new RulesRule();
		RULRulesService.setLastId(0L);
		return rule;
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
        rule.setName(xmlRule.getName());
		key = Long.parseLong(String.format("%02d%02d%02d", rule.getIdGroup(), 
				                                           rule.getIdItem(), 
				                                           rule.getIdRule()));
		
		rule.setActive(processActivateConditions(key * 10, xmlRule));
		rule.setIdCond(conds.createCondition((key * 10) + 1, xmlRule.getCondition(), 0));	
		rule.setIdTitle(descs.createTitle(key, xmlRule.getTitle()));
		rule.setIdDesc(descs.createDescription(key, xmlRule.getDescription()));
		rule.setIdSample(samps.createSample(key, xmlRule.getSample()));
		rule.setUid(System.getProperty("user.name"));
		rule.setTms(new Timestamp(System.currentTimeMillis()));
		rules.add(rule);
		return rule;
	}

	private Long processActivateConditions(Long key, Rule xmlRule) {
		Long active = processActive(xmlRule.isActive());
		if (xmlRule.getOnConditions() != null) {
			return conds.processConditions(key, xmlRule.getOnConditions().getCondition(), active);
		}
		if (xmlRule.getOnCondition() != null) {
			return conds.processCondition(key, xmlRule.getOnCondition(), active);
		}
		return active;
	}
	
	private Long processActive(Boolean bActive) {
		if (bActive == null) return ACTIVE;
		return (bActive) ? ACTIVE : INACTIVE;
	}
	
	public ADTBag<RULRule> getRules() {
		if (rule == null) return new ADTBag<RULRule>(); 
		return rules;
	}

	public static void clear() {
		rule = null;
	}
}
