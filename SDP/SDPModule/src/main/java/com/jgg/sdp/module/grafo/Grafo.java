/**
 * Implementa los nodos y metodos para la creacion del grafo
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.grafo;

import com.jgg.sdp.core.ctes.TRAP;

public class Grafo {
    
    Nodo root = null;
    Nodo current = null;

    public void addNode(Nodo nodo) {
        if (root == null) root = current;
        montaGrafo(nodo);
    }
    
    private void montaGrafo(Nodo nodo) {
        switch (nodo.getCause()) {
           case TRAP.IF: connectNodes(current, nodo);
                         break; 
        }
        current = nodo;
    }
    
    private void connectNodes(Nodo padre, Nodo hijo) {
        padre.addHijo(hijo);
        hijo.addPadre(padre);
    }
    
    public void setRoot(Nodo nodo) {
        
        this.root    = nodo;
        this.current = nodo;
    }
}
