package com.jgg.sdp.domain.module;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MOD_SECCIONES")
/*
@NamedQueries({
    @NamedQuery(name="MODSecciones.find",
                query="SELECT s FROM MODSecciones s WHERE s.idVersion = ?1")
}) 
*/
public class MODSecciones implements Serializable {

	private static final long serialVersionUID = 3734850800677065354L;

	@Id
	@Column(name="idVersion")
	Long idVersion;

	@Column(name="divIdentification")
	Integer divIdentification;
	
	@Column(name="divEnvironment")
	Integer divEnvironment;
	
	@Column(name="divData")
	Integer divData;
	
	@Column(name="divProcedure")
	Integer divProcedure;

	@Column(name="sectConfiguration")
	Integer sectConfiguration;

	@Column(name="sectInputOutput")
	Integer sectInputOutput;

	@Column(name="sectFile")
	Integer sectFile;
	
	@Column(name="sectWorking")
	Integer sectWorking;

	@Column(name="sectLocal")
	Integer sectLocal;
	
	@Column(name="sectLinkage")
	Integer sectLinkage;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getDivIdentification() {
		return divIdentification;
	}

	public void setDivIdentification(Integer divIdentification) {
		this.divIdentification = divIdentification;
	}

	public Integer getDivEnvironment() {
		return divEnvironment;
	}

	public void setDivEnvironment(Integer divEnvironment) {
		this.divEnvironment = divEnvironment;
	}

	public Integer getDivData() {
		return divData;
	}

	public void setDivData(Integer divData) {
		this.divData = divData;
	}

	public Integer getDivProcedure() {
		return divProcedure;
	}

	public void setDivProcedure(Integer divProcedure) {
		this.divProcedure = divProcedure;
	}

	public Integer getSectConfiguration() {
		return sectConfiguration;
	}

	public void setSectConfiguration(Integer sectConfiguration) {
		this.sectConfiguration = sectConfiguration;
	}

	public Integer getSectInputOutput() {
		return sectInputOutput;
	}

	public void setSectInputOutput(Integer sectInputOutput) {
		this.sectInputOutput = sectInputOutput;
	}

	public Integer getSectFile() {
		return sectFile;
	}

	public void setSectFile(Integer sectFile) {
		this.sectFile = sectFile;
	}

	public Integer getSectWorking() {
		return sectWorking;
	}

	public void setSectWorking(Integer sectWorking) {
		this.sectWorking = sectWorking;
	}

	public Integer getSectLocal() {
		return sectLocal;
	}

	public void setSectLocal(Integer sectLocal) {
		this.sectLocal = sectLocal;
	}
	
	public Integer getSectLinkage() {
		return sectLinkage;
	}

	public void setSectLinkage(Integer sectLinkage) {
		this.sectLinkage = sectLinkage;
	}

}
