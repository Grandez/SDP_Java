/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.web.core.*;
import com.jgg.sdp.web.json.*;

import com.jgg.sdp.web.WebCtes;

@RestController
public class AppTreeController {

    private Configuration cfg = DBConfiguration.getInstance();

    @Autowired
    private SDPAplicacionService appNamed;    
    @Autowired
    private SDPModuloService modNamed;
    @Autowired
    private MODSummaryService sumNamed;

    @RequestMapping("/appTree")
    public List<ApplTree> mountAppTree() {
        return mountTree(0L);
    }

    @RequestMapping("/areaTree")
    public List<ApplTree> mountAreaTree() {
        return mountTree(0L);
    }
    
    @RequestMapping("/apptree/{id}")
    public List<ApplTree> mountTree(@PathVariable Long id) {
        ArrayList<ApplTree> appTree = new ArrayList<ApplTree>();
        ApplTree node = createRoot(id);
        appTree.add(node);
        addModulos(appTree, node);    
        addAplicaciones(appTree, node);
        
    	return appTree;
    }
    
    private void addAplicaciones(ArrayList<ApplTree> appTree, ApplTree padre) {
    	List<SDPAplicacion> appList = appNamed.listByParent(padre.getId());
    	for (SDPAplicacion app : appList) {
    		padre.setTipo(WebCtes.AREA);
            ApplTree node = new ApplTree();
            node.setId(app.getId());
            node.setText(app.getAplicacion());
            node.setParent(padre.getId().toString());
            node.setModulos(app.getVolumen());
            node.setParent(padre.getId().toString());
            node.setTipo(0);
            padre.setModulos(padre.getModulos() + node.getModulos());
            appTree.add(node);
            addModulos(appTree, node);
    		addAplicaciones(appTree, node);
    		padre.setAplicaciones(padre.getAplicaciones() + node.getAplicaciones() + 1);
    		padre.setModulos(padre.getModulos() + node.getModulos());
    	}
    }

    private void addModulos(ArrayList<ApplTree> appTree, ApplTree padre) {
    	List<SDPModulo> modList = modNamed.listModulesByAppl(padre.getId());
    
    	for (SDPModulo m : modList) {
    		ApplTree node = new ApplTree();
    		node.setId(m.getIdModulo());
    		node.setText(m.getNombre());
            node.setParent(padre.getId().toString());
            node.setTipo(calculateType(m.getIdVersion()));    
    		appTree.add(node);
    	}
    }
    
    private int calculateType(long idVersion) {
    	int tipo = WebCtes.MOD;
    	MODSummary summ = sumNamed.find(idVersion);
       if (summ.hasCics()) tipo += WebCtes.CICS;
       if (summ.hasSgdb()) tipo += WebCtes.DB2;
       if (summ.hasMq())   tipo += WebCtes.MQ;
       if (summ.hasFichero()) tipo += WebCtes.FILE;
       return tipo;
    }
    
    private ApplTree createRoot(Long id) {
        ApplTree node = new ApplTree();
        node.setId(id);
        node.setParent( "#");
        node.setTipo(WebCtes.APPL);
        
        if (id == 0) {
            node.setText(cfg.getString(CFG.INST_NAME));
            node.setModulos(0);
            node.setAplicaciones(0);
        }
        else {
           SDPAplicacion app = appNamed.findById(id);
           node.setText(app.getAplicacion());
           node.setModulos(app.getVolumen());
           node.setAplicaciones(0);
        }
        return node;
    }

}