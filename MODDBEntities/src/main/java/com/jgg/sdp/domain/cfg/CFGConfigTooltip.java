package com.jgg.sdp.domain.cfg;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CFG_TOOLTIP")
public class CFGConfigTooltip implements Serializable {

	private static final long serialVersionUID = -4296071831723433486L;

	private static final String qryBase = "FROM CFGConfigTooltip c WHERE c.clave = ?1 AND c.idLang = ?2 AND c.idDialect = ?3";
	public  static final String getTooltip = "SELECT c " + qryBase;
	public  static final String delTooltip = "DELETE   " + qryBase;
	public  static final String delAll     = "DELETE   FROM CFGConfigTooltip c";		
	
	@Id
	@Column(name="clave")
	private String clave;

	@Id	
	@Column(name="idLang")
	private String idLang;

	@Id	
	@Column(name="idDialect")
	private String idDialect;

	@Column(name="tooltip")
	private String tooltip;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

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

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
		result = prime * result + ((tooltip == null) ? 0 : tooltip.hashCode());
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
		CFGConfigTooltip other = (CFGConfigTooltip) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (idDialect == null) {
			if (other.idDialect != null)
				return false;
		} else if (!idDialect.equals(other.idDialect))
			return false;
		if (idLang == null) {
			if (other.idLang != null)
				return false;
		} else if (!idLang.equals(other.idLang))
			return false;
		if (tooltip == null) {
			if (other.tooltip != null)
				return false;
		} else if (!tooltip.equals(other.tooltip))
			return false;
		return true;
	}

	
}
