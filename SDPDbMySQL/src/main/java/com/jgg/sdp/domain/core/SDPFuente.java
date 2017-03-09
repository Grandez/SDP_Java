package com.jgg.sdp.domain.core;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SDP_FUENTES")
public class SDPFuente implements Serializable {

	private static final long serialVersionUID = 3857865477258463190L;

    public final static String find = 
           "SELECT m FROM MODFuente m WHERE m.idFile = ?1";
	
	@Id
	@Column(name="idFile")
	Long idFile;

	@Lob
	@Column(name="source")
	byte[] source;

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long id) {
		this.idFile = id;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

}
