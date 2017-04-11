package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_APLICACIONES")
@NamedQueries({
	@NamedQuery( name="SDPAplicacion.find"
                ,query="SELECT a FROM SDPAplicacion a WHERE a.id = ?1")
   ,@NamedQuery( name="SDPAplicacion.findByName" 
                ,query="SELECT a FROM SDPAplicacion a WHERE a.aplicacion = ?1")
   ,@NamedQuery( name="SDPAplicacion.listByPadreId" 
                ,query="SELECT a FROM SDPAplicacion a WHERE a.padre = ?1")
})
public class SDPAplicacion implements Serializable {

	private static final long serialVersionUID = 926785694196293106L;

	public static final String listaHijos = "Select id FROM SDP_APLICACIONES WHERE padre = ?1";
	public static final String updVolumen = 
	       "UPDATE SDP_APLICACIONES SET volumen = volumen + 1 WHERE aplicacion = ?1";
	public static final String maxAppId = 
		       "SELECT MAX(a.idAppl)  FROM SDP_APLICACIONES a";
	
	@Id
	@Column(name="aplicacion")
	String aplicacion;

	@Column(name="idAppl")
	Long id;
	
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
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
