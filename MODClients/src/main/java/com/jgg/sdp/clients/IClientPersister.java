package com.jgg.sdp.clients;

public interface IClientPersister {
	public int sendZipFile(String name, int type, byte[] raw);

}
