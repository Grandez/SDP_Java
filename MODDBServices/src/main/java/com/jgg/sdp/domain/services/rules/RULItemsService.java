package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULItemsService extends AbstractService<RULItem> {
	
	public List<RULItem> listAll() {
		return listQuery(RULItem.listAll);
	}

	public List<RULItem> listByGroup(Integer group) {
		return listQuery(RULItem.listByGroup, group);
	}
	
	public List<RULItem> listActiveItemsByGroup(Integer group) {
		return listQuery(RULItem.listActiveByGroup, group);
	}
}
