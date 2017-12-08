package com.jgg.sdp.domain.cfg;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CFG_CODES")
public class CFGCode implements Serializable {

	private static final long serialVersionUID = 8841821411361702758L;

	public final static String getGrupo = "SELECT c FROM CFGCode c " +
	                                      "         WHERE c.grp = ?1 AND c.idLang = ?2 AND c.idDialect = ?3 " +
 			                              "         ORDER BY c.code";
	public final static String getMessage = 
			"SELECT c FROM CFGCode c " + 
			"         WHERE c.grp = ?1 AND c.code = ?2 AND c.idLang = ?3 AND c.idDialect = ?4 ";

	@Id
	@Column(name="grp")
	Integer grp;

    @Id
	@Column(name="code")
	Integer code;

    @Id
	@Column(name="idLang")
	String idLang;

    @Id
	@Column(name="idDialect")
	String idDialect;
    
	@Column(name="data")
	String data;

	public Integer getGrp() {
		return grp;
	}

	public void setGrp(Integer grp) {
		this.grp = grp;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((grp == null) ? 0 : grp.hashCode());
		result = prime * result + ((idDialect == null) ? 0 : idDialect.hashCode());
		result = prime * result + ((idLang == null) ? 0 : idLang.hashCode());
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
		CFGCode other = (CFGCode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (grp == null) {
			if (other.grp != null)
				return false;
		} else if (!grp.equals(other.grp))
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
		return true;
	}

	
}
