package com.jgg.sdp.excel.map;

import org.apache.poi.xssf.usermodel.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.domain.services.base.*;
import com.jgg.sdp.excel.data.*;
import com.jgg.sdp.excel.xls.*;
import com.jgg.sdp.tools.Numero;

public class XLSMapper {

    private XLSTools   tools      = new XLSTools();
    
    private CFGTranslateService xlateService = new CFGTranslateService();
    private Configuration cfg = Configuration.getInstance();
    
	private Long   idVersion  = 0L;

	private XLSData data = new XLSData();
	private XLSMap currentMap = null;

	public void map(String moduleName, XLSMap mapBase, long idVersion) {
		XLSMap root = mapBase;	
		this.idVersion  = idVersion;
		
		removeTemporal(root.getMap("TOOLS"));
		
		for (XLSMap map : root.getMapList()) {
			    currentMap = map;
			    System.out.println("Mapa " + map.getName());
			    processMap();   
		}
	}
	
	private void removeTemporal(XLSMap tools) {
		XSSFRow row;
		XSSFCell cell;
		
		if (tools == null) return;
		
		XLSField field = tools.getField("TMP");
		if (field == null) return;
		
		for (XLSCell celda : field.getCells()) {
			cell = celda.getCell();
			row = cell.getRow();
			row.removeCell(cell);
		}
		tools.removeField("TMP");
	}
	
	private void processMap() {
		data.open(currentMap, idVersion);
	    processList();
		data.close();	
		
	}
	
	private void processList() {
		boolean datos = false;
		int row = 0;
		while (data.next()) {
		   datos = true;
		   processFieldsList(row);	
		   row++;
		}
		if (datos == false) removeData();
	}

	private void processFieldsList(int row) {
		XLSCell  celda  = null;
		Object   value = null;
		Object   newValue = null;
		
		for (XLSField field : currentMap.getFields()) {
			if (field.isUnique() && row != 0) continue;
			value = data.getValue(field);
			
			if (value == null) continue;
			
            newValue = processValue(field, value);
            
			for (XLSCell cell : field.getCells()) {
				celda = cell; // Se debe clonar
			    celda = tools.cloneCell(row, cell);
			    applyNewValue(field, celda, newValue.toString(), row);
			}
		}
	}
	
	private void removeData() {
		XSSFCell cellXls   = null;
		StringBuilder aux = new StringBuilder();
		String   empty = null;
		
		aux.append(cfg.getString(CFG.EXCEL_PREFIX));
		aux.append(cfg.getString(CFG.EXCEL_DELIMITER));
		aux.append(cfg.getString(CFG.EXCEL_EMPTY));
		aux.append("()");
		
		empty =  aux.toString();
		
		for (XLSField field : currentMap.getFields()) {
			for (XLSCell cell : field.getCells()) {
				cellXls = cell.getCell();
				cellXls.setCellFormula(empty);
			}
		}
	}
	
	private void applyNewValue(XLSField field, XLSCell cell, String value, int row) {
	    boolean isNumber = Numero.isANumber(value);
	    String prfx = cfg.getString(CFG.EXCEL_PREFIX);
	    String delim = cfg.getString(CFG.EXCEL_DELIMITER);
		String mask = prfx + delim + field.getBaseName() + delim + field.getFieldName() + "(";
		String formula = cell.getCell().toString();
		StringBuilder newFormula = new StringBuilder();
		
		int pos = formula.indexOf(mask);
		int beg = 0;
		int end  = 0;
		int begFormula = 0;
		
		while(pos != -1) {
			beg = pos + mask.length();
			end = getEndOfParm(formula, beg);
		    newFormula.append(formula.substring(begFormula, beg));
		    if (isNumber) {
		    	newFormula.append(value);
		    }
		    else {
		    	newFormula.append('"' + value + '"');
		    }
		    begFormula = beg;
		    pos = formula.indexOf(mask, end); 
		}
		newFormula.append(formula.substring(end));
		
		cell.getCell().setCellFormula(applySdpRow(newFormula.toString(), row));		
	}
	
	private String applySdpRow(String formula, int row) {
		if (row == 0) return formula;
		
		String mask = "SDP_ROW(";
        int    maskLen = mask.length();
        
		StringBuilder newFormula = new StringBuilder();

		int beg = 0;
		int end  = 0;
		int begFormula = 0;
		int pos = formula.indexOf(mask);
		
		while(pos != -1) {
			beg = pos;
			end = getEndOfParm(formula, beg + maskLen);
			
			
		    newFormula.append(formula.substring(begFormula, beg + maskLen));
		    newFormula.append(applyOffset(formula.substring(pos + maskLen, end),row));
		    begFormula = beg;
		    pos = formula.indexOf(mask, end); 
		}
		
		newFormula.append(formula.substring(end));
		return newFormula.toString();		
	}

	// Incrementa la fila de la celda
	// Por si acaso es de la forma SDP_ROW(Hoja!Celda) lo procesamos a la inversa
	private String applyOffset(String cell, int row) {
		int pos = cell.length() - 1;
		int orden = 0;
		int value = 0;
		
		while (Character.isDigit(cell.charAt(pos))) {
			value += Integer.parseInt(String.valueOf(cell.charAt(pos))) * (Math.pow(10, orden));
			pos--;
			orden++;
		}
		value += row;
		pos++;
		
		return cell.substring(0, pos) + value;
	}
	
	/**
	 * Devuelve la posicion correcta de fin del parametro
	 * Por ejemplo: SDP_XXX_YYY()        Devuelve la posicion de )
	 *              SDP_XXX_YYY('A')     Devuelve la posicion de )
	 *              SDP_XXX_YYY(1)       Devuelve la posicion de )
	 *              SDP_XXX_YYY(1,falso) Devuelve la posicion de ,
	 *              F(SDP_XXX_YYY(),33)  Devuelve la posicion de )
	 * @param formula
	 * @param beg
	 * @return
	 */
	private int getEndOfParm(String formula, int beg) {
	    int endParm = formula.indexOf(",", beg);
	    int endFunc = formula.indexOf(")", beg);
	    if (endParm == -1) return endFunc;
	    return endParm > endFunc ? endFunc : endParm; 
	}

	private Object processValue(XLSField field, Object data) {		
		
		String trans = null;
		String tableField = null;
		
		// Solo se traducen codigos
		// Los codigos siempre estan en el orden 0-255
		if (Numero.isInteger(data) && Numero.makeInteger(data) < 256) {
			tableField = currentMap.getTable() + "." + field.getTableField();
			trans = xlateService.getTranslation(tableField, Numero.makeInteger(data), "ES");
		}
	    
        if (trans == null) return data;
        return data.toString() + cfg.getString(CFG.EXCEL_CONCATENATE) + trans;

	}

}
