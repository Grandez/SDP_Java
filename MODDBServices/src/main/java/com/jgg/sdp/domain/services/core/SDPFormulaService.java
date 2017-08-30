package com.jgg.sdp.domain.services.core;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPFormula;
import com.jgg.sdp.domain.services.AbstractService;


@Repository
public class SDPFormulaService extends AbstractService<SDPFormula> {

	public String getFormula(long id) {
		StringBuilder aux = new StringBuilder();
		for (SDPFormula f : listQuery(SDPFormula.getFormula, id)) {
			aux.append(f.getFormula());
			aux.append(";");
		}
		return aux.toString();
	}
}
