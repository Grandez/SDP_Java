package com.jgg.sdp.printer;

import com.jgg.sdp.printer.Printer;

public class Banners {

	private int level = 0;
	
	private Printer printer = new Printer();
	
	public void setLevel(int level) {
		this.level = level;
	}
/*	
	public void banner(int level, String... txt) {
		if (this.level < level) return;
		printer.boxBeg();
		for (int idx = 0; idx < txt.length; idx++) {
		    printer.boxLine(txt[idx], true);
		}
		printer.boxEnd();
		printer.nl();
	}

	public void bannerComponent(int level, String component) {
		if (this.level < level) return;
		printer.boxBeg();
		printer.boxLine("Component: " + component, true);
		printer.boxEnd();
	}
	
	public void bannerXML(int level, String name) {
		if (this.level < level) return;
		String text = (name == null) ? "No description" : name;
		printer.line(text);
	}

	public void bannerGroup(int level, String name) {
		if (this.level < level) return;
		String text = (name == null) ? "No description" : name;
		printer.line(text);
	}

	public void bannerBloque(int level, int bloque) {
		if (this.level < level) return;
		printer.boxBeg();
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxLine("BLOQUE DE PRUEBAS " +  bloque, true);		
		printer.boxEnd();
		printer.nl();
	}
*/
}
