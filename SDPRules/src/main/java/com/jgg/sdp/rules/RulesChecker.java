package com.jgg.sdp.rules;

import java.util.*;

import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.module.items.Issue;

public class RulesChecker {

	// Al modulo se pasan al final
	// Los issues del lexer pueden que no sepan el modulo
	private ArrayList<Issue> issues = new ArrayList<Issue>();
	
	private RulesTree tree = RulesTree.getInstance();
	private static RulesChecker checker = null;
	
	private RulesChecker() {
		
	}
	
	public static RulesChecker getInstance() {
		if (checker == null) checker = new RulesChecker();
		return checker;
	}
	
	public List<Issue> getIssues() {
		return issues;
	}
	
/*	
	public RulesChecker(Module module) {
		this.module = module;
	}
*/
/*	
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
	
	
*/	
	public void checkTab(int line, int column) {
          RuleGroup group = tree.getGroupById(RULES.GRP_LEXER);
          if (group == null) return;
          
          RuleItem item = group.getItemByName("TAB");
          if (item == null) return;
          
          processExist(item, "", "\t", line, column);
          
	}

	/**
	 * Casos en los que solo puede haber la regla de no permitido
	 * Procesamos la primera regla unicamente
	 */
	
	private void processExist(RuleItem item, String bloque, String txt, int line, int column) {
		for (RuleRule rule : item.getRules()) {
			applyIssue(txt, bloque, line, column, rule);
			return;
		}		
	}
	
	private void processRules(RuleItem item, String bloque, String txt, int line, int column) {
		for (RuleRule rule : item.getRules()) {
			if (processRule(txt, rule)) {
				applyIssue(txt, bloque, line, column, rule);
				if (rule.getPriority() > 0) return;
			}
		}		
	}
	
	private boolean processRule(String value, RuleRule rule) {
		switch (rule.getComparator()) {
		    case RULES.MISSING: return true;  // Existe
		}
		return false;
	}

	private void applyIssue(String txt, String bloque, int line, int column, RuleRule rule) {
		Issue issue = new Issue(rule.getIdGroup(), rule.getIdItem(), rule.getIdRule());
		issue.setBegLine(line);
		issue.setBegColumn(column);
		issue.setEndLine(line);
		issue.setEndColumn(column);
		issue.setSeverity(rule.getSeverity());
		issue.setBloque(bloque);
		issue.setFirma(Firma.createDefault());
		issues.add(issue);
	}
	
}
