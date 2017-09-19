package com.jgg.sdp.domain.core;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SDP_SOURCES")
public class SDPSource implements Serializable {

	private static final long serialVersionUID = 3857865477258463190L;

    public final static String findById = 
           "SELECT m FROM SDPSource m WHERE m.idFile = ?1";
	
	@Id
	@Column(name="idFile")
	Long idFile;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Lob
	@Column(name="source")
	byte[] source;

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long id) {
		this.idFile = id;
	}

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	
}
