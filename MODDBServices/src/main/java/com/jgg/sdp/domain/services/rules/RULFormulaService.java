package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULFormula;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULFormulaService extends AbstractService<RULFormula> {

	public String getFormula(long id) {
		StringBuilder aux = new StringBuilder();
		for (RULFormula f : listQuery(RULFormula.getFormula, id)) {
			aux.append(f.getFormula());
			aux.append(";");
		}
		return aux.toString();
	}
	
	public String getFormulaName(long id) {
		if (id == 0) return "N/A";
		List<RULFormula> l = listQuery(RULFormula.getFormulaName, id);
		if (l.size() == 0) return "N/A";
		return l.get(0).getFormula();
	}
	
	public void deleteFormula(Long key) {
		deleteQuery(RULFormula.delFormula, key);
	}
}
