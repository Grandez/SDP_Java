package com.jgg.sdp.rules;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RuleItem {

	private int       idGroup;
	private int       idItem;
	private int       keyNum;
	private String    keyTxt;
	private String    uid;
	private Timestamp tms;
	
	private ArrayList<RuleIssue> issues = new ArrayList<RuleIssue>();
	
	public int getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public int getKeyNum() {
		return keyNum;
	}
	public void setKeyNum(int keyNum) {
		this.keyNum = keyNum;
	}
	public String getKeyTxt() {
		return keyTxt;
	}
	public void setKeyTxt(String keyTxt) {
		this.keyTxt = keyTxt;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Timestamp getTms() {
		return tms;
	}
	public void setTms(Timestamp tms) {
		this.tms = tms;
	}
	
	
	public void addIssue(RuleIssue issue) {
	    issues.add(issue);	
	}
	
	public ArrayList<RuleIssue> getRuleIssues() {
		return issues;
	}
}
