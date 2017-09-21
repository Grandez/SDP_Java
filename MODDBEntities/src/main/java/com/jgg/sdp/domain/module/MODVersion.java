package com.jgg.sdp.domain.module;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.jgg.sdp.tools.Fechas;

@Entity
@Table(name="MOD_VERSIONES")
public class MODVersion  implements Serializable {

	private static final long serialVersionUID = -79589454234901239L;

	public final static String findById = 
            "SELECT v FROM MODVersion v WHERE v.idVersion = ?1";

	public final static String cuentaDeVersiones = 
            "SELECT v.idModulo, COUNT(v.idModulo) FROM MODVersion v GROUP BY v.idModulo";

	public final static String versionesByTimestamp = 
            "SELECT v FROM MODVersion v WHERE v.idModulo = ?1 AND v.tms > ?2 ORDER BY v.tms DESC";
	
	public final static String deleteByTimestamp = 
            "DELETE FROM MODVersion v WHERE v.tms < ?1";
	
	@Id	
	@Column(name="idModulo")
	Long idModulo;
	
	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Column(name="idVersionFile")
	Long idVersionFile;
	
	@Column(name="offsetBeg")
	Integer offsetBeg;

	@Column(name="offsetEnd")
	Integer offsetEnd;
	
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

	@Column(name="autor")
	String author;
	
	@Column(name="tms")
	Timestamp tms;

	public MODVersion() {
		this(Fechas.serial());
	}
	
	public MODVersion(Long idModulo) {
		this(idModulo, Fechas.serial());
	}

	public MODVersion(Long idModulo, long idVersion) {
		this.idModulo = idModulo;
		this.idVersion = idVersion;
		this.tms = new Timestamp(idVersion);
		this.offsetBeg = 0;
		this.offsetEnd = -1;
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

	public Long getIdVersionFile() {
		return idVersionFile;
	}

	public void setIdVersionFile(Long idVersionFile) {
		this.idVersionFile = idVersionFile;
	}
	
	public Integer getOffsetBeg() {
		return offsetBeg;
	}

	public void setOffsetBeg(Integer offsetBeg) {
		this.offsetBeg = offsetBeg;
	}

	public Integer getOffsetEnd() {
		return offsetEnd;
	}

	public void setOffsetEnd(Integer offsetEnd) {
		this.offsetEnd = offsetEnd;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
		result = prime * result + ((idVersionFile == null) ? 0 : idVersionFile.hashCode());
		result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((offsetBeg == null) ? 0 : offsetBeg.hashCode());
		result = prime * result + ((offsetEnd == null) ? 0 : offsetEnd.hashCode());		
		result = prime * result + ((missing == null) ? 0 : missing.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tms == null) ? 0 : tms.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
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
		if (idVersionFile == null) {
			if (other.idVersionFile != null)
				return false;
		} else if (!idVersionFile.equals(other.idVersionFile))
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
		if (offsetBeg == null) {
			if (other.offsetBeg != null)
				return false;
		} else if (!offsetBeg.equals(other.offsetBeg))
			return false;
		if (offsetEnd == null) {
			if (other.offsetEnd != null)
				return false;
		} else if (!offsetEnd.equals(other.offsetEnd))
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
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}
	
	


}
