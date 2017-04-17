package com.jgg.sdp.domain.sql;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_SQL")
public class MODSql implements Serializable {

	private static final long serialVersionUID = 1546837698091680692L;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="begLine")
	Integer begLine;

	@Id
	@Column(name="verb")
	String verb;

	@Column(name="complexity")
	Integer complexity;

	@Column(name="explanation")
	Integer explanation;

	@Column(name="firma")
	String firma;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getBegLine() {
		return begLine;
	}

	public void setBegLine(Integer begLine) {
		this.begLine = begLine;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}

	public Integer getExplanation() {
		return explanation;
	}

	public void setExplanation(Integer explanation) {
		this.explanation = explanation;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complexity == null) ? 0 : complexity.hashCode());
		result = prime * result + ((explanation == null) ? 0 : explanation.hashCode());
		result = prime * result + ((firma == null) ? 0 : firma.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((begLine == null) ? 0 : begLine.hashCode());
		result = prime * result + ((verb == null) ? 0 : verb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MODSql other = (MODSql) obj;
		if (complexity == null) {
			if (other.complexity != null)
				return false;
		} else if (!complexity.equals(other.complexity))
			return false;
		if (explanation == null) {
			if (other.explanation != null)
				return false;
		} else if (!explanation.equals(other.explanation))
			return false;
		if (firma == null) {
			if (other.firma != null)
				return false;
		} else if (!firma.equals(other.firma))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (begLine == null) {
			if (other.begLine != null)
				return false;
		} else if (!begLine.equals(other.begLine))
			return false;
		if (verb == null) {
			if (other.verb != null)
				return false;
		} else if (!verb.equals(other.verb))
			return false;
		return true;
	}

}
