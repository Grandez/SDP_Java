package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULGroup;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULGroupsService extends AbstractService<RULGroup> {
	
	public List<RULGroup> listAll() {
		return listQuery(RULGroup.listAll);
	}
	public List<RULGroup> listActiveGroups() {
		return listQuery(RULGroup.listActive);
	}
	
	public void clean() {
	    sqlExecute("DELETE FROM RUL_GROUPS");
	}
 
}
