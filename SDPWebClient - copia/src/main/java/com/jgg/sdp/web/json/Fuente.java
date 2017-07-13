/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.ArrayList;

public class Fuente {

	private Integer linea;
	private Integer tipo;
	private Integer bloque;
	private String  code;
	private Boolean usado = false;
	private Boolean malo = false;
    private ArrayList<Integer> issues = new ArrayList<Integer>();
	
	public Integer getLinea() {
		return linea;
	}
	public void setLinea(Integer linea) {
		this.linea = linea;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
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
	public void addIssue(Integer issue) {
		issues.add(issue);
	}
	public ArrayList<Integer> getIssues() {
		return issues;
	}
}
