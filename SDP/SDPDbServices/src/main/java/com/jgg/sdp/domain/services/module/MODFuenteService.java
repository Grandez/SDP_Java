/**
 * Servicio de acceso a la tabla MOD_Source
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODFuente;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODFuenteService extends AbstractService<MODFuente> {

	public MODFuente getSource (Long idVersion) {
		return find("find", idVersion);
	}
}
