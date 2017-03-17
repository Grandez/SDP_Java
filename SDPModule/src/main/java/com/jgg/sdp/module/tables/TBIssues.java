package com.jgg.sdp.module.tables;

import java.util.*;

import com.jgg.sdp.module.items.Issue;

public class TBIssues {
	
	private HashSet<String> keys = new HashSet<String>();
    private ArrayList<Issue> issues = new ArrayList<Issue>();
    
    public void addIssue(Issue issue) {
    	String key = String.format("%d%d%d", issue.getIdIssue(), issue.getBegLine(), issue.getEndLine());
        if (keys.contains(key)) return;
        keys.add(key);
        issues.add(issue);
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

}
