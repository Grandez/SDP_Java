/*
 * 
 */
package com.jgg.sdp.web.json;

import java.sql.Timestamp;

public class Tiempos {

    private String aplicacion;
    private String modulo;
    private String sesion;
    
    private Long   idAppl;
    private Long   idModulo;
    private Long   idVersion;
    
    private Long   elapsed;
    private Long   cpu;
    private Long   suspend;
    
    private Long   leido;
    private Long   escrito;
    
    private Timestamp fecha;
    private String    uid;
    
    public String getAplicacion() {
        return aplicacion;
    }
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }
    public String getModulo() {
        return modulo;
    }
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    
    public String getSesion() {
        return sesion;
    }
    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
    public Long getIdAppl() {
        return idAppl;
    }
    public void setIdAppl(Long idAppl) {
        this.idAppl = idAppl;
    }
    public Long getIdModulo() {
        return idModulo;
    }
    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }
    public Long getIdVersion() {
        return idVersion;
    }
    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
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
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    
}
