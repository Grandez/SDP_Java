/*
 * 
 */
package com.jgg.sdp.web.json;

public class Arbol {

    private Integer   id;
    private Long      idModulo;
    private Long      idVersion;
    private String    nombre;
    private Integer   parent;
    private Long      veces;
    private String    tms;
    private Long      avgElapsed;
    private Long      prcCpu; 
    private Long      prcSuspend;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getParent() {
        return parent;
    }
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    public Long getVeces() {
        return veces;
    }
    public void setVeces(Long veces) {
        this.veces = veces;
    }
    public String getTms() {
        return tms;
    }
    public void setTms(String tms) {
        this.tms = tms;
    }
    public Long getAvgElapsed() {
        return avgElapsed;
    }
    public void setAvgElapsed(Long avgElapsed) {
        this.avgElapsed = avgElapsed;
    }
    public Long getPrcCpu() {
        return prcCpu;
    }
    public void setPrcCpu(Long prcCpu) {
        this.prcCpu = prcCpu;
    }
    public Long getPrcSuspend() {
        return prcSuspend;
    }
    public void setPrcSuspend(Long prcSuspend) {
        this.prcSuspend = prcSuspend;
    }

}
