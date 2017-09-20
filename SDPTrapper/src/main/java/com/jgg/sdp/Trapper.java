/**
 * Lee un modulo fuente
 * Lo analiza
 * Genera el nuevo modulo fuente
 * 
 * Envia los datos a SDPPersister
 */
package com.jgg.sdp;

import java.nio.charset.StandardCharsets;

import com.jgg.sdp.clients.IClientPersister;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.core.unit.*;
import com.jgg.sdp.trapper.ClientFactory;
import com.jgg.sdp.trapper.TrapperParms;

public class Trapper {

	private Configuration cfg = ConfigurationLocal.getInstance();
    private Messages      msg = Messages.getInstance("PARSER");    
    

    private SDPUnitBase unit   = null;

    private Trapper() {
    	initObject();
    }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Trapper launcher = new Trapper();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		int     rc      = RC.OK;
		int     maxRC   = RC.OK;

		args = cfg.processCommandLine(TrapperParms.parms, args);
		
		if (args.length == 0) args = new String[]{"*"};
		
		for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
			rc = processArchivo(archivo);
			if (rc > maxRC) maxRC = rc;
		}
		
       return rc;		
	}

	private int processArchivo(Archivo archivo) {
		int rc  = RC.OK;
		int res = MSG.OK;
			
		if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, archivo.getBaseName());
        
		try {		
	        unit = new SDPUnitBase(archivo);
			rc = sendFile(unit.getCurrentSource(), cfg.getInteger(CFG.FILE_TYPE, CDG.SOURCE_CODE));
			if (rc != 0) res = MSG.KO;
			if (cfg.getVerbose() > 1)    msg.progress(res);
		} catch (Exception e) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.ERROR);
			unit.setStatus(CDG.STATUS_ERROR);
            msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, unit.getMemberName()));
            e.printStackTrace();
            rc = RC.FATAL;
		}
		return rc;
    }
	
	private int sendFile(Source source, int type) {
		
		IClientPersister html = ClientFactory.getClient();
		byte[] data = new String(source.getRawData()).getBytes(StandardCharsets.UTF_8);
		int rc = html.sendZipFile(source.getArchivo().getAbsolutePath(), type, data);
		return rc;
	}


	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_TRAPPER);
		cfg.addTitle(CDG.TXT_DESC,  MSG.DESC_TRAPPER);
		cfg.addTitle(CDG.TXT_SKIP,  0);
		cfg.addTitle(CDG.TXT_USE,   MSG.USE_SDP_TRAPPER);
		cfg.addTitle(CDG.TXT_SKIP,  0);
		cfg.addTitle(CDG.TXT_ITEM,  MSG.DESC_TRAPPER_41);
		cfg.addTitle(CDG.TXT_ITEM,  MSG.DESC_TRAPPER_42);
		cfg.addTitle(CDG.TXT_NEXT,  MSG.DESC_TRAPPER_43);
	}
}
