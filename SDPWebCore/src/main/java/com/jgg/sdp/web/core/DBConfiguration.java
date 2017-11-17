/*
 * 
 */
package com.jgg.sdp.web.core;

import java.util.*;

import com.jgg.sdp.common.config.*;

import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.CFGConfigService;

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
        CFGConfigService confService = new CFGConfigService();
        for (CFGConfig cfg : confService.getAll()) {
            conf.put(cfg.getClave(),  cfg.getValor());
        }
    }

    public static List<CFGConfig> getRawConfiguration() {
        CFGConfigService confService = new CFGConfigService();
        return  confService.getAll();
    }

}
