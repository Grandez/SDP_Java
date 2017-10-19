package com.jgg.sdp.client;

import static java.nio.charset.StandardCharsets.*;

import java.io.*;
import java.net.*;

import com.jgg.sdp.client.ICommClient;
import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.config.Messages;
import com.jgg.sdp.common.ctes.CDG;
import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.common.exceptions.SDPException;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.exceptions.ClientException;
import com.jgg.sdp.tools.json.JSONObject;

public class ClientHTTP implements ICommClient {
	
	private Configuration cfg = ConfigurationLocal.getInstance();
	private Messages      msg = Messages.getInstance();
	
/*	
    private final static char[] MULTIPART_CHARS =
		        "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		             .toCharArray();
*/
	private String  server;
	private Integer port;
	private String  service;

    public ClientHTTP() {
		this.server  = cfg.getJMSHostName(CDG.JMS_HTTP);
		this.port    = cfg.getJMSPort(CDG.JMS_HTTP);
		this.service = cfg.getJMSServicePersister(CDG.JMS_HTTP);
		if (server == null || port == null || service == null) {
			throw new SDPException(MSG.EXCEPTION_KEY, 
					               msg.getMsg(MSG.EXCEPTION_KEY_SERVER)); 
		}
    }
	
/*
	private String generateBoundary() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
           buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
   }
*/

	@SuppressWarnings("unchecked")
	private String mountJSON(String fullPath, int type, byte[] raw) {
		JSONObject obj = new JSONObject();
		
        obj.put("fullName", fullPath);
        obj.put("fileType", new Integer(type));
        obj.put("user", System.getProperty("user.name"));
        obj.put("modules", new Integer(1));
        
        byte[] utf = new String(raw).getBytes(UTF_8);
        String data = new String(utf); 
        obj.put("fileData", data);

        return obj.toJSONString();
	}
	public void openConnection(String host, int port) throws ClientException {
		// TODO Auto-generated method stub
		
	}
	public void closeConnection() throws ClientException {
		// TODO Auto-generated method stub
		
	}
	public void openEndPointOutput(String name) throws ClientException {
		// TODO Auto-generated method stub
		
	}
	public void openEndPointInput(String name) throws ClientException {
		// TODO Auto-generated method stub
		
	}
	public int sendText(String text) throws ClientException {
		// TODO Auto-generated method stub
		return 0;
	}
	public int sendData(byte[] data) throws ClientException {
		// TODO Auto-generated method stub
		return 0;
		
	}

	public int sendUnit(Object obj) {
		URL serverUrl;
		String txtPort = "";
		
		if (port > 0) txtPort = ":" + port;
		
		try {
			serverUrl = new URL("http://" + server + txtPort + "/" + service);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

			conn.setRequestMethod("POST");
//			conn.addRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			 		
			ObjectOutputStream oos = new ObjectOutputStream (conn.getOutputStream());
			oos.writeObject(obj);
            oos.flush(); 
			oos.close();
			int rc = (conn.getResponseCode() == 200) ? 0 : conn.getResponseCode();
			return rc;
			
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}
		
	public int sendFile(String fullPath, int type, byte[] data) throws ClientException {
		URL serverUrl;
		String txtPort = "";
		
		if (port > 0) txtPort = ":" + port;
		
		try {
			serverUrl = new URL("http://" + server + txtPort + "/" + service);
			HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

			conn.setRequestMethod("POST");
			conn.addRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			 		
			OutputStream os = conn.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(mountJSON(fullPath, type, data));
            bw.flush();
			os.close();
			bw.close();

			int rc = (conn.getResponseCode() == 200) ? 0 : conn.getResponseCode();
			return rc;
			
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}

}

