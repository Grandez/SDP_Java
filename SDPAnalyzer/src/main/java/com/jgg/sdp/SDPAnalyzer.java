package com.jgg.sdp;

import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.files.*;

import com.jgg.sdp.analyzer.*;
import com.jgg.sdp.common.config.Messages;
import com.jgg.sdp.common.exceptions.*;

import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.unit.*;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.parser.base.*;
import com.jgg.sdp.rules.components.RulesProcessor;

import static com.jgg.sdp.common.ctes.CFG.*;

public class SDPAnalyzer {

    private Module  module = null;
	private Unit unit   = null;

	private SDPFileService   fileService    = new SDPFileService();
	private SDPSourceService sourceService = new SDPSourceService();
	
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = DBConfiguration.getInstance();

	private SDPFile       file = null;
    
	private int     rc      = RC.OK;
	private int     maxRC   = RC.OK;

    public SDPAnalyzer() {
          initObject();    	
    }
    
    public Module getModule() { return module; }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   SDPAnalyzer launcher = new SDPAnalyzer();
	   rc = launcher.process(args);
       System.exit(rc);
	}

	public int process(String[] args) {
		
		args = initEnvironment(args);
		
		switch (cfg.getParserMode()) {
		   case PARSER_LOCAL:   processLocalMode(args); break;
		   case PARSER_MASSIVE: processMassive(args);   break;
		   case PARSER_CMD:     processModules(args);   break;
		}
       return rc;		
	}

	private void processLocalMode(String[] args) {
		if (args.length == 0) args = new String[]{"*"};
		
		for (Archive archivo : FileFinder.find(cfg.getInputDir(), args)) {
	        unit = new Unit(archivo);
			analyzeUnit(unit);
		}
	}

	/**
	 * Procesa todos los fuentes de la base de datos con estado
	 * UNPARSED
	 */
	private void processMassive(String[] args) {
		
		fileService.cursorPendingOpen();
		while ((file = fileService.cursorPendingNext()) != null) {
			Unit unit = new Unit();
			setMainSource(unit, file);
			analyzeUnit(unit);			
		}
	}

	// Por nombre idFile idVersion???
	
	private void processModules(String[] args) {
	}
	
	private void analyzeUnit(Unit unit) {
		String txt = "";
		int rc  = RC.OK;
		int res = MSG.OK;
		
		if (cfg.getVerbose() > 0) msg.progressCont(MSG.PARSING, unit.getMemberName());
        
		try {		
        	res = hasToProcess(unit);
			if (res == MSG.OK)      analyze(unit);
		// Caso, alguien ha borrado el fichero entre el find y el proceso
		} catch (FileException f) {
			unit.setStatus(CDG.STATUS_ERROR);
		    txt = msg.getExceptionMessage(f);
            rc = RC.ERROR;
            res = MSG.ERROR;
		} catch (NotSupportedException s) {
			unit.setStatus(CDG.STATUS_NOT_SUPPORTED);
			txt = msg.getExceptionMessage(s);
		    rc = RC.NOT_SUP;
		    res = MSG.KO;
        } catch (ParseException s) {
        	txt = msg.getExceptionMessage(s);
            unit.setStatus(CDG.STATUS_SDP_ERROR);
            rc = RC.SEVERE;
            res = MSG.KO;
		} catch (SDPException s) {
			unit.setStatus(CDG.STATUS_SDP_ERROR);
			txt = msg.getExceptionMessage(s);
		    s.printStackTrace();
		    rc = RC.ERROR;
		    res = MSG.KO;
		} catch (Exception e) {
			unit.setStatus(CDG.STATUS_ERROR);
			e.printStackTrace();
			txt = msg.getExceptionMessage(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
            e.printStackTrace();
            rc = RC.FATAL;
            res = MSG.KO;
		}
		finally {
			if (res != MSG.SKIP) {
				if (unit.getStatus() > CDG.STATUS_BAD) module.setParserStatus(unit.getStatus());
				if (!cfg.isIVPMode()) storeModuleInfo(unit);
//				ModulesFactory.cleanModules();
			}
			if (cfg.getVerbose() > 0)    msg.progress(res);
			if (txt.length() > 0)        msg.print(txt);
		}
		
		if (rc > maxRC) maxRC = rc;
    }
    
/*	
	private void createIgnoredModule(Unit unit) {
		module.setParserStatus(CDG.STATUS_IGNORED);
		unit.setStatus(CDG.STATUS_IGNORED);
	}
*/
	
	public int analyze(Unit unit) throws FileException, 
	                                             NotSupportedException,
	                                             ParseException,
	                                             SDPException, 
	                                             Exception {
		// Inicializa componentes estaticos
		ParserInfo     info      = ParserInfo.getInstance(true);
        RulesProcessor.getInstance(true);
        
		module = unit.getCurrentModule();
        
		info.setUnit(unit);
		info.setModule(module);
		
		Parser parser = new Parser(unit);
		parser.process();
		
	   if (module.getParserStatus() == CDG.STATUS_UNDEF) {
		   module.setParserStatus(CDG.STATUS_FULL);
	   }
	   return RC.OK;
	}

    
	private int storeModuleInfo (Unit unit) {
		SDPFile file = null;
		
		Persister pers = new Persister();
		pers.beginTrans();

	    int mode = cfg.getInteger(PARSER_MODE);
	    
	    if (mode == PARSER_LOCAL && !unit.existUnit()) {
	    	file = pers.persistUnit(unit);
	    }
	    else {
	    	// Recargar el fichero dentro de la transaccion
	    	file = fileService.findById(unit.getMainSource().getIdFile());
	    }
		
		// TODO Por ahora solo el principal
		pers.persistModule(unit.getModules().get(0));
//	    for (Module module : unit.getModules()) {
//	         pers.persistModule(module, unit);
//	    }
	    
    	file.setEstado(unit.getStatus());
	    pers.persistStatus(file);
	    
	    pers.commit();
	    
	    return MSG.OK;
	}

	/**
	 * Mira si el modulo existe y ha sido procesado
	 * @param source
	 * @return
	 */
	private int hasToProcess(Unit unit) {
		SDPFile f = null;
		
		// Es local mode?
		if (file == null) {
		   f = fileService.findByNameAndType(unit.getMainSource().getBaseName(), CDG.SOURCE_CODE);
		   if (f == null) return MSG.OK;
		}
		else {
		   if (file.getEstado() == CDG.STATUS_UNPARSED) return MSG.OK;
		}
		
		// Es el mismo?
		Source src = unit.getMainSource();
		if (f.getFirma().compareTo(src.getFirma()) != 0) return RC.OK;
		
		//Marcar como existente
		unit.setExist();
		src.setIdFile(f.getIdFile());
		src.setIdFileVersion(f.getIdVersion());
		
		// Forzar a reprocesar
		if (!cfg.getBoolean(CFG.PARSER_FORCE)) return MSG.SKIP;
		
		return MSG.OK;
	}

	private void setMainSource(Unit unit, SDPFile file) {
		SDPSource source = sourceService.getSource(file.getIdFile(), file.getIdVersion());
		Source src = new Source(file.getFullName());
		src.setFirma(file.getFirma());
		src.setIdFile(file.getIdFile());
		src.setIdFileVersion(file.getIdVersion());
		src.setRawData(file.getFullName(), source.getEncoded(), source.getSource());
		src.setTipo(file.getTipo());
		
		unit.addSource(src);
	}
	
	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_ANALYZER);		
	}

	private String[] initEnvironment(String[] args) {
		// Necesario para IVP
		RulesProcessor.initInstance();

		args = cfg.processCommandLine(AnalyzerParms.parms, args);
		
		// No es local
		if (cfg.getParserMode() == null) {
			cfg.setParameter(CFG.PARSER_MODE, (args.length == 0) ? PARSER_MASSIVE 
					                                             : PARSER_CMD);
		}

		return args;
	}
}
