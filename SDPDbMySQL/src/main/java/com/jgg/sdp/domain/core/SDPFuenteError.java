package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_FUENTES_ERR")
public class SDPFuenteError implements Serializable {

	private static final long serialVersionUID = 3857865477258463190L;

	@Id
	@Column(name="tms")
	Timestamp tms;

	@Column(name="uid")
	String uid;
	
	@Column(name="nombre")
	String nombre;
	
	@Lob
	@Column(name="source")
	byte[] source;


	public Timestamp getTms() {
		return tms;
	}

	public void setTms(Timestamp tms) {
		this.tms = tms;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}	
	
}
