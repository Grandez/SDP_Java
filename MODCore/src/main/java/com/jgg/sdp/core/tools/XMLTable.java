/**
 * Implementa la gestion de documentos XML con objetos serializados 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.tools;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.security.utils.Base64;


public class XMLTable {



/**
 * Generate un documento XML a partir de un objeto serializado
 *
 * @param o El objeto seri
 * @return El document XML en modo texto
 */
public String generate(Object o) {
      String result = "";
      
	  String root = o.getClass().getName();
	  root = root.substring(root.lastIndexOf('.') + 1);
	  
	  try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(root);
		String ser = serialize(o);
		   rootElement.appendChild(doc.createTextNode(ser));
		   doc.appendChild(rootElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
	        StringWriter sw=new StringWriter();
			StreamResult sr=new StreamResult(sw);
			transformer.transform(source,sr);
			result=sw.toString();

		return result;
		
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	  
	  return result;
	}

    private String serialize(Object obj) {
    	try {
    	   ByteArrayOutputStream bs= new ByteArrayOutputStream();
    	   ObjectOutputStream os = new ObjectOutputStream (bs);
    	   os.writeObject(obj);  // this es de tipo DatoUdp
    	   os.close();
    	   return Base64.encode(bs.toByteArray());
    	}
    	catch (Exception e) {
    		System.err.println(e.getLocalizedMessage());
    		
    	}
    	return "";
    }
    
}