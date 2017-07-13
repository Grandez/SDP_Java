/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.*;

import com.jgg.sdp.domain.session.SESSesion;
import com.jgg.sdp.core.ctes.LOG;

public class Alertas {
    private List<SESSesion> fallidas    = new ArrayList<SESSesion>();
    private List<Tiempos>   elapsedTime = new ArrayList<Tiempos>();
    private List<Tiempos>   cpuTime     = new ArrayList<Tiempos>();
    private List<Tiempos>   suspendTime = new ArrayList<Tiempos>();
    private List<Tiempos>   medias      = new ArrayList<Tiempos>();
    private List<Tiempos>   otras       = new ArrayList<Tiempos>();
    
    public List<SESSesion> getFallidas() {
        return fallidas;
    }

    public void setFallidas(List<SESSesion> fallidas) {
        this.fallidas = fallidas;
    }

    public List<Tiempos> getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(List<Tiempos> elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<Tiempos> getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(List<Tiempos> cpuTime) {
        this.cpuTime = cpuTime;
    }

    public List<Tiempos> getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(List<Tiempos> suspendTime) {
        this.suspendTime = suspendTime;
    }

    public List<Tiempos> getMedias() {
        return medias;
    }

    public void setMedias(List<Tiempos> medias) {
        this.medias = medias;
    }

    public List<Tiempos> getOtras() {
        return otras;
    }

    public void setOtras(List<Tiempos> otras) {
        this.otras = otras;
    }
    
    /**
     * Adds the alerta.
     *
     * @param code
     *            the code
     * @param t
     *            the t
     */
    public void addAlerta(int code, Tiempos t) {
        switch (code) {
           case LOG.SES_ELAPSED: elapsedTime.add(t); break;
           case LOG.SES_CPU:     cpuTime.add(t);     break;
           case LOG.SES_SUSPEND: suspendTime.add(t); break;
           default: otras.add(t);
        }
    }
}
