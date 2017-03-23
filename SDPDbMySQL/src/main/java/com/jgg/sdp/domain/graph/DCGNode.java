package com.jgg.sdp.domain.graph;

import java.io.Serializable;

import javax.persistence.*;

import com.jgg.sdp.domain.graph.DCGNode;

@Entity
@Table(name="DCG_NODES")
public class DCGNode implements Serializable {

	private static final long serialVersionUID = -5885991423139457689L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="idGrafo")
	Integer idGrafo;
		
	@Id
	@Column(name="idNode")
	Integer idNode;

	@Column(name="tipo")
	Integer tipo;
	
	@Column(name="nombre")
	String nombre;

	@Column(name="subgraph")
	Integer subgraph;
	
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

	public Integer getSubgraph() {
		return subgraph;
	}

	public void setSubgraph(Integer subgraph) {
		this.subgraph = subgraph;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
		result = prime * result + ((idNode == null) ? 0 : idNode.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((subgraph == null) ? 0 : subgraph.hashCode());
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
		DCGNode other = (DCGNode) obj;
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
		if (subgraph == null) {
			if (other.subgraph != null)
				return false;
		} else if (!subgraph.equals(other.subgraph))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	
}
