package com.jgg.sdp.module.unit;

import java.util.*;

import com.jgg.sdp.common.files.Archive;
import com.jgg.sdp.module.base.Module;

public class Unit {

	// Lista de ficheros fuente (al menos uno el principal)
	private ArrayList<Source> sources = new ArrayList<Source>();
	// La posicion se corresponde con el source equivalente
	private ArrayList<Module> modules = new ArrayList<Module>();

	private HashMap<String, Integer> mapSources = new HashMap<String, Integer>(); 
	
	// Nombre de los miembros activos 
	private Stack<String>  members  = new Stack<String>();

	// Fichero fuente activo (incluido pseudofiles en memory)
    private Source source = null;
	private Module module = null;
	    
    private int     numModulos;
    private int     estado = 0;;
    private boolean exist = false;
    
    public Unit() {
    	
    }
    
	public Unit (Archive archivo) {
        addSource(new Source(archivo));        
	}
	
	public Source getMainSource()    { return sources.get(0); }
    public boolean existUnit()       { return exist;    }
    public void    setExist()        { exist = true;    }

    public Source getCurrentSource() { return source; 	}
    public Source setCurrentSource(String name, int type) {
    	String key = type + name;
    	Integer pos = mapSources.get(key);
    	if (pos == null) return null;
    	members.push(name);
    	source = sources.get(pos);
    	module = modules.get(pos);
    	return source;
    }
    
	public Source addSource(Source source) {
		int tipo = source.getTipo();
		String key = tipo + source.getBaseName();
		Integer pos = mapSources.get(key);
		if (pos != null) {
			this.source = sources.get(pos);
			this.module = modules.get(pos);
		}
		else {
			sources.add(source);
			Module m = new Module(source.getFullName());
			m.setUnit(this);
			m.setSource(source);
			modules.add(m);
			mapSources.put(key, sources.size() - 1);
			this.source = source;
			this.module = m;
		}
		
		members.push(source.getBaseName());
    	return this.source;
	}
	    
    public void removeSource() {
        if (sources.size() > 0) sources.remove(sources.size() - 1);
    }
    
    /*
    public void addModule(Module module) {
    	modules.add(module);
    	this.module = module;
    }
    */
    public Module getCurrentModule() {
    	return module;
    }
    
    public List<Module> getModules() {
    	return modules;
    }
    
	/**
	 * Verifica si la unidad de compilacion esta en la lista de archivos a ignoraar
	 * @param archivo
	 * @return
	 */
	public int getStatus() {
		return estado;
	}

	public void setStatus(int estado) {
		this.estado = estado;
	}

	public int getNumModulos() {
		return numModulos;
	}


	public String removeMember() {
		if (!members.empty()) return members.pop();
		removeSource();
		return null;
	}
	
	public String getMemberName() {
		if (!members.empty()) return members.peek();
		return "";		
	}
	
	public String[] getStackOfMembers() {
		return (String[]) members.toArray(new String[members.size()]);
	}
	
	public int numMembers() {
		return members.size();
	}
}

/*
public SDPUnit(Archive archivo) {
super(archivo);

SourcesFactory.cleanSources();
Source source = SourcesFactory.getSourceCode(archivo);
sources.add(source);

module = new Module(archivo.getFullPath());
modules.add(module);

members.push(source.getBaseName());

this.source = sources.get(0);
}

public Source getMainSource()    { return sources.get(0); }

public Source getCurrentSource() { return source; 	}	
public boolean existUnit()       { return exist;    }
public void    setExist()        { exist = true;    }

public void setFileObject(Object obj) {
objFile = obj;
}

public Object getFileObject(Object obj) {
return objFile;
}

public ArrayList<Module> getModules() {
return modules;
}

public Source addSource(Source source) {
addMember(source.getBaseName(), source);    	
this.source = source;
return source;
}

public void removeSource() {
if (sources.size() > 0) sources.remove(sources.size() - 1);
}

public void addMember(String name, Source source) {
members.push(name);
sources.add(source);
}

public String removeMember() {
if (!members.empty()) return members.pop();
removeSource();
return null;
}

public String getMemberName() {
if (!members.empty()) return members.peek();
return "";		
}

public String[] getStackOfMembers() {
return (String[]) members.toArray(new String[members.size()]);
}

public int numMembers() {
return members.size();
}
*/