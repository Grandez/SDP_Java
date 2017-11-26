package com.jgg.sdp;

import com.jgg.sdp.common.config.Configuration;

import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.loader.*;

public class SDPLoader {

    private Configuration cfg = DBConfiguration.getInstance();

    private SDPLoader() {
    	initObject();
    }
    
	public static void main(String[] args) throws Exception {
	   SDPLoader launcher = new SDPLoader();
	   launcher.start(args);
	}

	private int start(String[] args) {
		int rc = RC.OK;
		args = cfg.processCommandLine(LoaderParms.parms, args);
		
		String processType = cfg.getString(CFG.LOADER_PROCESS);
		if (processType.compareToIgnoreCase("LOAD") == 0) {
			rc = processLoad(args);
		}
		else {
			rc = processUnload(args);
		}
        if (!cfg.isIVPMode()) System.exit(rc);
        return rc;
	}

	private int processLoad(String[] args) {
		String processType = cfg.getString(CFG.LOADER_TYPE);
		if (processType.compareToIgnoreCase("RULES") == 0) {
			RulesLoader loader = new RulesLoader();
			return loader.load(args);
		}
		else if (processType.compareToIgnoreCase("CONFIG") == 0) {
			ConfigLoader loader = new ConfigLoader();
			return loader.load(args);
		}

		return 0;
	}
	
	private int processUnload(String[] args) {
		return 0;
	}
	
	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_LOADER);		
	}
	
}
