/**
 * Servicio de acceso a la tabla SUM_Parrafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.summary;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.summary.SUMParrafo;

@Repository
public class SUMParrafoService extends AbstractService<SUMParrafo> {

    public List<Object[]> customQuery(String stmt) {
        return nativeQuery(stmt, new Object[0] );
    }
    
    public List<SUMParrafo> getParrafosByName(Long idVersion) {
        return  list("ParrafosByName",  idVersion);
    }

}
