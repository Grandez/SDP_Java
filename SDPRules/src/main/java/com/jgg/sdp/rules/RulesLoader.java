package com.jgg.sdp.rules;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import com.jgg.sdp.common.config.*;
import com.jgg.sdp.common.ctes.*;
import com.jgg.sdp.common.files.*;
import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.*;

import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.rules.processor.*;
import com.jgg.sdp.rules.xml.jaxb.*;

public class RulesLoader {
    
    private Configuration cfg = DBConfiguration.getInstance();

	private int     rc      = RC.OK;

	private boolean replace = false;
	
	private RulesCleaner cleaner;  
    private RulesGroup   rulesGroup;
    private RulesItem    rulesItem;
    private RulesRule    rulesRule;

	private RulesDescription rulesDesc   = RulesDescription.getInstance();
	private RulesCondition   rulesCond   = RulesCondition.getInstance();
	private RulesScript      rulesScript = RulesScript.getInstance();
	private RulesSample      rulesSamp   = RulesSample.getInstance();
    
    private CommonService dbCommon  = new CommonService();
    
    public RulesLoader() {
          initObject();    	
    }
    
	public int load() {
		return load(new String[0]);
	}
	
	public int load(String[] args) {

		if (args.length == 0) {
			return loadFromResources();
		}
		else {
			return loadFromFileSystem(args);
		}
	}
	
	private int loadFromResources() {
		URL root = Thread.currentThread().getContextClassLoader().getResource("rules");
		File dir = new File(root.getPath());

		File[] files = dir.listFiles();
		Arrays.sort(files);
		
		for (File f : files) {
			if (f.getName().endsWith(".xml")) {
			    processXMLRule(f);
			}    
        }
		return 0;				
	}

	private int loadFromFileSystem(String[] args) {
		processXMLRule(new Archive("P:/SDP/config/rule00.xml"));
		for (Archive archivo : FileFinder.find(null, args)) {
	        processXMLRule(archivo);
		}

		return rc;		
	}
	
	// Se invoca desde test
	public  void processXMLRule(File file) {
		System.out.print("Processing " + file.getName());
	    parseXMLRule(file);
        storeXMLRule();
        System.out.println("\tOK");
	}
	
	private void parseXMLRule(File file) {

		initEnvironment();
		
		try {
			RULEParser parser = new RULEParser();
			//RULES rule = parser.parse("P:/SDP/config/rule00.xml");
			SDPRules rule = parser.parse(file.getAbsolutePath());
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

	private void processHeader(SDPRules rule) {
		Header header = rule.getHeader();
		if (header == null) {
			replace = false;
			return;
		}
//		replace = header.isReplace();
	}
	
    private void processGroup(Group xmlGroup) {
    	RULGroup group = rulesGroup.createGroup(xmlGroup);
        for (Item xmlItem : xmlGroup.getItem()) {
            RULItem item = rulesItem.createItem(group, xmlItem);
            for (Rule xmlRule : xmlItem.getRule()) {
            	RULRule r = rulesRule.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
//            	r.friendly();
            }
        }
    }

    private void processItem(Item xmlItem) {
    	
   		RULItem item = rulesItem.createItem(xmlItem);
    	for (Rule xmlRule : xmlItem.getRule()) {
    	     RULRule r = rulesRule.createRule(item.getIdGroup(), item.getIdItem(), xmlRule);
//    	     r.friendly();
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

    	for (RULCond cond : rulesCond.getConditions()) {
    		dbCommon.update(cond);
    	}

    	for (RULScript script : rulesScript.getScripts()) {
    		dbCommon.update(script);
    	}

    	for (RULDesc desc : rulesDesc.getDescriptions()) {
    		dbCommon.update(desc);
    	}
    	for (RULSample samp : rulesSamp.getSamples()) {
    		dbCommon.update(samp);
    	}
    	
    	dbCommon.commitTrans();
    	
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
	    RulesCondition.getInstance().clear();
	    RulesSample.getInstance().clear();
	}
}
