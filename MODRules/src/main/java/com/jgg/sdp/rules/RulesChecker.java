package com.jgg.sdp.rules;

import java.math.BigDecimal;
import java.util.*;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.module.work.CommentLine;
import com.jgg.sdp.rules.objects.*;

import static com.jgg.sdp.rules.ctes.CDGRules.*;
import static com.jgg.sdp.rules.ctes.CDGItems.*;

public class RulesChecker {

	private RulesProcessor processor = new RulesProcessor();
	
	
	
	public RulesChecker() {
		
	}
	
	public List<Issue> getIssues() {
		return processor.getIssues();
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
	
	/***************************************************************/
	/* Reglas finales                                              */
	/***************************************************************/

	public void checkModule(Module module) {
		checkIssues(module);
	}
	
	private void checkIssues(Module module) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(0);
        obj.setBegColumn(0);
        obj.setComponent(module.getSumIssues());

        processor.processItemByName(ITEM_RULES, obj);
        
	}
	
	/***************************************************************/
	/* Reglas a nivel lexer                                        */
	/***************************************************************/
	
	public void checkTab(int line, int column) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setBegColumn(column);
        obj.setComponent(true);

        processor.processItemByName(ITEM_TAB, obj);        
	}

	public void checkComment(CommentLine comment) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(comment.getLine());
        obj.setComponent(comment);

        processor.processItemByName(ITEM_COMMENT, obj);
	}
	
	public void checkNoPrintable(int line, int column) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setBegColumn(column);
        obj.setComponent(true);
        processor.processItemByName(ITEM_HEXDATA, obj);
	}

	public void checkCompileDirective(int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(true);
        processor.processItemByName(ITEM_CBL_DIRECTIVE, obj);		
	}
	
	public void checkPrintDirective(String directive, int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(directive);
        processor.processItemByName(ITEM_CBL_LIST_DIR, obj);		
	}
	
}
