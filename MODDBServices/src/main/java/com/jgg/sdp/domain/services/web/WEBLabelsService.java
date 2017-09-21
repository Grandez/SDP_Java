package com.jgg.sdp.domain.services.web;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.web.WEBLabel;

@Repository
public class WEBLabelsService extends AbstractService<WEBLabel> {

	public List<WEBLabel> listByGroup(int idGroup) {
		return listQuery(WEBLabel.listByGroup, idGroup);
	}

	public List<WEBLabel> listAll() {
		return listQuery(WEBLabel.listAll);
	}
	
}
