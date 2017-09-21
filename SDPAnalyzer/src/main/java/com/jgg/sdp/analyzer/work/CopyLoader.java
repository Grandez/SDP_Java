package com.jgg.sdp.analyzer.work;

import java.io.File;
import java.util.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.core.unit.*;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.*;

public class CopyLoader {

	private Configuration cfg = DBConfiguration.getInstance();

	private SDPFileService   fileService   = new SDPFileService();
	private SDPSourceService sourceService = new SDPSourceService();
	
	public Source load(String name, int type, ArrayList<String> toks) {
		String cpyFile = getFullPathCopy(name);
		if (cpyFile == null) return null;
		Source copy = SourcesFactory.getCopy(new Archivo(cpyFile), toks);
		copy.setTipo(type);
		if (!cfg.isLocalMode()) copy = loadDataFromDB(copy, toks);
		return copy;
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
	
	private Source loadDataFromDB(Source copy, ArrayList<String> toks) {
         SDPFile file = fileService.findByNameAndType(copy.getBaseName(), copy.getTipo());	
         if (file == null) return null;
         // Si existe el fichero existe el codigo
         SDPSource source = sourceService.getSource(file.getIdFile(), file.getIdVersion());
         copy.setFirma(file.getFirma());
         copy.setId(file.getIdFile());
         copy.setRawData(source.getSource(), toks);
         return copy;
	}
}
