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

	/**
     * Obtiene el registro de la sesion indicada
     *
     * @param sesion
     * @return el objeto sesion asociado
     */
	public TRPSesion findBySession(String sesion) {
		return getRecord(TRPSesion.find, sesion);
	}
	
	/**
     * Obtiene la lista de ejecuciones fallidas desde una fecha dada
     *
     * @param tms   Timestamp con la fecha desde 
     * @return La lista de ejecuciones fallidas
     */
	public List<TRPSesion> listFallidas(Timestamp tms) {
	    return list("fallidas", tms);
	}

    /**
     * Obtiene la lista de ejecuciones fallidas desde una fecha dada
     * para un modulo dado por su firma
     *
     * @param firma Firma del modulo
     * @param tms   Timestamp con la fecha desde 
     * @return La lista de ejecuciones fallidas de ese modulo
     */
    public List<TRPSesion> listFallidasPorModulo(String firma, Timestamp tms) {
        return list("fallidasPorModulo", firma, tms);
    }
	
	/**
     * Eimina toda la informacion de los TRAPS generados durante la sesion
     * Por la integridad referencia borra el resto de la tablas
     *
     * @param idSesion Identifiador de la sesion
     */
	public void removeTraps(String idSesion) {
		deleteQuery(TRPSesion.delTraps, idSesion);
	}
}
