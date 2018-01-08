package com.jgg.sdp.web.adm.code;

import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jgg.sdp.core.exceptions.JavaException;
import com.jgg.sdp.domain.rules.RULSample;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.tools.Reflect;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.MDText;

@Component
public class RulesText {
	
    @Autowired
    private RULMessagesService msgService;    
    @Autowired
    private RULDescService     descService;    
    @Autowired
    private RULSamplesService sampService;    


    public void mountMessages(JSonItem item, String[] lang) {
    	mountDescription(item, lang);
    	mountItemConditions(item, lang);
    	
    	for (JSonRule rule : item.getRules()) {
		     rule.setMessage(mountMessage(rule.getIdMsg(), lang, null, rule, item));
		     mountRuleConditions(rule, item, lang);
		     
             JSonRuleCond cond = rule.getCondition(); 
		     cond.setMessage(mountMessage(cond.getIdMsg(), lang, cond, rule, item));
		     rule.setMessage(cond.getMessage());
	    }
    }
    
	public String mountMessage(Long idMsg, String[] lang, JSonRuleCond cond, JSonRule rule, JSonItem item) {
		String msg = null;
		
		if (idMsg != 0) msg = msgService.getMessage(idMsg, lang);
		
		if (msg == null) {
			if (rule != null) msg = rule.getMessage();
			if (msg  == null) msg = item.getMessage(); 
		}

		if (msg == null) return msg;
    	MDText md = new MDText();
    	return md.renderText(translateText(msg, cond, rule, item));
	}
	

	private void mountDescription(IJSonText item, String[] lang) {
		Long id = item.getIdDesc();
		if (id == 0L) return;
		
    	String desc = descService.getDescription(id, lang); 
    	MDText md = new MDText();
    	item.setDescription(md.renderText(desc));
	}

	private void mountItemConditions(JSonItem item, String[] lang) {
		for (JSonRuleCond cond : item.getActivations()) {
			mountCondition(cond, null, item, lang);
		}
		
	}
	private void mountRuleConditions(JSonRule rule, JSonItem item, String[] lang) {
		for (JSonRuleCond cond : rule.getActivations()) {
			mountCondition(cond, rule, item, lang);
		}
	}
	
	private void mountCondition(JSonRuleCond cond, JSonRule rule, JSonItem item, String[] lang) {
		Long key = cond.getIdMsg();
		if (key == 0) return;
		
		String msg = msgService.getMessage(key, lang);
		
    	String txt = translateText(msg, cond, rule, item); 
    	MDText md = new MDText();
		cond.setMessage(md.renderText(txt));
	}
	
	private String translateText(String msg, Object... obj) {
		Object val = null;
		String temp = msg;

		Pattern p = Pattern.compile(":[a-zA-Z0-9_]+:");
		Matcher m = p.matcher(msg);
		while (m.find()) {
			String mask = m.group(0);
			String var = mask.substring(1, mask.length() - 1);
			String method = "get" + var;

			for (int idx = 0; idx < obj.length; idx++) {
				try  { 
					if (obj[idx] != null) {
					    val = Reflect.executeMethod(obj[idx], method);
                        temp = temp.replaceAll(mask, val.toString());
                        break;
					}    
				} catch  (JavaException ex)   {
				    val = null;
			    }					
			}
			if (val == null) return temp;
			m = p.matcher(temp);
		}
		if (temp.endsWith("\n")) return temp.substring(0, temp.length() - 1);
		return temp;
	}
	
    public JSonRuleSample mountSample(Long idSample, String[] lang) {
    	if (idSample == null) return null;
    	JSonRuleSample sample = new JSonRuleSample();
    	StringBuilder sb = new StringBuilder();
    	for (RULSample s : sampService.getDescription(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setDesc(sb.toString());
    	
    	sb = new StringBuilder();
    	for (RULSample s : sampService.getSampleOK(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setOk(sb.toString());
    	
    	sb = new StringBuilder();
    	for (RULSample s : sampService.getSampleKO(idSample)) {
    		sb.append(s.getData());
    		sb.append('\n');
    	}
    	sample.setKo(sb.toString());
    	
    	return sample;
    }
    	
}
