/*
 * 
 */
package com.jgg.sdp.web.core;

import com.jgg.sdp.domain.base.CFGConfiguracion;
import com.jgg.sdp.domain.services.base.CFGConfiguracionService;
import com.jgg.sdp.core.config.Configuration;

public class DBConfiguration extends Configuration {

    private static Configuration cfg = null;
    
    private DBConfiguration() {
        super();
        loadConfFromDatabase();
    }
    
    public static Configuration getInstance() {
        if (cfg == null) cfg = (Configuration) new DBConfiguration();
        return cfg;
    }
    
    private void loadConfFromDatabase() {
        CFGConfiguracionService confService = new CFGConfiguracionService();
        for (CFGConfiguracion cfg : confService.getAll()) {
            conf.put(cfg.getClave(),  cfg.getValor());
        }
    }
}
