package com.jgg.sdp.trapper;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.CDG;

import com.jgg.sdp.client.*;
import com.jgg.sdp.clients.MQSClient;

public class ClientFactory {

	public static MQSClient getClient() {
		Configuration cfg = ConfigurationLocal.getInstance();
		String client =	cfg.getJMSType();
		if (client.compareToIgnoreCase(CDG.JMS_HTTP)   == 0) return (MQSClient) new ClientHTML();
		if (client.compareToIgnoreCase(CDG.JMS_WMQ)    == 0) return (MQSClient) new ClientHTML();
		if (client.compareToIgnoreCase(CDG.JMS_WMQJMS) == 0) return (MQSClient) new ClientHTML();
		return null;
	}
	 
}
