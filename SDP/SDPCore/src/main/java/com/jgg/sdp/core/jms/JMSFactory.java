/**
 * Factoria de instanciacion del sistema de mensajeria 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.jms;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.jms.wmq.WMQQueue;
import com.jgg.sdp.core.jms.wmqjms.WMQJMSQueue;

public class JMSFactory {

	private final static String WMQ    = "WMQ";
	private final static String WMQJMS = "WMQJMS";
	
	private static Configuration cfg = Configuration.getInstance();
	
	/**
	 * Devuelve una instancia al sistema de mensajeria
	 * @return La instancia del sistema de mensajeria
	 */
	public static JMSQueue getJMSQueue() {
		String jmsType = cfg.getJMSProvider();
	
		if (jmsType == null) throw new SDPException(MSG.NO_JMS_PROVIDER);
			
		if (jmsType.compareToIgnoreCase(WMQ) == 0) {
			return (JMSQueue) new WMQQueue();
		} else if (jmsType.compareToIgnoreCase(WMQJMS) == 0) {
			return (JMSQueue) new WMQJMSQueue();
		}

		throw new SDPException(MSG.BAD_JMS_PROVIDER, jmsType);
	}

}
