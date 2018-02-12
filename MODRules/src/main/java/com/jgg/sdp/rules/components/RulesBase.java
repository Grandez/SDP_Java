package com.jgg.sdp.rules.components;

import java.util.*;

import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.module.work.CommentLine;
import com.jgg.sdp.parser.symbols.SDPSymbol;
import com.jgg.sdp.rules.objects.*;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

public class RulesBase {

	protected RulesProcessor rulesProcessor = RulesProcessor.getInstance(false);
	
	public RulesBase() {
//		rulesProcessor.init();
	}
	
	public List<Issue> getIssues() {
		return rulesProcessor.getIssues();
	}

	/***************************************************************/
	/* Reglas comunes o que se resuelven en componentes genericos  */
	/***************************************************************/

	public void checkComment(CommentLine comment) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(comment.getLine());
        obj.setComponent(comment);

        rulesProcessor.processItemByName(ITEM_COMMENT, obj);
	}
	
	public void checkTab(SDPSymbol s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.line);
        obj.setBegColumn(s.column);
        obj.setComponent(s);

        rulesProcessor.processItemByName(ITEM_TAB, obj);        
	}

	public void checkTabsInText(SDPSymbol s) {
		if (s.value.indexOf('\t') != -1) {
			checkTab(new SDPSymbol(-2, s.line, s.column + s.value.indexOf('\t'), "\t"));
		}
	}
	
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
		
}
