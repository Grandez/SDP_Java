package com.jgg.sdp.module.unit;
/**
 * 
 * Unidad de compilacion.
 * Una unidad de compilacion es un conjunto de modulos fuente y copybooks
 * 
 * Mantiene los fuentes que son utilizados asumiendo que el primero es
 * el modulo principal
 *   
 * @author Javier Gonzalez Grandez
 * @version 4.0
 *   
 */


import java.util.*;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.ctes.MSG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.core.tools.Fechas;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.factorias.*;

public class SDPUnit {

	private Configuration cfg = Configuration.getInstance();

	// Lista de ficheros fuente (al menos uno)
	private ArrayList<Source> sources = new ArrayList<Source>();
	
	// Lista de modulos. Modulo es un programa y un fichero
	// Puede tener varios modulos
	private ArrayList<Module> modules = new ArrayList<Module>();

	// Nombre de los miembros activos 
	private Stack<String>  members  = new Stack<String>();

	// Fichero fuente activo (incluido pseudofiles en memory)
    private Source source = null;
    
    // Modulo activo
    private Module module = null;
    
    private long   id;
    private String nombre;
    private int    tipo;
    private String firma;
    private int    numModulos;
    private int    estado;
    
	public SDPUnit (Archivo archivo) {
        SourcesFactory.cleanSources();
        
        Source source = SourcesFactory.getSourceCode(archivo);
        sources.add(source);
        modules.add(ModulesFactory.getModule(source));
        
        module = modules.get(0);
        module.setName(source.getBaseName());
        
        members.push(source.getBaseName());

        this.firma = source.getFirma();
        this.source = sources.get(0);
        this.id = Fechas.serial();
        this.numModulos = 1;
        this.nombre = archivo.getBaseName();
        this.tipo = CDG.SOURCE_CODE;
        this.estado = CDG.STATUS_UNDEF;
	}
	
	public Source getMainSource()    { return sources.get(0); }
	public Module getCurrentModule() { return module; 	}
    public Source getCurrentSource() { return source; 	}	

    public ArrayList<Module> getModules() {
    	return modules;
    }
    
    public Source addSource(Source source) {
    	addMember(source.getBaseName(), source);    	
    	this.source = source;
    	return source;
    }
    
    public void removeSource() {
    	sources.remove(sources.size() - 1);
    	removeMember();
    }
    
	/**
	 * Verifica si la unidad de compilacion esta en la lista de archivos a ignoraar
	 * @param archivo
	 * @return
	 */
	public int isIgnored() {
		return cfg.isIgnored(source.getBaseName()) ? MSG.IGNORED : MSG.OK;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getStatus() {
		return estado;
	}

	public void setStatus(int estado) {
		this.estado = estado;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public int getNumModulos() {
		return numModulos;
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
}
