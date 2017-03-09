package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_CICS")
@NamedQueries ({
	@NamedQuery( name="MODCics.delete"
	            ,query="DELETE FROM MODCics c WHERE c.idVersion = ?1 ")
   ,@NamedQuery( name="MODCics.list"
	            ,query="SELECT c FROM MODCics c WHERE c.idVersion = ?1 ")

})
public class MODCics implements Serializable {

	private static final long serialVersionUID = -1005481942475720073L;

	@Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="verbo")
    String verbo;

    @Id
    @Column(name="tipo")
    Integer tipo;
    
    @Column(name="veces")
    Integer veces;
    
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public String getVerbo() {
		return verbo;
	}

	public void setVerbo(String verbo) {
		this.verbo = verbo;
	}

	public Integer getVeces() {
		return veces;
	}

	public void setVeces(Integer veces) {
		this.veces = veces;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((veces == null) ? 0 : veces.hashCode());
		result = prime * result + ((verbo == null) ? 0 : verbo.hashCode());
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
		MODCics other = (MODCics) obj;
		if (idVersion == null) {
			if (other.idVersion != null)
				return false;
		} else if (!idVersion.equals(other.idVersion))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (veces == null) {
			if (other.veces != null)
				return false;
		} else if (!veces.equals(other.veces))
			return false;
		if (verbo == null) {
			if (other.verbo != null)
				return false;
		} else if (!verbo.equals(other.verbo))
			return false;
		return true;
	}
    
}    