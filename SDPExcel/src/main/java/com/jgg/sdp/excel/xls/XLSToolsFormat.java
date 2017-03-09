package com.jgg.sdp.excel.xls;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTFontImpl;

public class XLSToolsFormat {

	public static void cloneConditionalFormat(XSSFConditionalFormattingRule orule, XSSFConditionalFormattingRule nrule) {
		cloneBorder(orule, nrule);
		cloneFont(orule, nrule);
		clonePattern(orule, nrule);
	}
	
	private static void cloneBorder(ConditionalFormattingRule orule, ConditionalFormattingRule nrule) {
		BorderFormatting obf = orule.getBorderFormatting();
		if (obf == null) return;
		
		
		BorderFormatting nbf = nrule.createBorderFormatting();
	
		nbf.setBorderBottom(obf.getBorderBottomEnum());

		nbf.setBorderDiagonal(obf.getBorderDiagonal());
		nbf.setBorderLeft(obf.getBorderLeft());
		nbf.setBorderRight(obf.getBorderRight());
		nbf.setBorderTop(obf.getBorderTop());
		nbf.setBottomBorderColor(obf.getBottomBorderColorColor());
		nbf.setDiagonalBorderColor(obf.getBottomBorderColorColor());
		nbf.setLeftBorderColor(obf.getLeftBorderColorColor());
		nbf.setRightBorderColor(obf.getRightBorderColorColor());
		nbf.setTopBorderColor(obf.getTopBorderColorColor());
	}
	
	private static void cloneFont(ConditionalFormattingRule orule, ConditionalFormattingRule nrule) {
		XSSFFontFormatting obf = (XSSFFontFormatting) orule.getFontFormatting();
		
		if (obf == null) return;
		
		XSSFFontFormatting nbf = (XSSFFontFormatting) nrule.createFontFormatting();
		
		try {
			Field field = nbf.getClass().getDeclaredField("_font");
			Field f = obf.getClass().getDeclaredField("_font");
			field.setAccessible(true);
			try {
				field.set(nbf, f);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		private int myField;

		    public SomeClass() {
		        myField = 42;
		    }

		    public static Object getInstanceField(Object instance, String fieldName) throws Throwable {
		        Field field = instance.getClass().getDeclaredField(fieldName);
		        return field.get(instance);
		    }
		
		nbf.setEscapementType(obf.getEscapementType());
		XSSFColor c = (XSSFColor) obf.getFontColor();
		
		if (c.isIndexed()) {
			nbf.setFontColorIndex(c.getIndex());
		}
		else if (c.isRGB()) {
//			nbf.setFontColor(new XSSFColor(c.getRGB()));
			nbf.setFontColor(c);
		}
		*/
/*		
		if (obf.getFontColorIndex() != 0) {
			nbf.setFontColorIndex(obf.getFontColorIndex());
		}
		else {
			if (obf.getFontColor() != null) {
				XSSFColor c = (XSSFColor) obf.getFontColor();
                System.out.println(c.getIndex()); 
                System.out.println(c.isAuto());
                System.out.println(c.isIndexed());
                System.out.println(c.isRGB());
                System.out.println(c.isThemed()); 
			    nbf.setFontColor(obf.getFontColor());
			}
		}	
*/
		if (obf.getFontHeight() != -1)  nbf.setFontHeight(obf.getFontHeight());
		nbf.setUnderlineType(obf.getUnderlineType());
		nbf.setFontStyle(obf.isItalic(), obf.isBold());
	}

	private static void clonePattern(ConditionalFormattingRule orule, ConditionalFormattingRule nrule) {
		PatternFormatting obf = orule.getPatternFormatting();
		
		if (obf == null) return;
		
		PatternFormatting nbf = nrule.createPatternFormatting();
		
		nbf.setFillBackgroundColor(obf.getFillBackgroundColorColor());
		nbf.setFillForegroundColor(obf.getFillForegroundColorColor());
		nbf.setFillPattern(obf.getFillPattern());
	}
	
}
