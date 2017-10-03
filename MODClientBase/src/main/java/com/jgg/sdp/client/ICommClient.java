package com.jgg.sdp.client;

import com.jgg.sdp.core.exceptions.ClientException;

public interface ICommClient {
//	public int sendZipFile(String name, int type, byte[] raw);
	public void openConnection(String host, int port)         throws ClientException;
	public void closeConnection()                             throws ClientException;
	public void openEndPointOutput(String name)               throws ClientException;
	public void openEndPointInput (String name)               throws ClientException;	
	public int  sendText (String text)                        throws ClientException;
	public int  sendData (byte[] data)                        throws ClientException;
	public int  sendFile (String name, int type, byte[] data) throws ClientException;	
}
