/**
 * Lee un modulo fuente
 * Lo analiza
 * Genera el nuevo modulo fuente
 * 
 * Envia los datos a SDPPersister
 */
package com.jgg.sdp;

import com.jgg.sdp.client.ICommClient;
import com.jgg.sdp.common.*;
import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.exceptions.SDPException;
import com.jgg.sdp.common.files.*;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.trapper.ClientFactory;
import com.jgg.sdp.trapper.TrapperParms;

public class Trapper {

	private Configuration cfg = ConfigurationLocal.getInstance();
    private Messages      msg = Messages.getInstance("PARSER");    
    

    private SDPUnit unit   = null;

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
		
		for (Archive archivo : FileFinder.find(cfg.getInputDir(), args)) {
			rc = processArchivo(archivo);
			if (rc > maxRC) maxRC = rc;
		}
		
       return rc;		
	}

	private int processArchivo(Archive archivo) {
		int rc  = RC.OK;
		int res = MSG.OK;
			
		if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, archivo.getBaseName());

        unit = new SDPUnit(archivo.getBaseName());
        
		try {
			SDPMember source = new SDPMember(archivo.getAbsolutePath(), CDG.SOURCE_CODE);
			source.loadData();
		    unit.addMember(source);	

			rc = sendUnit(unit);
			if (rc != 0) res = MSG.KO;
			if (cfg.getVerbose() > 1)    msg.progress(res);
		} catch (Exception e) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.ERROR);
            msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, unit.getName()));
            e.printStackTrace();
            rc = RC.FATAL;
		}
		return rc;
    }
	
	private int sendUnit(SDPUnit unit) {
		/*
		byte[] data = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(unit);
		  out.flush();
		  out.close();
//		  data = bos.toByteArray();

		} catch (Exception e) {
			
		}
		finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		*/
		ICommClient html = ClientFactory.getClient();
//		byte[] data = new String(source.getRawData()).getBytes(StandardCharsets.UTF_8);
		int rc = html.sendUnit(unit);
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
