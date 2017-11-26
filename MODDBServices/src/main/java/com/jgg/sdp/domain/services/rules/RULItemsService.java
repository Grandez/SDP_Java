package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULItem;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULItemsService extends AbstractService<RULItem> {

	private static Long lastId = 0L;
	
	public static void setLastId(Long last) {
		lastId = last;
	}
	
	public RULItem getByTextKey(String key) {
		return findQuery(RULItem.findByTextKey, key);
	}
	
	public RULItem getById(Long idParent, Long id) {
		return findQuery(RULItem.findById, idParent, id);
	}

	public RULItem getByObject(String name) {
		return findQuery(RULItem.findByObject, name);
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
	public List<RULItem> listActiveItemsByName(String name) {
		return listQuery(RULItem.listActiveByName, name);
	}
	
	public Long getNextId(Long idParent) {
		if (lastId == 0L) lastId = getLastId(idParent);
		return ++lastId;
	}

	public void deleteItemsOfGroup(Long idGroup) {
		deleteQuery(RULItem.delItemsOfGroup, idGroup);
	}

	public void deleteItem(Long idGroup, Long idItem) {
		deleteQuery(RULItem.delItem, idGroup, idItem);
	}

	private Long getLastId(Long idParent) {
		Object o = getItemAbstract(RULItem.findMaxId, idParent);
		if (o == null) return 0L;
		return (Long) o;
	}

}
