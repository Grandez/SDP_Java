package com.jgg.sdp.module.graph;

public class Grafo {

	private int id;
	private int level;
	private String name;
	
	public Grafo(int id, int level, String name) {
		this.id = id;
		this.name = name;
		this.level = level;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
