package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_GROUPS")
public class RULGroup implements Serializable {

	private static final long serialVersionUID = 2429660515271690865L;

	public static final String delGroup       = "DELETE FROM RULGroup g where g.idGroup = ?1";
    
    public static final String findByTextKey  = "SELECT r FROM RULGroup r WHERE r.prefix  = ?1";
    public static final String findById       = "SELECT r FROM RULGroup r WHERE r.idGroup = ?1";    
    public static final String findActiveById = "SELECT r FROM RULGroup r WHERE r.idGroup = ?1 AND r.active > 0";    
    public static final String findByName     = "SELECT r FROM RULGroup r WHERE r.idName  = ?1";    
	public static final String findMaxId      = "SELECT MAX(r.idGroup) FROM RULGroup r";
	
    public static final String listAll             = "SELECT r FROM RULGroup r";
	public static final String listActive          = "SELECT r FROM RULGroup r WHERE r.active   >= 0";
	public static final String listChildren        = "SELECT r FROM RULGroup r WHERE r.idParent  = ?1";
	public static final String listChildrenActive  = "SELECT r FROM RULGroup r WHERE r.idParent  = ?1 AND r.active > 0";
	
	@Id
	@Column(name="idGroup")
	private Long idGroup;

	@Column(name="idParent")
	private Long idParent;

	@Column(name="active")
	private Long active;
	
	@Column(name="idDesc")
	private Long idDesc;

	@Column(name="idName")
	private String idName;
	
    @Column(name="prefix")
    private String prefix;

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

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idName == null) ? 0 : idName.hashCode());
		result = prime * result + ((idParent == null) ? 0 : idParent.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		RULGroup other = (RULGroup) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
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
		if (idName == null) {
			if (other.idName != null)
				return false;
		} else if (!idName.equals(other.idName))
			return false;
		if (idParent == null) {
			if (other.idParent != null)
				return false;
		} else if (!idParent.equals(other.idParent))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
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
