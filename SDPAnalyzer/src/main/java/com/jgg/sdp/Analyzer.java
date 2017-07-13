package com.jgg.sdp;

import com.jgg.sdp.analyzer.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.core.tools.FileFinder;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.ModulesFactory;
import com.jgg.sdp.module.unit.*;

import com.jgg.sdp.parser.base.*;

public class Analyzer {

    private Module  module = null;
	private SDPUnit unit   = null;
	
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();
    

    public Module getModule() { return module; }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Analyzer launcher = new Analyzer();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {

		int     maxRC   = RC.OK;
		int     procesar = MSG.OK;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_ANALYZER, MSG.USE_SDP_ANALYZER);
		
		args = cfg.processCommandLine(AnalyzerParms.parms, args);
		
		if (args.length == 0) args = def;

        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
        	if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, archivo.getBaseName());
            
			try {
				
//            	procesar = db.hasToProcess(unit.getCurrentSource());
            	
//Nada            	if (procesar == MSG.OK)      procesar = unit.isIgnored();
//Nada              if (procesar == MSG.IGNORED) createIgnoredModule(unit);
 				if (procesar == MSG.OK)      analyze(archivo);
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
            } catch (ParseException s) {
                if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
                msg.exception(s);
                unit.setStatus(CDG.STATUS_SDP_ERROR);
                if (maxRC < RC.SEVERE) maxRC = RC.SEVERE;
			} catch (SDPException s) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_SDP_ERROR);
			    msg.exception(s);
			    s.printStackTrace();
			    if (maxRC < RC.ERROR) maxRC = RC.ERROR;
			} catch (Exception e) {
				if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
				unit.setStatus(CDG.STATUS_ERROR);
                msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
                e.printStackTrace();
                if (maxRC < RC.FATAL) maxRC = RC.FATAL;
			}
			finally {
//				storeCompileUnit(unit);
				if (unit.getStatus() > CDG.STATUS_BAD) {
					module.setStatus(unit.getStatus());
					storeModuleInfo(unit, false);
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
	
	public int analyze(Archivo archivo) throws FileException, 
	                                             NotSupportedException,
	                                             ParseException,
	                                             SDPException, 
	                                             Exception {
		ParserInfo info = ParserInfo.getInstance(true);
		
        unit = new SDPUnit(archivo);
        module = unit.getCurrentModule();
        module.setType(CDG.SOURCE_CODE);

		info.setUnit(unit);
		info.setModule(module);
		
		Parser parser = new Parser(unit);
		parser.process();
		
	   if (module.getStatus() == CDG.STATUS_UNDEF) {
		   module.setStatus(CDG.STATUS_FULL);
	   }
	   return RC.OK;
	}

    
	private int storeModuleInfo (SDPUnit unit, boolean full) {
		Persister pers = new Persister();
		pers.beginTrans();
	    for (Module module : unit.getModules()) {
	         pers.persistModule(module);
	    }
	    
	    if (full) pers.persistUnit(unit);
	    
	    pers.commit();
	    
	    return MSG.OK;
	}

}
