package com.jgg.sdp.rules.objects;

import java.util.*;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;

public class RulesTree {

	private HashMap<Long,   RuleGroup> groupsKeyMap   = new HashMap<Long,   RuleGroup>();
	private HashMap<String, Long>      groupsNameMap  = new HashMap<String, Long>();
	private HashMap<String, HashMap<String, RuleItem>>  itemsMap  = new HashMap<String, HashMap<String, RuleItem>>();
	
	private RULGroupsService groupsService = new RULGroupsService();
	private RULItemsService  itemsService  = new RULItemsService();
	private RULRulesService  rulesService  = new RULRulesService();	
	private RULCondsService  condsService  = new RULCondsService();
	
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

	public RuleGroup getGroupById(long id) {
		return groupsKeyMap.get(id);
	}

	public RuleGroup getGroupByName(String name) {
		Long id = groupsNameMap.get(name);
		if (id == null) return null;
		
//		if (id == null) {
//			throw new SystemException(MSG.EXCEPTION_RULE_GROUP, name);
//		}
		return getGroupById(id);
	}
	
	public RuleItem getItemByName(String name) {
		HashMap<String, RuleItem> itemMap = itemsMap.get(name);
		if (itemMap == null) return null;
		Object[] items = itemMap.values().toArray();
		if (items.length == 0) return null;
		return (RuleItem) items[0];
	}

	
	public void reload() {
		groupsKeyMap   = new HashMap<Long, RuleGroup>();
		groupsNameMap  = new HashMap<String, Long>();
		
		for (RULGroup grp : groupsService.listActiveGroups()) {
			RuleGroup group = new RuleGroup();
			group.setId(grp.getIdGroup());
			group.setIdParent(grp.getIdParent());
			group.setActivo(grp.getActive());
			group.setName(grp.getIdName());
			group.setPrefix(grp.getPrefix());
			groupsKeyMap.put(group.getId(), group);
			groupsNameMap.put(group.getName(), group.getId());

			loadItemsByGroup(group);
		}
	}

	
	private void loadItemsByGroup(RuleGroup group) {
		for (RULItem itm : itemsService.listActiveItemsByGroup(group.getId())) {
			RuleItem item = mountItem(itm);
			item.setPrefix(group.getPrefix());
			group.addItem(item);
			HashMap<String, RuleItem> map = itemsMap.get(item.getObject());
			if (map == null) {
				map = new HashMap<String, RuleItem>();
				map.put(group.getName(), item);
				itemsMap.put(item.getObject(), map);
			}
			else {
				map.put(group.getName(), item);
			}
			loadRulesByItem(item);
		}
	}
	
	private void loadRulesByItem(RuleItem item) {
		for (RULRule rul : rulesService.listActiveRulesByItem(item.getIdGroup(), item.getIdItem())) {
			RuleRule rule = mountRule(item, rul);
			item.addRule(rule);
		}
		
	}
	
	private RuleItem mountItem(RULItem itm) {
		RuleItem item = new RuleItem();
		item.setIdGroup(itm.getIdGroup());
		item.setIdItem(itm.getIdItem());
		item.setObject(itm.getObject());
		item.setActivations(getConditions(itm.getActive()));
		return item;
	}

	private RuleRule mountRule(RuleItem item, RULRule rul) {
		RuleRule rule = new RuleRule(item);
		rule.setIdGroup(rul.getIdGroup());
		rule.setIdItem(rul.getIdItem());
		rule.setIdRule(rul.getIdRule());
		rule.setSeverity(rul.getSeverity());
		rule.setPriority(rul.getPriority());
		rule.setPrefix(item.getPrefix());
		
		if (rul.getActive() > 1 || rul.getActive() < -1) {
			rule.setActivations(getConditions(rul.getActive()));
		}
		
		rule.setCondition(getCondition(rul.getIdCond()));
		return rule;
	}
	
	private RuleCond getCondition(Long key) {
		ArrayList<RuleCond> c = getConditions(key);
		return c.get(0);
	}
	
	private ArrayList<RuleCond> getConditions(Long key) {
		Long id = key;
		ArrayList<RuleCond> conds = new ArrayList<RuleCond>();
		if (id < 0) id *= -1;
		for (RULCond cond : condsService.getById(key)) {
			RuleCond c = new RuleCond();
			c.setIdCond(cond.getIdCond());
			c.setLvalue(cond.getLvalue());
			c.setLvalueType(cond.getLvalueType());
			c.setOperator(cond.getOperator());
			c.setRvalue(cond.getRvalue());
			c.setRvalueType(cond.getRvalueType());
			conds.add(c);			
		}
		return conds;
	}
	
/*	
	private void printTree() {
		for (Long key : groupsKeysTree.keySet())   {
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
*/	
}
