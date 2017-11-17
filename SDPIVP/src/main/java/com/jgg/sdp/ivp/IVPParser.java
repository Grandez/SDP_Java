package com.jgg.sdp.ivp;

import java.io.FileReader;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.*;

import org.dom4j.Document;
import org.xml.sax.*;

import com.jgg.sdp.core.exceptions.XMLException;
import com.jgg.sdp.ivp.jaxb.SDPIVP;

public class IVPParser {
	Document doc = null;
	SDPIVP ivp = null;
	Unmarshaller um = null;
	
	public IVPParser() throws JAXBException, SAXException {
		JAXBContext ctx = JAXBContext.newInstance(SDPIVP.class);
		
		SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = sf.newSchema(getClass().getResource("/SDPIVP.xsd"));
				
		um = ctx.createUnmarshaller();
		um.setSchema(schema);
        um.setEventHandler(new XMLException());
	}
	
	public SDPIVP parse(String xmlFileName) throws Exception {
        return (SDPIVP) um.unmarshal(new FileReader(xmlFileName));
	}
	
}
