package com.jgg.sdp.web.client.graph.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.SDPModuloService;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.tools.Fechas;
import com.jgg.sdp.web.client.graph.json.*;

import static com.jgg.sdp.module.graph.NodeTypes.*;

@RestController
public class CallController {

    @Autowired
    private SDPModuloService modService;
	
    @Autowired
    private MODVersionService versionService;
    @Autowired
    private MODCallService    callService;
    
    /**
     * Devuelve la lista de modulos llamados indicando quien lo llamo
     *
     * @param idVersion Version del modulo principal
     * @return la lista de modulos invocados
     */
    @RequestMapping("/call/{idVersion}")
    public JSonGraph getCall(@PathVariable Long idVersion) {
        JSonGraph graph = new JSonGraph(idVersion);
        HashSet<String> map = new HashSet<String>();
        
    	MODVersion ver = versionService.findById(idVersion);
        
    	if (ver == null) return graph;
    	
    	graph.setData(true);
    	graph.setName(ver.getNombre());
    	
    	JSonNode node = new JSonNode();
    	node.setIdVersion(idVersion);
        node.setIdGraph(idVersion);
    	node.setIdNode(idVersion);
    	node.setType(MODULE);
    	node.setName(ver.getNombre());
        graph.addNode(node);
        
        // Defecto para la raiz
    	JSonCall j = new JSonCall();
    	j.setExecs(1L);
    	j.setMethod(0);
    	j.setMode(0);
    	j.setRefs(1);
    	j.setStatus(1);
    	node.setData(j);
    	
        map.add(node.getName());
        loadCallers(graph, node, 1, map);
    	return graph;
    }

    private void loadCallers(JSonGraph graph, JSonNode parent, int level,  HashSet<String> map) {
    	Long idGraph = graph.getIdGraph();
    	for (MODCall called : callService.listCalls(parent.getIdNode())) {
    		if (map.contains(called.getModulo())) continue;
    		map.add(called.getModulo());
    		SDPModulo mod = modService.findModuleName(called.getModulo());
    	    JSonNode node = new JSonNode(called.getIdVersion());
    	    node.setIdVersion(graph.getIdVersion());
            node.setIdGraph(idGraph);
            node.setIdNode(mod == null ? Fechas.serial() : mod.getIdVersion());
        	node.setType(called.getModo());
        	node.setName(called.getModulo());
        	node.setData(mountJSonCall(called));
            graph.addNode(node);

            JSonEdge edge = new JSonEdge(parent.getIdNode(), node.getIdNode());
            graph.addEdge(edge);
            loadCallers(graph, node, level + 1, map);
    	}
    	
    }
    
    private JSonCall mountJSonCall(MODCall called) {
    	JSonCall j = new JSonCall();
    	j.setExecs(called.getEjecutado());
    	j.setMethod(called.getMetodo());
    	j.setMode(called.getModo());
    	j.setRefs(called.getRefs());
    	j.setStatus(called.getEstado());
    	return j;
    }
}
