package com.jgg.sdp.domain.core;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SDP_FORMULAS")
public class SDPFormula implements Serializable {

	private static final long serialVersionUID = 328010714904205607L;

	public static final String getFormula = "SELECT f FROM SDPFormula f WHERE idFormula = ?1 ORDER BY idSeq";
	
	@Id
	@Column(name="idFormula")
	Long idFormula;

	@Id
	@Column(name="idSeq")
	Long idSeq;
	
	@Column(name="formula")
	String formula;

	public Long getIdFormula() {
		return idFormula;
	}

	public void setIdFormula(Long idFormula) {
		this.idFormula = idFormula;
	}

	public Long getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Long idSeq) {
		this.idSeq = idSeq;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((idFormula == null) ? 0 : idFormula.hashCode());
		result = prime * result + ((idSeq == null) ? 0 : idSeq.hashCode());
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
		SDPFormula other = (SDPFormula) obj;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (idFormula == null) {
			if (other.idFormula != null)
				return false;
		} else if (!idFormula.equals(other.idFormula))
			return false;
		if (idSeq == null) {
			if (other.idSeq != null)
				return false;
		} else if (!idSeq.equals(other.idSeq))
			return false;
		return true;
	}

}
