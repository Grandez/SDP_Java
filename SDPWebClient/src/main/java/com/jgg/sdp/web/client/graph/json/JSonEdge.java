package com.jgg.sdp.web.client.graph.json;

public class JSonEdge {
	
//	private Long    idVersion;
//	private Long    idGraph;	
	private Long    from;
	private Long    to;
//	private String  from;
//	private String  to;	
	
	public JSonEdge() {
		
	}
	public JSonEdge(Long from, Long to) {
//		this.idVersion = idVersion;
//		this.idGraph   = idGraph;
		this.from      = from;
		this.to        = to;
	}
	
//	public Long getIdVersion() {
//		return idVersion;
//	}
//	public void setIdVersion(Long idVersion) {
//		this.idVersion = idVersion;
//	}
//	public Long getIdGraph() {
//		return idGraph;
//	}
//	public void setIdGraph(Long idGraph) {
//		this.idGraph = idGraph;
//	}
	
//	public String getFrom() {
//		return from;
//	}
//	public void setFrom(String from) {
//		this.from = from;
//	}
//	public String getTo() {
//		return to;
//	}
//	public void setTo(String to) {
//		this.to = to;
//	}
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	
}
