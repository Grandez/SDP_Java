package com.jgg.sdp.parser.code;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.ISSUE;
import com.jgg.sdp.module.base.Module;
import com.jgg.sdp.module.items.Copy;
import com.jgg.sdp.module.items.Issue;
import com.jgg.sdp.parser.base.ParserInfo;
import com.jgg.sdp.parser.info.*;

import com.jgg.sdp.parser.lang.COPYSym;
import com.jgg.sdp.parser.work.SymbolExt;

public class COPYCode {

	private Module          module = null;
	private ParserInfo      info   = ParserInfo.getInstance();
	
    public COPYCode(Module module) {
	   this.module = module;
	}

    public void processCopy(StmtCopy copy) {
    	
    	addDependence(copy);
    	//processIssue();
    	info.addCopy(copy);
    }

    private void addDependence(StmtCopy copy) {
    	String name = null;
    	int    type    = COPYSym.CPY_COPY;
    	int    subtype = COPYSym.CPY_COPY;
    	    	
        SymbolExt sym = copy.getRValue(0);
        name = sym.getName();
        switch (sym.sym) {
           case COPYSym.CPY_COPY:    type = CDG.DEP_COPY; break;
           case COPYSym.CPY_INCLUDE: type = CDG.DEP_INCLUDE; break;
        }
        switch(info.getSection()) {
            case CDG.SECT_ID: break;
            case CDG.SECT_ENV: break; 
        }
//        module.addCopy(new Copy(name, type, subtype));
    }

    private void checkIssues(Statement copy) {
    	Option opt = null;
    	opt = copy.getOption(COPYSym.CPY_SUPPRESS);
/*    	
    	if (opt != null) {
    		issues.addIssue(createIssue(ISSUE.CPY_SUPRESS, copy));
    	}
    	opt = copy.getOption(COPYSym.CPY_REPLACING);
    	if (opt != null) {
    		issues.addIssue(createIssue(ISSUE.CPY_REPLACING, copy));
    	}
*/
    }
 /*   
    private Issue createIssue(int id, Statement copy) {
    	Issue issue = new Issue(id);
    	issue.setBegLine(copy.getFirstLine());
    	issue.setBegColumn(copy.getFirstColumn());
    	issue.setLastLine(copy.getLastLine());
    	issue.setLastColumn(copy.getLastColumn());
        return issue;
    }
   */ 
}
