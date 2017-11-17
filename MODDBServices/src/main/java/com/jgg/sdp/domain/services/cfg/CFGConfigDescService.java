package com.jgg.sdp.domain.services.cfg;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.cfg.CFGConfigDesc;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class CFGConfigDescService extends AbstractService<CFGConfigDesc> {

	public CFGConfigDesc getDescription(String key, String lang, String dialect) {
		CFGConfigDesc t = null;
		if (lang != null) {
			if (dialect != null) {
				t = find(CFGConfigDesc.getDesc, key, lang, dialect);
			}
			else {
				t = find(CFGConfigDesc.getDesc, key, lang, SYS.DEF_DIALECT);
			}
		}
		
		if ( t == null) t = find(CFGConfigDesc.getDesc, key, SYS.DEF_LANG, SYS.DEF_DIALECT);
		return t;
	}
	
	public void delDescription (String key, String lang, String dialect) {
		if (lang != null && dialect != null) {
			updateQuery(CFGConfigDesc.delDesc, key, lang, dialect);
		}
	}

	public void deleteAll () {
		updateQuery(CFGConfigDesc.delAll);
	}
	
}
