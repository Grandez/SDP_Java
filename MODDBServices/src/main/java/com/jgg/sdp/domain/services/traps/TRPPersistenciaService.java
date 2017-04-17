/**
 * Servicio de acceso a la tabla TRP_Persistencia
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPPersistencia;

@Repository
public class TRPPersistenciaService extends AbstractService<TRPPersistencia> {

	public List<TRPPersistencia> getPersistencia(String idSesion, String idModulo) {
		return getList(TRPPersistencia.listBySesion, idSesion, idModulo);
	}

}
