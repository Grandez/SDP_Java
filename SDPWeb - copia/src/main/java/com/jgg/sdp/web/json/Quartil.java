/*
 * 
 */
package com.jgg.sdp.web.json;

public class Quartil {

    private String nombre;
    private Long    idModulo;
    private Long    idVersion = 0L;
    private int     total     = 0;
        
    private Long[] quartiles = new Long[5];
    
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
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Long[] getQuartiles() {
        return quartiles;
    }
    public void setQuartiles(Long[] quartiles) {
        this.quartiles = quartiles;
    }
    
    /**
     * Sets the quartil.
     *
     * @param index
     *            the index
     * @param value
     *            the value
     */
    public void setQuartil(int index, Long value) {
        this.quartiles[index] = value;
    }
    
}
