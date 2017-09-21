package com.jgg.sdp.domain.traps;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="TRP_PARR_WORKING")
@NamedQueries ({
	@NamedQuery( name="TRPParrWorking.find"
	            ,query="SELECT p FROM TRPParrWorking p " +
	                   "         WHERE p.idSesion = ?1 " +
	                   "         AND   p.idModulo = ?2 " + 
         	           "         AND   p.orden BETWEEN ?3 AND ?4")
   ,@NamedQuery( name="TRPParrWorking.findBySesion"
                ,query="SELECT p FROM TRPParrWorking p " +
                       "         WHERE p.idSesion = ?1 " +
                       "         AND   p.idModulo = ?2 ") 

})

public class TRPParrWorking  implements Serializable{

   private static final long serialVersionUID = -7556243933089584512L;

   @Id
   @Column(name="idSesion")
   private String idSesion;

   @Id
   @Column(name="idModulo")
   private String idModulo;

   @Id
   @Column(name="orden")
   private Long orden;
   
   @Column(name="datos")
   private String datos;

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
   
   public String getDatos() {
	  return datos;
   }

   public void setDatos(String datos) {
	  this.datos = datos;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((datos == null) ? 0 : datos.hashCode());
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
	TRPParrWorking other = (TRPParrWorking) obj;
	if (datos == null) {
		if (other.datos != null)
			return false;
	} else if (!datos.equals(other.datos))
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