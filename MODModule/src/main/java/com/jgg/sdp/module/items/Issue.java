package com.jgg.sdp.module.items;

public class Issue  {

	private long id;
	private int idGroup;
	private int idItem;
	private int idRule;
	private int severity;
	private int begLine;
	private int begColumn;
	private int endLine;
	private int endColumn;
	private long idException = 0;
	private String bloque;
	private String firma;
	
	public Issue(int idGroup, int idItem, int idRule) {
		this.idGroup = idGroup;
		this.idItem = idItem;
		this.idRule = idRule;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getIdRule() {
		return idRule;
	}

	public void setIdRule(int idRule) {
		this.idRule = idRule;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

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

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public void setException(long idException) {
		this.idException = idException;
	}
	public long getException() {
		return idException;
	}
	
}
