package com.jgg.sdp.domain.web;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="WEB_LABELS")
public class WEBLabel implements Serializable {

	private static final long serialVersionUID = -7238511830164715142L;

	public static final String listByGroup = "Select m FROM WEBLabel m WHERE idGroup = ?1";
	public static final String listAll     = "Select m FROM WEBLabel m";	

	@Id
	@Column(name="idLang")
	String idLang;

	@Id	
	@Column(name="idDialect")
	String idDialect;
	
	@Id	
	@Column(name="txtKey")
	String txtKey;

	@Column(name="txtValue")
	String txtValue;

	@Column(name="idGroup")
	Integer idGroup;

	public String getIdLang() {
		return idLang;
	}

	public void setIdLang(String idLang) {
		this.idLang = idLang;
	}

	public String getIdDialect() {
		return idDialect;
	}

	public void setIdDialect(String idDialect) {
		this.idDialect = idDialect;
	}

	public String getTxtKey() {
		return txtKey;
	}

	public void setTxtKey(String txtKey) {
		this.txtKey = txtKey;
	}

	public String getTxtValue() {
		return txtValue;
	}

	public void setTxtValue(String txtValue) {
		this.txtValue = txtValue;
	}

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
		result = prime * result + ((txtKey == null) ? 0 : txtKey.hashCode());
		result = prime * result + ((txtValue == null) ? 0 : txtValue.hashCode());
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
		WEBLabel other = (WEBLabel) obj;
		if (idDialect == null) {
			if (other.idDialect != null)
				return false;
		} else if (!idDialect.equals(other.idDialect))
			return false;
		if (idGroup == null) {
			if (other.idGroup != null)
				return false;
		} else if (!idGroup.equals(other.idGroup))
			return false;
		if (idLang == null) {
			if (other.idLang != null)
				return false;
		} else if (!idLang.equals(other.idLang))
			return false;
		if (txtKey == null) {
			if (other.txtKey != null)
				return false;
		} else if (!txtKey.equals(other.txtKey))
			return false;
		if (txtValue == null) {
			if (other.txtValue != null)
				return false;
		} else if (!txtValue.equals(other.txtValue))
			return false;
		return true;
	}
	
	
}
