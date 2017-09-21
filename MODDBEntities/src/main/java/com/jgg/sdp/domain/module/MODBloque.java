package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name="MOD_BLOQUES")
@NamedQueries ({
	@NamedQuery( name="MODBloque.list"
	            ,query="SELECT f FROM MODBloque f WHERE f.idVersion = ?1 " + 
         	           "         ORDER BY f.orden ASC")
})
public class MODBloque implements Serializable {

    public static final String findByKey =
                  "SELECT f FROM MODBloque f " +
                  "         WHERE f.idVersion = ?1 AND f.orden = ?2";

    public static final String totStmt = 
                  "SELECT SUM(b.sentencias) , SUM(b.sentencias) " +
                  "       FROM MODBloque b  " +
                  "       WHERE b.idVersion = ?1 " +
                  "       AND   b.usado <> 0";
            
	private static final long serialVersionUID = -6115919337129091168L;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="orden")
	Integer orden;

	@Column(name="lineBeg")
	Integer beg;

	@Column(name="lineEnd")
	Integer end;
	
	@Column(name="sentencias")
    Integer sentencias;

	@Column(name="usado")
	Integer usado = 0;
	
	public MODBloque() {
		
	}
	public MODBloque(Long idVersion) {
		this.idVersion = idVersion;
	}
	
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getBeg() {
		return beg;
	}

	public void setBeg(Integer beg) {
		this.beg = beg;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getSentencias() {
		return sentencias;
	}

	public void setSentencias(Integer sentencias) {
		this.sentencias = sentencias;
	}

	public Integer getUsado() {
		return usado;
	}

	public void setUsado(Integer usado) {
		this.usado = usado;
	}
	
	public boolean IsUsed() {
		return (usado != 0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beg == null) ? 0 : beg.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result
				+ ((sentencias == null) ? 0 : sentencias.hashCode());
		result = prime * result + ((usado == null) ? 0 : usado.hashCode());
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
		MODBloque other = (MODBloque) obj;
		if (beg == null) {
			if (other.beg != null)
				return false;
		} else if (!beg.equals(other.beg))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (sentencias == null) {
			if (other.sentencias != null)
				return false;
		} else if (!sentencias.equals(other.sentencias))
			return false;
		if (usado == null) {
			if (other.usado != null)
				return false;
		} else if (!usado.equals(other.usado))
			return false;
		return true;
	}
	
	
}
