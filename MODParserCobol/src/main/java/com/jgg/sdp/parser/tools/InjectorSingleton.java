/**
 * Solo es un truco para poder recuperar el injector
 */
package com.jgg.sdp.parser.tools;


public class InjectorSingleton {

	private static Injector inj = null;
	
	public static Injector getInjector() {
		if (inj == null) inj = new Injector();
		return inj;
	}
	
	public static void removeInjector() {
		inj = null;
	}
}

