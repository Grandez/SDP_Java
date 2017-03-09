package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODCics;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODCicsService extends AbstractService<MODCics> {
	public List<MODCics> listVerbs(Long idVersion) {
		return lista(idVersion);
	}

}
