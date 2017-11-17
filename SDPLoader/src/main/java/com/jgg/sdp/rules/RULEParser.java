package com.jgg.sdp.rules;

import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.validation.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.jgg.sdp.core.exceptions.XMLException;
import com.jgg.sdp.loader.jaxb.rules.SDPRules;

public class RULEParser {

	Document doc = null;
	SDPRules rules = null;
	Unmarshaller um = null;
	
	public RULEParser() throws JAXBException, SAXException {
		JAXBContext ctx = JAXBContext.newInstance(SDPRules.class);
		
		SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = sf.newSchema(getClass().getResource("/SDPRULES.xsd"));
				
		um = ctx.createUnmarshaller();
		um.setSchema(schema);
        um.setEventHandler(new XMLException());
	}
	
	public SDPRules parse(String xmlFileName) throws Exception {
        return (SDPRules) um.unmarshal(new FileReader(xmlFileName));
	}
	
}
