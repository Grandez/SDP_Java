package com.jgg.sdp.web.adm.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.CFGCodeService;
import com.jgg.sdp.web.adm.json.*;
import com.jgg.sdp.web.core.*;

import static com.jgg.sdp.common.ctes.CDG.*;

@RestController
public class ConfigController {

    @Autowired
    private CFGCodeService codeService;    
	
    @RequestMapping("/configuration")
    public JSonConfig getConfiguration(@RequestHeader HttpHeaders headers) {
    	JSonConfig cfg = new JSonConfig();    	
    	String lang[] = LANG.getLanguage(headers);

    	loadGroups(cfg, lang);
    	loadItems(cfg, lang);
    	
        return cfg;
    }

    private void loadGroups(JSonConfig cfg, String[] lang) {
    	for (CFGCode code : codeService.getGroupCodes(CFG_CONFIGURATION, lang)) {
    		JSonConfigGroup g = new JSonConfigGroup(code.getCode(), code.getData());
    		cfg.addGroup(g);
    	}
    }
    
    private void loadItems(JSonConfig cfg, String[] lang) {
    	HashMap<String, JSonConfigNode> map = new HashMap<String, JSonConfigNode>();

    	for (CFGConfig config : DBConfiguration.getRawConfiguration()) {
    		String toks[] = config.getClave().split("\\.");

    		JSonConfigNode parent = map.get(toks[0]);
  			if (parent == null) {
			    parent = mountItem(toks[0], lang); 
				cfg.addItem(parent);  	
				map.put(toks[0], parent);
  			}
  			
    		for (int idx = 1; idx < toks.length; idx++) {
    			JSonConfigNode children = parent.getChildrenNode(toks[idx]);
    			if (children == null) {
    				children = mountItem(toks[idx], lang);
    				parent.addChildren(children);
    			}
    			parent = children;
    		}
    		parent.setData(mountData(config));
    	}
    }
    
    private JSonConfigNode mountItem(String text, String[] lang) {
		JSonConfigNode j = new JSonConfigNode();
		j.setText(text);
		return j;
    }
    
    private JSonConfigData mountData(CFGConfig cfg) {
        JSonConfigData d = new JSonConfigData();
		d.setGrupo(cfg.getGrupo());
		d.setClave(cfg.getClave());
		d.setMask(cfg.getMask());
		d.setMaximo(cfg.getMaximo());
		d.setMinimo(cfg.getMinimo());
		d.setTipo(cfg.getTipo());
		d.setValor(cfg.getValor());
        return d; 
    }
    
}
