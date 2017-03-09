/*
 * 
 */
package com.jgg.sdp.web.json;

public class Ejecucion {

    String modulo;
    Long   veces;
    Long   elapsed;
    
    public String getModulo() {
        return modulo;
    }
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    public Long getVeces() {
        return veces;
    }
    public void setVeces(Long veces) {
        this.veces = veces;
    }
    public Long getElapsed() {
        return elapsed;
    }
    public void setElapsed(Long elapsed) {
        this.elapsed = elapsed;
    }
    
}
