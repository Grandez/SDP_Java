package com.jgg.sdp.domain.services.core;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPFilesService extends AbstractService<SDPFile> {

	public List<SDPFile> listByName(String name) {
		return null; 
	}
	
	public SDPFile findByNameAndType(String name, int type) {
		return findQuery(SDPFile.findByNameAndType, name, type);
	}

	public void deleteByNameAndType(String name, int type) {
        deleteQuery(SDPFile.deleteByNameAndType, name, type);
	}
	
}
