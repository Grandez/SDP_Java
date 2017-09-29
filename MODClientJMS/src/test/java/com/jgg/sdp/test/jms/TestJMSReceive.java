package com.jgg.sdp.test.jms;

import javax.jms.JMSException;

import com.jgg.sdp.jms.JMSClient;

public class TestJMSReceive {

	public static void main(String[] args) {

	    TestJMSReceive client = new TestJMSReceive();
	    client.process();
    }

   private void process() {
	    JMSClient client = new JMSClient();
	    
	    try {
  	    client.openConnection("localhost", 5672);
	    client.openInputQueue("SDPLogging");
	    String msg = client.get();
	    while (msg != null) {
	    	System.out.println(msg);
	    	msg = client.get();
	    }
	    client.close();
	    }
	    catch (JMSException e) {
	    	e.printStackTrace();
	    }
}

}
