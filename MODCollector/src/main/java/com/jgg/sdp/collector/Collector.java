/*
 * 
 */
package com.jgg.sdp.collector;

import com.jgg.sdp.collector.process.*;
import com.jgg.sdp.core.config.Args;
import com.jgg.sdp.core.config.Configuration;

public class Collector {

	private Configuration cfg = Configuration.getInstance();
	
	String prm[][] = { 
		    {"P", "p" , "parser"  , ""           , "collector.type"  , "0" , "205" , Args.STRING}			          
		   ,{"T", "t" , "trapper" , ""           , "collector.type"  , "0" , "205" , Args.STRING}
		   ,{"C", "c" , "collect" , ""           , "collector.type"  , "0" , "205" , Args.STRING}
           ,{"X", "x" , "tool" , ""           , "collector.type"  , "0" , "205" , Args.STRING}		   
		   ,{" ", "o" , "output" , "SDP_OUTPUT"  , "jms.output.queue", "1" , "206" , Args.STRING}
		   ,{" ", "q" , "qname"  , "SDP_QUEUE"   , "jms.queue"       , "1" , "208" , Args.STRING}	 
	};
	
	/**
     * The main method.
     *
     * @param args
     *            the arguments
     */
	public static void main(String[] args) {
		Collector collector = new Collector();
        int rc = collector.start(args);
	    System.exit(rc);
	}

	private int start(String[] args) {
        IProcess proceso = null;
		cfg.processCommandLine(prm, args);
		switch (cfg.getCollectorProcess()) {
		   case 'P': proceso = new CollectorParser();       break;
		   case 'T': proceso = new Trapper();      break;
		   case 'C': proceso = new Consolidator(); break;
		}
        return proceso.process();
	}
}	
