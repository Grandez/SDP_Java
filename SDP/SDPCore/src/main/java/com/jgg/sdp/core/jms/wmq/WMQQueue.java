/*
 * 
 */
package com.jgg.sdp.core.jms.wmq;

import java.io.IOException;

import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.*;
import com.jgg.sdp.core.jms.JMSQueue;

public class WMQQueue extends WMQ implements JMSQueue {

	private MQQueue queue;
	
	public void openInput(String qName) {
		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF ;
		queue = MQOpen(qName, openOptions);
	}
	
	public void openOutput(String qName) {
		int openOptions = MQConstants.MQOO_OUTPUT;
		queue = MQOpen(qName, openOptions);
	}

	public void close() {
		MQClose(queue);
	}
	
	public String get() {
        String msgText = null;
        
		MQMessage rcvMessage = new MQMessage();

        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options |= MQConstants.MQGMO_WAIT;
        gmo.waitInterval = wait * 1000;


	    try {
	       queue.get(rcvMessage, gmo);
	       if (rcvMessage.characterSet == 1208 && queue.getName().contains("TRAPPER") == false) { //UTF-8
	    	   msgText = rcvMessage.readUTF();
	       }
	       else {
              int len = rcvMessage.getMessageLength();
              msgText = rcvMessage.readStringOfCharLength(len);
	       } 
	    }
	    catch (MQException ex) {
	       	if (ex.reasonCode == MQConstants.MQRC_NO_MSG_AVAILABLE) {
	       	    return null;
	       	}
	    	throw new JMSException(MSG.JMS_GET, ex.reasonCode, queue); 
        }
        catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            System.out.println("ERROR: " + ex.getCause());
	    	throw new JMSException(MSG.JMS_GET, 9999, queue); 
	    }
	    return msgText; 
	}

	public void put(String txt) {
        
		MQMessage qmsg = new MQMessage();

		MQPutMessageOptions pmo = new MQPutMessageOptions();
		pmo.options = MQConstants.MQPMO_NEW_CORREL_ID
				    | MQConstants.MQPMO_NEW_MSG_ID
				    | MQConstants.MQPMO_NO_SYNCPOINT
				    ;
		
	    try {
            qmsg.setStringProperty(MQConstants.MQMD_PROPERTY_FORMAT, 
		                          MQConstants.MQFMT_STRING);
            qmsg.writeUTF(txt);
	    	queue.put(qmsg, pmo);
	    }
	    catch (MQException ex) {
	    	throw new JMSException(MSG.JMS_PUT, ex.reasonCode, queue);
       } catch (IOException ex) {
	    	throw new JMSException(MSG.JMS_PUT, 1000, queue);
		}
	}
	
}
