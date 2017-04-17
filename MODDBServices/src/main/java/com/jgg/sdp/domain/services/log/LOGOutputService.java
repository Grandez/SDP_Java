/**
 * Servicios de lectura de las tablas de Log
 * <br>
 * Son servicios enriquecidos para abstraer la complejidad
 * de la escritura de los logs a los programas que lo consumen
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.log;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.log.LOGLogging;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.core.ctes.LOG;

@Repository
public class LOGOutputService extends AbstractService<LOGLogging> {

    public List<LOGLogging> listByCode(Integer idMsg, Timestamp tms) {
        return list("listByCode", idMsg, tms);
    }
    
    public List<LOGLogging> list(Timestamp tms, Integer max) {
        return list(max, "list", tms);
    }
    
    public List<LOGLogging> listWithFilter(Timestamp tms, Object[] filtros) {
        Object[] prms = {null, null, null, null, null};
        int count = 1;
        String query="SELECT l FROM LOGLogging l WHERE l.tms > ?1 ";
        prms[0] = tms;
        if (filtros[0] != null) {  
            prms[count] = filtros[0];
            query = query + " AND l.idMsg = ?" + ++count;
        }
        if (filtros[1] != null) {
            prms[count] = filtros[1];
            query = query + " AND l.objeto = ?" + ++count;
        }
        if (filtros[2] != null) {
            prms[count] = filtros[2];
            query = query + " AND l.uid = ?" + ++count;
        }

        if (filtros[3] != null) {
            prms[count] = filtros[3];
            query = query + " AND l.tms BETWEEN ?" + ++count;
            prms[count] = filtros[4];
            query = query + " AND ?" + ++count;
        }

        return getList(query, prms);
    }
    
    public List<LOGLogging> getAlertas(Timestamp tms) {
        return list("listByType", tms, LOG.ALERT);
    }

}
