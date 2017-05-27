package com.jgg.sdp.domain.named.module;

import javax.persistence.*;

import com.jgg.sdp.domain.module.MODSecciones;
import com.jgg.sdp.domain.services.AbstractService;


@NamedQueries({
    @NamedQuery(name="MODSecciones.find",
                query="SELECT s FROM MODSecciones s WHERE s.idVersion = ?1")
}) 

public class MODSeccionesNamed extends AbstractService<MODSecciones> {
	public MODSecciones find (long idVersion) {
		return super.find(idVersion);
	}

}
