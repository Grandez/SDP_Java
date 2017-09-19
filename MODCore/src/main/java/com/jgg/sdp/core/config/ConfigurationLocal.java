package com.jgg.sdp.core.config;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.tools.Propiedades;

public class ConfigurationLocal extends ConfigurationBase implements Configuration {


    private HashSet<String>         ignore = new HashSet<String>();

	public static Configuration getInstance() {
		if (cfg == null) {
			cfg = new ConfigurationLocal();
		}
		return cfg;		
	}
	
	private ConfigurationLocal() {
	    loadConfigResource(CFG.CFG_DEFAULT);
        setDefaultConfigDir();
        loadFilesToIgnore();
	}
	
	
	private void loadConfigResource(String name) {
	    Propiedades props = new Propiedades();
	    loadParms(props.loadResource(name));		
	}
	
	private void setDefaultConfigDir() {
		int pos = 0;
		
		URL url = Configuration.class.getResource("Configuration.class");
		
		// Caso file: Se esta ejecutando en eclipse. Utilizar la configuracion
		if (url.getProtocol().compareToIgnoreCase("file") == 0) return;

		// No se puede utilizar file.separator. Java pone /		
		String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		pos = path.lastIndexOf('/');
		path = path.substring(0, pos);
		pos = path.lastIndexOf('/');
		path = path.substring(0, pos);
		path = path + File.separator + getString(CFG.DIR_CONFIG_REL);
		setParameter(CFG.DIR_CONFIG, path);
	}
	
	
/*	
	public char getCollectorProcess() {
		String value = getValue(CFG.COLLECTOR);
		return value.toUpperCase().charAt(0);
	}
	
*/



	


    private void loadFilesToIgnore() {
    	String f = getString(CFG.FILE_IGNORE);
    	String d = getConfigDir();
    	
    	if (f == null) return;
    	
		FileInputStream fis = null;
		try {
		   fis = new FileInputStream(d + f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
 
	        String line = null;
            while ((line = br.readLine()) != null) {
			   line = line.trim();
			   if (line.length() > 0) {
				   if (line.charAt(0) != '#') {
					   String toks[] = line.split("[ ;\t]");
					   ignore.add(toks[0]);  
				   }
			   }
		    }
	        br.close();	
		}
		catch (Exception e) { // No existe el fichero, se ignora el error
			return;
		}
	}


	
    
}
