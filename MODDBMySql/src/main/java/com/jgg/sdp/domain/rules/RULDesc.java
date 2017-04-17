package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_DESC")
public class RULDesc implements Serializable {

	private static final long serialVersionUID = -8250377074272339218L;

	public static final String getDescription = "SELECT r FROM RULDesc r where r.cdgMsg = ?1 AND r.idLang = ?2 AND r.idDialect = ?3";
	
	@Id
	@Column(name="idDesc")
	private Integer idDesc;

	@Id
	@Column(name="idLang")
	private String idLang;

	@Id
	@Column(name="idDialect")
	private String idDialect;

	@Column(name="msg")
	private String msg;

	public Integer getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Integer idDesc) {
		this.idDesc = idDesc;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
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
		RULDesc other = (RULDesc) obj;
		if (idDesc == null) {
			if (other.idDesc != null)
				return false;
		} else if (!idDesc.equals(other.idDesc))
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
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}
	
	
}
