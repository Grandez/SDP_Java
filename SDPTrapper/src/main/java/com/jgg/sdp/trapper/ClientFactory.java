package com.jgg.sdp.trapper;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.client.*;
import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.ctes.CDG;

public class ClientFactory {

	public static ICommClient getClient() {
		Configuration cfg = ConfigurationLocal.getInstance();
		String client =	cfg.getJMSType();
		if (client.compareToIgnoreCase(CDG.JMS_HTTP)   == 0) return (ICommClient) new ClientHTTP();
//		if (client.compareToIgnoreCase(CDG.JMS_WMQ)    == 0) return (ICommClient) new ClientWMQS();
		if (client.compareToIgnoreCase(CDG.JMS_WMQJMS) == 0) return (ICommClient) new ClientJMS();
		return null;
	}
	 
}
