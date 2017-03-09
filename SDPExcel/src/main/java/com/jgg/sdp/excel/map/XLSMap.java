package com.jgg.sdp.excel.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.jgg.sdp.excel.xls.XLSDictionary;

public class XLSMap {

	private String name;
	private String table;
	private boolean isFull = false;
	
	private HashMap<String, XLSMap>   maps   = new HashMap<String, XLSMap>();
	private HashMap<String, XLSField> fields = new HashMap<String, XLSField>();
    private ArrayList<XSSFCell> cells = new ArrayList<XSSFCell>();
	private ArrayList<Object> parms = new ArrayList<Object>();
	private XLSDictionary dictionary = XLSDictionary.getInstance();
	
	public XLSMap(String name) {
	   this.name = name;	
	   this.table = name.toUpperCase();
	   String t = dictionary.get(name);
       if (t != null) table = t.toUpperCase();
	}
	
	public XLSMap addMap(String name) {
		XLSMap m = maps.get(name);
		if (m == null) {
			m = new XLSMap(name);
			maps.put(name, m);
		}
		return m;
	}
	
	public XLSMap getMap(String name) {
		return maps.get(name);
	}

	public void removeMap(String name) {
		maps.remove(name);
	}
	
	public ArrayList<XLSMap> getMapList() {
		return new ArrayList<XLSMap>(maps.values());
	}
	
	public XLSField addField(XSSFCell cell, String base, String field, String[] parms) {
		XLSField f = getField(field);
		if (f == null) {
			f = new XLSField(name, base, field); 
		}
		f.addCell(cell, parms);
		fields.put(field, f);
		return f;
	}
	
	public XLSField getField(String name) {
		return fields.get(name);
	}
	
	public ArrayList<XLSField> getFields() {
		return new ArrayList<XLSField>(fields.values());
	}
	
	public void removeField(String name) {
		fields.remove(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	public String getTable() {
		return table;
	}
	
	public void setFull(boolean full) {
		isFull = full;
	}
	
	public boolean isFull() {
		return isFull;
	}
	
	public void addCell(XSSFCell cell) {
		cells.add(cell);
	}
	
	public ArrayList<XSSFCell> getCells() {
		return cells;
	}
	
	public void setParameter(Object parm) {
		parms.add(parm);
	}
	
	public Integer getParameterAsInteger(int order) {
		if (order >= parms.size()) return null;
		return (Integer) parms.get(order);
	}
	
}
