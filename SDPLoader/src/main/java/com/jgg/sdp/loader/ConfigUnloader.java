package com.jgg.sdp.loader;

import java.io.*;

import com.jgg.sdp.domain.cfg.CFGConfig;
import com.jgg.sdp.domain.services.cfg.CFGConfigService;

public class ConfigUnloader {

	private CFGConfigService cfgService = new CFGConfigService();

	private String FILENAME = "P:\\SDP\\Config\\SDPConfig.xml";
	private BufferedWriter bw = null;
	private FileWriter fw = null;
	
	public void unload() {
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write("<SDPConfig>");
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		for (CFGConfig c  : cfgService.getAll()) {
			try {
				bw.write("   <item>");
				bw.newLine();
				escribe("key"   , c.getClave());
				escribe("value" , c.getValor());
				escribe("grupo" , c.getGrupo());
				escribe("type"  , c.getTipo());
				escribe("min"   , c.getMinimo());
				escribe("max"   , c.getMaximo());
				escribe("mask"  , c.getMask());
				bw.write("   </item>");
				bw.newLine();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		try {
			bw.write("</SDPConfig>");
			bw.newLine();

			if (bw != null)
				bw.close();
				if (fw != null)
					fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	private void escribe(String k, Integer value) {
		if (value == null) return;
	    escribe(k, value.toString());
	}
	private void escribe(String k, String value) {
		if (value == null) return;
	    if (value.compareTo("N/A") == 0) return;
		try {
			bw.write("      <" + k + ">");
			bw.write(value);
			bw.write("</" + k + ">");
			bw.newLine();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
