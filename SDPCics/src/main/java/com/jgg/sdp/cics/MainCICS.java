/**
 * 
 *
 */

package com.jgg.sdp.cics;

import com.jgg.sdp.cics.base.GenericScanner;
import com.jgg.sdp.cics.base.ILexer;
import com.jgg.sdp.cics.db.Persistencia;
import com.jgg.sdp.cics.lang.CICSLexer;
import com.jgg.sdp.cics.lang.CICSParser;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.msg.*;
import com.jgg.sdp.core.tools.*;

import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;
import com.jgg.sdp.module.unit.Source;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.MODVersion;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.MODVersionService;


public class MainCICS 
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
		    {" ", "i" , "input"  , "SDP_INPUT"        , CFG.DIR_INPUT    , "1" , "204" , Args.DIR}			          
		   ,{" ", "l" , "left"   , "SDP_MARGIN_LEFT"  , CFG.MARGIN_LEFT  , "1" , "206" , Args.NUMBER}	   
		   ,{" ", "r" , "right"  , "SDP_MARGIN_RIGHT" , CFG.MARGIN_RIGHT , "1" , "207" , Args.NUMBER}
	       ,{"1", "" , "force"   , ""                 , CFG.PARSER_FORCE , "0" , "214;215" , Args.BOOLEAN}	       
	};

    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();

    SDPModuloService  moduloService  = new SDPModuloService();
    MODVersionService versionService = new MODVersionService();
    SDPFilesService   fileService    = new SDPFilesService();
    
    SDPModulo         modulo = null;
    MODVersion        version = null;

    private int countErrors = 0;

    public static void main( String[] args ) {
       int rc = RC.OK;
	   MainCICS launcher = new MainCICS();
	   rc = launcher.start(args);
       System.exit(rc);

    }
    
    private int start(String[] args) {
        Module module = null;
		int    maxRC   = RC.OK;
		
		String[] def = {"*"};
		
		cfg.setTitles(MSG.TITLE_SDP_CICS);
		
		args = cfg.processCommandLine(prm, args);
		
		if (args.length == 0) args = def;

        for (Archivo archivo : FileFinder.find(cfg.getInputDir(), args)) {

            if (cfg.getVerbose() > 1) msg.progressCont(MSG.PARSING, archivo.getBaseName());
            
            SourcesFactory.cleanSources();
        	Source source = SourcesFactory.getSourceCode(archivo);
        	module = ModulesFactory.getModule(source);

			try {
				int procesar = (cfg.getBoolean(CFG.PARSER_FORCE)) ? MSG.OK : hasToProcess(source);
 				if (procesar == MSG.OK) {
 				   analyze(module);
	               procesar = storeInfo(module);
				}
				if (cfg.getVerbose() > 0) System.out.println(msg.getMsg(procesar));

			} catch (SDPException s) {
				countErrors++;
				if (cfg.getVerbose() > 0) System.out.println(msg.getMsg(MSG.KO));
			    msg.exception(s);
			    if (maxRC < RC.WARNING) maxRC = RC.WARNING;
			} catch (Exception e) {
				if (cfg.getVerbose() > 0) System.out.println(msg.getMsg(MSG.KO));
                msg.exception(new SDPException(e, MSG.EXCEPTION_PARSER, module.getName()));
				e.printStackTrace();
                countErrors++;
                if (maxRC < RC.ERROR) maxRC = RC.ERROR;
                			    System.exit(16);
			}
        }
       if (countErrors > 0) {
            msg.warning(MSG.PARSE_ERRORS, countErrors);
        }

        return maxRC;
    }

   	private void analyze(Module module) throws SDPException, Exception {
/*
   		Source source = module.getSource();
       	ILexer lexer = new CICSLexer(source);	
       	lexer.setFullName(source.getFullName());
       	CICSParser parser = new CICSParser((GenericScanner) lexer);
       	parser.parse();
*/       	
	}

	private int storeInfo (Module module) {
		Persistencia pers = new Persistencia();
		boolean cics = pers.process(module);
 	    return (cics) ? MSG.OK : MSG.NODATA;
	}
   	
	private int hasToProcess(Source source) {
		String name = source.getBaseName();

		SDPFile f = fileService.findByNameAndType(name, CDG.SOURCE_CODE);
		if (f == null) return MSG.OK;
		if (f.getFirma().compareTo(source.getFirma()) == 0) return MSG.SKIP;
		return MSG.OK;
	}

}
