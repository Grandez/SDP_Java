package com.jgg.sdp.logger.jms;

import javax.jms.*;
import org.apache.qpid.jms.*;

public class TestBroker {

	public boolean isBrokerActive() {
	   try {
			String connectionURI = "amqp://localhost:5672";
	        JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);
		   
	       Connection conn = factory.createConnection("SDP", "sdp");
	       conn.start();
	       Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
           
           session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		   Destination destination = session.createQueue("SDP.TEST");
	        // Create a MessageProducer from the Session to the Topic or Queue
		   MessageProducer producer = session.createProducer(destination);
	       MessageConsumer consumer = session.createConsumer(destination);

           TextMessage message = session.createTextMessage("test");
           producer.send(message);
           Message msg = consumer.receive();
           System.out.println(((TextMessage) msg).getText());
           session.close();
           conn.close();
           return true;
	   } catch (JMSException e) {
//		   System.out.println(e.getMessage());
	  }
	   return false;
	   
	}	   
}
