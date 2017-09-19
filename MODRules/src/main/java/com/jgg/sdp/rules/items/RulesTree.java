package com.jgg.sdp.rules.items;

import java.util.HashMap;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;

public class RulesTree {

	//private HashMap<String,  RuleGroup> namesTree = new HashMap<String, RuleGroup>();
	private HashMap<Integer, RuleGroup> keysTree  = new HashMap<Integer, RuleGroup>();
	
	
	private RULGroupsService groupsService = new RULGroupsService();
	private RULItemsService  itemsService  = new RULItemsService();
	private RULRulesService  rulesService  = new RULRulesService();	
	
	private static RulesTree        tree = null;
	
	private RulesTree() {		
		reload();
	}

	public static RulesTree getInstance() {
		return getInstance(false);
	}
	
	public static RulesTree getInstance(boolean reload) {
		if (reload) tree = null;
		if (tree == null) tree = new RulesTree();
//		tree.printTree();
		return tree;		
	}

	public RuleGroup getGroupById(int id) {
		return keysTree.get(id);
	}
	
	public void reload() {
//		namesTree = new HashMap<String, RuleGroup>();
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
		rule.setIdGroup(rul.getIdGroup());
		rule.setIdItem(rul.getIdItem());
		rule.setIdRule(rul.getIdRule());
		rule.setPriority(rul.getPriority());
		rule.setSeverity(rul.getSeverity());
		rule.setType(rul.getTipo());
		rule.setProperty(rul.getPropiedad());
		rule.setValor(rul.getValor());
		return rule;
	}
	
	private void printTree() {
		for (Integer key : keysTree.keySet())   {
			RuleGroup grp = keysTree.get(key);
			System.out.println("Grupo: " + grp.getId());
			for (RuleItem itm : grp.getItems()) {
				System.out.println("\tItem: " + itm.getIdItem());
				for (RuleRule rul : itm.getRules()) {
					System.out.println("\t\tRule: " + rul.getIdRule());
				}
			}
        }	
	}
}
