package com.jgg.sdp.test.jms;

import javax.jms.JMSException;

import com.jgg.sdp.jms.JMSClient;

public class TestJMSSend {

	public static void main(String[] args) {

		    TestJMSSend client = new TestJMSSend();
		    client.process();
	}
	
	private void process() {
		    JMSClient client = new JMSClient();
		    
		    try {
		    client.openConnection("localhost", 5672);
		    client.openOutputQueue("SDPLogging");
		    for (int idx = 0; idx < 5; idx++) {
		    	client.put("Message " + idx);
		    }
		    client.close();
		    }
		    catch (JMSException e) {
		    	e.printStackTrace();
		    }
	}

}
