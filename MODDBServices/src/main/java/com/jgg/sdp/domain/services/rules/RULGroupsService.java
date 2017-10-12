package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULGroupsService extends AbstractService<RULGroup> {
	
	private static Long lastId = 0L;
	
	public RULGroup getByTextKey(String key) {
		return findQuery(RULGroup.findByTextKey, key);
	}
	
	public RULGroup getById(Long id) {
		return findQuery(RULGroup.findById, id);
	}

	public List<RULGroup> listAll() {
		return listQuery(RULGroup.listAll);
	}
	public List<RULGroup> listActiveGroups() {
		return listQuery(RULGroup.listActive);
	}
	
	public void clean() {
	    sqlExecute("DELETE FROM RUL_GROUPS");
	}
 
	public void deleteGroup(Long idGroup) {
		deleteQuery(RULGroup.delGroup, idGroup);
	}
	
	public Long getNextId() {
		if (lastId == 0L) lastId = getLastId();
		return ++lastId;
	}
	
	private Long getLastId() {
		Object o = getItemAbstract(RULGroup.findMaxId);
		if (o == null) return 0L;
		return (Long) o;
	}
	
}
