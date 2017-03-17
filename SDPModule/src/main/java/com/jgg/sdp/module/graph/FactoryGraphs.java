package com.jgg.sdp.module.graph;

public class FactoryGraphs {

	private static int count = 0;
	private static FactoryGraphs factory = null;
	
	private FactoryGraphs() {
	}
	
	public static FactoryGraphs getInstance() {
		if (factory == null) factory = new FactoryGraphs();
		return factory;
	}
	
	public SubGraph get(String name) {
	    return new SubGraph(name, count++);
	}
}
