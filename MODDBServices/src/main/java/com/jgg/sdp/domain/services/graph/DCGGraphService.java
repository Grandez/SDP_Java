/**
 * Servicio de acceso a la tabla MOD_Grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.graph;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.graph.DCGGraph;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class DCGGraphService extends AbstractService<DCGGraph> {

    public List<DCGGraph> getGrafo(Long idVersion, Long idGrafo) {
        return list("getGrafo", idVersion, idGrafo);
    }
}
