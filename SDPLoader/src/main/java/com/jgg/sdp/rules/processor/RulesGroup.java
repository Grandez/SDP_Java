package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.loader.jaxb.rules.Group;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

public class RulesGroup {

	private RULGroupsService groupService = new RULGroupsService();
	
	private ADTBag<RULGroup> groups = new ADTBag<RULGroup>();

	private RulesDescription descs = RulesDescription.getInstance();
	private RulesCondition   conds = RulesCondition.getInstance();

	private static RulesGroup group = null;
	
	public RulesGroup() {
		RULGroupsService.setLastId(0L);
	}
	
	public static RulesGroup getInstance() {
		if (group == null) group = new RulesGroup();
		RULGroupsService.setLastId(0L);
		return group;
	}
	
	public RULGroup createGroup(Group xmlGroup) {
		long key = 0L;
		RULGroup group =  null;
		Long id = xmlGroup.getIdGroup();
		if (id == null || id == 0) {
			group = groupService.getByTextKey(xmlGroup.getName());
		}
		else {
			group = groupService.getById(id);
		}
		
		if (group == null) {
			if (id == null) id = groupService.getNextId();
			group = new RULGroup();
			group.setIdGroup(id);
			group.setName(xmlGroup.getName());
		}
		
		key = group.getIdGroup();

		group.setIdParent(xmlGroup.getIdParent() == null ? 0L : xmlGroup.getIdParent());
		group.setPrefix(xmlGroup.getPrefix());
		group.setActive(processActivateConditions(key * 10, xmlGroup));
		group.setIdDesc(descs.createDescription(id, xmlGroup.getDescription()));
		group.setUid(System.getProperty("user.name"));
		group.setTms(new Timestamp(System.currentTimeMillis()));
		groups.add(group);
		return group;
	}
	
	private Long processActivateConditions(Long key, Group group) {
		Long active = processActive(group.isActive());
		if (group.getOnConditions() != null) {
			return conds.processConditions(key, group.getOnConditions().getCondition(), active);
		}
		if (group.getOnCondition() != null) {
			return conds.processCondition(key, group.getOnCondition(), active);
		}
		return active;
	}

	private Long processActive(Boolean bActive) {
		if (bActive == null) return ACTIVE;
		return (bActive) ? ACTIVE : INACTIVE;
	}
	
	public ADTBag<RULGroup> getGroups() {
		if (group == null) return new ADTBag<RULGroup>();
		return groups;
	}
	
    public static void clear() {
    	group = null;
    }
	
}
