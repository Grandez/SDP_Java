package com.jgg.sdp.domain.rules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="RUL_DESCS")
public class RULDesc implements Serializable {

	private static final long serialVersionUID = -3131157749900685955L;

	public static final String getDescription   = "SELECT r FROM RULDesc r where r.idDesc = ?1 " +
	                                              "         AND r.idType = ?2 AND r.idLang = ?3 AND r.idDialect = ?4 " +
			                                      "         ORDER BY r.idSeq";
	public static final String delDescription    = "DELETE   FROM RULDesc r where r.idDesc = ?1 AND r.idType = ?2";	
	public static final String delDescriptions   = "DELETE   FROM RULDesc r where r.idDesc = ?1";
	
	@Id
	@Column(name="idDesc")
	private Long idDesc;

	@Id
	@Column(name="idType")
	private Integer idType;
	
	@Id
	@Column(name="idSeq")
	private Integer idSeq;
	
	@Id
	@Column(name="idLang")
	private String idLang;

	@Id
	@Column(name="idDialect")
	private String idDialect;

	@Column(name="txt")
	private String txt;

	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
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
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
		result = prime * result + ((idSeq == null) ? 0 : idSeq.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
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
		if (idSeq == null) {
			if (other.idSeq != null)
				return false;
		} else if (!idSeq.equals(other.idSeq))
			return false;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		if (txt == null) {
			if (other.txt != null)
				return false;
		} else if (!txt.equals(other.txt))
			return false;
		return true;
	}

	
}
