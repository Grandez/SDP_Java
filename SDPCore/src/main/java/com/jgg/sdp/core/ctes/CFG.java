/**
 * Contiene las constantes simbolicas de las variables de configuracion
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.ctes;

public class CFG {
	
	// Ficheros de propiedades y configuracion

	public static final String DIR_CONFIG     = "sdp.dir.config";
	public static final String DIR_CONFIG_REL = "sdp.dir.config.relative";
	public static final String DIR_DOCS       = "sdp.dir.docs";	
	public static final String FILE_IGNORE    = "sdp.file.ignore";
    public static final String MAX_VERSIONS   = "sdp.max.versions";	
	
	public static final String SDP_LANG       = "sdp.lang";
	public static final String AUTO_APP       = "sdp.auto.application";
	public static final String APP_BASE       = "sdp.application.base";
	
	public static final String OUTPUT_ISOLATED = "sdp.output.isolated";
    
	public static final String SQL_INCLUDE     = "sql.extract.include";
	
	public static final String CFG_DEFAULT  = "SDPDefault.properties";
	public static final String CFG_SDP      = "sdp.properties";
	
	public static final String CFG_CICS_TS  = "cics_ts.properties";
	
    public static final String INST_NAME    = "inst.name";
    
    
    public static final String PROPS        = "sdp.config.file";
    
    public static final String DEF          = "def";
    public static final String HELP         = "help";
	public static final String VERBOSE      = "verbose";
	
	public static final String CURR_MODULE  = "current.module";
	
	// Especiales para IVP
	
	public static final String IVP_TEST     = "ivp.test";
	public static final String IVP_GROUP    = "ivp.group";
	
	public static final String DIR_INPUT    = "parser.dir.input";
	public static final String DIR_OUTPUT   = "parser.dir.output";
	public static final String DIR_COPY     = "parser.dir.copy";
	public static final String COPY_EXT     = "parser.copy.ext";
	public static final String TEMP_NAME    = "parser.temp.name";
	public static final String FILE_INPUT   = "parser.file.input";
	
	public static final String PARSER_ERR   = "parser.error";
    public static final String PARSER_LOCAL = "parser.local";
    public static final String PARSER_SKIP  = "parser.skip.equals";    
    public static final String PARSER_FORCE = "parser.process.equals";
	
	public static final String MARGIN_LEFT  = "parser.margin.left";
	public static final String MARGIN_RIGHT = "parser.margin.right";
	public static final String COBOL_LANG   = "parser.dialect";	

	public static final String TREE_DEEP    = "parser.tree.depth";
	public static final String PARSER_PARSE = "parser.parse";
	
	public static final String FILE_CALL    = "call.file";
	
	public static final String JMS_TYPE      = "jms.type";
	public static final String JMS_GROUP     = "jms.group";
	public static final String JMS_WAIT      = "jms.wait";
	public static final String JMS_INTERVAL  = "jms.commit.interval";
	public static final String JMS_MANAGER   = "jms.queue.manager";
	public static final String JMS_PARSER    = "jms.queue.parser";
	public static final String JMS_TRAPPER   = "jms.queue.trapper";
	public static final String JMS_COLLECTOR = "jms.queue.collector";
	public static final String JMS_QUEUE     = "jms.queue";
	public static final String JMS_OUTPUT    = "jms.output.queue";
	public static final String JMS_HOST      = "jms.hostname";
	public static final String JMS_PORT      = "jms.port";

	
	public static final String PRF_VERIFY    = "profiler.verify";
    public static final String PRF_PROFILE   = "profiler.profile";

    public static final String PRF_SUSPEND   = "profiler.suspend";

    public static final String PRF_MODULE    = "profiler.module";
    public static final String PRF_PARAGRAPH = "profiler.paragraph";
    public static final String PRF_PERFORM   = "profiler.perform";    
    public static final String PRF_CALL      = "profiler.call";
    public static final String PRF_SQL       = "profiler.sql";
    public static final String PRF_CICS      = "profiler.cics";
    public static final String PRF_DEPTH     = "profiler.depth";

	public static final String COLLECTOR    = "collector.type";
	
	public static final String DIALECT      = "cobol.dialect";

	public static final String TRAP_LOOP    = "trap.varying";
 
	public static final String DATE_INTERVAL   = "date.interval";
	
	public static final String CODE_COVERAGE  = "code.min.coverage";
	public static final String CODE_CC_PARR   = "code.max.complexity.parr";
    public static final String CODE_CC        = "code.max.complexity";
    public static final String CODE_STMT      = "code.max.sentences";    
	
	public static final String DESV_ELAPSED = "sesion.desv.elapsed";
	public static final String DESV_INPUT   = "sesion.desv.input";
	public static final String DESV_OUTPUT  = "sesion.desv.output";
    
	public static final String LOG_TMS_SECOND = "S";
	public static final String LOG_TMS_MINUTE = "M";
	public static final String LOG_TMS_HOUR   = "H";
	public static final String LOG_TMS_DAY    = "D";
	
	public static final String LOG_TMS      = "web.log.tms.interval";
	public static final String LOG_RECORDS  = "web.log.max.records";    


	public static final String EXCEL_MASK        = "excel.mask";
	public static final String EXCEL_CONCATENATE = "excel.concatenate";
	public static final String EXCEL_PREFIX      = "excel.prefix";
	public static final String EXCEL_DELIMITER   = "excel.delimiter";
	public static final String EXCEL_EMPTY       = "excel.empty";
	public static final String EXCEL_TEMPLATE    = "excel.template";
	
	////////////////////////////////////////////////////////////////////////
	// Listas de valores
	////////////////////////////////////////////////////////////////////////
	
	public static final String LANG_OPEN_COBOL = "OpenCobol";
	public static final String LANG_IBM_COBOL  = "ZCobol";
	public static final String LANG_MF_COBOL   = "MFCobol";	

	public static final int DEF_MAX_VERSIONS = 1;
	
	private CFG() {
		
	}

}
