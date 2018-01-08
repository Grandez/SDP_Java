package com.jgg.sdp.domain.services.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.tools.Cadena;

@Repository
public class RULDescService extends AbstractService<RULDesc> {

	private HashMap<Long, String> msgs = new HashMap<Long, String>();

	public String getTitle(Long code, String[] lang) {
		return getItemByType(code, 0, lang);
	}
	
	public String getDescription(Long code, String[] lang) {
		return getItemByType(code, 1, lang);
	}
	
	private String getItemByType(Long code, int type, String[] lang ) {
		Long key = (code * 10) + type;
		String cache = msgs.get(key);
		if (cache != null) return cache;
		StringBuilder sb = new StringBuilder();

		for (RULDesc r : getDescItem(code, type, lang)) {
			sb.append(r.getTxt());
			sb.append('\n');
		}
		if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
		msgs.put(key, sb.toString());
		return msgs.get(key);
	}
	
	private List<RULDesc> getDescItem(Long code, Integer type, String[] lang) {
		// Lang and dialect
		List<RULDesc> list = listData(code, type, lang[0], lang[1]);
		
		// Dialect
		if (list.size() == 0) {
            list = listData(code, type, lang[0], SYS.DEF_DIALECT);
		}
		
		// Default lang
		if (list.size() == 0) {
			list = listData(code, type, SYS.DEF_LANG, SYS.DEF_DIALECT);
		}
		
		return list; 		
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

	private List<RULDesc> listData(Long code, Integer type, String lang, String dialect) {
		return listQuery (RULDesc.getDescription, code, type, lang, dialect);
	}
}
