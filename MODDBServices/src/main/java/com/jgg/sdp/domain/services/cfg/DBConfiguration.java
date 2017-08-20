/*
 * 
 */
package com.jgg.sdp.domain.services.cfg;

import java.util.*;

import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.CFGConfigurationService;



import com.jgg.sdp.core.config.Configuration;

public class DBConfiguration extends Configuration {

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
