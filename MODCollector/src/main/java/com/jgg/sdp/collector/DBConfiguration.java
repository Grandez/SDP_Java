/*
 * 
 */
package com.jgg.sdp.collector;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.domain.cfg.CFGConfiguracion;
import com.jgg.sdp.domain.services.cfg.CFGConfigurationService;

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
        CFGConfigurationService confService = new CFGConfigurationService();
        for (CFGConfiguracion cfg : confService.getAll()) {
            conf.put(cfg.getClave(),  cfg.getValor());
        }
    }
}
