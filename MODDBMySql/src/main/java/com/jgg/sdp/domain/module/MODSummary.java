package com.jgg.sdp.domain.module;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="MOD_SUMMARY")
public class MODSummary implements Serializable {

	private static final long serialVersionUID = -3227812485978179383L;

	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Column(name="fichero")
	Integer fichero;

	@Column(name="sgdb")
	Integer sgdb;
	
	@Column(name="cics")
	Integer cics;
	
	@Column(name="mq")
	Integer mq;

	@Column(name="callsCount")
	Integer callsCount;
	
	@Column(name="callsMode")
	Integer callsMode;

	@Column(name="callsType")
	Integer callsType;
	
	@Column(name="ts")
	Integer ts;
	
	@Column(name="tsmode")
	Integer tsMode;
	
	@Column(name="memoria")
	Integer memoria;

	@Column(name="dinamico")
	Integer dinamico;
	
	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getFichero() {
		return fichero;
	}

	public void setFichero(Integer fichero) {
		this.fichero = fichero;
	}

	public void setFichero(boolean fichero) {
		this.fichero = fichero ? 1 : 0;
	}

	public boolean hasFichero() {
		return this.fichero == 0 ? false : true;
	}
	
	public Integer getSgdb() {
		return sgdb;
	}

	public void setSgdb(Integer sgdb) {
		this.sgdb = sgdb;
	}

	public void setSgdb(boolean sgdb) {
		this.sgdb = sgdb ? 1 : 0;
	}

	public boolean hasSgdb() {
		return this.sgdb == 0 ? false : true;
	}
	
	public Integer getCics() {
		return cics;
	}

	public void setCics(Integer cics) {
		this.cics = cics;
	}

	public void setCics(boolean cics) {
		this.cics = cics ? 1 : 0;
	}

	public boolean hasCics() {
		return this.cics == 0 ? false : true;
	}
	
	public Integer getMq() {
		return mq;
	}

	public void setMq(Integer mq) {
		this.mq = mq;
	}

	public void setMq(boolean mq) {
		this.mq = mq ? 1 : 0;
	}

	public boolean hasMq() {
		return this.mq == 0 ? false : true;
	}
		
	public Integer getCallsCount() {
		return callsCount;
	}

	public void setCallsCount(Integer callsCount) {
		this.callsCount = callsCount;
	}

	public Integer getCallsMode() {
		return callsMode;
	}

	public void setCallsMode(Integer callsMode) {
		this.callsMode = callsMode;
	}

	public Integer getCallsType() {
		return callsType;
	}

	public void setCallsType(Integer callsType) {
		this.callsType = callsType;
	}

	public Integer getTs() {
		return ts;
	}

	public void setTs(Integer ts) {
		this.ts = ts;
	}

	public void setTs(boolean ts) {
		this.ts = ts ? 1 : 0;
	}

	public boolean hasTs() {
		return this.ts == 0 ? false : true;
	}
	
	public Integer getTsMode() {
		return tsMode;
	}

	public void setTsMode(Integer tsMode) {
		this.tsMode = tsMode;
	}

	public Integer getMemoria() {
		return memoria;
	}

	public void setMemoria(Integer memoria) {
		this.memoria = memoria;
	}

	public Integer getDinamico() {
		return dinamico;
	}

	public void setDinamico(boolean dinamico) {
		this.dinamico = (dinamico == false) ? 0 : 1;
	}

}
