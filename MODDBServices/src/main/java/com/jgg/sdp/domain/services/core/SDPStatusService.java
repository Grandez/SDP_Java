package com.jgg.sdp.domain.services.core;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.core.SDPStatus;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPStatusService extends AbstractService<SDPStatus> {
	
	public List<SDPStatus> listAll(String name) {
		SDPModuloService modService = new SDPModuloService();
		SDPModulo mod = modService.findByNameAndType(name, CDG.SOURCE_CODE);
		if (mod == null) return new ArrayList<SDPStatus>();
		return listQuery(SDPStatus.listAll, mod.getIdModulo());
	}

}
