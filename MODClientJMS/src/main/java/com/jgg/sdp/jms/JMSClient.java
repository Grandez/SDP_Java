package com.jgg.sdp.jms;

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

public class JMSClient {

	private Connection      connection;
	private Session         session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	
	public void openConnection(String host, int port) throws JMSException {
        String user = "SDP";
        String password = "sdp";

		String connectionURI = "amqp://" + host + ":" + port;
        JmsConnectionFactory factory = new JmsConnectionFactory(connectionURI);

        connection = factory.createConnection(user, password);
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	}
	
	public void closeConnection() throws JMSException {
        session.close();
        connection.close();		
	}

	public void openOutputQueue(String name) throws JMSException {
        Destination destination = session.createQueue(name);
        // Create a MessageProducer from the Session to the Topic or Queue
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
	}

	public void openInputQueue(String name) throws JMSException {
        Destination destination = session.createQueue(name);
	    consumer = session.createConsumer(destination);
	}
	
	public void closeQueue() throws JMSException {
		if (consumer != null) consumer.close();
	}
	
	public int put(String text) throws JMSException {
        TextMessage message = session.createTextMessage(text);
        producer.send(message);
        return text.length();
	}

	public String get() throws JMSException {
		// Wait for a message
        Message message = consumer.receive(1000);

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Received: " + text);
            return text;
        } else {
            System.out.println("Received: " + message);
        }
        return null;
	}
	
	/**
	 * Wrapper to close all
	 * @throws JMSException 
	 */
	public void close() throws JMSException {
		closeQueue();
		closeConnection();
	}
}