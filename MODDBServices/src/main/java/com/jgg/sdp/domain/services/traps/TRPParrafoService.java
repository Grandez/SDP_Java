/**
 * Servicio de acceso a la tabla TRP_Parrafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.traps;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.traps.TRPParrafo;

@Repository
public class TRPParrafoService extends AbstractService<TRPParrafo> {

    public List<TRPParrafo> getParrafosBySesion(String idSesion) {
        return list("list", idSesion);
    }
    
    public List<Object[]> getSumaParrafos(String idSesion, String modFirma) {
        return nativeQuery(TRPParrafo.SumParrafos, idSesion, modFirma);
    }
}
