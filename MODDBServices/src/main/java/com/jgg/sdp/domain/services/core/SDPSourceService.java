/**
 * Servicio de acceso a la tabla MOD_Source
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.core;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPSource;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPSourceService extends AbstractService<SDPSource> {

	public SDPSource getSource (Long idFile) {
		return find("find", idFile);
	}
}
