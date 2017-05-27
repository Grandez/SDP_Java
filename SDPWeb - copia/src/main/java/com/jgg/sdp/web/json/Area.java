/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.ArrayList;

public class Area {

    private boolean exist = false;
    private String nombre;
    private Long   id;

    
    public ArrayList<Appl> hijos = new ArrayList<Appl>();
    
    public Stats   stats;
    
    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public ArrayList<Appl> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Appl> appl) {
        this.hijos = appl;
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

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
        
}
