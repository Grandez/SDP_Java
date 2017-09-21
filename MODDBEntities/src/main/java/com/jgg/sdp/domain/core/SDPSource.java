package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name="SDP_SOURCES")
public class SDPSource implements Serializable {

	private static final long serialVersionUID = 3857865477258463190L;

    public final static String findById = 
           "SELECT m FROM SDPSource m WHERE m.idFile = ?1 AND m.idVersion = ?2";
	
	@Id
	@Column(name="idFile")
	Long idFile;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Column(name="encoded")
	String encoded;
	
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

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encoded == null) ? 0 : encoded.hashCode());
		result = prime * result + ((idFile == null) ? 0 : idFile.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + Arrays.hashCode(source);
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
		SDPSource other = (SDPSource) obj;
		if (encoded == null) {
			if (other.encoded != null)
				return false;
		} else if (!encoded.equals(other.encoded))
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
		if (!Arrays.equals(source, other.source))
			return false;
		return true;
	}

}
