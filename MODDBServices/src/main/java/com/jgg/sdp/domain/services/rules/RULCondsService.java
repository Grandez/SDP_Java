package com.jgg.sdp.domain.services.rules;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULCond;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULCondsService extends AbstractService<RULCond> {

	public RULCond getById(Long id) {
		return findQuery(RULCond.findById, id);
	}
	
	public void deleteConditions(Long key) {
		Long min = key * 10;
		Long max = (key * 10) + 9;
		deleteQuery(RULCond.delConditions, min, max);
	}
	
} 
