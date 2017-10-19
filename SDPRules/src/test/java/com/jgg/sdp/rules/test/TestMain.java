package com.jgg.sdp.rules.test;

import java.io.File;
import java.net.URL;

import com.jgg.sdp.rules.RulesLoader;

public class TestMain {

	private RulesLoader loader = new RulesLoader();
	
	public static void main(String[] args) throws Exception {
		   int rc = 0;
		   TestMain launcher = new TestMain();
		   rc = launcher.start(args);
	       System.exit(rc);
		}

		private int start(String[] args) {
			URL root = Thread.currentThread().getContextClassLoader().getResource("rules");
			File dir = new File(root.getPath());

			
			for (File f : dir.listFiles()) {
				if (f.getName().endsWith(".xml")) {
				    System.out.print("Processing " + f.getName());
				    loader.processXMLRule(f);
				    System.out.println("\tOK");
				}    
            }

			return 0;		
		}

}
