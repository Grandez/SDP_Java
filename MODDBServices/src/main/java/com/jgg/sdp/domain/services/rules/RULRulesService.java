package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULRule;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULRulesService extends AbstractService<RULRule> {

	private static Long lastId = 0L;

	public RULRule getById(Long idGroup, Long idItem, Long idRule) {
		return findQuery (RULRule.findById, idGroup, idItem, idRule);
	}
	
	public List<RULRule> listByItem(Long idGroup, Long idItem) {
		return listQuery(RULRule.listByItem, idGroup, idItem);
	}
	public List<RULRule> listActiveRulesByItem(Long idGroup, Long idItem) {
		return listQuery(RULRule.listActiveByItem, idGroup, idItem);
	}
	public List<RULRule> listAll() {
		return listQuery(RULRule.listAll);
	}

	public Long getNextId(Long idGroup, Long idItem) {
		if (lastId == 0L) lastId = getLastId(idGroup, idItem);
		return ++lastId;
	}
	
	public void deleteRulesOfItem(Long idGroup, Long idItem) {
		deleteQuery(RULRule.delRulesOfItem, idGroup, idItem);
	}

	private Long getLastId(Long idGroup, Long idItem) {
		Object o = getItemAbstract(RULRule.findMaxId, idGroup, idItem);
		if (o == null) return 0L;
		return (Long) o;
	}
	
} 
