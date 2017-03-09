/**
 * Encapsula una celda de Excel
 * 
 * Se utiliza asi para poder clonar celdas adecuadamente
 * 
 * @author Javier Gonzalez Grandez
 * @version 4.0
 * @date    MAY - 2016
 * 
 */

package com.jgg.sdp.excel.map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;

import com.jgg.sdp.excel.xls.XLSTools;

public class XLSCell {

	private String[]   parms = null;
	private XSSFCell   base;
	private XSSFCell   cell;
	private int        row;
	private int        col;
	private int        type;
	private String     value;
	
	public XLSCell(XSSFCell cell) {
		this.cell = cell;
		this.base = cell;
		this.row  = cell.getRowIndex();
		this.col  = cell.getColumnIndex();
		this.type = cell.getCellType();
		this.value = XLSTools.getValueAsString(cell);
	}

	public XLSCell(XLSCell xlsCell, XSSFCell cell) {
		this.cell = cell;
		this.base = cell;
		this.row  = cell.getRowIndex();
		this.col  = cell.getColumnIndex();
		this.type = cell.getCellType();
		this.value = xlsCell.getValue();
		this.parms = xlsCell.getParameters();
		this.cell.setCellValue(xlsCell.getValue());
	}
	
	public XLSCell(XSSFCell cell, String[] parms) {
		this.cell = cell;
		this.base = cell;
		this.row  = cell.getRowIndex();
		this.col  = cell.getColumnIndex();
		this.type = cell.getCellType();
		this.value = XLSTools.getValueAsString(cell);
		this.parms = parms;
	}
	
	public XLSCell updateCell(XSSFCell newCell, int row) {

        XSSFCellStyle style = base.getSheet().getWorkbook().createCellStyle(); // the XSSFWorkbook from which you clone
        style.cloneStyleFrom(base.getCellStyle());
        newCell.setCellStyle(style);
        newCell.setCellType(Cell.CELL_TYPE_FORMULA);
        newCell.setCellFormula(value);
        
        cell = newCell;
		return this;
	}
	
	public XSSFCell getCell() {
		return cell;
	}

	public XSSFCell getCellBase() {
		return base;
	}
	
	public String[] getParameters() {
		return parms;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
