package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;


public class RulesCleaner {

	private RULGroupsService groupService = new RULGroupsService();
	private RULItemsService  itemService  = new RULItemsService();
	private RULRulesService  ruleService  = new RULRulesService();
	private RULDescService   descService  = new RULDescService();
	
	public void deleteGroup(Long idGroup) {
		for (RULItem r : itemService.listByGroup(idGroup)) {
			deleteDescription(r.getIdDesc());
			deleteItem(idGroup, r.getIdItem());
		}
		itemService.deleteItemsOfRule(idGroup);
		deleteDescription(idGroup);
		groupService.deleteGroup(idGroup);
	}
	
	public void deleteItem(Long idGroup, Long idItem) {
		for (RULRule r : ruleService.listByItem(idGroup, idItem)) {
			deleteDescription(r.getIdDesc());
		}
		ruleService.deleteRulesOfItem(idGroup, idItem);
	}
	
	private void deleteDescription(Long idDesc) {
		if (idDesc == 0) return;
		descService.deleteDescription(idDesc);
	}
}
