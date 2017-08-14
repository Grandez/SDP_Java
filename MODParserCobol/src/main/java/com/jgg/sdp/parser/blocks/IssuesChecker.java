package com.jgg.sdp.parser.blocks;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.parser.stmt.Statement;
import com.jgg.sdp.rules.*;

import java_cup.runtime.Symbol;

public class IssuesChecker {

	private Module module;
	private RulesChecker rules = RulesChecker.getInstance();
	
	public IssuesChecker(Module module) {
		this.module = module;
//		rules = new RulesChecker(module);
	}
	
	public void checkDivision(BlockCode block) {
	    
	}
	public void checkIdentification(BlockCode block) {
		/*
		if (!rules.setGroup(block.getName())) return;
		for (RuleItem item : rules.getItemsByGroup(block.getName())) {
 			Statement stmt = block.getStatement(item.getKeyTxt());
			for (RuleIssue issue : item.getRuleIssues()) {
			    switch (issue.getType()) {
			       case RULES.TYPE_VERB: processVerb(issue, block, stmt); break;
			    }
			}
		}
		*/	
	}
	
    private void processVerb(RuleRule issue, BlockCode block, Statement stmt) {
		switch (issue.getComparator()) {
		   case RULES.MISSING: 
			    if (stmt == null) createIssue(issue, block.getHeader());
			    break;
		   case RULES.EXIST: 
			    if (stmt != null)  	createIssue(issue, stmt, block.getHeaderName());
			    break;

		}

    }
    
	private void createIssue(RuleRule rule, Symbol sym) {
/*		
    	Issue i = new Issue(rule.getIdIssue());
    	i.setBegLine(sym.left);
    	i.setBegColumn(sym.right);
    	i.setEndLine(sym.left);
    	i.setEndColumn(sym.right);
    	i.setBloque((String) sym.value);
    	i.setFirma(Firma.createDefault());
        module.addIssue(i);
*/        
	}
	
	private void createIssue(RuleRule rule, Statement stmt, String bloque) {
/*		
    	Issue i = new Issue(rule.getIdIssue());
    	i.setBegLine(stmt.getBegLine());
    	i.setBegColumn(stmt.getBegColumn());
    	i.setEndLine(stmt.getEndLine());
    	i.setEndColumn(stmt.getEndColumn());
    	i.setBloque(bloque);
    	i.setFirma(stmt.getFirma());
        module.addIssue(i);
*/        
	}

}
