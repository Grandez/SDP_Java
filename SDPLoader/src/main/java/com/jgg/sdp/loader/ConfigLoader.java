package com.jgg.sdp.loader;

import java.io.File;

import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.domain.cfg.*;
import com.jgg.sdp.domain.services.cfg.*;
import com.jgg.sdp.loader.jaxb.config.*;

public class ConfigLoader {
    
    private CFGConfigService        cfgService     = new CFGConfigService();
    private CFGConfigTooltipService tooltipService = new CFGConfigTooltipService();
    private CFGConfigDescService    descService    = new CFGConfigDescService();
    
    private Configuration cfg = DBConfiguration.getInstance();
        
	public int load(String[] files) {
    	cfgService.beginTrans();
    	if (cfg.getBoolean(CFG.LOADER_CLEAN)) deleteData();
    		
		if (files.length == 0) {
			loadFromResources();
		}
		for (int idx = 0; idx < files.length; idx++) {
			loadGeneric(files[idx]);
		}
		cfgService.commitTrans();
		return 0;
	}
	
	private int loadFromResources() {
		XMLParser<SDPConfig> loader = new XMLParser<SDPConfig>();
		
		for (File f : loader.loadFromResource("config")) {
			SDPConfig cfg = loader.readXML(f, "/SDPConfig.xsd", SDPConfig.class);
			loadXMLFile(cfg);
		}
		return 0;		
	}
	
	private int loadGeneric(String file) {
		return 0;
	}
	
	private void loadXMLFile(SDPConfig cfg) {
		for (Group g : cfg.getGroup()) {
			for (Item i : g.getItem()) {
				String key = g.getName() + "." + i.getName();
				loadItem(key, g.getId(), i);
				loadTexts(key, i);
			}
		}
	}
	
	private void loadItem(String key, Integer group, Item i) {
		
		CFGConfig c = cfgService.get(key);
		if (c == null) {
			c = new CFGConfig();
			c.setClave(key);
			c.setTipo(0);
		}
		c.setGrupo(group);
		c.setMask(i.getMask());
		if (i.getMin() != null) c.setMinimo(i.getMin().toString());
		if (i.getMax() != null) c.setMaximo(i.getMax().toString());
		if (i.getType() != null) c.setTipo(i.getType());
		c.setValor(i.getValue());
		cfgService.update(c);

	}

	private void loadTexts(String key, Item i) {
		for (Description d : i.getDescription()) {
			loadTooltip(key, d.getLang(), d.getDialect(), d.getTooltip());
			loadDesc(key, d.getLang(), d.getDialect(), d.getDesc());
		}
	}
	
	private void loadTooltip(String key, String lang, String dialect, String text) {
		CFGConfigTooltip t = tooltipService.getTooltip(key, lang, dialect);
		if (text != null) {
			if (t == null) {
				t = new CFGConfigTooltip();
				t.setClave(key);
				t.setIdLang(lang);
				t.setIdDialect(dialect);
			}
			t.setTooltip(text);
			tooltipService.update(t);
		}
		else {
			if (t != null) {
				tooltipService.delTooltip(key, lang, dialect);
			}
		}
	}

	private void loadDesc(String key, String lang, String dialect, String text) {
		CFGConfigDesc t = descService.getDescription(key, lang, dialect);
		if (text != null) {
			if (t == null) {
				t = new CFGConfigDesc();
				t.setClave(key);
				t.setIdLang(lang);
				t.setIdDialect(dialect);
			}
			t.setData(text);
			descService.update(t);
		}
		else {
			if (t != null) {
				descService.delDescription(key, lang, dialect);
			}
		}
	}

	private void deleteData() {
		cfgService.deleteAll();
		descService.deleteAll();
		tooltipService.deleteAll();
	}
}
