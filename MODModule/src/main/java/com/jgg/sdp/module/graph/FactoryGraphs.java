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
		return getNode(type, name, 0);
    }
	
	public Node getNode(Integer type, String name, int numStatements) {
		Node n = new Node(type, name, curGraph, numNodes++);
		n.setSubtype(type);
		n.setFromTo(name, name);
		n.setStmts(numStatements);
		return nodes.add(n);
    }

	public Node getNode(Integer type, Integer subtype) {
		return getNode(type, subtype, 0);
	}	
	public Node getNode(Integer type, Integer subtype, int stmts) {
		String name = NodeTypes.getNodeName(type)    + "-" + numNodes;  
		
		Node n = new Node(type, name, curGraph, numNodes++);
		n.setStmts(stmts);
		n.setSubtype(subtype);
		n.setFromTo(name, name);
		return nodes.add(n);
    }

	public Node getNode(Integer type, Integer subtype, String name, int numStatements) {
		Node n = new Node(type, name, curGraph, numNodes++);
		n.setSubtype(subtype);
		n.setFromTo(name, name);
		n.setStmts(numStatements);
		return nodes.add(n);
    }

	public Node getNode(Integer type, String from, String to, int stmts) {
		Node n = new Node(type, from, curGraph, numNodes++);
		n.setSubtype(type);
		n.setFromTo(from, to);
		n.setStmts(stmts);
		return nodes.add(n);
    }

	public Iterator<Node> getNodes() {
		return nodes.iterator();
	}
	
}
