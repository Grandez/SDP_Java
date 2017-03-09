package com.jgg.sdp.excel.data;

public interface XLSDataRecord {

	public boolean open(String table, Long key);
	public boolean next();
	public boolean close();
	public Object  getValue(String member);
	public int     getLevel();
}
