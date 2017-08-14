package com.jgg.sdp.rules;

import java.util.HashMap;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;

public class RulesTree {

	private HashMap<String,  RuleGroup> namesTree = new HashMap<String, RuleGroup>();
	private HashMap<Integer, RuleGroup> keysTree  = new HashMap<Integer, RuleGroup>();
	
	
	private RULGroupsService groupsService = new RULGroupsService();
	private RULItemsService  itemsService  = new RULItemsService();
	private RULRulesService  rulesService  = new RULRulesService();	
	
	private static RulesTree        tree = null;
	
	private RulesTree() {
		reload();
	}
	
	public static RulesTree getInstance() {
		if (tree == null) tree = new RulesTree();
		return tree;
	}

	public RuleGroup getGroupById(int id) {
		return keysTree.get(id);
	}
	
	public void reload() {
		namesTree = new HashMap<String, RuleGroup>();
		keysTree  = new HashMap<Integer, RuleGroup>();
		
		for (RULGroup grp : groupsService.listActiveGroups()) {
			RuleGroup group = new RuleGroup();
			group.setId(grp.getIdGroup());
			group.setIdParent(grp.getIdParent());
			group.setActivo(grp.getActivo());
			keysTree.put(group.getId(), group);
			loadItems(group);
		}
	}

	private void loadItems(RuleGroup group) {
		for (RULItem itm : itemsService.listActiveItemsByGroup(group.getId())) {
			RuleItem item = mountItem(itm);
			group.addItem(item);
			loadRules(item);
		}
	}
	
	private void loadRules(RuleItem item) {
		for (RULRule rul : rulesService.listActiveRulesByItem(item.getIdGroup(), item.getIdItem())) {
			RuleRule rule = mountRule(rul);
			item.addRule(rule);
		}
		
	}
	
	private RuleItem mountItem(RULItem itm) {
		RuleItem item = new RuleItem();
		item.setIdGroup(itm.getIdGroup());
		item.setIdItem(itm.getIdItem());
		item.setClave(itm.getClave());
		return item;
	}

	private RuleRule mountRule(RULRule rul) {
		RuleRule rule = new RuleRule();
		rule.setComparator(rul.getComparador());
		rule.setIdRule(rul.getIdRule());
		
		return rule;
	}
}
