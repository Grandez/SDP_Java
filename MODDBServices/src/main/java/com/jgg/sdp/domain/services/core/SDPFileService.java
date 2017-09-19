package com.jgg.sdp.domain.services.core;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPFileService extends AbstractService<SDPFile> {

	public SDPFile findById(long idFile) {
		List<SDPFile> l = listQuery(SDPFile.findById, idFile);
		if (l.isEmpty()) return null;
		return l.get(0);
	}
	
	public List<SDPFile> listByName(String name) {
		return null; 
	}
	
	public SDPFile findByNameAndType(String name, int type) {
		List<SDPFile> l = listQuery(SDPFile.findByNameAndType, name, type);
		if (l.isEmpty()) return null;
		return l.get(0);
	}

	public void deleteByNameAndType(String name, int type) {
        deleteQuery(SDPFile.deleteByNameAndType, name, type);
	}
	
}
