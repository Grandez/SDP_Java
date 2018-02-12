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
	
	public Node getNode(Integer type, String name) {
		return getNode(type, type, name, name, 0);
    }
	
	public Node getNode(Integer type, String name, int stmts) {
		return getNode(type, type, name, name, stmts);
    }

	public Node getNode(Integer type, Integer subtype) {
		return getNode(type, subtype, 0);
	}	
	public Node getNode(Integer type, Integer subtype, int stmts) {
		String name = NodeTypes.getNodeName(type)    + "-" + numNodes;  
        return getNode(type, subtype, name, name, stmts);		
    }

	public Node getNode(int type, int subtype, String from, String to, int stmts) {
		Node n = new Node(type, subtype, from, to, stmts, curGraph, numNodes++);
		return nodes.add(n);
    }

	public Iterator<Node> getNodes() {
		return nodes.iterator();
	}
	
}
