package com.jgg.sdp.web.adm.rest;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.web.adm.code.RulesText;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.LANG;
import com.jgg.sdp.web.core.json.JSonTree;

import java.util.*;

@RestController
public class RulesController {

    @Autowired
    private RULGroupsService  grpService;    
    @Autowired
    private RULItemsService   itemService;    
    @Autowired
    private RULRulesService   ruleService;    
    @Autowired
    private RULDescService    descService;
    @Autowired
    private RULCondsService   condService;        
    @Autowired
    private RULSamplesService sampService;    
//

    @Autowired
    private RulesText base;  

    @RequestMapping("/rulesTree")
    public List<JSonTree<JSonItem>> getRules(@RequestHeader HttpHeaders headers) {
    	ArrayList<JSonTree<JSonItem>> tree = new ArrayList<JSonTree<JSonItem>>();
    	String[] lang = LANG.getLanguage(headers);
    	
    	for (JSonTree<JSonItem> group : loadGroups(lang)) {
    		tree.add(group);
    		loadItems(tree, group, lang);
    	}
        return tree;
    }

    @RequestMapping("/ruleDetail/{idGroup}")
    public JSonItem getRulesGroup(@PathVariable Long idGroup, @RequestHeader HttpHeaders headers) {
    	return createGroup(grpService.getById(idGroup), LANG.getLanguage(headers));
    }

    @RequestMapping("/ruleDetail/{idGroup}/{idItem}")
    public JSonItem getRulesItem(@PathVariable Long idGroup
    		                              ,@PathVariable Long idItem
    		                              ,@RequestHeader HttpHeaders headers) {
    	JSonItem group = createGroup(grpService.getById(idGroup), LANG.getLanguage(headers));
    	return createItem(group, itemService.getById(idGroup, idItem), LANG.getLanguage(headers));
    }
    
    private List<JSonTree<JSonItem>> loadGroups(String lang[]) {
    	ArrayList<JSonTree<JSonItem>> tree = new ArrayList<JSonTree<JSonItem>>();
    	for (RULGroup g : grpService.listAll()) {
    		String id = String.format("%03d",  g.getIdGroup());
    		JSonTree<JSonItem> group = new JSonTree<JSonItem>();
    		group.setId(id);
        	group.setText(getTitle(g, lang));
        	group.setParent(g.getIdGroup() == 0 ? "#" : String.format("%03d",  g.getIdParent()));
        	
        	JSonItem item = new JSonItem(g);
        	item.setNodeType(g.getIdGroup() == 0 ? 0 : 1);
        	item.setId(id);
        	item.setIdParent(g.getIdParent());
        	item.setTitle(getTitle(g, lang));
            item.setPrefix(g.getPrefix());
            
        	group.setData(item);
        	
    		tree.add(group);
    	}
    	return tree;
    }
    
    private void loadItems(ArrayList<JSonTree<JSonItem>> tree, JSonTree<JSonItem> parent, String lang[]) {
    	for (RULItem i : itemService.listByGroup(parent.getData().getIdGroup())) {
    		String id = String.format("%03d%03d",  i.getIdGroup(), i.getIdItem()); 
    		JSonTree<JSonItem> item = new JSonTree<JSonItem>();
    		item.setId(id);
    		item.setText(getTitle(i, lang));
    		item.setParent(String.format("%03d",  i.getIdGroup()));
    		
        	JSonItem data = new JSonItem(i);
        	data.setId(id);
        	data.setIdItem(i.getIdItem());
        	data.setNodeType(2);
        	data.setTitle(getTitle(i, lang));
        	
        	item.setData(data);
    		tree.add(item);
    	}
    }

    private JSonItem createGroup(RULGroup group, String lang[]) {
    	String id = String.format("%03d",  group.getIdGroup());
		JSonItem g = new JSonItem(group);
		g.setExpanded(true);        
		g.setId(id);
        g.setIdParent(group.getIdParent());
        g.setPrefix(group.getPrefix());
        g.setTitle(getTitle(group, lang));
        
		createDetail(g, group, lang);
        base.mountMessages(g, lang);		
		return g;
    }

    private JSonItem createItem(JSonItem group, RULItem item, String lang[]) {
    	String id = String.format("%03d%03d",  item.getIdGroup(), item.getIdItem());
		JSonItem i = new JSonItem(item);
   		i.setNodeType(2);
		i.setIdItem(item.getIdItem());
		i.setId(id);

		i.merge(group);
		createDetail(i, item, lang);
		createRules(i, lang);
		
		base.mountMessages(i, lang);
        return i;
    }

    private void createRules(JSonItem parent, String lang[]) {
    	
    	for (RULRule r : ruleService.listByItem(parent.getIdGroup(), parent.getIdItem())) {
    		JSonRule json = new JSonRule(r);
        	json.setLabel(String.format("%s%02d%02d%02d",  parent.getPrefix(), r.getIdGroup(), r.getIdItem(), r.getIdRule()));
        	json.setActivations(getActivations(r.getActive()));
        	json.setCondition(getCondition(r.getIdCond()));
    		parent.addRule(json);
    	}
    }
    
    private void createDetail(JSonItem item, IRule obj, String[] lang) {   
        item.setActivations(getActivations(obj.getActive()));
        item.setSample(getSample(item.getIdSample()));
    }
        
    private String getTitle(IRule rule, String lang[]) {
    	String txt = null;
    	if (rule.getIdTitle() != 0)  txt = descService.getTitle(rule.getIdTitle(), lang);
    	if (txt == null)             txt = rule.getName();
    	return txt;
    }
    
	public List<JSonRuleCond> getActivations(Long idCond) {
		List<JSonRuleCond> list = new ArrayList<JSonRuleCond>();
		
	    for (RULCond cond: condService.getConditions(idCond)) {
	    	list.add(new JSonRuleCond(cond));
	    }
	    
	    return list;
	}
	
	public JSonRuleCond getCondition(Long idCond) {
		List<JSonRuleCond> list = getActivations(idCond);
		return list.get(0);
	}
	
	public JSonRuleSample getSample(Long idSample) {
		if (idSample == 0) return null;
		JSonRuleSample sample = new JSonRuleSample();
		StringBuilder sb = new StringBuilder();
		for (RULSample s : sampService.getSampleKO(idSample)) {
			sb.append(s.getData());
			sb.append('\n');
		}
		sb.deleteCharAt(sb.length() - 1);
		sample.setKo(sb.toString());
		
		sb.setLength(0);
		for (RULSample s : sampService.getSampleOK(idSample)) {
			sb.append(s.getData());
			sb.append('\n');
		}
		sb.deleteCharAt(sb.length() - 1);
		sample.setOk(sb.toString());
		return sample;
	}
 	
}