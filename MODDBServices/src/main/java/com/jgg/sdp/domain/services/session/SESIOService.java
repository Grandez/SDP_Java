/**
 * Servicio de acceso a la tabla SES_IO
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.session;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.session.SESIO;

@Repository
public class SESIOService extends AbstractService<SESIO> {

    public SESIO find(String idSesion, Long idVersion) {
        return find("find", idSesion, idVersion);
    }

    public SESIO findBySesion(String idSesion) {
        return find("findBySesion", idSesion);
    }
    
    public List<Object[]> getIOModulo(Long idVersion, Timestamp from) {
        return listAbstract("sumModulo", idVersion, from);
    }
}
