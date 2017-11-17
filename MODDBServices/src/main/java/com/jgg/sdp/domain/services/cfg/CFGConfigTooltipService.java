package com.jgg.sdp.domain.services.cfg;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.cfg.CFGConfigTooltip;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class CFGConfigTooltipService extends AbstractService<CFGConfigTooltip> {

	public CFGConfigTooltip getTooltip(String key, String lang, String dialect) {
		CFGConfigTooltip t = null;
		if (lang != null) {
			if (dialect != null) {
				t = find(CFGConfigTooltip.getTooltip, key, lang, dialect);
			}
			else {
				t = find(CFGConfigTooltip.getTooltip, key, lang, SYS.DEF_DIALECT);
			}
		}
		
		if ( t == null) t = find(CFGConfigTooltip.getTooltip, key, SYS.DEF_LANG, SYS.DEF_DIALECT);
		return t;
	}
	
	public void delTooltip (String key, String lang, String dialect) {
		if (lang != null && dialect != null) {
			updateQuery(CFGConfigTooltip.delTooltip, key, lang, dialect);
		}
	}

	public void deleteAll () {
		updateQuery(CFGConfigTooltip.delAll);
	}
	
}

