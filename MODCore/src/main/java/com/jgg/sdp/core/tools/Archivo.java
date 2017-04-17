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
	
	public Archivo(java.io.File parent, String child) {
		super(parent, child);
	}

	public Archivo(String pathname) {
		super(pathname);
		splitPath(pathname, true);
	}
	
	public Archivo(String parent, String child) {
		super(parent, child);
	}
	
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

	public void setFullPath(String fullPath) {
		splitPath(fullPath, false);
	}

	public boolean isRelative() { return (dirName.length()  == 0); }
	public boolean hasDrive()   { return (driveName.length() > 0); }
	public boolean hasDir()     { return (dirName.length()   > 0); }
	public boolean hasFile()    { return (fileName.length()  > 0); }
	public boolean hasExt()     { return (extName.length()   > 0); }

	private void splitPath(String fullName, boolean hasFile) {
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

		if (hasFile) { 
		   pos = f.lastIndexOf('.');
		   fileName = (pos == -1) ? f  : f.substring(0, pos);
		   extName  = (pos == -1) ? "" : f.substring(pos + 1);
		}   
	}
	
}
