package com.jgg.sdp.domain.cfg;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CFG_CONFIGURATION")
public class CFGConfig implements Serializable {

	private static final long serialVersionUID = -8263370501168895268L;

	public static final String listAll = "SELECT c FROM CFGConfig c";
	public static final String delAll  = "DELETE   FROM CFGConfig c";	
	public static final String getItem = "SELECT c FROM CFGConfig c WHERE c.clave = ?1";
	
	@Id
	@Column(name="clave")
	private String clave;

	@Column(name="valor")
	private String valor;

	@Column(name="tipo")
	private Integer tipo;

	@Column(name="mask")
	private String mask;

	@Column(name="minimo")
	private String minimo;

	@Column(name="maximo")
	private String maximo;

	@Column(name="grupo")
	private Integer grupo;
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		result = prime * result + ((maximo == null) ? 0 : maximo.hashCode());
		result = prime * result + ((minimo == null) ? 0 : minimo.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		CFGConfig other = (CFGConfig) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (mask == null) {
			if (other.mask != null)
				return false;
		} else if (!mask.equals(other.mask))
			return false;
		if (maximo == null) {
			if (other.maximo != null)
				return false;
		} else if (!maximo.equals(other.maximo))
			return false;
		if (minimo == null) {
			if (other.minimo != null)
				return false;
		} else if (!minimo.equals(other.minimo))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
