package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_APLICACIONES")
public class SDPAplicacion implements Serializable {

	private static final long serialVersionUID = 926785694196293106L;

	public static final String listChildrens = "Select a FROM SDPAplicacion a WHERE padre = ?1";
	
	public static final String updVolumen = 
	       "UPDATE SDP_APLICACIONES SET volumen = volumen + 1 WHERE aplicacion = ?1";
	public static final String maxAppId = 
		       "SELECT MAX(a.idAppl)  FROM SDPAplicacion a";
	public static final String findByName = 
		       "SELECT a  FROM SDPAplicacion a WHERE aplicacion = ?1";
	
	@Id
	@Column(name="aplicacion")
	String aplicacion;

	@Column(name="idAppl")
	Long idAppl;
	
	@Column(name="descripcion")
	String Descripcion;

	@Column(name="padre")
	Long padre;

	@Column(name="volumen")
	Integer volumen;

	@Column(name="uid")
	String uid;
	
	@Column(name="tms")
	Timestamp tms;

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Long getId() {
		return idAppl;
	}

	public void setId(Long id) {
		this.idAppl = id;
	}

	public Long getPadre() {
		return padre;
	}

	public void setPadre(Long padre) {
		this.padre = padre;
	}

	public Integer getVolumen() {
		return volumen;
	}

	public void setVolumen(Integer volumen) {
		this.volumen = volumen;
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
