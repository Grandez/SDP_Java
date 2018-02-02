package com.jgg.sdp.web.client.graph.json;

import java.util.*;

public class JSonGraph {
	private Long    idVersion;
	private Long    idGraph;
	private boolean isGroup = false;
	private String  name;
	private boolean data = false; 
	
	private ArrayList<JSonNode> nodes = new ArrayList<JSonNode>();
	private ArrayList<JSonEdge> edges = new ArrayList<JSonEdge>();
	
	public JSonGraph() {
		
	}
	
	public JSonGraph(Long idVersion) {
		this.idVersion = idVersion;
		this.idGraph = idVersion;
	}
	
	public void addNode(JSonNode node) {
		nodes.add(node);
	}
	public void addEdge(JSonEdge edge) {
		edges.add(edge);
	}
	
	public List<JSonNode> getNodes() {
		return nodes;
	}
	public List<JSonEdge> getEdges() {
		return edges;
	}
	public Long getIdVersion() {
		return idVersion;
	}
	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}
	public Long getIdGraph() {
		return idGraph;
	}
	public void setIdGraph(Long idGraph) {
		this.idGraph = idGraph;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isData() {
		return data;
	}
	public void setData(boolean data) {
		this.data = data;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	

}
