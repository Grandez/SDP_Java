package com.jgg.sdp.domain.traps;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="TRP_PARRAFOS")
@NamedQueries ({
    @NamedQuery( name="TRPParrafo.list"
                ,query="SELECT m FROM TRPParrafo m      " +
                       "         WHERE m.idSesion = ?1 " + 
                       "         ORDER BY m.orden ASC")
})

public class TRPParrafo implements Serializable {

    private static final long serialVersionUID = 2590965202745203074L;

    public static final String SumParrafos = 
            "SELECT etiqueta, COUNT(*), MAX(orden) " +
                    ",SUM(totElapsed), SUM(totCpu), SUM(totSuspend) " +
                    ",SUM(intElapsed), SUM(intCpu), SUM(intSuspend) " +
                    ",MIN(totElapsed), MIN(totCpu), MIN(totSuspend) " +
                    ",MIN(intElapsed), MIN(intCpu), MIN(intSuspend) " +                 
                    ",MAX(totElapsed), MAX(totCpu), MAX(totSuspend) " +
                    ",MAX(intElapsed), MAX(intCpu), MAX(intSuspend) " +                 
            "FROM  TRP_PARRAFOS "            +        
            "WHERE idSesion = ?1 "           +
            "AND   idModulo = ?2 "           +
            "GROUP BY etiqueta ";
       
   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Id  
   @Column(name="idModulo")
   private String idModulo;

   @Id
   @Column(name="orden")
   private Long orden;
   
   @Column(name="etiqueta")
   private String etiqueta;

   @Column(name="veces")
   private Long veces;
          
   @Column(name="totElapsed")
   private Long totElapsed;

   @Column(name="totCpu")
   private Long totCpu;
           
   @Column(name="totSuspend")
   private Long totSuspend;

   @Column(name="intElapsed")
   private Long intElapsed;

   @Column(name="intCpu")
   private Long intCpu;
           
   @Column(name="intSuspend")
   private Long intSuspend;

public String getIdSesion() {
    return idSesion;
}

public void setIdSesion(String idSesion) {
    this.idSesion = idSesion;
}

public String getIdModulo() {
    return idModulo;
}

public void setIdModulo(String idModulo) {
    this.idModulo = idModulo;
}

public String getEtiqueta() {
    return etiqueta;
}

public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
}

public Long getOrden() {
    return orden;
}

public void setOrden(Long orden) {
    this.orden = orden;
}

public Long getVeces() {
    return veces;
}

public void setVeces(Long veces) {
    this.veces = veces;
}

public Long getTotElapsed() {
    return totElapsed;
}

public void setTotElapsed(Long totElapsed) {
    this.totElapsed = totElapsed;
}

public Long getTotCpu() {
    return totCpu;
}

public void setTotCpu(Long totCpu) {
    this.totCpu = totCpu;
}

public Long getTotSuspend() {
    return totSuspend;
}

public void setTotSuspend(Long totSuspend) {
    this.totSuspend = totSuspend;
}

public Long getIntElapsed() {
    return intElapsed;
}

public void setIntElapsed(Long intElapsed) {
    this.intElapsed = intElapsed;
}

public Long getIntCpu() {
    return intCpu;
}

public void setIntCpu(Long intCpu) {
    this.intCpu = intCpu;
}

public Long getIntSuspend() {
    return intSuspend;
}

public void setIntSuspend(Long intSuspend) {
    this.intSuspend = intSuspend;
}

@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
    result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
    result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
    result = prime * result + ((intCpu == null) ? 0 : intCpu.hashCode());
    result = prime * result
            + ((intElapsed == null) ? 0 : intElapsed.hashCode());
    result = prime * result
            + ((intSuspend == null) ? 0 : intSuspend.hashCode());
    result = prime * result + ((orden == null) ? 0 : orden.hashCode());
    result = prime * result + ((totCpu == null) ? 0 : totCpu.hashCode());
    result = prime * result
            + ((totElapsed == null) ? 0 : totElapsed.hashCode());
    result = prime * result
            + ((totSuspend == null) ? 0 : totSuspend.hashCode());
    result = prime * result + ((veces == null) ? 0 : veces.hashCode());
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
    TRPParrafo other = (TRPParrafo) obj;
    if (etiqueta == null) {
        if (other.etiqueta != null)
            return false;
    } else if (!etiqueta.equals(other.etiqueta))
        return false;
    if (idModulo == null) {
        if (other.idModulo != null)
            return false;
    } else if (!idModulo.equals(other.idModulo))
        return false;
    if (idSesion == null) {
        if (other.idSesion != null)
            return false;
    } else if (!idSesion.equals(other.idSesion))
        return false;
    if (intCpu == null) {
        if (other.intCpu != null)
            return false;
    } else if (!intCpu.equals(other.intCpu))
        return false;
    if (intElapsed == null) {
        if (other.intElapsed != null)
            return false;
    } else if (!intElapsed.equals(other.intElapsed))
        return false;
    if (intSuspend == null) {
        if (other.intSuspend != null)
            return false;
    } else if (!intSuspend.equals(other.intSuspend))
        return false;
    if (orden == null) {
        if (other.orden != null)
            return false;
    } else if (!orden.equals(other.orden))
        return false;
    if (totCpu == null) {
        if (other.totCpu != null)
            return false;
    } else if (!totCpu.equals(other.totCpu))
        return false;
    if (totElapsed == null) {
        if (other.totElapsed != null)
            return false;
    } else if (!totElapsed.equals(other.totElapsed))
        return false;
    if (totSuspend == null) {
        if (other.totSuspend != null)
            return false;
    } else if (!totSuspend.equals(other.totSuspend))
        return false;
    if (veces == null) {
        if (other.veces != null)
            return false;
    } else if (!veces.equals(other.veces))
        return false;
    return true;
}


}
