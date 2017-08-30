package com.jgg.sdp.domain.cfg;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ATEST")
public class ATest {

	public static final String listAll = "SELECT c FROM ATest c";
	
	@Id
	@Column(name="clave")
	private String clave;

	@Column(name="tms")
	Timestamp tms;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Timestamp getTms() {
		return tms;
	}

	public void setTms(Timestamp tms) {
		this.tms = tms;
	}


}
