package com.jgg.sdp.ivp.base;

import java.io.File;
import java.nio.file.*;

import com.jgg.sdp.ivp.jaxb.IVPConfigType;

public class IVPConfig {

	private String baseDir = null;
	private String workingDir;
	private String binDir;
	private String cobDir;
		
	public IVPConfig() { 
		workingDir = Paths.get("").toAbsolutePath().toString();
	}
	
	public IVPConfig(IVPConfig cfg) {
		if (cfg != null) {
		    this.baseDir    = cfg.getBaseDir();
		    this.workingDir = cfg.getWorkingDir();
		    this.binDir     = cfg.getBinDir();
            this.cobDir     = cfg.getCobolDir();
		}
	}
		
	public void update(IVPConfigType other) {
		if (other == null) return;
		setBaseDir(other.getBaseDir());
		setWorkingDir(other.getWorkingDir());
		setBinDir(other.getWorkingDir());
		setCobolDir(other.getCobolDir());
	}
	
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		if (baseDir == null || baseDir.length() == 0) return;
		if (baseDir.charAt(baseDir.length() - 1) == '/' || 
			baseDir.charAt(baseDir.length() - 1) == '\\' ) {
		    this.baseDir = baseDir;
		}
		else {
			this.baseDir = baseDir + File.separatorChar;
		}
	}
	public String getWorkingDir() {
		return workingDir;
	}
	
	public void setWorkingDir(String workingDir) {
		if (workingDir == null || workingDir.length() == 0) return;		
		this.workingDir = isFull(workingDir) ? workingDir : baseDir + workingDir;
	}
	
	public String getBinDir() {
		return binDir;
	}
	
	public void setBinDir(String binDir) {
		if (binDir == null || binDir.length() == 0) return;		
		this.binDir = isFull(binDir) ? binDir : baseDir + binDir;
	}
	
	public String getCobolDir() {
		return cobDir;
	}

	public void setCobolDir(String cobDir) {
		if (cobDir == null || cobDir.length() == 0) return;		
		this.cobDir = isFull(cobDir) ? cobDir : baseDir + cobDir;
	}

	private boolean isFull(String data) {
		if (data.length() > 0) {
		   if (data.charAt(0) == '/')  return true;
		   if (data.charAt(0) == '\\') return true;
		}
		if (data.length() > 1 && data.charAt(1) == ':') return true;
		return false;
	}
}
