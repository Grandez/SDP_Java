package com.jgg.sdp.blocks.reflect;

import java.util.List;

import com.jgg.sdp.blocks.stmt.Option;

public interface IStatement { // extends Reflect {

	// Propiedades
	public boolean hasEndPoint();
	public boolean hasOption(String name);
	
	// Atributos (Empiezan con get)
	

	// Metodos (Evitar sobrecarga de funciones)
	public Object        getOptionByName(String name);
	public List<Option>  getOptionList  ();
	public Object        getLValue      ();
	public Object        getLValueList  ();
	public Object        getRValue      ();
	public Object        getRValueList  ();
	
}
