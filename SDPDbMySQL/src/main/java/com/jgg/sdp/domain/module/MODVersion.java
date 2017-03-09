package com.jgg.sdp.domain.module;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.jgg.sdp.core.tools.Fechas;

@Entity
@Table(name="MOD_VERSIONES")
@NamedQueries({
    @NamedQuery( name="MODVersion.findByVersion" 
                ,query="Select v FROM MODVersion v " +
                       "         WHERE v.idVersion = ?1 ")

   ,@NamedQuery( name="MODVersion.VersionesPorModulo" 
                ,query="Select v FROM MODVersion v " +
                               " WHERE v.idModulo = ?1 " +
         		               " ORDER BY idVersion DESC")
})
public class MODVersion  implements Serializable {

	private static final long serialVersionUID = -79589454234901239L;

	public final static String findByFirma = 
            "SELECT v FROM MODVersion v WHERE v.firma = ?1";

	public final static String cuentaDeVersiones = 
            "SELECT v.idModulo, COUNT(v.idModulo) FROM MODVersion v GROUP BY v.idModulo";

	public final static String versionesByTimestamp = 
            "SELECT v.idModulo, v.tms FROM MODVersion v WHERE v.idModulo = ?1 ORDER BY v.tms DESC";

	public final static String deleteByTimestamp = 
            "DELETE FROM MODVersion v WHERE v.tms < ?1";
	
	@Id	
	@Column(name="idModulo")
	Long idModulo;
	
	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Column(name="idFile")
	Long idFile;
	
	@Column(name="linea")
	Integer linea;

	@Column(name="nombre")
	String nombre;

	@Column(name="tipo")
	Integer tipo;

	@Column(name="estado")
	Integer estado;

	@Column(name="missing")
	Integer missing;
	
	@Column(name="arbol")
	Integer arbol;
	
	@Column(name="descripcion")
	String desc;
	
	@Column(name="uid")
	String uid;
	
	@Column(name="tms")
	Timestamp tms;

	public MODVersion() {
		this.idModulo = Fechas.serial();
		this.idVersion = Fechas.serial();
		this.tms = new Timestamp(idVersion);
		this.linea = 0;
	}
	
	public MODVersion(Long idModulo) {
		this.idModulo = idModulo;
		this.idVersion = Fechas.serial();
		this.tms = new Timestamp(idVersion);
		this.linea = 0;
	}

	public MODVersion(Long idModulo, long idVersion) {
		this.idModulo = idModulo;
		this.idVersion = idVersion;
		this.tms = new Timestamp(idVersion);
		this.linea = 0;
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

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
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

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getMissing() {
		return missing;
	}

	public void setMissing(Integer missing) {
		this.missing = missing;
	}

	public Integer getArbol() {
		return arbol;
	}

	public void setArbol(Integer arbol) {
		this.arbol = arbol;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arbol == null) ? 0 : arbol.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((idFile == null) ? 0 : idFile.hashCode());
		result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
		result = prime * result + ((missing == null) ? 0 : missing.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		MODVersion other = (MODVersion) obj;
		if (arbol == null) {
			if (other.arbol != null)
				return false;
		} else if (!arbol.equals(other.arbol))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (idFile == null) {
			if (other.idFile != null)
				return false;
		} else if (!idFile.equals(other.idFile))
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
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		if (missing == null) {
			if (other.missing != null)
				return false;
		} else if (!missing.equals(other.missing))
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
		return true;
	}
	
	


}
