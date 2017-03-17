package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_NODES")
public class MODNode implements Serializable {

	private static final long serialVersionUID = -2526806502868686628L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="idNode")
	Integer idNode;

	@Column(name="idgRAFO")
	Integer idGrafo;
	
	@Column(name="tipo")
	Integer tipo;
	
	@Column(name="nombre")
	String nombre;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getIdNode() {
		return idNode;
	}

	public void setIdNode(Integer idNode) {
		this.idNode = idNode;
	}

	
	public Integer getIdGrafo() {
		return idGrafo;
	}

	public void setIdGrafo(Integer idGrafo) {
		this.idGrafo = idGrafo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
		result = prime * result + ((idNode == null) ? 0 : idNode.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		MODNode other = (MODNode) obj;
		if (idGrafo == null) {
			if (other.idGrafo != null)
				return false;
		} else if (!idGrafo.equals(other.idGrafo))
			return false;
		if (idNode == null) {
			if (other.idNode != null)
				return false;
		} else if (!idNode.equals(other.idNode))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	
}
