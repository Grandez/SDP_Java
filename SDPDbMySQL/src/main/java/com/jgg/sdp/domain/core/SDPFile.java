package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_FILES")
public class SDPFile implements Serializable {

	public static final String findByNameAndType = 
		   "SELECT f  FROM SDPFile f WHERE f.archivo = ?1 AND f.tipo = ?2";

	public static final String deleteByNameAndType = 
			   "DELETE  FROM SDPFile f WHERE f.archivo = ?1 AND f.tipo = ?2";
	
	private static final long serialVersionUID = -8884394016232675188L;

	@Id
	@Column(name="idFile")
	Long idFile;

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
	
	
}
