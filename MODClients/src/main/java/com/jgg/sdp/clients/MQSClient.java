package com.jgg.sdp.clients;

public interface MQSClient {
	public int sendZipFile(String name, int type, byte[] raw);

}
