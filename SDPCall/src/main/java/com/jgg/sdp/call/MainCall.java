/**
 * 
 *
 */

package com.jgg.sdp.call;

import java.io.*;
import java.text.*;
import java.util.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.msg.*;

import com.jgg.sdp.domain.services.core.SDPDependenciaService;


public class MainCall 
{
	
	private String prm[][] = { 
	};

    FileInputStream fis   = null;
	BufferedReader  br    = null;
	String          fName = null;

	private SDPDependenciaService depService = null; 
    private Messages      msg = Messages.getInstance("PARSER");    
    private Configuration cfg = Configuration.getInstance();

    public static void main( String[] args ) {
       int rc = RC.OK;
	   MainCall launcher = new MainCall();
	   rc = launcher.start(args);
       System.exit(rc);

    }
    
    private int start(String[] args) {
		int    maxRC   = RC.OK;
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		
		cfg.setTitles(MSG.TITLE_SDP_CALL, MSG.USE_SDP_CALL);
		
		args = cfg.processCommandLine(prm, args);

		if (depService == null && args.length > 0) {
			depService = new SDPDependenciaService();
			depService.beginTrans();
		}
		
        for (int idx = 0; idx < args.length; idx++) {

        	
            fName = args[idx];
            if (cfg.getVerbose() > 0) {
                System.out.print(df.format(new Date()) + " - " + 
                                 msg.getMsg(MSG.PARSING, args[idx]));
            }
            try {
               openFile();
               processFile();   
               closeFile();
            }
            catch (Exception e) {
            	System.err.println("Error accediendo al fichero " + fName);
            }
        }    
		if (depService != null) {
			depService.commitTrans();
		}

        return maxRC;
    }

    private void processFile() throws IOException {
    	String line = null;
    	int pos = -1;
        while ((line = br.readLine()) != null) {
        	pos = line.indexOf('#');
        	if (pos != -1) {
        		line = line.substring(0,  pos);
        	}
        	line = line.trim();
        	
        	if (line.length() > 0) processLine(line);
	       }
     }
    
   	private void processLine (String line) {
   		String[] tokens = line.toUpperCase().split("[;,\t]");
   		
   		for (int idx = 0; idx < tokens.length; idx++) {
   			tokens[idx] = tokens[idx].trim(); 
   		}
   		
   		switch (tokens.length) {
   		   case 1: depService.deleteModule(tokens);     break;
   		   case 2: depService.deleteVariable(tokens);   break;
   		   case 3: depService.addDependence(tokens);    break;
   		   case 4: depService.deleteDependence(tokens); break;
   		}
	}

    private void openFile() throws FileNotFoundException {
         fis = new FileInputStream(fName);
          br  = new BufferedReader(new InputStreamReader(fis));
    }
    
    private void closeFile() throws IOException {
			br.close();
			fis.close();
    }
}    