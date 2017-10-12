package com.jgg.sdp.rules.processor;

import java.util.ArrayList;
import java.util.List;

import com.jgg.sdp.rules.xml.Group;
import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.rules.*;

import static com.jgg.sdp.rules.CDGRules.*;

public class RulesGroup {

	private RULGroupsService groupService = new RULGroupsService();
	
	private ArrayList<RULGroup> groups = new ArrayList<RULGroup>();

	private RulesDescription ruleDesc = RulesDescription.getInstance();
	
	public RULGroup createGroup(Group xmlGroup) {
		RULGroup group =  null;
		Long id = xmlGroup.getId();
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
		}
		group.setIdParent(xmlGroup.getParent() == null ? 0L : xmlGroup.getParent());
		group.setPrefix(xmlGroup.getPrefix());
		group.setActivo(xmlGroup.isActive() ? ACTIVE : INACTIVE);
		group.setIdDesc(ruleDesc.createDescription(id, xmlGroup.getDescription()));
		groups.add(group);
		return group;
	}
	
	public List<RULGroup> getGroups() {
		return groups;
	}
	
}
