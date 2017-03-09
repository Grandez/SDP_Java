/**
 * Servicios de acceso a la tabla TRP_Sesion
 * 
 * @author Javier Gonzalez Grandez
 * @date   SEP - 2015
 * @version 3.0
 * 
 */
package com.jgg.sdp.domain.services.traps;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPSesion;

@Repository
public class TRPSesionService extends AbstractService<TRPSesion> {

	public TRPSesion findBySession(String sesion) {
		return getRecord(TRPSesion.find, sesion);
	}
	
	public List<TRPSesion> listFallidas(Timestamp tms) {
	    return list("fallidas", tms);
	}

    public List<TRPSesion> listFallidasPorModulo(String firma, Timestamp tms) {
        return list("fallidasPorModulo", firma, tms);
    }
	
	public void removeTraps(String idSesion) {
		deleteQuery(TRPSesion.delTraps, idSesion);
	}
}
