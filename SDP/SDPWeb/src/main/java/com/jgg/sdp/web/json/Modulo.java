package com.jgg.sdp.web.json;

import java.sql.Timestamp;
import java.util.*;

import com.jgg.sdp.domain.module.*;

public class Modulo {

    private boolean exist = false;
    private Long    idModulo;
    private Long    idVersion;
    private String  nombre;
    
    private MODResumen     resumen;
    private MODVersion     version;
    
    private ArrayList<MODDependencia> dependencias = new ArrayList<MODDependencia>();
    
    private List<MODParrafo> parrafos;
    private List<MODGrafo>   grafo;
    
    private Long      cobertura;
    private Long      ejecuciones;
    private Timestamp lastSesion;
    
    private Integer   depCopy = 0;;
    private Integer   depModulo = 0;;
    
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
    public MODResumen getResumen() {
        return resumen;
    }
    public void setResumen(MODResumen resumen) {
        this.resumen = resumen;
    }
    public MODVersion getVersion() {
        return version;
    }
    public void setVersion(MODVersion version) {
        this.version = version;
    }
    public Long getCobertura() {
        return cobertura;
    }
    public void setCobertura(Long cobertura) {
        this.cobertura = cobertura;
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

    public ArrayList<MODDependencia> getDependencias() {
        return dependencias;
    }
    public void setDependencias(ArrayList<MODDependencia> dependencias) {
        this.dependencias = dependencias;
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
    
    public void incDepCopy() {
        depCopy++;
    }
    public void incDepModulo() {
        depModulo++;
    }
}
