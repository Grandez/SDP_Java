/**
 * Servicio de acceso a la tabla SES_Parrafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.session;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.session.SESParrafo;

@Repository
public class SESParrafoService extends AbstractService<SESParrafo> {

	public List<SESParrafo> getParrafosBySesion(String idSesion) {
		return  list("ParrafosBySesion", idSesion);
	}

	public List<SESParrafo> getParrafosByName(String idSesion, Long idVersion) {
        return  list("ParrafosByName", idSesion, idVersion);
    }

}
