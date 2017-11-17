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

import com.jgg.sdp.domain.cfg.CFGConfig;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class CFGConfigService extends AbstractService<CFGConfig> {

    public List<CFGConfig> getAll() {
        return listQuery(CFGConfig.listAll);
    }
    
    public CFGConfig get(String key) {
        return findQuery(CFGConfig.getItem, key);
    }
    
    public void deleteAll() {
    	updateQuery(CFGConfig.delAll);
    }
}
