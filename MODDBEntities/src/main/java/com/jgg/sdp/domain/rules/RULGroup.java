package com.jgg.sdp.domain.rules;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="RUL_GROUPS")
public class RULGroup implements Serializable {

    private static final long serialVersionUID = 5691054216369129050L;

    public static final String delGroup      = "DELETE FROM RULGroup g where g.idGroup = ?1";
    
    public static final String findByTextKey = "SELECT r FROM RULGroup r WHERE r.prefix  = ?1";
    public static final String findById      = "SELECT r FROM RULGroup r WHERE r.idGroup = ?1";    
	public static final String findMaxId     = "SELECT MAX(r.idGroup) FROM RULGroup r";
	
    public static final String listAll       = "SELECT r FROM RULGroup r";
	public static final String listActive    = "SELECT r FROM RULGroup r WHERE r.activo = 0";

	
	@Id
	@Column(name="idGroup")
	private Long idGroup;

	@Column(name="idParent")
	private Long idParent;

	@Column(name="activo")
	private Integer activo;
	
	@Column(name="idDesc")
	private Long idDesc;

    @Column(name="prefix")
    private String prefix;

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
        result = prime * result + ((activo == null) ? 0 : activo.hashCode());
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
        if (activo == null) {
            if (other.activo != null)
                return false;
        } else if (!activo.equals(other.activo))
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
