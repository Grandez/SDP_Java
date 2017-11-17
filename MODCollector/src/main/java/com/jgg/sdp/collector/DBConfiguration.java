/*
 * 
 */
package com.jgg.sdp.collector;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.domain.cfg.CFGConfig;
import com.jgg.sdp.domain.services.cfg.CFGConfigService;

public class DBConfiguration extends ConfigurationBase implements Configuration {

    private static Configuration cfg = null;
    
    private DBConfiguration() {
        CFGConfigService confService = new CFGConfigService();
        for (CFGConfig cfg : confService.getAll()) {
            setParameter(cfg.getClave(),  cfg.getValor());
        }
    }
    
    public static Configuration getInstance() {
        if (cfg == null) cfg = new DBConfiguration();
        return cfg;
    }
    
}
