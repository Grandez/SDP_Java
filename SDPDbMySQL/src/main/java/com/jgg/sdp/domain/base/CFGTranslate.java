package com.jgg.sdp.domain.base;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CFG_TRANSLATE")
public class CFGTranslate implements Serializable {

	private static final long serialVersionUID = 8505778582225118411L;

	public static final String GetTranslationValue = 
			"SELECT t FROM CFGTranslate t WHERE t.idField = ?1 AND t.idValue = ?2 AND t.lang = ?3";
	public static final String GetMask = 
			"SELECT t FROM CFGTranslate t WHERE t.idField = ?1 AND t.idValue > 250 AND t.lang = ?2";
	
    @Id
	@Column(name="idField")
	String idField;

    @Id
	@Column(name="idValue")
	Integer idValue;
    
    @Id
	@Column(name="lang")
	String lang;
    
    @Id
	@Column(name="msg")
	String msg;

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public Integer getIdValue() {
		return idValue;
	}

	public void setIdValue(Integer idValue) {
		this.idValue = idValue;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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
		result = prime * result + ((idField == null) ? 0 : idField.hashCode());
		result = prime * result + ((idValue == null) ? 0 : idValue.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
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
		CFGTranslate other = (CFGTranslate) obj;
		if (idField == null) {
			if (other.idField != null)
				return false;
		} else if (!idField.equals(other.idField))
			return false;
		if (idValue == null) {
			if (other.idValue != null)
				return false;
		} else if (!idValue.equals(other.idValue))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}
	
}
