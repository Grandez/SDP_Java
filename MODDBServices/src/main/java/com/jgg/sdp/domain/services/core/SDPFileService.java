package com.jgg.sdp.domain.services.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.domain.core.SDPFile;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class SDPFileService extends AbstractService<SDPFile> {

	private List<SDPFile> data = new ArrayList<SDPFile>();
	
	private Long last = 0L;

	public SDPFile findById(long idFile) {
/* JGG No cache				
        SDPFile value = dbCache.get(idFile);
        if (value == null) {
    		List<SDPFile> l = listQuery(SDPFile.findById, idFile);
    		if (l.isEmpty()) return null;
    		value = dbCache.put(idFile, l.get(0));
        }
*/        
		return getFirst(listQuery(SDPFile.findById, idFile));
	}
	
	public SDPFile findByNameAndType(String name, int type) {
/* JGG No cache		
		for (SDPFile f : dbCache.getAll()) {
			if (f.getArchivo().compareTo(name) == 0) {
				if (f.getTipo() == type) {
					return f;
				}
			}
		}
*/		
		return getFirst(listQuery(SDPFile.findByNameAndType, name, type));
	}
	
	private SDPFile getFirst(List<SDPFile> l) {
		if (l.isEmpty()) return null;
		return l.get(0);
	}

	public void deleteByNameAndType(String name, int type) {
        deleteQuery(SDPFile.deleteByNameAndType, name, type);
	}

	public List<SDPFile> listByName(String name) {
		return null; 
	}
		
	public void cursorPendingOpen() {
		data = new ArrayList<SDPFile>();
        data = listQueryCursor(SDPFile.listPendingCursor, last, CDG.SOURCE_CODE);
	}

	
	public SDPFile cursorPendingNext() {
		if (data.size() == 0) cursorPendingOpen();
		if (data.size() == 0) return null;
		SDPFile f = data.remove(0);
		last = f.getIdFile();
		return f;
	}
	
}
