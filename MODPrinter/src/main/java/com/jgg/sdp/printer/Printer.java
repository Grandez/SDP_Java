package com.jgg.sdp.printer;

import com.jgg.sdp.printer.color.ColoredPrinter;
import com.jgg.sdp.printer.color.api.Ansi.*;

public class Printer {

	private final int LEN = 75;
	
	private StringBuilder linea = new StringBuilder();;

	ColoredPrinter cp = new ColoredPrinter.Builder(9, false).build();

	public static void print  (String txt) { System.out.print(txt);   }
	public static void println(String txt) { System.out.println(txt); }
	
	public void nl()                { nl(1); }
	public void boxBeg()            { boxDecorator(); }
	public void boxEnd()            { boxDecorator(); }
	public void boxLine(String txt) { boxLine(txt, false); }
	
	public void nl(int lines) {
		for (int idx = 0; idx < lines; idx++) write("");
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
		writeln();
	}
	
	public void lineBeg(String txt) {
		linea.setLength(0);
		linea.append(txt);
		write(txt);
	}

	public void lineCnt(String txt) {
		linea.append(txt);
		write(txt);
	}

	public void lineEnd(String txt) {
		StringBuilder s = new StringBuilder();
		for (int idx = linea.length() + txt.length(); idx < LEN; idx++) s.append(' ');
		s.append(txt);
		writeln(s.toString());
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
		writeln();
	}
	
	
	public void writeAction(String msg) {
		StringBuilder aux = new StringBuilder();
		write(msg);
		for (int i = msg.length(); i < LEN; i++) aux.append(' ');
		write(aux.toString());
	}
	public void writeRes(String res) {
		writeln(res);
	}
	
	private void boxDecorator() {
		linea.setLength(0); 
		linea.append("+");
		for (int idx = 0; idx < (LEN - 2); idx++) linea.append('-');
		linea.append('+');
		writeln(Attribute.BOLD);
		
	}
	
	private void writeln()               { writeln(linea.toString(), Attribute.NONE); }	
	private void writeln(Attribute attr) { writeln(linea.toString(), attr);           }
	
	private void write(String txt) { System.out.print(txt); }
	
	private void writeln(String txt) { writeln(txt, Attribute.NONE); }
	private void writeln(String txt, Attribute attr) {
		cp.println(txt, attr, FColor.NONE, BColor.NONE);
//		System.out.writeln(txt);
	}
	
}
