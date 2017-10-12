package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULItemsService extends AbstractService<RULItem> {

	private static Long lastId = 0L;
	
	public RULItem getByTextKey(String key) {
		return findQuery(RULItem.findByTextKey, key);
	}
	
	public RULItem getById(Long idParent, Long id) {
		return findQuery(RULItem.findById, idParent, id);
	}
	
	public List<RULItem> listAll() {
		return listQuery(RULItem.listAll);
	}

	public List<RULItem> listByGroup(Long group) {
		return listQuery(RULItem.listByGroup, group);
	}
	
	public List<RULItem> listActiveItemsByGroup(Long group) {
		return listQuery(RULItem.listActiveByGroup, group);
	}
	
	public Long getNextId(Long idParent) {
		if (lastId == 0L) lastId = getLastId(idParent);
		return ++lastId;
	}

	public void deleteItemsOfRule(Long idGroup) {
		deleteQuery(RULItem.delItemsOfGroup, idGroup);
	}
	private Long getLastId(Long idParent) {
		Object o = getItemAbstract(RULItem.findMaxId, idParent);
		if (o == null) return 0L;
		return (Long) o;
	}

}
