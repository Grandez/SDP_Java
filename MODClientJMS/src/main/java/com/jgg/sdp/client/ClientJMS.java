package com.jgg.sdp.client;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.jms.*;
import org.apache.qpid.jms.*;

import com.jgg.sdp.client.ICommClient;
import com.jgg.sdp.core.exceptions.ClientException;

public class ClientJMS implements ICommClient {

	private Connection      connection;
	private Session         session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	public void openConnection(String host, int port) throws ClientException {
        String user = "SDP";
        String password = "sdp";

		String connectionURI = "amqp://" + host + ":" + port;
        JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

        try {
           connection = factory.createConnection(user, password);
           connection.start();
           session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			throw new ClientException(e);
		}

	}
	
	public void closeConnection() throws ClientException {
        try {
			session.close();
			connection.close();		
		} catch (JMSException e) {
			throw new ClientException(e);
		}
        
	}

	public void openEndPointOutput(String name) throws ClientException {
        Destination destination;
		try {
			destination = session.createQueue(name);
	        // Create a MessageProducer from the Session to the Topic or Queue
	        producer = session.createProducer(destination);
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} catch (JMSException e) {
			throw new ClientException(e);
		}
	}

	public void openEndPointInput(String name) throws ClientException {
		
        Destination destination;
		try {
			destination = session.createQueue(name);
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			throw new ClientException(e);
		}	    
	}
	
	public void closeEndPoint() throws ClientException {

			try {
				if (consumer != null) consumer.close();
			} catch (JMSException e) {
				throw new ClientException(e);
			}	    
	}
	
	public int sendText(String text) throws ClientException {
		try {
           TextMessage message = session.createTextMessage(text);
           producer.send(message);
		} catch (JMSException e) {
			throw new ClientException(e);
		}	    
        return text.length();
	}

	public String receive() throws ClientException {
		// Wait for a message
		try {
           Message message = consumer.receive(1000);

           if (message instanceof TextMessage) {
               TextMessage textMessage = (TextMessage) message;
               String text = textMessage.getText();
               System.out.println("Received: " + text);
               return text;
           } else {
              System.out.println("Received: " + message);
           }
		} catch (JMSException e) {
			throw new ClientException(e);
		}	    

        return null;
	}
	
	/**
	 * Wrapper to close all
	 * @throws JMSException 
	 */
	public void close() throws ClientException {
           closeEndPoint();
           closeConnection();		
	}

	public int sendData(byte[] data) throws ClientException {
		return 0;
	}

	public int sendUnit(Object object) throws ClientException {
		return 0;
	}
}