package com.jgg.sdp.parser.rules;

import com.jgg.sdp.rules.components.RulesBase;
import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.parser.stmt.StmtCICS;

public class RulesCICS extends RulesBase {

	public void checkCICS(StmtCICS cics) {
	       RuleObject obj = new RuleObject(cics);
	       rulesProcessor.processGroupByName(GROUP_CICS, obj);        
	}
}
