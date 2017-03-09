package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_DEPENDENCIAS")

@NamedQueries ({
	@NamedQuery( name="MODDependencia.list"
	            ,query="SELECT d FROM MODDependencia d WHERE d.idVersion = ?1 " + 
         	           "         ORDER BY d.tipo ASC")
   ,@NamedQuery( name="MODDependencia.listByType"
                ,query="SELECT d FROM MODDependencia d WHERE d.idVersion = ?1 AND d.tipo = ?2" + 
                       "         ORDER BY d.subTipo ASC, d.modulo ASC")
   ,@NamedQuery( name="MODDependencia.find"
                ,query="SELECT d FROM MODDependencia d WHERE d.idVersion = ?1 AND d.modulo = ?2")
})
public class MODDependencia implements Serializable {

    private static final long serialVersionUID = 3957451961034106014L;

    @Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="modulo")
	String modulo;

	@Column(name="tipo")
	Integer tipo;

    @Column(name="subTipo")
    Integer subTipo = 0;
	
	@Column(name="estado")
	Integer estado;

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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

    public Integer getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(Integer subTipo) {
        this.subTipo = subTipo;
    }
	
	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
        result = prime * result + ((subTipo == null) ? 0 : subTipo.hashCode());
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
        MODDependencia other = (MODDependencia) obj;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
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
        if (subTipo == null) {
            if (other.subTipo != null)
                return false;
        } else if (!subTipo.equals(other.subTipo))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }
	
}
