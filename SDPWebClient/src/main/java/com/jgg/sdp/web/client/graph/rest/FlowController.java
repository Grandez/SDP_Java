/**
 * Servicio REST para devolver la información relativa a los arboles de llamadas
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.web.client.graph.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.graph.*;
import com.jgg.sdp.domain.services.graph.*;

import com.jgg.sdp.web.client.graph.json.*;

@RestController
public class FlowController {

    @Autowired
    private DCGGraphService graphService;
    @Autowired
    private DCGNodeService  nodeService;
    @Autowired
    private DCGEdgeService  edgeService;
    
    /**
     * Devuelve la lista de modulos llamados indicando quien lo llamo
     *
     * @param idVersion Version del modulo principal
     * @return la lista de modulos invocados
     */
    @RequestMapping("/graph/{idVersion}")
    public List<JSonGraph> getGraph(@PathVariable Long idVersion) {
    	return getGraphs(idVersion);
    }
    
    private List<JSonGraph> getGraphs(Long idVersion) {
    	List<JSonGraph> graphs = new ArrayList<JSonGraph>();
    	for (DCGGraph graph : graphService.getGraphs(idVersion)) {
    		JSonGraph g = createGraph(graph);
    		if (graph.getIdGrafo() == 0) g.setGroup(false);
    		graphs.add(g);
    	}
    	return graphs;
    }
    
    private JSonGraph createGraph(DCGGraph graph) {
    	JSonGraph g = new JSonGraph();
    	g.setData(true);
    	g.setIdVersion(graph.getIdVersion());
    	g.setIdGraph(graph.getIdGrafo());
    	g.setName(graph.getName());
    	loadEdges(g, loadNodes(g));
    	return g;
    }
    
    private HashMap<Long, String> loadNodes(JSonGraph graph) {
    	HashMap<Long, String> nodes = new HashMap<Long, String>();
    	for (DCGNode node : nodeService.listNodes(graph.getIdVersion(),  graph.getIdGraph())) {
    		JSonNode n = new JSonNode();
//    		n.setIdVersion(node.getIdVersion());
//    		n.setIdGraph(node.getIdGrafo());
    		n.setIdNode(node.getIdNode());
    		n.setName(node.getNombre());
    		n.setType(node.getTipo());
    		n.setSubGraph(node.getSubgraph());
    		graph.addNode(n);
    		nodes.put(n.getIdNode(), n.getName());
    	}
    	return nodes;
    }
    
    private void loadEdges(JSonGraph graph, HashMap<Long, String> nodes) {
    	for (DCGEdge edge : edgeService.listEdges(graph.getIdVersion(),  graph.getIdGraph())) {
    		graph.addEdge(new JSonEdge(edge.getIdFrom(), edge.getIdTo()));
    	}
    }
    
}
