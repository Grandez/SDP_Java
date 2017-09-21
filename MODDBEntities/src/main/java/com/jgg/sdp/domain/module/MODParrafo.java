package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_PARRAFOS")
@NamedQueries({
	 @NamedQuery(name="MODParrafo.listByKey",
	             query="SELECT p FROM MODParrafo p WHERE p.idVersion = ?1 ORDER BY p.nombre ASC")
	,@NamedQuery(name="MODParrafo.listByIndex",
                 query="SELECT p FROM MODParrafo p WHERE p.idVersion = ?1 ORDER BY p.indice ASC")
    ,@NamedQuery(name="MODParrafo.listByName",
                 query="SELECT p FROM MODParrafo p WHERE p.idVersion = ?1 ORDER BY p.nombre ASC")
})
public class MODParrafo implements Serializable {

	private static final long serialVersionUID = 7045773894247904704L;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="nombre")
	String nombre;
	
	@Column(name="linea")
	Integer linea;
	
	@Column(name="sentencias")
	Integer sentencias;

	@Column(name="referencias")
	Integer referencias;

	@Column(name="indice")
	Integer indice;

	@Column(name="cc")
	Integer cc;

	@Column(name="isExit")
	Integer isExit;
	
	public MODParrafo() {
	}
	public MODParrafo(Long idVersion) {
		this.idVersion = idVersion;
	}
	
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSentencias() {
		return sentencias;
	}

	public void setSentencias(Integer sentencias) {
		this.sentencias = sentencias;
	}

	public Integer getReferencias() {
		return referencias;
	}

	public void setReferencias(Integer referencias) {
		this.referencias = referencias;
	}
	
	public Integer getIndice() {
		return indice;
	}
	
	public void setIndice(Integer indice) {
		this.indice = indice;
	}
	
	public Integer getCc() {
		return cc;
	}
	
	public void setCc(Integer cc) {
		this.cc = cc;
	}
	
	public void setIsExit(Integer isExit) {
		this.isExit = isExit;
	}
	
	public Integer getIsExit() {
		return this.isExit;
	}
	
	public boolean isExit() {
		return (!(isExit.intValue() == 0));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cc == null) ? 0 : cc.hashCode());
		result = prime * result
				+ ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((indice == null) ? 0 : indice.hashCode());
		result = prime * result + ((isExit == null) ? 0 : isExit.hashCode());
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((referencias == null) ? 0 : referencias.hashCode());
		result = prime * result
				+ ((sentencias == null) ? 0 : sentencias.hashCode());
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
		MODParrafo other = (MODParrafo) obj;
		if (cc == null) {
			if (other.cc != null)
				return false;
		} else if (!cc.equals(other.cc))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (indice == null) {
			if (other.indice != null)
				return false;
		} else if (!indice.equals(other.indice))
			return false;
		if (isExit == null) {
			if (other.isExit != null)
				return false;
		} else if (!isExit.equals(other.isExit))
			return false;
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (referencias == null) {
			if (other.referencias != null)
				return false;
		} else if (!referencias.equals(other.referencias))
			return false;
		if (sentencias == null) {
			if (other.sentencias != null)
				return false;
		} else if (!sentencias.equals(other.sentencias))
			return false;
		return true;
	}

}
