/*
 * 
 */
package com.jgg.sdp.core.jms.wmqjms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.*;
import com.jgg.sdp.core.jms.JMSQueue;

public class WMQJMSQueue extends WMQJMS implements JMSQueue {
	private MQQueue queue;
	private MQQueueSender   qOutput;
	private MQQueueReceiver qInput;

	public void openInput(String qName) {
		if (connection == null) MQConnect();

		try {
			queue = (MQQueue) session.createQueue("queue:///" + qName);
			qInput = (MQQueueReceiver) session.createReceiver((Queue) queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
    
	public void openOutput(String qName) {
		if (connection == null) MQConnect();
		try {
			queue = (MQQueue) session.createQueue("queue:///" + qName);
			qOutput =  (MQQueueSender) session.createSender((Queue) queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	

	public void close() {
        try {
            if (qInput != null)     qInput.close();
            if (qOutput != null)    qOutput.close();
        	if (session != null)    session.close();
            if (connection != null) connection.close();
        } catch (JMSException e) {
		}

	}
	
	public String get() {
        String msgText = null;
        
        TextMessage txt;
		try {
			txt = (TextMessage) qInput.receive(wait * 1000);
	        msgText = txt.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
        return msgText;
	}

	public void put(String txt) {
		try {
           JMSTextMessage message = (JMSTextMessage) session.createTextMessage(txt);     
           qOutput.send(message);
		}
        catch (JMSException jmsex) {
	          System.out.println(jmsex);
	          System.out.println("\\nFAILURE\\n");
	    }
/*
		catch (MQException ex) {
	    	throw new SDPException(msg.getMsg(MSG.JMS_PUT), 
                                   ex.reasonCode, 
                                   new Object[] {queue});
       } catch (IOException ex) {
	    	throw new SDPException(msg.getMsg(MSG.JMS_PUT), 
                    1000, 
                    new Object[] {queue});
		}
*/		
	}

}
