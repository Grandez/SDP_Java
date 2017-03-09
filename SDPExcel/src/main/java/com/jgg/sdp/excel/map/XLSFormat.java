package com.jgg.sdp.excel.map;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;

import com.jgg.sdp.excel.xls.XLSTools;
import com.jgg.sdp.excel.xls.XLSToolsFormat;

public class XLSFormat {

	private XSSFSheet sheet;
	private ArrayList<CellRangeAddress> rangos = new ArrayList<CellRangeAddress>();
	private ArrayList<XSSFConditionalFormattingRule> reglas = new ArrayList<XSSFConditionalFormattingRule>(); 
	
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	
	public void addRange(int rowFirst, int rowLast, int colFirst, int colLast) {
		rangos.add(new CellRangeAddress(rowFirst, rowLast, colFirst, colLast));
	}

	public void addRange(CellRangeAddress range) {
		rangos.add(range);
	}
	
	public void addRule(XSSFConditionalFormattingRule rule) {
		reglas.add(rule);
	}
	
	public int getNumRanges() {
		return rangos.size();
	}

	public CellRangeAddress[] getRanges() {
		return rangos.toArray(new CellRangeAddress[rangos.size()]);
	}

	public ConditionalFormattingRule[] getRules() {
		return reglas.toArray(new XSSFConditionalFormattingRule[reglas.size()]);
	}

	public CellRangeAddress[] getNewRanges(int row) {
		int rowFirst;
		int rowLast;
		
		ArrayList<CellRangeAddress> nuevos = new ArrayList<CellRangeAddress>();
		for (CellRangeAddress r : rangos) {
			rowFirst = r.getFirstRow() + row;
			rowLast = r.getLastRow() + row;
			nuevos.add(new CellRangeAddress(rowFirst, rowLast, r.getFirstColumn(), r.getLastColumn()));
		}
		return nuevos.toArray(new CellRangeAddress[nuevos.size()]);
	}
	
	public ConditionalFormattingRule[] getNewRules(int row) {
		ArrayList<XSSFConditionalFormattingRule> nuevos = new ArrayList<XSSFConditionalFormattingRule>();
		for (XSSFConditionalFormattingRule rule : reglas) {
			XSSFConditionalFormattingRule nRule = copyRule(rule, row);
//			adjustFormula(rule, row);
//			ConditionalFormattingRule nRule = new ConditionalFormattingRule();
			nuevos.add(nRule);
		}

//		XSSFSheetConditionalFormatting xscf = sheet.getSheetConditionalFormatting();
//		xscf.c
//		for (XLSFormat fmt : formatos) {
//	         xscf.addConditionalFormatting(fmt.getNewRanges(row), fmt.getNewRules(row));
//		}
		
		
		return nuevos.toArray(new XSSFConditionalFormattingRule[nuevos.size()]);
	}
	
	private XSSFConditionalFormattingRule copyRule(XSSFConditionalFormattingRule old, int row) {
		XSSFSheetConditionalFormatting  cf = sheet.getSheetConditionalFormatting();
		String formula = adjustFormula(old, row);
		
		XSSFConditionalFormattingRule fr = cf.createConditionalFormattingRule(formula);
		
		XLSToolsFormat.cloneConditionalFormat(old, fr);
        return fr;
	}

	private String adjustFormula(ConditionalFormattingRule formula, int row) {
		StringBuilder nFormula = new StringBuilder();
		int offset = 0;
		int rc[] = {0, -1};
		boolean rF[] = {false, false};
		
		String sF[] = {formula.getFormula1(), formula.getFormula2()};
	
		for (int iF = 0; iF < 2; iF++) {
		     String f = sF[iF];
		     rc = XLSTools.getCellReference(f, offset);
		     while (rc[0] != -1) {
		           rF[iF] = true;
			       nFormula.append(f.substring(offset, rc[0]));
			       nFormula.append(XLSTools.adjustCellReference(f.substring(rc[0], rc[1]), row, 0));
			       offset = rc[1];
			       rc = XLSTools.getCellReference(f, offset);
		     }
		     nFormula.append(f.substring(offset));
		     return nFormula.toString();

		}
	     return nFormula.toString();
		
	}

}
