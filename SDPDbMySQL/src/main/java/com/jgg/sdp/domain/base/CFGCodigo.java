package com.jgg.sdp.domain.base;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="CFG_CODIGOS")
@NamedQueries({
    @NamedQuery( name="CFGCodigo.getGrupo"
                ,query="SELECT c FROM CFGCodigo c WHERE c.grupo = ?1 AND c.lang = ?2 ORDER BY c.codigo")
   ,@NamedQuery( name="CFGCodigo.getMessage"
               ,query="SELECT c FROM CFGCodigo c WHERE c.grupo = ?1 AND c.codigo = ?2 AND c.lang = ?3")

})
public class CFGCodigo implements Serializable {

    private static final long serialVersionUID = -8228709965721391672L;

    @Id
	@Column(name="grupo")
	Integer grupo;

    @Id
	@Column(name="codigo")
	Integer codigo;

    @Id
	@Column(name="lang")
	String lang;

	@Column(name="valor")
	String valor;

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
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
        CFGCodigo other = (CFGCodigo) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (grupo == null) {
            if (other.grupo != null)
                return false;
        } else if (!grupo.equals(other.grupo))
            return false;
        if (lang == null) {
            if (other.lang != null)
                return false;
        } else if (!lang.equals(other.lang))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        return true;
    }
	
}
