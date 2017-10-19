package com.jgg.sdp.rules;

import java.io.*;
import java.util.List;

import com.jgg.sdp.domain.rules.*;
import com.jgg.sdp.domain.services.rules.*;
import com.jgg.sdp.tools.Cadena;

public class RulesUnloader {

	private RULGroupsService groupService = new RULGroupsService();
	private RULItemsService  itemService  = new RULItemsService();
	private RULDescService   descService  = new RULDescService();
	
	private BufferedWriter bw = null;
	private FileWriter     fw = null;
    private StringBuilder  buffer = new StringBuilder();
    
	private final int INDENT = 3;
    
	public void unload(String[] args) {
	
		for (RULGroup group : groupService.listAll()) {
			begFile(group.getPrefix().toLowerCase());
			processGroup(1, group);
			endFile();
		}
	}
	
	private void processGroup(int l, RULGroup group) {

	    writeLine(l, replace("<p:group name=\"{g}\">"           , "{g}", group.getPrefix()));
	    writeLine(l, replace("   <parent>{p}</parent>"          , "{p}", group.getIdParent()));
	    writeLine(l, replace("   <id>{i}</id>"                  , "{i}", group.getIdGroup()));
	    writeLine(l, replace("   <prefix>{p}</prefix>"          , "{p}", group.getPrefix()));
	    writeLine(l, replace("   <active>{a}</active>"          , "{a}", group.getActive(), true));

	    if (group.getIdDesc() != 0) {
	    	processDescription(l+1, group.getIdDesc());
	    }
	    
	    if (group.getIdGroup() > 1) {
	        for (RULGroup g : groupService.listChildren(group.getIdGroup())) {
	        	processGroup(l+1, g);
	        }
	    }    
	    for (RULItem item : itemService.listByGroup(group.getIdGroup())) {
	    	processItem(l+1, item);
	    }
		writeLine(1, "</p:group>");
	}

	private void processItem(int l, RULItem item) {

	    writeLine(l, replace("<p:item name=\"{i}\">"            , "{i}", item.getObject()));
	    writeLine(l, replace("   <group>{g}</group>"            , "{g}", item.getIdGroup()));
	    writeLine(l, replace("   <id>{i}</id>"                  , "{i}", item.getIdItem()));
//	    writeLine(l, replace("   <prefix>{p}</prefix>"          , "{p}", group.getPrefix()));
//	    writeLine(l, replace("   <active>{a}</active>"          , "{a}", group.getActivo(), true));

	    if (item.getIdDesc() != 0) {
	    	processDescription(l+1, item.getIdDesc());
	    }
	    
		writeLine(l, "</p:item>");
		writeLine(l, "");
	}
	
	private void processDescription(int level, Long id) {
		List<RULDesc> desc = descService.findDescription(id);
		// Caso que faltan desripciones
		if (desc.isEmpty()) return;
		
		writeLine(level, "<description>");
		writeLine(level, replace("   <id>{i}</id>"                  , "{i}", desc.get(0).getIdDesc()));
		for (RULDesc d : desc) {
			String aux  = replace("<value lang=\"{l}\" dialect=\"{d}\">", "{l}", d.getIdLang());
			String aux1 = replace(aux, "{d}", d.getIdDialect());
			writeLine(level + 1, aux1 + d.getTxt() + "</value>");
		}
		writeLine(level, "</description>");
	}

	private String replace(String from, String cad, Long value) {
		return replace(from, cad, value.toString());
	}
	
	private String replace(String from, String cad, Integer value) {
		return replace(from, cad, value.toString());
	}

	private String replace(String from, String cad, Long value, boolean bool) {
		String b = (value == 0) ? "false" : "true";
		return replace(from, cad, b);
	}
	
	private String replace(String from, String cad, String value) {
		return from.replace(cad, value);
	}
	
	private void begFile(String name) {
		openFile(name);
		writeLine(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writeLine(0, "<p:RULES xmlns:p=\"http://www.sdp.com/SDPRULES\""); 
		writeLine(0, "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""); 
		writeLine(0, "xsi:schemaLocation=\"http://www.sdp.com/SDPRULES http://www.sdp.com/SDPRULES.xsd\">");
		writeLine();
    }
	
	private void endFile() {
		writeLine();
		writeLine(0, "</p:RULES>");
		closeFile();
	}

	private void writeLine() {
		try {
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeLine(int level) {
		writeLine(level, buffer.toString());
		buffer.setLength(0);
	}
	
	private void writeLine(int level, String data) {
		try {
		   bw.write(Cadena.spaces(level * INDENT));
		   bw.write(data);
		   bw.newLine();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void openFile(String name) {
		try {
			fw = new FileWriter("group_" + name + ".xml");
			bw = new BufferedWriter(fw);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void closeFile() {
		try {
		   bw.flush();
		   bw.close();
		   fw.flush();
		   fw.close();
		}
		catch (Exception e) {
			
		}
	}
	
}
