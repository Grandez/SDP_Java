package com.jgg.sdp.rules.components;

// import com.jgg.sdp.parser.stmt.StmtGral;
import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

public class RulesData extends RulesLexer {
	
	public void checkIdentification(Object stmt) {
	        RuleObject obj = new RuleObject();
	        
//	        obj.setBegLine(stmt.getBegLine());
//	        obj.setBegColumn(stmt.getBegColumn());
	        obj.setComponent(stmt);

	        rulesProcessor.processGroupByName(GROUP_ID, obj);        
	}


}
