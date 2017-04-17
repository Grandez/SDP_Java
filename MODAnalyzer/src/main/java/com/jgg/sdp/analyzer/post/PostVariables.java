package com.jgg.sdp.analyzer.post;

import com.jgg.sdp.module.base.Module;

public class PostVariables {

	public void parse(Module module) {
		module.getSummary().setMemory(module.getTBVars().calculateMemorySize());
		updateReadWrite(module);
		
/*		
		for (Variable v : module.getVariables()) {
			if (v.isDirectWritten()) module.addIssue(new Issue(ISSUE.WRITE, v.getLine(), v.getColumn()));
			if (!v.isUsed())         {
				int i = (v.getLevel() == 01 || v.getLevel() == 77) ? ISSUE.ROOT_NOT_USED : ISSUE.NOT_USED; 
				module.addIssue(new Issue(i, v.getLine(), v.getColumn()));			
			}
		}
	*/	
	}
	
	private void updateReadWrite(Module module) {
		
	}
}
