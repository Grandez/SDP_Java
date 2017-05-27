/*
 * 
 */
package com.jgg.sdp.web.rest.app;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.web.core.DBConfiguration;
import com.jgg.sdp.web.json.ApplTree;
import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;

@RestController
public class AppTreeController {

    private Configuration cfg = DBConfiguration.getInstance();
    
    @Autowired
    private SDPAplicacionService appService;

    @Autowired
    private SDPModuloService modService;

    @RequestMapping("/apptree")
    public List<ApplTree> mountTree() {
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
    	List<SDPAplicacion> appList = appService.listByPadreId(padre.getId());
    	for (SDPAplicacion app : appList) {
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
    	List<SDPModulo> modList = modService.getModulesByAppl(padre.getId());
    
    	for (SDPModulo m : modList) {
    		ApplTree node = new ApplTree();
    		node.setId(m.getIdModulo());
    		node.setText(m.getNombre());
            node.setParent(padre.getId().toString());
            node.setTipo(1);    
    		appTree.add(node);
    	}
    }
        
    private ApplTree createRoot(Long id) {
        ApplTree node = new ApplTree();
        node.setId(id);
        node.setParent( "#");
        node.setTipo(0);
        
        if (id == 0) {
            node.setText(cfg.getString(CFG.INST_NAME));
            node.setModulos(0);
            node.setAplicaciones(0);
        }
        else {
           SDPAplicacion app = appService.findById(id);
           node.setText(app.getAplicacion());
           node.setModulos(app.getVolumen());
           node.setAplicaciones(0);
        }
        return node;
    }

}