package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_FILES")
public class SDPFile implements Serializable {

	public static final String findById =  "SELECT f FROM SDPFile f WHERE f.idFile = ?1 ORDER BY f.idVersion DESC";

	public static final String findByNameAndType = 
		   "SELECT f  FROM SDPFile f WHERE f.archivo = ?1 AND f.tipo = ?2";

	public static final String deleteByNameAndType = 
			   "DELETE  FROM SDPFile f WHERE f.archivo = ?1 AND f.tipo = ?2";
	
	private static final long serialVersionUID = -8884394016232675188L;

	@Id
	@Column(name="idFile")
	Long idFile;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Column(name="archivo")
	String archivo;

	@Column(name="tipo")
	Integer tipo;
	
	@Column(name="firma")
	String firma;
	
	@Column(name="numModulos")
	Integer numModulos;

	@Column(name="estado")
	Integer estado;
	
	@Column(name="uid")
	String uid;
	
	@Column(name="tms")
	Timestamp tms;

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Integer getNumModulos() {
		return numModulos;
	}

	public void setNumModulos(Integer numModulos) {
		this.numModulos = numModulos;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
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
		result = prime * result + ((archivo == null) ? 0 : archivo.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((firma == null) ? 0 : firma.hashCode());
		result = prime * result + ((idFile == null) ? 0 : idFile.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((numModulos == null) ? 0 : numModulos.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		SDPFile other = (SDPFile) obj;
		if (archivo == null) {
			if (other.archivo != null)
				return false;
		} else if (!archivo.equals(other.archivo))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (firma == null) {
			if (other.firma != null)
				return false;
		} else if (!firma.equals(other.firma))
			return false;
		if (idFile == null) {
			if (other.idFile != null)
				return false;
		} else if (!idFile.equals(other.idFile))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (numModulos == null) {
			if (other.numModulos != null)
				return false;
		} else if (!numModulos.equals(other.numModulos))
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
		return true;
	}
	
}
