package com.jgg.sdp.core.unit;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;
import com.jgg.sdp.core.tools.Archivo;
import com.jgg.sdp.tools.*;

public class SDPUnitBase {

	// Lista de ficheros fuente (al menos uno)
	private ArrayList<Source> sources = new ArrayList<Source>();
	
	// Nombre de los miembros activos 
	private Stack<String>  members  = new Stack<String>();

	// Fichero fuente activo (incluido pseudofiles en memory)
    private Source source = null;
    
    private long    id;
    private String  nombre;
    private int     tipo;
    private String  firma;
    private int     numModulos;
    private int     estado;
    private boolean exist = false;
    
	public SDPUnitBase (Archivo archivo) {
        SourcesFactory.cleanSources();
        
        Source source = SourcesFactory.getSourceCode(archivo);
        sources.add(source);
        
        members.push(source.getBaseName());

        this.firma = source.getFirma();
        this.source = sources.get(0);
        this.id = Fechas.serial();
        this.numModulos = 0;
        this.nombre = archivo.getBaseName();
        this.tipo = CDG.SOURCE_CODE;
        this.estado = CDG.STATUS_UNDEF;
	}
	
	public Source getMainSource()    { return sources.get(0); }
    public Source getCurrentSource() { return source; 	}	
    public boolean existUnit()       { return exist;    }
    public void    setExist()        { exist = true;    }
        
    public Source addSource(Source source) {
    	addMember(source.getBaseName(), source);    	
    	this.source = source;
    	return source;
    }
    
    public void removeSource() {
        if (sources.size() > 0) sources.remove(sources.size() - 1);
    }
    
	/**
	 * Verifica si la unidad de compilacion esta en la lista de archivos a ignoraar
	 * @param archivo
	 * @return
	 */
/*    
	public int isIgnored() {
		return cfg.isIgnored(source.getBaseName()) ? MSG.IGNORED : MSG.OK;
	}
*/
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
