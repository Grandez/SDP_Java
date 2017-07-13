package com.jgg.sdp.domain.named.core;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPAplicacion;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
	@NamedQuery( name="SDPAplicacion.find"
                ,query="SELECT a FROM SDPAplicacion a WHERE a.id = ?1")
   ,@NamedQuery( name="SDPAplicacion.findByName" 
                ,query="SELECT a FROM SDPAplicacion a WHERE a.aplicacion = ?1")
   ,@NamedQuery( name="SDPAplicacion.listByPadreId" 
                ,query="SELECT a FROM SDPAplicacion a WHERE a.padre = ?1")
})

@Repository
public class SDPAplicacionNamed extends AbstractService<SDPAplicacion> {

	public SDPAplicacion find(Long id) {
		return getRecord("SELECT a FROM SDPAplicacion a WHERE a.id = ?1", id);
//	    return super.find(id);
	}	

	public List<SDPAplicacion> listByPadreId(Long id) {
		return listRecords("SELECT a FROM SDPAplicacion a WHERE a.padre = ?1", id);
//		return list("listByPadreId", id);
	}

}
