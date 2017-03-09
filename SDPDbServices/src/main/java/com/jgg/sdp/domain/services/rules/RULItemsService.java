package com.jgg.sdp.domain.services.rules;

import java.util.List;

import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.AbstractService;

public class RULItemsService extends AbstractService<RULItem> {
	
	public List<RULItem> listAll() {
		return lista(RULItem.listAll);
	}

	public List<RULItem> listByGroup(Integer group) {
		return lista(RULItem.listByGroup, group);
	}
	
	public List<RULItem> listActiveByGroup(Integer group) {
		return lista(RULItem.listActiveByGroup, group);
	}
}
