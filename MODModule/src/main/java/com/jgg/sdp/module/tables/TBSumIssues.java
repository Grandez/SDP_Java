package com.jgg.sdp.module.tables;

public class TBSumIssues {

	private int[] issuesLevel  = {0,1,2,3,4,5};
	private int[] issuesCount  = {0,0,0,0,0,0};
	private int[] issuesMax    = {0,0,0,0,0,0};
	private int[] issuesStatus = {0,0,0,0,0,0};
	
	private int totalCount  = 0;
	private int totalMax    = 0;
	private int totalStatus = 0;
	
	public TBSumIssues() {
	}
	
	public void incCountIssues(int severity) {
		issuesCount[severity]++;
	}

	public void setCount(int severity, int value) {
		if (severity == 99) {
			totalCount = value;  
		}
		else {
            issuesCount[severity] = value;
		}	
	}

	public int getCount(int severity) {
		if (severity == 99) {
			return totalCount;
		}
		else {
		    return issuesCount[severity];
		}    
	}
	
	public void setMaximum(int severity, int max) {
		if (severity == 99) {
			totalMax = max;  
		}
		else {
            issuesMax[severity] = max;
		}
	}
	
	public int getMaximum(int severity) {
		if (severity == 99)  return totalMax;  
        return  issuesMax[severity];
    }
	
	public void setStatus(int severity, int st) {
		if (severity == 99) {
			totalStatus = st;  
		}
		else {
		   issuesStatus[severity] = st;
		}
	}
	
	public int getStatus(int severity) {
		return issuesStatus[severity];
	}
	
    public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalMax() {
		return totalMax;
	}

	public void setTotalMax(int totalMax) {
		this.totalMax = totalMax;
	}

	public int getTotalStatus() {
		return totalStatus;
	}

	public void setTotalStatus(int totalStatus) {
		this.totalStatus = totalStatus;
	}

	/********************************************************/
	/* INTROSPECTION                                        */
	/********************************************************/
	
	public int[] getIssuesLevels() { return issuesLevel; }
	public int[] getIssuesCounts() { return issuesCount; }
}
