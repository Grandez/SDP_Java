/*
 * 
 */
package com.jgg.sdp.web.core.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.CFG;

import com.jgg.sdp.domain.core.*;
import com.jgg.sdp.domain.module.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.web.core.*;
import com.jgg.sdp.web.core.json.ApplTree;

import static com.jgg.sdp.web.core.WEBCodes.*;

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
        
        // TODO
        // Por ahora devolver solo 500
        ArrayList<ApplTree> appTree2 = new ArrayList<ApplTree>();
        for (int idx = 0; idx < appTree.size(); idx++) {
        	appTree2.add(appTree.get(idx));
        	if (idx > 100) break;
        }
    	return appTree2;
    }
    
    private void addAplicaciones(ArrayList<ApplTree> appTree, ApplTree padre) {
    	List<SDPAplicacion> appList = appNamed.listByParent(padre.getId());
    	for (SDPAplicacion app : appList) {
    		padre.setTipo(AREA);
            ApplTree node = new ApplTree();
            node.setId(app.getId());
            node.setText(app.getAplicacion());
            node.setParent(padre.getId().toString());
            node.setModulos(app.getVolumen());
            node.setParent(padre.getId().toString());
            node.setTipo(APPL);
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
    	int tipo = MOD;
    	MODSummary summ = sumNamed.find(idVersion);
       if (summ.hasCics()) tipo += CICS;
       if (summ.hasSgdb()) tipo += DB2;
       if (summ.hasMq())   tipo += MQ;
       if (summ.hasFichero()) tipo += FILE;
       return tipo;
    }
    
    private ApplTree createRoot(Long id) {
        ApplTree node = new ApplTree();
        node.setId(id);
        node.setParent( "#");
        node.setTipo(ROOT);
        
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