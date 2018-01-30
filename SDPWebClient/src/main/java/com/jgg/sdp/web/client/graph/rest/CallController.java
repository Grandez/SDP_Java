package com.jgg.sdp.web.client.graph.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.common.ctes.CDG;
import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.SDPModuloService;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.tools.Fechas;
import com.jgg.sdp.web.client.graph.json.*;

import static com.jgg.sdp.module.graph.NodeTypes.*;

import java.util.Collections;

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
        CallStack stack = new CallStack();
        
        MODVersion ver = versionService.findById(idVersion);
    	if (ver == null) return graph;
    	
    	graph.setData(true);
    	graph.setName(ver.getNombre());
    	        
        JSonNode node = createJSonNode(stack, ver.getNombre(), 0);
//        graph.addNode(node);
    	stack.push(ver.getNombre(), null);

        loadCalleds(graph, stack, 1, node);
        Collections.sort(graph.getNodes());
    	return graph;
    }

    private String loadCalleds(JSonGraph graph, CallStack stack, int level, JSonNode parent) {
    	String recursive = null;
    	JSonNode node;
    	if (parent.isVirtual()) return recursive;
    	
    	for (MODCall called : callService.listCalls(parent.getIdNode())) {
    		node = createJSonNode(graph, stack, called, level);
            JSonEdge edge = new JSonEdge(parent.getIdNode(), node.getIdNode(), level);
            edge.setData(createJSonCall(called));
            graph.addEdge(edge);
            if (stack.containsNode(called.getModulo())) {
            	edge.setType(JSonEdge.REC_END);
            	recursive = called.getModulo();
    		} else {
    			if (!stack.processed(node.getName())) {
    				stack.addNode(node);
    		        stack.push(called.getModulo(), edge);
    		        recursive = loadCalleds(graph, stack, level + 1, node);
		           if (recursive != null) stack.setRecursive();
		           stack.pop();
    			}
		    }
    	}    
    	
    	stack.removeNode(parent.getName());
    	graph.addNode(parent);
    	if (recursive != null && parent.getName().compareTo(recursive) != 0) return recursive;
    	return null;
    }
    
    private JSonNode createJSonNode(CallStack stack, String name, int level) {
    	JSonNode n = stack.getNode(name);
    	if (n != null) return n;

    	JSonNode node = createNode(name, level);
    	node.setType(ROOT);
    	node.setData(new JSonCall());
    	return node;
    }	

    private JSonNode createJSonNode(JSonGraph graph, CallStack stack, MODCall call, int level) {
    	JSonNode n = stack.getNode(call.getModulo());
    	if (n != null) {
    		if (n.getLevel() > level) n.setLevel(level);
    		return n;
    	}

    	JSonNode node = createNode(call.getModulo(), level);
        switch (call.getMetodo()) {
           case CDG.CALL_UNDEF:  node.setType(GRAL);  break;
           case CDG.CALL_CALL:   node.setType(COBOL); break;
           case CDG.CALL_LINK:   node.setType(CICS);  break;
           case CDG.CALL_XCTL:   node.setType(CICS);  break;
           case CDG.CALL_START:  node.setType(TRAN);  break;
           case CDG.CALL_RETURN: node.setType(TRAN);  break;
           default:              node.setType(GRAL);

        }

		node.setData(createJSonCall(call));
		return node;
    }
    
    private JSonNode createNode(String name, int level) {    	
		SDPModulo mod = modService.findModuleName(name);
		Long idNode;
    	
		JSonNode node = new JSonNode();
    	
		if (mod == null) {
			idNode = Fechas.serial();
			 node.setVirtual(true);
		} else {
			idNode = mod.getIdVersion();
		}
		
    	node.setIdNode(idNode);
    	node.setName(name);
    	node.setLevel(level);
    	
    	return node;
    }
    
    private JSonCall createJSonCall(MODCall called) {
    	JSonCall j = new JSonCall();
    	j.setExecs(called.getEjecutado());
    	j.setMethod(called.getMetodo());
    	j.setMode(called.getModo());
    	j.setRefs(called.getRefs());
    	j.setStatus(called.getEstado());
    	return j;
    }
}
