package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.services.rules.RULDescService;
import com.jgg.sdp.rules.xml.jaxb.Description;
import com.jgg.sdp.rules.xml.jaxb.Text;

import java.util.ArrayList;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;

public class RulesDescription {

	private RULDescService descService = new RULDescService();
	
	private ArrayList<RULDesc> descs = new ArrayList<RULDesc>();
	
    private static RulesDescription desc = null;
    
    private RulesDescription() {
    	
    }
    
    public static RulesDescription getInstance() {
    	if (desc == null) desc = new RulesDescription();
    	return desc;
    }
    
    public Long createDescription(Long key, Description desc) {
		if (desc == null) return 0L;
			
		for (Text v : desc.getText()) {
			String l = v.getLang() == null ? SYS.DEF_LANG : v.getLang();
			String d = v.getDialect() == null ? l.toUpperCase() : v.getDialect();
			RULDesc r = descService.getDescriptionObject(key, l, d);
			if (r == null) {
				r = new RULDesc();
				r.setIdDesc(key);
				r.setIdLang(l);
				r.setIdDialect(d);
			}
			r.setTxt(v.getValue());
			descs.add(r);			
		}
		return key;
    }
    
    public void clear() {
    	descs = new ArrayList<RULDesc>();
    }
}
