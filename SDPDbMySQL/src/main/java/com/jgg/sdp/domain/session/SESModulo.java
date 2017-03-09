package com.jgg.sdp.domain.session;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SES_MODULOS")
@NamedQueries({
	@NamedQuery(name="SESModulo.findInSesion"
			   ,query="SELECT m FROM SESModulo m WHERE m.idSesion = ?1 AND m.idVersion = ?2"
			   )
   ,@NamedQuery(name="SESModulo.resEjecuciones"
	               ,query="SELECT s.nombre, COUNT(s.veces), SUM(s.totElapsed) " +
                          "FROM SESModulo s "     +
                          "WHERE s.nombre IN ?1 " +
                          "AND   s.tms > ?2 "     +
                          "GROUP BY s.nombre"
    )
   ,@NamedQuery( name="SESModulo.resEjecucionesModulo"
                ,query="SELECT COUNT(s.veces), SUM(s.totElapsed), SUM(s.totCpu), "  + 
                       "       MIN(s.minTotElapsed), MIN(s.minTotCpu)," + 
                        "      MAX(s.maxTotElapsed), MIN(s.maxTotCpu) " +
                       "       FROM SESModulo s "                       +
                       "       WHERE s.idVersion = ?1 "                 +
                       "       AND   s.tms > ?2 "  
    )
	,@NamedQuery( name="SESModulo.todos"
                ,query="SELECT m FROM SESModulo m"
    )

})
public class SESModulo implements Serializable {

    private static final long serialVersionUID = -7025466189632836844L;

    @Id
	@Column(name="idSesion")
	String idSesion;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Id
	@Column(name="orden")
	Long orden;
	
	@Id
	@Column(name="nombre")
	String nombre;
	
	@Column(name="veces")
	Long veces;
	
	@Column(name="totElapsed")
	Long totElapsed;

	@Column(name="totCpu")
	Long totCpu;

	@Column(name="totSuspend")
	Long totSuspend;

	@Column(name="intElapsed")
	Long intElapsed;

	@Column(name="intCpu")
	Long intCpu;

	@Column(name="intSuspend")
	Long intSuspend;

	@Column(name="minTotElapsed")
	Long minTotElapsed;

	@Column(name="minTotCpu")
	Long minTotCpu;

	@Column(name="minTotSuspend")
	Long minTotSuspend;

	@Column(name="minIntElapsed")
	Long minIntElapsed;

	@Column(name="minIntCpu")
	Long minIntCpu;

	@Column(name="minIntSuspend")
	Long minIntSuspend;

	@Column(name="maxTotElapsed")
	Long maxTotElapsed;

	@Column(name="maxTotCpu")
	Long maxTotCpu;

	@Column(name="maxTotSuspend")
	Long maxTotSuspend;

	@Column(name="maxIntElapsed")
	Long maxIntElapsed;

	@Column(name="maxIntCpu")
	Long maxIntCpu;

	@Column(name="maxIntSuspend")
	Long maxIntSuspend;
	
	@Column(name="avgTotElapsed")
	Long avgTotElapsed;

	@Column(name="avgTotCpu")
	Long avgTotCpu;

	@Column(name="avgTotSuspend")
	Long avgTotSuspend;

	@Column(name="avgIntElapsed")
	Long avgIntElapsed;

	@Column(name="avgIntCpu")
	Long avgIntCpu;

	@Column(name="avgIntSuspend")
	Long avgIntSuspend;

	@Column(name="tms")
	Timestamp tms;
	
	public SESModulo() {
		veces = 0L;
		totElapsed = 0L;
        totCpu = 0L;
        totSuspend = 0L;
        intElapsed = 0L;
        intCpu = 0L;
        intSuspend= 0L;
        minTotElapsed = 0L;
        minTotCpu = 0L;
        minTotSuspend = 0L;
        minIntElapsed = 0L;
        minIntCpu = 0L;
        minIntSuspend  = 0L;
        maxTotElapsed = 0L;
        maxTotCpu = 0L;
        maxTotSuspend  = 0L;
        maxIntElapsed = 0L;
        maxIntCpu = 0L;
        maxIntSuspend = 0L;
        avgTotElapsed  = 0L;
        avgTotCpu = 0L;
        avgTotSuspend = 0L;
        avgIntElapsed = 0L;
        avgIntCpu = 0L;
        avgIntSuspend = 0L;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
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

	public Long getAvgTotElapsed() {
		return avgTotElapsed;
	}

	public void setAvgTotElapsed(Long avgTotElapsed) {
		this.avgTotElapsed = avgTotElapsed;
	}

	public Long getAvgTotCpu() {
		return avgTotCpu;
	}

	public void setAvgTotCpu(Long avgTotCpu) {
		this.avgTotCpu = avgTotCpu;
	}

	public Long getAvgTotSuspend() {
		return avgTotSuspend;
	}

	public void setAvgTotSuspend(Long avgTotSuspend) {
		this.avgTotSuspend = avgTotSuspend;
	}

	public Long getAvgIntElapsed() {
		return avgIntElapsed;
	}

	public void setAvgIntElapsed(Long avgIntElapsed) {
		this.avgIntElapsed = avgIntElapsed;
	}

	public Long getAvgIntCpu() {
		return avgIntCpu;
	}

	public void setAvgIntCpu(Long avgIntCpu) {
		this.avgIntCpu = avgIntCpu;
	}

	public Long getAvgIntSuspend() {
		return avgIntSuspend;
	}

	public void setAvgIntSuspend(Long avgIntSuspend) {
		this.avgIntSuspend = avgIntSuspend;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getMinTotElapsed() {
		return minTotElapsed;
	}

	public void setMinTotElapsed(Long minTotElapsed) {
		this.minTotElapsed = minTotElapsed;
	}

	public Long getMinTotCpu() {
		return minTotCpu;
	}

	public void setMinTotCpu(Long minTotCpu) {
		this.minTotCpu = minTotCpu;
	}

	public Long getMinTotSuspend() {
		return minTotSuspend;
	}

	public void setMinTotSuspend(Long minTotSuspend) {
		this.minTotSuspend = minTotSuspend;
	}

	public Long getMinIntElapsed() {
		return minIntElapsed;
	}

	public void setMinIntElapsed(Long minIntElapsed) {
		this.minIntElapsed = minIntElapsed;
	}

	public Long getMinIntCpu() {
		return minIntCpu;
	}

	public void setMinIntCpu(Long minIntCpu) {
		this.minIntCpu = minIntCpu;
	}

	public Long getMinIntSuspend() {
		return minIntSuspend;
	}

	public void setMinIntSuspend(Long minIntSuspend) {
		this.minIntSuspend = minIntSuspend;
	}

	public Long getMaxTotElapsed() {
		return maxTotElapsed;
	}

	public void setMaxTotElapsed(Long maxTotElapsed) {
		this.maxTotElapsed = maxTotElapsed;
	}

	public Long getMaxTotCpu() {
		return maxTotCpu;
	}

	public void setMaxTotCpu(Long maxTotCpu) {
		this.maxTotCpu = maxTotCpu;
	}

	public Long getMaxTotSuspend() {
		return maxTotSuspend;
	}

	public void setMaxTotSuspend(Long maxTotSuspend) {
		this.maxTotSuspend = maxTotSuspend;
	}

	public Long getMaxIntElapsed() {
		return maxIntElapsed;
	}

	public void setMaxIntElapsed(Long maxIntElapsed) {
		this.maxIntElapsed = maxIntElapsed;
	}

	public Long getMaxIntCpu() {
		return maxIntCpu;
	}

	public void setMaxIntCpu(Long maxIntCpu) {
		this.maxIntCpu = maxIntCpu;
	}

	public Long getMaxIntSuspend() {
		return maxIntSuspend;
	}

	public void setMaxIntSuspend(Long maxIntSuspend) {
		this.maxIntSuspend = maxIntSuspend;
	}
	
	public void setTms(Timestamp tms) {
	    this.tms = tms;
	}
	
	public Timestamp getTms() {
	    return tms;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((avgIntCpu == null) ? 0 : avgIntCpu.hashCode());
        result = prime * result
                + ((avgIntElapsed == null) ? 0 : avgIntElapsed.hashCode());
        result = prime * result
                + ((avgIntSuspend == null) ? 0 : avgIntSuspend.hashCode());
        result = prime * result
                + ((avgTotCpu == null) ? 0 : avgTotCpu.hashCode());
        result = prime * result
                + ((avgTotElapsed == null) ? 0 : avgTotElapsed.hashCode());
        result = prime * result
                + ((avgTotSuspend == null) ? 0 : avgTotSuspend.hashCode());
        result = prime * result
                + ((idSesion == null) ? 0 : idSesion.hashCode());
        result = prime * result
                + ((idVersion == null) ? 0 : idVersion.hashCode());
        result = prime * result + ((intCpu == null) ? 0 : intCpu.hashCode());
        result = prime * result
                + ((intElapsed == null) ? 0 : intElapsed.hashCode());
        result = prime * result
                + ((intSuspend == null) ? 0 : intSuspend.hashCode());
        result = prime * result
                + ((maxIntCpu == null) ? 0 : maxIntCpu.hashCode());
        result = prime * result
                + ((maxIntElapsed == null) ? 0 : maxIntElapsed.hashCode());
        result = prime * result
                + ((maxIntSuspend == null) ? 0 : maxIntSuspend.hashCode());
        result = prime * result
                + ((maxTotCpu == null) ? 0 : maxTotCpu.hashCode());
        result = prime * result
                + ((maxTotElapsed == null) ? 0 : maxTotElapsed.hashCode());
        result = prime * result
                + ((maxTotSuspend == null) ? 0 : maxTotSuspend.hashCode());
        result = prime * result
                + ((minIntCpu == null) ? 0 : minIntCpu.hashCode());
        result = prime * result
                + ((minIntElapsed == null) ? 0 : minIntElapsed.hashCode());
        result = prime * result
                + ((minIntSuspend == null) ? 0 : minIntSuspend.hashCode());
        result = prime * result
                + ((minTotCpu == null) ? 0 : minTotCpu.hashCode());
        result = prime * result
                + ((minTotElapsed == null) ? 0 : minTotElapsed.hashCode());
        result = prime * result
                + ((minTotSuspend == null) ? 0 : minTotSuspend.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((orden == null) ? 0 : orden.hashCode());
        result = prime * result + ((tms == null) ? 0 : tms.hashCode());
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
        SESModulo other = (SESModulo) obj;
        if (avgIntCpu == null) {
            if (other.avgIntCpu != null)
                return false;
        } else if (!avgIntCpu.equals(other.avgIntCpu))
            return false;
        if (avgIntElapsed == null) {
            if (other.avgIntElapsed != null)
                return false;
        } else if (!avgIntElapsed.equals(other.avgIntElapsed))
            return false;
        if (avgIntSuspend == null) {
            if (other.avgIntSuspend != null)
                return false;
        } else if (!avgIntSuspend.equals(other.avgIntSuspend))
            return false;
        if (avgTotCpu == null) {
            if (other.avgTotCpu != null)
                return false;
        } else if (!avgTotCpu.equals(other.avgTotCpu))
            return false;
        if (avgTotElapsed == null) {
            if (other.avgTotElapsed != null)
                return false;
        } else if (!avgTotElapsed.equals(other.avgTotElapsed))
            return false;
        if (avgTotSuspend == null) {
            if (other.avgTotSuspend != null)
                return false;
        } else if (!avgTotSuspend.equals(other.avgTotSuspend))
            return false;
        if (idSesion == null) {
            if (other.idSesion != null)
                return false;
        } else if (!idSesion.equals(other.idSesion))
            return false;
        if (idVersion == null) {
            if (other.idVersion != null)
                return false;
        } else if (!idVersion.equals(other.idVersion))
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
        if (maxIntCpu == null) {
            if (other.maxIntCpu != null)
                return false;
        } else if (!maxIntCpu.equals(other.maxIntCpu))
            return false;
        if (maxIntElapsed == null) {
            if (other.maxIntElapsed != null)
                return false;
        } else if (!maxIntElapsed.equals(other.maxIntElapsed))
            return false;
        if (maxIntSuspend == null) {
            if (other.maxIntSuspend != null)
                return false;
        } else if (!maxIntSuspend.equals(other.maxIntSuspend))
            return false;
        if (maxTotCpu == null) {
            if (other.maxTotCpu != null)
                return false;
        } else if (!maxTotCpu.equals(other.maxTotCpu))
            return false;
        if (maxTotElapsed == null) {
            if (other.maxTotElapsed != null)
                return false;
        } else if (!maxTotElapsed.equals(other.maxTotElapsed))
            return false;
        if (maxTotSuspend == null) {
            if (other.maxTotSuspend != null)
                return false;
        } else if (!maxTotSuspend.equals(other.maxTotSuspend))
            return false;
        if (minIntCpu == null) {
            if (other.minIntCpu != null)
                return false;
        } else if (!minIntCpu.equals(other.minIntCpu))
            return false;
        if (minIntElapsed == null) {
            if (other.minIntElapsed != null)
                return false;
        } else if (!minIntElapsed.equals(other.minIntElapsed))
            return false;
        if (minIntSuspend == null) {
            if (other.minIntSuspend != null)
                return false;
        } else if (!minIntSuspend.equals(other.minIntSuspend))
            return false;
        if (minTotCpu == null) {
            if (other.minTotCpu != null)
                return false;
        } else if (!minTotCpu.equals(other.minTotCpu))
            return false;
        if (minTotElapsed == null) {
            if (other.minTotElapsed != null)
                return false;
        } else if (!minTotElapsed.equals(other.minTotElapsed))
            return false;
        if (minTotSuspend == null) {
            if (other.minTotSuspend != null)
                return false;
        } else if (!minTotSuspend.equals(other.minTotSuspend))
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
        if (tms == null) {
            if (other.tms != null)
                return false;
        } else if (!tms.equals(other.tms))
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