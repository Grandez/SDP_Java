package com.sdp.jgg.ts;

import java.util.List;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;

public class TSMain 
{
    private Configuration cfg = Configuration.getInstance();

    SDPModuloService  moduloService  = new SDPModuloService();
    MODVersionService versionService = new MODVersionService();
    MODCicsService    cicsService    = new MODCicsService();
    
	String prm[][] = { 
	};

	public static void main(String[] args) {	
	   int rc = RC.OK;
	   TSMain launcher = new TSMain();
	   try {
	      rc = launcher.start(args);
	   }
	   catch (Exception e) {
		   System.exit(RC.FATAL);
	   }
       System.exit(rc);
	}

	private int start(String[] args) {

        int     maxRC   = RC.OK;
		
		cfg.setTitles(MSG.TITLE_SDP_TS);
		
		args = cfg.processCommandLine(prm, args);
		
		for (int iParm = 0; iParm < args.length; iParm++) {
			for (SDPModulo m : moduloService.getModulesByPseudoMask(args[iParm])) {
                 if (cfg.getVerbose() > 0) {
                     System.out.print(m.getNombre());
                 }
                 maxRC |= processCICS(m.getIdVersion());				
			}
		}
        return maxRC;
	}
	
	private int processCICS(Long idVersion) {
		int rc = 0;
		List<MODCics> lista = cicsService.listVerbs(idVersion); 
		for (MODCics c : lista) {
            rc |= c.getTipo();	
		}
		
		if (cfg.getVerbose() > 0) System.out.println("\t" + rc);
		if (cfg.getVerbose() > 1) {
            for (MODCics c : lista) {
            	System.out.println("\t" + c.getVerbo() + "\t" + c.getTipo());
            }
		}
		return rc;
	}
}
