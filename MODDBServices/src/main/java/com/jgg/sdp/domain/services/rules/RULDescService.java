package com.jgg.sdp.domain.services.rules;

import java.util.HashMap;

import com.jgg.sdp.core.ctes.SYS;
import com.jgg.sdp.domain.rules.RULDesc;
import com.jgg.sdp.domain.services.AbstractService;

public class RULDescService extends AbstractService<RULDesc> {

	private String lang = null;
	private String dialect = null;
	
	private HashMap<Integer, String> msgs = new HashMap<Integer, String>();
	
	public void setLanguage(String lang) {
		this.lang = lang;
	}
	public void setDialed(String dialect) {
		this.dialect = dialect;
	}
	
	public String getIssueDecription(Integer code) {
		RULDesc desc = null;
		
		String msg = msgs.get(code);
		if (msg != null) return msg;
		if (lang != null) {
			if (dialect == null) dialect = lang;
			desc = findQuery (RULDesc.getDescription, code, lang, dialect);	
		}
		if (desc == null) {
			desc = findQuery (RULDesc.getDescription, code, SYS.DEF_LANG, SYS.DEF_LANG);
		}
		return (desc == null) ? "N/A" : desc.getMsg();
	}
	
    public void clean() {
        sqlExecute("DELETE FROM RUL_DESC");
    }

}
