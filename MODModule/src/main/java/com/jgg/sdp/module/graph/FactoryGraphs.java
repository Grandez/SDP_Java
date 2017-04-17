package com.jgg.sdp.module.graph;

import java.util.Iterator;

import com.jgg.sdp.tools.Bag;

public class FactoryGraphs {

	private static int numGraphs = 0;
	private static int numNodes  = 0;

	private Bag<Node> nodes = new Bag<Node>();
		
	private static FactoryGraphs factory = null;
	
	private FactoryGraphs() {
		nodes = new Bag<Node>();
	}
	
	public static FactoryGraphs getInstance(boolean reset) {
		if (factory == null) factory = new FactoryGraphs();
		if (reset) {
			numGraphs = 0;
			numNodes = 0;
		}
		return factory;
	}

	public SubGraph getGraph(String name) {
	    return new SubGraph(name, numGraphs++);
	}
	
	public Node getNode(Nodes type) {
		return nodes.add(new Node(type, numGraphs, numNodes++));
    }
    
	public Node getNode(Nodes type, String name) {
		return nodes.add(new Node(type, name, name, numGraphs, numNodes++));
    }

	public Node getNode(Nodes type, Nodes subtype, String name) {
		return nodes.add(new Node(type, subtype, name, name, numGraphs, numNodes++));
    }

	public Node getNode(Nodes type, String from, String to) {
		return nodes.add(new Node(type, from, to, numGraphs, numNodes++));
    }

	public Iterator<Node> getNodes() {
		return nodes.iterator();
	}
	
}
