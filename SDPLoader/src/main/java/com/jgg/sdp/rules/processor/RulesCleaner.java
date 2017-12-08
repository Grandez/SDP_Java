package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;


public class RulesCleaner {

	private RULGroupsService   groupService   = new RULGroupsService();
	private RULItemsService    itemService    = new RULItemsService();
	private RULRulesService    ruleService    = new RULRulesService();
	private RULCondsService    condService    = new RULCondsService();
	private RULDescService     descService    = new RULDescService();
	private RULSamplesService  sampService    = new RULSamplesService();
	private RULScriptsService  scriptService  = new RULScriptsService();
	
	public RULGroup deleteGroup(Long idGroup) {
		for (RULItem i : itemService.listByGroup(idGroup)) {
			Long key = Long.parseLong(String.format("%02d%02d", i.getIdGroup(), i.getIdItem()));
			deleteItemDetail(i.getIdGroup(), i.getIdItem());
			deleteDetail(key);
		}
		itemService.deleteItemsOfGroup(idGroup);
		RULGroup g = groupService.getById(idGroup);
		groupService.deleteGroup(idGroup);
		return g;
	}

	public void deleteItem(Long idGroup, Long idItem) {
		deleteItemDetail(idGroup, idItem);
		Long key = Long.parseLong(String.format("%02d%02d", idGroup, idItem));
		deleteDetail(key);
		itemService.deleteItem(idGroup, idItem);
	}
	
	public void deleteItemDetail(Long idGroup, Long idItem) {
		for (RULRule r : ruleService.listByItem(idGroup, idItem)) {
			Long key = Long.parseLong(String.format("%02d%02d%02d", r.getIdGroup(), r.getIdItem(), r.getIdRule()));
			deleteDetail(key);
		}
		ruleService.deleteRulesOfItem(idGroup, idItem);
		
	}
	
	private void deleteDetail(Long key) {
		sampService.deleteSample(key);
		condService.deleteConditions(key);
		descService.deleteDescriptions(key);
		scriptService.deleteScripts(key);
	}
}
