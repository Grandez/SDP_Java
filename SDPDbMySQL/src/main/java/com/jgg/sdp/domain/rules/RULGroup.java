package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="RUL_GROUPS")
public class RULGroup implements Serializable {

	private static final long serialVersionUID = -3140707351185870104L;

	public static String listAll    = "SELECT r FROM RULGroup r";
	public static String listActive = "SELECT r FROM RULGroup r WHERE r.activo = 1";
	
	@Id
	@Column(name="idGroup")
	private Integer idGroup;

	@Column(name="idParent")
	private Integer idParent;

	@Column(name="activo")
	private Integer activo;
	
	@Column(name="idName")
	private String idName;

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getIdParent() {
		return idParent;
	}

	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
		result = prime * result + ((idName == null) ? 0 : idName.hashCode());
		result = prime * result + ((idParent == null) ? 0 : idParent.hashCode());
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
		return true;
	}

	
}
