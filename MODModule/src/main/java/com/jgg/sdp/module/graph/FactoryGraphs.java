package com.jgg.sdp.module.graph;

import java.util.Iterator;

import com.jgg.sdp.tools.Bag;

public class FactoryGraphs {

	private static int numNodes = 0;
    private        int curGraph = 0;
	
	private Bag<Node> nodes = new Bag<Node>();
		
	private static FactoryGraphs factory = null;
	
	private FactoryGraphs() {
		nodes = new Bag<Node>();
	}
	
	public static FactoryGraphs getInstance(boolean reset) {
		if (factory == null) factory = new FactoryGraphs();
		if (reset) {
			numNodes = 0;
		}
		return factory;
	}

	public SubGraph createGraph(int id, String name) {
		curGraph = id;
	    return new SubGraph(id, name);
	}
	
	public Node getNode(Integer type) {
		return nodes.add(new Node(type, curGraph, numNodes++));
    }
    
	public Node getNode(Integer type, String name) {
		return nodes.add(new Node(type, name, name, curGraph, numNodes++));
    }
	
	public Node getNode(Integer type, Integer subtype) {
		String name = NodeTypes.getNodeName(type)    + "-" + 
	                  NodeTypes.getNodeName(subtype) + "-" + numNodes; 
		return nodes.add(new Node(type, subtype, name, name, curGraph, numNodes++));
    }

	public Node getNode(Integer type, Integer subtype, String name) {
		return nodes.add(new Node(type, subtype, name, name, curGraph, numNodes++));
    }

	public Node getNode(Integer type, String from, String to) {
		return nodes.add(new Node(type, from, to, curGraph, numNodes++));
    }

	public Iterator<Node> getNodes() {
		return nodes.iterator();
	}
	
}
