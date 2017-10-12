package com.jgg.sdp.module.items;

public class Issue  {

	private long id;
	private long idGroup;
	private long idItem;
	private long idRule;
	private int severity;
	private int begLine;
	private int begColumn;
	private int endLine;
	private int endColumn;
	private long idException = 0;
	private String bloque;
	private String firma;
	
	public Issue(long idGroup, long idItem, long idRule) {
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

	public long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(long idGroup) {
		this.idGroup = idGroup;
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public long getIdRule() {
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
