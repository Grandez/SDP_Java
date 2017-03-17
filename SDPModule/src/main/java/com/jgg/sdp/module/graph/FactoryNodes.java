package com.jgg.sdp.module.graph;

import java.util.Iterator;

import com.jgg.sdp.core.tools.Bag;

public class FactoryNodes {

	private Bag<Node> nodes = new Bag<Node>();
	
	private static int count = 0;
	private static FactoryNodes factory = null;
	
	private FactoryNodes() {
		nodes = new Bag<Node>();
	}
	
	public static FactoryNodes getInstance() {
		if (factory == null) factory = new FactoryNodes();
		return factory;
	}
	
	public Node get(Nodes type) {
		return nodes.add(new Node(type, count++));
    }
    
	public Node get(Nodes type, String name) {
		return nodes.add(new Node(type, name, name, count++));
    }

	public Node get(Nodes type, String from, String to) {
		return nodes.add(new Node(type, from, to, count++));
    }

	public Iterator<Node> getNodes() {
		return nodes.iterator();
	}
}
