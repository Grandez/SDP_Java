/**
 * Mantiene el control de las secciones declaradas en el programa
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.module.items;

import com.jgg.sdp.core.ctes.CDG;

public class Sections {

	int divId   = -1;
	int divEnv  = -1;
	int divData = -1;
	int divPrc  = -1;
	
	int sectConf   = -1;
	int sectIO     = -1;
	int sectFile   = -1;
	int sectWork   = -1;
	int sectLocal  = -1;
	int sectLink   = -1;
	int sectScreen = -1;

	public int getTypeByPosition() {
	   if (divPrc   != -1) return CDG.DEP_CODE;
	   if (sectLink != -1) return CDG.DEP_LINK;
	   if (divData  != -1) return CDG.DEP_VAR;
	   if (divEnv   != -1) return CDG.DEP_FILE;
	   if (divId    != -1) return CDG.DEP_CPY;
	   return CDG.DEP_CPY;
	}
	
	public int getIdentification() {
		return divId;
	}
	public void setIdentification(int divId) {
		this.divId = divId;
	}
	public int getEnvironment() {
		return divEnv;
	}
	public void setEnvironment(int divEnv) {
		this.divEnv = divEnv;
	}
	public int getData() {
		return divData;
	}
	public void setData(int divData) {
		this.divData = divData;
	}
	public int getProcedure() {
		return divPrc;
	}
	public void setProcedure(int divPrc) {
		this.divPrc = divPrc;
	}
	public int getConfiguration() {
		return sectConf;
	}
	public void setConfiguration(int sectConf) {
		this.sectConf = sectConf;
	}
	public int getInputOutput() {
		return sectIO;
	}
	public void setInputOutput(int sectIO) {
		this.sectIO = sectIO;
	}
	public int getFile() {
		return sectFile;
	}
	public void setFile(int sectFile) {
		this.sectFile = sectFile;
	}
	public int getWorkingStorage() {
		return sectWork;
	}
	public void setWorkingStorage(int sectWork) {
		this.sectWork = sectWork;
	}
	public int getLocalStorage() {
		return sectLocal;
	}
	public void setLocalStorage(int sectLocal) {
		this.sectLocal = sectLocal;
	}
	public int getLinkage() {
		return sectLink;
	}
	public void setLinkage(int sectLink) {
		this.sectLink = sectLink;
	}
	public int getScreen() {
		return sectScreen;
	}
	public void setScreen(int sectScreen) {
		this.sectScreen = sectScreen;
	}
	

	public boolean hasDataDivision() {
		return (divData != -1);
	}
	
	public boolean hasWorkingStorage() {
		return (sectWork != -1);
	}
	
	public boolean hasLocalStorage() {
		return (sectLocal != -1);
	}
	
	public boolean hasLinkage() {
		return (sectLink != -1);
	}
	
	public boolean hasScreen() {
		return (sectScreen != -1);
	}

}
