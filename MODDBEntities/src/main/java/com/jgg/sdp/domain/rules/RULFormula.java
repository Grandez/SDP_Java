package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_FORMULAS")
public class RULFormula implements Serializable {

	private static final long serialVersionUID = -5163793448697653463L;

	public static final String getFormula = "SELECT f FROM RULFormula f " +
	                                        "WHERE idFormula = ?1 AND idSeq > 0 " +
			                                "ORDER BY idSeq";
	public static final String getFormulaName = "SELECT f FROM RULFormula f WHERE idFormula = ?1 AND idSeq = 0 ";
	
	@Id
	@Column(name="idFormula")
	Long idFormula;

	@Id
	@Column(name="idType")
	Long idType;
	
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

	public Long getIdType() {
		return idType;
	}

	public void setIdType(Long idType) {
		this.idType = idType;
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
		RULFormula other = (RULFormula) obj;
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
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		return true;
	}
	
}
