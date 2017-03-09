package com.jgg.sdp.excel.map;

import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class XLSField {
	
    private String base;
	private String field;
	private String dbTable;
	private String tblField;
	private boolean unique = false;
    
     private ArrayList<XLSCell> cells = new ArrayList<XLSCell>();

     public XLSField(String table, String base, String field) {
    	 this.dbTable = table;
    	 this.field = field;
    	 this.tblField = field;
    	 this.base  = base;
     }
     
     public XLSField(String name,  XSSFCell cell) {
    	 this.field = name;
    	 this.tblField = name;
    	 cells.add(new XLSCell(cell));
     }
     
     public XSSFCell addCell(XSSFCell cell, String[] parms) {
    	 cells.add(new XLSCell(cell, parms));
    	 return cell;
     }
     
     public ArrayList<XLSCell> getCells() {
    	 return cells;
     }
     
     public String getFieldName() {
    	 return field;
     }

     public String getBaseName() {
    	 return base;
     }
     
     public String getQualifiedName() {
    	 return dbTable + "." + tblField;
     }
     
     public void setTableField(String name) {
    	 tblField = name;
     }
     
     public String getTableField() {
    	 return tblField;
     }
     
     public void setUnique() {
    	 unique = true;
     }
     public boolean isUnique() {
    	 return unique;
     }
     
}
