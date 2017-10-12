package com.jgg.sdp.rules;

import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.validation.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.jgg.sdp.core.exceptions.XMLException;
import com.jgg.sdp.rules.xml.RULES;

public class RULEParser {

	Document doc = null;
	RULES rules = null;
	Unmarshaller um = null;
	
	public RULEParser() throws JAXBException, SAXException {
		JAXBContext ctx = JAXBContext.newInstance(RULES.class);
		
		SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = sf.newSchema(getClass().getResource("/SDPRULES.xsd"));
				
		um = ctx.createUnmarshaller();
		um.setSchema(schema);
        um.setEventHandler(new XMLException());
	}
	
	public RULES parse(String xmlFileName) throws Exception {
        return (RULES) um.unmarshal(new FileReader(xmlFileName));
	}
	
/*	
	public IVPConfig getConfig() {
		
		
		 
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

	private Element getNode(String name) {
		NodeList nList = doc.getElementsByTagName(name);
		if (nList == null || nList.getLength() == 0) return null;
		return (Element) nList.item(0);
	}
	
	private Element getNode(Element node, String name) {
		NodeList nList = node.getElementsByTagName(name);
		if (nList == null || nList.getLength() == 0) return null;
		return (Element) nList.item(0);
	}
*/	
}
