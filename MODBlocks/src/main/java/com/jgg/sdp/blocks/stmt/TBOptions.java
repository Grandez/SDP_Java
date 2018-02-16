/**
 * Guarda las opciones junto con sus mapas
 */
package com.jgg.sdp.blocks.stmt;

import java.util.*;

import com.jgg.sdp.adt.ADTHashDup;

public class TBOptions {
    // Opciones segun aparecen
    protected ArrayList<Option>    lstOptions = new ArrayList<Option>();
    
    // Mapeo para busqueda por nombre
    private ADTHashDup<String, Integer>  mapOptName = new ADTHashDup<String, Integer>();
    // Mapeo para busqueda por SDPSymbol.id
    private ADTHashDup<Integer, Integer> mapOptId = new ADTHashDup<Integer, Integer>();

    public Option add(Option opt) {
    	if (opt != null) {
		   lstOptions.add(opt);
		   mapOptName.addItem(opt.getName(),lstOptions.size() - 1);
		   mapOptId.addItem  (opt.getId(),  lstOptions.size() - 1);
    	}
		return opt;
    }
    
    public List<Option> getOptions() {
    	return lstOptions;
    }
    
    public int numOptions() {
		return lstOptions.size();
	}
    
	public boolean hasOption(int id) { 
	   return mapOptId.getItem(id) != null;
	}

	public boolean hasOption(String name) {
		return mapOptName.getItem(name) != null;
    }

	public Option getOption(int id) {
		Integer pos = mapOptId.getItem(id);
    	return (pos == null) ? null : lstOptions.get(pos);
	}
	
	public Option getOptionByPos(int pos) {
    	return lstOptions.get(pos);
    }
	public Option getOptionByName(String name) {
		Integer pos = mapOptName.getItem(name);
    	return (pos == null) ? null : lstOptions.get(pos);
	}

	public Option replace(Option opt) {
	    lstOptions.remove(lstOptions.size() - 1);
	    return add(opt);
	}		

	
}
