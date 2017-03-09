package com.jgg.sdp.domain.session;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="SES_SESIONES")
@NamedQueries({
    @NamedQuery(name="SESSesion.sesionesByVersion"
               ,query="SELECT s FROM SESSesion s        " +
                      "         WHERE s.idVersion = ?1  " +
                      "         AND   s.finished  >  0  " + 
                      "         AND   s.tms       >  ?2 " +
                      "         ORDER BY s.tms "
               )
    ,@NamedQuery(name="SESSesion.sesionesByElapsed"
                ,query="SELECT s FROM SESSesion s       " +
                       "         WHERE s.idVersion = ?1 " +
                       "         AND   s.finished > 0   " + 
                       "         AND   s.tms      >  ?2 " +
                       "         ORDER BY s.elapsed ASC "
    )

    ,@NamedQuery(name="SESSesion.sesiones"
               ,query="SELECT s FROM SESSesion s   " +
                      "         WHERE s.tms  > ?1  "
    )

    ,@NamedQuery(name="SESSesion.fallidas"
                ,query="SELECT s FROM SESSesion s       " +
                       "         WHERE s.finished = 0 " +
                       "         AND   s.tms > ?1   "
    )                   
    ,@NamedQuery(name="SESSesion.cuentaSesiones"
                ,query="SELECT s.idVersion, COUNT(s.idVersion) " +
                       "       FROM SESSesion s " +
                       "       WHERE s.tms > ?1 " +
                       "       GROUP BY s.idVersion  "
    )
    ,@NamedQuery( name="SESSesion.cuentaSesionesModulo"
                 ,query="SELECT COUNT(s.idVersion) " +
                        "       FROM SESSesion s "       +
                        "       WHERE s.idVersion = ?1 " + 
                        "       AND   s.tms > ?2 " 
    )

})
public class SESSesion implements Serializable {

    private static final long serialVersionUID = 5337526612257635638L;

    public final static String find = 
            "SELECT e FROM  SESSesion e WHERE e.idSesion = ?1";
    public final static String totSesiones = 
           "SELECT COUNT(s.tms), MAX(s.tms) " +
           "       FROM SESSesion s " +
           "       WHERE s.idVersion = ?1 AND s.tms > ?2";
    public final static String sesionesPorVersion = 
            "SELECT s.idVersion , COUNT(s.idVersion) " +
            "       FROM SESSesion s " +
            "       WHERE s.tms > ?1" +
            "       GROUP BY s.idVersion";

    
   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Column(name="idVersion")
   private Long idVersion;
   
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

   public Long getIdVersion() {
	   return idVersion;
   }

   public void setIdVersion(Long idVersion) {
	   this.idVersion = idVersion;
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
       result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
       result = prime * result + ((idVersion == null) ? 0 : idVersion.hashCode());
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
       SESSesion other = (SESSesion) obj;
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
