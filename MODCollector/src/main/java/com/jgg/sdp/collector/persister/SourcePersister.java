package com.jgg.sdp.collector.persister;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.tools.Fechas;

public class SourcePersister {
	
	private long idFile;
	private long idVersion;
	
    SDPFileService      fileService    = new SDPFileService();
    SDPSourceService    sourceService  = new SDPSourceService(); 
	
	public void persist(SourceInfo member) {
		idFile    = Fechas.serial();
		idVersion = Fechas.serial();
		
		fileService.beginTrans();
		
		if (persistFile(member)) {
		    persistSource(member);
		}
		
		fileService.commitTrans();
	}
	
	private boolean persistFile(SourceInfo member) {
		SDPFile file = fileService.findByNameAndType(member.getName(), member.getType());
		if (file != null && member.getFirma().compareTo(file.getFirma()) == 0) return false;
		
		file = new SDPFile();
		file.setIdFile(idFile);
		file.setIdVersion(idVersion);
		file.setArchivo(member.getName());
		file.setFullName(member.getFullPath());
		file.setFirma(member.getFirma());
		file.setNumModulos(member.getModules());
		file.setTipo(member.getType());
		file.setTms(member.getTms());
		file.setUid(member.getUid());
		file.setEstado(CDG.STATUS_PENDING);

		fileService.update(file);
		return true;
	}
	
	private void persistSource(SourceInfo member) {
		SDPSource source = new SDPSource();
		source.setIdFile(idFile);
		source.setIdVersion(idVersion);
		source.setSource(member.getSource().getBytes());
		source.setEncoded("UTF-8");
		sourceService.update(source);
	}
}
