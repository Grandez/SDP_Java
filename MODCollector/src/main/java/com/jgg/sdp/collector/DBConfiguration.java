/*
 * 
 */
package com.jgg.sdp.collector;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.domain.cfg.CFGConfiguracion;
import com.jgg.sdp.domain.services.cfg.CFGConfigurationService;

public class DBConfiguration extends ConfigurationBase implements Configuration {

    private static Configuration cfg = null;
    
    private DBConfiguration() {
        CFGConfigurationService confService = new CFGConfigurationService();
        for (CFGConfiguracion cfg : confService.getAll()) {
            setParameter(cfg.getClave(),  cfg.getValor());
        }
    }
    
    public static Configuration getInstance() {
        if (cfg == null) cfg = new DBConfiguration();
        return cfg;
    }
    
}
