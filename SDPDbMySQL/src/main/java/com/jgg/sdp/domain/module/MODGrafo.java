package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_GRAPH")
public class MODGrafo implements Serializable {

    private static final long serialVersionUID = -3574798070702785374L;

    @Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="idGrafo")
    Integer idGrafo;

    @Column(name="nodeFrom")
    Integer nodeFrom;

    @Column(name="nodeTo")
    Integer nodeTo;

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

	public Integer getNodeFrom() {
		return nodeFrom;
	}

	public void setNodeFrom(Integer nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	public Integer getNodeTo() {
		return nodeTo;
	}

	public void setNodeTo(Integer nodeTo) {
		this.nodeTo = nodeTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((nodeFrom == null) ? 0 : nodeFrom.hashCode());
		result = prime * result + ((nodeTo == null) ? 0 : nodeTo.hashCode());
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
		MODGrafo other = (MODGrafo) obj;
		if (idGrafo == null) {
			if (other.idGrafo != null)
				return false;
		} else if (!idGrafo.equals(other.idGrafo))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (nodeFrom == null) {
			if (other.nodeFrom != null)
				return false;
		} else if (!nodeFrom.equals(other.nodeFrom))
			return false;
		if (nodeTo == null) {
			if (other.nodeTo != null)
				return false;
		} else if (!nodeTo.equals(other.nodeTo))
			return false;
		return true;
	}

}
