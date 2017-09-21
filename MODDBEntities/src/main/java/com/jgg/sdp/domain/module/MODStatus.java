package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_STATUS")
public class MODStatus implements Serializable {

	private static final long serialVersionUID = 4755565955017576806L;

	@Id
    @Column(name="idVersion")
    Long idVersion;

	@Id
    @Column(name="idGrupo")
    Integer idGrupo;
	
	@Id
    @Column(name="idItem")
    Integer idItem;
    
    @Column(name="actual")
    Integer actual;

    @Column(name="maximo")
    Integer maximo;

    @Column(name="excepcion")
    Integer excepcion;
    
    @Column(name="progreso")
    Integer progreso;

    @Column(name="delta")
    Integer delta;
    
    @Column(name="status")
    Integer status;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Integer getActual() {
		return actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	public Integer getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Integer excepcion) {
		this.excepcion = excepcion;
	}

	public Integer getProgreso() {
		return progreso;
	}

	public void setProgreso(Integer progreso) {
		this.progreso = progreso;
	}

	public Integer getDelta() {
		return delta;
	}

	public void setDelta(Integer delta) {
		this.delta = delta;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result + ((delta == null) ? 0 : delta.hashCode());
		result = prime * result + ((excepcion == null) ? 0 : excepcion.hashCode());
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((maximo == null) ? 0 : maximo.hashCode());
		result = prime * result + ((progreso == null) ? 0 : progreso.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		MODStatus other = (MODStatus) obj;
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (delta == null) {
			if (other.delta != null)
				return false;
		} else if (!delta.equals(other.delta))
			return false;
		if (excepcion == null) {
			if (other.excepcion != null)
				return false;
		} else if (!excepcion.equals(other.excepcion))
			return false;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (maximo == null) {
			if (other.maximo != null)
				return false;
		} else if (!maximo.equals(other.maximo))
			return false;
		if (progreso == null) {
			if (other.progreso != null)
				return false;
		} else if (!progreso.equals(other.progreso))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

    
}
