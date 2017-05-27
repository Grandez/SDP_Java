/*
 * 
 */
package com.jgg.sdp.web.json.modulo;

import java.util.*;

import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.core.ctes.SYS;

public class Modulo {

    private boolean exist = false;
    
    private Long    idModulo;
    private Long    idVersion;
    private String  nombre;
    private String  desc;

    private int status = SYS.STATUS_OK;
    
    private Resumen resumen;
    private Attrs   attrs;
    
    private Integer maxCCParr;
    private Integer maxCC;
    private Integer maxStmt;
    

    
    private List<MODParrafo> parrafos;
    private List<MODGrafo>   grafo;
    
    private Long   maxCobertura;
    private Long   cobertura;
    
    
    public boolean isExist() {
        return exist;
    }
    public void setExist(boolean exist) {
        this.exist = exist;
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
    public Long getCobertura() {
        return cobertura;
    }
    public void setCobertura(Long cobertura) {
        this.cobertura = cobertura;
    }
    public Long getMaxCobertura() {
        return maxCobertura;
    }
    public void setMaxCobertura(Long cobertura) {
        this.maxCobertura = cobertura;
    }
    
    public void setDesc(String desc) {
    	this.desc = desc;
    }
    
    public String getDesc() {
    	return desc;
    }
    
    public Integer getMaxCCParr() {
        return maxCCParr;
    }
    public void setMaxCCParr(Integer maxCCParr) {
        this.maxCCParr = maxCCParr;
    }
    public Integer getMaxCC() {
        return maxCC;
    }
    public void setMaxCC(Integer maxCC) {
        this.maxCC = maxCC;
    }
    public Integer getMaxStmt() {
        return maxStmt;
    }
    public void setMaxStmt(Integer maxStmt) {
        this.maxStmt = maxStmt;
    }

    public List<MODParrafo> getParrafos() {
        return parrafos;
    }
    public void setParrafos(List<MODParrafo> parrafos) {
        this.parrafos = parrafos;
    }

    public List<MODGrafo> getGrafo() {
        return grafo;
    }
    public void setGrafo(List<MODGrafo> grafo) {
        this.grafo = grafo;
    }
    public void setResumen(Resumen resumen) {
        this.resumen = resumen;
    }
    public Resumen getResumen() {
        return resumen;
    }
    public void setAttrs(Attrs attrs) {
        this.attrs = attrs;
    }
    public Attrs getAttrs() {
        return attrs;
    }

    public void setStatus(int st) {
    	this.status = st;
    }
    public int getStatus() {
    	return this.status;
    }
}
