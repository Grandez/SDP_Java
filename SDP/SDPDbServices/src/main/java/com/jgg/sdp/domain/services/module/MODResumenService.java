/**
 * Servicio de acceso a la tabla MOD_Resumen
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODResumen;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODResumenService  extends AbstractService<MODResumen> {

	public MODResumen getResumen(Long idVersion) {
		return find("find", idVersion);
	}
}
