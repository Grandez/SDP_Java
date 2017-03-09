package com.jgg.sdp.domain.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SDP_REL_APP_MOD")
public class SDPRelModuloApp implements Serializable {

	private static final long serialVersionUID = 8648504910952544472L;

    public final static String findByMask =
            "SELECT c FROM SDPRelModuloApp c  WHERE c.mask = ?1";
    public final static String getMasks =
             "SELECT c FROM SDPRelModuloApp c " +
                            "ORDER BY c.peso ASC, c.fijo DESC";

	
	@Id
	@Column(name="idAppl")
	Long idAppl;

	@Id
	@Column(name="mask")
	String mask;

	@Column(name="fijo")
	Integer fijo;

	@Column(name="peso")
	Integer peso;
	
	@Column(name="uid")
	String uid;

	public Long getIdAppl() {
		return idAppl;
	}

	public void setIdAppl(Long idAppl) {
		this.idAppl = idAppl;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public Integer getFijo() {
		return fijo;
	}

	public void setFijo(Integer fijo) {
		this.fijo = fijo;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fijo == null) ? 0 : fijo.hashCode());
		result = prime * result + ((idAppl == null) ? 0 : idAppl.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
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
		SDPRelModuloApp other = (SDPRelModuloApp) obj;
		if (fijo == null) {
			if (other.fijo != null)
				return false;
		} else if (!fijo.equals(other.fijo))
			return false;
		if (idAppl == null) {
			if (other.idAppl != null)
				return false;
		} else if (!idAppl.equals(other.idAppl))
			return false;
		if (mask == null) {
			if (other.mask != null)
				return false;
		} else if (!mask.equals(other.mask))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
	
}
