package com.jgg.sdp.excel.data;

import java.util.ArrayList;
import java.util.HashMap;

public class XLSRoutine {

	private String nombre;
	private long   id;
	private int    refs;
	private int    modo;
	private int    metodo;
	private int    estado;
	private int    ejecutado;
	
	private int    firstLevel = 64000;
	private int    lastLevel  = 0;
	private int    level = 0;

	private boolean recursive = false;
	
	private HashMap<String, Object> map = null;
		
	private ArrayList<XLSRoutine> hijos = new ArrayList<XLSRoutine>();
	
	public XLSRoutine(String nombre) {
		this.nombre = nombre;
		this.id = 0;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getReferences() {
		return refs;
	}
	public void setReferences(int refs) {
		this.refs = refs;
	}
	public void addReferences(int refs) {
		this.refs += refs;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getModo() {
		return modo;
	}
	public void setModo(int modo) {
		this.modo = modo;
	}
	public int getEjecutado() {
		return ejecutado;
	}
	public void setEjecutado(int ejecutado) {
		this.ejecutado = ejecutado;
	}
	public void combineModo(int modo) {
		this.modo |= modo;
	}	
	public int getMetodo() {
		return metodo;
	}
	public void setMetodo(int metodo) {
		this.metodo = metodo;
	}
	
	public void combineMetodo(int metodo) {
		this.metodo |= metodo;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getFirstLevel() {
		return firstLevel;
	}
	
	public int getLastLevel() {
		return lastLevel;
	}

	public void setStatus(int status) {
		estado = status;
	}
	
	public int getStatus() {
		return estado;
	}
	
	public void setRecursive() {
		recursive = true;
	}
	
	public boolean isRecursive() {
		return recursive;
	}
	
	public void setLevel(int level) {
		this.level = level;
		if (firstLevel > level) firstLevel = level;
		if (lastLevel  < level) lastLevel = level;
	}
	
	public void addRoutine(XLSRoutine rut) {
         hijos.add(rut);		
	}
	
	public ArrayList<XLSRoutine> getRoutines() {
		return hijos;
	}
	
	public Object getByName(String name) {
		if (map == null) createMap();
		return map.get(name);
	}
	
	private void createMap() {
		map = new HashMap<String, Object>();
		map.put("ID", id);
		map.put("NAME", nombre);
		map.put("STATUS", estado);
		map.put("REFS", refs);
		map.put("LEVEL", level);
		map.put("FIRST", firstLevel);
		map.put("LAST",  lastLevel);
		map.put("METHOD", metodo);
		map.put("MODE", modo);
        map.put("EXECUTED", ejecutado); 
		map.put("RECURSIVE", recursive ? 1 : 0);
	}
}
