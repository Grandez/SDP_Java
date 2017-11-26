package com.jgg.sdp.domain.services.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULGroupsService extends AbstractService<RULGroup> {
	
	private static Long lastId = 0L;
	
	public static void setLastId(Long last) {
		lastId = last;
	}

	public RULGroup getById(Long id) {
		return findQuery(RULGroup.findById, id);
	}

	public RULGroup getByName(String name) {
		return findQuery(RULGroup.findByName, name);
	}
	
	public RULGroup getByTextKey(String key) {
		return findQuery(RULGroup.findByTextKey, key);
	}
	
	public List<RULGroup> listAll() {
		return listQuery(RULGroup.listAll);
	}
	public List<RULGroup> listActiveGroups() {
		RULGroup root = findQuery(RULGroup.findActiveById, 0L);
		// Todo anulado
		if (root == null) return new ArrayList<RULGroup>();
		
		HashMap<String,RULGroup> groups = new HashMap<String,RULGroup>();
		groups.put(root.getIdName(), root);
		
		ArrayList<RULGroup> sig = new ArrayList<RULGroup>();
		sig.addAll(listQuery(RULGroup.listChildrenActive, root.getIdGroup()));
		
		while (!sig.isEmpty()) {
			RULGroup g = sig.remove(0);
			if (groups.get(g.getIdName()) == null) {
				groups.put(g.getIdName(), g);
				sig.addAll(listQuery(RULGroup.listChildrenActive, g.getIdGroup()));
			}
		}
		return new ArrayList<RULGroup>(groups.values());
	}

	public List<RULGroup> listChildren(Long idParent) {
		return listQuery(RULGroup.listChildren, idParent);
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
