package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;

import com.jgg.sdp.adt.SDPBag;
import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.loader.jaxb.rules.ConditionType;
import com.jgg.sdp.loader.jaxb.rules.Group;

public class RulesGroup {

	private RULGroupsService groupService = new RULGroupsService();
	
	private SDPBag<RULGroup> groups = new SDPBag<RULGroup>();

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
			group.setIdName(xmlGroup.getName());
		}
		
		key = group.getIdGroup();
		
		group.setIdParent(xmlGroup.getIdParent() == null ? 0L : xmlGroup.getIdParent());
		group.setPrefix(xmlGroup.getPrefix());
		
		ConditionType cond = xmlGroup.getOnCondition();
		
		group.setActive(conds.createCondition( key * 10,      cond, xmlGroup.isActive()));
		group.setIdDesc(descs.createDescription(id, xmlGroup.getDescription()));
		group.setUid(System.getProperty("user.name"));
		group.setTms(new Timestamp(System.currentTimeMillis()));
		groups.add(group);
		return group;
	}
	
	public SDPBag<RULGroup> getGroups() {
		if (group == null) return new SDPBag<RULGroup>();
		return groups;
	}
	
    public static void clear() {
    	group = null;
    }
	
}
