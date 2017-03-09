/**
 * Almacena el codigo fuente original
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import java.io.IOException;
import java.io.Reader;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.Firma;

public class Source extends Reader {

	private String        moduleName = null;
	private String        fullName   = null;
	
	private String        digest  = null;
	private char[]        rawData = null;
	private char[]        data    = null;
	private int           size    = 0;
	private int           offset  = 0;
	private int           length  = 0;

	private Configuration cfg = Configuration.getInstance();
	
    public Source(String fullName) {
    	this.fullName = fullName;
    }
    
    public void setSize(int size) {
    	this.size = size;
    	rawData = new char[size + 1];
    }
    
	public char[] getRawData() {
		return rawData;
	}
	
	public void setModuleName(String name) {
		moduleName = name;
	}
    public String getModuleName() {
		return moduleName;
	}
	
	public void setFullName(String path) {
		fullName = path;
	}
	public String getFullName() {
		return fullName;
	}
	
	public String getDigest() {
		return digest;
	}

	public char[] getData() {
		return data;
	}

	public int getSize() {
		return size;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	/*
     * Quita los caracteres no relevantes por la derecha
     * y por la izquierda
     */
	public void prepareData() {
    	int pos = 0;
		int col = 0;

		int left  = cfg.getInteger(CFG.MARGIN_LEFT,  0) - 1;
    	int right = cfg.getInteger(CFG.MARGIN_RIGHT, 0);
		
    	if (right == 0) right = Integer.MAX_VALUE;
    	if (left   < 0) left = 0;
    	
    	digest = Firma.calculate(new String(rawData).getBytes());

    	data = new char[size + 1];
    	length = 0;
		
    	if (right == 0) right = size;
        
    	// Lee caracter a caracter
     	while (pos < size) {
     		// Quitar el lado izquierdo
     		for (col = 0; col < left; col++) {
     			if (rawData[col+pos] == '\r' || rawData[col+pos] == '\n') break; // Fin de linea
     			pos++;
     		}
     		
     		while (col < right) {
     			if (pos == size)  break; // EOF
     			if (rawData[pos] == '\r' || rawData[pos] == '\n') break; // Fin de linea
     			data[length++] = rawData[pos++];
     			col++;
     		}
			// Salta los caracteres hasta fin de linea
			while (rawData[pos] != '\r' && rawData[pos] != '\n') {
				pos++;
			}
			// Controlar uno o dos caracteres
			if (rawData[pos] == '\r') 	{
				data[length++] = '\r';
				pos++;
			}
			data[length++] = '\n';
			pos++;
		}
    }
    
	@Override
	public int read() throws IOException {
		return (offset == length) ? -1 : data[offset++];
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int leido = 0;
		int to   = 0;
		
		if (offset == length) return 0;
		
		offset = off;
		while (leido < len && offset < length) {
			cbuf[to++] = data[offset++];
			leido++;
		}
		return leido;
	}
	
	@Override
	public void close() throws IOException {
       offset = 0;
	}

}
