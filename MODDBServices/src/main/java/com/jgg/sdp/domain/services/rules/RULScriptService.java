package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULScript;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULScriptService extends AbstractService<RULScript> {

	public String getScript(long id) {
		StringBuilder aux = new StringBuilder();
		for (RULScript f : listQuery(RULScript.getFormula, id)) {
			aux.append(f.getCode());
			aux.append(";");
		}
		return aux.toString();
	}
	
	public String getScriptName(long id) {
		if (id == 0) return "NO NAME";
		List<RULScript> l = listQuery(RULScript.getFormulaName, id);
		if (l.size() == 0) return "NO NAME";
		return l.get(0).getCode();
	}
	
	public void deleteFormula(Long key) {
		deleteQuery(RULScript.delFormula, key);
	}
}
