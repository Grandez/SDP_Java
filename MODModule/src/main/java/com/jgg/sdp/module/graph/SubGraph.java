/**
 * Un subgrafo es una secuencia de acciones dentro de un bloque de codigo 
 * (Normalmente parrafos)
 * Cada accion es un nodo, el cual puede dirigir a varios nodos
 *  
 *  Todo subgrafo empieza con un nodo BEGIN y un nodo END
 *  
 *              BEGIN
 *                |
 *               END 
 */
package com.jgg.sdp.module.graph;

import java.util.*;

import com.jgg.sdp.tools.Cadena;

import static com.jgg.sdp.module.graph.NodeTypes.*;

public class SubGraph {
	
	private String  name;
	private Long    id;
	private boolean real  = false;
	private int     level = 0;
	
    private Node first;    
    private Node current = null;

    private FactoryGraphs nodes    = FactoryGraphs.getInstance(false);
    private Stack<Node>  currents = new Stack<Node>();
    
    boolean reduced = true;
    
    public SubGraph(long id, String name) {
        this.name = name;
        this.id = id;
    	first = nodes.getNode(BEGIN, "BEGIN");
    	first.addChild(nodes.getNode(END, "END"));
    	current = currents.push(first);
//    	print("BEGIN");
    }

    // GETTERS AND SETTERS
    
    public String  getName()   { return name;  }
    public long    getId()     { return id;    }
    public Node    getRoot()   { return first; }
    public boolean isGraph()   { return real;  }
    public int     getLevel()  { return level; }
    
    public void    setLevel(int level)  { 
    	if (this.level == 0) this.level = level;  
    }
    
    /**********************************************************/
    /* GESTION GRAFO                                          */
    /**********************************************************/
    
    /**
     * Nodo lineal
     * Son PERFORM 
     * 
     * BEG --> NODE1 --> END
     * CURRENT   X
     * STACK: NODE1
     *       
     * BEG --> NODE1 --> ACT --> END
     * CURRENT            X
     * STACK: ACT      
     * 
     *        
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void addLinearNode(Integer type, String name) {
    	addLinearNode(type, name, name);
    }
    
    
    public void addLinearNode(Integer type, String from, String to) {
    	addLinearNode(type, from, to, false);
    }
    
    public void addLinearNode(Integer type, String from, String to, boolean loop) {
       if (to == null) to = from;	
 	   Node tmp = nodes.getNode(type, from, to);
 	   tmp.addChild(current.getLast());
 	   current.replaceChild(tmp);
       current = tmp;
       stackReplace(tmp);
       if (loop) {
    	   current.addChildFirst(tmp);
    	   current.setActive(1);
       }
       real = true;
    }
    
    /**
     * Crea un bloque BEG-END
     * 
     * BEG --> NODE1 --> END
     * CURRENT   X
     * STACK: NODE1
     *       
     * BEG --> NODE1 --> BEG --> END --> END
     * CURRENT            X
     * STACK: BEG      
     * 
     */

    public void addBlock(Integer type) {
    	addBlock(type, type.toString(), false);
    }
    
    public void addBlock(Integer type, String name, boolean loop) {
    	Node beg = nodes.getNode(BLOCK, type, "BEGIN " + name);
    	Node end = nodes.getNode(END, BLOCK, "END " + name);

    	beg.addChild(end);
    	end.addChild(current.getLast());
    	current.replace(beg);
    	current = currents.push(beg);
    }


    /**
     * Inserta otra rama creando un subgrafo interno 
     * Hay que cerrar todas las pendientes
     * Caso EVALUATE WHEN
     *    
     * BEG --> NODE1 --> END
     * CURRENT   X
     * STACK: NODE1
     *       
     * BEG ---> NODE1 ------------+-> END ---> NEXT
     *            |               |         
     *            + BEG1 -> END1 -+         
     *       
     * STACK: NODE1 | BEG1      
     *       
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */

    public void addIF(Integer type) {
    	Node beg   = nodes.getNode(BEGIN, IF_BEG);
    	Node end   = nodes.getNode(END,   IF_END);
    	Node left  = nodes.getNode(BEGIN, IF_LEFT);
    	Node right = nodes.getNode(BEGIN, IF_RIGHT);
    	
    	end.moveChildren(current.getChildren());
    	
    	left.addChild(end);
    	right.addChild(end);
    	
    	
    	beg.addChild(left);
    	beg.addChild(right);
    	current.replaceChild(beg);
    	
    	stackReplace(end);
    	stackPush(beg, false);
    	stackPush(left, true);
    }

    public void addElse() {
    	stackSet(IF_BEG);
    	current.setActive(B_RIGHT);
    	stackReplace(current.getActiveChild());
    }
    
    public void addChoice(Integer type, boolean closeChoice) {
    	Node beg = nodes.getNode(BRANCH, type.toString());
    	Node end = nodes.getNode(END, "END " + type.toString());

    	stackSet(BLOCK, true);
    	
    	beg.addChild(end);
    	end.addChild(current.getLast());
    	if (closeChoice) {
    		current.replace(beg);
    	} else {
    	   current.addChildFirst(beg);
    	}
    	current = currents.push(beg);
    	//print("EDGE");
    }
    
        
    /**
     * Casos END-xxxx
     * Ajusta el nodo actual en la pila 
     *  
     * Ejemplo:
     *    
     *        Situacion previa
     *        
     * PREV -+-> BEG1 -> NODO -+-> END -> NEXT
     *       |                 |
     *       +---BEG2 -> NODO -+
     * STACK =  PREV - BEG2
     *
     * STACK = END
     * 
     * @param type Tipo del nodo central
     * @param from Nombre del nodo central
     * @param to   Parte THRU
     */
    
    public void endCycle(Integer type) {
    	stackSet(type);
    }
    
    /***************************************************/
    /* TRATAMIENTO DE LA PILA                          */
    /***************************************************/
    
    private void stackReplace(Node n) {
    	currents.pop();
    	current = currents.push(n);
    }
    
    private void stackPop() {
    	current = currents.pop();
    }

    private void stackPush(Node n) {
    	stackPush(n, true);
    }
    
    private void stackPush(Node n, boolean setCurrent) {
    	currents.push(n);
    	if (setCurrent) current = n;
    }
    
    private void stackSet(int subType) {
    	Node n = currents.pop();
    	while (n.getSubtype() != subType) 
    		n = currents.pop();
    	stackPush(n);
    }
    
    private void stackSet(int type, boolean keep) {
    	// En fin de programa y multimodulos puede llamarse varias veces
    	while (!currents.isEmpty() && (current = currents.pop()).getType() != type);
    	if (!currents.isEmpty()) current = currents.peek();
    	if (keep) current = currents.push(current);
    }
/*
    public SubGraph reduce() {
    	ArrayList<Node> childs;
    	ArrayList<Node> newChilds;
    	Node curr = first;
    	Node wrk;
    	
    	while (reduced) {
    	   childs = curr.getNodes();
    	   newChilds = new ArrayList<Node>();
    	   for (int i = 0; i < childs.size(); i++) {
    		   wrk = childs.get(i);
	    		if (wrk.isVirtual()) {
	    			for (Node n : wrk.getNodes()) newChilds.add(n); 
	    		}
	    		else {
	    			newChilds.add(wrk);
	    		}
    	   }
    	   curr.setChilds(newChilds);
    	   Node padre = curr;
 		   for (Node next: curr.getNodes()) {

// 			   Node hijo = getHijo(next);
   			
 		   }
    		
    	}
    }
*/    
    private Node getChild(Node n) {
    	if (n == null) return null;
    	Node child = getChild(n);
    	if (child == null) return null;
        return n;  	
    	
    }
    @Override
    public String toString() {

    	StringBuilder str = new StringBuilder();
    	str.append(name + "\n" + first.toString() + "\n");

    	HashSet<Long> nodes = new HashSet<Long>();    	
    	nodes.add(first.getId());
    	
    	for (Node next : first.getChildren()) {
    		str.append(toString(1, next, nodes));
    	}
    	return str.toString();
    }
    
    private String toString(int level, Node node, HashSet<Long> nodes) {
    	
    	if (nodes.contains(node.getId())) return "";
    	
    	StringBuilder str = new StringBuilder();
        String pad = Cadena.spaces(level * 3);
        
		str.append(pad + "|\n");        
        str.append(pad + node.toString() + "\n");
        
    	for (Node n : node.getChildren()) {
    		str.append(toString(level + 1, n, nodes));
    	}
    	return str.toString();
    }

}
