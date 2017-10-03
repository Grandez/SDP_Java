package com.jgg.sdp.test.jms;

import com.jgg.sdp.client.ClientJMS;
import com.jgg.sdp.core.exceptions.ClientException;

public class TestJMSReceive {

	public static void main(String[] args) {
/*
	    TestJMSReceive client = new TestJMSReceive();
	    client.process();
*/	    
    }

   private void process() {
	    ClientJMS client = new ClientJMS();
	    
	    try {
  	    client.openConnection("localhost", 5672);
	    client.openEndPointInput("SDPLogging");
	    String msg = client.receive();
	    while (msg != null) {
	    	System.out.println(msg);
	    	msg = client.receive();
	    }
	    client.close();
	    }
	    catch (ClientException e) {
	    	e.printStackTrace();
	    }
}

}
