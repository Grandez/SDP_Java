package com.jgg.sdp.rules.components;

import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.rules.objects.RuleObject;

import static com.jgg.sdp.rules.ctes.CDGItems.*;

public class RulesModule extends RulesBase {

	public void checkModule(Module module) {
		checkIssues(module);
	}
	
	private void checkIssues(Module module) {
        RuleObject obj = new RuleObject();
        
        obj.setBegLine(0);
        obj.setBegColumn(0);
        obj.setComponent(module.getSumIssues());

        rulesProcessor.processGroupByName(GROUP_ISSUES, obj);
        
	}
	
}
