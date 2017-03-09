package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_GRAFO")

@NamedQueries({
     @NamedQuery(name="MODGrafo.getGrafo",
                 query="SELECT g FROM MODGrafo g WHERE g.idVersion = ?1 " +
                       "                         AND   g.idGrafo = ?2   " + 
                       "                         ORDER BY g.orden ASC")
})
public class MODGrafo implements Serializable {

    private static final long serialVersionUID = -3574798070702785374L;

    @Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="idGrafo")
    Long idGrafo;

    @Id
    @Column(name="orden")
    Integer orden;

    @Column(name="tipo")
    Integer tipo;

    @Column(name="nombre")
    String nombre;

    @Column(name="peso")
    Integer peso;

    public Long getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    public Long getIdGrafo() {
        return idGrafo;
    }

    public void setIdGrafo(Long idGrafo) {
        this.idGrafo = idGrafo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idGrafo == null) ? 0 : idGrafo.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((orden == null) ? 0 : orden.hashCode());
        result = prime * result + ((peso == null) ? 0 : peso.hashCode());
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
        MODGrafo other = (MODGrafo) obj;
        if (idGrafo == null) {
            if (other.idGrafo != null)
                return false;
        } else if (!idGrafo.equals(other.idGrafo))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (orden == null) {
            if (other.orden != null)
                return false;
        } else if (!orden.equals(other.orden))
            return false;
        if (peso == null) {
            if (other.peso != null)
                return false;
        } else if (!peso.equals(other.peso))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }

}
