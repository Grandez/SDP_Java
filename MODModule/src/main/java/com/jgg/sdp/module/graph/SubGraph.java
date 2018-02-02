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

import com.jgg.sdp.core.ctes.TRAP;
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
    
    public void endGraph(int stmts) {
    	current.endStmts(stmts);
    }
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

    public void addLinearNode(Integer type, int stmts) {
    	addLinearNode(type, "CODE", stmts);
    }
    public void addLinearNode(Integer type, int subtype, int stmts) {
    	addLinearNode(type, "CODE", stmts);
    }

    public void addLinearNode(Integer type, String name, int stmts) {
    	addLinearNode(type, name, name, stmts);
    }
    public void addLinearNode(Integer type, String from, String to, int stmts) {
    	addLinearNode(type, from, to, stmts, false);
    }
    
    public Node addLinearNode(Integer type, String from, String to, int stmts, boolean loop) {
  	    Node node = nodes.getNode(type, from, to, stmts);
  	  
    	if (current.getSubtype() == BRANCH_BEG) {
    		node.addChild(current.getTerminal());
    		node.setCause(EDGE_IF);
    		current.addChild(node);
    		stackPush(node);
    		return node;
    	}
        
 	   if (current.getType() == TRAP.BLOCK) {
 		  current.endStmts(stmts);  
 	 	  if (current.getStmts() == 0) {
 	 		  current.copy(node);
 	 		  real = true;
 	 		  return node;
 	 	  }
 	   }

 	   node.addChild(current.getChild());
 	   current.replaceChild(node);
 	   stackReplace(node);
 	   
       if (loop) {
    	   current.addChild(node);
    	   current.setActive(1);
       }
       real = true;
       return node;
    }
    
    /**
     * Inserta otra rama creando un subgrafo interno 
     * Hay que cerrar todas las pendientes
     * Caso EVALUATE WHEN
     *    
     * BEG --> NODE1 --> END
     * STACK: NODE1
     *       
     * BEG ---> NODE1 ------------+-> END ---> NEXT
     *            |               |         
     *            + BEG1 -> END1 -+         
     *       
     * STACK: NODE1 | END1 | BEG1      
     *       
     */
    
    public Node addBlock(int cause, int stmts, String label) {
    	current.endStmts(stmts);
    	Node beg   = nodes.getNode(cause, BRANCH_BEG, stmts);
    	Node end   = nodes.getNode(END,   BRANCH_END, stmts);
    	
    	end.setCause(EDGE_ELSE);
    	beg.setTerminal(end);
    	
    	switch (cause) {
    	   case TRAP.IF:     beg.setName("IF");       break;
    	   case TRAP.EVAL:   beg.setName("EVALUATE"); break;
    	   case TRAP.SEARCH: beg.setName("SEARCH");   break;
    	}

    	if (current.getSubtype() == BRANCH_BEG) {
    		end.addChild(current.getTerminal());
    		beg.setCause(EDGE_IF);
    		current.addChild(beg);
    	}
    	else {
    		end.addChild(current.getChild());
        	current.replaceChild(beg);
    	}
    	
    	stackReplace(end);
    	stackPush(beg);
    	return beg;
    }
    
    public void endBlock(int stmts, boolean full) {
    	Node end;
    	Node n;
    	do { end = stackPop();
    	     end.endStmts(stmts);
    	}  	while (end.getSubtype() != BRANCH_BEG && end.getSubtype() != BRANCH_END);
    	
    	// Si encuentra un IF, detras esta su end (Caso IF IF)
    	// Si encuentra en END ha habido un ELSE
    	if (end.getSubtype() == BRANCH_BEG) {
    	    if (end.getNumChilds() < 2) end.addChild(end.getTerminal());
    	    end = stackPop();
    	}
    	
    	// Fin con punto. Acaba todas las ramas
    	if (full) {
    		n = stackPop();
    		while (n.getSubtype() == BRANCH_END) {
    			n = stackPop();
    			if (n.getSubtype() == BRANCH_BEG) {
    				if (n.getNumChilds() < 2) n.addChild(end.getTerminal());
    			}
    		}
    	   n.endStmts(stmts); 
    	}
 	   stackPush(end);
    }
    
    public Node addBranch(int type, int stmts, boolean close, String label) {
    	current.endStmts(stmts);

    	Node node   = nodes.getNode(TRAP.BLOCK, BRANCH, "CODE", stmts);
    	node.setLabel(label);
		node.setCause(EDGE_ELSE);
		
    	if (type == TRAP.WHEN) {
    		node.setCause(EDGE_IF);
    		if (close) node.setCause(EDGE_ELSE);
     	}
    	
    	stackSet(BRANCH_BEG);
    	current.addChild(node);
    	node.addChild(current.getTerminal());  
        
    	// Si hay que cerrar, hay que quitar la parte else
    	// del nodo anterior al end
    	
    	if (close) stackPop(false); // Quitar IF
    		
//    		Node ifend = stackPop(true);  // Guardar el END-BRANCH
//    		current.removeChild(0);       // Quitar la parte ELSE (0 Siempre apunta al fin)
//    		stackPush(ifend, false);      // Restarurar el END-BRANCH
//    	}
    	stackPush(node);
    	
    	return node;
    }
    
    /***************************************************/
    /* TRATAMIENTO NODO                                */
    /***************************************************/

    public void setStmtsBeg(int beg) {
    	current.setStmts(beg);
    }
    
    public void setStmtsEnd(int end) {
    	current.setStmts(end - current.getStmts());
    }
    
    /***************************************************/
    /* TRATAMIENTO DE LA PILA                          */
    /***************************************************/
    
    private void stackReplace(Node n) {
    	currents.pop();
    	current = currents.push(n);
    }
    
    private Node stackPop() {
    	return stackPop(true);
    }
    private Node stackPop(boolean setCurrent) {
    	Node n = currents.pop();
    	if (setCurrent && !currents.empty()) current = currents.peek();
    	return n;
    }

    private void stackPush(Node n) {
    	stackPush(n, true);
    }
    
    private void stackPush(Node n, boolean setCurrent) {
    	currents.push(n);
    	if (setCurrent) current = n;
    }
    
    private Node stackSet(int subType) {
    	Node n = currents.pop();
    	while (n.getSubtype() != subType) 
    		n = currents.pop();
    	stackPush(n);
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
