package com.jgg.sdp.domain.traps;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="TRP_CALL")
@NamedQueries({
    @NamedQuery(name="TRPCall.listCalls",
                query="SELECT c FROM TRPCall c WHERE c.idSesion = ?1 ORDER BY c.idCalling, c.idCalled"
               )
})
public class TRPCall implements Serializable {

   private static final long serialVersionUID = -1181083869182028989L;

   public static final String sumCalls = 
           "SELECT idCalling, idCalled, modulo, MIN(orden), COUNT(*) " +
                   ",SUM(totElapsed), SUM(totCpu), SUM(totSuspend) " +
                   ",SUM(intElapsed), SUM(intCpu), SUM(intSuspend) " +
                   ",MIN(totElapsed), MIN(totCpu), MIN(totSuspend) " +
                   ",MIN(intElapsed), MIN(intCpu), MIN(intSuspend) " +                 
                   ",MAX(totElapsed), MAX(totCpu), MAX(totSuspend) " +
                   ",MAX(intElapsed), MAX(intCpu), MAX(intSuspend) " +                 
                   ",AVG(totElapsed), AVG(totCpu), AVG(totSuspend) " +
                   ",AVG(intElapsed), AVG(intCpu), AVG(intSuspend) " +                 
           "FROM TRP_CALL " +        
           "WHERE idSesion = ?1 " + 
           "GROUP BY idCalling, idCalled, modulo ";

   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Id
   @Column(name="idCalling")
   private String idCalling;

   @Id
   @Column(name="idCalled")
   private String idCalled;

   @Id
   @Column(name="orden")
   private Long orden;
   
   @Column(name="modulo")
   private String modulo;

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

public void setOrden(Long orden) {
	   this.orden = orden;
}

public Long getOrden() {
	   return orden;
}

public String getIdCalling() {
	return idCalling;
}

public void setIdCalling(String idCalling) {
	this.idCalling = idCalling;
}

public String getIdCalled() {
	return idCalled;
}

public void setIdCalled(String idCalled) {
	this.idCalled = idCalled;
}

public String getModulo() {
	return modulo;
}

public void setModulo(String modulo) {
	this.modulo = modulo;
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
    result = prime * result + ((idCalled == null) ? 0 : idCalled.hashCode());
    result = prime * result + ((idCalling == null) ? 0 : idCalling.hashCode());
    result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
    result = prime * result + ((intCpu == null) ? 0 : intCpu.hashCode());
    result = prime * result
            + ((intElapsed == null) ? 0 : intElapsed.hashCode());
    result = prime * result
            + ((intSuspend == null) ? 0 : intSuspend.hashCode());
    result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
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
    TRPCall other = (TRPCall) obj;
    if (idCalled == null) {
        if (other.idCalled != null)
            return false;
    } else if (!idCalled.equals(other.idCalled))
        return false;
    if (idCalling == null) {
        if (other.idCalling != null)
            return false;
    } else if (!idCalling.equals(other.idCalling))
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
    if (modulo == null) {
        if (other.modulo != null)
            return false;
    } else if (!modulo.equals(other.modulo))
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
