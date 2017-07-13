package com.jgg.sdp.domain.services.core;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPFuente;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPFuenteService extends AbstractService<SDPFuente> {

	public SDPFuente findById(Long idFile) {
		return findQuery(SDPFuente.findById, idFile);
	}
}
