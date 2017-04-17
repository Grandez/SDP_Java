/**
 * Servicio de acceso a la tabla MOD_Resumen
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODCodigo;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODCodigoService  extends AbstractService<MODCodigo> {

	public MODCodigo getResumen(Long idVersion) {
		return findQuery(MODCodigo.find, idVersion);
	}
}
