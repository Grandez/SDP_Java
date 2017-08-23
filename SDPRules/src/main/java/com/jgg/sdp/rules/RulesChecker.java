package com.jgg.sdp.rules;

import java.util.*;

import com.jgg.sdp.tools.Cadena;
import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.tools.JGGJava;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.rules.items.RuleGroup;
import com.jgg.sdp.rules.items.RuleItem;
import com.jgg.sdp.rules.items.RuleObject;
import com.jgg.sdp.rules.items.RuleRule;
import com.jgg.sdp.rules.items.RulesTree;

public class RulesChecker {

	private final int MASK_EQ = 1;
	private final int MASK_LT = 2;
	private final int MASK_GT = 4;
	
	// Al modulo se pasan al final
	// Los issues del lexer pueden que no sepan el modulo
	private ArrayList<Issue> issues = new ArrayList<Issue>();
	
	private RulesTree tree = RulesTree.getInstance();
	
	public RulesChecker() {
		
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
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setBegColumn(column);
        obj.setComponent("\t");

        processRuleByName(RULES.GRP_LEXER, "TAB", obj);        
	}

	public void checkComment(String text, int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(text);

        processRuleByName(RULES.GRP_COMMENT, "CMT", obj);
	}
	
	public void checkNoPrintable(int line, int column) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setBegColumn(column);
        obj.setComponent("");
        processRuleByName(RULES.GRP_LEXER, "HEX", obj);
	}

	public void checkCompileDirective(int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent("CBL");
        processRuleByName(RULES.GRP_DIRECTIVES, "CBL", obj);		
	}
	
	public void checkPrintDirective(String directive, int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(directive);
        processRuleByName(RULES.GRP_DIRECTIVES, directive, obj);		
	}
	
	private RuleItem getRuleItemByName(int group, String name) {
        RuleGroup grp = tree.getGroupById(group);
        if (grp == null) return null;
        
        RuleItem item = grp.getItemByName(name);
        return item;
	}
	
    private void processRuleByName(int group, String name, RuleObject obj) {
    	RuleItem item = getRuleItemByName(group, name);
    	if (item != null) processItem(item, obj);
    }
    
	private void processItem(RuleItem item, RuleObject obj) {
		for (RuleRule rule : item.getRules()) {
			if (processRule(rule, obj)) {
				applyIssue(rule, obj);
				if (rule.getPriority() > 0) return;
			}
		}		
	}
	
	private boolean processRule(RuleRule rule, RuleObject obj) {
		switch (rule.getType()) {
		    case RULES.TYPE_VERB:   return processRuleValue(rule, obj);
		    case RULES.TYPE_METHOD: return processRuleMethod(rule, obj);
		}
		return true;
	}
	
	private boolean processRuleValue(RuleRule rule, RuleObject obj) {
		boolean res = false;
		
		switch (rule.getComparator() % 100) {
		   case RULES.OP_EXIST: return checkExist(rule, obj);
		   case RULES.OP_START: return checkTypeStart(rule, obj);           
		}
		return res;
	}
	
	private boolean processRuleMethod(RuleRule rule, RuleObject obj) {
		
		String method = rule.getProperty();
		method = "get" + method.substring(0,1).toUpperCase() + method.substring(1);
		Object result = JGGJava.executeMethod(obj, method);
		if (result instanceof Integer) return  matchInteger((Integer) result, rule);
		return false;
	}
	
	/**
	 * Realmente existe, miramos si esta prohibido
	 * @param rule
	 * @param obj
	 * @return
	 */
	private boolean checkExist(RuleRule rule, RuleObject obj) {
		return (rule.getComparator() > 100);
	}
	
	/**
	 * Chequea si el objeto como cadena empieza con una subcadena
	 * @param rule La regla
	 * @param obj  El objeto asociado
	 * @return false si cierto
	 *         true  error
	 */
	private boolean checkTypeStart(RuleRule rule, RuleObject obj) {
		String value = obj.getObjectAsString();
		String target = rule.getValor();
		String src = Cadena.left(value, target.length());
		if (src == null) return true;
		
		//  Tabla de verdad
		//  Condicion Negada Issue
		//     F        F     T
		//     F        T     F
		//     T        F     F
		//     T        T     T
        //     XOR
		
		return (src.compareToIgnoreCase(target) == 0 ^ rule.getComparator() > 100);
			   
	}

	private boolean matchInteger (Integer source, RuleRule rule) {
		return compareInteger(source, rule.getValor(), rule.getComparator());
	}
	
	private boolean compareInteger(int source, String target, int comp) {
		int aux = source - Integer.parseInt(target);
		int res = (aux == 0) ? 0 : aux / Math.abs(aux);
		int mask = 0;

		switch (res) {
		   case  0: mask = MASK_EQ; break;
		   case  1: mask = MASK_LT; break;
		   default: mask = MASK_GT;
		}
		
		// Cuidado con el XOR
		return !(((mask & (comp % 100)) > 0) ^ comp > 100);
	}
	
	private void applyIssue(RuleRule rule, RuleObject obj) {
		Issue issue = new Issue(rule.getIdGroup(), rule.getIdItem(), rule.getIdRule());
		issue.setBegLine(obj.getBegLine());
		issue.setBegColumn(obj.getBegColumn());
		issue.setEndLine(obj.getEndLine());
		issue.setEndColumn(obj.getEndColumn());
		issue.setSeverity(rule.getSeverity());
		issue.setBloque(obj.getBloque());
		issue.setFirma(Firma.createDefault());
		issues.add(issue);
	}
	
}
