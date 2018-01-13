package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.rules.RULRulesService;
import com.jgg.sdp.loader.jaxb.rules.*;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

public class RulesRule {

	private RULRulesService  rulesService = new RULRulesService();	
	private ADTBag<RULRule>  rules        = new ADTBag<RULRule>();

	private RulesDescription descs = RulesDescription.getInstance();
	private RulesMessage     msgs  = RulesMessage.getInstance();	
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

	public RULRule createRule(RULItem item, Rule xmlRule) {
		long key = 0L;		
		RULRule rule =  null;
		Long idRule = xmlRule.getIdRule();
		if (idRule == null || idRule == 0) {
			idRule = rulesService.getNextId(item.getIdGroup(), item.getIdItem());
		}
		else {
			rule = rulesService.getById(item.getIdGroup(), item.getIdItem(), idRule);
		}
		if (rule == null) rule = new RULRule();
		
		rule.setIdGroup(item.getIdGroup());
		rule.setIdItem(item.getIdItem());
		rule.setIdRule(idRule);
		rule.setPriority(xmlRule.getPriority() == null ? 0 : xmlRule.getPriority());
		rule.setSeverity(xmlRule.getSeverity());
        rule.setName(xmlRule.getName());
        rule.setPrefix(item.getPrefix());
		key = Long.parseLong(String.format("%03d%03d%03d", rule.getIdGroup(), 
				                                           rule.getIdItem(), 
				                                           rule.getIdRule()));
		
	    rule.setIdTitle(0L);
		rule.setIdDesc(descs.createDescription(key, xmlRule.getDescription()));
		rule.setIdMsg(msgs.createMessage(key, xmlRule.getMessage(), item.getIdMsg()));
		rule.setIdSample(samps.createSample(key, xmlRule.getSample()));
		rule.setUid(System.getProperty("user.name"));
		rule.setTms(new Timestamp(System.currentTimeMillis()));

		rule.setActive(processActivateConditions(key * 10, xmlRule, rule.getIdMsg()));		
		rule.setIdCond(conds.createCondition((key * 10) + 1, xmlRule.getCondition(), 0, rule.getIdMsg()));
		rules.add(rule);
		return rule;
	}

	private Long processActivateConditions(Long key, Rule xmlRule, long idMsg) {
		Long active = processActive(xmlRule.isActive());
		if (xmlRule.getOnConditions() != null) {
			return conds.processConditions(key, xmlRule.getOnConditions().getCondition(), active, idMsg);
		}
		if (xmlRule.getOnCondition() != null) {
			return conds.processCondition(key, xmlRule.getOnCondition(), active, idMsg);
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
