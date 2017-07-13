package com.jgg.sdp.domain.cfg;

import javax.persistence.*;

@Entity
@Table(name="CFG_CONFIGURACION")
public class CFGConfiguracion {

	public static final String listAll = "SELECT c FROM CFGConfiguracion c";
	
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
	
	@Column(name="tooltip")
	private Integer tooltip;

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

    public Integer getTooltip() {
        return tooltip;
    }

    public void setTooltip(Integer tooltip) {
        this.tooltip = tooltip;
    }

}
