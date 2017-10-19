package com.jgg.sdp.collector.persister;

import com.jgg.sdp.common.SDPMember;
import com.jgg.sdp.common.SDPUnit;
import com.jgg.sdp.common.ctes.CDG;
import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.tools.Fechas;

public class SourcePersister {
	
	private long idFile;
	private long idVersion;
	
    SDPFileService      fileService    = new SDPFileService();
    SDPSourceService    sourceService  = new SDPSourceService(); 
	
	public void persistUnit(SDPUnit unit) {
		idFile    = Fechas.serial();
		idVersion = Fechas.serial();
		
		fileService.beginTrans();
		
		for (SDPMember member : unit.getMembers()) {
			if (persistFile(unit, member)) {
			    persistSource(unit, member);
			}			
		}
		
		fileService.commitTrans();
	}
	
	private boolean persistFile(SDPUnit unit, SDPMember member) {
		SDPFile file = fileService.findByNameAndType(member.getBaseName(), member.getType());
		if (file != null && member.getFirma().compareTo(file.getFirma()) == 0) return false;
		
		file = new SDPFile();
		file.setIdFile(idFile);
		file.setIdVersion(idVersion);
		file.setArchivo(member.getBaseName());
		file.setFullName(member.getFullName());
		file.setFirma(member.getFirma());
		file.setNumModulos(1);
		file.setTipo(member.getType());
		file.setTms(unit.getTms());
		file.setUid(unit.getUid());
		file.setEstado(CDG.STATUS_UNPARSED);

		fileService.update(file);
		return true;
	}
	
	private void persistSource(SDPUnit unit, SDPMember member) {
		SDPSource source = new SDPSource();
		source.setIdFile(idFile);
		source.setIdVersion(idVersion);
		source.setSource(member.getZipData());
		source.setEncoded("ZIP");
		sourceService.update(source);
	}
}
