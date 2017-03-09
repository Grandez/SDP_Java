package com.jgg.sdp.domain.services.module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.module.MODIssue;
import com.jgg.sdp.domain.services.AbstractService;
import com.jgg.sdp.domain.services.rules.RULDescService;

@Repository
public class MODIssueService extends AbstractService<MODIssue> {

	private RULDescService descService = new RULDescService();
	
	public List<MODIssue> getIssues(Long idVersion) {
		List<MODIssue> datos = lista(MODIssue.issuesByVersion, idVersion);
		for (MODIssue issue : datos) {
			issue.setDesc(descService.getIssueDecription(issue.getIdIssue()));
		}
		return datos;
	}
	
	public List<Integer> getIssuesCount(Long idVersion) {
		int[] issues = new int[6]; 
		List<Object[]>	data = nativeQuery(MODIssue.sqlCountIssues, idVersion);
		
		for (Object[] o : data) {
			issues[(Integer) o[0]] = (((BigInteger) o[1]).intValue());
		}
		ArrayList<Integer> d = new ArrayList<Integer>();
		for (int idx = 0; idx < issues.length; idx++) {
			d.add(issues[idx]);
		}
		return d;
	}
}
