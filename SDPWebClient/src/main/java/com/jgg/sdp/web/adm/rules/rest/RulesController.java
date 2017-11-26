package com.jgg.sdp.web.adm.rules.rest;

import java.util.*;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.tools.Reflect;
import com.jgg.sdp.web.adm.json.*;

import com.jgg.sdp.rules.*;
import com.jgg.sdp.rules.ctes.CDGRules;

@RestController
public class RulesController {

    private String lang    = SYS.DEF_LANG;
    private String dialect = SYS.DEF_DIALECT;
    
    @Autowired
    private RULGroupsService grpService;    
    @Autowired
    private RULItemsService itemService;    
    @Autowired
    private RULRulesService ruleService;    
    @Autowired
    private RULScriptsService formulaService;    

    @Autowired
    private RULDescService descService;

    @RequestMapping("admin/rules")
    //public List<RuleObject> getAllRules(@CookieValue("lang") String lang) {
    public List<RuleGroup> getAllRules() {
        setLanguage("XX");
        
    	List<RuleGroup> grupos = loadGroups(); 
        loadItems(grupos); 
        return grupos;
    }
    
    private List<RuleGroup> loadGroups() {
    	ArrayList<RuleGroup> lista = new ArrayList<RuleGroup>();
    	for (RULGroup g : grpService.listAll()) {
            RuleGroup o = new RuleGroup();
            o.setId(g.getIdGroup());
            o.setActive(g.getActive());
            o.setIdDesc(g.getIdDesc());
            o.setIdGroup(g.getIdGroup());
            o.setIdParent(g.getIdParent());
            o.setPrefix(g.getPrefix());
            o.setDesc(mountDescription(g.getIdDesc(), g));
    		lista.add(o);
    	}
        return lista;
    }

    private void loadItems(List<RuleGroup> grupos) {
    	for (RuleGroup grupo : grupos) {
        	for (RULItem item : itemService.listByGroup(grupo.getId())) {
        		loadRules(grupo, item);
        	}
    	}
    }

    private void loadRules(RuleGroup g, RULItem item) {
 
   	   	for (RULRule rule : ruleService.listByItem(item.getIdGroup(), item.getIdItem())) {
    		RuleItem obj = new RuleItem();
    		obj.setId(Long.parseLong(String.format("%d%03d",item.getIdGroup(), item.getIdItem())));
    		long idCode = item.getIdGroup();
    		idCode = (idCode * 100) + item.getIdItem();
    		idCode = (idCode * 100) + rule.getIdRule();
    		obj.setCode(String.format("%s%05d", g.getPrefix(), idCode % 100000));
    		obj.setIdGroup(item.getIdGroup());
    		obj.setIdItem(item.getIdItem());
            obj.setActiveGroup(g.getActive());
            obj.setActiveItem(item.getActive());
            obj.setActiveRule(rule.getActive());
            obj.setActive(rule.getActive());
    		if (g.getActive() != 0 || item.getActive() != 0) {
    			obj.setActive(CDGRules.INHERIT);
    		}
    		obj.setDesc(mountDescription(item.getIdDesc(), item));
    		makeRule(obj, rule);

    		if (obj.getPriority() == 0) {
   		        g.addItem0(obj);
    		} else {
    			g.addItem1(obj);
    		}
   	   	}    
    }
    
    private void makeRule(RuleItem item, RULRule r) {
        item.setIdRule(r.getIdRule());
        item.setActiveRule(r.getActive());
        item.setPriority(r.getPriority());
        item.setPropiedad(r.getProperty());
        item.setSeverity(r.getSeverity());
        item.setComparator(r.getComparator() % 100);
        item.setNegated(r.getComparator() > 100 ? true : false);
        item.setTipo(r.getTipo());
        item.setValor(r.getValor());
        item.setUid(r.getUid());
        item.setTms(r.getTms());
        item.setDesc(mountDescription(r.getIdDesc(), r));       
    }
    
    private String mountDescription(long id, Object o) {
    	String token = null;
    	String base = descService.getDescription(id, lang, dialect);
    	int start = base.indexOf('{');
    	int end   = 0;
    	while (start != -1) {
    		end = base.indexOf('}');
    		token = base.substring(start + 1, end);
    		String value = Reflect.executeMethod(o, "get" + token).toString();
    		value = checkReservedWords(value, token);
    		String from = base.substring(start, end + 1);
    		base = base.replaceAll(Pattern.quote(from),value);
    		start = base.indexOf('{');
    	}
    	return base;
    }
    
    private String checkReservedWords(String value, String token) {
		if (token.compareToIgnoreCase("comparador") == 0) return xlateComparator(value);
		if (token.compareToIgnoreCase("propiedad") == 0)  return getFormulaName(value);
		return value;
    }
    
    private String xlateComparator(String txt) {
    	StringBuilder cad = new StringBuilder();
    	long valor = Integer.parseInt(txt);
    	if (valor > 99) cad.append("NO ");
    	valor %= 100;
    	cad.append(descService.getDescription(valor, lang, dialect));
    	return cad.toString();
    }
    
    private String getFormulaName(String idFormula) {
    	return formulaService.getFormulaName(Long.parseLong(idFormula)); 
    }
    private void setLanguage(String lang) {
    	if (lang == null) return;
    	String[] l = lang.split("_");
    	this.lang = l[0];
    	if (l.length > 1) dialect = l[1];
    }
}