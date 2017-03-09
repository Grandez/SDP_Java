/**
 * Servicio de acceso a la tabla SUM_Modulo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.summary;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.summary.SUMModulo;

@Repository
public class SUMModuloService extends AbstractService<SUMModulo> {

    public SUMModulo find(Long idVersion) {
        return find("findByVersion", idVersion);
    }
}
