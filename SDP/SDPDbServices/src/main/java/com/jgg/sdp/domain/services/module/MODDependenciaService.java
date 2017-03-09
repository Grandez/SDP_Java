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

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.module.MODDependencia;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class MODDependenciaService extends AbstractService<MODDependencia> {
    
    public List<MODDependencia> getDependencias(Long idVersion) {
          return list("list", idVersion);
    }
    
    public List<MODDependencia> getCopys(Long idVersion) {
        return getDepsByType(idVersion, CDG.DEP_COPY);
    }

    public List<MODDependencia> getRoutines(Long idVersion) {
        return getDepsByType(idVersion, CDG.DEP_MODULO);
    }
    
    public MODDependencia find(Long idVersion, String nombre) {
        return find("find", idVersion, nombre);
    }
    
    private List<MODDependencia> getDepsByType(Long idVersion, Integer type) {
        return list("listByType", idVersion, type);
    }
}