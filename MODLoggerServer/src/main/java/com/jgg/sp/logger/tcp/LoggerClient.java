package com.jgg.sp.logger.tcp;

import java.io.*;
import java.net.*;

public class LoggerClient {

	public boolean loggerActive(int port) {
	  Socket client;
	  try {
		client = new Socket("localhost", port);
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		BufferedReader   in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out.writeBytes("ready\n");
		in.readLine();
		client.close();
		return true;
	  } catch (Exception e) {
	    return false;
	  }
	}  
}
