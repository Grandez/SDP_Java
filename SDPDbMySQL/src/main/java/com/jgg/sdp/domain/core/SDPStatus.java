package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SDP_STATUS")
@NamedQueries({
    @NamedQuery( name="SDPStatus.ModulosPendientes" 
                ,query="Select m FROM SDPStatus m WHERE m.estado = 0 AND m.tms > ?1")
   ,@NamedQuery( name="SDPStatus.ModulosCorrectos" 
                ,query="Select m FROM SDPStatus m WHERE m.estado > 0 AND m.tms > ?1")
   ,@NamedQuery( name="SDPStatus.ModulosManuales" 
                ,query="Select m FROM SDPStatus m WHERE m.estado > 1 AND m.tms > ?1")

})
public class SDPStatus implements Serializable {

    private static final long serialVersionUID = 7365928678976277401L;

    @Id
    @Column(name="idModulo")
    Long idModulo;
    
    @Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Column(name="nombre")
    String nombre;
    
    @Column(name="estado")
    Integer estado;

    @Column(name="uid")
    String uid;
    
    @Column(name="tms")
    Timestamp tms;

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public Long getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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
    
    public void setTms(long tms) {
        this.tms = new Timestamp(tms);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result
                + ((idModulo == null) ? 0 : idModulo.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
        SDPStatus other = (SDPStatus) obj;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (idModulo == null) {
            if (other.idModulo != null)
                return false;
        } else if (!idModulo.equals(other.idModulo))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
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
