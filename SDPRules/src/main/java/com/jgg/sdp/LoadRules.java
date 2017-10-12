package com.jgg.sdp;

import java.io.File;

import com.jgg.sdp.core.config.*;
import com.jgg.sdp.core.ctes.*;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.*;

import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.rules.LoadRulesParms;
import com.jgg.sdp.rules.RULEParser;
import com.jgg.sdp.rules.processor.*;
import com.jgg.sdp.rules.xml.*;

public class LoadRules {
    
    private Configuration cfg = DBConfiguration.getInstance();

	private int     rc      = RC.OK;

	private boolean replace = false;
	
	private RulesCleaner cleaner;  
    private RulesGroup   rulesGroup;
    private RulesItem    rulesItem;
    private RulesRule    rulesRule;

    private CommonService dbCommon  = new CommonService();
    
    public LoadRules() {
          initObject();    	
    }
    
	public static void main(String[] args) throws Exception {
	   int rc = RC.OK;
	   LoadRules launcher = new LoadRules();
	   rc = launcher.start(args);
       System.exit(rc);
	}

	private int start(String[] args) {
		args = cfg.processCommandLine(LoadRulesParms.parms, args);
		
		processXMLRule(new Archivo("P:/SDP/config/rule00.xml"));
		for (Archivo archivo : FileFinder.find(null, args)) {
	        processXMLRule(archivo);
	        storeXMLRule();
		}

		return rc;		
	}
	
	// Se invoca desde test
	public  void processXMLRule(File file) {
		processXMLRule(new Archivo(file));
        storeXMLRule();
	}
	
	private void processXMLRule(Archivo archivo) {

		initEnvironment();
		
		try {
			RULEParser parser = new RULEParser();
			//RULES rule = parser.parse("P:/SDP/config/rule00.xml");
			RULES rule = parser.parse(archivo.getAbsolutePath());
			processHeader(rule);

            for (Group group : rule.getGroup()) processGroup(group);
            for (Item  item  : rule.getItem())  processItem(item);
			
		} catch (Exception ex) {
			if (ex instanceof javax.xml.bind.UnmarshalException) {
	            javax.xml.bind.UnmarshalException unmarshalEx = (javax.xml.bind.UnmarshalException) ex;
	            if (unmarshalEx.getLinkedException() != null) {
	                System.out.println(unmarshalEx.getLinkedException().getMessage());
	            } else {
	                System.out.println(unmarshalEx.getMessage());
	            }
	        }
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void processHeader(RULES rule) {
		Header header = rule.getHeader();
		if (header == null) {
			replace = false;
			return;
		}
		replace = header.isReplace();
	}
	
    private void processGroup(Group xmlGroup) {
    	
    	RULGroup group = rulesGroup.createGroup(xmlGroup);
    	for (Item xmlItem : xmlGroup.getItem()) {
    		RULItem item = rulesItem.createItem(group.getIdGroup(), xmlItem);
    		for (Rule xmlRule : xmlItem.getRule()) {
    		   rulesRule.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
    		}
    	}
    }

    private void processItem(Item xmlItem) {
    	
   		RULItem item = rulesItem.createItem(xmlItem);
    	for (Rule xmlRule : xmlItem.getRule()) {
    	     rulesRule.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
    	}
    }

    private void storeXMLRule() {
    	dbCommon.beginTrans();
    	for (RULGroup group : rulesGroup.getGroups()) {
    		if (replace) cleaner.deleteGroup(group.getIdGroup());
    		dbCommon.update(group);
    	}

    	for (RULItem item : rulesItem.getItems()) {
    		if (replace) cleaner.deleteItem(item.getIdGroup(), item.getIdItem());
    		dbCommon.update(item);
    	}

    	for (RULRule rule : rulesRule.getRules()) {
    		dbCommon.update(rule);
    	}
    	
    	dbCommon.commitTrans();
    	
//    	if (replace) cleaner.deleteGroup(xmlGroup);
    }
    
	private void initObject() {
		cfg.addTitle(CDG.TXT_TITLE, MSG.TITLE_SDP_ANALYZER);		
	}


	private void initEnvironment() {
		cleaner    = new RulesCleaner();
	    rulesGroup = new RulesGroup();
	    rulesItem  = new RulesItem();
	    rulesRule  = new RulesRule();
	    
	    RulesDescription.getInstance().clear();
	}
}
