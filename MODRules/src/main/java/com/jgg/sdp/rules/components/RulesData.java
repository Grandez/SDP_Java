package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;

import java_cup.runtime.Symbol;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.blocks.symbols.SymbolExt;

public class RulesData extends RulesLexer {
	
	public void checkIdentification(Object stmt) {
        RuleObject obj = new RuleObject(stmt);
        rulesProcessor.processGroupByName(GROUP_DIV_ID, obj);        
	}

	public void checkCompileDirective(SymbolExt s) {
        RuleObject obj = new RuleObject(s);
        rulesProcessor.processItemByName(ITEM_CBL_DIRECTIVE, obj);		
	}
	
	public void checkIdentificationVerbEndP(SymbolExt s, SymbolExt point) {
		RuleObject obj = (point != null) ? new RuleObject(point)
				                         : new RuleObject(s);
        if (point == null) obj.setComponent(null);
        rulesProcessor.processItemByName(ITEM_POINT, obj);
	}
	public void checkProgramIDEndP(Object o, Object point) {
//		System.out.println("Parar");
	}
	public void checkProgramType(Symbol s) {
//		System.out.println("Parar");
	}
}
