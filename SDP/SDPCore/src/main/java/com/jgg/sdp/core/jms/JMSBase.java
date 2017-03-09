/**
 * Clase abstracta de la que heredan los sistemas de mensajeria 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.jms;

import com.jgg.sdp.core.config.Configuration;

public abstract class JMSBase {

	protected String qmgr    = null;
	protected String qInput  = null;
	protected String qOutput = null;
	protected int    wait    = 1;
	protected String jmsHost = null;
	protected int    jmsPort = 0;
	
	private Configuration cfg = Configuration.getInstance();
	
	protected void setEnvironment() {
		qmgr    = cfg.getJMSManager();
		wait    = cfg.getJMSWaitInterval();
		qInput  = cfg.getJMSQueue();
        qOutput = cfg.getJMSOutputQueue();
        jmsHost = cfg.getJMSHostName();
        jmsPort = cfg.getJMSPort();
	}

}
