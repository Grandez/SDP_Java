package com.jgg.sdp.rules;

import java.util.ArrayList;

import com.jgg.sdp.core.tools.Firma;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.Issue;

public class RulesChecker {

	private Module module = null;
	private Rules rules = Rules.getInstance();
	
	public RulesChecker(Module module) {
		this.module = module;
	}
	
	public boolean setGroup(String name) {
		return rules.setGroup(name);
	}

	public ArrayList<RuleItem> getItemsByGroup(String groupName) {
		ArrayList<RuleItem> issues = new ArrayList<RuleItem>();
		RuleGroup group = rules.getGroupByName(groupName);
		if (group == null) return issues;
		return group.getItems();
	}
	
	public ArrayList<RuleIssue> getIssuesByGroup(String groupName) {
		ArrayList<RuleIssue> issues = new ArrayList<RuleIssue>();
		RuleGroup group = rules.getGroupByName(groupName);
		if (group == null) return issues;
		for (RuleItem item : group.getItems()) {
			issues.addAll(item.getRuleIssues());
		}
		return issues;
	}

	public void checkComment(String txt, int line) {
		RuleGroup group = rules.getGroupByName("COMMENT");
		for (RuleItem item : group.getItems()) {
			processIssues(item, group.getName(), txt, line, 0);
		}
	}

	public void checkSymbol(String txt, int line, int column) {
		RuleGroup group = rules.getGroupByName("SYMBOLS");
		RuleItem  item = group.getItem(txt);
		if (item != null) {
		    processIssues(item, group.getName(), txt, line, column);
		}
	}

	public void checkLiteral(String txt, int line, int column) {
		RuleGroup group = rules.getGroupByName("SYMBOLS");
		RuleItem  item = group.getItem(" ");
		if (item != null) {
			processIssues(item, group.getName(), txt, line, column);
		}
	}
	
	private void processIssues(RuleItem item, String bloque, String txt, int line, int column) {
		for (RuleIssue issue : item.getRuleIssues()) {
			if (processIssue(txt, issue)) {
				applyIssue(txt, bloque, line, column, issue);
			}
		}		
	}
	
	private boolean processIssue(String value, RuleIssue issue) {
		switch (issue.getComparator()) {
		    case RULE.EXIST: if (value.compareTo(issue.getValor()) == 0) return true;
		}
		return false;
	}
	
	private void applyIssue(String txt, String bloque, int line, int column, RuleIssue ruleIssue) {
		Issue issue = new Issue(ruleIssue.getIdIssue());
		issue.setBegLine(line);
		issue.setBegColumn(column);
		issue.setEndLine(line);
		issue.setEndColumn(column);
		issue.setSeverity(ruleIssue.getSeverity());
		issue.setBloque(bloque);
		issue.setFirma(Firma.createDefault());
		module.addIssue(issue);
	}
}
