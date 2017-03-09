package com.jgg.sdp.excel.map;

import java.util.*;

import org.apache.poi.xssf.usermodel.*;

public class XLSMapperFormats {

	private static XLSMapperFormats singleton = null;
	private HashMap<String, XLSFormatList> fmt = new HashMap<String,XLSFormatList>();
	
	private XLSMapperFormats() {
		
	}
	
	public static XLSMapperFormats getInstance() {
		if (singleton == null) singleton = new XLSMapperFormats();
		return singleton;
	}
	
	public void add(XSSFCell cell) {
		String name = cell.getSheet().getSheetName();
		XLSFormatList current = fmt.get(name);
		if (current == null) {
			current = new XLSFormatList();
			fmt.put(name, current);
			add(cell);
		}
//		addConditionalFormats(current, cell);
	}

	public XLSFormatList get(String nombre) {
		return fmt.get(nombre);
	}
	
	private void addConditionalFormats(XLSFormatList fmt, XSSFCell cell) {
		fmt.setSheet(cell.getSheet());
		fmt.addFormats(cell);
	}
	
	public XLSFormatList getFormatList(String name) {
		return fmt.get(name);
	}
	
	public void addFormatList(String sheet, XLSFormatList fmt) {
		this.fmt.put(sheet, fmt);
	}
}
