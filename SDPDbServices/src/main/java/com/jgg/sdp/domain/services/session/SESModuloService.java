/**
 * Servicio de acceso a la tabla SES_Modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.session.SESModulo;

@Repository
public class SESModuloService extends AbstractService<SESModulo> {

	public ArrayList<SESModulo> getModulosBySesion(String idSesion) {
		return (ArrayList<SESModulo>) list("ModulosBySesion", idSesion);
	}
	
	public SESModulo find(String idSesion, Long idVersion) {
	    return find("findInSesion", idSesion, idVersion);
	}
	
	public List<Object[]> getResumenEjecuciones(List<String> lista, Timestamp from) {
	    return listAbstract("resEjecuciones", lista, from);
	}

    public List<Object[]> getResumenEjecucionesModulo(Long idVersion, Timestamp from) {
        return listAbstract("resEjecucionesModulo", idVersion, from);
    }
	
	public List<SESModulo> getTodos() {
	    return list("todos");
	}
}
