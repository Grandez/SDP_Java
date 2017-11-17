package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.services.rules.RULDescService;
import com.jgg.sdp.loader.jaxb.rules.Description;
import com.jgg.sdp.loader.jaxb.rules.TextType;
import com.jgg.sdp.adt.SDPBag;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;

public class RulesDescription {

	private RULDescService descService = new RULDescService();
	
	private SDPBag<RULDesc> descs = new SDPBag<RULDesc>();
	
    private static RulesDescription desc = null;
    
    private RulesDescription() {
    	
    }
    
    public static RulesDescription getInstance() {
    	if (desc == null) desc = new RulesDescription();
    	return desc;
    }
    
    public SDPBag<RULDesc> getDescriptions() {
    	return descs;
    }
    
    public Long createDescription(Long key, Description desc) {
    	Long id = key;
    	
		if (desc == null) return 0L;
		
		if (desc.getRef() != null) return desc.getRef();
		
		if (desc.getId() != null) id = desc.getId();
		
		descService.deleteDescription(id);
		
		for (TextType v : desc.getText()) {
			String l = v.getLang() == null ? SYS.DEF_LANG : v.getLang();
			String d = v.getDialect() == null ? l.toUpperCase() : v.getDialect();
			RULDesc r = new RULDesc();
			r.setIdDesc(id);
			r.setIdLang(l);
			r.setIdDialect(d);
			r.setTxt(v.getValue());
			descs.add(r);			
		}
		return key;
    }
    
    public void clear() {
    	descs = new SDPBag<RULDesc>();
    }
}
