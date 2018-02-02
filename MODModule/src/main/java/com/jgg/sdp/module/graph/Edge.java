package com.jgg.sdp.module.graph;

public class Edge {

	private long idGrafo;
	private Node from;
	private Node to;
	private int  type;
	
	public Edge(long idGrafo, Node from, Node to) {
		this.idGrafo = idGrafo;
		this.from = from;
		this.to = to;
		this.type = to.getCause();
	}
	
	public long getIdGrafo() {
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
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Edge: " + String.format("%3d", from.getId()) + " -> " + String.format("%3d", to.getId());
	}

	public void setIdGrafo(long idGrafo) {
		this.idGrafo = idGrafo;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	

}
