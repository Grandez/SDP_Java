package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_CONDS")
public class RULCond implements Serializable {
	
	private static final long serialVersionUID = -4594791622754587124L;

	@Id
	@Column(name="idGroup")
	private Integer idGroup;
	
	@Id
	@Column(name="idItem")
	private Integer idItem;

	@Id
	@Column(name="idCond")
	private Integer idCond;

	@Column(name="activo")
	private Integer activo;

	@Column(name="idDesc")
	private Integer idDesc;
	
	@Column(name="tipo")
	private Integer tipo;
	
	@Column(name="propiedad")
	private String propiedad;
	
	@Column(name="comparador")
	private Integer comparador;

	@Column(name="valor")
	private String valor;

	@Column(name="idSample")
	private Integer idSample;
	
	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Integer getIdCond() {
		return idCond;
	}

	public void setIdCond(Integer idCond) {
		this.idCond = idCond;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Integer idDesc) {
		this.idDesc = idDesc;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}

	public Integer getComparador() {
		return comparador;
	}

	public void setComparador(Integer comparador) {
		this.comparador = comparador;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getIdSample() {
		return idSample;
	}

	public void setIdSample(Integer idSample) {
		this.idSample = idSample;
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
		result = prime * result + ((idCond == null) ? 0 : idCond.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
		result = prime * result + ((propiedad == null) ? 0 : propiedad.hashCode());
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
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		if (propiedad == null) {
			if (other.propiedad != null)
				return false;
		} else if (!propiedad.equals(other.propiedad))
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
