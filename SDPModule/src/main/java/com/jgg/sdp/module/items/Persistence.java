/**
 * Implementa un objeto de persistencia
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.util.*;

public class Persistence {

	public final static int SEQUENTIAL = 0x01;
	public final static int RELATIVE   = 0x02;
    public final static int INDEXED    = 0x04;
    public final static int TABLE      = 0x10;
    public final static int CURSOR     = 0x20;    
	
    public final static int INPUT      = 0x01;
    public final static int OUTPUT     = 0x02;
    public final static int EXTEND     = 0x04;
    public final static int IO         = 0x08;

    public final static int READ       = 0x01;
    public final static int WRITE      = 0x02;
    
    private String  logicalName;
    private String  physicalName;
    private ArrayList<String>  recordNames = new ArrayList<String>();
    private boolean master;
	
    private int type;
	private int access = 0x00;
	private int pos = -1;
	
	public Persistence() {
		
	}
	
	public Persistence (String name, boolean master) {
		logicalName = name;
		this.master = master;
	}

	public void setLogicalName(String name) {
		logicalName = name;
	}
	
	public String getLogicalName() {
		return logicalName;
	}
	
	public void setPhysicalName(String name) {
		physicalName = name;
	}
	
	public String getPhysicalName() {
		if (physicalName == null) return "N/A"; 		//JGG Problemas en el ASSIGN. TO DO
		return physicalName;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public void setAccess(int access) {
		this.access |= access;
	}
	
	public int getAccess() {
		return access;
	}
	
	public boolean accessRead() {
		return (this.access & READ) != 0;
	}
	
	public boolean accessWrite() {
		return (this.access & WRITE) != 0;
	}

	public void setRecordName(String record) {
		this.recordNames.add(record);
	}

	public void setRecordNames(List<String> records) {
		this.recordNames.addAll(records);
	}
	
	public int getNumRecordNames() {
		return recordNames.size();
	}
	
	public List<String> getRecordNames() {
		return recordNames;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public int getPos() {
		return pos;
	}
	
	public boolean isMaster() {
		return master;
	}
}
