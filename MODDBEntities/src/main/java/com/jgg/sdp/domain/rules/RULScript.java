package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_SCRIPTS")
public class RULScript implements Serializable {

	private static final long serialVersionUID = -5163793448697653463L;

	public static final String getScript  = "SELECT s FROM RULScript s " +
	                                        "WHERE s.idScript = ?1 AND s.idSeq > 0 " +
			                                "ORDER BY s.idSeq";
	public static final String getScriptName  = "SELECT s FROM RULScript s WHERE s.idScript = ?1 AND s.idSeq = 0 ";
	public static final String delScript      = "DELETE   FROM RULScript s WHERE s.idScript = ?1";
	public static final String delScripts     = "DELETE   FROM RULScript s WHERE s.idScript BETWEEN ?1 AND ?2";
	
	@Id
	@Column(name="idScript")
	Long idScript;

	@Id
	@Column(name="idType")
	Integer idType;
	
	@Id
	@Column(name="idSeq")
	Integer idSeq;
	
	@Column(name="code")
	String code;

	public Long getIdScript() {
		return idScript;
	}

	public void setIdScript(Long idScript) {
		this.idScript = idScript;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((idScript == null) ? 0 : idScript.hashCode());
		result = prime * result + ((idSeq == null) ? 0 : idSeq.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
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
		RULScript other = (RULScript) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (idScript == null) {
			if (other.idScript != null)
				return false;
		} else if (!idScript.equals(other.idScript))
			return false;
		if (idSeq == null) {
			if (other.idSeq != null)
				return false;
		} else if (!idSeq.equals(other.idSeq))
			return false;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		return true;
	}

	
}
