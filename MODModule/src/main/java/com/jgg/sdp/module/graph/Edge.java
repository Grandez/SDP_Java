package com.jgg.sdp.module.graph;

public class Edge {

	private int idGrafo;
	private int idFrom;
	private int idTo;
	private Node from;
	private Node to;
	
	public Edge(int idGrafo, Node from, Node to) {
		this.idGrafo = idGrafo;
		this.from = from;
		this.to = to;
		this.idFrom = from.getId();
		this.idTo = from.getId();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFrom;
		result = prime * result + idGrafo;
		result = prime * result + idTo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (idFrom != other.idFrom)
			return false;
		if (idGrafo != other.idGrafo)
			return false;
		if (idTo != other.idTo)
			return false;
		return true;
	}
		
}
