package com.jgg.sdp.rules.processor;

import com.jgg.sdp.rules.xml.jaxb.ScriptType;

import java.util.ArrayList;

import com.jgg.sdp.domain.rules.RULScript;
import com.jgg.sdp.domain.services.rules.RULScriptsService;

public class RulesScript {

	private RULScriptsService scriptService = new RULScriptsService();
	
	private ArrayList<RULScript> scripts = new ArrayList<RULScript>();
	
    private static RulesScript script = null;
    
    private RulesScript() {
    	
    }
    
    public static RulesScript getInstance() {
    	if (script == null) script = new RulesScript();
    	return script;
    }
    
    public ArrayList<RULScript> getScripts() {
    	return scripts;
    }
    
    public Long createScript(Long key, ScriptType script) {
    	scriptService.deleteScript(key);
    	int count = 1;
		if (script == null) return 0L;

		String name = script.getName();
		if (name == null) name = "NO NAME";
		scripts.add(createLine(key, 1, 0, name));
		for (String line : script.getCode()) {
			scripts.add(createLine(key, 1, count++, line));
		}
		return key;
    }

    public void clear() {
    	scripts = new ArrayList<RULScript>();
    }
    
    private RULScript createLine(Long id, Integer type, Integer line, String data) {
    	RULScript script = new RULScript();
    	script.setIdScript(id);
    	script.setIdType(type);
    	script.setIdSeq(line);
    	script.setCode(data.trim());
    	return script;
    }
    
    
}
