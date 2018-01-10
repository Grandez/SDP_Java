package com.jgg.sdp.blocks.reflect;

public interface IReflect {

	public String toValue();
	public String toString();
	public int    getBegLine();
	public int    getBegColumn();
	public int    getEndLine();
	public int    getEndColumn();
	public int    getLines();
	
}
