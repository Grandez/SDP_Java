package com.jgg.sdp.web.adm.rest;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.web.adm.code.RulesCodeBase;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.LANG;
import com.jgg.sdp.web.core.json.JSonTree;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RulesController {

    @Autowired
    private RULGroupsService   grpService;    
    @Autowired
    private RULItemsService    itemService;    
    @Autowired
    private RULRulesService    ruleService;    
    @Autowired
    private RULDescService     descService;    
    @Autowired
    private RulesCodeBase base;  

    @RequestMapping("/rulesTree")
    public List<JSonTree<JSonRule>> getRules(@RequestHeader HttpHeaders headers) {
    	String lang[] = LANG.getLanguage(headers);
        return loadData(lang);
    }

    @RequestMapping("/ruleDetail/{idGroup}")
    public JSonRule getRulesGroup(@PathVariable Long idGroup, @RequestHeader HttpHeaders headers) {
    	return createGroup(grpService.getById(idGroup), LANG.getLanguage(headers));
    }

    @RequestMapping("/ruleDetail/{idGroup}/{idItem}")
    public JSonRule getRulesItem(@PathVariable Long idGroup
    		                              ,@PathVariable Long idItem
    		                              ,@RequestHeader HttpHeaders headers) {
    	JSonRule group = createGroup(grpService.getById(idGroup), LANG.getLanguage(headers));
    	return createItem(group, itemService.getById(idGroup, idItem), LANG.getLanguage(headers));
    }
    
    @RequestMapping("/ruleDetail/{idGroup}/{idItem}/{idRule}")
    public JSonRule getRulesRule(@PathVariable Long idGroup
    		                              ,@PathVariable Long idItem
    		                              ,@PathVariable Long idRule
    		                              ,@RequestHeader HttpHeaders headers) {
    	JSonRule group = createGroup(grpService.getById(idGroup), LANG.getLanguage(headers));
    	JSonRule item  = createItem(group, itemService.getById(idGroup, idItem), LANG.getLanguage(headers));    	
    	return createRule(item, ruleService.getById(idGroup, idItem, idRule), LANG.getLanguage(headers));
    }
    
    private List<JSonTree<JSonRule>> loadData(String[] lang) {
    	ArrayList<JSonTree<JSonRule>> tree = new ArrayList<JSonTree<JSonRule>>();
    	
    	for (JSonTree<JSonRule> group : loadGroups(lang)) {
    		tree.add(group);
    		loadItems(tree, group, lang);
    	}
    	return tree;
    }
    
    private List<JSonTree<JSonRule>> loadGroups(String lang[]) {
    	ArrayList<JSonTree<JSonRule>> tree = new ArrayList<JSonTree<JSonRule>>(); 
    	for (RULGroup g : grpService.listAll()) {
    		JSonTree<JSonRule> group = new JSonTree<JSonRule>();
    		group.setId(String.format("%03d",  g.getIdGroup()));
        	group.setText(getTitle(lang, g));
        	if (g.getIdGroup() == 0) {
        		group.setParent("#");
        	}
        	else {
        	    group.setParent(String.format("%03d",  g.getIdParent()));
        	}
        	
        	JSonRule json = new JSonRule();
        	json.setActive(g.getActive() < 0L ? false : true);
        	json.setId(group.getId());
        	json.setIdGroup(g.getIdGroup());
        	json.setNodeType(group.getParent().charAt(0) == '#' ? 0 : 1);
        	group.setData(json);
    		tree.add(group);
    	}
    	return tree;
    }
    
    private void loadItems(ArrayList<JSonTree<JSonRule>> tree, JSonTree<JSonRule> parent, String lang[]) {
    	for (RULItem i : itemService.listByGroup(parent.getData().getIdGroup())) {
    		JSonTree<JSonRule> item = new JSonTree<JSonRule>();
    		item.setId(String.format("%03d%03d",  i.getIdGroup(), i.getIdItem()));
    		item.setText(getTitle(lang, i));
    		item.setParent(String.format("%03d",  i.getIdGroup()));
    		
        	JSonRule json = new JSonRule(parent.getData());
        	json.setActive(i.getActive() < 0L ? false : true);
        	json.setId(item.getId());
        	json.setIdGroup(i.getIdGroup());
        	json.setIdItem(i.getIdItem());
        	json.setNodeType(2);
        	item.setData(json);
        	
    		tree.add(item);
//    		loadRules(tree, item, lang);
    	}
    }

    private void loadRules(ArrayList<JSonTree<JSonRule>> tree, JSonTree<JSonRule> parent, String lang[]) {
    	JSonRule p = parent.getData();
    	for (RULRule r : ruleService.listByItem(p.getIdGroup(), p.getIdItem())) {
    		JSonTree<JSonRule> rule = new JSonTree<JSonRule>();
    		rule.setId(String.format("%03d%03d%03d",  r.getIdGroup(), r.getIdItem(), r.getIdRule()));
    		rule.setText(getTitle(lang, r));
    		rule.setParent(String.format("%03d%03d",  r.getIdGroup(), r.getIdItem()));

    		JSonRule json = new JSonRule(p);
        	json.setActive(r.getActive() < 0L ? false : true);
        	json.setId(rule.getId());
        	json.setIdGroup(r.getIdGroup());
        	json.setIdItem(r.getIdItem());
        	json.setIdRule(r.getIdRule());
        	json.setNodeType(3);
        	rule.setData(json);

    		tree.add(rule);
    	}
    }
        
    private JSonRule createGroup(RULGroup group, String lang[]) {
		JSonRule g = new JSonRule();
		g.setId(String.format("%03d",  group.getIdGroup()));
		g.setNodeType(group.getIdGroup() == 0 ? 0 : 1);
        g.setIdGroup(group.getIdGroup());
        g.setIdParent(group.getIdParent());
		createBase(g, group, lang);
		if (group.getIdGroup() == 0) { // Raiz
        	g.setIdDesc(group.getIdDesc());
        	g.setDescription(base.mountDescription(group.getIdDesc(), lang));
        	g.setIdMsg(group.getIdMsg());
        	g.setMessage(base.mountMessage(group, lang));
        	g.setIdTitle(group.getIdTitle());
        }

		return g;
    }

    private JSonRule createItem(JSonRule i, RULItem item, String lang[]) {
   		i.setNodeType(2);
		i.setIdGroup(item.getIdGroup());
		i.setIdItem(item.getIdItem());
		i.setId(String.format("%03d%03d",  item.getIdGroup(), item.getIdItem()));
		i.setSample(base.mountSample(item.getIdSample(), lang));
		createBase(i, item, lang);		
        return i;
    }

    private JSonRule createRule(JSonRule r, RULRule rule, String lang[]) {
		r.setNodeType(3);
		r.setIdGroup(rule.getIdGroup());
		r.setIdItem(rule.getIdItem());
		r.setIdRule(rule.getIdRule());
		r.setId(String.format("%03d%03d%03d",  rule.getIdGroup(), rule.getIdItem(), rule.getIdRule()));
		r.setPriority(rule.getPriority());
		r.setSeverity(rule.getSeverity());

		r.setSample(base.mountSample     (r.getIdSample(), lang));
		createBase(r, rule, lang);
		
		return r;
    }

    private void createBase(JSonRule r, IRule obj, String[] lang) {
        r.setActive(obj.getActive() <= OP_NEGATED ? false : true);
        if (obj.getIdDesc()  != null && obj.getIdDesc() != 0) {
        	r.setIdDesc(obj.getIdDesc());
        	r.setDescription(base.mountDescription(obj.getIdDesc(), lang));
        }
        if (obj.getIdMsg()   != null && obj.getIdMsg() != 0) {
        	r.setIdMsg(obj.getIdMsg());
        	r.setMessage(base.mountMessage(obj, lang));
        }
        if (obj.getIdTitle() != null && obj.getIdTitle() != 0) {
        	r.setIdTitle(obj.getIdTitle());
        }
        r.setName(obj.getName());
        r.setPrefix(obj.getPrefix());
        r.setTms(obj.getTms());
        r.setUid(obj.getUid());
        r.setStatus(obj.getActive());
        
//        r.setConds(base.getRawConditions(obj.getActive(), lang));
		

        
    }
    
    private String getTitle(String lang[], IRule rule) {
    	String txt = null;
    	System.out.println(rule.getName());
    	if (rule.getName() != null && rule.getName().compareTo("TAB") == 0) {
    		System.out.println(rule.getName());	
    	}
    	if (rule.getIdTitle() != null && rule.getIdTitle() != 0)  {
    		txt = descService.getTitle      (rule.getIdTitle(), lang);
    	}
    	if (txt == null)             txt = rule.getName();
    	return txt;
    }
    

    
    
}