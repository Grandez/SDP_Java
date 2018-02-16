package com.jgg.sdp.rules.components;

import com.jgg.sdp.rules.objects.RuleObject;
import com.jgg.sdp.tools.Cadena;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.blocks.stmt.Statement;
import com.jgg.sdp.parser.symbols.SDPSymbol;

public class RulesData extends RulesLexer {
	
	@SuppressWarnings("rawtypes")
	public void checkDivision(Statement stmt) {
        RuleObject obj   = new RuleObject(stmt);
        String     group = Cadena.getWord(stmt.getVerbName(),0);
        rulesProcessor.processGroupByName(group, obj);        		
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
