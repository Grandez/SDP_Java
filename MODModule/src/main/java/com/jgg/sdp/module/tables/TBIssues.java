package com.jgg.sdp.module.tables;

/**
 * Tabla de Issues. 
 * Puede haber varios issues en la misma liena, columna 
 */
import java.util.*;

import com.jgg.sdp.module.items.Issue;

public class TBIssues {
	
    private ArrayList<Issue> issues = new ArrayList<Issue>();
    
    public void addIssue(Issue issue) {
        issues.add(issue);
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }
    
    public void setIssues(List<Issue> issues) {
    	this.issues.addAll(issues);
    }

    public int getCount() {
    	return issues.size();
    }
}
