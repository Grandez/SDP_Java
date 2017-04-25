/*
 * 
 */
package com.jgg.sdp.collector.consolidator;

import java.sql.Timestamp;

public class ConsClaves {

    // La sesion es unica 
	private String idSesion   = "";
	
	// Modulo principal
	private Long   mainId      = 0L;
	private Long   mainVersion = 0L;
	private Long   mainAppl    = 0L;
    private String mainFirma   = "";
	private String mainNombre  = "";
	
	// Modulo en proceso
	private Long   modId      = 0L;
	private Long   modVersion = 0L;
	private Long   modAppl    = 0L;
	
    private String modNombre  = "";
    private String modFirma   = "";
    
	// Intervalos de mensajes
	private Long   minOrden  = 0L;
	private Long   maxOrden  = 0L;
	
	private Timestamp tms;
	private String    uid;
    public String getIdSesion() {
        return idSesion;
    }
    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }
    public String getMainFirma() {
        return mainFirma;
    }
    public void setMainFirma(String mainFirma) {
        this.mainFirma = mainFirma;
    }
    public Long getMainId() {
        return mainId;
    }
    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
    public Long getMainVersion() {
        return mainVersion;
    }
    public void setMainVersion(Long mainVersion) {
        this.mainVersion = mainVersion;
    }
    public Long getMainAppl() {
        return mainAppl;
    }
    public void setMainAppl(Long mainAppl) {
        this.mainAppl = mainAppl;
    }
    public String getMainNombre() {
        return mainNombre;
    }
    public void setMainNombre(String mainNombre) {
        this.mainNombre = mainNombre;
    }
    public String getModFirma() {
        return modFirma;
    }
    public void setModFirma(String modFirma) {
        this.modFirma = modFirma;
    }
    public Long getModId() {
        return modId;
    }
    public void setModId(Long modId) {
        this.modId = modId;
    }
    public Long getModVersion() {
        return modVersion;
    }
    public void setModVersion(Long modVersion) {
        this.modVersion = modVersion;
    }
    public Long getMinOrden() {
        return minOrden;
    }
    public void setMinOrden(Long minOrden) {
        this.minOrden = minOrden;
    }
    public Long getMaxOrden() {
        return maxOrden;
    }
    public void setMaxOrden(Long maxOrden) {
        this.maxOrden = maxOrden;
    }
    public Timestamp getTms() {
        return tms;
    }
    public void setTms(Timestamp tms) {
        this.tms = tms;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public Long getModAppl() {
        return modAppl;
    }
    public void setModAppl(Long modAppl) {
        this.modAppl = modAppl;
    }
    public String getModNombre() {
        return modNombre;
    }
    public void setModNombre(String modNombre) {
        this.modNombre = modNombre;
    }
	
}
