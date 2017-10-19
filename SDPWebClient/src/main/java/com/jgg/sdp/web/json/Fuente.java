/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.ArrayList;

public class Fuente {

	private Integer parent;
	private Integer linea;
	private Integer type;
	private Integer bloque;
	private String  code;
	private Boolean usado = false;
	private Boolean malo = false;
    private ArrayList<Long> issues = new ArrayList<Long>();
	
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
    
	public Integer getLinea() {
		return linea;
	}
	public void setLinea(Integer linea) {
		this.linea = linea;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getBloque() {
		return bloque;
	}
	public void setBloque(Integer bloque) {
		this.bloque = bloque;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getUsado() {
		return usado;
	}
	public void setUsado(Boolean usado) {
		this.usado = usado;
	}
    public Boolean getMalo() {
        return malo;
    }
    public void setMalo(Boolean malo) {
        this.malo = malo;
    }
	public void addIssue(Long issue) {
		issues.add(issue);
	}
	public ArrayList<Long> getIssues() {
		return issues;
	}
}
