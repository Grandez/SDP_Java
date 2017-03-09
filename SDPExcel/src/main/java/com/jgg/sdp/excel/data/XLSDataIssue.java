package com.jgg.sdp.excel.data;

import com.jgg.sdp.domain.module.MODIssue;
import com.jgg.sdp.domain.services.module.MODIssueService;

public class XLSDataIssue extends XLSDataService {

	private MODIssueService issueService = new MODIssueService();
	
	public boolean open(String table, Long key) {
		for (MODIssue issue : issueService.getIssues(key)) {
			datos.add(issue);
		}
		pos = -1;
		return false;
	}

	public boolean next() {
		if (++pos < datos.size()) return true;
        return false;
    }

	public boolean close() {
		return false;
	}

	public Object getValue(String member) {
		MODIssue issue = (MODIssue) datos.get(pos);
		if (member.compareTo("ID")        == 0) return issue.getIdIssue();
		if (member.compareTo("SEVERITY")  == 0) return issue.getSeverity();
		if (member.compareTo("BEGLINE")   == 0) return issue.getBegLine();
		if (member.compareTo("ENDLINE")   == 0) return issue.getEndLine();		
		if (member.compareTo("BEGCOLUMN") == 0) return issue.getBegLine();
		if (member.compareTo("ENDLINE")   == 0) return issue.getEndLine();
		if (member.compareTo("ENDCOLUMN") == 0) return issue.getEndColumn();
		if (member.compareTo("DESC")      == 0) return issue.getDesc();
        return null;
	}

	public int getLevel() {
		return 0;
	}
}
