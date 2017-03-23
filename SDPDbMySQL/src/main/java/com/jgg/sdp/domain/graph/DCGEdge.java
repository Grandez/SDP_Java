package com.jgg.sdp.domain.graph;

import java.io.Serializable;

import javax.persistence.*;

import com.jgg.sdp.domain.graph.DCGEdge;

@Entity
@Table(name="DCG_EDGES")
public class DCGEdge implements Serializable {

	private static final long serialVersionUID = 6151611699600715094L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="idGrafo")
	Integer idGrafo;

	@Id
	@Column(name="idFrom")
	Integer idFrom;

	@Id
	@Column(name="idTo")
	Integer idTo;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getIdGrafo() {
		return idGrafo;
	}

	public void setIdGrafo(Integer idGrafo) {
		this.idGrafo = idGrafo;
	}

	public Integer getIdFrom() {
		return idFrom;
	}

	public void setIdFrom(Integer idFrom) {
		this.idFrom = idFrom;
	}

	public Integer getIdTo() {
		return idTo;
	}

	public void setIdTo(Integer idTo) {
		this.idTo = idTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFrom == null) ? 0 : idFrom.hashCode());
		result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
		result = prime * result + ((idTo == null) ? 0 : idTo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
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
		DCGEdge other = (DCGEdge) obj;
		if (idFrom == null) {
			if (other.idFrom != null)
				return false;
		} else if (!idFrom.equals(other.idFrom))
			return false;
		if (idGrafo == null) {
			if (other.idGrafo != null)
				return false;
		} else if (!idGrafo.equals(other.idGrafo))
			return false;
		if (idTo == null) {
			if (other.idTo != null)
				return false;
		} else if (!idTo.equals(other.idTo))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		return true;
	}
	
	
}
