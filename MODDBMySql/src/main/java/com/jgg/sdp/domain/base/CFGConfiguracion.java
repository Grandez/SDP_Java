package com.jgg.sdp.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CFG_CONFIGURACION")
@NamedQueries({
    @NamedQuery(name="CFGConfiguracion.listAll",
                query="SELECT c FROM CFGConfiguracion c")
   ,@NamedQuery(name="CFGConfiguracion.get",
                query="SELECT c FROM CFGConfiguracion c WHERE c.clave = ?1")

}) 
public class CFGConfiguracion {
    
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
