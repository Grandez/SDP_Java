package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_ITEMS")
public class RULItem implements Serializable {

	private static final long serialVersionUID = 1057811313977487107L;

	public static String listAll           = "SELECT i FROM RULItem i ";
	public static String listActive        = "SELECT i FROM RULItem i WHERE i.activo = 1";
	public static String listByGroup       = "SELECT i FROM RULItem i WHERE i.idGroup = ?1";
	public static String listActiveByGroup = "SELECT i FROM RULItem i WHERE i.idGroup = ?1 AND i.activo = 1";
	
	@Id
	@Column(name="idGroup")
	private Integer idGroup;

	@Id
	@Column(name="idItem")
	private Integer idItem;

	@Column(name="activo")
	private Integer activo;
	
	@Column(name="keyNum")
	private Integer keyNum;

	@Column(name="keyTxt")
	private String keyTxt;

	@Column(name="uid")
	private String uid;
	
	@Column(name="tms")
	private Timestamp tms;

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getKeyNum() {
		return keyNum;
	}

	public void setKeyNum(Integer keyNum) {
		this.keyNum = keyNum;
	}

	public String getKeyTxt() {
		return keyTxt;
	}

	public void setKeyTxt(String keyTxt) {
		this.keyTxt = keyTxt;
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
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((keyNum == null) ? 0 : keyNum.hashCode());
		result = prime * result + ((keyTxt == null) ? 0 : keyTxt.hashCode());
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
		if (keyNum == null) {
			if (other.keyNum != null)
				return false;
		} else if (!keyNum.equals(other.keyNum))
			return false;
		if (keyTxt == null) {
			if (other.keyTxt != null)
				return false;
		} else if (!keyTxt.equals(other.keyTxt))
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
