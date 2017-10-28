package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.rules.xml.jaxb.ConditionType;
import com.jgg.sdp.rules.xml.jaxb.Group;

public class RulesGroup {

	private RULGroupsService groupService = new RULGroupsService();
	
	private ArrayList<RULGroup> groups = new ArrayList<RULGroup>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	private RulesCondition   ruleCond = RulesCondition.getInstance();
	
	public RulesGroup() {
		RULGroupsService.setLastId(0L);
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
		
		ConditionType cond = xmlGroup.getActivateOnCondition();
		
		group.setActive(ruleCond.createCondition( key * 10,      cond, xmlGroup.isActive()));
		group.setIdDesc(ruleDesc.createDescription(id, xmlGroup.getDescription()));
		group.setUid(System.getProperty("user.name"));
		group.setTms(new Timestamp(System.currentTimeMillis()));
		groups.add(group);
		return group;
	}
	
	public List<RULGroup> getGroups() {
		return groups;
	}
	
}
