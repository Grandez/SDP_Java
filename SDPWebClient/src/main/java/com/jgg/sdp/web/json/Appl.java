/*
 * 
 */
package com.jgg.sdp.web.json;

public class Appl {

   String  nombre;
   Long    id;
   Integer aplicaciones = 0;
   Integer modulos      = 0;
   Integer orden;
   Long    sesiones;

   public Alertas alertas;
   

   public void setAplicaciones(Long inc) {
       aplicaciones = inc.intValue();
   }
   public void setAplicaciones(Integer inc) {
       aplicaciones = inc;
   }
   
   /**
     * Inc modulos.
     *
     * @param inc
     *            the inc
     */
   public void incModulos(Long inc) {
       modulos += inc.intValue();
   }
   
   /**
     * Inc modulos.
     *
     * @param inc
     *            the inc
     */
   public void incModulos(Integer inc) {
       modulos += inc;
   }

   public String getNombre() {
       return nombre;
   }
   public void setNombre(String nombre) {
       this.nombre = nombre;
   }
   public Long getId() {
       return id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   public Integer getAplicaciones() {
       return aplicaciones;
   }

   public Integer getModulos() {
       return modulos;
   }
   public void setModulos(Integer modulos) {
       this.modulos = modulos;
   }
   public Integer getOrden() {
       return orden;
   }

   public void setOrden(Integer orden) {
       this.orden = orden;
   }

   public Long getSesiones() {
      return sesiones;
   }

   public void setSesiones(Long sesiones) {
      this.sesiones = sesiones;
   }
   
   /**
     * Inc sesiones.
     *
     * @param value
     *            the value
     */
   public void incSesiones(long value) {
       this.sesiones += value;
   }

   public Alertas getAlertas() {
      return alertas;
   }

   public void setAlertas(Alertas alertas) {
       this.alertas = alertas;
   }
   
}
