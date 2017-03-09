package com.jgg.sdp.domain.summary;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SUM_MODULOS")
@NamedQueries({
     @NamedQuery(name="SUMModulo.findByVersion",
                query="SELECT s FROM SUMModulo s WHERE s.idVersion = ?1")
    ,@NamedQuery(name="SUMModulo.delete",
            query="DELETE FROM SUMModulo s WHERE s.idVersion = ?1 ")
}) 

public class SUMModulo implements Serializable {

   private static final long serialVersionUID = -4909664469995359689L;

   @Id
   @Column(name="idVersion")
   private Long idVersion;
   
   @Column(name="idModulo")
   private Long idModulo;
   
   @Column(name="nombre")
   private String nombre;

   @Column(name="veces")
   private Long veces;

   @Column(name="avgElapsed")
   private Long avgElapsed;

   @Column(name="avgCpu")
   private Long avgCpu;

   @Column(name="avgSuspend")
   private Long avgSuspend;

   @Column(name="minElapsed")
   private Long minElapsed;

   @Column(name="minCpu")
   private Long minCpu;

   @Column(name="minSuspend")
   private Long minSuspend;

   @Column(name="maxElapsed")
   private Long maxElapsed;

   @Column(name="maxCpu")
   private Long maxCpu;

   @Column(name="maxSuspend")
   private Long maxSuspend;

   @Column(name="avgLeido")
   private Long avgLeido;

   @Column(name="avgEscrito")
   private Long avgEscrito;

   @Column(name="minLeido")
   private Long minLeido;

   @Column(name="minEscrito")
   private Long minEscrito;

   @Column(name="maxLeido")
   private Long maxLeido;

   @Column(name="maxEscrito")
   private Long maxEscrito;

public Long getIdModulo() {
    return idModulo;
}

public void setIdModulo(Long idModulo) {
    this.idModulo = idModulo;
}

public Long getIdVersion() {
    return idVersion;
}

public void setIdVersion(Long idVersion) {
    this.idVersion = idVersion;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public Long getVeces() {
    return veces;
}

public void setVeces(Long veces) {
    this.veces = veces;
}

public Long getAvgElapsed() {
    return avgElapsed;
}

public void setAvgElapsed(Long avgElapsed) {
    this.avgElapsed = avgElapsed;
}

public Long getAvgCpu() {
    return avgCpu;
}

public void setAvgCpu(Long avgCpu) {
    this.avgCpu = avgCpu;
}

public Long getAvgSuspend() {
    return avgSuspend;
}

public void setAvgSuspend(Long avgSuspend) {
    this.avgSuspend = avgSuspend;
}

public Long getMinElapsed() {
    return minElapsed;
}

public void setMinElapsed(Long minElapsed) {
    this.minElapsed = minElapsed;
}

public Long getMinCpu() {
    return minCpu;
}

public void setMinCpu(Long minCpu) {
    this.minCpu = minCpu;
}

public Long getMinSuspend() {
    return minSuspend;
}

public void setMinSuspend(Long minSuspend) {
    this.minSuspend = minSuspend;
}

public Long getMaxElapsed() {
    return maxElapsed;
}

public void setMaxElapsed(Long maxElapsed) {
    this.maxElapsed = maxElapsed;
}

public Long getMaxCpu() {
    return maxCpu;
}

public void setMaxCpu(Long maxCpu) {
    this.maxCpu = maxCpu;
}

public Long getMaxSuspend() {
    return maxSuspend;
}

public void setMaxSuspend(Long maxSuspend) {
    this.maxSuspend = maxSuspend;
}

public Long getAvgLeido() {
    return avgLeido;
}

public void setAvgLeido(Long avgLeido) {
    this.avgLeido = avgLeido;
}

public Long getAvgEscrito() {
    return avgEscrito;
}

public void setAvgEscrito(Long avgEscrito) {
    this.avgEscrito = avgEscrito;
}

public Long getMinLeido() {
    return minLeido;
}

public void setMinLeido(Long minLeido) {
    this.minLeido = minLeido;
}

public Long getMinEscrito() {
    return minEscrito;
}

public void setMinEscrito(Long minEscrito) {
    this.minEscrito = minEscrito;
}

public Long getMaxLeido() {
    return maxLeido;
}

public void setMaxLeido(Long maxLeido) {
    this.maxLeido = maxLeido;
}

public Long getMaxEscrito() {
    return maxEscrito;
}

public void setMaxEscrito(Long maxEscrito) {
    this.maxEscrito = maxEscrito;
}

@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((avgCpu == null) ? 0 : avgCpu.hashCode());
    result = prime * result
            + ((avgElapsed == null) ? 0 : avgElapsed.hashCode());
    result = prime * result
            + ((avgEscrito == null) ? 0 : avgEscrito.hashCode());
    result = prime * result + ((avgLeido == null) ? 0 : avgLeido.hashCode());
    result = prime * result
            + ((avgSuspend == null) ? 0 : avgSuspend.hashCode());
    result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
    result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
    result = prime * result + ((maxCpu == null) ? 0 : maxCpu.hashCode());
    result = prime * result
            + ((maxElapsed == null) ? 0 : maxElapsed.hashCode());
    result = prime * result
            + ((maxEscrito == null) ? 0 : maxEscrito.hashCode());
    result = prime * result + ((maxLeido == null) ? 0 : maxLeido.hashCode());
    result = prime * result
            + ((maxSuspend == null) ? 0 : maxSuspend.hashCode());
    result = prime * result + ((minCpu == null) ? 0 : minCpu.hashCode());
    result = prime * result
            + ((minElapsed == null) ? 0 : minElapsed.hashCode());
    result = prime * result
            + ((minEscrito == null) ? 0 : minEscrito.hashCode());
    result = prime * result + ((minLeido == null) ? 0 : minLeido.hashCode());
    result = prime * result
            + ((minSuspend == null) ? 0 : minSuspend.hashCode());
    result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
    SUMModulo other = (SUMModulo) obj;
    if (avgCpu == null) {
        if (other.avgCpu != null)
            return false;
    } else if (!avgCpu.equals(other.avgCpu))
        return false;
    if (avgElapsed == null) {
        if (other.avgElapsed != null)
            return false;
    } else if (!avgElapsed.equals(other.avgElapsed))
        return false;
    if (avgEscrito == null) {
        if (other.avgEscrito != null)
            return false;
    } else if (!avgEscrito.equals(other.avgEscrito))
        return false;
    if (avgLeido == null) {
        if (other.avgLeido != null)
            return false;
    } else if (!avgLeido.equals(other.avgLeido))
        return false;
    if (avgSuspend == null) {
        if (other.avgSuspend != null)
            return false;
    } else if (!avgSuspend.equals(other.avgSuspend))
        return false;
    if (idModulo == null) {
        if (other.idModulo != null)
            return false;
    } else if (!idModulo.equals(other.idModulo))
        return false;
    if (idVersion == null) {
        if (other.idVersion != null)
            return false;
    } else if (!idVersion.equals(other.idVersion))
        return false;
    if (maxCpu == null) {
        if (other.maxCpu != null)
            return false;
    } else if (!maxCpu.equals(other.maxCpu))
        return false;
    if (maxElapsed == null) {
        if (other.maxElapsed != null)
            return false;
    } else if (!maxElapsed.equals(other.maxElapsed))
        return false;
    if (maxEscrito == null) {
        if (other.maxEscrito != null)
            return false;
    } else if (!maxEscrito.equals(other.maxEscrito))
        return false;
    if (maxLeido == null) {
        if (other.maxLeido != null)
            return false;
    } else if (!maxLeido.equals(other.maxLeido))
        return false;
    if (maxSuspend == null) {
        if (other.maxSuspend != null)
            return false;
    } else if (!maxSuspend.equals(other.maxSuspend))
        return false;
    if (minCpu == null) {
        if (other.minCpu != null)
            return false;
    } else if (!minCpu.equals(other.minCpu))
        return false;
    if (minElapsed == null) {
        if (other.minElapsed != null)
            return false;
    } else if (!minElapsed.equals(other.minElapsed))
        return false;
    if (minEscrito == null) {
        if (other.minEscrito != null)
            return false;
    } else if (!minEscrito.equals(other.minEscrito))
        return false;
    if (minLeido == null) {
        if (other.minLeido != null)
            return false;
    } else if (!minLeido.equals(other.minLeido))
        return false;
    if (minSuspend == null) {
        if (other.minSuspend != null)
            return false;
    } else if (!minSuspend.equals(other.minSuspend))
        return false;
    if (nombre == null) {
        if (other.nombre != null)
            return false;
    } else if (!nombre.equals(other.nombre))
        return false;
    if (veces == null) {
        if (other.veces != null)
            return false;
    } else if (!veces.equals(other.veces))
        return false;
    return true;
}
   
   
   
}
