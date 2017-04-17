package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="RUL_GROUPS")
public class RULGroup implements Serializable {

    private static final long serialVersionUID = 5691054216369129050L;

    public static String listAll    = "SELECT r FROM RULGroup r";
	public static String listActive = "SELECT r FROM RULGroup r WHERE r.activo = 1";
	
	@Id
	@Column(name="idGroup")
	private Integer idGroup;

	@Column(name="idParent")
	private Integer idParent;

	@Column(name="active")
	private Integer active;
	
	@Column(name="idDesc")
	private Integer idDesc;

    @Column(name="prefix")
    private String prefix;

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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getIdDesc() {
        return idDesc;
    }

    public void setIdDesc(Integer idDesc) {
        this.idDesc = idDesc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((idDesc == null) ? 0 : idDesc.hashCode());
        result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
        result = prime * result + ((idParent == null) ? 0 : idParent.hashCode());
        result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
        return true;
    }
    
    
	
}
