package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_MSGS")
public class RULMessage implements Serializable {

	private static final long serialVersionUID = -3131157749900685955L;
	
	public static final String delMessages = "DELETE   FROM RULMessage m WHERE m.idMsg = ?1";
	public static final String getMessage  = "SELECT m FROM RULMessage m WHERE m.idMsg = ?1 " +
	                                              "         AND m.idLang = ?2 AND m.idDialect = ?3 ";

	@Id
	@Column(name="idMsg")
	private Long idMsg;

	@Id
	@Column(name="idLang")
	private String idLang;

	@Id
	@Column(name="idDialect")
	private String idDialect;

	@Column(name="txt")
	private String txt;

	public Long getIdMsg() {
		return idMsg;
	}

	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
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

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
		result = prime * result + ((idMsg == null) ? 0 : idMsg.hashCode());
		result = prime * result + ((txt == null) ? 0 : txt.hashCode());
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
		RULMessage other = (RULMessage) obj;
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
		if (idMsg == null) {
			if (other.idMsg != null)
				return false;
		} else if (!idMsg.equals(other.idMsg))
			return false;
		if (txt == null) {
			if (other.txt != null)
				return false;
		} else if (!txt.equals(other.txt))
			return false;
		return true;
	}

	
}
