package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_CALLS")
public class MODCall implements Serializable {

	public final static String listCall = 
	             "SELECT d FROM MODCall d WHERE d.idVersion = ?1 ";
	public final static String listCalling = 
            "SELECT d FROM MODCall d WHERE d.modulo = ?1 ";
	public final static String find = 
            "SELECT d FROM MODCall d WHERE d.idVersion = ?1 AND d.modulo = ?2 ";

	private static final long serialVersionUID = 2427864515290422779L;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Id
	@Column(name="modulo")
	String modulo;

	@Id
	@Column(name="metodo")
	Integer metodo;

    @Column(name="modo")
    Integer modo = 0;

    @Column(name="refs")
    Integer refs;
    
	@Column(name="estado")
	Integer estado;

	@Column(name="ejecutado")
	Integer ejecutado;

	public MODCall() {
	}
	
	public MODCall(MODCall from, String called) {
	    this.idVersion = from.getIdVersion();
	    this.modulo = called;
	    this.metodo = from.getMetodo();
	    this.modo = from.getModo();
	    this.refs = from.getRefs();
	    this.estado = from.getEstado();
	    this.ejecutado = from.getEjecutado();
	}

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

	public Integer getMetodo() {
		return metodo;
	}

	public void setMetodo(Integer metodo) {
		this.metodo = metodo;
	}

    public Integer getModo() {
        return modo;
    }

    public void setModo(Integer modo) {
        this.modo = modo;
    }

    public Integer getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Integer ejecutado) {
        this.ejecutado = ejecutado;
    }

    public Integer getRefs() {
        return refs;
    }

    public void setRefs(Integer refs) {
        this.refs = refs;
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
		result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
		result = prime * result + ((modo == null) ? 0 : modo.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((refs == null) ? 0 : refs.hashCode());
		result = prime * result + ((metodo == null) ? 0 : metodo.hashCode());
		result = prime * result + ((ejecutado == null) ? 0 : ejecutado.hashCode());		
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
		MODCall other = (MODCall) obj;
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
		if (modo == null) {
			if (other.modo != null)
				return false;
		} else if (!modo.equals(other.modo))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (refs == null) {
			if (other.refs != null)
				return false;
		} else if (!refs.equals(other.refs))
			return false;
		if (ejecutado == null) {
			if (other.ejecutado != null)
				return false;
		} else if (!ejecutado.equals(other.ejecutado))
			return false;
		if (metodo == null) {
			if (other.metodo != null)
				return false;
		} else if (!metodo.equals(other.metodo))
			return false;
		return true;
	}

}
