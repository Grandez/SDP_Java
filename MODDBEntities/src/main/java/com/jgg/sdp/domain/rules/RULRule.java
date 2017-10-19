package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_RULES")
public class RULRule implements Serializable {
	
	private static final long serialVersionUID = -7744994251713729860L;

	public final static String delRulesOfItem   = "DELETE FROM RULRule r where r.idGroup = ?1 AND r.idItem = ?2";
	public final static String findMaxId        = "SELECT MAX(r.idRule) FROM RULRule r WHERE idGroup = ?1 AND idItem = ?2";
    public final static String findById         = "SELECT r FROM RULRule r WHERE idGroup = ?1 AND idItem = ?2 AND idRule = ?3";
	public final static String listAll          = "Select i from RULRule i";
	public final static String listByItem       = "Select i from RULRule i where idGroup = ?1 AND idItem = ?2";
	public final static String listActiveByItem = "Select i from RULRule i " +
	                                              "WHERE idGroup = ?1 AND idItem = ?2 AND activo = 0 " +
			                                      "ORDER BY i.priority ";

	@Id
	@Column(name="idGroup")
	private Long idGroup;
	
	@Id
	@Column(name="idItem")
	private Long idItem;

	@Id
	@Column(name="idRule")
	private Long idRule;

	@Column(name="idDesc")
	private Long idDesc;

	@Column(name="idSample")
	private Long idSample;
	
	@Column(name="active")
	private Long active;

	@Column(name="priority")
	private Integer priority;

	@Column(name="severity")
	private Integer severity;
	
	@Column(name="property")
	private String property;

	@Column(name="comparator")
	private Integer comparator;

	@Column(name="tipo")
	private Integer tipo;
	
	@Column(name="valor")
	private String valor;
	
	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Long getIdRule() {
		return idRule;
	}

	public void setIdRule(Long idRule) {
		this.idRule = idRule;
	}

	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
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
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((comparator == null) ? 0 : comparator.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idRule == null) ? 0 : idRule.hashCode());
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
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
		RULRule other = (RULRule) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (comparator == null) {
			if (other.comparator != null)
				return false;
		} else if (!comparator.equals(other.comparator))
			return false;
		if (idDesc == null) {
			if (other.idDesc != null)
				return false;
		} else if (!idDesc.equals(other.idDesc))
			return false;
		if (idGroup == null) {
			if (other.idGroup != null)
				return false;
		} else if (!idGroup.equals(other.idGroup))
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (idRule == null) {
			if (other.idRule != null)
				return false;
		} else if (!idRule.equals(other.idRule))
			return false;
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
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
