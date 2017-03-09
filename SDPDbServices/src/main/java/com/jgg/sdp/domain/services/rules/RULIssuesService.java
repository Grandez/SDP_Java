package com.jgg.sdp.domain.services.rules;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.rules.RULIssue;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class RULIssuesService extends AbstractService<RULIssue> {

	public RULIssue getIssue(Integer idIssue) {
		return find ("find", idIssue);
	}
	public List<RULIssue> listByItem(Integer idItem) {
		return lista(RULIssue.listByItem, idItem);
	}
	public List<RULIssue> listActiveByItem(Integer idItem) {
		return lista(RULIssue.listActiveByItem, idItem);
	}

} 
