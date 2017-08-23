package com.jgg.sdp.printer;

import com.jgg.sdp.printer.color.ColoredPrinter;
import com.jgg.sdp.printer.color.api.Ansi.*;

public class JGGPrinter {

	private final int LEN = 75;
	
	private StringBuilder linea = new StringBuilder();;

	ColoredPrinter cp = new ColoredPrinter.Builder(9, false).build();

	public void nl()                { nl(1); }
	public void boxBeg()            { boxDecorator(); }
	public void boxEnd()            { boxDecorator(); }
	public void boxLine(String txt) { boxLine(txt, false); }
	
	public void nl(int lines) {
		for (int idx = 0; idx < lines; idx++) print("");
	}
	
	public void box(String txt, String...strings) {
		boxDecorator();
		boxLine(txt);
		for (String s : strings) boxLine(s);
		boxDecorator();
	}
	
	public void line(String txt) {
		linea.setLength(0);
		linea.append(txt);
		println();
	}
	
	public void lineBeg(String txt) {
		linea.setLength(0);
		linea.append(txt);
		print(txt);
	}

	public void lineCnt(String txt) {
		linea.append(txt);
		print(txt);
	}

	public void lineEnd(String txt) {
		StringBuilder s = new StringBuilder();
		for (int idx = linea.length() + txt.length(); idx < LEN; idx++) s.append(' ');
		s.append(txt);
		println(s.toString());
	}
	
	public void boxLine(String txt, boolean centered) {
	    int idx = 0;
		int left = (centered) ? (LEN - txt.length()) / 2 : 1;
		linea.setLength(0);
		linea.append("|");
		for (idx = 0; idx < left; idx++) linea.append(' ');
		linea.append(txt);
		for (idx = linea.length(); idx < LEN - 1; idx++) linea.append(' ');
		linea.append("|");
		println();
	}
	
	
	public void printAction(String msg) {
		StringBuilder aux = new StringBuilder();
		print(msg);
		for (int i = msg.length(); i < LEN; i++) aux.append(' ');
		print(aux.toString());
	}
	public void printRes(String res) {
		println(res);
	}
	
	private void boxDecorator() {
		linea.setLength(0); 
		linea.append("+");
		for (int idx = 0; idx < (LEN - 2); idx++) linea.append('-');
		linea.append('+');
		println(Attribute.BOLD);
		
	}
	
	private void println()               { println(linea.toString(), Attribute.NONE); }	
	private void println(Attribute attr) { println(linea.toString(), attr);           }
	
	private void print(String txt) { System.out.print(txt); }
	
	private void println(String txt) { println(txt, Attribute.NONE); }
	private void println(String txt, Attribute attr) {
		cp.println(txt, attr, FColor.NONE, BColor.NONE);
//		System.out.println(txt);
	}
	
}
