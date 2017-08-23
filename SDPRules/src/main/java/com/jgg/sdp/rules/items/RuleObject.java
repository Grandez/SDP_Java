package com.jgg.sdp.rules.items;

public class RuleObject {
	private int begLine = 0;
	private int begColumn = 0;
	private int endLine = 0;
	private int endColumn = 0;

	private String bloque = "";
	private Object component = null;
	
    	
	public int getBegLine() {
		return begLine;
	}


	public void setBegLine(int begLine) {
		this.begLine = begLine;
	}


	public int getBegColumn() {
		return begColumn;
	}


	public void setBegColumn(int begColumn) {
		this.begColumn = begColumn;
	}


	public int getEndLine() {
		return endLine;
	}


	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}


	public int getEndColumn() {
		return endColumn;
	}


	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}


	public String getBloque() {
		return bloque;
	}


	public void setBloque(String bloque) {
		this.bloque = bloque;
	}


	public Object getComponent() {
		return component;
	}


	public void setComponent(Object component) {
		this.component = component;
	}


	public String getObjectAsString() {
		return (String) component;
	}
	

	// Metodos dinamicos
	
	public int getLength() {
		if (component == null) return 0;
		return ((String) component).length();
	}
}
