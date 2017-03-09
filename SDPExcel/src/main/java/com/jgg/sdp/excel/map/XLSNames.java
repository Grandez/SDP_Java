package com.jgg.sdp.excel.map;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFName;

public class XLSNames {

	private HashMap<String, XSSFName> tbNames = new HashMap<String, XSSFName>();
	
	public void add(XSSFName namedRange) {
		tbNames.put(namedRange.getNameName(), namedRange);
	}
	
	public XSSFName get(String name) {
		return tbNames.get(name);
	}
}
