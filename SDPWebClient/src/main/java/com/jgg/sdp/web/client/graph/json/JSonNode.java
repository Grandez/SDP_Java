package com.jgg.sdp.web.client.graph.json;

public class JSonNode {
	private Long    idVersion;
	private Long    idGraph;
	private Long    subGraph;
	private Long    idNode;
	private Integer type;
	private String  name;
	
	private String  category;
	private Long    key;
	
	private Object  data;
	
	public JSonNode() {
		
	}
	public JSonNode(Long idVersion) {
		this.idVersion = idVersion;
		this.idNode = idVersion;
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
	public Long getIdNode() {
		return idNode;
	}
	public void setIdNode(Long idNode) {
		this.idNode = idNode;
		this.key = idNode;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getSubGraph() {
		return subGraph;
	}
	public void setSubGraph(Long subGraph) {
		this.subGraph = subGraph;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
