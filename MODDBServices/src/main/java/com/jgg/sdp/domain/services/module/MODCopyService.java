/**
 * Servicio de acceso a la tabla MOD_Dependencia
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.module;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODCopy;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODCopyService extends AbstractService<MODCopy> {
    
    public List<MODCopy> getDependencias(Long idVersion) {
          return list("list", idVersion);
    }
    
    public MODCopy find(Long idVersion, String nombre) {
        return find("find", idVersion, nombre);
    }

    public List<MODCopy> getDepsByType(Long idVersion, Integer type) {
        return list("listByType", idVersion, type);
    }
}