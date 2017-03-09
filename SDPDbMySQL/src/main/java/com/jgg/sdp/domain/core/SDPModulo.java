package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.jgg.sdp.core.tools.Fechas;

@Entity
@Table(name="SDP_MODULOS")
@NamedQueries({
	@NamedQuery( name="SDPModulo.ModulosPorAplicacion" 
                ,query="Select m FROM SDPModulo m WHERE m.idAppl = ?1 AND m.activo = 1 ORDER BY m.nombre")
   ,@NamedQuery( name="SDPModulo.CuentaPorAplicacion" 
                ,query="Select COUNT(m.idAppl) FROM SDPModulo m WHERE m.idAppl = ?1 AND m.activo = 1")
   ,@NamedQuery( name="SDPModulo.ModulosModificados" 
                ,query="Select m FROM SDPModulo m WHERE m.tms > ?1")
   ,@NamedQuery( name="SDPModulo.ModulosActivos" 
                ,query="Select m FROM SDPModulo m WHERE m.activo = 1")
   ,@NamedQuery( name="SDPModulo.find"
                ,query="SELECT m FROM SDPModulo m WHERE m.idModulo = ?1")   
   ,@NamedQuery( name="SDPModulo.findByName"
                ,query="SELECT m FROM SDPModulo m WHERE m.nombre = ?1")   
   ,@NamedQuery( name="SDPModulo.updateVersion"
                ,query="UPDATE SDPModulo m SET m.idVersion = ?2 WHERE idModulo = ?1")   
   ,@NamedQuery( name="SDPModulo.ModulosPorPseudoMascara" 
                ,query="Select m FROM SDPModulo m WHERE m.nombre LIKE ?1")
	
})
public class SDPModulo implements Serializable {

	private static final long serialVersionUID = 1240723639177247054L;

	public final static String findByNameAndType = "SELECT m FROM SDPModulo m WHERE m.nombre = ?1 AND m.tipo = ?2";   
	public final static String findByVersion     = "SELECT m FROM SDPModulo m WHERE m.idVersion = ?1";
	
	@Id
	@Column(name="idAppl")
	Long idAppl;
	
	@Id
	@Column(name="idModulo")
	Long idModulo;

	@Id
    @Column(name="tipo")
    Integer tipo;

	
    @Column(name="idVersion")
    Long idVersion;
        
	@Column(name="nombre")
	String nombre;

    @Column(name="activo")
    Integer activo;
    
    @Column(name="versiones")
    Integer versiones;

    @Column(name="uid")
    String uid;
	
    @Column(name="tms") 
    Timestamp tms;
    
	public SDPModulo() {
		this.idModulo = Fechas.serial();
	}

	public SDPModulo(Long id) {
		this.idModulo = id;
	}
	
	public Long getIdAppl() {
		return idAppl;
	}

	public void setIdAppl(Long idAppl) {
		this.idAppl = idAppl;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long id) {
		this.idModulo = id;
	}

	public Long getIdVersion() {
        return idVersion;
    }
    public void setIdVersion(Long id) {
        this.idVersion = id;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
    
    public void setVersiones(Integer versiones) {
        this.versiones = versiones;
    }

    public Integer getVersiones() {
        return versiones;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activo == null) ? 0 : activo.hashCode());
		result = prime * result + ((idAppl == null) ? 0 : idAppl.hashCode());
		result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((versiones == null) ? 0 : versiones.hashCode());
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
		SDPModulo other = (SDPModulo) obj;
		if (activo == null) {
			if (other.activo != null)
				return false;
		} else if (!activo.equals(other.activo))
			return false;
		if (idAppl == null) {
			if (other.idAppl != null)
				return false;
		} else if (!idAppl.equals(other.idAppl))
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
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
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
		if (versiones == null) {
			if (other.versiones != null)
				return false;
		} else if (!versiones.equals(other.versiones))
			return false;
		return true;
	}
    
    
}
