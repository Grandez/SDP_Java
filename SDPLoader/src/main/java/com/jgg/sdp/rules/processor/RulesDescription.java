package com.jgg.sdp.rules.processor;

import com.jgg.sdp.domain.services.rules.RULDescService;
import com.jgg.sdp.loader.jaxb.rules.Description;
import com.jgg.sdp.loader.jaxb.rules.TextType;
import com.jgg.sdp.loader.jaxb.rules.Title;
import com.jgg.sdp.loader.jaxb.rules.TitleType;
import com.jgg.sdp.adt.ADTBag;
import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;

public class RulesDescription {

	private RULDescService descService = new RULDescService();
	
	private ADTBag<RULDesc> descs = new ADTBag<RULDesc>();
	
    private static RulesDescription desc = null;
    
    private RulesDescription() {
    	
    }
    
    public static RulesDescription getInstance() {
    	if (desc == null) desc = new RulesDescription();
    	return desc;
    }
    
    public ADTBag<RULDesc> getDescriptions() {
    	return descs;
    }
    
    public Long createTitle(Long key, Title title) {
    	if (title == null) return 0L;
    	descService.deleteDescription(key, 0);
		for (TitleType t : title.getText()) {
			createDescription(key, 0, t.getLang(), t.getDialect(), t.getValue());
		}
    	return key;
    }
    
    public Long createDescription(Long key, Description desc) {
    	Long id = key;
    	
		if (desc == null) return 0L;		
		if (desc.getRef() != null) return desc.getRef();
		if (desc.getId() != null) id = desc.getId();

		descService.deleteDescription(id, 1);
		
		for (TextType v : desc.getText()) {
			createDescription(id, 1, v.getLang(), v.getDialect(), v.getValue());
		}
		return key;
    }

    public void createDescription(Long key, int type, String lang, String dialect, String text) {
		
		String l = lang    == null ? SYS.DEF_LANG    : lang;
		String d = dialect == null ? SYS.DEF_DIALECT : dialect;
		String[] toks = text.split("(?<=\\G.{255})");
		
		for (int idx = 0; idx < toks.length; idx++) {
			RULDesc r = new RULDesc();
			r.setIdDesc(key);
			r.setIdType(type);
			r.setIdSeq(idx);
			r.setIdLang(l);
			r.setIdDialect(d);
			r.setTxt(toks[idx]);
			descs.add(r);			
		}
    }
    
    public void clear() {
    	descs = new ADTBag<RULDesc>();
    }
}
