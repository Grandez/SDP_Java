/**
 * Servicio de acceso a la tabla CFG_Configuracion
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain.services.cfg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.cfg.CFGConfiguracion;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class CFGConfiguracionService extends AbstractService<CFGConfiguracion> {

    public List<CFGConfiguracion> getAll() {
        return listQuery(CFGConfiguracion.listAll);
    }
    
    public CFGConfiguracion get(String key) {
        return find("get", key);
    }
}
