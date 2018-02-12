package com.jgg.sdp.printer;

import com.jgg.sdp.printer.color.ColoredPrinter;
import com.jgg.sdp.printer.color.api.Ansi.*;
import com.jgg.sdp.tools.Cadena;

public class Printer {

	private int level =  0;
	private int len   = 75;
	private int curr  =  0;
	 
	private static Printer printer = null;
	
	private StringBuilder linea = new StringBuilder();

	ColoredPrinter cp = new ColoredPrinter.Builder(99, false).build();

	public Printer() {
		
	}
	public Printer(int level) {
		this.level = level;
		this.cp.setLevel(level);
	}
	
	public static Printer getInstance() {
		if (printer == null) printer = new Printer();
		return printer;
	}  
	public static Printer getInstance(int level) {
		if (printer == null) printer = new Printer();
		printer.setLevel(level);
		printer.cp.setLevel(level);
		return printer;
	}  

	public void setLevel    (int level)    { this.level = level; } 
	public void setLineWidth(int length)   { this.len = length; }
	
	public void print   (String txt) {print  (0, txt); }
	public void println (String txt) {println(0, txt); }
	
	public void lineBeg (String txt) {lineBeg(0, txt); }
	public void lineCnt (String txt) {lineCnt(0, txt); }
	public void lineEnd (String txt) {lineEnd(0, txt); }

	public void print   (int level, String txt) {
		cp.print  (level, txt);  curr += txt.length(); 
	}
	public void println (int level, String txt) {
		cp.println(level, txt);  curr = 0; 
	}
	
	public void lineBeg (int level, String txt) {flush(level); print(level, txt); }
	public void lineCnt (int level, String txt) {print(level, txt);               }
	public void lineEnd (int level, String txt) {
		print(level, Cadena.spaces(len - curr));  
		println(level, txt);
	}
	
	private void flush(int level) {
		if (this.level < level) return;
		if (curr > 0) println(0, "");
	}
	
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
	
	public void writeAction(String msg) {
		StringBuilder aux = new StringBuilder();
		write(msg);
		for (int i = msg.length(); i < len; i++) aux.append(' ');
		write(aux.toString());
	}
	public void writeRes(String res) {
		writeln(res);
	}
		
	private void writeln()               { writeln(linea.toString(), Attribute.NONE); }	
	private void writeln(Attribute attr) { writeln(linea.toString(), attr);           }
	
	private void write(String txt) { 
		curr += txt.length(); 
		cp.print(txt); 
	}
	
	private void writeln(String txt) { writeln(txt, Attribute.NONE); }
	private void writeln(String txt, Attribute attr) {
		cp.println(txt, attr, FColor.NONE, BColor.NONE);
//		System.out.writeln(txt);
	}
	
	private void out(String txt) { 
		curr += txt.length(); 
		System.out.print(txt); 
	}
	
	private void outln(String txt) {
		System.out.println(txt);
		curr = 0;
	}

	/**********************************************************/
	/***               BANNERS                              ***/
	/**********************************************************/
	
	public void banner(int level, String... txt) {
		if (this.level < level) return;
		boxBeg();
		for (int idx = 0; idx < txt.length; idx++) {
		    boxLine(txt[idx], true);
		}
		boxEnd();
		nl();
	}

	private void nl()                { nl(1); }
	private void boxBeg()            { boxDecorator(); }
	private void boxEnd()            { boxDecorator(); }
	private void boxLine(String txt) { boxLine(txt, false); }

	private void boxLine(String txt, boolean centered) {
	    int idx = 0;
		int left = (centered) ? (len - txt.length()) / 2 : 1;
		linea.setLength(0);
		linea.append("|");
		for (idx = 0; idx < left; idx++) linea.append(' ');
		linea.append(txt);
		for (idx = linea.length(); idx < len - 1; idx++) linea.append(' ');
		linea.append("|");
		writeln();
	}

	private void boxDecorator() {
		linea.setLength(0); 
		linea.append("+");
		for (int idx = 0; idx < (len - 2); idx++) linea.append('-');
		linea.append('+');
		writeln(Attribute.BOLD);
		
	}
	
}
