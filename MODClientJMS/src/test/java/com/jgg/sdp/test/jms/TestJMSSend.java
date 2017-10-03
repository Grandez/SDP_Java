package com.jgg.sdp.test.jms;

import com.jgg.sdp.client.ClientJMS;
import com.jgg.sdp.core.exceptions.ClientException;

public class TestJMSSend {

	public static void main(String[] args) {

//		    TestJMSSend client = new TestJMSSend();
		    //client.process();
	}
	
	private void process() {
		    ClientJMS client = new ClientJMS();
		    
		    try {
		    client.openConnection("localhost", 5672);
		    client.openEndPointOutput("SDPLogging");
		    for (int idx = 0; idx < 5; idx++) {
		    	client.sendText("Message " + idx);
		    }
		    client.close();
		    }
		    catch (ClientException e) {
		    	e.printStackTrace();
		    }
	}

}
