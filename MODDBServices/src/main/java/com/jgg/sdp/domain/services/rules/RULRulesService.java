package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULRulesService extends AbstractService<RULRule> {

	public RULRule getIssue(Integer idIssue) {
		return find ("find", idIssue);
	}
	public List<RULRule> listByItem(Integer idGroup, Integer idItem) {
		return listQuery(RULRule.listByItem, idGroup, idItem);
	}
	public List<RULRule> listActiveRulesByItem(Integer idGroup, Integer idItem) {
		return listQuery(RULRule.listActiveByItem, idGroup, idItem);
	}
	public List<RULRule> listAll() {
		return listQuery(RULRule.listAll);
	}

} 
