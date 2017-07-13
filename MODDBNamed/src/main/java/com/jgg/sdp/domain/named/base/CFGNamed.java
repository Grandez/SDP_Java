package com.jgg.sdp.domain.named.base;

import javax.persistence.*;
import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.cfg.CFGConfiguracion;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
    @NamedQuery(name="CFGConfiguracion.find",
                query="SELECT c FROM CFGConfiguracion c WHERE c.clave = ?1")

}) 

@Repository
public class CFGNamed extends AbstractService<CFGConfiguracion> {

	public CFGConfiguracion find(String clave) {
		return super.find(clave);
	}
}
