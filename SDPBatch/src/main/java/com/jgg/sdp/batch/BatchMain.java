package com.jgg.sdp.batch;

import com.jgg.sdp.batch.steps.CleanVersions;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;

public class BatchMain 
{
	
	/* Tabla de parametros aceptados
	 * 0 - valor simbolico
	 * 1 - parametro corto
	 * 2 - parametro largo
	 * 3 - variable de entorno
	 * 4 - clave de configuracion
	 * 5 - Requiere valor (0 - no)
	 * 6 - tipo de dato
	 */

	private String prm[][] = { 
	};

    private Configuration cfg = Configuration.getInstance();

    public static void main( String[] args ) {
       int rc = RC.OK;
	   BatchMain launcher = new BatchMain();
	   rc = launcher.start(args);
       System.exit(rc);

    }
    
    private int start(String[] args) {
		int    maxRC   = RC.OK;
		
		cfg.setTitles(MSG.TITLE_SDP_BATCH);
		
		args = cfg.processCommandLine(prm, args);
		
		cleanVersions();
		
        return maxRC;
    }

    private void cleanVersions() {
        CleanVersions clean = new CleanVersions();
        clean.setMaxVersions(cfg.getInteger(CFG.MAX_VERSIONS, CFG.DEF_MAX_VERSIONS));
        clean.process();
    }
}
