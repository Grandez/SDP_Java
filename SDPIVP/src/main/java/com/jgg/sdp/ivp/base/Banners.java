package com.jgg.sdp.ivp.base;

import com.jgg.sdp.printer.Printer;

public class Banners {

	private static Printer printer = new Printer();
	
	public static void banner() {
		
		printer.boxBeg();
		printer.boxLine("SERENDIPITY", true);
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxEnd();
		printer.nl();
	}

	public static void bannerComponent(String component) {
		
		printer.boxBeg();
		printer.boxLine("Component: " + component, true);
		printer.boxEnd();
	}
	
	public static void bannerXML(String name) {
		String text = (name == null) ? "No description" : name;
		printer.line(text);
	}

	public static void bannerGroup(String name) {
		String text = (name == null) ? "No description" : name;
		printer.line(text);
	}

	public static void bannerBloque(int bloque) {
		
		printer.boxBeg();
		printer.boxLine("PROCESO DE VERIFICACION", true);
		printer.boxLine("BLOQUE DE PRUEBAS " +  bloque, true);		
		printer.boxEnd();
		printer.nl();
	}

}
