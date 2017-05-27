/*
 * 
 */
package com.jgg.sdp.web.json;

public class Stats {

    private Integer aplicaciones;
    private Integer modulos;     
    private Integer activos;
    private Integer variacion;
    private Long    sesiones;
    
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
    public Long getSesiones() {
        return sesiones;
    }
    public void setSesiones(Long sesiones) {
        this.sesiones = sesiones;
    }
    public Integer getVariacion() {
        return variacion;
    }
    public void setVariacion(Integer variacion) {
        this.variacion = variacion;
    }
    public Integer getActivos() {
        return activos;
    }
    public void setActivos(Integer activos) {
        this.activos = activos;
    }
    
}
