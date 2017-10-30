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
	
	public String getDescription(Long code, String lang, String dialect) {
		RULDesc desc = null;
		
		String msg = msgs.get(code);
		if (msg != null) return msg;
		if (lang != null) {
			if (dialect == null) dialect = lang;
			desc = findQuery (RULDesc.getDescription, code, lang, dialect);	
		}
		if (desc == null) {
			desc = findQuery (RULDesc.getDescription, code, SYS.DEF_LANG, SYS.DEF_DIALECT);
		}
		return (desc == null) ? "N/A" : desc.getTxt();
	}
	
	public List<RULDesc> getDescriptionObject(Long code, String lang, String dialect) {
		return listQuery (RULDesc.getDescription, code, lang, dialect);
	}
	
	public List<RULDesc> findDescription(Long code) {
		return listQuery(RULDesc.listDescriptions, code);
	}
	public void deleteDescription(Long idDesc) {
		deleteQuery(RULDesc.delDescription, idDesc);
	}
}
