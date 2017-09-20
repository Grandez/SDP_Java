package com.jgg.sdp;

import com.jgg.sdp.analyzer.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.core.unit.Source;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.ModulesFactory;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.core.SDPSource;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.SDPFileService;
import com.jgg.sdp.domain.services.core.SDPSourceService;
import com.jgg.sdp.parser.base.*;

public class Analyzer {

    private Module  module = null;
	private SDPUnit unit   = null;

	private SDPFileService   fileService    = new SDPFileService();
	private SDPSourceService sourceService = new SDPSourceService();
	
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = DBConfiguration.getInstance();

    // Lo usamos para actualizar el estado
	private SDPFile       file = null;

    
	private int     rc      = RC.OK;
	private int     maxRC   = RC.OK;

    public Analyzer() {
          initObject();    	
    }
    
    public Module getModule() { return module; }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   Analyzer launcher = new Analyzer();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		args = cfg.processCommandLine(AnalyzerParms.parms, args);
		
		if (cfg.isLocalMode()) {
			processLocalMode(args);
		} else if (args.length == 0) {
			processMassive();
		} else {
			processModules(args);
		}
		
       return rc;		
	}

	private void processLocalMode(String[] args) {
		if (args.length == 0) args = new String[]{"*"};
		
		for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {
	        unit = new SDPUnit(archivo);
			analyzeUnit(unit);
		}
	}

	private void processMassive() {
		
		fileService.cursorPendingOpen();
		while ((file = fileService.cursorPendingNext()) != null) {
			Archivo archivo = new Archivo(file.getFullName());
			unit = new SDPUnit(archivo);
			unit.setFirma(file.getFirma());
			unit.setTipo(file.getTipo());
			unit.setStatus(file.getEstado());
			setSource(unit, file);
			analyzeUnit(unit);			
		}
	}

	// Por nombre idFile idVersion???
	
	private void processModules(String[] args) {
	}
	
	private void analyzeUnit(SDPUnit unit) {
		int procesar = RC.OK;
		
		if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, unit.getMemberName());
        
		try {		

			
        	procesar = hasToProcess(unit);
        	
//Nada            	if (procesar == MSG.OK)      procesar = unit.isIgnored();
//Nada              if (procesar == MSG.IGNORED) createIgnoredModule(unit);
				if (procesar == RC.OK)      analyze(unit);
				if (procesar == RC.OK)      procesar = storeModuleInfo(unit);
			if (cfg.getVerbose() > 1)    msg.progress(procesar);
		// Caso, alguien ha borrado el fichero entre el find y el proceso
		} catch (FileException f) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
			unit.setStatus(CDG.STATUS_ERROR);
		    msg.exception(f);
            rc = RC.ERROR;
		} catch (NotSupportedException s) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
			unit.setStatus(CDG.STATUS_NOT_SUPPORTED);
		    msg.exception(s);
		    rc = RC.NOT_SUP;
        } catch (ParseException s) {
            if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
            msg.exception(s);
            unit.setStatus(CDG.STATUS_SDP_ERROR);
            rc = RC.SEVERE;
		} catch (SDPException s) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
			unit.setStatus(CDG.STATUS_SDP_ERROR);
		    msg.exception(s);
		    s.printStackTrace();
		    rc = RC.ERROR;
		} catch (Exception e) {
			if (cfg.getVerbose() > 1) msg.progress(MSG.KO);
			unit.setStatus(CDG.STATUS_ERROR);
            msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
            e.printStackTrace();
            rc = RC.FATAL;
		}
		finally {
			if (unit.getStatus() > CDG.STATUS_BAD) {
				module.setParserStatus(unit.getStatus());
				storeModuleInfo(unit);
			}
			ModulesFactory.cleanModules();
			
		}
		
		if (rc > maxRC) maxRC = rc;
    }
    
	private void createIgnoredModule(SDPUnit unit) {
		module.setParserStatus(CDG.STATUS_IGNORED);
		unit.setStatus(CDG.STATUS_IGNORED);
	}
	
	public int analyze(SDPUnit unit) throws FileException, 
	                                             NotSupportedException,
	                                             ParseException,
	                                             SDPException, 
	                                             Exception {
		
		ParserInfo info = ParserInfo.getInstance(true);
        
		module = unit.getCurrentModule();
        module.setType(CDG.SOURCE_CODE);

		info.setUnit(unit);
		info.setModule(module);
		
		Parser parser = new Parser(unit);
		parser.process();
		
	   if (module.getParserStatus() == CDG.STATUS_UNDEF) {
		   module.setParserStatus(CDG.STATUS_FULL);
	   }
	   return RC.OK;
	}

    
	private int storeModuleInfo (SDPUnit unit) {
		long idUnit = unit.existUnit() ? unit.getId() : 0l;
		Persister pers = new Persister();
		pers.beginTrans();
	    for (Module module : unit.getModules()) {
	         pers.persistModule(module, idUnit);
	    }
	    
	    if (cfg.isLocalMode() && !unit.existUnit()) pers.persistUnit(unit);
	    if (!cfg.isLocalMode()) {
	    	file.setEstado(unit.getStatus());
	    	pers.persistStatus(file);
	    }
	    
	    pers.commit();
	    
	    return MSG.OK;
	}

	/**
	 * Mira si el modulo existe y ha sido procesado
	 * @param source
	 * @return
	 */
	private int hasToProcess(SDPUnit unit) {
		
		SDPFile f = fileService.findByNameAndType(unit.getMemberName(), CDG.SOURCE_CODE);
		if (f == null) return RC.OK;
		
		if (f.getEstado() == CDG.STATUS_PENDING) return RC.OK;
		
		// if (f.getFirma().compareTo(unit.getFirma()) != 0) return RC.OK;
		
		unit.setExist();
		
		if (!cfg.getBoolean(CFG.PARSER_FORCE)) return RC.SKIP;
		
		return RC.OK;
	}

	private void setSource(SDPUnit unit, SDPFile file) {
		Source src = unit.getCurrentSource();
		src.setFirma(file.getFirma());
		src.setId(file.getIdFile());
		SDPSource source = sourceService.getSource(file.getIdFile(), file.getIdVersion());
		src.setRawData(source.getSource());
	}
	
	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_ANALYZER);		
	}

}
