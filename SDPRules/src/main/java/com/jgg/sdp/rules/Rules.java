package com.jgg.sdp.rules;

import java.util.*;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;

public class Rules {
	
	
	private RULGroupsService  groupsService = new RULGroupsService();
	private RULItemsService   itemsService  = new RULItemsService();
	private RULIssuesService  issuesService = new RULIssuesService();
	
	private ArrayList<RuleGroup> groups = new ArrayList<RuleGroup>();
	
	private HashMap<Integer, Integer> mapKey  = new HashMap<Integer,Integer>();
	private HashMap<String,  Integer> mapName = new HashMap<String, Integer>();
	
	private static Rules rules = null;
	
	private RuleGroup currGroup;
	
	private Rules() {
//		loadGroups();
	}

	public static Rules getInstance() {
		if (rules == null) rules = new Rules();
		return rules;
	}

	public RuleGroup getGroupByName(String name) {
		Integer pos = mapName.get(name);
		return (pos == null) ? null : groups.get(pos);
	}

	public boolean setGroup(String name) {
	    Integer pos = mapName.get(name);
	    if (pos != null) {
	    	currGroup = groups.get(pos);
	    	return true;
	    }
	    return false;
	}
	
	private void loadGroups() {
        int pos = 0;
        RuleGroup g;
        
    	// Asigna los padres
    	for (RULGroup r : groupsService.listActives()) {
    		g = createRuleGroup(r);
    	    groups.add(g);	
    	    mapKey.put (g.getId(),   pos);
    	    mapName.put(g.getName(), pos);
    	    loadItems(g);
    	    pos++;
    	}	
    	
    	// Asignar padres
    	for (RuleGroup group : groups) {
    		pos = mapKey.get(group.getIdParent());
    		group.setIdParent(pos);
    	}
    	
    }

    private RuleGroup createRuleGroup(RULGroup g) {
        RuleGroup group = new RuleGroup();
        group.setId(g.getIdGroup());
//        group.setName(g.getIdName());
        group.setIdParent(g.getIdParent());
        return group;
    }
    	
    private void loadItems(RuleGroup group) {
      	for (RULItem item : itemsService.listActiveByGroup(group.getId())) {
   			group.addItem(createRuleItem(item, group.getId()));
      	}
    }
	
    private RuleItem createRuleItem(RULItem item, Integer idGroup) {
    	RuleItem r = new RuleItem();
    	r.setIdGroup(item.getIdGroup());
    	r.setIdItem(item.getIdItem());
    	r.setKeyNum(item.getKeyNum());
    	r.setKeyTxt(item.getKeyTxt());
    	r.setUid(item.getUid());
    	r.setTms(item.getTms());
    	loadIssues(r);
    	return r;
    }
    
    private void loadIssues(RuleItem item) {
    	for (RULIssue issue : issuesService.listActiveByItem(item.getIdItem())) {
    		item.addIssue(createIssue(issue,item));
    	}
    	
    }
    
    private RuleIssue createIssue(RULIssue issue, RuleItem item) {
    	RuleIssue i = new RuleIssue();
    	i.setItemName(item.getKeyTxt());
    	i.setIdGroup(item.getIdGroup());
    	i.setIdItem(issue.getIdItem());
    	i.setIdIssue(issue.getIdIssue());
    	i.setType(issue.getTipo());
    	i.setSeverity(issue.getSeverity());
    	i.setComparator(issue.getComparador());
    	i.setValor(issue.getValor());
    	i.setUid(issue.getUid());
    	i.setTms(issue.getTms());
    	
    	return i;
    }
	
/*	
	public ArrayList<Integer> check(Integer id, String value) {
		ArrayList<Integer> issues = new ArrayList<Integer>(); 
		processByKey(id, issues);
		processByValue(value, issues);
		return issues;
	}
	
	private void processByKey(Integer id, ArrayList<Integer> issues) {
		if (id == null) return;
		if (id == 0)    return;
		processIssues(keyNum.get(id), issues);
	}
	
	private void processByValue(String value, ArrayList<Integer> issues) {
		if (value == null) return;
		if (value.trim().length() == 0)    return;
		processIssues(keyTxt.get(value), issues);
	}

	private void processIssues(RuleItem rule, ArrayList<Integer> issues) {
		if (rule == null) return;
		for (RuleIssue issue : rule.getRuleIssues()) {
		    if (issue.getComparator() == OP_EXIST) issues.add(issue.getIdIssue());	
		}	
	}
	
	private void mapItems() {
		Integer kNum;
		String  kTxt;
		
		for (RuleGroup group : groups) {
			for (RuleItem item : group.getItems()) {
				kNum = item.getKeyNum();
				if (kNum != null && kNum != 0) keyNum.put(kNum,  item);
				kTxt = item.getKeyTxt();
                if (kTxt != null && kTxt.trim().length() > 0) keyTxt.put(kTxt, item);				
			}
		}
	}
*/
}
