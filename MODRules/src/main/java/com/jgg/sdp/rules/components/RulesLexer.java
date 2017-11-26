package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

public class RulesLexer extends RulesBase {

	/***************************************************************/
	/* Reglas a nivel lexer                                        */
	/***************************************************************/
	
		
	public void checkNoPrintable(int line, int column) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setBegColumn(column);
        obj.setComponent(true);
        rulesProcessor.processItemByName(ITEM_HEXDATA, obj);
	}

	public void checkCompileDirective(int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(true);
        rulesProcessor.processItemByName(ITEM_CBL_DIRECTIVE, obj);		
	}
	
	public void checkPrintDirective(String directive, int line) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(line);
        obj.setComponent(directive);
        rulesProcessor.processItemByName(ITEM_CBL_LIST_DIR, obj);		
	}
		
}
