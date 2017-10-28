package com.jgg.sdp.web.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.module.unit.Source;

@Service
public class DBSource {
	
	@Autowired
	SDPFileService fileService;
	@Autowired
	SDPSourceService srcService;
	
	public Source getSourceMember(Long idFile) {
		SDPFile file = fileService.findById(idFile);
		if (file == null) return null;
	    return getSourceMember(file);	
	}

	private Source getSourceMember(SDPFile file) {
		SDPSource src = srcService.getSource(file.getIdFile(), file.getIdVersion());
		if (src == null) return null;
		Source source = new Source(file.getArchivo());
		source.setFirma(file.getFirma());
		source.setIdSource(file.getIdFile());
		source.setIdVersion(src.getIdVersion());
		source.loadRawData(file.getFullName(), src.getEncoded(), src.getSource());
		return source;
	}
	
}
