package com.jgg.sdp.parser.base;

import java.util.ArrayList;
import java.util.HashMap;

import com.jgg.sdp.parser.base.stmt.Option;
import com.jgg.sdp.parser.base.stmt.Statement;

import java_cup.runtime.Symbol;

public class StmtCICS  extends Statement<StmtCICS> {

		private String fullVerb;
		private int    qrType;
		private ArrayList<Option>       parms = new ArrayList<Option>();
		private HashMap<String, Option> map   = new HashMap<String, Option>();

		public StmtCICS(Symbol verb) {
			super(verb);
			this.fullVerb = (String) verb.value;
		}
		
		public void setQRType(int type) {
			qrType = type;
		}
		
		public int getQRType() {
			return qrType;
		}
		
	    public Option addParm(Option parm) {
	    	parms.add(parm);
	    	map.put(parm.getName(), parm);
	    	return parm;
	    }

		public void setFullVerb(String fullVerb) {
			this.fullVerb = fullVerb;
		}
		
		public String getFullVerb() {
			return fullVerb;
		}
		
		public ArrayList<Option> getParms() {
			return parms;
		}

		public void setParms(ArrayList<Option> parms) {
			this.parms = parms;
			if (parms == null) return;
			for (Option prm : parms) {
				map.put(prm.getName(), prm);
			}
		}

		/* 
		 * Devuelve todas las opciones concatenadas con underscore
		 * junto con el primer parametro
		 * por ejemplo:  WEB STARTBROWSE FORMFIELD [(data_area)]
		 *     devuelve: WEB_STARTBROWSE_FORMFIELD
		 */
		public String getFullVerbs() {
			StringBuilder full = new StringBuilder(getVerbName());
			for (Option parm : parms) {
				if (parm.isParm()) break;
				full.append('_');
				full.append(parm.getName());
			}
			return full.toString();
		}
		
		public Option getParameter(String name) {
			return map.get(name);
		}
		
		public boolean hasOption(String option) {
			return (map.get(option) != null);
		}
	}
