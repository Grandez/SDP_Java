/**
 * Servicio de acceso a la tabla TRP_Modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPModulo;

@Repository
public class TRPModuloService extends AbstractService<TRPModulo> {

	public List<TRPModulo> getModulosBySesion(String idSesion) {
		return list("list", idSesion);
	}
	
    public List<Object[]> getModulos(String idSesion) {
        return nativeQuery(TRPModulo.SumModulos, idSesion);
    }
}
