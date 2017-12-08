package com.jgg.sdp.domain.services.rules;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULDescService extends AbstractService<RULDesc> {

	private HashMap<Long, String> msgs = new HashMap<Long, String>();

	public String getTitle(Long code, String[] lang) {
		return getDescItem(code, 0, lang[0], lang[1]);
	}
	
	public String getDescription(Long code, String[] lang) {
		return getDescription(code, lang[0], lang[1]);
	}
	
	public String getDescription(Long code, String lang, String dialect) {
		return getDescItem(code, 1, lang, dialect);
	}
	
	public String getDescItem(Long code, Integer type, String lang, String dialect) {
		List<RULDesc> desc = null;
		
		String msg = msgs.get((code * 10) + type);
		if (msg != null) return msg;
		
		if (lang != null) {
			if (dialect == null) dialect = lang;
			desc = listQuery (RULDesc.getDescription, code, type, lang, dialect);	
		}
		if (desc == null || desc.isEmpty()) {
			desc = listQuery (RULDesc.getDescription, code, type, SYS.DEF_LANG, SYS.DEF_DIALECT);
		}
		
		if (desc == null || desc.isEmpty()) return null;
		
		StringBuilder sb = new StringBuilder();
		for (RULDesc d : desc) sb.append(d.getTxt());
		
		return sb.toString();
	}
	
	public List<RULDesc> getDescriptionObject(Long code, String lang, String dialect) {
		return listQuery (RULDesc.getDescription, code, lang, dialect);
	}
	
	public void deleteDescriptions(Long idDesc) {
		deleteQuery(RULDesc.delDescriptions, idDesc);
	}
	public void deleteDescription(Long idDesc, int type) {
		deleteQuery(RULDesc.delDescription, idDesc, type);
	}
	
}
