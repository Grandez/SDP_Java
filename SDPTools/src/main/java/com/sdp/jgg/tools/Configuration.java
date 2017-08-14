package com.sdp.jgg.tools;

/**
 * Utilidad para cargar o descargar el fichero de configuracion
 *  Si se especifica full, resetea todo, si no va mirando las claves
 */
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.ctes.RC;

public class Configuration {

	public static void main(String[] args) throws Exception {
		   int rc = RC.OK;
		   Configuration launcher = new Configuration();
		   rc = launcher.start(args);
	       System.exit(rc);
		}

		private int start(String[] args) {
			int     rc   = RC.OK;
/*			
			cfg.setTitles(MSG.TITLE_SDP_ANALYZER, MSG.USE_SDP_ANALYZER);
			
			args = cfg.processCommandLine(AnalyzerParms.parms, args);
			
			if (cfg.isLocalMode()) {
				rc = processLocalMode(args);
			}
	*/		
	       return rc;		
		}

}
