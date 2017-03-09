package com.jgg.sdp.module.items;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;

public class Copy {

	private String  nombre;
	private Integer tipo = 0;
	private Integer subtipo = 0;
	private Integer refs = 1;
	private Integer estado = CDG.DEP_ST_NOT_PARSED;
	
	private Integer begLine;
	private Integer begColumn;
	private Integer endLine;
	private Integer endColumn;

	private long    idFile = 0L;
	private long    idCopy = 0L;
	
	private HashSet<String>       values = new HashSet<String>();
	private HashMap<String, Copy> deps   = new HashMap<String, Copy>();
	
	public Copy() {
	}
	
	public Copy(String nombre, Integer tipo, Integer subtipo) {
		this.nombre  = nombre;
		this.tipo    = tipo;
		this.subtipo = subtipo;
	}
	
	// Constructor de copia
	public Copy (Copy dep) {
	   this.nombre    = dep.getNombre();
	   this.tipo      = dep.getTipo();
	   this.subtipo   = dep.getSubtipo();
	   this.begLine   = dep.getBegLine();
	   this.begColumn = dep.getBegColumn();
	   this.endLine   = dep.getEndLine();
	   this.endColumn = dep.getEndColumn();
	   this.estado    = dep.getEstado();
	   this.refs      = dep.getRefs();
	}

	public void addValue(String val) {
		values.add(val);
	}
	
	public void addDependence(Copy dep) {
		deps.put(dep.getNombre(), dep);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Integer getSubtipo() {
		return subtipo;
	}
	public void setSubtipo(Integer subtipo) {
		this.subtipo |= subtipo;
	}
	public Integer getBegLine() {
		return begLine;
	}
	public void setBegLine(Integer begLine) {
		this.begLine = begLine;
	}
	public Integer getBegColumn() {
		return begColumn;
	}
	public void setBegColumn(Integer begColumn) {
		this.begColumn = begColumn;
	}
	public Integer getEndLine() {
		return endLine;
	}
	public void setEndLine(Integer endLine) {
		this.endLine = endLine;
	}
	public Integer getEndColumn() {
		return endColumn;
	}
	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
    public Integer getEstado() {
    	return estado;
    }
	public void setRefs(Integer refs) {
		this.refs = refs;
	}
    public Integer getRefs() {
    	return refs;
    }
    public void incRefs() {
    	refs++;
    }

    public void setIdFile(long idFile) {
    	this.idFile = idFile;
    }
    public long getIdFile() {
    	return idFile;
    }

    public void setIdCopy(long idCopy) {
    	this.idCopy = idCopy;
    }
    public long getIdCopy() {
    	return idCopy;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Copy other = (Copy) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

    
}
