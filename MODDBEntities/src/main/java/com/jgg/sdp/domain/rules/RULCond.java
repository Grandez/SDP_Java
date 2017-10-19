package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_CONDS")
public class RULCond implements Serializable {
	
	private static final long serialVersionUID = -9159376801732479923L;

	@Id
	@Column(name="idCond")
	private Long idCond;
	
	@Column(name="idDesc")
	private Long idDesc;

	@Column(name="idName")
	private Long idName;

	@Column(name="idSample")
	private Long idSample;
	
	@Column(name="tipo")
	private Integer tipo;
	
	@Column(name="property")
	private String property;
	
	@Column(name="comparator")
	private Integer comparator;

	@Column(name="valor")
	private String valor;

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

	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public Long getIdName() {
		return idName;
	}

	public void setIdName(Long idName) {
		this.idName = idName;
	}

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getComparator() {
		return comparator;
	}

	public void setComparator(Integer comparator) {
		this.comparator = comparator;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
		result = prime * result + ((comparator == null) ? 0 : comparator.hashCode());
		result = prime * result + ((idCond == null) ? 0 : idCond.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idName == null) ? 0 : idName.hashCode());
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (comparator == null) {
			if (other.comparator != null)
				return false;
		} else if (!comparator.equals(other.comparator))
			return false;
		if (idCond == null) {
			if (other.idCond != null)
				return false;
		} else if (!idCond.equals(other.idCond))
			return false;
		if (idDesc == null) {
			if (other.idDesc != null)
				return false;
		} else if (!idDesc.equals(other.idDesc))
			return false;
		if (idName == null) {
			if (other.idName != null)
				return false;
		} else if (!idName.equals(other.idName))
			return false;
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
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
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
