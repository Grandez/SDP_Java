package com.jgg.sdp.parser.rules;

import com.jgg.sdp.rules.components.RulesBase;
import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

import com.jgg.sdp.parser.stmt.StmtSQL;

public class RulesSQL extends RulesBase {

	public void checkSQL(StmtSQL sql) {
        RuleObject obj = new RuleObject(sql);
        rulesProcessor.processGroupByName(GROUP_DB2, obj);        
	}
}
