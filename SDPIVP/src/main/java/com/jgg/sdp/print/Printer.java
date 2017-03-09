package com.jgg.sdp.print;

public class Printer {

	private final int LEN = 65;
	
	private StringBuilder linea = new StringBuilder();;
	
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
		println();
		
	}
	
	private void println() { println(linea.toString()); }
	
	private void print(String txt) { System.out.print(txt); }
	
	private void println(String txt) {
		System.out.println(txt);
	}
	
}
