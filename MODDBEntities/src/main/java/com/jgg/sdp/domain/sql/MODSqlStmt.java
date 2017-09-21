package com.jgg.sdp.domain.sql;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.*;

@Entity
@Table(name="MOD_SQL_STMT")
public class MODSqlStmt implements Serializable {

	private static final long serialVersionUID = -1723415647879666310L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="begLine")
	Integer begLine;

	@Column(name="firma")
	String firma;
	
	@Lob
	@Column(name="stmt")
	byte[] stmt;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getBegLine() {
		return begLine;
	}

	public void setBegLine(Integer begLine) {
		this.begLine = begLine;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public byte[] getStmt() {
		return stmt;
	}

	public void setStmt(byte[] stmt) {
		this.stmt = stmt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begLine == null) ? 0 : begLine.hashCode());
		result = prime * result + ((firma == null) ? 0 : firma.hashCode());
		result = prime * result + Arrays.hashCode(stmt);
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
		MODSqlStmt other = (MODSqlStmt) obj;
		if (begLine == null) {
			if (other.begLine != null)
				return false;
		} else if (!begLine.equals(other.begLine))
			return false;
		if (firma == null) {
			if (other.firma != null)
				return false;
		} else if (!firma.equals(other.firma))
			return false;
		if (!Arrays.equals(stmt, other.stmt))
			return false;
		return true;
	}

	
}
