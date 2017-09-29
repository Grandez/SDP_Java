package com.jgg.sdp.client;

import static java.nio.charset.StandardCharsets.*;

import java.io.*;
import java.net.*;

import com.jgg.sdp.clients.MQSClient;
import com.jgg.sdp.core.config.*;
import com.jgg.sdp.tools.json.JSONObject;

public class ClientHTML implements MQSClient {
	
	private Configuration cfg = ConfigurationLocal.getInstance();
	
/*	
    private final static char[] MULTIPART_CHARS =
		        "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		             .toCharArray();
*/
	private String server;
	private int    port;
	private String service;

    public ClientHTML() {
		this.server  = cfg.getJMSHostName();
		this.port    = cfg.getJMSPort();
		this.service = cfg.getJMSServicePersister();

    }
	public int sendZipFile(String fullPath, int type, byte[] raw) {
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
            bw.write(mountJSON(fullPath, type, raw));
            bw.flush();
			os.close();
			bw.close();

			int rc = (conn.getResponseCode() == 200) ? 0 : conn.getResponseCode();
			return rc;
			
		} catch (Exception e) {
			return 500;
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

}

