/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.List;

import com.jgg.sdp.domain.traps.TRPSesion;

public class ModStats {

    
    List<TRPSesion> fallidas = null;
    
    Long    ejecuciones  = 0L;
    Long    elapsedTime  = 0L;
    Long    elapsedMin   = 0L;
    Long    elapsedMax   = 0L;
    Long    cpuTime      = 0L;
    Long    cpuMin       = 0L;
    Long    cpuMax       = 0L;
    Long    input        = 0L;
    Long    output       = 0L;
    Long    ioTotal      = 0L;
    Integer arbolModulos = null;
    Integer arbolRutinas = null;
    Integer arbolDepth   = null;

    
    public List<TRPSesion> getFallidas() {
        return fallidas;
    }
    public void setFallidas(List<TRPSesion> fallidas) {
        this.fallidas = fallidas;
    }
    public Long getEjecuciones() {
        return ejecuciones;
    }
    public void setEjecuciones(Long ejecuciones) {
        this.ejecuciones = ejecuciones;
    }
    public Long getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public Long getElapsedMin() {
        return elapsedMin;
    }
    public void setElapsedMin(Long elapsedMin) {
        this.elapsedMin = elapsedMin;
    }
    public Long getElapsedMax() {
        return elapsedMax;
    }
    public void setElapsedMax(Long elapsedMax) {
        this.elapsedMax = elapsedMax;
    }
    public Long getCpuTime() {
        return cpuTime;
    }
    public void setCpuTime(Long cpuTime) {
        this.cpuTime = cpuTime;
    }
    public Long getCpuMin() {
        return cpuMin;
    }
    public void setCpuMin(Long cpuMin) {
        this.cpuMin = cpuMin;
    }
    public Long getCpuMax() {
        return cpuMax;
    }
    public void setCpuMax(Long cpuMax) {
        this.cpuMax = cpuMax;
    }
    public Long getInput() {
        return input;
    }
    public void setInput(Long input) {
        this.input = input;
    }
    public Long getOutput() {
        return output;
    }
    public void setOutput(Long output) {
        this.output = output;
    }
    
    public Long getIoTotal() {
        return ioTotal;
    }
    public void setIoTotal(Long ioTotal) {
        this.ioTotal = ioTotal;
    }
    public Integer getArbolModulos() {
        return arbolModulos;
    }
    public void setArbolModulos(Integer arbolModulos) {
        this.arbolModulos = arbolModulos;
    }
    public Integer getArbolRutinas() {
        return arbolRutinas;
    }
    public void setArbolRutinas(Integer arbolRutinas) {
        this.arbolRutinas = arbolRutinas;
    }
    public Integer getArbolDepth() {
        return arbolDepth;
    }
    public void setArbolDepth(Integer arbolDepth) {
        this.arbolDepth = arbolDepth;
    }    
    
}
