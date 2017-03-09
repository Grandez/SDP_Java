package com.jgg.sdp.domain.module;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="MOD_VERSIONES")
@NamedQueries({
	@NamedQuery( name="MODVersion.findByFirma" 
                ,query="Select v FROM MODVersion v " +
                                " WHERE v.firma = ?1 ")

   ,@NamedQuery( name="MODVersion.findByVersion" 
                ,query="Select v FROM MODVersion v " +
                       "         WHERE v.idVersion = ?1 ")

   ,@NamedQuery( name="MODVersion.VersionesPorModulo" 
                ,query="Select v FROM MODVersion v " +
                               " WHERE v.idModulo = ?1 " +
         		               " ORDER BY idVersion DESC")
})
public class MODVersion  implements Serializable {

	private static final long serialVersionUID = 1249741742430270940L;

	public final static String findByFirma = 
            "SELECT v FROM MODVersion v WHERE v.firma = ?1";


	@Column(name="idModulo")
	Long idModulo;
	
	@Id
	@Column(name="idVersion")
	Long idVersion;
		
	@Column(name="firma")
	String firma;

	@Column(name="nombre")
	String nombre;

	@Column(name="tipo")
	Integer tipo;
	
	@Column(name="fichero")
	Integer fichero;
	
	@Column(name="descripcion")
	String desc;
	
	@Column(name="uid")
	String uid;
	
	@Column(name="tms")
	Timestamp tms;

	public MODVersion() {
		this.idModulo = Fechas.serial();
		this.idVersion = Fechas.serial();
		tms = new Timestamp(idVersion);
	}
	
	public MODVersion(Long idModulo) {
		this.idModulo = idModulo;
		this.idVersion = Fechas.serial();
		tms = new Timestamp(idVersion);
	}

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

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getFichero() {
		return fichero;
	}

	public void setFichero(Integer fichero) {
		this.fichero = fichero;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
	
    public void setTms() {
    	this.tms = new Timestamp(System.currentTimeMillis());
    }
}
