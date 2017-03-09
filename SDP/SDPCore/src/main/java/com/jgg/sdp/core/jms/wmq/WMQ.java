/*
 * 
 */
package com.jgg.sdp.core.jms.wmq;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.JMSException;
import com.jgg.sdp.core.jms.JMSBase;

public abstract class WMQ extends JMSBase {

    protected static MQQueueManager qMgr = null;
    private   static int connCount = 0;
    
	protected void MQConnect() {
		setEnvironment();
//		MQEnvironment.hostname = "localhost";
//		MQEnvironment.channel = "CHANNEL.SDP";
//		MQEnvironment.properties.put(MQConstants.TRANSPORT_PROPERTY, MQConstants.TRANSPORT_MQSERIES_CLIENT);
		
	    try {
	        qMgr = new MQQueueManager(qmgr);
	    } catch (MQException ex) {
	        System.out.println("MQ: " + ex.toString());
	        System.out.println("MQ: " + ex.getLocalizedMessage());
	        System.out.println("MQ: " + ex.getMessage());
	        System.out.println("MQ: " + ex.getCause().toString());
	    	throw new JMSException(MSG.JMS_CONNECT, ex.reasonCode);
	    }
		
	}

	protected MQQueue MQOpen(String qname, int openOptions) {
		MQQueue queue;
		if (qmgr == null) MQConnect();
		try {
	        queue = qMgr.accessQueue(qname, openOptions);
	        connCount++;
	    } catch (MQException ex) {
	    	throw new JMSException(MSG.JMS_OPEN, ex.reasonCode, qname);
	    }
	    return queue;
	}

	protected void MQClose(MQQueue queue) {
		try {
			queue.close();
	        connCount--;
	        if (connCount == 0) disconnect();
	    } catch (MQException ex) {
	        // Se ignoran posibles errores al cerrar las colas
	    }
	}

	protected void disconnect() {
		try {
	        qMgr.disconnect();
	        qMgr = null;
		} catch (MQException ex) {
	          // Se ignoran posibles errores al desconectar
		}	
	}

    
}
