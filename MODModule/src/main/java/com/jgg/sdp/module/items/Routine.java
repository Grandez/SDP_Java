package com.jgg.sdp.module.items;

import java.util.*;

import com.jgg.sdp.core.ctes.CDG;

public class Routine {
	private String  nombre;
	private Integer metodo = 0;
	private Integer modo = 0;
	private Integer begLine;
	private Integer begColumn;
	private Integer endLine;
	private Integer endColumn;
	private Integer estado = CDG.DEP_ST_NOT_PARSED;
	private Integer refs = 1;
	
	private HashSet<String>             values = new HashSet<String>();
	private HashMap<String, Routine> deps   = new HashMap<String, Routine>();
	
	public Routine() {
	}
	
	public Routine(String nombre, Integer metodo, Integer subtipo) {
		this.nombre  = nombre;
		this.metodo  = metodo;
	}
	
	// Constructor de copia
	public Routine (Routine dep) {
	   this.nombre    = dep.getNombre();
	   this.metodo    = dep.getMetodo();
	   this.modo      = dep.getModo();
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
	
	public void addDependence(Routine dep) {
		deps.put(dep.getNombre(), dep);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getMetodo() {
		return metodo;
	}
	public void setMetodo(Integer metodo) {
		this.metodo |= metodo;
	}
	public Integer getModo() {
		return modo;
	}
	public void setModo(Integer modo) {
		this.modo |= modo;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
    public Integer getEstado() {
    	return estado;
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
	public void setRefs(Integer refs) {
		this.refs = refs;
	}
    public Integer getRefs() {
    	return refs;
    }

    public void incRefs() {
    	refs++;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((metodo == null) ? 0 : metodo.hashCode());
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
		Routine other = (Routine) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (metodo == null) {
			if (other.metodo != null)
				return false;
		} else if (!metodo.equals(other.metodo))
			return false;
		return true;
	}

    

}
