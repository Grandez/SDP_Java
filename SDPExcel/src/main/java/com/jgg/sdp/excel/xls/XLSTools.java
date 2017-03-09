package com.jgg.sdp.excel.xls;

import org.apache.poi.xssf.usermodel.*;

import com.jgg.sdp.excel.map.XLSCell;

public class XLSTools {

	private final static String COLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private final static int OTH = 0;
	private final static int COL = 1;
	private final static int ROW = 2;
	private final static int ABS = 3;
	private final static int QUO = 4;
	
	public static String getValueAsString(XSSFCell cell) {
		return cell.toString();
		/*
		System.out.println(cell.toString());
		System.out.println(cell.getRawValue());
           DataFormatter objDefaultFormat = new DataFormatter();
           FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator(cell.getSheet().getWorkbook());
           objFormulaEvaluator.evaluate(cell); 
           return objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
           */
	}

	public XLSCell cloneCell(int rowOffset, XLSCell xlsCell) {
		if (rowOffset == 0) return xlsCell;
		
		XSSFCell base = xlsCell.getCellBase();
		int fila = base.getRowIndex() + rowOffset;
		int col  = base.getColumnIndex() ;
		
		// Al loro lode clone y devolver una nueva
		
		XSSFRow row = createRow(fila, base);
		XSSFCell newCell = row.getCell(col);

		if (newCell == null) newCell = row.createCell(col);
		
		return xlsCell.updateCell(newCell, rowOffset);
         
		
		// System.out.println("Crea la celda (" + newCell.getRowIndex() + "," + newCell.getColumnIndex() + ")");
 	
		//return new XLSCell(xlsCell, newCell);
	}
	
	public XSSFRow createRow(int nRow, XSSFCell cell) {
		XSSFRow row = cell.getSheet().getRow(nRow);
		if (row != null) return row;
		
		row = cell.getSheet().createRow(nRow);
		return row;
	}

	/**
	 * Identifica si la formula tiene referencias a celdas
	 * @param formula La formula a analizar
	 * @param offset  Posicion de inicio del analisis
	 * @param begin   Salida. Posicion de inicio de la celda
	 * @param end     Salida. Posicion final de la celda
	 * @return   true si hay referencia a una celda.
	 *           begin y end se actualizan a la posicion de inicio y fin
	 */
	public static int[] getCellReference(String formula, int offset) {
		int[] rc = {-1, -1};
		char cA = 'A';
		char curC;
		int  curV;
		int start = -1;
		boolean absolute = false;

		for (int idx = offset; idx < formula.length(); idx++) {
			curC = formula.charAt(idx);
			curV = (int) curC;
			switch(charType(curC)) {
			    case QUO: 
			    	while (charType(formula.charAt(++idx)) != QUO);
			    	break;
			    case ABS:
					if (start == -1) { // No hay un $ previo
						start = idx;
						rc[1] = idx;
					}
					else {
						rc[1]++;
					}
					break;
			    case COL:
					if (start == -1) {
						start = idx;
						rc[1] = idx;
					}
					else {
						rc[1]++;
					}
					break;
			    case ROW:
					if (start > -1) {
						rc[1]++;
					}
					break;
				default: 	
					if (start > -1) {
						rc[0] = start;
						rc[1]++;
						return rc;
					}			    	
			}
		}
		if (start > -1) {
			rc[0] = start;
			rc[1]++;
		}			    	

		return rc;
	}

	public static String adjustCellReference(String ref, int row, int col) {
		boolean abs[] = {false, false};
		char c;
		StringBuilder sRow = new StringBuilder();
		StringBuilder sCol = new StringBuilder();
		StringBuilder sRef = new StringBuilder();
        int nRow;
		String nCol;
		
		for (int idx = 0; idx < ref.length(); idx++) {
			c = ref.charAt(idx);
			switch (charType((int) c)) {
			    case ABS:  
                     if (idx == 0) {
					     abs[0] = true;
                     }
                     else {
                         abs[1] = true;
                     }
                     break;
			    case ROW: sRow.append(c); break;
			    case COL: sCol.append(c); break;
			}
		}
		nRow = Integer.parseInt(sRow.toString());
		if (row > 0 && abs[1] == false) nRow += row;
		nCol = sCol.toString();
		if (col > 0) {
			
		}
		if (abs[0] == true) sRef.append('$');
		sRef.append(nCol);
		if (abs[1] == true) sRef.append('$');
		sRef.append(nRow);
		return sRef.toString();
	}
	
	private static int charType(int c) {
		if (c > 64 && c < 91) return COL;
		if (c > 48 && c < 58) return ROW;
		if (c == 36)          return ABS;
		if (c == 34)          return QUO;
        return OTH;		
	}
}
