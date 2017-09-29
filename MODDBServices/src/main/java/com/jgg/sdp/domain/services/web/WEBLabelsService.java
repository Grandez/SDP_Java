package com.jgg.sdp.domain.services.web;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.web.WEBLabel;

@Repository
public class WEBLabelsService extends AbstractService<WEBLabel> {

	public List<WEBLabel> listByGroup(String lang[], int idGroup) {
		// Lang and dialect
		List<WEBLabel> list = listQuery(WEBLabel.listByGroup, lang[0], lang[1], idGroup);
		
		// Lang
		if (list.size() == 0) {
			lang[1] = lang[0];
			list = listQuery(WEBLabel.listByGroup, lang[0], lang[1], idGroup);
		}
		
		// Default lang
		if (list.size() == 0) {
			lang[0] = SYS.DEF_LANG;
			lang[1] = SYS.DEF_DIALECT;
			list = listQuery(WEBLabel.listByGroup, lang[0], lang[1], idGroup);
		}
		
		return list; 
	}
	
	public List<WEBLabel> listByGroup(String lang, String dialect, int idGroup) {
		return listQuery(WEBLabel.listByGroup, lang, dialect, idGroup);
	}

	public List<WEBLabel> listAll() {
		return listQuery(WEBLabel.listAll);
	}
	
}
