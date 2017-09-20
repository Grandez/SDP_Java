package com.jgg.sdp.ivp;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.printer.Printer;

public class IVPLaunchers {

    private Configuration cfg = DBConfiguration.getInstance();

    private Printer printer = new Printer();
    
	public int setEnvironment(int level) {
		printer.lineBeg("Estableciendo entorno para bloque " + level);
		File module = getModuleName(level);
		if (module == null) {
			printer.lineEnd("NO");
			return -1;
		}
		int rc = launchScript(module);
		String end = ( rc == 0) ? "OK" : "KO"; 
		printer.lineEnd(end);
		return rc;
	}
	
	private File getModuleName(int level) {
		String ext = System.getProperty("os.name").startsWith("Windows") ? ".bat" : ".sh";
		String dir = cfg.getConfigDir();
		String base = cfg.getString(CFG.IVP_LOADER);
		String fileName = dir + base + level + ext;
		fileName = "P:\\SDP\\config\\IVPLoader" + level + ".bat";
		File f = new File(fileName);
		return (f.exists()) ? f : null;
	}
	
	private int launchScript(File script) {
		try {       
		   ProcessBuilder pb = new ProcessBuilder(Arrays.asList("cmd.exe", "/C", script.getAbsolutePath()));
		   pb.redirectErrorStream(true);
	       pb.redirectError(Redirect.PIPE);
           Process p = pb.start();
           p.waitFor(300, TimeUnit.SECONDS);
           return p.exitValue();
		} catch (Exception e) {
			return -1;
		}
	}
}

