/**
 * Implementa un ligero cache de objetos de persistencia en memoria
 * para optimizar accesos a registros comunes:
 * <br>
 * El registro del modulo y version principalmente
 * <br>
 * Los registros tienen una clave Long
 * <br>  
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.domain;

import java.util.HashMap;

public class DBCache<T> {

    private HashMap<Long, T> cache = new HashMap<Long, T>();
    
    public T get(Long key) {
        return cache.get(key);
    }
    
    public T put(Long key, T value) {
        cache.put(key, value);
        return value;
    }
}
