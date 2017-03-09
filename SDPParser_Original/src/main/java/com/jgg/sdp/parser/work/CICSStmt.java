package com.jgg.sdp.parser.work;

import java.util.ArrayList;
import java.util.HashMap;

public class CICSStmt {

	private String verb;
	private String fullVerb;
	private int    qrType;
	private ArrayList<CICSParm>       parms = new ArrayList<CICSParm>();
	private HashMap<String, CICSParm> map   = new HashMap<String, CICSParm>();

	public CICSStmt(String verb) {
		this.verb = verb;
		this.fullVerb = verb;
	}
	
	public void setQRType(int type) {
		qrType = type;
	}
	
	public int getQRType() {
		return qrType;
	}
	
    public CICSParm addParm(CICSParm parm) {
    	parms.add(parm);
    	map.put(parm.getName(), parm);
    	return parm;
    }

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public void setFullVerb(String fullVerb) {
		this.fullVerb = fullVerb;
	}
	
	public String getFullVerb() {
		return fullVerb;
	}
	
	public ArrayList<CICSParm> getParms() {
		return parms;
	}

	public void setParms(ArrayList<CICSParm> parms) {
		this.parms = parms;
		if (parms == null) return;
		for (CICSParm prm : parms) {
			map.put(prm.getName(), prm);
		}
	}

	public String getVerb() {
		return verb;
	}
	
	/* 
	 * Devuelve todas las opciones concatenadas con underscore
	 * junto con el primer parametro
	 * por ejemplo:  WEB STARTBROWSE FORMFIELD [(data_area)]
	 *     devuelve: WEB_STARTBROWSE_FORMFIELD
	 */
	public String getFullVerbs() {
		StringBuilder full = new StringBuilder(getVerb());
		for (CICSParm parm : parms) {
			if (parm.isParm()) break;
			full.append('_');
			full.append(parm.getName());
		}
		return full.toString();
	}
	
	public CICSParm getParameter(String name) {
		return map.get(name);
	}
	
	public boolean hasOption(String option) {
		return (map.get(option) != null);
	}
}
