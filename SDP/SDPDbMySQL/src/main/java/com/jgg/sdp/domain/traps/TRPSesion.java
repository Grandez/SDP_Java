package com.jgg.sdp.domain.traps;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="TRP_SESIONES")
@NamedQueries({
    @NamedQuery(name="TRPSesion.fallidas",
                query="SELECT t FROM  TRPSesion t WHERE t.finished = 0 AND t.tms > ?1")
   ,@NamedQuery(name="TRPSesion.fallidasPorModulo",
                query="SELECT t FROM  TRPSesion t WHERE t.finished = 0 " +
                                                 " AND t.idModulo = ?1 " + 
                                                 " AND t.tms > ?2")

})
public class TRPSesion implements Serializable {

   private static final long serialVersionUID = 8980861710552442388L;

   public final static String find = 
            "SELECT e FROM  TRPSesion e WHERE e.idSesion = ?1";
   
   public final static String delTraps = 
           "DELETE FROM  TRPSesion t WHERE t.idSesion = ?1";

   
   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Id
   @Column(name="idModulo")
   private String idModulo;
   
   @Column(name="uid")
   private String uid;

   @Column(name="elapsed")
   private Long elapsed;

   @Column(name="cpu")
   private Long cpu;

   @Column(name="suspend")
   private Long suspend;

   @Column(name="leido")
   private Long leido;

   @Column(name="escrito")
   private Long escrito;
   
   @Column(name="finished")
   private Integer finished;
   
   @Column(name="tms")
   private Timestamp tms;

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
   
   public String getUid() {
	   return uid;
   }

   public void setUid(String uid) {
	   this.uid = uid;
   }

   public Long getElapsed() {
	   return elapsed;
   }

   public void setElapsed(Long elapsed) {
	  this.elapsed = elapsed;
   }

   public Long getCpu() {
	  return cpu;
   }

   public void setCpu(Long cpu) {
	  this.cpu = cpu;
   }

   public Long getSuspend() {
	  return suspend;
   }

   public void setSuspend(Long suspend) {
	  this.suspend = suspend;
   }

   public Long getLeido() {
	  return leido;
   }

   public void setLeido(Long leido) {
	  this.leido = leido;
   }

   public Long getEscrito() {
       return escrito;
    }

    public void setEscrito(Long escrito) {
       this.escrito = escrito;
    }
   
   public Integer getFinished() {
	  return finished;
   }

   public void setFinished(Integer finished) {
	  this.finished = finished;
   }

   public Timestamp getTms() {
	  return tms;
   }

   public void setTms(Timestamp tms) {
	  this.tms = tms;
   }

@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
    result = prime * result + ((elapsed == null) ? 0 : elapsed.hashCode());
    result = prime * result + ((escrito == null) ? 0 : escrito.hashCode());
    result = prime * result + ((finished == null) ? 0 : finished.hashCode());
    result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
    result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
    result = prime * result + ((leido == null) ? 0 : leido.hashCode());
    result = prime * result + ((suspend == null) ? 0 : suspend.hashCode());
    result = prime * result + ((tms == null) ? 0 : tms.hashCode());
    result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
    TRPSesion other = (TRPSesion) obj;
    if (cpu == null) {
        if (other.cpu != null)
            return false;
    } else if (!cpu.equals(other.cpu))
        return false;
    if (elapsed == null) {
        if (other.elapsed != null)
            return false;
    } else if (!elapsed.equals(other.elapsed))
        return false;
    if (escrito == null) {
        if (other.escrito != null)
            return false;
    } else if (!escrito.equals(other.escrito))
        return false;
    if (finished == null) {
        if (other.finished != null)
            return false;
    } else if (!finished.equals(other.finished))
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
    if (leido == null) {
        if (other.leido != null)
            return false;
    } else if (!leido.equals(other.leido))
        return false;
    if (suspend == null) {
        if (other.suspend != null)
            return false;
    } else if (!suspend.equals(other.suspend))
        return false;
    if (tms == null) {
        if (other.tms != null)
            return false;
    } else if (!tms.equals(other.tms))
        return false;
    if (uid == null) {
        if (other.uid != null)
            return false;
    } else if (!uid.equals(other.uid))
        return false;
    return true;
}
   
   

   
}
