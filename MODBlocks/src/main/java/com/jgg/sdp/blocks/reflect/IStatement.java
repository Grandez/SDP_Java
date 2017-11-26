package com.jgg.sdp.blocks.reflect;

public interface IStatement {

	// Propiedades
	public boolean hasEndPoint();
	public boolean hasOption(String name);
	
	// Atributos
	public int lines();	
}
