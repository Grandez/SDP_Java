package com.jgg.sdp.domain.batch;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_PENDING")
public class BATPending implements Serializable {

	private static final long serialVersionUID = 7393227977519260271L;

	@Id
	@Column(name="id")
	@GeneratedValue
	Integer id;

	@Column(name="idAppl")	
	Integer idAppl;
	
	@Column(name="idType")	
	Integer idType;
	
	@Column(name="oldValue")	
	String oldValue;

	@Column(name="newValue")	
	String newValue;

	@Column(name="status")	
	Integer status;

	@Column(name="uid")
	String uid;

	@Column(name="tmsBeg")
	Timestamp tmsBeg;

	@Column(name="tmsEnd")
	Timestamp tmsEnd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAppl() {
		return idAppl;
	}

	public void setIdAppl(Integer idAppl) {
		this.idAppl = idAppl;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Timestamp getTmsBeg() {
		return tmsBeg;
	}

	public void setTmsBeg(Timestamp tmsBeg) {
		this.tmsBeg = tmsBeg;
	}

	public Timestamp getTmsEnd() {
		return tmsEnd;
	}

	public void setTmsEnd(Timestamp tmsEnd) {
		this.tmsEnd = tmsEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAppl == null) ? 0 : idAppl.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tmsBeg == null) ? 0 : tmsBeg.hashCode());
		result = prime * result + ((tmsEnd == null) ? 0 : tmsEnd.hashCode());
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
		BATPending other = (BATPending) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idAppl == null) {
			if (other.idAppl != null)
				return false;
		} else if (!idAppl.equals(other.idAppl))
			return false;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tmsBeg == null) {
			if (other.tmsBeg != null)
				return false;
		} else if (!tmsBeg.equals(other.tmsBeg))
			return false;
		if (tmsEnd == null) {
			if (other.tmsEnd != null)
				return false;
		} else if (!tmsEnd.equals(other.tmsEnd))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
	
}
