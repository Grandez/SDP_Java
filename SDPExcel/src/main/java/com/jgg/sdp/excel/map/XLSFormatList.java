package com.jgg.sdp.excel.map;

import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;

public class XLSFormatList {

	private XSSFSheet sheet = null;
	private ArrayList<XLSFormat> formatos = new ArrayList<XLSFormat>();
	private ArrayList<Integer>   pos = new ArrayList<Integer>();
	
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	
	public XSSFSheet getSheet() {
		return sheet;
	}
	
	public void addFormats(XSSFCell cell) {
			 
			 XLSFormat format = null;
		 
			 XSSFConditionalFormatting cf;
			 List<CellRangeAddress> cra;	         
	         
			 XSSFSheetConditionalFormatting xscf = cell.getSheet().getSheetConditionalFormatting();
			 
		      for (int idx = 0; idx < xscf.getNumConditionalFormattings(); idx++) {
		    	  
		          format = new XLSFormat();

		          cf = xscf.getConditionalFormattingAt(idx);
		    	  cra = Arrays.asList(cf.getFormattingRanges());

		    	  for (CellRangeAddress rango:cra) {
				       if (containsCell(rango, cell)) {
				    	   format.addRange(rango);
				       } 
	              }
		    	  
		    	  if (format.getNumRanges() > 0) {
		    		  for(int iRule = 0; iRule < cf.getNumberOfRules(); iRule++) {
		    			  format.addRule(cf.getRule(iRule));
		    		  }
		    		  if (idx >= pos.size()) {
		    		      for (int j = pos.size(); j < idx + 10; j++) {
		    			       pos.add(null);
		    		      }
		    		  }    
// No incrementa la capacidad		    		  
//                	  pos.ensureCapacity(idx + 1);
		    		  if (pos.get(idx) == null) {
                          formatos.add(format);
                          pos.set(idx, formatos.size() - 1);
		    	      }
		    	  }  
	    	  }
		      
	}

	public void addConditionalFormats(int row) {

		XSSFSheetConditionalFormatting xscf = sheet.getSheetConditionalFormatting();
		for (XLSFormat fmt : formatos) {
			 fmt.setSheet(sheet);
	         xscf.addConditionalFormatting(fmt.getNewRanges(row), fmt.getNewRules(row));
		}
	}
	
    private boolean containsCell(CellRangeAddress cra, Cell cell){
       if(cra.getFirstRow()<=cell.getRowIndex() && cra.getLastRow()>=cell.getRowIndex()){
          if(cra.getFirstColumn()<=cell.getColumnIndex() && cra.getLastColumn()>=cell.getColumnIndex()){
             return true;
          }
       }
       return false;
    }

}
