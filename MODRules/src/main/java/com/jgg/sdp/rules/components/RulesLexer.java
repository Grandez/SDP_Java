package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.parser.symbols.SDPSymbol;

public class RulesLexer extends RulesBase {

	/***************************************************************/
	/* Reglas a nivel lexer                                        */
	/***************************************************************/
	
		
	public void checkNoPrintable(SDPSymbol s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.line);
        obj.setBegColumn(s.column);
        obj.setComponent(s);
        rulesProcessor.processItemByName(ITEM_HEXDATA, obj);
	}
	
	public void checkPrintDirective(SDPSymbol s) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(s.line);
        obj.setComponent(s);
        rulesProcessor.processItemByName(ITEM_CBL_LIST_DIR, obj);		
	}
		
}
