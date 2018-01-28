package com.jgg.sdp.web.client.graph.json;

import java.util.*;

public class JSonGraphs {

	private ArrayList<JSonGraph> graphs = new ArrayList<JSonGraph>();
	
	public void addGraph(JSonGraph graph) {
		graphs.add(graph);
	}
	public List<JSonGraph> getGraphs() {
		return graphs;
	}
}
