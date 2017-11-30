package com.jgg.sdp.rules.components;

import java.util.*;

import com.jgg.sdp.blocks.symbols.SymbolExt;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.module.work.CommentLine;
import com.jgg.sdp.rules.objects.*;

import java_cup.runtime.Symbol;

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
	
	public void checkTab(SymbolExt s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.left);
        obj.setBegColumn(s.right);
        obj.setComponent(s);

        rulesProcessor.processItemByName(ITEM_TAB, obj);        
	}

	public void checkTabsInText(SymbolExt s) {
		String txt = (String) ((Symbol) s.value).value;
		if (txt.indexOf('\t') != -1) {
			SymbolExt e = new SymbolExt(new Symbol(-2, s.left, s.right + txt.indexOf('\t'), "\t"));
			checkTab(e);
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
