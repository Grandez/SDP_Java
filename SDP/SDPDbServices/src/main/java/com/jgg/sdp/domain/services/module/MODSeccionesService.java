/**
 * Servicio de acceso a la tabla MOD_Secciones
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODSecciones;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODSeccionesService extends AbstractService<MODSecciones> {

	public MODSecciones getSecciones(Long idVersion) {
		return find("find", idVersion);
	}
}
