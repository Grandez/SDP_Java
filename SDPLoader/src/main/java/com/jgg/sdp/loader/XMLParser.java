package com.jgg.sdp.loader;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

import com.jgg.sdp.core.exceptions.XMLException;

public class XMLParser<T> {

	private Unmarshaller um = null;

	public List<File> loadFromResource(String res) {
		ArrayList<File> files = new ArrayList<File>();
		
		URL root = Thread.currentThread().getContextClassLoader().getResource(res);
		File dir = new File(root.getPath());

		File[] tf = dir.listFiles();
		Arrays.sort(tf);
		
		for (File f : tf) {
			if (f.getName().endsWith(".xml")) files.add(f);
        }
		return files;				
	}
	
	
	@SuppressWarnings("unchecked")
	public T readXML(File xmlFile, String sch, Class<T> c) {
		try {
		   JAXBContext ctx = JAXBContext.newInstance(c);
		
		   SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		   Schema schema = sf.newSchema(getClass().getResource(sch));
				
		   um = ctx.createUnmarshaller();
		   um.setSchema(schema);
           um.setEventHandler(new XMLException());
		   return (T) um.unmarshal(new FileReader(xmlFile));
		}
		catch (JAXBException ex) {
			System.out.println(ex.getMessage());
		}
		catch (SAXException ex) {
			System.out.println(ex.getMessage());
		}
		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
}
