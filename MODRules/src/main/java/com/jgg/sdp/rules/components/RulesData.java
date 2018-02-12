package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.parser.symbols.SDPSymbol;

public class RulesData extends RulesLexer {
	
	public void checkIdentification(Object stmt) {
        RuleObject obj = new RuleObject(stmt);
        rulesProcessor.processGroupByName(GROUP_DIV_ID, obj);        
	}

	public void checkCompileDirective(SDPSymbol s) {
        RuleObject obj = new RuleObject(s);
        rulesProcessor.processItemByName(ITEM_CBL_DIRECTIVE, obj);		
	}
	
	public void checkIdentificationVerbEndP(SDPSymbol s, SDPSymbol point) {
		RuleObject obj = (point != null) ? new RuleObject(point)
				                         : new RuleObject(s);
        if (point == null) obj.setComponent(null);
        rulesProcessor.processItemByName(ITEM_POINT, obj);
	}
	public void checkProgramIDEndP(Object o, Object point) {
//		System.out.println("Parar");
	}
	public void checkProgramType(SDPSymbol s) {
//		System.out.println("Parar");
	}
}
