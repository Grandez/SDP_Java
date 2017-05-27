package com.jgg.sdp.domain.named.core;

import javax.persistence.*;

import com.jgg.sdp.domain.core.SDPFuente;
import com.jgg.sdp.domain.services.AbstractService;

@NamedQueries({
	@NamedQuery( name="SDPFuente.find" 
                ,query="Select m FROM SDPFuente m WHERE m.idFile = ?1")
})

public class SDPFuenteNamed extends AbstractService<SDPFuente> {

	public SDPFuente find(long idFile) {
		return super.find(idFile);
	}
}
