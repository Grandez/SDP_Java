/*
 * 
 */
package com.jgg.sdp.core.jms.wmqjms;


import javax.jms.JMSException;
import javax.jms.Session;

import com.ibm.jms.JMSMessage;
import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import com.ibm.msg.client.wmq.WMQConstants;
import com.jgg.sdp.core.jms.JMSBase;

public class WMQJMS extends JMSBase {

    protected MQQueueSession    session = null;
    protected MQQueueConnection connection = null;
    
	protected void MQConnect() {
		setEnvironment();
		
	    try {
           MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
           cf.setHostName(jmsHost);
           cf.setPort(jmsPort);
           if (qmgr != null) cf.setQueueManager(qmgr);
           cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);

           cf.setChannel("SYSTEM.DEF.SVRCONN");
        

           connection = (MQQueueConnection) cf.createQueueConnection();
           session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
           connection.start();
	    }
        catch (JMSException jmsex) {
	          System.out.println(jmsex);
	          System.out.println("\\nFAILURE\\n");
	          System.exit(3);
	    }

	}

	/**
     * Funcion.
     */
	public void funcion() {
    try {
        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

        // Config
        cf.setHostName("localhost");
        cf.setPort(1414);
        cf.setTransportType( WMQConstants.WMQ_CM_CLIENT );
        cf.setChannel("SYSTEM.DEF.SVRCONN");

        
        MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
        MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        MQQueue queue = (MQQueue) session.createQueue("queue:///SDP.TEST");
        MQQueueSender sender =  (MQQueueSender) session.createSender(queue);
        MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);      

        long uniqueNumber = System.currentTimeMillis() % 1000;
        JMSTextMessage message = (JMSTextMessage) session.createTextMessage("SimplePTP "+ uniqueNumber);     

        // Start the connection
        connection.start();

        sender.send(message);

        JMSMessage receivedMessage = (JMSMessage) receiver.receive(10000);

        sender.close();
        receiver.close();
        session.close();
        connection.close();

      }
      catch (JMSException jmsex) {
        System.out.println(jmsex);
        System.out.println("\\nFAILURE\\n");
      }
      catch (Exception ex) {
        System.out.println(ex);
        System.out.println("\\nFAILURE\\n");
      }
    }
}
