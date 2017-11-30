package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.blocks.symbols.SymbolExt;

public class RulesLexer extends RulesBase {

	/***************************************************************/
	/* Reglas a nivel lexer                                        */
	/***************************************************************/
	
		
	public void checkNoPrintable(SymbolExt s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.left);
        obj.setBegColumn(s.right);
        obj.setComponent(s);
        rulesProcessor.processItemByName(ITEM_HEXDATA, obj);
	}
	
	public void checkPrintDirective(SymbolExt s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.left);
        obj.setComponent(s);
        rulesProcessor.processItemByName(ITEM_CBL_LIST_DIR, obj);		
	}
		
}
