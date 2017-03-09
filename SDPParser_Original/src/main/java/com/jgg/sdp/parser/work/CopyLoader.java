package com.jgg.sdp.parser.work;

import java.io.File;
import java.util.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.module.factorias.SourcesFactory;
import com.jgg.sdp.module.unit.Source;

public class CopyLoader {

	private Configuration cfg = Configuration.getInstance();
	    
	public Source load(String name, ArrayList<String> toks) {
		String cpyFile = getFullPathCopy(name);
		if (cpyFile == null) return null;
		return SourcesFactory.getCopy(new Archivo(cpyFile), toks);
	}
	
	private String getFullPathCopy(String name) {
		StringBuilder buff = new StringBuilder(256);
		
		ArrayList<String> dirs = cfg.getList(CFG.DIR_COPY);
		ArrayList<String> exts = cfg.getList(CFG.COPY_EXT);
		
		int baseLength;
		
		for (String dir : dirs) {
			buff.setLength(0);
			buff.append(dir);
			if (dir.charAt(dir.length() - 1) != File.separatorChar) {
				buff.append(File.separatorChar);
			}
			buff.append(name);
		    baseLength = buff.length();
		    
			for (String ext : exts) {
				buff.setLength(baseLength);
				if (ext.length() > 1) buff.append(ext);
				File f = new File(buff.toString());
//				System.err.println("Buscando COPY " + buff);
				if (f.exists()) return buff.toString();
			}
		}
		return null;
	}
}
