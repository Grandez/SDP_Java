package com.jgg.sdp.excel.xls;

import java.util.Properties;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.excel.ctes.XLS;

public class XLSDictionary {

	private static XLSDictionary d = null;
	private        Properties dict = new Properties();
	
	private XLSDictionary() {
	    try {
		   dict.load(getClass().getClassLoader().getResourceAsStream(XLS.DICTIONARY));
        }
        catch (Exception e) {
        	throw new SDPException(e, MSG.FATAL_EXCEPTION, XLS.DICTIONARY);
        }
	}
	
	public static XLSDictionary getInstance() {
		if (d == null) d = new XLSDictionary();
		return d;
	}
	
	public String get(String key) {
		return dict.getProperty(key); 
	}
	
	public String getValue(String key) {
		String value = dict.getProperty(key);
		return (value == null) ? key : value;
	}
	
	public void add(String key, String value) {
		dict.put(key, value);
	}
}
