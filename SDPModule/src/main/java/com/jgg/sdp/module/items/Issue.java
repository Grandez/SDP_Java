package com.jgg.sdp.module.items;

public class Issue  {

	private long id;
	private int idIssue;
	private int severity;
	private int begLine;
	private int begColumn;
	private int endLine;
	private int endColumn;
	private String bloque;
	private String firma;
	
	public Issue(int idIssue) {
		this.idIssue = idIssue;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public int getIdIssue() {
		return idIssue;
	}
	public void setIdIssue(int idIssue) {
		this.idIssue = idIssue;
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


}
