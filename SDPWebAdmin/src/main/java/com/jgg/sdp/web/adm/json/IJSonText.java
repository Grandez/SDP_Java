package com.jgg.sdp.web.adm.json;

import java.util.List;

public interface IJSonText {

	public String getMessage();
	public Long   getIdMsg();
	public Long   getIdDesc();
	public void   setMessage(String msg);
	public void   setDescription(String desc);
	public List<JSonRuleCond> getActivations();
}
