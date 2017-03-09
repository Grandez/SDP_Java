package com.jgg.sdp.excel.map;

import java.io.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;

import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.exceptions.SDPException;
import com.jgg.sdp.excel.ctes.XLS;

public class XLSLoader {

    private InputStream  XLSMaster = null;
    private XSSFWorkbook wb        = null;
    private XLSMap       root      = new XLSMap("root");

    public XLSLoader () {
    	loadMasterFile(XLS.MASTER);
    }

    public XLSLoader (String template) {
    	loadMasterFile(template);
    }

    public XSSFWorkbook getWorkBook() {
    	return wb;
    }

    public XLSMap getMap() {
    	return root;
    }
    
	public  void loadMasterFile(String template) {
		
		XLSMaster = this.getClass().getClassLoader().getResourceAsStream(template);
	
		try {
		   wb = new XSSFWorkbook(OPCPackage.open(XLSMaster));
		}
		catch (Exception e) {
			throw new SDPException(e, MSG.FATAL_EXCEPTION, XLS.MASTER);
		}
		loadMap();
	}
	
	private void loadMap() {
		XSSFSheet sh = null;
		
		for (int hoja = 0; hoja < wb.getNumberOfSheets(); hoja++) {
			sh = wb.getSheetAt(hoja);
            for (int fila = 0; fila <= sh.getLastRowNum(); fila++) {
                 loadRow(sh, sh.getRow(fila));
		    }
		}
		setGlobalParameters();
	}
	
	/**
	 * Tratamiento de niveles
	 */
	private void setGlobalParameters() {
		XLSMap map = root.getMap("CALLED");
		if (map != null) setMaxLevel(map, 1);
		map = root.getMap("TREE");
		if (map != null) setMaxLevel(map, Integer.MAX_VALUE - 1);
		XLSField field = map.getField("MAX_LEVEL");
		if (field != null) field.setUnique();
	}
	
	private void setMaxLevel(XLSMap map, int defaultLevel) {
		String[] parms = null;
		int curLevel = 0;
		int level = defaultLevel;
		
		XLSField field = map.getField("NAME");
		if (field == null) return;
		for (XLSCell cell : field.getCells()) {
			parms = cell.getParameters();
			if (parms != null && parms.length > 1) {
				try {
				   curLevel = Integer.parseInt(parms[1]);
				   if (curLevel > level) level = curLevel;
				}   
				catch (NumberFormatException e) {
					// El parametro no es un numero
				}
			}
		}
		map.setParameter(level);
	}
	
	private void loadRow(XSSFSheet sh, XSSFRow row) {
		if (row == null) return;
		XSSFCell cell = null;
		for (int idx = 0; idx < row.getLastCellNum(); idx++) {
             cell = row.getCell(idx);
             if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            	 processValue(cell);
             }
		}
		
	}
	
	private void processValue(XSSFCell cell) {
		int beg = 0;
		int end = 0;
		StringBuilder field = new StringBuilder();
		
		XLSMap table = root;
		XLSMap temp = root;
		
		String value = cell.toString().toUpperCase();
		
		if (value.contains("SDP_") == false) return;
		
		
		beg = value.indexOf("SDP_", beg);
		while (beg > -1) {
			end = value.indexOf("(", beg);
			String[] parms = getParameters(value, end);
			String[] toks = value.substring(beg, end).split("_");
			if (toks.length >= 3) {
				temp = addMap(table, toks[1]);
		        for (int idx = 2; idx < toks.length; idx++) {
		        	field.append(toks[idx]);
		        	field.append('_');
		        }
			    temp.addField(cell, toks[1], field.substring(0, field.length() - 1), parms);
			}    
			
			if (toks.length == 2) {
				temp = addMap(table, toks[1]);
				temp.addCell(cell);
			    temp.addField(cell, toks[1], toks[1], parms);
			}
			
			beg = value.indexOf("SDP_", beg + 4);
	   }
		
	}

	private String[] getParameters(String value, int beg) {
		int end = value.indexOf(')', beg);
		String parms = value.substring(beg + 1, end);
		return parms.split(",");
	}
	
	private XLSMap addMap(XLSMap parent, String map) {
        XLSMap temp = parent.getMap(map);
        if (temp == null) temp = parent.addMap(map);
        return temp;
	}
	
}

