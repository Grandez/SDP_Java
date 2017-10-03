package com.jgg.sdp.logger;

import com.jgg.sdp.client.ClientJMS;

public class LoggerClient {

	private ClientJMS client = null;
	
	public LoggerClient() {
		client = new ClientJMS();
		client.openConnection("localhost", 5672);
		client.openEndPointOutput("SDP.LOG");
	}
	
	public void endLogger() {
		client.closeEndPoint();
		client.closeConnection();
	}
	
	public int sendLog(String txt) {
		return client.sendText(txt);
		
	}
}
