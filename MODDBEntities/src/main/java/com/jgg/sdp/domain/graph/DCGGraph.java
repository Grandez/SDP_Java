package com.jgg.sdp.domain.graph;

import java.io.Serializable;

import javax.persistence.*;

import com.jgg.sdp.domain.graph.DCGGraph;

@Entity
@Table(name="DCG_GRAPHS")
public class DCGGraph implements Serializable {

	private static final long serialVersionUID = -1238086103731821178L;

	@Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="idGrafo")
    Integer idGrafo;

    @Column(name="level")
    Integer level;

    @Column(name="name")
    String name;
    
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

	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DCGGraph other = (DCGGraph) obj;
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
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
