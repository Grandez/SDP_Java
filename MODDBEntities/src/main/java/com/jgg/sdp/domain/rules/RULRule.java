package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_RULES")
public class RULRule implements Serializable {
	
	private static final long serialVersionUID = -8064928861200075885L;

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
	
	@Column(name="activo")
	private Integer activo;

	@Column(name="priority")
	private Integer priority;

	@Column(name="propiedad")
	private String propiedad;

	@Column(name="severity")
	private Integer severity;
	
	@Column(name="comparador")
	private Integer comparador;

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

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public Integer getComparador() {
		return comparador;
	}

	public void setComparador(Integer comparador) {
		this.comparador = comparador;
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
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((comparador == null) ? 0 : comparador.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idRule == null) ? 0 : idRule.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((propiedad == null) ? 0 : propiedad.hashCode());
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
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (comparador == null) {
			if (other.comparador != null)
				return false;
		} else if (!comparador.equals(other.comparador))
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
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (propiedad == null) {
			if (other.propiedad != null)
				return false;
		} else if (!propiedad.equals(other.propiedad))
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
