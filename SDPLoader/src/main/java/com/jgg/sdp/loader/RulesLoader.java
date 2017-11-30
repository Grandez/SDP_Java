package com.jgg.sdp.loader;

import java.io.File;

import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.common.ctes.CFG;
import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.*;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.loader.jaxb.rules.*;
import com.jgg.sdp.rules.processor.*;

public class RulesLoader {
    
    private Configuration cfg = DBConfiguration.getInstance();
	
	private boolean replace = false;
	
	private RulesCleaner cleaner = null;  
    private RulesGroup   groups  = null;
    private RulesItem    items   = null;
    private RulesRule    rules   = null;

	private RulesDescription descs   = RulesDescription.getInstance();
	private RulesCondition   conds   = RulesCondition.getInstance();
	private RulesScript      scripts = RulesScript.getInstance();
	private RulesSample      samples = RulesSample.getInstance();
    
    private CommonService dbCommon  = new CommonService();

	public int load(String[] files) {
    	dbCommon.beginTrans();
    	if (cfg.getBoolean(CFG.LOADER_CLEAN)) deleteData();
    		
		if (files.length == 0) {
			loadFromResources();
		}
		for (int idx = 0; idx < files.length; idx++) {
			loadGeneric(files[idx]);
		}
		dbCommon.commitTrans();
		return 0;

	}
	
	private int loadFromResources() {
		XMLParser<SDPRules> loader = new XMLParser<SDPRules>();
		
		for (File f : loader.loadFromResource("rules")) {
//			System.out.print("Processing " + f.getName());
			SDPRules cfg = loader.readXML(f, "/SDPRules.xsd", SDPRules.class);
			loadXMLFile(cfg);
//	        System.out.println("\tOK");
		}

		return 0;
	}
	
	private int loadGeneric(String file) {
		return 0;
	}
    
	// Se invoca desde test
	public  void loadXMLFile(SDPRules rule) {
	    parseXMLRule(rule);
        storeXMLRule();
	}
	
	private void parseXMLRule(SDPRules rule) {

		initEnvironment();
		
		try {
			processHeader(rule);

			groups = RulesGroup.getInstance();
            for (Group group : rule.getGroup()) processGroup(group);
            for (Item  item  : rule.getItem())  processItem(item);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(16);
		}
	}

	private void processHeader(SDPRules rule) {
		Header header = rule.getHeader();
		if (header == null) {
			replace = true;
			return;
		}
		replace = header.isReplace();
	}
	
    private void processGroup(Group xmlGroup) {
    	RULGroup group = groups.createGroup(xmlGroup);
    	items = RulesItem.getInstance();
    	
        for (Item xmlItem : xmlGroup.getItem()) {
            RULItem item = items.createItem(group, xmlItem);
            rules = RulesRule.getInstance();
            for (Rule xmlRule : xmlItem.getRule()) {
            	rules.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
//            	r.friendly();
            }
        }
    }

    private void processItem(Item xmlItem) {
    	
   		RULItem item = items.createItem(xmlItem);
    	for (Rule xmlRule : xmlItem.getRule()) {
    	     rules.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
//    	     r.friendly();
    	}
    }

    private void storeXMLRule() {

    	// Siempre hay
    	for (RULGroup group : groups.getGroups()) {
    		if (replace) cleaner.deleteGroup(group.getIdGroup());
    		dbCommon.update(group);
    	}

    	if (items != null) {
    	   for (RULItem item : items.getItems()) {
    		   if (replace) cleaner.deleteItem(item.getIdGroup(), item.getIdItem());
    		   dbCommon.update(item);
    	   }
    	}
    	if (rules != null) {
    	   for (RULRule rule : rules.getRules())  dbCommon.update(rule);
    	}
    	if (conds != null) {
    	   for (RULCond cond : conds.getConditions()) dbCommon.update(cond);
    	}
    	if (scripts != null) {
    	   for (RULScript script : scripts.getScripts()) dbCommon.update(script);
    	}
    	if (descs != null) {
    	   for (RULDesc desc : descs.getDescriptions()) dbCommon.update(desc);
    	}
        if (samples != null) {
      	   for (RULSample     samp  : samples.getSamples()) dbCommon.update(samp);
      	   for (RULSampleDesc sampd : samples.getSamplesDesc()) dbCommon.update(sampd);
    	}    	
    }
    
	private void initEnvironment() {
		cleaner    = new RulesCleaner();
	    RulesGroup.clear();
	    RulesItem.clear();
	    RulesRule.clear();
	    
	    descs.clear();
	    conds.clear();
	    samples.clear();
	    scripts.clear();
	}

	public void deleteData() {
		CommonService common = new CommonService();
		common.deleteQuery("DELETE FROM RULGroup      g");
		common.deleteQuery("DELETE FROM RULItem       i");
		common.deleteQuery("DELETE FROM RULRule       r");
		common.deleteQuery("DELETE FROM RULCond       c");
		common.deleteQuery("DELETE FROM RULSample     s");
		common.deleteQuery("DELETE FROM RULSampleDesc d");
		common.deleteQuery("DELETE FROM RULDesc       d");
		common.deleteQuery("DELETE FROM RULScript     S");
	}

}