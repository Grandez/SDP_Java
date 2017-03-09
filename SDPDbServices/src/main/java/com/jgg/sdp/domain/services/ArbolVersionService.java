package com.jgg.sdp.domain.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;

@Repository
public class ArbolVersionService extends AbstractService<MODVersion> {

    @Autowired
    SDPAplicacionService aplicacionService;
	@Autowired
	SDPModuloService     moduloService;
	@Autowired
	MODVersionService    versionService;
	
	public ArrayList<Long> getTreeVersion(Long idVersion) {
		ArrayList<Long> arbol = new ArrayList<Long>();
		arbol.add(idVersion);
		MODVersion version = versionService.getByVersion(idVersion);
		arbol.add(version.getIdModulo());
		SDPModulo mod = moduloService.findById(version.getIdModulo());
		arbol.addAll(aplicacionService.getTree(mod.getIdAppl()));
		return arbol;
	}
}
