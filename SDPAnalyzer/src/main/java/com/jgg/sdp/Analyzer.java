package com.jgg.sdp;

import com.jgg.sdp.collector.process.CollectorParser;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.factorias.ModulesFactory;
import com.jgg.sdp.module.unit.SDPUnit;
import com.jgg.sdp.parser.*;
import com.jgg.sdp.parser.base.ParserInfo;

public class Analyzer {

    private Module module = null;

    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();
    
//    private SDPLiteDB db = new SDPLiteDB();
    
  	/* Tabla de parametros aceptados
	 * 0 - valor simbolico
	 * 1 - parametro corto
	 * 2 - parametro largo
	 * 3 - variable de entorno
	 * 4 - clave de configuracion
	 * 5 - Requiere valor (0 - no)
	 * 6 - tipo de dato
	 */

	String prm[][] = { 
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT    , "1" , "204" , Args.DIR}			          
		   ,{" ", "l" , "left"   , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT  , "1" , "206" , Args.NUMBER}	   
		   ,{" ", "r" , "right"  , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT , "1" , "207" , Args.NUMBER}
	       ,{"1", "" , "force"   , ""                 , CFG.PARSER_FORCE , "0" , "214;215" , Args.BOOLEAN}	       
	};

	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Analyzer launcher = new Analyzer();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		SDPUnit unit = null;
		int     maxRC   = RC.OK;
		int     procesar = MSG.OK;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_ANALYZER, MSG.USE_SDP_ANALYZER);
		
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) args = def;

        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
        	if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, archivo.getBaseName());
            
			try {
	            unit = new SDPUnit(archivo);
	            module = unit.getCurrentModule();
	            module.setType(CDG.SOURCE_CODE);
				
//            	procesar = db.hasToProcess(unit.getCurrentSource());
            	
//Nada            	if (procesar == MSG.OK)      procesar = unit.isIgnored();
//Nada              if (procesar == MSG.IGNORED) createIgnoredModule(unit);
  				if (procesar == MSG.OK)      analyze(unit);
 				if (procesar == MSG.OK)      procesar = storeModuleInfo(unit, true);
				if (cfg.getVerbose() > 1)    msg.progress(procesar);
			// Caso, alguien ha borrado el fichero entre el find y el proceso
			} catch (FileException f) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_ERROR);
			    msg.exception(f);
                if (maxRC < RC.ERROR) maxRC = RC.ERROR;
			} catch (NotSupportedException s) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_NOT_SUPPORTED);
			    msg.exception(s);
			    if (maxRC < RC.NOT_SUP) maxRC = RC.NOT_SUP;
			} catch (SDPException s) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_SDP_ERROR);
			    msg.exception(s);
			    if (maxRC < RC.WARNING) maxRC = RC.WARNING;
			} catch (Exception e) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_ERROR);
                msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
                e.printStackTrace();
                if (maxRC < RC.ERROR) maxRC = RC.ERROR;
			}
			finally {
//				storeCompileUnit(unit);
				if (unit.getStatus() > CDG.STATUS_BAD) {
					module.setStatus(unit.getStatus());
//					storeModuleInfo(unit, false);
				}
				ModulesFactory.cleanModules();
			}
        }
        
        return maxRC;
	}

	private void createIgnoredModule(SDPUnit unit) {
		module.setStatus(CDG.STATUS_IGNORED);
		unit.setStatus(CDG.STATUS_IGNORED);
	}
	
	private void analyze(SDPUnit unit) throws SDPException, Exception {
		ParserInfo info = ParserInfo.getInstance();
		info.setUnit(unit);
		info.setModule(module);
		
		try {
		   Parser parser = new Parser(unit);
		   parser.process();
		   if (module.getStatus() == CDG.STATUS_UNDEF) {
			   module.setStatus(CDG.STATUS_FULL);
		   }
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private int storeModuleInfo (SDPUnit unit, boolean full) {
		Serializer ser = new Serializer();
	    CollectorParser collector = new CollectorParser();
	    for (Module module : unit.getModules()) {
	         String data = ser.generateXMLModule(module, unit.getId(), full);
             collector.processXMLData(data);
	    }
	    return MSG.OK;
	}

	private void storeCompileUnit (SDPUnit unit) {
		Serializer ser = new Serializer();
	    CollectorParser collector = new CollectorParser();
	    String data = ser.generateXMLFile(unit);
	    collector.processXMLData(data);
	    
	}


}
