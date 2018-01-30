package com.jgg.sdp.web.client.graph.json;

public class JSonCall {
	private Integer method = 0;  // Verbo 
    private Integer mode   = 0;    // Dinamico - Estatico
    private Integer refs   = 1;
    private Integer status = 1;
    private Long    execs  = 1L;
	public Integer getMethod() {
		return method;
	}
	public void setMethod(Integer method) {
		this.method = method;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Integer getRefs() {
		return refs;
	}
	public void setRefs(Integer refs) {
		this.refs = refs;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getExecs() {
		return execs;
	}
	public void setExecs(Long execs) {
		this.execs = execs;
	}

}
