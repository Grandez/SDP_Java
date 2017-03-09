package com.jgg.sdp.web.json;

public class Fuente {

	private Integer linea;
	private Integer tipo;
	private Integer bloque;
	private String  code;
	private Boolean usado = false;
  
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
	
}
