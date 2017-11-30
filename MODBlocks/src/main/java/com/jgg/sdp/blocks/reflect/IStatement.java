package com.jgg.sdp.blocks.reflect;

import java.util.List;

import com.jgg.sdp.blocks.stmt.Option;

public interface IStatement extends IReflect {

	// Propiedades
	public boolean hasEndPoint();
	public boolean hasOption(String name);
	
	// Atributos
	public int lines();	

	// Metodos (Evitar sobrecarga de funciones)
	public Object        getOptionByName(String name);
	public List<Option>  getOptionList  ();
	public Object  getLValue      ();
	public Object  getLValueList  ();
	public Object  getRValue      ();
	public Object  getRValueList  ();
	
}
