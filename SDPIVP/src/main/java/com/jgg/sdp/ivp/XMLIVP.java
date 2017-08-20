package com.jgg.sdp.ivp;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.jgg.sdp.ivp.cases.Case;

public class XMLIVP {

	Document doc = null;
	
	public void loadFile(String xmlFileName) {

	    try {
            File fXmlFile = new File(xmlFileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		doc = dBuilder.parse(fXmlFile);

    		//optional, but recommended
    		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    		doc.getDocumentElement().normalize();

	    } catch (Exception e) {
          e.printStackTrace();
        }
	}
	
	public List<Case> getCases() {
		NodeList pgms;
		Element  node;
		
		ArrayList<Case> cases = new ArrayList<Case>();
		
		NodeList nodeList = doc.getElementsByTagName("group");

		for (int idx = 0; idx < nodeList.getLength(); idx++) {

			Case c = new Case();
			
			node = (Element) nodeList.item(idx);
			c.setName(node.getAttribute("name"));
			
			Element d = (Element) node.getElementsByTagName("description").item(0);
			c.setDescription(d.getFirstChild().getNodeValue());
			
			pgms = node.getElementsByTagName("pattern");
			
			for (int p = 0; p < pgms.getLength(); p++) {
				Element e = (Element) pgms.item(p);				
				c.addModules(e.getFirstChild().getNodeValue());
			}
/*			
			list = node.getChildNodes();
			for (int tag = 0; tag < list.getLength(); tag++) {
			   item = list.item(tag);
			   if (item.getNodeType() == Node.ELEMENT_NODE) {
				   System.out.println("Es un elemento");
			   }
			}
	*/		
			cases.add(c);
		}
		return cases;
/*		
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				System.out.println("Staff id : " + eElement.getAttribute("id"));
				System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

			}
		}
	*/	
	}
	
/*
		

		System.out.println("----------------------------");

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	  }
*/
}