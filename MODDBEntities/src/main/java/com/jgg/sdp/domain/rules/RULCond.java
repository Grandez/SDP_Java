package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_CONDS")
public class RULCond implements Serializable {
	
	private static final long serialVersionUID = -2014036529693511435L;

	public static final String findById = "SELECT c FROM RULCond c WHERE c.idCond  = ?1";
	
	@Id
	@Column(name="idCond")
	private Long idCond;
	
	@Column(name="lvalueType")
	private Integer lvalueType;

	@Column(name="lvalue")
	private String lvalue;

	@Column(name="rvalueType")
	private Integer rvalueType;

	@Column(name="rvalue")
	private String rvalue;
	
	@Column(name="operator")
	private Integer operator;

	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public Long getIdCond() {
		return idCond;
	}

	public void setIdCond(Long idCond) {
		this.idCond = idCond;
	}

	public Integer getLvalueType() {
		return lvalueType;
	}

	public void setLvalueType(Integer lvalueType) {
		this.lvalueType = lvalueType;
	}

	public String getLvalue() {
		return lvalue;
	}

	public void setLvalue(String lvalue) {
		this.lvalue = lvalue;
	}

	public Integer getRvalueType() {
		return rvalueType;
	}

	public void setRvalueType(Integer rvalueType) {
		this.rvalueType = rvalueType;
	}

	public String getRvalue() {
		return rvalue;
	}

	public void setRvalue(String rvalue) {
		this.rvalue = rvalue;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
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
		result = prime * result + ((idCond == null) ? 0 : idCond.hashCode());
		result = prime * result + ((lvalue == null) ? 0 : lvalue.hashCode());
		result = prime * result + ((lvalueType == null) ? 0 : lvalueType.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((rvalue == null) ? 0 : rvalue.hashCode());
		result = prime * result + ((rvalueType == null) ? 0 : rvalueType.hashCode());
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
		RULCond other = (RULCond) obj;
		if (idCond == null) {
			if (other.idCond != null)
				return false;
		} else if (!idCond.equals(other.idCond))
			return false;
		if (lvalue == null) {
			if (other.lvalue != null)
				return false;
		} else if (!lvalue.equals(other.lvalue))
			return false;
		if (lvalueType == null) {
			if (other.lvalueType != null)
				return false;
		} else if (!lvalueType.equals(other.lvalueType))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (rvalue == null) {
			if (other.rvalue != null)
				return false;
		} else if (!rvalue.equals(other.rvalue))
			return false;
		if (rvalueType == null) {
			if (other.rvalueType != null)
				return false;
		} else if (!rvalueType.equals(other.rvalueType))
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
