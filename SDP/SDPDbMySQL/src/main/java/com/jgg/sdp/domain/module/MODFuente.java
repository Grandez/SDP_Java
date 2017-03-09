package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_FUENTE")
@NamedQueries({
     @NamedQuery(name="MODFuente.find",
                 query="SELECT m FROM MODFuente m WHERE m.idVersion = ?1")
}) 

public class MODFuente implements Serializable {

	private static final long serialVersionUID = 3857865477258463190L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Lob
	@Column(name="source")
	byte[] source;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long id) {
		this.idVersion = id;
	}

	public byte[] getSource() {
		return source;
	}

	public void setSource(byte[] source) {
		this.source = source;
	}

	
	
}
