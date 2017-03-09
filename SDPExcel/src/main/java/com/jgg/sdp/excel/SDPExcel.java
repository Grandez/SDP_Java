/**
 * Modulo principal de ejecucion del analizador
 * Se ejecuta con la instrucción main
 *    
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.excel;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.excel.map.XLSLoader;
import com.jgg.sdp.excel.map.XLSMapper;


public class SDPExcel {

    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();
        
    private SDPModuloService  moduloService  = new SDPModuloService();
    private MODVersionService versionService = new MODVersionService();
    
    private SDPModulo    modulo = null;
    private MODVersion  version = null;
    private Long        idVersion = 0L;
    
    private XSSFWorkbook wb    = null;

    protected HashMap<String, String> xlsConfig   = new HashMap<String, String>();
    
	String prm[][] = { 
		    {" ", "l" , "lang"     , "SDP_LANG"        , CFG.SDP_LANG       , "1" ,  "30" , Args.STRING}			          
		   ,{" ", "t" , "template" , "SDP_TEMPLATE"    , CFG.EXCEL_TEMPLATE , "1" , "220" , Args.STRING}			
	};

	private SDPExcel() {
	    Propiedades props = new Propiedades();
	    for (Map.Entry<String, String> entry : props.loadResource("excel").entrySet()) {
	        xlsConfig.put(entry.getKey(), entry.getValue());
	    }
	}

	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   SDPExcel launcher = new SDPExcel();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		int     maxRC   = RC.OK;
		
		cfg.setTitles(MSG.TITLE_SDP_TREE);
		
		args = cfg.processCommandLine(prm, args);
		
		
		for (int iParm = 0; iParm < args.length; iParm++) {
			for (SDPModulo m : moduloService.getModulesByPseudoMask(args[iParm])) {
				try {
				   modulo = m;
				   if (cfg.getVerbose() > 0) msg.progressCont(MSG.PROCESSING, modulo.getNombre());
                   analyze();
                   if (cfg.getVerbose() > 0)    msg.progress(MSG.OK);
				}
				catch (Exception e) {
					if (cfg.getVerbose() > 0)    msg.progress(MSG.KO);
				}
			}
		}
        return maxRC;
	}

	private void analyze() {
		XLSLoader  loader = new XLSLoader();
	    XLSMapper  mapper = new XLSMapper();
		wb = loader.getWorkBook();
		
		version = versionService.getByVersion(modulo.getIdVersion());
		idVersion = version.getIdVersion();
		
		mapper.map(modulo.getNombre(), loader.getMap(), idVersion);
        writeExcel();
	}
				
	private void writeExcel() {
		String dir = cfg.getDocDir();
		String fileName = dir + modulo.getNombre() + ".xlsm";
		try {
            FileOutputStream out = 
            new FileOutputStream(new File(fileName));
            wb.write(out);
            out.close();
       } catch (FileNotFoundException e) {
           throw new SDPException(MSG.EXCEPTION_LOCK, fileName);
       } catch (IOException e) {
          e.printStackTrace();
       }
	}

}

