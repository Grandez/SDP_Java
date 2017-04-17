package com.jgg.sdp.domain.services.rules;

import java.util.List;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.AbstractService;

public class RULGroupsService extends AbstractService<RULGroup> {
	
	public List<RULGroup> listAll() {
		return lista(RULGroup.listAll);
	}
	public List<RULGroup> listActives() {
		return lista(RULGroup.listActive);
	}
	
	public void clean() {
	    sqlExecute("DELETE FROM RUL_GROUPS");
	}
 
}
