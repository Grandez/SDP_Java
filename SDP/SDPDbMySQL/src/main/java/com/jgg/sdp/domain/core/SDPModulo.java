package com.jgg.sdp.domain.core;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SDP_MODULOS")
@NamedQueries({
	@NamedQuery( name="SDPModulo.ModulosPorAplicacion" 
                ,query="Select m FROM SDPModulo m WHERE m.idAppl = ?1 AND m.estado = 1 ORDER BY m.nombre")
   ,@NamedQuery( name="SDPModulo.CuentaPorAplicacion" 
                ,query="Select COUNT(m.idAppl) FROM SDPModulo m WHERE m.idAppl = ?1 AND m.estado = 1")
   ,@NamedQuery( name="SDPModulo.ModulosModificados" 
                ,query="Select m FROM SDPModulo m WHERE m.tms > ?1")
   ,@NamedQuery( name="SDPModulo.ModulosActivos" 
                ,query="Select m FROM SDPModulo m WHERE m.estado = 1")
   ,@NamedQuery( name="SDPModulo.find"
                ,query="SELECT m FROM SDPModulo m WHERE m.idModulo = ?1")   
   ,@NamedQuery( name="SDPModulo.findByName"
                ,query="SELECT m FROM SDPModulo m WHERE m.nombre = ?1")   
   ,@NamedQuery( name="SDPModulo.updateVersion"
                ,query="UPDATE SDPModulo m SET m.idVersion = ?2 WHERE idModulo = ?1")   
	
})
public class SDPModulo implements Serializable{

    private static final long serialVersionUID = 203163672284348791L;

	@Column(name="idAppl")
	Long idAppl;
	
	@Id
	@Column(name="idModulo")
	Long idModulo;

    @Column(name="idVersion")
    Long idVersion;
	
	@Column(name="nombre")
	String nombre;

    @Column(name="uid")
    String uid;
	
    @Column(name="tms") 
    Timestamp tms;
    
    @Column(name="tipo")
    Integer tipo;

    @Column(name="estado")
    Integer estado;
    
	public SDPModulo() {
		this.idModulo = Fechas.serial();
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    	
}
