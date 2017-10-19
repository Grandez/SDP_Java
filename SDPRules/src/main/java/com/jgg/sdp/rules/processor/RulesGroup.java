package com.jgg.sdp.rules.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.rules.xml.jaxb.Group;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesGroup {

	private RULGroupsService groupService = new RULGroupsService();
	
	private ArrayList<RULGroup> groups = new ArrayList<RULGroup>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	
	public RULGroup createGroup(Group xmlGroup) {
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
		
		group.setIdParent(xmlGroup.getIdParent() == null ? 0L : xmlGroup.getIdParent());
		group.setPrefix(xmlGroup.getPrefix());
		group.setActive(xmlGroup.isActive() ? ACTIVE : INACTIVE);
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
