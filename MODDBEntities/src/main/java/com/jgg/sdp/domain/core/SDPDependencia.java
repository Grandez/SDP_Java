package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_DEPENDENCIAS")
public class SDPDependencia  implements Serializable {

	public final static String listByModule = 
		   "SELECT d FROM SDPDependencia d WHERE d.modulo = ?1";
	public final static String listByVariable = 
		   "SELECT d FROM SDPDependencia d WHERE d.modulo = ?1 AND d.variable = ?2";
	public final static String deleteByModule   = 
		   "DELETE FROM SDPDependencia d WHERE d.modulo = ?1";
	public final static String deleteByVariable = 
		   "DELETE FROM SDPDependencia d WHERE d.modulo = ?1 AND d.variable = ?2";
	public final static String deleteByCalled = 
		   "DELETE FROM SDPDependencia d WHERE d.modulo = ?1 AND d.variable = ?2 AND d.called = ?3";
	
	private static final long serialVersionUID = -8926401563851874819L;

	@Id
	@Column(name="modulo")	
	private String modulo;
	
	@Id
	@Column(name="variable")
	private String variable;
	
	@Id
	@Column(name="called")
	private String called;
	
	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Timestamp getTms() {
		return tms;
	}

	public void setTms(Timestamp tms) {
		this.tms = tms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((called == null) ? 0 : called.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
		SDPDependencia other = (SDPDependencia) obj;
		if (called == null) {
			if (other.called != null)
				return false;
		} else if (!called.equals(other.called))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (tms == null) {
			if (other.tms != null)
				return false;
		} else if (!tms.equals(other.tms))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
	
}
