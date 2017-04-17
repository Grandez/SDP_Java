package com.jgg.sdp.domain.module;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="MOD_CODIGO")
public class MODCodigo implements Serializable {

	private static final long serialVersionUID = -8218083665196693143L;

	public final static String find = "SELECT r FROM MODResumen r WHERE r.id = ?1";
	
	@Id
	@Column(name="idVersion")
	Long idVersion;
	
	@Column(name="lineas")
	Integer lineas;

	@Column(name="parrafos")
	Integer parrafos;

	@Column(name="sentencias")
	Integer sentencias;
	
	@Column(name="blancos")
	Integer blancos;
	
	@Column(name="comentarios")
	Integer comentarios;

	@Column(name="bloques")
	Integer bloques;
	
	@Column(name="decoradores")
	Integer decoradores;
	
	@Column(name="verbosData")
	Integer verbosData;

	@Column(name="verbosIO")
	Integer verbosIO;

	@Column(name="verbosControl")
	Integer verbosControl;

	@Column(name="verbosFlujo")
	Integer verbosFlujo;

	@Column(name="verbosArit")
	Integer verbosArit;

	@Column(name="verbosLang")
	Integer verbosLang;

	@Column(name="verbosCics")
	Integer verbosCics;

	@Column(name="verbosSql")
	Integer verbosSql;
	
	@Column(name="ficheros")
	Integer ficheros;
	
	@Column(name="loops")
	Integer loops;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public Integer getLineas() {
		return lineas;
	}

	public void setLineas(Integer lineas) {
		this.lineas = lineas;
	}

	public Integer getParrafos() {
		return parrafos;
	}

	public void setParrafos(Integer parrafos) {
		this.parrafos = parrafos;
	}

	public Integer getSentencias() {
		return sentencias;
	}

	public void setSentencias(Integer sentencias) {
		this.sentencias = sentencias;
	}

	public Integer getBlancos() {
		return blancos;
	}

	public void setBlancos(Integer blancos) {
		this.blancos = blancos;
	}

	public Integer getComentarios() {
		return comentarios;
	}

	public void setComentarios(Integer comentarios) {
		this.comentarios = comentarios;
	}

	public Integer getBloques() {
		return bloques;
	}

	public void setBloques(Integer bloques) {
		this.bloques = bloques;
	}

	public Integer getDecoradores() {
		return decoradores;
	}

	public void setDecoradores(Integer decoradores) {
		this.decoradores = decoradores;
	}

	public Integer getVerbosData() {
		return verbosData;
	}

	public void setVerbosData(Integer verbosData) {
		this.verbosData = verbosData;
	}

	public Integer getVerbosIO() {
		return verbosIO;
	}

	public void setVerbosIO(Integer verbosIO) {
		this.verbosIO = verbosIO;
	}

	public Integer getVerbosControl() {
		return verbosControl;
	}

	public void setVerbosControl(Integer verbosControl) {
		this.verbosControl = verbosControl;
	}

	public Integer getVerbosFlujo() {
		return verbosFlujo;
	}

	public void setVerbosFlujo(Integer verbosFlujo) {
		this.verbosFlujo = verbosFlujo;
	}

	public Integer getVerbosArit() {
		return verbosArit;
	}

	public void setVerbosArit(Integer verbosArit) {
		this.verbosArit = verbosArit;
	}

	public Integer getVerbosLang() {
		return verbosLang;
	}

	public void setVerbosLang(Integer verbosLang) {
		this.verbosLang = verbosLang;
	}

	public Integer getVerbosCics() {
		return verbosCics;
	}

	public void setVerbosCics(Integer verbosCics) {
		this.verbosCics = verbosCics;
	}

	public Integer getVerbosSql() {
		return verbosSql;
	}

	public void setVerbosSql(Integer verbosSql) {
		this.verbosSql = verbosSql;
	}

	public Integer getFicheros() {
		return ficheros;
	}

	public void setFicheros(Integer ficheros) {
		this.ficheros = ficheros;
	}

	public Integer getLoops() {
		return loops;
	}

	public void setLoops(Integer loops) {
		this.loops = loops;
	}

}
