/*
 * 
 */
package com.jgg.sdp.web.json.modulo;

import java.sql.Timestamp;

import com.jgg.sdp.domain.module.MODResumen;

public class Resumen {

    private String  nombre = "";
    
    private Integer   depCopy = 0;;
    private Integer   depModulo = 0;;
    
    private Long      ejecuciones = 0L;
    private Timestamp lastSesion;
    private Timestamp lastCompile;
    
    private Integer   lineas     = 0;
    private Integer   sentencias = 0;
    private Integer   parrafos   = 0;
    private Integer   badStmts   = 0; 
    
    private String    uid = "";

    private MODResumen info = null;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDepCopy() {
        return depCopy;
    }

    public void setDepCopy(Integer depCopy) {
        this.depCopy = depCopy;
    }

    public Integer getDepModulo() {
        return depModulo;
    }

    public void setDepModulo(Integer depModulo) {
        this.depModulo = depModulo;
    }

    public Long getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(Long ejecuciones) {
        this.ejecuciones = ejecuciones;
    }

    public Timestamp getLastSesion() {
        return lastSesion;
    }

    public void setLastSesion(Timestamp lastSesion) {
        this.lastSesion = lastSesion;
    }

    public Timestamp getLastCompile() {
        return lastCompile;
    }

    public void setLastCompile(Timestamp lastCompile) {
        this.lastCompile = lastCompile;
    }

    public Integer getLineas() {
        return lineas;
    }

    public void setLineas(Integer lineas) {
        this.lineas = lineas;
    }

    public Integer getSentencias() {
        return sentencias;
    }

    public void setSentencias(Integer sentencias) {
        this.sentencias = sentencias;
    }

    public Integer getParrafos() {
        return parrafos;
    }

    public void setParrafos(Integer parrafos) {
        this.parrafos = parrafos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    /**
     * Inc dep copy.
     */
    public void incDepCopy() {
        depCopy++;
    }
    
    /**
     * Inc dep modulo.
     */
    public void incDepModulo() {
        depModulo++;
    }

    public void setInfo(MODResumen info) {
        this.info = info;
    }
    
    public MODResumen getInfo() {
        return info;
    }
    
    public Integer getBadStmts() {
        return badStmts;
    }
    public void setBadStmts(Integer num) {
        badStmts = num;
    }
}
