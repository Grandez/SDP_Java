package com.jgg.sdp.web.adm.rest;

import java.util.*;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.tools.Reflect;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.LANG;

import static com.jgg.sdp.rules.ctes.CDGRules.*;

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
    private RULCondsService condService;    
    @Autowired
    private RULDescService  descService;    

    @Autowired
    private RULScriptsService scriptService;    


    @RequestMapping("/rulesTree")
    public List<JSonRules> getRules(@RequestHeader HttpHeaders headers) {
    	String lang[] = LANG.getLanguage(headers);
        return loadRules(lang);
    }
    
    private List<JSonRules> loadRules(String lang[]) {
    	ArrayList<JSonRules> lista = new ArrayList<JSonRules>();
    	RULGroup g = grpService.getById(0L);
		JSonRules o = new JSonRules();
		o.setNodeType(1);
        o.setIdGroup(g.getIdGroup());
        o.setIdParent(g.getIdParent());
        o.setName(g.getName());   
        o.setText(getTitle(lang, g));
        o.setPrefix(g.getPrefix());
        o.setActive(g.getActive() > 0 ? true : false);
        o.setIdDesc(g.getIdDesc());
        o.setUid(g.getUid());
        o.setTms(g.getTms());
//        o.setConditions(getConditions(g.getActive()));
//        o.setDesc(mountDescription(g.getIdDesc(), g));
        o.setParent("#");
		o.setId(String.format("%03d",  g.getIdGroup()));
		lista.add(o);
		loadGroups(lang, lista, o);
		return lista;
    }
    
    private void loadGroups(String lang[], ArrayList<JSonRules> lista, JSonRules parent) {
    	for (RULGroup g : grpService.listChildren(parent.getIdGroup())) {
    		JSonRules o = new JSonRules();
    		o.setNodeType(1);
            o.setIdGroup(g.getIdGroup());
            o.setIdParent(g.getIdParent());
            o.setName(g.getName());     
            o.setText(getTitle(lang, g));
            o.setPrefix(g.getPrefix());
            o.setActive(g.getActive() > 0 ? true : false);
            o.setIdDesc(g.getIdDesc());
            o.setUid(g.getUid());
            o.setTms(g.getTms());
//            o.setConditions(getConditions(g.getActive()));
//            o.setDesc(mountDescription(g.getIdDesc(), g));
//            o.setParent(g.getIdGroup() == 0 ? "#" : String.format("%03d", g.getIdParent()));
    		o.setId(String.format("%03d",  g.getIdGroup()));
    		o.setParent(parent.getId());
//    		parent.addChildren(o);
    		lista.add(o);
    		loadItems(lang, lista, o);
    	}
    }

    private void loadItems(String lang[], ArrayList<JSonRules> lista, JSonRules parent) {
       	for (RULItem item : itemService.listByGroup(parent.getIdGroup())) {
       		JSonRules o = new JSonRules();
       		o.setNodeType(2);
    		o.setIdGroup(item.getIdGroup());
    		o.setIdItem(item.getIdItem());
    		o.setName(item.getName());
            o.setText(getTitle(lang, item));
//    		o.setObject(item.getObject());
    		o.setNodeType(2);
    		o.setActive(item.getActive() <= OP_NEGATED ? false : true);
    		o.setId(String.format("%03d%03d",  item.getIdGroup(),item.getIdItem()));
    		o.setParent(parent.getId());
//    		parent.addChildren(o);
    		lista.add(o);
    		loadRules(lang, lista, o);
    	}
    }

    private void loadRules(String lang[], ArrayList<JSonRules> lista, JSonRules parent) {
    	for (RULRule r : ruleService.listByItem(parent.getIdGroup(), parent.getIdItem())) {
    		JSonRules o = new JSonRules();
    		o.setNodeType(3);
    		o.setIdGroup(r.getIdGroup());
    		o.setIdItem(r.getIdItem());
    		o.setIdRule(r.getIdRule());
    		o.setActive(r.getActive() <= OP_NEGATED ? false : true);
    		o.setIdCond(r.getIdCond());
    		o.setIdDesc(r.getIdDesc());
    		o.setIdSample(r.getIdSample());
    		o.setName(r.getName());
            o.setText(getTitle(lang, r));
    		o.setPriority(r.getPriority());
    		o.setSeverity(r.getSeverity());
    		o.setUid(r.getUid());
    		o.setTms(r.getTms());
    		
    		o.setId(String.format("%03d%03d%03d",  r.getIdGroup(),r.getIdItem(),r.getIdRule()));
    		o.setParent(parent.getId());
//    		parent.addChildren(o);
    		lista.add(o);
    	}   
    }
        
    private String getTitle(String lang[], IRules rule) {
    	String txt = null;
    	if (rule.getIdTitle() != 0)                txt = descService.getTitle      (rule.getIdTitle(), lang);
    	if (txt == null && rule.getIdDesc() != 0)  txt = descService.getDescription(rule.getIdDesc(),  lang);
    	if (txt == null)                           txt = rule.getName();
    	return txt;
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
//    		value = checkReservedWords(value, token);
    		String from = base.substring(start, end + 1);
    		base = base.replaceAll(Pattern.quote(from),value);
    		start = base.indexOf('{');
    	}
    	return base;
    }

/*    
    private String xlateComparator(String txt) {
    	StringBuilder cad = new StringBuilder();
    	long valor = Integer.parseInt(txt);
    	if (valor > 99) cad.append("NO ");
    	valor %= 100;
    	cad.append(descService.getDescription(valor, lang, dialect));
    	return cad.toString();
    }
    */
    private void setLanguage(String lang) {
    	if (lang == null) return;
    	String[] l = lang.split("_");
    	this.lang = l[0];
    	if (l.length > 1) dialect = l[1];
    }
/*    
    private List<RuleCond> getConditions(Long id) {
    	ArrayList<RuleCond> conds = new ArrayList<RuleCond>();
    	if (id < 0) id *= OP_NEGATED;
    	if (id == OP_POSITIVE) return conds;
    	for (RULCond cond : condService.getConditions(id)) {
    		RuleCond r = new RuleCond();
    		r.setIdCond(cond.getIdCond());
    		r.setIdSeq(cond.getIdSeq());
    		r.setLvalue(cond.getLvalue());
    		r.setLvalueType(cond.getLvalueType());
    		r.setRvalue(cond.getRvalue());
    		r.setRvalueType(cond.getRvalueType());
    		r.setOperator(cond.getOperator());
    		r.setUid(cond.getUid());
    		r.setTms(cond.getTms());
    		r.setLvalueTxt(translateCondCode(cond.getRvalueType()));
    		conds.add(r);
    	}
    	return conds;
    }
    
    private String translateCondCode(Integer code) {
    	switch (code) {
           case TYPE_TYPE       : return "Type";
           case TYPE_PROPERTY   : return "Property";
           case TYPE_ATTRIBUTE  : return "Attribute";
           case TYPE_METHOD     : return "Method";
           case TYPE_FUNCTION   : return "Function";
           case TYPE_EXPRESSION : return "Expression";
           case TYPE_SCRIPT     : return "Script";
           case TYPE_EXIST    	 : return "Exist";
           case TYPE_RSCRIPT    : return "Script";
           case TYPE_CONFIG     : return "Configuration";
           case TYPE_VALUE      : return "Value";
           case TYPE_VARIABLE   : return "Variable";
           case TYPE_STRING     : return "String";
           case TYPE_INTEGER    : return "Integer";
           case TYPE_LONG       : return "Long";
           case TYPE_BOOLEAN    : return "Boolean";
           case TYPE_DECIMAL    : return "Decimal";
           case TYPE_DATE       : return "Date";
           case TYPE_TIME       : return "Time";
           case TYPE_TIMESTAMP  : return "Timestamp";
           case TYPE_VERB       : return "Verb";
           case TYPE_LVALUE     : return "LValue";
           case TYPE_RVALUE     : return "RValue";
           case TYPE_OPTION     : return "Option";
           case TYPE_LIST       : return "List";    	
    	}
        return "";
    }
*/    
}