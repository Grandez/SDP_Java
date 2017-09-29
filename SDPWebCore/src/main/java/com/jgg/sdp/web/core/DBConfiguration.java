/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.*;

import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.CFGConfigurationService;



import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.config.ConfigurationBase;

public class DBConfiguration extends ConfigurationBase implements Configuration {

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

    public static List<CFGConfiguracion> getRawConfiguration() {
        CFGConfigurationService confService = new CFGConfigurationService();
        return  confService.getAll();
    }

}
