package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_FICHEROS")
@NamedQueries({
     @NamedQuery(name="MODFichero.find",
                 query="SELECT f FROM MODFichero f WHERE f.idVersion = ?1 AND f.idFile = ?2")
    ,@NamedQuery(name="MODFichero.list",
                 query="SELECT f FROM MODFichero f WHERE f.idVersion = ?1 ORDER BY f.idFile")

}) 


public class MODFichero implements Serializable {

	private static final long serialVersionUID = 4729732646898697174L;

    public static final String getMaestros = 
                        "SELECT f FROM MODFichero f " +
                        "         WHERE f.idVersion = ?1 AND f.maestro > 0";
    
    public static final String getByIndex    = 
                              "SELECT f FROM MODFichero f " +
                              "         WHERE f.idVersion = ?1 AND f.idFile = ?2";

	
	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="idFile")
	Integer idFile;
	
	@Column(name="logico")
	String nombreLogico;
	
	@Column(name="fisico")
	String nombreFisico;
	
	@Column(name="tipo")
	Integer tipo;

	@Column(name="acceso")
	Integer acceso;

	@Column(name="maestro")
	Integer maestro;

	@Column(name="leido")
	Long leido;
	
	public MODFichero() {
		
	}
	public MODFichero(Long idModule) {
		this.idVersion = idModule;
	}
	
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getIdFile() {
		return idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public String getNombreLogico() {
		return nombreLogico;
	}

	public void setNombreLogico(String nombreLogico) {
		this.nombreLogico = nombreLogico;
	}

	public String getNombreFisico() {
		return nombreFisico;
	}

	public void setNombreFisico(String nombreFisico) {
		this.nombreFisico = nombreFisico;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getAcceso() {
		return acceso;
	}

	public void setAcceso(Integer acceso) {
		this.acceso = acceso;
	}

	public void setMaestro(Integer maestro) {
		this.maestro = maestro;
	}
	
	public Integer getMaestro() {
		return maestro;
	}

	public void setLeido(Long leido) {
		this.leido = leido;
	}
	
	public Long getLeido() {
		return leido;
	}
	
	public boolean isMaestro() {
		return (maestro != 0);
	}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((acceso == null) ? 0 : acceso.hashCode());
        result = prime * result + ((idFile == null) ? 0 : idFile.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((leido == null) ? 0 : leido.hashCode());
        result = prime * result + ((maestro == null) ? 0 : maestro.hashCode());
        result = prime * result
                + ((nombreFisico == null) ? 0 : nombreFisico.hashCode());
        result = prime * result
                + ((nombreLogico == null) ? 0 : nombreLogico.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
        MODFichero other = (MODFichero) obj;
        if (acceso == null) {
            if (other.acceso != null)
                return false;
        } else if (!acceso.equals(other.acceso))
            return false;
        if (idFile == null) {
            if (other.idFile != null)
                return false;
        } else if (!idFile.equals(other.idFile))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
            return false;
        if (leido == null) {
            if (other.leido != null)
                return false;
        } else if (!leido.equals(other.leido))
            return false;
        if (maestro == null) {
            if (other.maestro != null)
                return false;
        } else if (!maestro.equals(other.maestro))
            return false;
        if (nombreFisico == null) {
            if (other.nombreFisico != null)
                return false;
        } else if (!nombreFisico.equals(other.nombreFisico))
            return false;
        if (nombreLogico == null) {
            if (other.nombreLogico != null)
                return false;
        } else if (!nombreLogico.equals(other.nombreLogico))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }
	
}
