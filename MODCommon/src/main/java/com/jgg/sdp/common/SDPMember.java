/**
 * Almacena el codigo fuente original
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.common;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import com.jgg.sdp.common.ctes.MSG;
import com.jgg.sdp.common.exceptions.FileException;
import com.jgg.sdp.tools.Firma;
import com.jgg.sdp.tools.Zipper;

public class SDPMember implements Serializable {

	private static final long serialVersionUID = 2007096407237498264L;

	private File   file    = null;
	
	private String   firma   = null;
	private char[]   rawData = null;
	private byte[]   zipData = null;
	private int      size    = 0;
	private int      length  = 0;

	private int      type    = 0;
	
	public SDPMember(String fileName, int type) {
		this.file = new File(fileName);
		this.type = type;
	}
	
	public void loadData() {
		
        checkFile();
        size = ((Long)file.length()).intValue();
        
        rawData = new char[size + 1];
        
		try {
        	FileReader reader = new FileReader(file);
        	reader.read(rawData, 0, size);
        	reader.close();
        	firma = Firma.calculate(new String(rawData));
        	zipData = Zipper.zip(getFullName(), rawData);
        	rawData = null;
        }
        catch (Exception e) {
	    	throw new FileException(MSG.FILE_NOT_READ, file.getAbsolutePath());
        }
		
	}
    

	public char[] getRawData() {
		return rawData;
	}
	
	public byte[] getZipData() {
		return zipData;
	}

    public String getBaseName() {
    	String n = file.getName();
    	int pos = n.lastIndexOf('.');
    	if (pos > -1) return n.substring(0, pos);
		return n;
	}
	
	public String getFullName() {
		return file.getAbsolutePath();
	}
	
	public int getSize() {
		return size;
	}

	public int getLength() {
		return length;
	}

	public void setTipo(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public String getFirma() {
		return firma;
	}
	
	private void checkFile() {
	    if (file.exists() == false) {
	    	throw new FileException(MSG.FILE_NOT_EXIST, file.getAbsolutePath());
	    }
        if (!file.canRead()) {
	    	throw new FileException(MSG.FILE_NOT_READ, file.getAbsolutePath());
	    }
	}
	
}
