/**
 * Controlador REST para obtener informacion de los issues
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.web.rest.modulo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgg.sdp.domain.core.SDPModulo;
import com.jgg.sdp.domain.rules.RULRules;
import com.jgg.sdp.domain.services.*;
import com.jgg.sdp.domain.services.core.*;
import com.jgg.sdp.domain.services.module.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.web.json.modulo.Issues;

@RestController
public class IssueController {

    @Autowired
    SDPModuloService    moduloService;	
    @Autowired
    MODIssueService     issueService;
    @Autowired
    ArbolVersionService arbolService;
    @Autowired
    RULRulesService     rulesService;

    
    @RequestMapping("/issues/{idModule}")
    public Issues getIssues(@PathVariable Long idModule) {
        SDPModulo modulo = moduloService.findById(idModule);
        return getIssuesInfo(modulo.getIdVersion());

    }
    
    private Issues getIssuesInfo(Long idVersion) {
    	Issues issues = new Issues();
    	getIssues(issues, idVersion);
    	getStatus(issues, idVersion);
    	return issues;
    }

    private void getIssues(Issues issues, Long idVersion) {
    	issues.setIssues((ArrayList<Integer>) issueService.getIssuesCount(idVersion));
    }

    private void getStatus(Issues issues, Long idVersion) {
    	int max = 0;
    	
    	RULRules[] rules = loadRules(idVersion);
    	ArrayList<Integer> valores = issues.getIssues(); 
    	for (int idx = 1; idx < valores.size(); idx++) {
    		max += (idx * valores.get(idx));
    		if (valores.get(idx) > rules[idx].getValor()) {
    		   issues.setStatus(-1);
    		}
    	}
    	if (issues.getStatus() == 0) {
    		if (max > rules[99].getValor()) issues.setStatus(-1);
    	}
    }
    
    private RULRules[] loadRules(Long idVersion) {
    	RULRules[] rules = new RULRules[100];
    	
    	List<Long> arbol = arbolService.getTreeVersion(idVersion);
    	for (int idx = arbol.size() - 1; idx >= 0; idx--) {
    		List<RULRules> lista = rulesService.getRulesByObject(arbol.get(idx));
    		for (RULRules rule : lista) {
    			rules[rule.getSeverity()] = rule;
    		}
    	}
    	return rules;
    }
}
