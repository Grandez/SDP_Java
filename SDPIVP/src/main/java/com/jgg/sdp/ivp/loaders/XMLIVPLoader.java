package com.jgg.sdp.ivp.loaders;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.jgg.sdp.ivp.items.*;
import static com.jgg.sdp.ivp.items.IVPAction.*;

public class XMLIVPLoader {

	Document doc = null;
	
	public XMLIVPLoader(File loader) throws Exception {
		loadFile(loader.getAbsolutePath());
	}
	
	public void loadFile(String xmlFileName) throws Exception {

         File fXmlFile = new File(xmlFileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         doc = dBuilder.parse(fXmlFile);

         //optional, but recommended: normalization-in-dom-parsing-with-java-how-does-it-work
         doc.getDocumentElement().normalize();
	}
	
	public IVPConfig getConfig() {
		IVPConfig config = new IVPConfig();
		
		 NodeList nList = doc.getElementsByTagName("configuration");
		 if (nList == null || nList.getLength() == 0) return config;
		 
		 Element conf = (Element) nList.item(0);
		 
		 NodeList itemList = conf.getElementsByTagName("*");
		 if (itemList == null) return config;
		 
 		 for (int idx = 0; idx < itemList.getLength(); idx++) {
			 Element item = (Element) itemList.item(idx);
			 String value = item.getNodeName();
			 if (value.compareTo("description")   == 0) 
				 config.setDescription(item.getFirstChild().getNodeValue());
			 if (value.compareTo("workDirectory") == 0) 
				 config.setWorkDir(item.getFirstChild().getNodeValue());
		 }
 		 return config;
	}
	
	public List<IVPAction> getActions() {
		Element  node;
		
		ArrayList<IVPAction> lstActions = new ArrayList<IVPAction>();

		 NodeList nodes = doc.getElementsByTagName("action");
		 for (int idx = 0; idx < nodes.getLength(); idx++) {
			 IVPAction action = new IVPAction();
			 System.out.println(nodes.item(idx).getNodeName());
			 node = (Element) nodes.item(idx);
			 action.setType(getTypeNode(node.getAttribute("type")));
			 action.setValue(node.getFirstChild().getNodeValue());
			 lstActions.add(action);
		 }
		return lstActions;
	}
	
	private int getTypeNode(String name) {
		if (name.compareTo("sql")    == 0) return SQL_STMT;
		if (name.compareTo("script") == 0) return SQL_SCRIPT;
		return NONE;
	}
}