/**
 * Objetos Nodo del grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.grafo;

import java.util.ArrayList;

import com.jgg.sdp.module.items.Block;

public class Nodo {

    private Block bloque = null;
    private Integer cause;
    
    private ArrayList<Nodo> padres = new ArrayList<Nodo>();
    private ArrayList<Nodo> hijos  = new ArrayList<Nodo>();
    
    public Nodo(Block block, Integer cause) {
        bloque = block;
        this.cause = cause;
    }
    
    public Integer getCause() {
        return cause;
    }
    
    public void addPadre(Nodo nodo) {
        padres.add(nodo);
    }
    
    public void addHijo(Nodo nodo) {
        hijos.add(nodo);
    }
    
} 
