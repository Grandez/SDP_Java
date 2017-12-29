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
		return getAsLine(getDescItem(code, 0, lang));
	}
	
	public List<String> getDescription(Long code, String[] lang) {
		List<RULDesc> l = getDescItem(code, 1, lang);
		ArrayList<String> lista = new ArrayList<String>();
		for (RULDesc r : l) {
			lista.add(r.getTxt());
		}
		return lista;
	}
	
	private String getAsLine(List<RULDesc> lista) {
		StringBuilder sb = new StringBuilder();
		if (lista.size() == 0) return null;	
		for (RULDesc r : lista) {
			sb.append(r.getTxt());
			sb.append('\n');
		}
		return sb.toString();
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
