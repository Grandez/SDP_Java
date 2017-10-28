package com.jgg.sdp.analyzer.work;

import static com.jgg.sdp.common.ctes.CFG.*;

import java.io.File;
import java.util.*;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.common.files.Archive;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.module.unit.*;

public class CopyLoader {

	private Configuration cfg = DBConfiguration.getInstance();

	private SDPFileService   fileService   = new SDPFileService();
	private SDPSourceService sourceService = new SDPSourceService();
	
	public Source load(Unit unit, String name, int type, ArrayList<String> toks) {
		// 1.- Chequear si ya ha sido cargado
		Source src = unit.setCurrentSource(name, type);
		if (src != null) {
			src.prepareData(toks);
			return src;
		}
		// 2.- Obtener la fuente
		if (cfg.getParserMode() != PARSER_LOCAL) {
		   return loadFromDataBase(name, type, toks);	
		}
		
		String cpyFile = getFullPathCopy(name);
		if (cpyFile == null) return null;
		Source copy = new Source(new Archive(cpyFile), toks);
		copy.setTipo(type);
		return copy;
	}
	
	public Source loadFromDataBase(String name, int type, ArrayList<String> toks) {
		SDPFile file = fileService.findByNameAndType(name, type);
		if (file == null) return null;
		SDPSource src = sourceService.getSource(file.getIdFile(), file.getIdVersion());
		if (src == null) return null;
		Source source = new Source(file.getFullName());
		source.setTipo(file.getTipo());
		source.setIdFile(src.getIdFile());
		source.setIdFileVersion(src.getIdVersion());
		source.setRawData(file.getFullName(), src.getEncoded(), src.getSource());
		source.setFirma(file.getFirma());
		source.prepareData(toks);
		return source;
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
