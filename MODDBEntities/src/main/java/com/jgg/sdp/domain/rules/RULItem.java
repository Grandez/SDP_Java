package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="RUL_ITEMS")
public class RULItem implements Serializable, IRule {

	private static final long serialVersionUID = 6458200298367309227L;

	public static final String delItem         = "DELETE FROM RULItem i where i.idGroup = ?1 AND i.idItem = ?2";
	public static final String delItemsOfGroup = "DELETE FROM RULItem i where i.idGroup = ?1";
	
    public static final String findByName      = "SELECT i FROM RULItem i WHERE i.name  = ?1";
    public static final String findById        = "SELECT i FROM RULItem i WHERE i.idGroup = ?1 AND i.idItem = ?2";    
    public static final String findByObject    = "SELECT i FROM RULItem i WHERE i.object = ?1";    
	public static final String findMaxId       = "SELECT MAX(i.idItem) FROM RULItem i WHERE idGroup = ?1";
	
	public static final String listAll           = "SELECT i FROM RULItem i ";
	public static final String listActive        = "SELECT i FROM RULItem i WHERE i.active >= 0";
	public static final String listByGroup       = "SELECT i FROM RULItem i WHERE i.idGroup = ?1";
	public static final String listActiveByGroup = "SELECT i FROM RULItem i WHERE i.idGroup = ?1 AND i.active >= 0";
	public static final String listActiveByName  = "SELECT i FROM RULItem i WHERE i.object  = ?1 AND i.active >= 0";
	
	@Id
	@Column(name="idGroup")
	private Long idGroup;

	@Id
	@Column(name="idItem")
	private Long idItem;

	@Column(name="idDesc")
	private Long idDesc;

	@Column(name="idTitle")
	private Long idTitle;

	@Column(name="idMsg")
	private Long idMsg;
	
	@Column(name="object")
	private String object;

	@Column(name="name")
	private String name;

    @Column(name="prefix")
    private String prefix;
	
	@Column(name="idSample")
	private Long idSample;
	
	@Column(name="active")
	private Long active;
	
	@Column(name="type")
	private Integer type;

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
	
	public Long getIdTitle() {
		return idTitle;
	}

	public void setIdTitle(Long idTitle) {
		this.idTitle = idTitle;
	}

	public Long getIdMsg() {
		return idMsg;
	}

	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
	}
	
	public Long getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(Long idDesc) {
		this.idDesc = idDesc;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}
	
	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		result = prime * result + ((idMsg == null) ? 0 : idMsg.hashCode());
		result = prime * result + ((idSample == null) ? 0 : idSample.hashCode());
		result = prime * result + ((idTitle == null) ? 0 : idTitle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		if (idMsg == null) {
			if (other.idMsg != null)
				return false;
		} else if (!idMsg.equals(other.idMsg))
			return false;
		if (idSample == null) {
			if (other.idSample != null)
				return false;
		} else if (!idSample.equals(other.idSample))
			return false;
		if (idTitle == null) {
			if (other.idTitle != null)
				return false;
		} else if (!idTitle.equals(other.idTitle))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

}
