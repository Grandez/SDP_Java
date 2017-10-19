package com.jgg.sdp;

import com.jgg.sdp.common.config.Configuration;

import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.rules.*;

public class RulesManager {

    private Configuration cfg = DBConfiguration.getInstance();
    
	public static void main(String[] args) throws Exception {
		int rc = RC.OK;
	   RulesManager launcher = new RulesManager();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		int rc = RC.OK;
		args = cfg.processCommandLine(RulesManagerParms.parms, args);
		
//		RulesUnloader unloader = new RulesUnloader();
//		unloader.unload(args);
		
		RulesLoader loader = new RulesLoader();
		loader.load(args);
		
	/*	
		processXMLRule(new Archivo("P:/SDP/config/rule00.xml"));
		for (Archivo archivo : FileFinder.find(null, args)) {
	        processXMLRule(archivo);
	        storeXMLRule();
		}
	*/
		return rc;		
	}

	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_RULES);		
	}
	
}
