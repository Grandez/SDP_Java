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
		return tree;		
	}

	public RuleGroup getGroupById(long id) {
		return groupsKeyMap.get(id);
	}

	public RuleGroup getGroupByName(String name) {
		Long id = groupsNameMap.get(name);
        return (id == null) ? null : getGroupById(id); 
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
			setRuleBase(group, grp);
			group.setId(grp.getIdGroup());
			group.setIdParent(grp.getIdParent());
 
			groupsKeyMap.put(group.getId(), group);
			groupsNameMap.put(group.getName(), group.getId());
			loadItemsByGroup(group);
			for (RULGroup child : groupsService.listActiveChildren(grp.getIdGroup())) {
				group.addChildren(child.getIdGroup());
			}
		}
	}

	
	private void loadItemsByGroup(RuleGroup group) {
		for (RULItem itm : itemsService.listActiveItemsByGroup(group.getId())) {
			RuleItem item = mountItem(group, itm);

			group.addItem(item);
			HashMap<String, RuleItem> map = itemsMap.get(item.getName());
			if (map == null) {
				map = new HashMap<String, RuleItem>();
				map.put(group.getName(), item);
				itemsMap.put(item.getName(), map);
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

	private void setRuleBase(RuleBase base, IRule reg) {
        base.setIdDesc(reg.getIdDesc());
        base.setIdTitle(reg.getIdTitle());
        base.setIdMsg(reg.getIdMsg());
        base.setName(reg.getName());
        base.setActive(reg.getActive());
        base.setPrefix(reg.getPrefix());
        base.setUid(reg.getUid());
        base.setTms(reg.getTms());
	}
	
	private RuleItem mountItem(RuleGroup group, RULItem itm) {
		RuleItem item = new RuleItem(group);
		setRuleBase(item, itm);
		item.setIdGroup(itm.getIdGroup());
		item.setIdItem(itm.getIdItem());
		item.setObject(itm.getObject());
		item.setActivations(getConditions(itm.getActive()));
		item.setType(itm.getType());
		return item;
	}

	private RuleRule mountRule(RuleItem item, RULRule rul) {
		RuleRule rule = new RuleRule(item);
		setRuleBase(item, rul);
		rule.setIdRule(rul.getIdRule());
		rule.setSeverity(rul.getSeverity());
		rule.setPriority(rul.getPriority());
		rule.setActivations(getConditions(rul.getActive()));
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
		for (RULCond cond : condsService.getConditions(key)) {
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
		printGroups();
		printItems();
	}
	
	private void printGroups() {
		Iterator<String> it = groupsNameMap.keySet().iterator();
		System.out.println("GRUPOS");
		while (it.hasNext()) {
			System.out.println("\t" + it.next());
		}
	}
	private void printItems() {
		Iterator<String> it = itemsMap.keySet().iterator();
		System.out.println("ITEMS");
		while (it.hasNext()) {
			System.out.println("\t" + it.next());
		}
	}
*/	
}
