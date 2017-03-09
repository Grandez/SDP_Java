/*
 * 
 */
package com.jgg.sdp.core.jms.wmq;

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

public class MQJMS {
	
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
//        cf.setQueueManager("QM_thinkpad");
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
        System.out.println("Sent message:\\n" + message);

        JMSMessage receivedMessage = (JMSMessage) receiver.receive(10000);
        System.out.println("\\nReceived message:\\n" + receivedMessage);

        sender.close();
        receiver.close();
        session.close();
        connection.close();

        System.out.println("\\nSUCCESS\\n");
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
