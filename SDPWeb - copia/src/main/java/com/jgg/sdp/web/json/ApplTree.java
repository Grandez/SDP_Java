/*
 * 
 */
package com.jgg.sdp.web.json;

public class ApplTree {
 
   Long id;          
   String parent;
   String text; 
   Integer aplicaciones = 0;
   Integer modulos = 0;
   Integer tipo;
   
   public Long getId() {
	  return id;
   }

   public void setId(Long id) {
	  this.id = id;
   }

   public String getParent() {
	  return parent;
   }

   public void setParent(String parent) {
	  this.parent = parent;
   }

   public String getText() {
	   return text;
   }

   public void setText(String text) {
	   this.text = text;
   }

   public Integer getAplicaciones() {
      return aplicaciones;
   }

   public void setAplicaciones(Integer aplicaciones) {
       this.aplicaciones = aplicaciones;
   }

   public Integer getModulos() {
       return modulos;
   }

   public void setModulos(Integer modulos) {
       this.modulos = modulos;
   }

   public Integer getTipo() {
      return tipo;
   }

   public void setTipo(Integer tipo) {
      this.tipo = tipo;
   }
   
}
