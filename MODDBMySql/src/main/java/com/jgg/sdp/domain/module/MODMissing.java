package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_MISSING")
@NamedQueries ({
	@NamedQuery( name="MODMissing.delete"
	            ,query="DELETE FROM MODMissing m " +
	            	   "      WHERE m.idVersion = ?1 " + 
         	           "      AND   m.modulo = ?2")
})

public class MODMissing implements Serializable {

	private static final long serialVersionUID = 2305662019016644398L;

	@Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="modulo")
    String modulo;

    @Column(name="motivo")
    Integer motivo;
    
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Integer getMotivo() {
		return motivo;
	}

	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
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
		MODMissing other = (MODMissing) obj;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		return true;
	}
	
    
}    