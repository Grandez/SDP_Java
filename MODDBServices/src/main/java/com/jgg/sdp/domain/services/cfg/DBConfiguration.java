/*
 * 
 */
package com.jgg.sdp.domain.services.cfg;

import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.CFGConfigService;
import com.jgg.sdp.common.config.*;

public class DBConfiguration extends ConfigurationBase implements Configuration {

	private static boolean loading = false;
	
    private DBConfiguration() {
        CFGConfigService confService = new CFGConfigService();
        for (CFGConfig config : confService.getAll()) {
            conf.put(config.getClave(),  config.getValor());
        }    	

    }
    
    public static Configuration getInstance() {
		if (cfg == null && loading == false) {
			loading = true;
			cfg = new DBConfiguration();
		}
		return cfg;		
    }
    
}
