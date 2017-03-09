/**
 * Wrapper de la clase java.io.File
 * Se usa para implementar getBaseName y getExt 
 * 
 * @author Javier Gonzalez Grandez
 * @version 3.0
 * @date    SEP - 2015
 * 
 */
package com.jgg.sdp.core.tools;

import java.net.URI;

public class Archivo extends java.io.File {

	private static final long serialVersionUID = -6418706965490670167L;

    private String driveName;
	private String dirName;
	private String fileName;
	private String extName;
	
	/**
     * Crea un nueo Archivo a partir de un objeto File
     *
     * @param parent El objeto File
     * @param child El nombre del fichero
     */
	public Archivo(java.io.File parent, String child) {
		super(parent, child);
	}

	/**
     * Genera un objeto Archivo a partir de una cadena con el nombre del fichero
     *
     * @param pathname Nombre del fichero
     */
	public Archivo(String pathname) {
		super(pathname);
		splitPath(pathname);
	}
	
	/**
     * Genera un nuevo objeto Archivo a partir de su padre
     *
     * @param parent El nombre del padre
     * @param child El nombre del archivo
     */
	public Archivo(String parent, String child) {
		super(parent, child);
	}
	
	/**
     * Crea un nuevo Archivo a partir de un objeto URI
     *
     * @param uri El objeto URI
     */
	public Archivo(URI uri) {
		super(uri);
	}
	
	public String getBaseName()  { return fileName;  }
	public String getDirName()   { return dirName;   }
	public String getDriveName() { return driveName; }
	public String getExtName()   { return extName;   }
	
	public String getFileName()  { 
		return (extName.length() == 0) ? fileName : fileName + "." + extName;
	}
	
	public String getFullDirName() { 
		return (driveName.length() == 0) ? dirName : driveName + ":" + dirName;
	}
	
	public String getFullPath() {
		return getFullDirName() + getFileName();
	}

	/**
     * Chequea si tiene disco
     *
     * @return true, si cierto
     */
	public boolean hasDrive() { return (driveName.length() > 0); }
	
	/**
     * Chequea si tiene directorios
     *
     * @return true, si cierto
     */
	public boolean hasDir()   { return (dirName.length()   > 0); }
	
	/**
     * Chequea si tiene nombre de fichero
     *
     * @return true, si cierto
     */
	public boolean hasFile()  { return (fileName.length()  > 0); }
	
	/**
     * Chequea si tiene extension
     *
     * @return true, si cierto
     */
	public boolean hasExt()   { return (extName.length()   > 0); }

	private void splitPath(String fullName) {
		int pos = fullName.lastIndexOf('/');
		int pos2 = fullName.lastIndexOf('\\');
		if (pos2 > pos) pos = pos2;

		String d = (pos == -1) ? ""       : fullName.substring(0, pos + 1);
		String f = (pos == -1) ? fullName : fullName.substring(pos + 1);

		if (d.length() > 2) { 
		    driveName = (d.charAt(1) == ':') ? d.substring(0,1) : "";
		    dirName   = (d.charAt(1) == ':') ? d.substring(2)   : d;
		}
		else {
		    driveName = "";
		    dirName   = d;
		}		    	

		pos = f.lastIndexOf('.');
		fileName = (pos == -1) ? f  : f.substring(0, pos);
		extName  = (pos == -1) ? "" : f.substring(pos + 1);
	}
	
}
