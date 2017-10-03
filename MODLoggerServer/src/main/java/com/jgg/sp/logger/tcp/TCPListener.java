package com.jgg.sp.logger.tcp;

import java.io.*;
import java.net.*;
	
public class TCPListener extends Thread {

	private int port;
	private boolean listen = true;
	
	public TCPListener(int port) {
		this.port = port;
	}
	
	public void run() {
	  ServerSocket socket;
	   try {
          socket = new ServerSocket(port);
		  while (listen) {
			     Socket conn = socket.accept();
			     BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			     String resp = processSentence(in.readLine());
			     DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			     out.writeBytes(resp + '\n');
		  }

	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		  e.printStackTrace();
	   }
   }

	private String processSentence(String stmt) {
		String res = "OK";
		if (stmt.startsWith("log"))   return res;
		if (stmt.startsWith("nolog")) return res;
		if (stmt.startsWith("test"))  return res;
		if (stmt.startsWith("stop")) {
			listen = false;
			return res;
		}		
		return "KO";
	}
}
