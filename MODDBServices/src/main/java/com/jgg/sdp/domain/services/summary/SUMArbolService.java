/**
 * Servicio de acceso a la tabla SUM_Arbol
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.summary;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.summary.SUMArbol;

@Repository
public class SUMArbolService extends AbstractService<SUMArbol> {

    public SUMArbol getCall(Long idCalling, Long idCalled, String nombre) {
        return find("find", idCalling, idCalled, nombre);
    }
    
    public List<SUMArbol> getCalled(Long idCalling) {
        return list("listCalled", idCalling);   
    }
    
    public List<Object[]> getCountCalled(Long idCalling) {
        return nativeQuery(SUMArbol.Llamados, idCalling);
    }
}
