package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_ISSUES_EXCEP")
public class SDPIssueException implements Serializable {

	private static final long serialVersionUID = -7469163722940597784L;

	@Id
	@Column(name="modulo")	
	private String modulo;
	
	@Id
	@Column(name="idSeq")	
	private Integer idSeq;
	
	@Id
	@Column(name="idIssue")	
	private Integer idIssue;

	@Id
	@Column(name="bloque")	
	private String bloque;

	@Column(name="uid")	
	private String uid;

	@Column(name="tms")	
	private Timestamp tms;

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

	public Integer getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(Integer idIssue) {
		this.idIssue = idIssue;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Timestamp getTms() {
		return tms;
	}

	public void setTms(Timestamp tms) {
		this.tms = tms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bloque == null) ? 0 : bloque.hashCode());
		result = prime * result + ((idIssue == null) ? 0 : idIssue.hashCode());
		result = prime * result + ((idSeq == null) ? 0 : idSeq.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		SDPIssueException other = (SDPIssueException) obj;
		if (bloque == null) {
			if (other.bloque != null)
				return false;
		} else if (!bloque.equals(other.bloque))
			return false;
		if (idIssue == null) {
			if (other.idIssue != null)
				return false;
		} else if (!idIssue.equals(other.idIssue))
			return false;
		if (idSeq == null) {
			if (other.idSeq != null)
				return false;
		} else if (!idSeq.equals(other.idSeq))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (tms == null) {
			if (other.tms != null)
				return false;
		} else if (!tms.equals(other.tms))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}


}
