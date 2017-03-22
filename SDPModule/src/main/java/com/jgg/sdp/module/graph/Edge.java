package com.jgg.sdp.module.graph;

public class Edge {

	private int idGrafo;
	private Node from;
	private Node to;
	
	public Edge(int idGrafo, Node from, Node to) {
		this.idGrafo = idGrafo;
		this.from = from;
		this.to = to;
	}
	
	public int getIdGrafo() {
		return idGrafo;
	}
	public void setIdGrafo(int idGrafo) {
		this.idGrafo = idGrafo;
	}
	
	public void setFrom(Node from) {
		this.from = from;
	}
	
	public Node getFrom() {
		return from;
	}
	public Node getTo() {
		return to;
	}

	public boolean toBlock() {
		return to.getType() == Nodes.BLOCK || to.getType() == Nodes.END;
	}
	
	public boolean toEnd() {
		return to.getType() == Nodes.END;
	}
	
	public boolean isEmpty() {
		return from.isBegin() && to.isEnd();
	}
	@Override
	public String toString() {
		String p1 = "Edge: " + String.format("%3d", from.getId()) + " -> " + String.format("%3d", to.getId());
		String p2 = "\t" + from.getType().toString()  + " -> " + to.getType().toString();
		return p1 + p2;
	}
	
}
