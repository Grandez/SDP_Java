/*
 * 
 */
package com.jgg.sdp.web.json;

import java.util.*;

public class Quartiles {

    private Long minElapsed;
    private Long maxElapsed;
    private Integer minSesiones;
    private Integer maxSesiones;
    private Integer items;
    
    private List<Quartil> lstQuartiles;
    
    /**
     * Instantiates a new quartiles.
     */
    public Quartiles() {
        lstQuartiles = new ArrayList<Quartil>();
        minElapsed = Long.MAX_VALUE;
        maxElapsed = 0L;
        minSesiones = Integer.MAX_VALUE;
        maxSesiones = 0;
        items = 0;
    }
    
    public Long getMinElapsed() {
        return minElapsed;
    }
    public void setMinElapsed(Long minElapsed) {
        this.minElapsed = minElapsed;
    }
    public Long getMaxElapsed() {
        return maxElapsed;
    }
    public void setMaxElapsed(Long maxElapsed) {
        this.maxElapsed = maxElapsed;
    }
    public Integer getMinSesiones() {
        return minSesiones;
    }
    public void setMinSesiones(Integer minSesiones) {
        this.minSesiones = minSesiones;
    }
    public Integer getMaxSesiones() {
        return maxSesiones;
    }
    public void setMaxSesiones(Integer maxSesiones) {
        this.maxSesiones = maxSesiones;
    }
    public void setItems(int items) {
        this.items = items;
    }
    public Integer getItems() {
        return items;
    }

    /**
     * Adds the quartil.
     *
     * @param q
     *            the q
     */
    public void addQuartil(Quartil q) {
        lstQuartiles.add(q);
        items = lstQuartiles.size();
        if (q.getQuartiles()[0] < minElapsed) minElapsed = q.getQuartiles()[0];
        if (q.getQuartiles()[4] > maxElapsed) maxElapsed = q.getQuartiles()[4];
        if (q.getTotal() < minSesiones) minSesiones = q.getTotal();
        if (q.getTotal() > maxSesiones) maxSesiones = q.getTotal();
    }
    public List<Quartil> getLstQuartiles() {
        return lstQuartiles;
    }    
    
}
