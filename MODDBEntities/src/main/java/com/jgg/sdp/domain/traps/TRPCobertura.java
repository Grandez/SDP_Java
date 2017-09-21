package com.jgg.sdp.domain.traps;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="TRP_COBERTURA")
@NamedQueries ({
	@NamedQuery( name="TRPCobertura.list"
	            ,query="SELECT c FROM TRPCobertura c " +
	                   "         WHERE c.idSesion = ?1 " +
	                   "         AND   c.idModulo = ?2 " ) 
})
public class TRPCobertura implements Serializable {

   private static final long serialVersionUID = 6223880520994874527L;

   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Id
   @Column(name="idModulo")
   private String idModulo;

   @Id
   @Column(name="orden")
   private Long orden;
   
   @Column(name="flags")
   private String flags;

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

   public void setOrden(Long orden) {
	   this.orden = orden;
   }
   
   public Long getOrden() {
	   return orden;
   }
   
   public String getFlags() {
	  return flags;
   }

   public void setFlags(String flags) {
	  this.flags = flags;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((flags == null) ? 0 : flags.hashCode());
	result = prime * result + ((idModulo == null) ? 0 : idModulo.hashCode());
	result = prime * result + ((idSesion == null) ? 0 : idSesion.hashCode());
	result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
	TRPCobertura other = (TRPCobertura) obj;
	if (flags == null) {
		if (other.flags != null)
			return false;
	} else if (!flags.equals(other.flags))
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
	if (orden == null) {
		if (other.orden != null)
			return false;
	} else if (!orden.equals(other.orden))
		return false;
	return true;
}

     
}
