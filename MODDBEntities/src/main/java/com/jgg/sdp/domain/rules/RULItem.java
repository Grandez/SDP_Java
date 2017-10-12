package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_ITEMS")
public class RULItem implements Serializable {

	private static final long serialVersionUID = -3035001051783710425L;

	public static final String delItemsOfGroup = "DELETE FROM RULItem i where i.idGroup = ?1";
	
    public static final String findByTextKey   = "SELECT i FROM RULItem i WHERE i.clave  = ?1";
    public static final String findById        = "SELECT i FROM RULItem i WHERE i.idGroup = ?1 AND i.idItem = ?1";    
	public static final String findMaxId       = "SELECT MAX(i.idItem) FROM RULItem i WHERE idGroup = ?1";
	
	public static final String listAll           = "SELECT i FROM RULItem i ";
	public static final String listActive        = "SELECT i FROM RULItem i WHERE i.activo = 0";
	public static final String listByGroup       = "SELECT i FROM RULItem i WHERE i.idGroup = ?1";
	public static final String listActiveByGroup = "SELECT i FROM RULItem i WHERE i.idGroup = ?1 AND i.activo = 0";
	
	@Id
	@Column(name="idGroup")
	private Long idGroup;

	@Id
	@Column(name="idItem")
	private Long idItem;

	@Column(name="activo")
	private Integer activo;
	
	@Column(name="idDesc")
	private Long idDesc;

	@Column(name="clave")
	private String clave;

	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		RULItem other = (RULItem) obj;
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (idDesc == null) {
			if (other.idDesc != null)
				return false;
		} else if (!idDesc.equals(other.idDesc))
			return false;
		if (idGroup == null) {
			if (other.idGroup != null)
				return false;
		} else if (!idGroup.equals(other.idGroup))
			return false;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
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
		return true;
	}

	
}
