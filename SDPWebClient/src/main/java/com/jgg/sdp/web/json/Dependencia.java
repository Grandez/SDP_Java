/*
 * 
 */
package com.jgg.sdp.web.json;

public class Dependencia {

    private String  nombre;
    private Integer tipo;
    private Integer subTipo;
    private Integer estado;
    private String  desc;
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getTipo() {
        return tipo;
    }
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    public Integer getSubTipo() {
        return subTipo;
    }
    public void setSubTipo(Integer subTipo) {
        this.subTipo = subTipo;
    }
    public Integer getEstado() {
        return estado;
    }
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
