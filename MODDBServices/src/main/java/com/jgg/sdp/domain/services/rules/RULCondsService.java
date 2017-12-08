package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULCondsService extends AbstractService<RULCond> {

	public List<RULCond> getConditions(Long id) {
		return listQuery(RULCond.findById, id);
	}

	public RULCond getCondition(Long id, int seq) {
		return findQuery(RULCond.findCond, id, seq);
	}
	
	public void deleteConditions(Long key) {
		Long min = key * 10;
		Long max = (key * 10) + 9;
		deleteQuery(RULCond.delConditions, min, max);
	}
	
} 
