/**
 * Servicio de acceso a la tabla MOD_Grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODGrafo;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODGrafoService extends AbstractService<MODGrafo> {

    public List<MODGrafo> getGrafo(Long idVersion, Long idGrafo) {
        return list("getGrafo", idVersion, idGrafo);
    }
}