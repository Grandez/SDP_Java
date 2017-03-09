package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="MOD_BADSTMTS")
@NamedQueries ({
    @NamedQuery( name="MODBadStmt.list"
                ,query="SELECT b FROM MODBadStmt b WHERE b.idVersion = ?1 " + 
                       "         ORDER BY b.orden ASC")
})
public class MODBadStmt implements Serializable {

    private static final long serialVersionUID = 6706831540769170997L;

    @Id
    @Column(name="idVersion")
    Long idVersion;
    
    @Id
    @Column(name="orden")
    Integer orden;

    @Column(name="sentencia")
    String sentencia;

    @Column(name="begLine")
    Integer begLine;

    @Column(name="endLine")
    Integer endLine;
    
    @Column(name="columna")
    Integer columna;

    public Long getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getSentencia() {
        return sentencia;
    }

    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    public Integer getBegLine() {
        return begLine;
    }

    public void setBegLine(Integer begLine) {
        this.begLine = begLine;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((begLine == null) ? 0 : begLine.hashCode());
        result = prime * result + ((columna == null) ? 0 : columna.hashCode());
        result = prime * result + ((endLine == null) ? 0 : endLine.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((orden == null) ? 0 : orden.hashCode());
        result = prime * result
                + ((sentencia == null) ? 0 : sentencia.hashCode());
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
        MODBadStmt other = (MODBadStmt) obj;
        if (begLine == null) {
            if (other.begLine != null)
                return false;
        } else if (!begLine.equals(other.begLine))
            return false;
        if (columna == null) {
            if (other.columna != null)
                return false;
        } else if (!columna.equals(other.columna))
            return false;
        if (endLine == null) {
            if (other.endLine != null)
                return false;
        } else if (!endLine.equals(other.endLine))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
            return false;
        if (orden == null) {
            if (other.orden != null)
                return false;
        } else if (!orden.equals(other.orden))
            return false;
        if (sentencia == null) {
            if (other.sentencia != null)
                return false;
        } else if (!sentencia.equals(other.sentencia))
            return false;
        return true;
    }
    
    
}    