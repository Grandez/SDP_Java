package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULScript;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULScriptsService extends AbstractService<RULScript> {

	public String getScript(long id) {
		StringBuilder aux = new StringBuilder();
		for (RULScript f : listQuery(RULScript.getScript, id)) {
			aux.append(f.getCode());
			aux.append(";");
		}
		return aux.toString();
	}
	
	public String getScriptName(long id) {
		if (id == 0) return "NO NAME";
		List<RULScript> l = listQuery(RULScript.getScriptName, id);
		if (l.size() == 0) return "NO NAME";
		return l.get(0).getCode();
	}

	public void deleteScript(Long key) {
		deleteQuery(RULScript.delScript, key);
	}
	
	public void deleteScripts(Long key) {
		Long min = key * 10;
		Long max = (key * 10) + 9;
		deleteQuery(RULScript.delScripts, min, max);
	}
	
}
