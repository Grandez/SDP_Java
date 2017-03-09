package com.jgg.sdp.module.items;

public class CICSVerb {

	private String verbo;
	private int    tipo;
	private int    count;
	
	
	public CICSVerb(String verbo, int tipo) {
		this.verbo = verbo;
		this.tipo  = tipo;
		this.count = 0;
	}
	
	public void inc() { count++; }
	
	public String getVerbo() {
		return verbo;
	}
	public void setVerbo(String verbo) {
		this.verbo = verbo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
}
