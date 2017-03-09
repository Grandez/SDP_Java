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

    public static final String INST_NAME    = "inst.name";
    
    public static final String CFG_DEFAULT  = "SDPDefault.properties";
    public static final String CONFIG       = "sdp.config";
    
    public static final String HELP         = "help";
	public static final String VERBOSE      = "verbose";
	public static final String DIR_INPUT    = "parser.dir.input";
	public static final String DIR_OUTPUT   = "parser.dir.output";
	public static final String TEMP_NAME    = "parser.temp.name";
	public static final String PARSER_ERR   = "parser.error";
	
	public static final String MARGIN_LEFT  = "parser.margin.left";
	public static final String MARGIN_RIGHT = "parser.margin.right";
	
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
	
	public static final String COLLECTOR    = "collector.type";
	
	public static final String DIALECT      = "cobol.dialect";

	public static final String TRAP_LOOP    = "trap.varying";
 
	public static final String DATE_INTERVAL   = "date.interval";
	
    public static final String CODE_VERSIONS  = "code.max.versions";
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
	
	private CFG() {
		
	}

}
